package com.dahuangit.iots.app.android.dto.response;

/**
 * 感知端dto
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 上午9:47:29
 */
public class PerceptionInfo {
	/** 类型id */
	private Integer perceptionTypeId = null;

	/** 感知端地址 */
	private String perceptionAddr = null;

	/** 感知端类型名称 */
	private String perceptionTypeName = null;

	/** 感知端id */
	private Integer perceptionId = null;

	/** 感知端名称 */
	private String perceptionName = null;

	/** 感知端安装位置 */
	private String installSite = null;

	/** 是否在线 */
	private Boolean isOnline = false;

	/** 最后通信时间 */
	private String lastCommTime = null;

	public Integer getPerceptionTypeId() {
		return perceptionTypeId;
	}

	public void setPerceptionTypeId(Integer perceptionTypeId) {
		this.perceptionTypeId = perceptionTypeId;
	}

	public String getPerceptionTypeName() {
		return perceptionTypeName;
	}

	public void setPerceptionTypeName(String perceptionTypeName) {
		this.perceptionTypeName = perceptionTypeName;
	}

	public Integer getPerceptionId() {
		return perceptionId;
	}

	public void setPerceptionId(Integer perceptionId) {
		this.perceptionId = perceptionId;
	}

	public String getPerceptionName() {
		return perceptionName;
	}

	public void setPerceptionName(String perceptionName) {
		this.perceptionName = perceptionName;
	}

	public String getInstallSite() {
		return installSite;
	}

	public void setInstallSite(String installSite) {
		this.installSite = installSite;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getLastCommTime() {
		return lastCommTime;
	}

	public void setLastCommTime(String lastCommTime) {
		this.lastCommTime = lastCommTime;
	}

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

}
