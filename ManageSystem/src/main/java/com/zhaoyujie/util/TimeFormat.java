package com.zhaoyujie.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class TimeFormat {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	private static SimpleDateFormat sdfTurnTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfTurnTimeYMD = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfMM = new SimpleDateFormat("MM");
	private static SimpleDateFormat sdfdd = new SimpleDateFormat("dd");

	/**
	 * @return 返回时间格式（2016-09-08 12-09-46）
	 */
	public static String getFormatStr() {
		return sdf.format(new Date());
	}

	/**
	 * @param time传入是时间戳
	 * @return 返回时间格式（2016-09-08 12:09:46）
	 * @throws ParseException
	 */
	public static String getFormatStr(long time) throws ParseException {
		Date date = sdfTurnTime.parse(String.valueOf(time));
		return sdf.format(date);
	}

	/**
	 * @return 返回时间格式（2016-09-08 12:09:46）
	 */
	public static String getFormatStrInfo() {
		return sdfTurnTime.format(new Date());
	}

	/**
	 * @return 返回时间格式（2016-09-08）
	 */
	public static String getFormatStrYMD() {
		return sdfTurnTimeYMD.format(new Date());
	}

	/**
	 * @return
	 */
	/**
	 * @param DateTime 传入Date
	 * @return 返回时间格式（2016-09-08 12:09:46）
	 */
	public static String getFormatStrYMDByDate(Date DateTime) {
		return sdfTurnTime.format(DateTime);
	}

	/**
	 * @param turnTime 传入时间格式（2016-09-08 12:09:46）
	 * @return 传出Date
	 * @throws ParseException
	 */
	public static Date getFormatStrToDate(String turnTime) throws ParseException {
		Date date = new Date();
		date = sdfTurnTime.parse(turnTime);
		return date;
	}

	/**
	 * @param date 传入Date
	 * @return 传出[6,25]//[月份，日期]-实例：6月25日
	 * @throws ParseException
	 */
	public static String[] getFormatStrToDate(Date date) throws ParseException {
		String month = Integer.valueOf(sdfMM.format(date)) + "";
		String day = sdfdd.format(date);
		String[] time = { month, day };
		return time;
	}

	/**
	 * @param year
	 * @return 返回年分内所有月份的时间戳范围
	 * @throws Exception
	 */
	public static Map<Integer, JSONObject> getAYearMonthArea(int year) throws Exception {
		if (year < 0 || year > 2200) {
			throw new Exception("传入年份错误。year：" + year);
		}
		JSONObject return_data = new JSONObject();
		for (int i = 1; i <= 13; i++) {
			String startTimeStr = "";
			String month = "";
			if (i <= 12) {
				if (i > 9) {
					month = String.valueOf(i);
				} else {
					month = "0" + i;
				}
				startTimeStr = year + "-" + month + "-01 00:00:00";
			} else {
				startTimeStr = (year + 1) + "-01-01 00:00:00";
			}
			return_data.put(String.valueOf(i), getFormatStrToDate(startTimeStr));
		}
		Map<Integer, JSONObject> timeMap = new HashMap<>();
		for (int i = 1; i < 13; i++) {
			JSONObject time = new JSONObject();
			time.put("start", return_data.getDate(String.valueOf(i)));
			time.put("end", return_data.getDate(String.valueOf(i + 1)));
			timeMap.put(i, time);
		}
		return timeMap;
	}

	/**
	 * @return 根据时间戳计算天数
	 */
	public static String getDayNumberByLong(long time) {
		// 一天的时间戳
		long dayTime = 24L * 60 * 60 * 1000;
		int number = (int) (time / dayTime);
		int yushu = (int) (time % dayTime);
		if (yushu > 0) {
			number++;
		}
		return String.valueOf(number);
	}

	/**
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 根据相差月份
	 * @throws ParseException
	 */
	public static int getBetweenMonth(Date start, Date end) throws ParseException {
		String str1 = sdfTurnTimeYMD.format(end);// 当前时间
		String str2 = sdfTurnTimeYMD.format(start);// 发证时间
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(sdfTurnTimeYMD.parse(str1));
		aft.setTime(sdfTurnTimeYMD.parse(str2));
		int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int year = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		if (result < 0) {
			result = Math.abs(result);
			if (surplus < 0) {
				result = result + 1;
			}
		} else if (result == 0) {
			if (surplus < 0) {
				result = 1;
			}
		} else {
			if (surplus > 0) {
				result = result + 1;
			}
		}
		int yearData = ((Math.abs(year)) + result);
		return yearData;
	}

}
