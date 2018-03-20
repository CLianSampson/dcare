package com.dcare.common.code;

public enum AppErrorEnums{

	/********************** 错误码 *****************************/
	APP_OK(1, "ok"),
	
	APP_ERROR(-1, "系统错误"),
	
	APP_USER_LOCKING(-2,"账户锁定"),
	
	APP_ACCESSSERVER_ERROR(-3, "接入服务器错误"),

	APP_PASSWD_ERROR(-4, "密码错误"),
	
	APP_ERROR_PHONE(-5,"手机号错误"),
	
	APP_NOT_EXIST(-6, "条件不满足，无结果"),

	APP_ARGS_ERRORS(-7, "参数错误"),
	
	APP_LOGIN_ERROR(-8, "登录错误"),
	
	APP_VERIFY_SEND_EXCEEDING(-9,"短信验证码超过次数"),
	
	APP_SQLDB_ERROR(-10, "系统关系型数据储错误"),
	
	APP_CODE_WRONG(-11,"验证码错误"),
	
	APP_CODE_TIMEOUT(-12,"无效验证码"),
	
	APP_TOKEN_INVALID(-13, "无效token"),
	
	APP_NOT_EXIST_USER(-14, "用户不存在"),
	

	APP_ERROR_ADVICE_NULL(-16,"建议为空"),
	
	
	APP_ERROR_FAMILY_RELATION_EXIST(-17,"关系已存在"),
	
	APP_ERROR_FAMILY_MEMBER_NOT_EXIST(-18,"家庭成员不存在"),
	
	APP_ERROR_FAMILY_MEMBER_NOT_IN_USR(-19,"家庭成员不属于该用户"),
	
	APP_ERROR_FAMILY_CANNOT_EDIT(-20,"本人不能编辑"),
	
	APP_ERROR_FAMILY_BEYOND(-21,"超过人数限制"),
	
	APP_ERROR_SHARE_USER_BEYOND(-22,"共享用户超过人数限制"),
	
	APP_ERROR_SHARE_USER_NOT_EXIST(-23,"共享用户不存在"),
	
	
	APP_ERROR_BLOOD_GLUCOSE_FAMILY_USER_CAN_NOT_CHANGE(-24,"血糖记录不能更新家庭用户"),
	
	
	
	APP_ERROR_MAIL_EXIST(-30,"邮箱已存在"),
	
	APP_ERROR_SEND_MAIL_ERROR(-30,"发送邮件错误"),
	
	
	APP_ERROR_PLATFORM_NULL_ERROR(-40,"平台号错误"),
	
	APP_ERROR_DEVICETOKEN_NULL_ERROR(-40,"设备号错误"),
	
	
	
	
	/*
	 * int ALREADY_RECEIVE = 2;//已领取奖励 
	int SUCCESS = 1;//成功
	int PARAMETER_ERROR = 0;//参数不正确
	int USER_NULL_ERROR = -1;//用户不存在
	int ACTIVE_NOT_HAVE = -2;//没有活动
	int CONDITION_NOT_SATISFIED = -3;//条件不满足
	int GIFT_NOT_HAVE = -4;//奖励发完了
	int ATHOR_ERROR = -10;//其它异常
*/	/************************活动模块************************/
	
	/********************** 错误码结束 *****************************/
	
	
	
	EMPTY(0, "起到结束作用");
	
    private int code;
    private String message;

    AppErrorEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    

	public String toString(){
    	return "rtv=" + this.code + "   message=" + this.message;
    }
}
