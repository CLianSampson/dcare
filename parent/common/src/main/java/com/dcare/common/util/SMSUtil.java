package com.dcare.common.util;


import com.cloopen.rest.sdk.CCPRestSDK;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SMSUtil {
    private final static Logger logger = LoggerFactory.getLogger(SMSUtil.class);

    // 服务器地址和端口
    public final static String CLOOPEN_URL = "app.cloopen.com"; // sandboxapp
    // 服务器端口
    public final static String CLOOPEN_PORT = "8883";
    // 容联帐号
    public final static String CLOOPEN_ACCOUNT_SID = "8a216da85c334124015c342f0aac01d0";
    // 主帐号令牌
    public final static String CLOOPEN_AUTH_TOKEN = "099532826a7c484491ccca80f0c4d254";
    // 应用ID
    public final static String CLOOPEN_APP_ID = "8a216da85c334124015c342f0c4801d6";

    // 模块ID
    public final static String TEMPLATE_CODE = "178559"; // 验证码
    
    public final static String TEMPLATE_RECEIVE = "43030"; // 接单
    public final static String TEMPLATE_CANCEL = "43034"; // 取消订单
    public final static String TEMPLATE_ARRIVE = "43039"; // 接车
    public final static String TEMPLATE_PASSENGER_UNPAY = "72364";//乘客未支付
    public final static String TEMPLATE_DRIVER_UNPAY = "72365";//司机未支付
    public final static String DRIVER_AUDIT_PASS = "145683";//司机审核通过
    public final static String DRIVER_AUDIT_FAILED = "145685";//司机审核失败
    
    public final static String TICKET_BOOKING_SUCCESS = "154769";//订票成功短信
    
    public final static String TICKET_BOOKING_SUCCESS_SMS_TEMPLATE ="【西湖中南行】您成功预订 %s 发车的车票 %s 张，出示验票码 %s 或订单二维码验票乘车.";
    
    public final static String TEMPLATE_SHARE_USER = "191306";//共享用户

    // 验证码有效时间 单位：分钟
    public final static String CLOOPEN_VALID_TIME = "60分钟";

    // 语音验证 显示号码
    public final static String OFFICIAL_PHONE = "0755-86544040";

    public static Map<String, Object> sendSMS(String phone, String[] datas, String template) {
        // 初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        // ******************************注释*********************************************
        // *初始化服务器地址和端口 *
        // *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        // *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
        // *******************************************************************************
        restAPI.init(CLOOPEN_URL, CLOOPEN_PORT);

        // ******************************注释*********************************************
        // *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
        // *ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        // *参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。 *
        // *******************************************************************************
        restAPI.setAccount(CLOOPEN_ACCOUNT_SID, CLOOPEN_AUTH_TOKEN);

        // ******************************注释*********************************************
        // *初始化应用ID *
        // *测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID *
        // *应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        // *******************************************************************************
        restAPI.setAppId(CLOOPEN_APP_ID);

        // ******************************注释****************************************************************
        // *调用发送模板短信的接口发送短信 *
        // *参数顺序说明： *
        // *第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号 *
        // *第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。 *
        // *系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        // *第三个参数是要替换的内容数组。 *
        // **************************************************************************************************

        // **************************************举例说明***********************************************************************
        // *假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为
        // *
        // *result = restAPI.sendTempla-teSMS("13800000000","1" ,new
        // String[]{"6532","5"}); *
        // *则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入 *
        // *********************************************************************************************************************
        // if (addSMS(phone, code)) {
        return restAPI.sendTemplateSMS(phone, template, datas);

    }

    public static Map<String, Object> sendVoice(String phone, String code) {
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init(CLOOPEN_URL, CLOOPEN_PORT);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(CLOOPEN_ACCOUNT_SID, CLOOPEN_AUTH_TOKEN);// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(CLOOPEN_APP_ID);// 初始化应用ID

        // 语音内容、手机号码、来电显示号码、播报次数、回调地址、语言、自定义数据
        return restAPI.voiceVerify(code, phone, OFFICIAL_PHONE, "3", "", "zh",
                "");

    }

    public static boolean sendSMS(String phone, String template, String[] datas) {
        // 初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        // ******************************注释*********************************************
        // *初始化服务器地址和端口 *
        // *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        // *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
        // *******************************************************************************
        restAPI.init(CLOOPEN_URL, CLOOPEN_PORT);

        // ******************************注释*********************************************
        // *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
        // *ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        // *参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。 *
        // *******************************************************************************
        restAPI.setAccount(CLOOPEN_ACCOUNT_SID, CLOOPEN_AUTH_TOKEN);

        // ******************************注释*********************************************
        // *初始化应用ID *
        // *测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID *
        // *应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        // *******************************************************************************
        restAPI.setAppId(CLOOPEN_APP_ID);

        // ******************************注释****************************************************************
        // *调用发送模板短信的接口发送短信 *
        // *参数顺序说明： *
        // *第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号 *
        // *第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。 *
        // *系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        // *第三个参数是要替换的内容数组。 *
        // **************************************************************************************************

        // **************************************举例说明***********************************************************************
        // *假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为
        // *
        // *result = restAPI.sendTemplateSMS("13800000000","1" ,new
        // String[]{"6532","5"}); *
        // *则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入 *
        // *********************************************************************************************************************
        // if (addSMS(phone, code)) {
        Map<String, Object> result = restAPI.sendTemplateSMS(phone, template,
                datas);

        // logger.info("SDKTestGetSubAccounts result=" + result);

        if ("000000".equals(result.get("statusCode"))) { // 正常返回输出data包体信息（map）
            return true;
        } else {
            // 异常返回输出错误码和错误信息
            logger.error("错误码=" + result.get("statusCode") + " phone= " + phone
                    + " msg= " + datas);
        }
        return false;
    }

}
