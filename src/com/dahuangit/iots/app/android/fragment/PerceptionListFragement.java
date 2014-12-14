package com.dahuangit.iots.app.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dahuangit.iots.app.android.R;
import com.dahuangit.iots.app.android.activity.LoginActivity;
import com.dahuangit.iots.app.android.activity.PerceptionInfoTabActivity;
import com.dahuangit.iots.app.android.common.IniConfig;
import com.dahuangit.iots.app.android.dto.response.GetPerceptionListResponse;
import com.dahuangit.iots.app.android.dto.response.PerceptionInfo;
import com.dahuangit.iots.app.android.util.HttpUtils;
import com.dahuangit.iots.app.android.util.XmlUtils;

public class PerceptionListFragement extends Fragment {
	private ListView machineListView = null;

	private List<Map<String, Object>> itemList = null;

	private SimpleAdapter listItemAdapter = null;

	private final OnKeyListener backKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(final View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
				new AlertDialog.Builder(v.getContext()).setTitle("提示").setMessage("确定退出?")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								System.exit(0);
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								// 取消按钮事件
							}
						}).show();

				return true;
			}

			return false;
		}
	};

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case 1:
				GetPerceptionListResponse response = (GetPerceptionListResponse) msg.getData().get("response");
				for (PerceptionInfo info : response.getPerceptionDtos()) {
					itemList.add(perceptionInfoToItemViewMap(info));
				}

				listItemAdapter.notifyDataSetChanged();
				break;
			}
		}

	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page1, null);

		machineListView = (ListView) view.findViewById(R.id.ListView01);

		machineListView.setOnKeyListener(backKeyListener);

		// NetworkOnMainThreadExceptionKit.kit();

		itemList = new ArrayList<Map<String, Object>>();

		listItemAdapter = new SimpleAdapter(getActivity(), itemList, R.layout.listview_item, new String[] {
				"ItemImage", "ItemTitle", "ItemText", "ItemSign", "perceptionId", "ItemInImage" }, new int[] {
				R.id.ItemImage, R.id.ItemTitle, R.id.ItemText, R.id.ItemSign, R.id.perceptionId, R.id.ItemInImage });

		machineListView.setAdapter(listItemAdapter);

		// 点击操作
		machineListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long index) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), PerceptionInfoTabActivity.class);
				Map<String, Object> map = itemList.get((int) index);
				intent.putExtra("perceptionId", String.valueOf(map.get("perceptionId")));
				startActivity(intent);
			}
		});

		// 添加长按点击
		machineListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("功能选择菜单");
				menu.add(0, 0, 0, "预览（设想功能）");
				menu.add(0, 1, 0, "返回（设想功能）");
			}
		});

		// 检查服务器数据有无更新
		new Thread() {
			public void run() {
				try {
					StringBuffer host = new StringBuffer();
					host.append(IniConfig.propConfig.getProperty("server.host"));
					host.append(IniConfig.propConfig.getProperty("getPerceptionList.url"));
					Map<String, String> params = new HashMap<String, String>();

					Integer localMaxPerceptionId = 0;

					params.put("userId", IniConfig.currentUser.getUserId().toString());
					params.put("localMaxPerceptionId", localMaxPerceptionId.toString());

					String xmlStr = HttpUtils.getHttpRequestContent(host.toString(), params);

					GetPerceptionListResponse response = XmlUtils.xml2obj(IniConfig.mapping, xmlStr,
							GetPerceptionListResponse.class);

					Message msg = new Message();
					msg.what = 1;
					Bundle data = new Bundle();
					data.putSerializable("response", response);
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		return view;
	}

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		getActivity().setTitle("选择了长按菜单中第" + item.getItemId() + "项功能");// 又是在标题栏
		return super.onContextItemSelected(item);
	}

	private Map<String, Object> perceptionInfoToItemViewMap(PerceptionInfo info) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.head_default);
		map.put("ItemTitle", "设备[" + info.getPerceptionAddr() + "]");
		map.put("ItemText", info.getInstallSite());
		map.put("ItemSign", info.getIsOnline() ? "在线 " : "离线");
		map.put("perceptionId", info.getPerceptionId());
		map.put("ItemInImage", R.drawable.in);

		return map;
	}

}
