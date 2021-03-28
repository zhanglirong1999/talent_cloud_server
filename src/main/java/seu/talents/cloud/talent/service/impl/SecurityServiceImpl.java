package seu.talents.cloud.talent.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.common.config.AccessTokenConfig;
import seu.talents.cloud.talent.model.dto.returnDTO.JobDTO;
import seu.talents.cloud.talent.service.SecurityService;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    AccessTokenConfig accessTokenConfig;

    @Override
    public boolean checkoutJobDTOSecurity(JobDTO obDTO) {
        return  checkoutText(obDTO.getCompany()) &&
                checkoutText(obDTO.getPosition());
    }

    /**
     * 使用微信文本安全内容检测接口检测传入的String字符串是否合法
     * @param text
     * @return
     */
    private boolean checkoutText(String text){
        if(StringUtils.isEmpty(text)) {
            return true;
        }
        String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + CONST.getAccessToken();
        RestTemplate rest = new RestTemplate();
        Map<String, String> param = new HashMap<>();
        param.put("content", text);
        String response = rest.postForObject(url, param, String.class);
        String code = JSONObject.parseObject(response).getString("errcode");
        if (code.equals("40001")){
            accessTokenConfig.regainAccessToken();
            url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + CONST.getAccessToken();
            response = rest.postForObject(url, param, String.class);
            code = JSONObject.parseObject(response).getString("errcode");
        }
        return !code.equals("87014");
    }
}
