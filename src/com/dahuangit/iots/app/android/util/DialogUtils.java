package com.dahuangit.iots.app.android.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;

import com.dahuangit.iots.app.android.activity.PerceptionInfoTabActivity;

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

	/**
	 * alert对话框
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Builder createAlertDialog(Context context, String msg) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage(msg);

		builder.setNegativeButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		return builder;
	}

}
