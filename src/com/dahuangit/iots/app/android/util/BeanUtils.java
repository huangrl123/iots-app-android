package com.dahuangit.iots.app.android.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {
	public static Map<String, String> bean2Map(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = obj.getClass().getDeclaredFields();

		try {
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (null == value) {
					map.put(field.getName(), null);
				} else {
					map.put(field.getName(), String.valueOf(value));
				}
				field.setAccessible(false);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return map;
	}
}
