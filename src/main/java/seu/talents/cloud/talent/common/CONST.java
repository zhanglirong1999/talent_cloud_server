package seu.talents.cloud.talent.common;

import seu.talents.cloud.talent.common.config.AccessTokenConfig;

public class CONST {
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = -1;
    public static final String SUCCESS_MESSAGE_DEFAULT = "SUCCESS";
    public static final String FAIL_MESSAGE_DEFAULT = "FAIL";

    /**
     * 同意1，拒绝0
     */
//    public static final int FRIEND_ACTION_Y = 1;
//    public static final int FRIEND_ACTION_N = 0;

    // wx 参数
    public static final String appId = "wxb6c2adef86425b32";
    public static final String appSecret = "e582762fae203188f5258484504473dd";

    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //  "a5ba3736dbf6a66715b43fc05ab3f8db"
    // ACL Key
    public static final String ACL_ACCOUNTID = "accountId";
    public static final String SCHOOL_ID ="schoolId";

    /**
     * 同意1，拒绝0
     */
    public static final int FRIEND_ACTION_Y = 1;
    public static final int FRIEND_ACTION_N = 0;

    /**
     * 订阅消息提示类型
     */
    public static final String  ACTIVITY_MESSAGE = "活动消息";
    public static final String  SYSTEM_MESSAGE = "系统消息";
    public static final String  NEW_FRIEND_MESSAGE = "有新朋友请求交换名片";
    public static final String  REJECT_MESSAGE = "拒绝添加朋友";
    public static final String  AGREE_MESSAGE = "同意添加朋友";

    public static String getAccessToken() {
        return AccessTokenConfig.getAccessToken();
    }

    public static final String fail() {
        return "<xml>\n" +
                "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                "  <return_msg><![CDATA[]]></return_msg>\n" +
                "</xml>";
    }

    public static final String success() {
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    public static final int PAGE_SIZE = 5;
    public static final String avatar =
            "https://seu-talent-cloud-1304015296.cos.ap-nanjing.myqcloud.com/heaImage.png";

}
