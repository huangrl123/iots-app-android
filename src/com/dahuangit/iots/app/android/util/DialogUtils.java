package com.dahuangit.iots.app.android.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {

	/**
	 * 创建进度条dialog
	 * 
	 * @param context
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.BUTTON1);
		progressDialog.setTitle("进度");
		progressDialog.setMessage("正在请求,请稍后...");
		progressDialog.setButton("取消", new ProgressDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}
		});

		return progressDialog;
	}

}
