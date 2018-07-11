package com.project.utils;

/**
 * Created by jome on 2017/12/29.
 */

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MapDistance {

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1 用户经度
     * @param lng1 用户纬度
     * @param lat2 商家经度
     * @param lng2 商家纬度
     * @return
     */
    public static String getDistance(double long1, double lat1, double long2, double lat2) {
        DecimalFormat df   = new DecimalFormat("####.##");
        double a, b, d, sa2, sb2;
        lat1 = rad(lat1);
        lat2 = rad(lat2);
        a = lat1 - lat2;
        b = rad(long1 - long2);

        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2   * EARTH_RADIUS
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));

        return df.format(d);
    }

    public static void main(String[] args) {
        System.out.println(getDistance(Double.valueOf("23.09258460998535"),Double.valueOf("113.40494537353516"),Double.valueOf("23.09192"),Double.valueOf("113.40479")));

    }



}