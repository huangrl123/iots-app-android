package com.dahuangit.iots.app.android.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.dahuangit.iots.app.android.dto.response.GetPerceptionListResponse;
import com.dahuangit.iots.app.android.dto.response.GetPerceptionRuntimeLogListResponse;
import com.dahuangit.iots.app.android.dto.response.PerceptionInfo;
import com.dahuangit.iots.app.android.dto.response.PerceptionRuntimeLogDto;
import com.dahuangit.iots.app.android.dto.response.PerceptionRuntimeLogInfo;
import com.dahuangit.iots.app.android.util.DateUtils;

/**
 * 感知端dao
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月10日 下午5:17:22
 */
public class PerceptionDao extends BaseDao {

	public PerceptionDao(Context context) {
		super(context);
	}

	/**
	 * 添加感知端
	 * 
	 * @param perceptionInfo
	 */
	public void addPerception(PerceptionInfo perceptionInfo) {
		String table = "t_perception";
		ContentValues values = new ContentValues();
		values.put("perception_type_id", perceptionInfo.getPerceptionTypeId());
		values.put("perception_type_name", perceptionInfo.getPerceptionTypeName());
		values.put("perception_id", perceptionInfo.getPerceptionId());
		values.put("perception_name", perceptionInfo.getPerceptionName());
		values.put("perception_addr", perceptionInfo.getPerceptionAddr());
		values.put("install_site", perceptionInfo.getInstallSite());
		values.put("is_on_line", perceptionInfo.getIsOnline());
		values.put("last_comm_time", perceptionInfo.getLastCommTime());

		this.insert(table, values);
	}

	/**
	 * 添加感知端日志
	 * 
	 * @param perceptionRuntimeLogInfo
	 */
	public void addPerceptionRuntimeLog(PerceptionRuntimeLogInfo perceptionRuntimeLogInfo) {
		
		String table = "t_perception_runtime_log";
		ContentValues values = new ContentValues();
		values.put("perception_runtime_log_id", perceptionRuntimeLogInfo.getPerceptionRuntimeLogId());
		values.put("perception_id", perceptionRuntimeLogInfo.getPerceptionId());
		values.put("perception_name", perceptionRuntimeLogInfo.getPerceptionName());
		values.put("perception_addr", perceptionRuntimeLogInfo.getPerceptionAddr());
		values.put("perception_param_id", perceptionRuntimeLogInfo.getPerceptionParamId());
		values.put("perception_param_name", perceptionRuntimeLogInfo.getPerceptionParamName());
		values.put("perception_type_id", perceptionRuntimeLogInfo.getPerceptionTypeId());
		values.put("perception_type_name", perceptionRuntimeLogInfo.getPerceptionTypeName());
		values.put("perception_param_value_info_id", perceptionRuntimeLogInfo.getPerceptionParamValueInfoId());
		values.put("perception_param_value_desc", perceptionRuntimeLogInfo.getPerceptionParamValueDesc());
		values.put("create_date_time", perceptionRuntimeLogInfo.getCreateDateTime());
		values.put("remark", perceptionRuntimeLogInfo.getRemark());

		this.insert(table, values);
	}

	/**
	 * 知获取感端列表
	 * 
	 * @param userId
	 * @return
	 */
	public GetPerceptionListResponse getPerceptionList(Integer userId) {
		GetPerceptionListResponse response = new GetPerceptionListResponse();
		String sql = "select * from t_perception t order by t.perception_id desc";
		Cursor c = this.query(sql);

		List<PerceptionInfo> perceptionDtos = new ArrayList<PerceptionInfo>();
		while (c.moveToNext()) {
			PerceptionInfo info = new PerceptionInfo();

			int index = c.getColumnIndex("perception_id");
			info.setPerceptionId((c.getInt(index)));

			index = c.getColumnIndex("perception_name");
			info.setPerceptionName(c.getString(index));

			index = c.getColumnIndex("perception_type_id");
			info.setPerceptionTypeId(c.getInt(index));

			index = c.getColumnIndex("perception_type_name");
			info.setPerceptionTypeName(c.getString(index));

			index = c.getColumnIndex("install_site");
			info.setInstallSite(c.getString(index));

			index = c.getColumnIndex("is_on_line");
			info.setIsOnline(c.getInt(index) > 0);

			index = c.getColumnIndex("last_comm_time");
			info.setLastCommTime(c.getString(index));

			index = c.getColumnIndex("perception_addr");
			info.setPerceptionAddr(c.getString(index));
			
			perceptionDtos.add(info);
		}

		response.setPerceptionDtos(perceptionDtos);
		
		c.close();
		return response;
	}

	/**
	 * 获取某个感知端下的所有运行日志
	 * 
	 * @param perceptionId
	 * @return
	 */
	public GetPerceptionRuntimeLogListResponse getPerceptionRuntimeLogList(Integer perceptionId) {
		GetPerceptionRuntimeLogListResponse response = new GetPerceptionRuntimeLogListResponse();

		String sql = "select * from t_perception_runtime_log t order by t.create_date_time desc";
		Cursor c = this.query(sql);

		Map<String, List<PerceptionRuntimeLogInfo>> map = new HashMap<String, List<PerceptionRuntimeLogInfo>>();
		while (c.moveToNext()) {
			PerceptionRuntimeLogInfo info = new PerceptionRuntimeLogInfo();

			int columnIndex = c.getColumnIndex("perception_runtime_log_id");
			Integer perceptionRuntimeLogId = c.getInt(columnIndex);
			info.setPerceptionRuntimeLogId(perceptionRuntimeLogId);

			info.setPerceptionId(perceptionId);

			columnIndex = c.getColumnIndex("perception_name");
			String perceptionName = c.getString(columnIndex);
			info.setPerceptionName(perceptionName);

			columnIndex = c.getColumnIndex("perception_addr");
			String perceptionAddr = c.getString(columnIndex);
			info.setPerceptionAddr(perceptionAddr);

			columnIndex = c.getColumnIndex("perception_param_id");
			Integer perceptionParamId = c.getInt(columnIndex);
			info.setPerceptionParamId(perceptionParamId);

			columnIndex = c.getColumnIndex("perception_param_name");
			String perceptionParamName = c.getString(columnIndex);
			info.setPerceptionParamName(perceptionParamName);

			columnIndex = c.getColumnIndex("perception_type_id");
			Integer perceptionTypeId = c.getInt(columnIndex);
			info.setPerceptionTypeId(perceptionTypeId);

			columnIndex = c.getColumnIndex("perception_type_name");
			String perceptionTypeName = c.getString(columnIndex);
			info.setPerceptionTypeName(perceptionTypeName);

			columnIndex = c.getColumnIndex("perception_param_value_info_id");
			Integer perceptionParamValueInfoId = c.getInt(columnIndex);
			info.setPerceptionParamValueInfoId(perceptionParamValueInfoId);

			columnIndex = c.getColumnIndex("perception_param_value_desc");
			String perceptionParamValueDesc = c.getString(columnIndex);
			info.setPerceptionParamValueDesc(perceptionParamValueDesc);

			columnIndex = c.getColumnIndex("create_date_time");
			String createDateTime = c.getString(columnIndex);
			info.setCreateDateTime(createDateTime);

			columnIndex = c.getColumnIndex("remark");
			String remark = c.getString(columnIndex);
			info.setRemark(remark);

			Date d = DateUtils.parse(info.getCreateDateTime());
			String key = DateUtils.format(d, "yy年MM月dd日");
			List<PerceptionRuntimeLogInfo> list = map.get(key);
			if (null != list) {
				list.add(info);
			} else {
				list = new ArrayList<PerceptionRuntimeLogInfo>();
				list.add(info);
			}
		}

		List<PerceptionRuntimeLogDto> logDtos = new ArrayList<PerceptionRuntimeLogDto>();
		for (Map.Entry<String, List<PerceptionRuntimeLogInfo>> entry : map.entrySet()) {
			PerceptionRuntimeLogDto dto = new PerceptionRuntimeLogDto();
			dto.setGroupTag(entry.getKey());
			dto.setLogInfos(entry.getValue());
			logDtos.add(dto);
		}

		response.setLogDtos(logDtos);
       
		c.close();
		
		return response;
	}
}
