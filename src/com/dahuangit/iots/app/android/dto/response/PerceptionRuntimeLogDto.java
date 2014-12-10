package com.dahuangit.iots.app.android.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.dahuangit.iots.app.android.dto.Dto;

/**
 * 感知端运行日志
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 上午10:03:13
 */
public class PerceptionRuntimeLogDto extends Dto {

	private String groupTag = null;

	private List<PerceptionRuntimeLogInfo> logInfos = new ArrayList<PerceptionRuntimeLogInfo>();

	public String getGroupTag() {
		return groupTag;
	}

	public void setGroupTag(String groupTag) {
		this.groupTag = groupTag;
	}

	public List<PerceptionRuntimeLogInfo> getLogInfos() {
		return logInfos;
	}

	public void setLogInfos(List<PerceptionRuntimeLogInfo> logInfos) {
		this.logInfos = logInfos;
	}

}
