package com.dahuangit.iots.app.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.dahuangit.iots.app.android.util.XmlUtils;

public class PerceptionListFragement extends Fragment {
	private ListView machineListView = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page1, null);
		machineListView = (ListView) view.findViewById(R.id.ListView01);

		// 先从数据库查询
		PerceptionDao perceptionDao = new PerceptionDao(getActivity());
		GetPerceptionListResponse response = perceptionDao.getPerceptionList(IniConfig.currentUser.getUserId());
		perceptionDao.close();

		final List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		for (PerceptionInfo info : response.getPerceptionDtos()) {
			itemList.add(perceptionInfoToItemViewMap(info));
		}

		final SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(), itemList, R.layout.listview_item,
				new String[] { "ItemImage", "ItemTitle", "ItemText", "ItemSign", "perceptionId", "ItemInImage" },
				new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText, R.id.ItemSign, R.id.ItemInImage });
		machineListView.setAdapter(listItemAdapter);

		machineListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long index) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), PerceptionInfoTabActivity.class);
				Map<String, Object> map = itemList.get((int) index);
				intent.putExtra("perceptionId", Integer.valueOf(map.get("perceptionId").toString()));
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
					String url = IniConfig.propConfig.getProperty("getPerceptionList.url");
					Map<String, String> params = new HashMap<String, String>();

					String xmlStr = HttpUtils.getHttpRequestContent(url, params);

					GetPerceptionListResponse response = XmlUtils.xml2obj(IniConfig.mapping, xmlStr,
							GetPerceptionListResponse.class);

					List<PerceptionInfo> perceptionDtos = response.getPerceptionDtos();

					for (PerceptionInfo info : perceptionDtos) {
						itemList.add(0, perceptionInfoToItemViewMap(info));
					}

					listItemAdapter.notifyDataSetChanged();
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
		map.put("ItemTitle", "设备[" + info.getPerceptionAddr());
		map.put("ItemText", info.getInstallSite());
		map.put("ItemSign", info.getIsOnline() ? "在线 " : "离线");
		map.put("perceptionId", info.getPerceptionId());
		map.put("ItemInImage", R.drawable.in);

		return map;
	}
}
