package com.dahuangit.iots.app.android.dto.request;

import java.util.Date;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.dahuangit.iots.app.android.dto.Dto;
import com.dahuangit.iots.app.android.util.NetUtils;

public class Request extends Dto {

	/** 经度 */
	private Double latitude = null;

	/** 维度 */
	private Double longitude = null;

	/** 海拔 */
	private Double altitude = null;

	public Request(Context context) {
		boolean isGpsEnabled = NetUtils.isGpsEnabled(context);

		if (isGpsEnabled) {
			LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			if (null != location) {
				this.latitude = location.getLatitude();// 经度
				this.longitude = location.getLongitude();// 纬度
				this.altitude = location.getAltitude();// 海拔
			}
		}
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

}
