package com.dahuangit.iots.app.android.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dahuangit.iots.app.android.R;
import com.dahuangit.iots.app.android.common.IniConfig;
import com.dahuangit.iots.app.android.dao.PerceptionDao;
import com.dahuangit.iots.app.android.dto.response.GetPerceptionRuntimeLogListResponse;
import com.dahuangit.iots.app.android.dto.response.PerceptionRuntimeLogDto;
import com.dahuangit.iots.app.android.dto.response.PerceptionRuntimeLogInfo;
import com.dahuangit.iots.app.android.util.HttpUtils;
import com.dahuangit.iots.app.android.util.XmlUtils;

/**
 * 感知端运行日志activity
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月8日 下午8:53:32
 */
public class PerceptionRuntimeLogActivity extends Activity {
	private List<String> listTag = new ArrayList<String>();

	private ListView listView = null;

	private GroupListAdapter adapter = null;

	private List<String> list = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.perception_runtime_log_list);
		listView = (ListView) findViewById(R.id.perceptionRuntimeLogList);

		adapter = new GroupListAdapter(this, list, listTag);
		listView.setAdapter(adapter);

		Intent intent = getIntent();
		Integer perceptionId = intent.getIntExtra("perceptionId", -1);

		// 先从本地数据库中查询出数据
		PerceptionDao perceptionDao = new PerceptionDao(this);
		GetPerceptionRuntimeLogListResponse response = perceptionDao.getPerceptionRuntimeLogList(perceptionId);
		perceptionDao.close();
		setData(response, false);

		// 检查服务器上有没有更新的数据
		new Thread() {
			public void run() {
				try {
					String host = IniConfig.propConfig.getProperty("getPerceptionRuntimeLogList.url");
					Map<String, String> params = new HashMap<String, String>();
					String xml = HttpUtils.getHttpRequestContent(host, params);

					GetPerceptionRuntimeLogListResponse r = XmlUtils.xml2obj(IniConfig.mapping, xml,
							GetPerceptionRuntimeLogListResponse.class);
					setData(r, true);

					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 设置数据
	 * 
	 * @param response
	 * @param appendTop
	 *            是否追加在顶部
	 */
	private void setData(GetPerceptionRuntimeLogListResponse response, boolean appendTop) {
		List<PerceptionRuntimeLogDto> logDtos = response.getLogDtos();
		for (PerceptionRuntimeLogDto dto : logDtos) {
			if (appendTop) {
				list.add(0, dto.getGroupTag());
				listTag.add(0, dto.getGroupTag());
			} else {
				list.add(dto.getGroupTag());
				listTag.add(dto.getGroupTag());
			}
			
			for (PerceptionRuntimeLogInfo info : dto.getLogInfos()) {
				StringBuffer sb = new StringBuffer();
				sb.append(info.getCreateDateTime());
				sb.append(" ");
				sb.append(info.getPerceptionParamName());
				sb.append(":");
				sb.append(info.getPerceptionParamValueDesc());

				if (appendTop) {
					list.add(0, sb.toString());
				} else {
					list.add(sb.toString());
				}
			}
		}
	}

	private static class GroupListAdapter extends ArrayAdapter<String> {

		private List<String> listTag = null;

		public GroupListAdapter(Context context, List<String> objects, List<String> tags) {
			super(context, 0, objects);
			this.listTag = tags;
		}

		@Override
		public boolean isEnabled(int position) {
			if (listTag.contains(getItem(position))) {
				return false;
			}
			return super.isEnabled(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (listTag.contains(getItem(position))) {
				view = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item_tag, null);
			} else {
				view = LayoutInflater.from(getContext()).inflate(R.layout.perception_runtime_log_item, null);
			}

			TextView textView = (TextView) view.findViewById(R.id.group_list_item_text);
			textView.setText(getItem(position));
			return view;
		}
	};

}
