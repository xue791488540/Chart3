package com.example.testchart1;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 格式化时间
	 */
	public static String format(Calendar calendar) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = format.format(calendar.getTime());
		return s;
	}

	/**
	 * 格式化时间
	 */
	public static String format(Calendar calendar, String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		String s = format.format(calendar.getTime());
		return s;
	}

	/**
	 * 格式化时间
	 */
	public static String format(String dateTime, String formatFrom, String formatTo) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatFrom);
			Date date = format.parse(dateTime);
			format = new SimpleDateFormat(formatTo);
			String s = format.format(date);
			return s;
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 转换成Calendar
	 */
	public static Calendar prease1(String dateString) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			Date date = format.parse(dateString);
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 转换成Calendar
	 */
	public static Calendar prease(String dateString, String formatString) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			Calendar calendar = Calendar.getInstance();
			Date date = format.parse(dateString);
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 毫秒转时间
	 */
	public static String timesToString(long times) {
		DecimalFormat fnum = new DecimalFormat("##00");
		long totle_second = times / 1000;
		String hour = fnum.format(totle_second / 3600);
		String minute = fnum.format(totle_second % 3600 / 60);
		String second = fnum.format(totle_second % 3600 % 60);
		return hour + ":" + minute + ":" + second;
	}

	/**
	 * 比较量年月是否同月
	 */
	public static boolean compareMonth(String date1, String date2) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(format.parse(date1));
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(format.parse(date2));
			if (calendar1.get(Calendar.YEAR) != calendar2.get(Calendar.YEAR)) {
				return false;
			} else if (calendar1.get(Calendar.MONTH) != calendar2.get(Calendar.MONTH)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 比较量日期相差天数
	 */
	public static int compareDay(Calendar calendar1, Calendar calendar2) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = format.parse(format.format(calendar1.getTime()));
			Date date2 = format.parse(format.format(calendar2.getTime()));
			long day1 = date1.getTime() / 1000 / 60 / 60 / 24;
			long day2 = date2.getTime() / 1000 / 60 / 60 / 24;
			int days = (int) (day1 - day2);
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取某周的周一
	 */
	public static Calendar getMonday(Calendar calendar) {
		Calendar monday = (Calendar) calendar.clone();
		int dayw = calendar.get(Calendar.DAY_OF_WEEK);
		// 外国人 1 代表周天
		int sub;
		if (dayw != 1) {
			sub = dayw - 2;
		} else {
			sub = 6;
		}
		monday.add(Calendar.DATE, -sub);
		return monday;
	}

	/**
	 * 获取某周的周日
	 */
	public static Calendar getSunday(Calendar calendar) {
		Calendar sunday = (Calendar) calendar.clone();
		int dayw = calendar.get(Calendar.DAY_OF_WEEK);
		int sub;
		if (dayw != 1) {
			sub = 8 - dayw;
		} else {
			sub = 0;
		}
		sunday.add(Calendar.DATE, sub);
		return sunday;
	}

	/**
	 * 比较量日期相差分钟
	 */
	public static int compareMinutes(Calendar date1, Calendar date2) {
		long day1 = date1.getTimeInMillis() / 1000 / 60;
		long day2 = date2.getTimeInMillis() / 1000 / 60;
		int days = (int) (day1 - day2);
		return days;
	}

	/**
	 * 比较量日期相差分钟
	 */
	public static int compareMinutes(String dateStr1, String dateStr2) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(format.parse(dateStr1));
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(format.parse(dateStr2));
			long day1 = calendar1.getTimeInMillis() / 1000 / 60;
			long day2 = calendar2.getTimeInMillis() / 1000 / 60;
			int days = (int) (day1 - day2);
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
