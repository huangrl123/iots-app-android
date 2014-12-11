package com.dahuangit.iots.app.android.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 感知端运行日志列表响应
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 上午10:35:29
 */
public class GetPerceptionRuntimeLogListResponse extends Response {
	
	private List<PerceptionRuntimeLogDto> logDtos = new ArrayList<PerceptionRuntimeLogDto>();

	public List<PerceptionRuntimeLogDto> getLogDtos() {
		return logDtos;
	}

	public void setLogDtos(List<PerceptionRuntimeLogDto> logDtos) {
		this.logDtos = logDtos;
	}
}
