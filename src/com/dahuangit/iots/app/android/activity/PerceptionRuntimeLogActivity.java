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
import android.os.Handler;
import android.os.Message;
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
import com.dahuangit.iots.app.android.util.NetworkOnMainThreadExceptionKit;
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

	final PerceptionDao perceptionDao = new PerceptionDao(this);

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case 1:
				GetPerceptionRuntimeLogListResponse r = (GetPerceptionRuntimeLogListResponse) msg.getData().get(
						"response");
				setData(r, true);
				adapter.notifyDataSetChanged();
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		NetworkOnMainThreadExceptionKit.kit();

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.perception_runtime_log_list);
		listView = (ListView) findViewById(R.id.perceptionRuntimeLogList);

		adapter = new GroupListAdapter(this, list, listTag);
		listView.setAdapter(adapter);

		Intent intent = getIntent();
		String perceptionId = intent.getStringExtra("perceptionId");

		// 先从本地数据库中查询出数据
		final GetPerceptionRuntimeLogListResponse response = perceptionDao.getPerceptionRuntimeLogList(Integer
				.valueOf(perceptionId));
		setData(response, false);

		// 检查服务器上有没有更新的数据
		new Thread() {
			public void run() {
				try {
					StringBuffer host = new StringBuffer();
					host.append(IniConfig.propConfig.getProperty("server.host"));
					host.append(IniConfig.propConfig.getProperty("getPerceptionRuntimeLogList.url"));
					Map<String, String> params = new HashMap<String, String>();
					params.put("perceptionId", getIntent().getStringExtra("perceptionId"));

					Integer localMaxPerceptionRuntimeLogId = 0;
					if (response.getLogDtos().size() > 0 && response.getLogDtos().get(0).getLogInfos().size() > 0) {
						localMaxPerceptionRuntimeLogId = response.getLogDtos().get(0).getLogInfos().get(0)
								.getPerceptionRuntimeLogId();
					}
					params.put("localMaxPerceptionRuntimeLogId", localMaxPerceptionRuntimeLogId.toString());

					String xml = HttpUtils.getHttpRequestContent(host.toString(), params);

					GetPerceptionRuntimeLogListResponse r = XmlUtils.xml2obj(IniConfig.mapping, xml,
							GetPerceptionRuntimeLogListResponse.class);

					for (PerceptionRuntimeLogDto dto : r.getLogDtos()) {
						for (PerceptionRuntimeLogInfo info : dto.getLogInfos()) {
							perceptionDao.addPerceptionRuntimeLog(info);
						}
					}

					Message msg = new Message();
					msg.what = 1;
					Bundle data = new Bundle();
					data.putSerializable("response", r);
					msg.setData(data);
					handler.sendMessage(msg);
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
//			if (appendTop) {
//				if (list.size() > 0) {
//					list.add(list.size() - 1, dto.getGroupTag());
//					listTag.add(listTag.size() - 1, dto.getGroupTag());
//				}
//			} else {
				list.add(dto.getGroupTag());
				listTag.add(dto.getGroupTag());
//			}

			for (PerceptionRuntimeLogInfo info : dto.getLogInfos()) {
				StringBuffer sb = new StringBuffer();
				sb.append(info.getCreateDateTime());
				sb.append(" ");
				sb.append(info.getPerceptionParamName());
				sb.append(":");
				sb.append(info.getPerceptionParamValueDesc());
//
//				if (appendTop) {
//					list.add(0, sb.toString());
//				} else {
					list.add(sb.toString());
//				}
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
