package seu.talents.cloud.talent.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.Data;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.common.annotation.TokenRequired;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dto.post.AdminDTO;
import seu.talents.cloud.talent.model.dto.post.LoginDTO;
import seu.talents.cloud.talent.model.dto.post.Register;
import seu.talents.cloud.talent.service.AccountService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RedisUtil;
import seu.talents.cloud.talent.util.TokenUtil;
import seu.talents.cloud.talent.util.WXBizDataCryptUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/account")
@WebResponse
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;


    String access_token = "";
    long expireTime = 0L;

    String getAccessToken(String openid) {
        if (System.currentTimeMillis() > expireTime) {
            // 使用appid和secret访问接口.获取公众号的access_token
            String wxApiUrl = "https://api.weixin.qq.com/cgi-bin/token?" +
                    "openid=" + openid +
                    "&secret=" + CONST.appSecret +
                    "&grant_type=client_credential";
            String respronse = restTemplate.getForObject(wxApiUrl, String.class);
            System.out.println("执行了");
        }
        return access_token;
    }

    /**
     * 登录
     * @param js_code
     * @return
     */
    @PostMapping("/login")
    public Object getLogin(
            @RequestParam String js_code
    ){
        // 微信登陆，获取openid
        String wxApiUrl = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + CONST.appId +
                "&secret=" + CONST.appSecret +
                "&js_code=" + js_code +
                "&grant_type=authorization_code";
        String respronse = restTemplate.getForObject(wxApiUrl, String.class);
        Map res = new Gson().fromJson(respronse, Map.class);
        System.out.println(res);

        String openid = (String) res.get("openid");
        if (openid != null && !openid.equals("")) {
            isRegister register = new isRegister();
            Account account = accountMapper.selectOneByExample(
                    Example.builder(Account.class).where(Sqls.custom().andEqualTo("openId",openid))
                            .build()
            );
            if(account != null){
                String accountId = account.getAccountId();
                String name = account.getName();
                if(name==null){
                    register.setIsRegister(false);
                }else{
                    register.setIsRegister(true);
                }
                register.setToken(TokenUtil.createToken(accountId));
                return register;
            }else {
                //需要新注册
                Account newAccount = new Account();
                newAccount.setOpenId(openid);
                newAccount.setAccountId(UUID.randomUUID().toString());
                accountMapper.insertSelective(newAccount);
                String token = TokenUtil.createToken(newAccount.getAccountId());
                register.setToken(token);
                register.setIsRegister(false);
                return register;
            }
        }else {
            throw new BizException(ConstantUtil.BizExceptionCause.NOT_OPENID);
        }
    }

    @Data
    class isRegister{
        Boolean isRegister;
        String token;
    }

    /**
     * 注册
     * @param register
     * @return
     */
    @TokenRequired
    @PostMapping("/alumni")
    public Object test(
            @RequestBody Register register
    ){
        //获取accountId
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        accountService.registerUser(register,accountId);
        return "success";
    }

    @PostMapping("/getToken")
    public Object getToken(){
//        String accountId= UUID.randomUUID().toString();
//        String accountId ="17a189a9-6db8-4338-86b6-27e15f752a2a";
        String accountId = "3d008aa1-681e-4100-9aaa-9cce15ac2bec";
        String token = TokenUtil.createToken(accountId);
        System.out.println(token);
        return token;
    }

    /**
     * 获取用户信息
     * @return
     */
    @TokenRequired
    @GetMapping("/info")
    public Object getUserInfo(){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        return accountService.getUserInfo(accountId);
    }

    /**
     * 获取头像和unionId
     * @param param
     * @return
     * @throws Exception
     */
    @TokenRequired
    @PostMapping("/getAvatar")
    public Object login(@RequestBody LoginDTO param) throws Exception {
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.weixin.qq.com/sns/jscode2session?appid="+CONST.appId+"&secret="+CONST.appSecret+"&js_code="+param.getCode()+"&grant_type=authorization_code").build();
        Call call = client.newCall(request);
        Response response = call.execute();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(response.body().string(), new TypeReference<Map<String,Object>>(){});
        Map<String, Object> result = WXBizDataCryptUtil.decrypt((String)map.get("session_key"), param.getEncryptedData(), param.getIv());
        String unionId = (String)result.get("unionId");
        String avatarUrl = (String)result.get("avatarUrl");
        Integer gender = (Integer)result.get("sex");
        String city = (String)result.get("city");
        accountMapper.updateAvatar(avatarUrl,unionId,accountId,gender,city);
        return avatarUrl;
    }

    /**
     * 管理员登录
     * @param adminDTO
     * @return
     */
    @PostMapping("/admin")
    public Object adminLogin(@RequestBody AdminDTO adminDTO){
        String accountId = accountService.adminLogin(adminDTO);
        String token = TokenUtil.createToken(accountId);
        System.out.println(token);
        return token;
    }

    @TokenRequired
    @GetMapping("/decoded-phone")
    public Object getPhoneNumber(@RequestParam String encryptedData,
                                 @RequestParam String iv,
                                 @RequestParam String js_code) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        // 微信登陆，获取openid
        String wxApiUrl = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + CONST.appId +
                "&secret=" + CONST.appSecret +
                "&js_code=" + js_code +
                "&grant_type=authorization_code";
        String respronse = restTemplate.getForObject(wxApiUrl, String.class);
        Map res = new Gson().fromJson(respronse, Map.class);
        System.out.println(res);

        String session_key = (String) res.get("session_key");
        // 被加密的数据
        byte[] dataByte = Base64.getDecoder().decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.getDecoder().decode(session_key);
        // 偏移量
        byte[] ivByte = Base64.getDecoder().decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @TokenRequired
    @GetMapping("/accountId")
    public Object getAccountId(){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        return accountId;
    }



}
