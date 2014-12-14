package com.dahuangit.iots.app.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dahuangit.iots.app.android.R;
import com.dahuangit.iots.app.android.activity.PerceptionInfoTabActivity;
import com.dahuangit.iots.app.android.common.IniConfig;
import com.dahuangit.iots.app.android.dao.PerceptionDao;
import com.dahuangit.iots.app.android.dto.response.GetPerceptionListResponse;
import com.dahuangit.iots.app.android.dto.response.PerceptionInfo;
import com.dahuangit.iots.app.android.util.HttpUtils;
import com.dahuangit.iots.app.android.util.NetworkOnMainThreadExceptionKit;
import com.dahuangit.iots.app.android.util.XmlUtils;

public class PerceptionListFragement extends Fragment {
	private ListView machineListView = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page1, null);
		machineListView = (ListView) view.findViewById(R.id.ListView01);

		NetworkOnMainThreadExceptionKit.kit();

		// 先从数据库查询
		final PerceptionDao perceptionDao = new PerceptionDao(getActivity());
		final GetPerceptionListResponse response = perceptionDao.getPerceptionList(IniConfig.currentUser.getUserId());

		final List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		for (PerceptionInfo info : response.getPerceptionDtos()) {
			itemList.add(perceptionInfoToItemViewMap(info));
		}

		final SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(), itemList, R.layout.listview_item,
				new String[] { "ItemImage", "ItemTitle", "ItemText", "ItemSign", "perceptionId", "ItemInImage" },
				new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText, R.id.ItemSign, R.id.perceptionId,
						R.id.ItemInImage });

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

		// 主线程中的handler--用户通知界面更新
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;

				switch (what) {
				case 1: {
					listItemAdapter.notifyDataSetChanged();
					break;
				}

				}
			}

		};

		// 检查服务器数据有无更新
		new Thread() {
			public void run() {
				try {
					StringBuffer host = new StringBuffer();
					host.append(IniConfig.propConfig.getProperty("server.host"));
					host.append(IniConfig.propConfig.getProperty("getPerceptionList.url"));
					Map<String, String> params = new HashMap<String, String>();

					Integer localMaxPerceptionId = null;
					if (response != null && response.getPerceptionDtos().size() > 0) {
						localMaxPerceptionId = response.getPerceptionDtos().get(0).getPerceptionId();
					} else {
						localMaxPerceptionId = 0;
					}
					params.put("userId", IniConfig.currentUser.getUserId().toString());
					params.put("localMaxPerceptionId", localMaxPerceptionId.toString());

					String xmlStr = HttpUtils.getHttpRequestContent(host.toString(), params);

					GetPerceptionListResponse response = XmlUtils.xml2obj(IniConfig.mapping, xmlStr,
							GetPerceptionListResponse.class);

					List<PerceptionInfo> perceptionDtos = response.getPerceptionDtos();

					for (PerceptionInfo info : perceptionDtos) {
						itemList.add(0, perceptionInfoToItemViewMap(info));
						ContentValues values = new ContentValues();
						values.put("perception_type_id", info.getPerceptionTypeId());
						values.put("perception_type_name", info.getPerceptionTypeName());
						values.put("perception_id", info.getPerceptionId());
						values.put("perception_name", info.getPerceptionName());
						values.put("perception_addr", info.getPerceptionAddr());
						values.put("install_site", info.getInstallSite());
						values.put("is_on_line", info.getIsOnline());
						values.put("last_comm_time", info.getLastCommTime());
						perceptionDao.insert("t_perception", values);
					}

					perceptionDao.close();

					handler.sendEmptyMessage(1);
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
