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

		perceptionStatusPanelIntent = new Intent(this, PerceptionCurrentStatusActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		perceptionRuntimeLogPanelIntent = new Intent(this, PerceptionRuntimeLogActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		perceptionRuntimeVediaFilePanelInent = new Intent(this, PerceptionRuntimeVediaFileActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		addTab();

		tabHost.setCurrentTab(0);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

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
		tabHost.addTab(tabHost.newTabSpec("perceptionStatusPanelIntent")
				.setIndicator("当前状态", getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(perceptionStatusPanelIntent));

		perceptionRuntimeLogPanelIntent.putExtra("perceptionId", getIntent().getStringExtra("perceptionId"));
		tabHost.addTab(tabHost.newTabSpec("perceptionRuntimeLogPanelIntent")
				.setIndicator("运行日志", getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(perceptionRuntimeLogPanelIntent));

		if (PerceptionCurrentStatusActivity.perceptionType == 2) {
			tabHost.addTab(tabHost.newTabSpec("perceptionRuntimeVediaFilePanelInent")
					.setIndicator("运行视频", getResources().getDrawable(R.drawable.ic_launcher))
					.setContent(perceptionRuntimeVediaFilePanelInent));
		}

	}

}
