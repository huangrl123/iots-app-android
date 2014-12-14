package com.dahuangit.iots.app.android.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
	public static View getRootView(Activity context) {
		return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
	}
}
