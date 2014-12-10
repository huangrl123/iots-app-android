package com.dahuangit.iots.app.android.dto.response;


public class RemoteQuery6j6PerceptionResponse extends Response {
	private Integer perceptionId = null;

	private String rotateStatus = null;

	private String switchStatus = null;

	/** 红外状态 */
	private String infraredStatus = null;

	/** 振动状态 */
	private String vibrateStatus = null;

	/** 压力状态 */
	private String pressureStatus = null;

	/** 接近状态 */
	private String approachStatus = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getRotateStatus() {
		return rotateStatus;
	}

	public void setRotateStatus(String rotateStatus) {
		this.rotateStatus = rotateStatus;
	}

	public String getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(String switchStatus) {
		this.switchStatus = switchStatus;
	}

	public String getInfraredStatus() {
		return infraredStatus;
	}

	public void setInfraredStatus(String infraredStatus) {
		this.infraredStatus = infraredStatus;
	}

	public String getVibrateStatus() {
		return vibrateStatus;
	}

	public void setVibrateStatus(String vibrateStatus) {
		this.vibrateStatus = vibrateStatus;
	}

	public String getPressureStatus() {
		return pressureStatus;
	}

	public void setPressureStatus(String pressureStatus) {
		this.pressureStatus = pressureStatus;
	}

	public String getApproachStatus() {
		return approachStatus;
	}

	public void setApproachStatus(String approachStatus) {
		this.approachStatus = approachStatus;
	}

}
