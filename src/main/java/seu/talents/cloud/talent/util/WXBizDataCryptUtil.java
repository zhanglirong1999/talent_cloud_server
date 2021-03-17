package seu.talents.cloud.talent.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WXBizDataCryptUtil {

    public static Map<String, Object> decrypt(String sessionKey, String encryptedData, String iv) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Decoder base64Decoder = Base64.getDecoder();
        byte[] encryptedByte = base64Decoder.decode(encryptedData);
        byte[] sessionKeyByte = base64Decoder.decode(sessionKey);
        byte[] ivByte = base64Decoder.decode(iv);
        //以下为AES-128-CBC解密算法
        SecretKeySpec skeySpec = new SecretKeySpec(sessionKeyByte, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] original = cipher.doFinal(encryptedByte);
        return objectMapper.readValue(new String(original), new TypeReference<Map<String,Object>>(){});
    }
}
