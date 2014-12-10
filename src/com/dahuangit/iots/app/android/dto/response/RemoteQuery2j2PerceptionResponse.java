package com.dahuangit.iots.app.android.dto.response;


public class RemoteQuery2j2PerceptionResponse extends Response {
	private Integer perceptionId = null;

	/** 电机1旋转状态 */
	private String machine1RotateStatus = null;
	/** 电机2旋转状态 */
	private String machine2RotateStatus = null;
	/** 电机1开关状态 */
	private String machine1SwitchStatus = null;
	/** 电机2开关状态 */
	private String machine2SwitchStatus = null;
	/** i2c状态 */
	private String i2cStatus = null;
	/** 红外状态 */
	private String infraredStatus = null;
	/** 按键状态 */
	private String pressKeyStatus = null;

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getMachine1RotateStatus() {
		return machine1RotateStatus;
	}

	public void setMachine1RotateStatus(String machine1RotateStatus) {
		this.machine1RotateStatus = machine1RotateStatus;
	}

	public String getMachine2RotateStatus() {
		return machine2RotateStatus;
	}

	public void setMachine2RotateStatus(String machine2RotateStatus) {
		this.machine2RotateStatus = machine2RotateStatus;
	}

	public String getMachine1SwitchStatus() {
		return machine1SwitchStatus;
	}

	public void setMachine1SwitchStatus(String machine1SwitchStatus) {
		this.machine1SwitchStatus = machine1SwitchStatus;
	}

	public String getMachine2SwitchStatus() {
		return machine2SwitchStatus;
	}

	public void setMachine2SwitchStatus(String machine2SwitchStatus) {
		this.machine2SwitchStatus = machine2SwitchStatus;
	}

	public String getI2cStatus() {
		return i2cStatus;
	}

	public void setI2cStatus(String i2cStatus) {
		this.i2cStatus = i2cStatus;
	}

	public String getInfraredStatus() {
		return infraredStatus;
	}

	public void setInfraredStatus(String infraredStatus) {
		this.infraredStatus = infraredStatus;
	}

	public String getPressKeyStatus() {
		return pressKeyStatus;
	}

	public void setPressKeyStatus(String pressKeyStatus) {
		this.pressKeyStatus = pressKeyStatus;
	}

}
