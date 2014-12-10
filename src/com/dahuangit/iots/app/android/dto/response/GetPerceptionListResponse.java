package com.dahuangit.iots.app.android.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取感知端列表响应类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 上午9:46:01
 */
public class GetPerceptionListResponse extends Response {
	private List<PerceptionInfo> perceptionDtos = new ArrayList<PerceptionInfo>();

	public List<PerceptionInfo> getPerceptionDtos() {
		return perceptionDtos;
	}

	public void setPerceptionDtos(List<PerceptionInfo> perceptionDtos) {
		this.perceptionDtos = perceptionDtos;
	}

}
