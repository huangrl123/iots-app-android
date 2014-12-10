/*
 * Copyright (C) 2012 yueyueniao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

public class PageFragment1 extends Fragment {
	private ListView machineListView = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page1, null);
		machineListView = (ListView) view.findViewById(R.id.ListView01);

		// 生成动态数组，加入数据
		List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 5; i++) {
			// HashMap为键值对类型。第一个参数为键，第二个参数为值
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.head_default);
			map.put("ItemTitle", "设备" + i);
			map.put("ItemText", "深圳市富士康A场5栋" + i + "楼厂房");
			map.put("ItemSign", "在线 ");
			map.put("ItemInImage", R.drawable.in);
			listItem.add(map);// 添加到listItem中
		}

		// 生成适配器的Item和动态数组对应的元素，这里用SimpleAdapter作为ListView的数据源
		// 如果条目布局比较复杂，可以继承BaseAdapter来定义自己的数据源。
		// 生成一个SimpleAdapter类型的变量来填充数据
		SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(),// this是当前Activity的对象
				listItem,// 数据源 为填充数据后的ArrayList类型的对象
				R.layout.listview_item,// 子项的布局.xml文件名
				new String[] { "ItemImage", "ItemTitle", "ItemText", "ItemSign", "ItemInImage" },
				// 这个String数组中的元素就是list对象中的列，list中有几这个数组中就要写几列。
				new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText, R.id.ItemSign, R.id.ItemInImage });// 值是对应XML布局文件中的一个ImageView,三个TextView的id
		// 添加并显示
		machineListView.setAdapter(listItemAdapter);

		// 添加点击
		machineListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			// 重写单击响应
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), PerceptionInfoTabActivity.class);
				startActivity(intent);
				getActivity().setTitle("点击第" + arg2 + "个项目");// 直接在标题显示
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

		return view;
	}

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		getActivity().setTitle("选择了长按菜单中第" + item.getItemId() + "项功能");// 又是在标题栏
		return super.onContextItemSelected(item);
	}
}
