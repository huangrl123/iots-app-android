package com.dahuangit.iots.app.android.dto;

/**
 * 用户信息
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月11日 下午4:53:10
 */
public class UserInfoDto extends Dto {
	/** 用户主键 */
	private Integer userId = null;

	/** 登录用户名 */
	private String userName = null;

	/** 登录密码 */
	private String password = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
