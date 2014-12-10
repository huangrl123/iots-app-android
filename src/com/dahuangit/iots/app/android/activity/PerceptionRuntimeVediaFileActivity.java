package com.dahuangit.iots.app.android.activity;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;

/**
 * 感知端运行日志activity
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月8日 下午8:53:32
 */
public class PerceptionRuntimeVediaFileActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
	}
}
