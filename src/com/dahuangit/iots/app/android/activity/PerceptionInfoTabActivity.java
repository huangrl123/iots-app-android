package com.dahuangit.iots.app.android.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.dahuangit.iots.app.android.R;

public class PerceptionInfoTabActivity extends TabActivity {
	private TabHost tabHost;

	/** 电机状态面板 */
	private Intent perceptionStatusPanelIntent = null;

	/** 电机运行日志面板 */
	private Intent perceptionRuntimeLogPanelIntent = null;

	/** 电机运行视频文件面板 */
	private Intent perceptionRuntimeVediaFilePanelInent = null;

	private ImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.tab);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		tabHost = getTabHost();

		imageView = (ImageView) findViewById(R.id.backImg);

		perceptionStatusPanelIntent = new Intent(this, PerceptionCurrentStatusActivity.class);
		perceptionRuntimeLogPanelIntent = new Intent(this, PerceptionRuntimeLogActivity.class);
		perceptionRuntimeVediaFilePanelInent = new Intent(this, PerceptionRuntimeVediaFileActivity.class);

		addTab();// 添加标签
		// 设置TabHost背景颜色
		// tabHost.setBackgroundColor(Color.argb(150, 20, 80, 150));
		// 设置TabHost背景图片资源
		// tabHost.setBackgroundResource(R.drawable.ic_launcher);
		// 设置当前显示哪一个标签 我的理解就是当你第一次启动程序默认显示那个标签 这里是指定的选项卡的ID从0开始
		tabHost.setCurrentTab(0);
		// 标签切换事件处理，setOnTabChangedListener
		// 注意是标签切换事件不是点击事件，而是从一个标签切换到另外一个标签会触发的事件
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// AlertDialog.Builder builder = new
				// AlertDialog.Builder(PerceptionInfoTabActivity.this);
				// Dialog dia;
				// builder.setTitle("提示");
				// builder.setMessage("当前选中了" + tabId + "标签");

				// builder.setNegativeButton("确定", new OnClickListener() {
				//
				// public void onClick(DialogInterface dialog, int which) {
				// dialog.cancel();
				// }
				// });

				// dia = builder.create();
				// dia.show();
			}
		});

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void addTab() {
		perceptionStatusPanelIntent.putExtra("perceptionId", getIntent().getStringExtra("perceptionId"));
		tabHost.addTab(tabHost.newTabSpec("当前状态")
				.setIndicator("当前状态", getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(perceptionStatusPanelIntent));

		perceptionRuntimeLogPanelIntent.putExtra("perceptionId", getIntent().getStringExtra("perceptionId"));
		tabHost.addTab(tabHost.newTabSpec("运行日志")
				.setIndicator("运行日志", getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(perceptionRuntimeLogPanelIntent));

		if (PerceptionCurrentStatusActivity.perceptionType == 2) {
			tabHost.addTab(tabHost.newTabSpec("运行视频")
					.setIndicator("运行视频", getResources().getDrawable(R.drawable.ic_launcher))
					.setContent(perceptionRuntimeVediaFilePanelInent));
		}

	}

}
