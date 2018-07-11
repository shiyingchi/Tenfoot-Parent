package com.project.utils;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * @author squirrel
 *
 */
public class DateUtils {
	
	public final static String FORMMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
	public final static String FORMMAT_HOUR_MINUTE = "HH:mm";
	public final static String FROMMAT_YEARMONTHDAY = "yyyyMMdd";
	private static final long MILLIS_IN_A_SECOND = 1000;

	private static final long SECONDS_IN_A_MINUTE = 60;

	private static final long MINUTES_IN_AN_HOUR = 60;

	private static final long HOURS_IN_A_DAY = 24;

	private static final int DAYS_IN_A_WEEK = 7;

	private static final int MONTHS_IN_A_YEAR = 12;

	@SuppressWarnings("unused")
	private static final int[] daysInMonth = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 最小日期，设定为1000年1月1日
	 */
	public static final Date MIN_DATE = getMinDate();

	/**
	 * 最大日期，设定为8888年1月1日
	 */
	public static final Date MAX_DATE = getMaxDate();

	private static Date getMaxDate() {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate("8888-01-01", new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Date getMinDate() {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate("1000-01-01", new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算两个日期（不包括时间）之间相差的周年数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getYearDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException("date1 and date2 cannot be null!");
		}
		if (date1.after(date2)) {
			throw new InvalidParameterException("date1 cannot be after date2!");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		int day1 = calendar.get(Calendar.DATE);

		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		int day2 = calendar.get(Calendar.DATE);

		int result = year2 - year1;
		if (month2 < month1) {
			result--;
		} else if (month2 == month1 && day2 < day1) {
			result--;
		}
		return result;
	}

	/**
	 * 计算两个日期（不包括时间）之间相差的整月数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMonthDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException("date1 and date2 cannot be null!");
		}
		if (date1.after(date2)) {
			throw new InvalidParameterException("date1 cannot be after date2!");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		int day1 = calendar.get(Calendar.DATE);

		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		int day2 = calendar.get(Calendar.DATE);

		int months = 0;
		if (day2 >= day1) {
			months = month2 - month1;
		} else {
			months = month2 - month1 - 1;
		}
		return (year2 - year1) * MONTHS_IN_A_YEAR + months;
	}

	/**
	 * 统计两个日期之间包含的天数。包含date1，但不包含date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDayDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException("date1 and date2 cannot be null!");
		}
		Date startDate = org.apache.commons.lang3.time.DateUtils.truncate(date1, Calendar.DATE);
		Date endDate = org.apache.commons.lang3.time.DateUtils.truncate(date2, Calendar.DATE);
		if (startDate.after(endDate)) {
			throw new InvalidParameterException("date1 cannot be after date2!");
		}
		long millSecondsInOneDay = HOURS_IN_A_DAY * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE * MILLIS_IN_A_SECOND;
		return (int) ((endDate.getTime() - startDate.getTime()) / millSecondsInOneDay);
	}

	/**
	 * 计算time2比time1晚多少分钟，忽略日期部分
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getMinuteDiffByTime(Date time1, Date time2) {
		long startMil = 0;
		long endMil = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time1);
		calendar.set(1900, 1, 1);
		startMil = calendar.getTimeInMillis();
		calendar.setTime(time2);
		calendar.set(1900, 1, 1);
		endMil = calendar.getTimeInMillis();
		return (int) ((endMil - startMil) / MILLIS_IN_A_SECOND / SECONDS_IN_A_MINUTE);
	}

	/**
	 * 计算指定日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPrevDay(Date date) {
		return org.apache.commons.lang3.time.DateUtils.addDays(date, -1);
	}

	/**
	 * 计算指定日期的后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		return org.apache.commons.lang3.time.DateUtils.addDays(date, 1);
	}

	/**
	 * 判断date1是否在date2之后，忽略时间部分
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateAfter(Date date1, Date date2) {
		Date theDate1 = org.apache.commons.lang3.time.DateUtils.truncate(date1, Calendar.DATE);
		Date theDate2 = org.apache.commons.lang3.time.DateUtils.truncate(date2, Calendar.DATE);
		return theDate1.after(theDate2);
	}

	/**
	 * 判断date1是否在date2之前，忽略时间部分
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateBefore(Date date1, Date date2) {
		return isDateAfter(date2, date1);
	}

	/**
	 * 判断time1是否在time2之后，忽略日期部分
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isTimeAfter(Date time1, Date time2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);
		calendar1.set(1900, 1, 1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);
		calendar2.set(1900, 1, 1);
		return calendar1.after(calendar2);
	}

	/**
	 * 判断time1是否在time2之前，忽略日期部分
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isTimeBefore(Date time1, Date time2) {
		return isTimeAfter(time2, time1);
	}

	/**
	 * 判断两个日期是否同一天（忽略时间部分）
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return org.apache.commons.lang3.time.DateUtils.isSameDay(date1, date2);
	}

	/**
	 * 判断两个日历天是否同一天（忽略时间部分）
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Calendar date1, Calendar date2) {
		return org.apache.commons.lang3.time.DateUtils.isSameDay(date1, date2);
	}

	/**
	 * 将字符串形式的日期表示解析为日期对象
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date parseDate(String dateString) {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(dateString,
					new String[] { "yyyy-MM-dd", "yyyy-M-d", "yyyy-MM-d", "yyyy-M-dd" });
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将字符串形式的时间表示解析为日期时间对象
	 * 
	 * @param timeString
	 * @return
	 */
	public static Date parseTime(String timeString) {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(timeString,
					new String[] { "HH:mm:ss", "H:m:s", "HH:mm", "H:m" });
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将字符串形式的日期时间表示解析为时间对象
	 * 
	 * @param timeString
	 * @return
	 */
	public static Date parseDateTime(String timeString) {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(timeString,
					new String[] { "yyyy-MM-dd hh:mm:ss", "yyyy-M-d h:m:s", "yyyy-MM-dd h:m:s", "yyyy-M-d hh:mm:ss" });
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 计算两个日期之间包含的星期X的天数。
	 * 
	 * @param fromDate
	 *            起始日期
	 * @param toDate
	 *            结束日期
	 * @param dayOfWeek
	 *            星期，例如星期三，星期四
	 * @return
	 */
	public static int getWeekDaysBetween(Date fromDate, Date toDate, int dayOfWeek) {
		int result = 0;
		Date firstDate = getFirstWeekdayBetween(fromDate, toDate, dayOfWeek);
		if (firstDate == null) {
			return 0;
		}
		Calendar aDay = Calendar.getInstance();
		aDay.setTime(firstDate);
		while (aDay.getTime().before(toDate)) {
			result++;
			aDay.add(Calendar.DATE, DAYS_IN_A_WEEK);
		}
		return result;
	}

	/**
	 * 获取在两个日期之间的第一个星期X
	 * 
	 * @param fromDate
	 *            起始日期
	 * @param toDate
	 *            结束日期
	 * @param dayOfWeek
	 *            星期，例如星期三，星期四
	 * @return
	 */
	public static Date getFirstWeekdayBetween(Date fromDate, Date toDate, int dayOfWeek) {
		Calendar aDay = Calendar.getInstance();
		aDay.setTime(fromDate);
		while (aDay.getTime().before(toDate)) {
			if (aDay.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
				return aDay.getTime();
			}
			aDay.add(Calendar.DATE, 1);
		}
		return null;
	}

	/**
	 * 取得参数year指定的年份的总天数
	 * 
	 * @param year
	 * @return
	 */
	public static int getDaysInYear(int year) {
		Calendar aDay = Calendar.getInstance();
		aDay.set(year, 1, 1);
		Date from = aDay.getTime();
		aDay.set(year + 1, 1, 1);
		Date to = aDay.getTime();
		return getDayDiff(from, to);
	}

	/**
	 * 取得指定年月的总天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysInMonth(int year, int month) {
		Calendar aDay = Calendar.getInstance();
		aDay.set(year, month, 1);
		Date from = aDay.getTime();
		if (month == Calendar.DECEMBER) {
			aDay.set(year + 1, Calendar.JANUARY, 1);
		} else {
			aDay.set(year, month + 1, 1);
		}
		Date to = aDay.getTime();
		return getDayDiff(from, to);
	}

	/**
	 * 获得指定日期的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return getFieldValue(date, Calendar.YEAR);
	}

	/**
	 * 获得指定日期的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return getFieldValue(date, Calendar.MONTH) + 1;
	}

	/**
	 * 获得指定日期是当年的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		return getFieldValue(date, Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得指定日期是当月的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		return getFieldValue(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得指定日期是当周的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		return getFieldValue(date, Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得指定日期是当周的第几天,返回的是中文
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayOfWeekString(Date date) {
		Integer i = getFieldValue(date, Calendar.DAY_OF_WEEK);
		if (i == 1) {
			return "日";
		} else if (i == 2) {
			return "一";
		} else if (i == 3) {
			return "二";
		} else if (i == 4) {
			return "三";
		} else if (i == 5) {
			return "四";
		} else if (i == 6) {
			return "五";
		} else {
			return "六";
		}
	}

	private static int getFieldValue(Date date, int field) {
		if (date == null) {
			throw new InvalidParameterException("date cannot be null!");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	// /////////////// add methods /////////////////////////////////

	/**
	 * 返回系统当前月
	 * 
	 * @param sdate
	 * @return
	 */
	public static Integer getNowMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前年
	 * 
	 * @return
	 */
	public static Integer getNowYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获得当前日
	 * 
	 * @return
	 */
	public static Integer getNowDay() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取系统当前月的天数
	 * 
	 * @return
	 */
	public static Integer getCountDaysInNowMonth() {
		Calendar c = Calendar.getInstance();
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取某年某月的天数
	 * 
	 * @return
	 */
	public static Integer getCountDaysInMonth(String year, String month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.MONTH, Integer.parseInt(month) - 1);// 月的计数从0开始,所以要减1
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据格式字符串格式化当前时间
	 * 
	 * @param formatStr
	 * @return
	 */
	public static String getNowStr(String formatStr) {
		Date date = new Date();
		return new SimpleDateFormat(formatStr).format(date);
	}

	/**
	 * 获取月的首日是星期几，对应关系： 日 一 二 三 四 五 六 1 2 3 4 5 6 7
	 * 
	 * @return
	 */
	public static Integer getWeekThisMonth(String year, String month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.MONTH, Integer.parseInt(month) - 1);// 月的计数从0开始,所以要减1
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置月的第一日
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 转Calendar的时间为字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String changeCalendarToString(Calendar calendar, String format) {
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * 转Calendar的时间为字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String changeDateToString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static String formatDate(Date date, String formatStr) throws Exception {
		return new SimpleDateFormat(formatStr).format(date);
	}

	/**
	 * 返回一日同一周的七天
	 * 
	 * @return
	 */
	public static List<String> findWeekDaysByDay(String day) throws Exception {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			dayOfWeek = 8;
		}
		calendar.add(Calendar.DAY_OF_MONTH, 2 - dayOfWeek);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			list.add(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		return list;

	}

	/**
	 * 格式化日期，去掉后面的时间，全部改成0
	 * 
	 * @param str
	 *            参数必须为字符串形式的日期，格式为yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static Date formatDate(String str) {
		String[] strs = str.split(" ");
		String[] strDate = strs[0].split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(strDate[0]).intValue(), Integer.valueOf(strDate[1]).intValue() - 1,
				Integer.valueOf(strDate[2]).intValue(), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取一个日期是星期几
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekByDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if (week == 1) {
			return 7;
		} else {
			return week - 1;
		}
	}

	/**
	 * 获取当前时间只有日期的Calendar实例
	 * 
	 * @return
	 */
	public static Calendar getCalendarInstanceDate() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return ca;
	}

	/**
	 * 在date基础上增加多少天
	 * 
	 * @param date
	 *            当前输入日期
	 * @param amount
	 *            增加的天数
	 * @return
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * 在当前时间date基础上增加时分秒指定值
	 * 
	 * @param date
	 *            当前输入时间
	 * @param calendarField
	 *            Calendar.DAY_OF_MONTH 等
	 * @param amount
	 *            增加的幅度
	 * @return
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}
	
	/**
	 * 计算添加多少小时后的时间
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}
	
	/**
	 * @author yangjk 2015年10月12日 下午5:17:18
	 * @Title: getMonthBeginDate
	 * @Description: 获取传入日期的月份的一号日期，即获取月初日期，date为Null传入时，为当前日期；int为需要变更的月份的正负值，
	 *               0时不变
	 * @param date
	 * @param plusMonth
	 * @return Date
	 */
	public static Date getMonthBeginDate(Date date, int plusMonth) {
		if (date == null)
			date = new Date();
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.set(Calendar.MONTH, calDate.get(Calendar.MONTH) + plusMonth);
		calDate.set(Calendar.DAY_OF_MONTH, 1);
		return calDate.getTime();
	}

	/**
	 * @author yangjk 2015年10月12日 下午6:11:13
	 * @Title: getMonthEndDate
	 * @Description: 获取传入日期的月份的最后一天日期，即获取月初末日期，date为Null传入时，为当前日期；
	 *               int为需要变更的月份的正负值，0时不变
	 * @param date
	 * @param plusMonth
	 * @return Date
	 */
	public static Date getMonthEndDate(Date date, int plusMonth) {
		if (date == null)
			date = new Date();
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.set(Calendar.MONTH, calDate.get(Calendar.MONTH) + plusMonth);
		calDate.set(Calendar.DATE, calDate.getMaximum(Calendar.DATE));
		return calDate.getTime();
	}

	/**
	 * @author yangjk 2015年10月12日 下午5:18:21
	 * @Title: getNextMonthBeginDate
	 * @Description: 获取下一月的一号日期
	 * @param date
	 * @return Date
	 */
	public static Date getNextMonthBeginDate(Date date) {
		if (date == null)
			date = new Date();
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.set(Calendar.MONTH, calDate.get(Calendar.MONTH) + 1);
		calDate.set(Calendar.DAY_OF_MONTH, 1);
		return calDate.getTime();
	}

	/**
	 * 把日期格式化成当天的初始一个时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWithStartTime(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
		return rightNow.getTime();
	}

	/**
	 * 把日期格式化成当天的最后一个时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWithEndTime(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(Calendar.HOUR_OF_DAY, 23);
		rightNow.set(Calendar.MINUTE, 59);
		rightNow.set(Calendar.SECOND, 59);
		return rightNow.getTime();
	}
	
	public static String getDateStr(){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}

	/**
	 * 获取格林尼治时间
	 * 
	 * @return
	 * @throws ParseException
	 * @author jnie 2015-10-12 下午4:02:59
	 */
	public static long getGmtTime() {
		final int msInMin = 60000;
		final int minInHr = 60;
		Date date = new Date();
		int Hours, Minutes;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		TimeZone zone = dateFormat.getTimeZone();
		Minutes = zone.getOffset(date.getTime()) / msInMin;
		Hours = Minutes / minInHr;
		zone = TimeZone.getTimeZone("GMT Time" + (Hours >= 0 ? "+" : "") + Hours + ":" + Minutes);
		dateFormat.setTimeZone(zone);
		String time = dateFormat.format(date);
		long seconds = 0;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss");
			seconds = simpledateformat.parse(time).getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		} // 毫秒
		return seconds;
	}



	/**
	 * 时间格式(yyyy-MM-dd)
	 */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * 时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 计算距离现在多久，非精确
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeBefore(Date date) {
		Date now = new Date();
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String r = "";
		if (day > 0) {
			r += day + "天";
		} else if (hour > 0) {
			r += hour + "小时";
		} else if (min > 0) {
			r += min + "分";
		} else if (s > 0) {
			r += s + "秒";
		}
		r += "前";
		return r;
	}

	/**
	 * 计算距离现在多久，精确
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeBeforeAccurate(Date date) {
		Date now = new Date();
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String r = "";
		if (day > 0) {
			r += day + "天";
		}
		if (hour > 0) {
			r += hour + "小时";
		}
		if (min > 0) {
			r += min + "分";
		}
		if (s > 0) {
			r += s + "秒";
		}
		r += "前";
		return r;
	}
	
}
