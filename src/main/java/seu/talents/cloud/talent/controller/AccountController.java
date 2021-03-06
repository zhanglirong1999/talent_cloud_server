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
import seu.talents.cloud.talent.model.dao.entity.Graduation;
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
            // ??????appid???secret????????????.??????????????????access_token
            String wxApiUrl = "https://api.weixin.qq.com/cgi-bin/token?" +
                    "openid=" + openid +
                    "&secret=" + CONST.appSecret +
                    "&grant_type=client_credential";
            String respronse = restTemplate.getForObject(wxApiUrl, String.class);
            System.out.println("?????????");
        }
        return access_token;
    }

    /**
     * ??????
     * @param js_code
     * @return
     */
    @PostMapping("/login")
    public Object getLogin(
            @RequestParam String js_code
    ){
        // ?????????????????????openid
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
                //???????????????
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
     * ??????
     * @param register
     * @return
     */
    @TokenRequired
    @PostMapping("/alumni")
    public Object test(
            @RequestBody Register register
    ){
        //??????accountId
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        accountService.registerUser(register,accountId);
        return "success";
    }

    @PostMapping("/getToken")
    public Object getToken(){
//        String accountId= UUID.randomUUID().toString();
//        String accountId ="17a189a9-6db8-4338-86b6-27e15f752a2a";
        String accountId = "30ad08ff-603c-4db8-8c0b-128b82aa099e";
        String token = TokenUtil.createToken(accountId);
        System.out.println(token);
        return token;
    }

    /**
     * ??????????????????
     * @return
     */
    @TokenRequired
    @GetMapping("/info")
    public Object getUserInfo(){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        return accountService.getUserInfo(accountId);
    }

    /**
     * ???????????????unionId
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
     * ???????????????
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

        // ?????????????????????openid
        String wxApiUrl = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + CONST.appId +
                "&secret=" + CONST.appSecret +
                "&js_code=" + phoneDTO.getJs_code() +
                "&grant_type=authorization_code";
        String respronse = restTemplate.getForObject(wxApiUrl, String.class);
        Map res = new Gson().fromJson(respronse, Map.class);
        System.out.println(res);

        String session_key = (String) res.get("session_key");
//        // ??????????????????
//        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
//        byte[] dataByte = base64.decode(phoneDTO.getEncryptedData().getBytes());
//        byte[] keyByte = base64.decode(session_key.getBytes());
//        byte[] ivByte = base64.decode(phoneDTO.getIv().getBytes());


//        String encryptedData = URLDecoder.decode(phoneDTO.getEncryptedData(),"UTF-8");
//        String sessionKey = URLDecoder.decode(session_key,"UTF-8");
//        String iv = URLDecoder.decode(phoneDTO.getIv(),"UTF-8");
        byte[] dataByte = Base64.decode(phoneDTO.getEncryptedData());
         //????????????
        byte[] keyByte = Base64.decode(session_key);
         //?????????
        byte[] ivByte = Base64.decode(phoneDTO.getIv());
        try {
            // ??????????????????16?????????????????????.  ??????if ?????????????????????
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.out.println(".............kk");
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // ?????????
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// ?????????
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
     * ??????????????????
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
     * ??????????????????
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


    @TokenRequired
    @PostMapping("/graduation")
    public Object graduationLogin(@RequestBody Graduation graduation){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        return "1";
    }





}
