package cn.com.bmsoft.base.common.response;

/**
 * 通用返回代码枚举
 */
public enum ResponseCode {

	SUCCEED("200", "成功。"),
	FAILED_300("300", "登录失败，请检查用户名或密码。"),
	FAILED_301("301", "登录失败，存在重复用户。"),
	FAILED_400("400", "没有此用户。"),
	FAILED_401("401", "没有此应用账户。"),
	FAILED_402("402", "没有此会员。"),
	FAILED_403("403", "用户权限不足。"),
	FAILED_404("404", "没有找到相关资源。"),
	FAILED_405("405", "用户已存在。"),
	FAILED_500("500", "内部程序出错。"),
	FAILED_501("501", "远程服务程序出错。"),
	FAILED_600("600", "短信发送失败。"),
	FAILED_601("601", "短信校验失败。");

	private String code;
	private String message;
	private String status;

	ResponseCode(String code, String message) {
		setCode(code);
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		if(code.equals("200")) {
			this.status = "1";
		} else {
			this.status = "0";
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

}
