package com.dahuangit.iots.app.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期相关工具
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月11日 下午1:31:45
 */
public abstract class DateUtils {

	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final Long ONE_DAY_IN_MILLS = 1000 * 3600 * 24L;

	public static final String REG = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

	/**
	 * 以标准形式(yyyy-MM-dd HH:mm:ss)格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static final String format(Date date) {
		return SDF.format(date);
	}

	/**
	 * 以标准形式(yyyy-MM-dd HH:mm:ss)解析日期
	 * 
	 * @param s
	 * @return
	 */
	public static final Date parse(String s) {
		try {
			return SDF.parse(s);
		} catch (ParseException e) {
			throw new IllegalArgumentException(s + "不符合标准日期格式：" + "yyyy-MM-dd HH:mm:ss");
		}
	}

	/**
	 * 以指定形式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static final String format(Date date, String formatPattern) {
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);
		return df.format(date);
	}

	/**
	 * 以指定形式解析日期
	 * 
	 * @param s
	 * @return
	 */
	public static final Date parse(String s, String parsePattern) {
		SimpleDateFormat df = new SimpleDateFormat(parsePattern);
		try {
			return df.parse(s);
		} catch (ParseException e) {
			throw new IllegalArgumentException(s + "不符合日期格式：" + parsePattern);
		}
	}

	/**
	 * 返回当前日期时间字符串
	 * 
	 * @return
	 */
	public static final String now() {
		return SDF.format(new Date());
	}

	/**
	 * 为指定日期增加指定年数，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            年数，可为负数
	 * @return 新的日期
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 为指定日期增加指定月数，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            月数，可为负数
	 * @return 新的日期
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 为指定日期增加指定周，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            周数，可为负数
	 * @return 新的日期
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	/**
	 * 为指定日期增加指定天，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            天数，可为负数
	 * @return 新的日期
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * 为指定日期增加指定小时，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            小时数，可为负数
	 * @return 新的日期
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * 为指定日期增加指定分钟数，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            分钟数，可为负数
	 * @return 新的日期
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 为指定日期增加指定秒数，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            秒数，可为负数
	 * @return 新的日期
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 为指定日期增加指定毫秒数，原日期将不做修改
	 * 
	 * @param date
	 *            日期，必须
	 * @param amount
	 *            毫秒数，可为负数
	 * @return 新的日期
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	private static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("必须设置日期");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * 得到某月的某一天
	 * 
	 * @param month
	 *            月份，与当前月比较如：上个月是 -1 ，当前月是 0
	 * @param day
	 *            日期，如 ：1 是 2011-04-01 ，31 是 2011-05-01
	 * @param parsePattern
	 *            日期格式
	 * @return 新的日期字符串
	 */
	public static final String getNowDayOfMonth(int month, int day, String parsePattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param month
	 *            当前月
	 * @param year
	 *            当前年
	 */
	public static final String getLastDayOfMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		int timeOfYear = Integer.parseInt(format(date, "yyyy"));
		int timeOfMonth = Integer.parseInt(format(date, "MM"));
		calendar.add(Calendar.YEAR, year - timeOfYear);
		calendar.add(Calendar.MONTH, month - timeOfMonth);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = format(calendar.getTime(), "dd");
		return lastDay;
	}

	/**
	 * 校验日期格式
	 * 
	 * @param date
	 *            日期字符串
	 * @param reg
	 *            正则表达式
	 * @return
	 */
	public static boolean checkDateStyle(String date, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(date);
		return m.matches();
	}

	/**
	 * 校验日期格式
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static boolean checkDateStyle(String date) {
		Pattern p = Pattern.compile(REG);
		Matcher m = p.matcher(date);
		return m.matches();
	}

	// 获得本年第一天的日期
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

}
