package com.dahuangit.iots.app.android.util;

import java.util.List;

import android.content.Context;
import android.location.LocationManager;

public class NetUtils {
	public static boolean isGpsEnabled(Context context) {
		LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders = lm.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}
}
