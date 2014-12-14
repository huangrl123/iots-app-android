package com.dahuangit.iots.app.android.dto.request;

import android.content.Context;

public class UserLogoutRequest extends Request {
	public UserLogoutRequest(Context context) {
		super(context);
	}

	private Integer userId = null;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
