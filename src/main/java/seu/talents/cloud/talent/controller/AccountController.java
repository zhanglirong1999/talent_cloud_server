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
import seu.talents.cloud.talent.model.dao.entity.Favorite;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dao.mapper.FavoriteMapper;
import seu.talents.cloud.talent.model.dto.PageResult;
import seu.talents.cloud.talent.model.dto.post.*;
import seu.talents.cloud.talent.service.AccountService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RedisUtil;
import seu.talents.cloud.talent.util.TokenUtil;
import seu.talents.cloud.talent.util.WXBizDataCryptUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @Autowired
    private FavoriteMapper favoriteMapper;


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
        String accountId = "c9250405-85cb-4208-86dc-be67bdbeeeb2";
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

  //  @TokenRequired
    @PostMapping("/decoded-phone")
    public Object getPhoneNumber(@RequestBody PhoneDTO phoneDTO) throws Exception {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("https://api.weixin.qq.com/sns/jscode2session?appid="+CONST.appId+"&secret="+CONST.appSecret+"&js_code="+phoneDTO.getJs_code()+"&grant_type=authorization_code").build();
//        Call call = client.newCall(request);
//        Response response = call.execute();
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> map = objectMapper.readValue(response.body().string(), new TypeReference<Map<String,Object>>(){});
//        Map<String, Object> result = WXBizDataCryptUtil.decrypt((String)map.get("session_key"), phoneDTO.getEncryptedData(), phoneDTO.getIv());
//        System.out.println(result);
//        String phone = (String)result.get("purePhoneNumber");
//        return phone;

        // 微信登陆，获取openid
        String wxApiUrl = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + CONST.appId +
                "&secret=" + CONST.appSecret +
                "&js_code=" + phoneDTO.getJs_code() +
                "&grant_type=authorization_code";
        String respronse = restTemplate.getForObject(wxApiUrl, String.class);
        Map res = new Gson().fromJson(respronse, Map.class);
        System.out.println(res);

        String session_key = (String) res.get("session_key");
//        // 被加密的数据
//        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
//        byte[] dataByte = base64.decode(phoneDTO.getEncryptedData().getBytes());
//        byte[] keyByte = base64.decode(session_key.getBytes());
//        byte[] ivByte = base64.decode(phoneDTO.getIv().getBytes());


//        String encryptedData = URLDecoder.decode(phoneDTO.getEncryptedData(),"UTF-8");
//        String sessionKey = URLDecoder.decode(session_key,"UTF-8");
//        String iv = URLDecoder.decode(phoneDTO.getIv(),"UTF-8");
        byte[] dataByte = Base64.decode(phoneDTO.getEncryptedData());
         //加密秘钥
        byte[] keyByte = Base64.decode(session_key);
         //偏移量
        byte[] ivByte = Base64.decode(phoneDTO.getIv());
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.out.println(".............kk");
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

    /**
     * 修改用户信息
     * @param modifyDTO
     * @return
     */
    @TokenRequired
    @PostMapping("/modify")
    public Object modifyUser(@RequestBody ModifyDTO modifyDTO){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        accountService.modifyUser(modifyDTO,accountId);
        return "success";
    }

    /**
     * 新增工作经历
     * @param addJob
     * @return
     */
    @TokenRequired
    @PostMapping("/job")
    public Object addJob(@RequestBody AddJob addJob){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        accountService.addJob(addJob,accountId);
        return "success";
    }

    @TokenRequired
    @PostMapping("/job/modify")
    public Object modifyJob(@RequestBody ModifyJobDTO modifyJobDTO){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        accountService.modifyJob(modifyJobDTO,accountId);
        return "success";
    }

    @TokenRequired
    @PostMapping("/job/delete")
    public Object deleteJob(@RequestBody JobDeleteDTO jobDeleteDTO){
        accountService.deleteJob(jobDeleteDTO.getJobId());
        return "success";
    }


}
