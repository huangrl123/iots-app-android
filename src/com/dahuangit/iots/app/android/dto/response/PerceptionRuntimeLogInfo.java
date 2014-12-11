package com.dahuangit.iots.app.android.dto.response;

/**
 * 感知端运行日志
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 上午10:03:13
 */
public class PerceptionRuntimeLogInfo {

	private Integer perceptionRuntimeLogId = null;

	private Integer perceptionId = null;

	private String perceptionName = null;

	private String perceptionAddr = null;

	private Integer perceptionParamId = null;

	private String perceptionParamName = null;

	private Integer perceptionTypeId = null;

	private String perceptionTypeName = null;

	private Integer perceptionParamValueInfoId = null;

	private String perceptionParamValueDesc = null;

	private String createDateTime = null;

	private String remark = null;

	public Integer getPerceptionRuntimeLogId() {
		return perceptionRuntimeLogId;
	}

	public void setPerceptionRuntimeLogId(Integer perceptionRuntimeLogId) {
		this.perceptionRuntimeLogId = perceptionRuntimeLogId;
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

	public String getPerceptionAddr() {
		return perceptionAddr;
	}

	public void setPerceptionAddr(String perceptionAddr) {
		this.perceptionAddr = perceptionAddr;
	}

	public Integer getPerceptionParamId() {
		return perceptionParamId;
	}

	public void setPerceptionParamId(Integer perceptionParamId) {
		this.perceptionParamId = perceptionParamId;
	}

	public String getPerceptionParamName() {
		return perceptionParamName;
	}

	public void setPerceptionParamName(String perceptionParamName) {
		this.perceptionParamName = perceptionParamName;
	}

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

	public Integer getPerceptionParamValueInfoId() {
		return perceptionParamValueInfoId;
	}

	public void setPerceptionParamValueInfoId(Integer perceptionParamValueInfoId) {
		this.perceptionParamValueInfoId = perceptionParamValueInfoId;
	}

	public String getPerceptionParamValueDesc() {
		return perceptionParamValueDesc;
	}

	public void setPerceptionParamValueDesc(String perceptionParamValueDesc) {
		this.perceptionParamValueDesc = perceptionParamValueDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

}
