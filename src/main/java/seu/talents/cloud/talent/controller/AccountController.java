package seu.talents.cloud.talent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.Data;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.common.annotation.TokenRequired;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dto.post.LoginDTO;
import seu.talents.cloud.talent.model.dto.post.Register;
import seu.talents.cloud.talent.service.AccountService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RedisUtil;
import seu.talents.cloud.talent.util.TokenUtil;
import seu.talents.cloud.talent.util.WXBizDataCryptUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.servlet.http.HttpServletRequest;
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
                register.setIsRegister(true);
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
        accountMapper.updateAvatar(avatarUrl,unionId,accountId);
        return avatarUrl;
    }



}
