package com.dahuangit.iots.app.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dahuangit.iots.app.android.R;

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

		setData();
	}

	public void setData() {
		list.add("2014年12月08日");
		listTag.add("2014年12月08日");
		for (int i = 0; i < 3; i++) {
			list.add("2014年12月08日 " + i +"分50秒  红外感应状态:接近");
		}
		
		list.add("2014年12月07日");
		listTag.add("2014年12月07日");
		for (int i = 0; i < 3; i++) {
			list.add("2014年12月07日 " + i +"分50秒  按键状态:关闭");
		}
		
		list.add("2014年12月05日");
		listTag.add("2014年12月05日");
		for (int i = 0; i < 30; i++) {
			list.add("2014年12月05日 " + i +"分50秒  电机1旋转状态:正转");
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
