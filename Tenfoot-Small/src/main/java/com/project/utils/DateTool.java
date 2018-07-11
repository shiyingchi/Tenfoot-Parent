package com.project.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期时间相关工具 日期格式 日期计算
 * 
 */

public class DateTool {

    public static void main(String[] args) throws Exception {
        // System.out.println(DateTool.getWeekDayNum(DateTool.getDateFormat("2009-04-18", 1)));
        // System.out.println(DateTool.getStringDateFormat(DateTool.getNDayOfDate(new Date(), -3), 5));
    }

    public static final int yyyy_MM_dd = 1;

    public static final int yyyy_M_d = 2;

    public static final int yy_MM_dd = 3;

    public static final int yy_M_d = 4;

    public static final int yyyy_MM_dd_HH_mm_ss = 5;

    public static final int yyyy_M_d_H_m_s = 6;

    public static final int yy_MM_dd_HH_mm_ss = 7;

    public static final int yy_M_d_H_m_s = 8;

    public static final int yyyy = 9;

    public static final int yyyy_MM = 10;

    public static final int yyyyMMdd = 11;

    public static final int yyyyMM = 12;

    public static final int yyyyMMddHHmmss = 13;

    public static final int yyMMddHH = 14;

    private static final int yyyy_MM_dd_T_HH_mm_ss_0Z = 15;

    private static final int HH_mm_ss = 16;

    private static final int HHmmss = 17;

    private static final String yyyy_MM_dd_Str = "yyyy-MM-dd";

    private static final String yyyy_M_d_Str = "yyyy-M-d";

    private static final String yy_MM_dd_Str = "yy-MM-dd";

    private static final String yy_M_d_Str = "yy-M-d";

    private static final String yyyy_MM_dd_HH_mm_ss_Str = "yyyy-MM-dd HH:mm:ss";

    private static final String yyyy_M_d_H_m_s_Str = "yyyy-M-d H:m:s";

    private static final String yy_MM_dd_HH_mm_ss_Str = "yy-MM-dd HH:mm:ss";
    private static final String yy_M_d_H_m_s_Str = "yy-M-d H:m:s";
    private static final String yyyy_Str = "yyyy";
    private static final String yyyy_MM_Str = "yyyy-MM";
    private static final String yyyyMMdd_Str = "yyyyMMdd";
    private static final String yyyyMM_Str = "yyyyMM";
    private static final String yyMMddHH_Str = "yyMMddHH";
    private static final String HH_mm_ss_Str = "HH:mm:ss";
    private static final String HHmmss_Str = "HHmmss";
    private static final String yy_MM_dd_HH_mm_ss_Str2 = "yyyy-MM-dd'T'HH:mm:ss.0'Z'";
    private static final String yyyyMMddHHmmss_Str = "yyyyMMddHHmmss";

    // 2010-01-01T00:00:00.0Z

    private static final int THOUSAND = 1000;
    private static final int SEVEN = 7;
    private static final int SIXTY = 60;
    private static final int TWENTYFOUR = 24;

    /**
     * 输入String类型的日期与格式代号，以String类型返回需要的格式
     * 
     * @param date
     *            String 类型的日期
     * @param i
     *            格式类型 可以这样调用DateTool.yyyy_MM_dd
     * */
    public static String getStringDateFormat(String date, int i) throws Exception {
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        switch (i) {
            case yyyy_MM_dd: // '\001'
                simpledateformat.applyPattern(yyyy_MM_dd_Str);
                break;
            case yyyy_M_d: // '\002'
                simpledateformat.applyPattern(yyyy_M_d_Str);
                break;
            case yy_MM_dd: // '\003'
                simpledateformat.applyPattern(yy_MM_dd_Str);
                break;
            case yy_M_d: // '\004'
                simpledateformat.applyPattern(yy_M_d_Str);
                break;
            case yyyy_MM_dd_HH_mm_ss: // '\005'
                simpledateformat.applyPattern(yyyy_MM_dd_HH_mm_ss_Str);
                break;
            case yyyy_M_d_H_m_s: // '\006'
                simpledateformat.applyPattern(yyyy_M_d_H_m_s_Str);
                break;
            case yy_MM_dd_HH_mm_ss: // '\007'
                simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str);
                break;
            case yy_M_d_H_m_s: // '\b'
                simpledateformat.applyPattern(yy_M_d_H_m_s_Str);
                break;
            case yyyy: //
                simpledateformat.applyPattern(yyyy_Str);
                break;
            case yyyy_MM: //
                simpledateformat.applyPattern(yyyy_MM_Str);
                break;
            case yyyyMMdd: //
                simpledateformat.applyPattern(yyyyMMdd_Str);
                break;
            case yyyyMM: //
                simpledateformat.applyPattern(yyyyMM_Str);
                break;
            case yyyyMMddHHmmss:
                simpledateformat.applyPattern(yyyyMMddHHmmss_Str);
                break;
            case yyMMddHH:
                simpledateformat.applyPattern(yyMMddHH_Str);
                break;
            case yyyy_MM_dd_T_HH_mm_ss_0Z: //
                simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str2);
                break;
            case HH_mm_ss: //
                simpledateformat.applyPattern(HH_mm_ss_Str);
                break;
            default:
                simpledateformat.applyPattern(yyyy_MM_dd_Str);
                break;
        }
        return simpledateformat.format(simpledateformat.parse(date));
    }

    public static String getStringDateFormat(String date, int inputi, int outputi) {
        try {
            Date input = getDateFormat(date, inputi);
            return getStringDateFormat(input, outputi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getStringDateFormat1() {
        return DateTool.getStringDateFormat(new Date(), 1);
    }

    public static String getStringDateFormat5() {
        return DateTool.getStringDateFormat(new Date(), 5);
    }

    /**
     * 输入Date类型的日期与格式代号，以String类型返回需要的格式
     * 
     * @param date
     *            Date 类型的日期
     * @param i
     *            格式类型 可以这样调用DateTool.yyyy_MM_dd
     * */

    public static String getStringDateFormat(Date date, int i) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        switch (i) {
            case 1: // '\001'
                simpledateformat.applyPattern(yyyy_MM_dd_Str);
                break;
            case 2: // '\002'
                simpledateformat.applyPattern(yyyy_M_d_Str);
                break;
            case yy_MM_dd: // '\003'
                simpledateformat.applyPattern(yy_MM_dd_Str);
                break;
            case yy_M_d: // '\004'
                simpledateformat.applyPattern(yy_M_d_Str);
                break;
            case yyyy_MM_dd_HH_mm_ss: // '\005'
                simpledateformat.applyPattern(yyyy_MM_dd_HH_mm_ss_Str);
                break;
            case yyyy_M_d_H_m_s: // '\006'
                simpledateformat.applyPattern(yyyy_M_d_H_m_s_Str);
                break;
            case yy_MM_dd_HH_mm_ss: // '\007'
                simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str);
                break;
            case yy_M_d_H_m_s: // '\b'
                simpledateformat.applyPattern(yy_M_d_H_m_s_Str);
                break;
            case yyyy: //
                simpledateformat.applyPattern(yyyy_Str);
                break;
            case yyyy_MM: //
                simpledateformat.applyPattern(yyyy_MM_Str);
                break;
            case yyyyMMdd: //
                simpledateformat.applyPattern(yyyyMMdd_Str);
                break;
            case yyyyMM: //
                simpledateformat.applyPattern(yyyyMM_Str);
                break;
            case yyyyMMddHHmmss: //
                simpledateformat.applyPattern(yyyyMMddHHmmss_Str);
                break;
            case yyMMddHH:
                simpledateformat.applyPattern(yyMMddHH_Str);
                break;
            case yyyy_MM_dd_T_HH_mm_ss_0Z: //
                simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str2);
                break;
            case HH_mm_ss: //
                simpledateformat.applyPattern(HH_mm_ss_Str);
                break;
            default:
                simpledateformat.applyPattern("yyyy/MM/dd");
                break;
        }

        return simpledateformat.format(date);
    }

    /**
     * 
     * 输入String类型的日期与格式代号，以Date类型返回需要的格式
     * 
     * @param date
     *            String 类型的日期
     * @param i
     *            格式类型 可以这样调用DateTool.yyyy_MM_dd
     * */
    public static Date getDateFormat(String date, int i) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat();
            switch (i) {
                case 1: // '\001'
                    simpledateformat.applyPattern(yyyy_MM_dd_Str);
                    break;
                case 2: // '\002'
                    simpledateformat.applyPattern(yyyy_M_d_Str);
                    break;
                case yy_MM_dd: // '\003'
                    simpledateformat.applyPattern(yy_MM_dd_Str);
                    break;
                case yy_M_d: // '\004'
                    simpledateformat.applyPattern(yy_M_d_Str);
                    break;
                case yyyy_MM_dd_HH_mm_ss: // '\005'
                    simpledateformat.applyPattern(yyyy_MM_dd_HH_mm_ss_Str);
                    break;
                case yyyy_M_d_H_m_s: // '\006'
                    simpledateformat.applyPattern(yyyy_M_d_H_m_s_Str);
                    break;
                case yy_MM_dd_HH_mm_ss: // '\007'
                    simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str);
                    break;
                case yy_M_d_H_m_s: // '\b'
                    simpledateformat.applyPattern(yy_M_d_H_m_s_Str);
                    break;
                case yyyy: //
                    simpledateformat.applyPattern(yyyy_Str);
                    break;
                case yyyy_MM: //
                    simpledateformat.applyPattern(yyyy_MM_Str);
                    break;
                case yyyyMMdd: //
                    simpledateformat.applyPattern(yyyyMMdd_Str);
                    break;
                case yyyyMM: //
                    simpledateformat.applyPattern(yyyyMM_Str);
                    break;
                case yyyyMMddHHmmss:
                    simpledateformat.applyPattern(yyyyMMddHHmmss_Str);
                    break;
                case yyMMddHH:
                    simpledateformat.applyPattern(yyMMddHH_Str);
                    break;
                case yyyy_MM_dd_T_HH_mm_ss_0Z: //
                    simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str2);
                    break;
                case HH_mm_ss: //
                    simpledateformat.applyPattern(HH_mm_ss_Str);
                    break;
                default:
                    simpledateformat.applyPattern(yyyy_MM_dd_Str);
                    break;
            }
            return simpledateformat.parse(date);
        }catch (Exception e){
            e.printStackTrace();
            return new Date();
        }

    }

    /**
     * 输入日期格式代号，以String类型返回需要的格式的当前日期时间
     * 
     * @param i
     *            格式类型 可以这样调用DateTool.yyyy_MM_dd
     * */

    public static String getNowDate(int i) throws Exception {
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        switch (i) {
            case 1: // '\001'
                simpledateformat.applyPattern(yyyy_MM_dd_Str);
                break;
            case 2: // '\002'
                simpledateformat.applyPattern(yyyy_M_d_Str);
                break;
            case yy_MM_dd: // '\003'
                simpledateformat.applyPattern(yy_MM_dd_Str);
                break;
            case yy_M_d: // '\004'
                simpledateformat.applyPattern(yy_M_d_Str);
                break;
            case yyyy_MM_dd_HH_mm_ss: // '\005'
                simpledateformat.applyPattern(yyyy_MM_dd_HH_mm_ss_Str);
                break;
            case yyyy_M_d_H_m_s: // '\006'
                simpledateformat.applyPattern(yyyy_M_d_H_m_s_Str);
                break;
            case yy_MM_dd_HH_mm_ss: // '\007'
                simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str);
                break;
            case yy_M_d_H_m_s: // '\b'
                simpledateformat.applyPattern(yy_M_d_H_m_s_Str);
                break;
            case yyyy: //
                simpledateformat.applyPattern(yyyy_Str);
                break;
            case yyyy_MM: //
                simpledateformat.applyPattern(yyyy_MM_Str);
                break;
            case yyyyMMdd: //
                simpledateformat.applyPattern(yyyyMMdd_Str);
                break;
            case yyyyMM: //
                simpledateformat.applyPattern(yyyyMM_Str);
                break;
            case yyyyMMddHHmmss: //
                simpledateformat.applyPattern(yyyyMMddHHmmss_Str);
                break;
            case yyMMddHH:
                simpledateformat.applyPattern(yyMMddHH_Str);
                break;
            case yyyy_MM_dd_T_HH_mm_ss_0Z: //
                simpledateformat.applyPattern(yy_MM_dd_HH_mm_ss_Str2);
                break;
            case HH_mm_ss: //
                simpledateformat.applyPattern(HH_mm_ss_Str);
                break;
            case HHmmss: //
                simpledateformat.applyPattern(HHmmss_Str);
                break;
            default:
                simpledateformat.applyPattern(yyyy_MM_dd_Str);
                break;
        }
        return simpledateformat.format(new Date());
    }

    /**
     * 
     * 取得系统当前时间，返回类型为Date
     */
    public static Date getNowDate() throws ParseException {
        return new Date();
    }

    /**
     * 获取当前系统日期，返回字符串
     * 
     * @return
     */
    public static String takeNowDateStr(Integer k) {
        return DateTool.getStringDateFormat(new Date(), k);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 取得一天的开始时间
     * 
     * @param date
     *  pattern
     * @return
     * @throws Exception
     */
    public static Date getFristTimeOfDate(Date date) throws Exception {
        String s = DateTool.getStringDateFormat(date, DateTool.yyyy_MM_dd);
        return DateTool.getDateFormat(s + " 00:00:00", DateTool.yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 取得一天的最后时间
     * 
     * @param date
     *  pattern
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static Date getLastTimeOfDate(Date date) throws Exception {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(GregorianCalendar.DATE, 1);
        return getFristTimeOfDate(gc.getTime());
    }

    /**
     * 取得前N天前或后的时间
     * 
     * @param date
     *            日期
     * @param N
     *            date的N天 负数为前N天 正数为后N天
     * @throws Exception
     */
    public static Date getNDayOfDate(Date date, Integer N) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(GregorianCalendar.DATE, N);
        return gc.getTime();
    }

    /**
     * date1与date2相差的天数
     * date1
     * date2
     * 
     * @throws Exception
     */
    public static long getDays(String date1, String date2) throws Exception {
        long days = 0;
        Date dt1 = DateTool.getDateFormat(date1, DateTool.yy_MM_dd);
        Date dt2 = DateTool.getDateFormat(date2, DateTool.yy_MM_dd);
        days = dt1.getTime() - dt2.getTime();
        days = days / THOUSAND / SIXTY / SIXTY / TWENTYFOUR;
        return days;
    }

    /**
     * date1与date2相差的天数
     * date1
     * date2
     * 
     * @throws Exception
     */
    public static long getDays(Date date1, Date date2) throws Exception {
        long days = 0;
        days = date1.getTime() - date2.getTime();
        days = days / THOUSAND / SIXTY / SIXTY / TWENTYFOUR;
        return days;
    }

    /**
     * 取得日期对应的星期
     * 
     * @param date
     */
    public static String getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - 1;
        String[] day = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
        return day[i];
    }

    /**
     * 取得日期对应的星期数字
     * 
     * @param date
     */
    public static int getWeekDayNum(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 取当前时间的秒数
     * */
    public static int getSecond() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.SECOND);

    }

    /**
     * 将指定的天数添加到给定的日历字段中。
     * 例: date=2007.12.1 days=2 return 2007.12.3
     * 例: date=2007.12.1 days=-2 return 2007.11.29
     * 
     * @param date
     *            指定日期
     * @param days
     *            时间量
     * @return 返回修改后的日期
     */
    public static Date addDays(Date date, int days) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.DAY_OF_MONTH, days);
        return gc.getTime();
    }

    /**
     * 将指定的天数添加到给定的日历字段中。
     * 例: date=2007.12.1 weeks=2 return 2007.12.15
     * 例: date=2007.12.1 weeks=-2 return 2007.11.17
     * 
     * @param date
     *            指定日期
     *  days
     *            时间量
     * @return 返回修改后的日期
     */
    public static Date addWeeks(Date date, int weeks) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.WEEK_OF_YEAR, weeks);
        return gc.getTime();
    }

    /**
     * 将指定的天数添加到给定的日历字段中。
     * 例: date=2007.12.1 months=2 return 2008.02.1
     * 例: date=2007.12.1 months=-2 return 2007.10.30
     * 
     * @param date
     *            指定日期
     *  days
     *            时间量
     * @return 返回修改后的日期
     */
    public static Date addMonths(Date date, int months) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MONTH, months);
        return gc.getTime();
    }

    // 装载当前日期所有的周的所有日期
    public static Map<Integer, Date> getWeekMap(Date date) throws Exception {
        // 找出这个更新排班所在的周一
        String searchDay = DateTool.getStringDateFormat(date, DateTool.yyyy_MM_dd);
        int searchDateNum = DateTool.getWeekDayNum(DateTool.getDateFormat(searchDay, 1));
        if (searchDateNum == 0) {
            searchDateNum = SEVEN;
        }
        Date startDay = DateTool.getNDayOfDate(DateTool.getDateFormat(searchDay, 1), -searchDateNum + 1);
        Map<Integer, Date> weekMap = new HashMap<Integer, Date>();
        // 先设置星期一
        weekMap.put(1, startDay);
        for (int i = 1; i <= SEVEN; i++) {
            weekMap.put(i + 1, DateTool.addDays(startDay, i));
        }
        return weekMap;
    }

    public static boolean compareDate(Date date1, Date date2) {
        try {
            return ((getDays(getStringDateFormat(date1, yyyy_MM_dd), getStringDateFormat(date2, yyyy_MM_dd))) >= 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = "";
        if (hour > 0)
            result += hour + "小时";
        if (min > 0)
            result += min + "分";
        if (sec > 0)
            result += sec + "秒";

        return result;
    }

    /**
     * 判断字符串是否符合日期格式
     * 
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        Pattern p = Pattern.compile("([1][9][4-9][0-9])|([2][0-9][0-9][0-9])-" + "(([0]?[0-9])|([1]?[0-2]))-(([0-2]?[0-9])|([3][0-1]))"
                + "[   ](([0-1]?[0-9])|([2]?[0-3])):([0-5]?[0-9]):([0-5]?[0-9])$");
        Matcher m = p.matcher(date);
        boolean b = false;
        b = m.matches();
        return b;
    }

}