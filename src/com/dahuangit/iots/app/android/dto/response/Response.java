package com.dahuangit.iots.app.android.dto.response;

import com.dahuangit.iots.app.android.dto.Dto;

public class Response extends Dto {
	private Boolean success = true;

	private String msg = null;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
