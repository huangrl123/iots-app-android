package com.dahuangit.iots.app.android.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.dahuangit.iots.app.android.dto.response.GetPerceptionRuntimeLogListResponse;
import com.dahuangit.iots.app.android.dto.response.PerceptionRuntimeLogDto;
import com.dahuangit.iots.app.android.dto.response.PerceptionRuntimeLogInfo;
import com.dahuangit.iots.app.android.util.DialogUtils;
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

	private ProgressDialog progressDialog = null;

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
				progressDialog.hide();
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

		progressDialog = DialogUtils.createProgressDialog(this);

		progressDialog.show();

		request();
	}

	private void request() {
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

					params.put("localMaxPerceptionRuntimeLogId", localMaxPerceptionRuntimeLogId.toString());

					String xml = HttpUtils.getHttpRequestContent(host.toString(), params);

					GetPerceptionRuntimeLogListResponse r = XmlUtils.xml2obj(IniConfig.mapping, xml,
							GetPerceptionRuntimeLogListResponse.class);

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
			list.add(dto.getGroupTag());
			listTag.add(dto.getGroupTag());

			for (PerceptionRuntimeLogInfo info : dto.getLogInfos()) {
				StringBuffer sb = new StringBuffer();
				sb.append(info.getCreateDateTime());
				sb.append(" ");
				sb.append(info.getPerceptionParamName());
				sb.append(":");
				sb.append(info.getPerceptionParamValueDesc());
				list.add(sb.toString());
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
