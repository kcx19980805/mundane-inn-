package com.common.utils.date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 日期处理
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }


    /**
     * 比较两个字符串时间大小
     */
    public static int compareTwoTime(String time1, String time2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        int flagValue = 0;
        try {
            Date date1, date2;
            date1 = simpleDateFormat.parse(time1);
            date2 = simpleDateFormat.parse(time2);
            long millisecond = date1.getTime() - date2.getTime();
            if (millisecond > 0) {
                flagValue = 1;
            } else if (millisecond < 0) {
                flagValue = -1;
            } else if (millisecond == 0) {
                flagValue = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (flagValue);
    }

    /**
     * 比较两个时间差
     * time1>time2=1
     * time1<time2=-1
     * time1=time2=0
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compareTwoTime(Date time1, Date time2) {
        int flagValue = 0;
        try {
            long millisecond = time1.getTime() - time2.getTime();
            if (millisecond > 0) {
                flagValue = 1;
            } else if (millisecond < 0) {
                flagValue = -1;
            } else if (millisecond == 0) {
                flagValue = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (flagValue);
    }

    /**
     * 比较两个时间相差天数
     */
    public static float calculateTimeGapDay(String time1, String time2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        float day = 0;
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(time1);
            date2 = simpleDateFormat.parse(time2);
            long millisecond = date2.getTime() - date1.getTime();
            day = millisecond / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (day);
    }

    /**
     * 比较两个时间相差天数
     */
    public static float calculateTimeGapDay(Date time1, Date time2) {
        float day = 0;
        try {
            Date date1, date2;
            date1 = time1;
            date2 = time2;
            long millisecond = date2.getTime() - date1.getTime();
            day = millisecond / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (day);
    }

    /**
     * 比较两个时间相差小时
     */
    public static double calculatetimeGapHour(String time1, String time2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        double hour = 0;
        try {
            Date date1, date2;
            date1 = simpleDateFormat.parse(time1);
            date2 = simpleDateFormat.parse(time2);
            double millisecond = date2.getTime() - date1.getTime();
            hour = millisecond / (60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hour;
    }

    /**
     * 比较两个时间相差小时
     */
    public static double calculatetimeGapHour(Date date1, Date date2) {
        double hour = 0;
        double millisecond = date2.getTime() - date1.getTime();
        hour = millisecond / (60 * 60 * 1000);
        return hour;
    }

    /**
     * 比较两个时间相差分钟
     */
    public static double calculatetimeGapMinute(String time1, String time2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        double minute = 0;
        try {
            Date date1, date2;
            date1 = simpleDateFormat.parse(time1);
            date2 = simpleDateFormat.parse(time2);
            double millisecond = date2.getTime() - date1.getTime();
            minute = millisecond / (60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return minute;
    }

    /**
     * 比较两个时间相差分钟
     */
    public static double calculatetimeGapMinute(Date date1, Date date2) {
        double minute = 0;
        double millisecond = date2.getTime() - date1.getTime();
        minute = millisecond / (60 * 1000);
        return minute;
    }

    /**
     * 比较两个时间相差秒
     */
    public static double calculatetimeGapSecond(String time1, String time2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        double second = 0;
        try {
            Date date1, date2;
            date1 = simpleDateFormat.parse(time1);
            date2 = simpleDateFormat.parse(time2);
            double millisecond = date2.getTime() - date1.getTime();
            second = millisecond / (1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return second;
    }

    /**
     * 比较两个时间相差秒
     */
    public static double calculatetimeGapSecond(Date date1, Date date2) {
        double second = 0;
        double millisecond = date2.getTime() - date1.getTime();
        second = millisecond / (1000);
        return second;
    }

    /**
     * 时间工具
     *
     * @param ctime 入参
     * @return 返回
     */
    public static String showTime(Date ctime) {
        String r = "";
        if (ctime == null) {
            return r;
        }
        String format = "yyyy-MM-dd HH:mm:ss";

        long nowtimelong = System.currentTimeMillis();

        long ctimelong = ctime.getTime();
        long result = Math.abs(nowtimelong - ctimelong);

        if (result < 60000) {// 一分钟内
            long seconds = result / 1000;
            if (seconds == 0) {
                r = "刚刚";
            } else {
                r = seconds + "秒前";
            }
        } else if (result >= 60000 && result < 3600000) {// 一小时内
            long seconds = result / 60000;
            r = seconds + "分钟前";
        } else if (result >= 3600000 && result < 86400000) {// 一天内
            long seconds = result / 3600000;
            r = seconds + "小时前";
        } else if (result >= 86400000 && result < 1702967296) {// 三十天内
            long seconds = result / 86400000;
            r = seconds + "天前";
        } else {// 日期格式
            SimpleDateFormat df = new SimpleDateFormat(format);
            r = df.format(ctime);
        }
        return r;
    }

    /**
     * 根据对应语言-显示对应格式
     * languageCode:国际语言编码：en zh
     * date:需处理的日期
     */
    public static String formatDateByObject(String languageCode, Object date) {
        if (StringUtils.isBlank(languageCode) || date == null || StringUtils.isBlank(date.toString())) {
            return "";
        } else {
            return DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(languageCode)).format(date);
        }

    }

    /**
     * 根据对应语言-显示对应格式
     * languageCode:国际语言编码：en zh
     * date:需处理的日期
     * 注：通过sql语句查询出来的date不能转换成Date类型来调用此方法 应直接调用用formatDateByObject方法
     */
    public static String formatDateByDate(String languageCode, Date date) {
        if (StringUtils.isBlank(languageCode) || date == null || StringUtils.isBlank(date.toString())) {
            return "";
        } else {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(languageCode));
            return df.format(date);
        }
    }

    /**
     * 获取当前时间 默认返回 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间字符串
     */
    public static String nowTime(String pattern) {
        //return new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern).format(new Date());
        return localDateTimeToString(LocalDateTime.now(), StringUtils.isBlank(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern);
    }

    /**
     * 获取当前日期 默认返回 yyyy-MM-dd
     *
     * @return 当前日期字符串
     */
    public String today(String pattern) {
        //return new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyyy-MM-dd" : pattern).format(new Date());
        return localDateToString(java.time.LocalDate.now(), StringUtils.isBlank(pattern) ? "yyyy-MM-dd" : pattern);
    }

    /**
     * 日期格式化  Date 转 String
     * 默认返回 yyyy-MM-dd HH:mm:ss
     *
     * @param date    日期 必填
     * @param pattern 转换格式
     * @return 返回格式后日期
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            return new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern).format(date);
        }
    }

    /**
     * String 转 Date
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String date, String pattern) {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(pattern)) {
            return null;
        } else {
//            try {
//                return new SimpleDateFormat(pattern).parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//                return null;
//            }
            DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
            return fmt.parseLocalDateTime(date).toDate();

        }
    }

    /**
     * String 转 LocalDate
     *
     * @param date    待转换的字符串 必填
     * @param pattern 转换格式 默认为yyyy-MM-dd
     * @return
     */
    public static java.time.LocalDate stringToLocalDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            return java.time.LocalDate.parse(date, java.time.format.DateTimeFormatter.ofPattern(StringUtils.isBlank(pattern) ? "yyyy-MM-dd" : pattern));
        }
    }

    /**
     * String 转 LocalDateTime
     *
     * @param date    待转换的字符串 必填
     * @param pattern 转换格式 默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            return LocalDateTime.parse(date, java.time.format.DateTimeFormatter.ofPattern(StringUtils.isBlank(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern));
        }
    }

    /**
     * String 转 LocalTime
     *
     * @param date    待转换的字符串 必填
     * @param pattern 转换格式 默认为HH:mm:ss
     * @return
     */
    public static LocalTime stringToLocalTime(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            return LocalTime.parse(date, java.time.format.DateTimeFormatter.ofPattern(StringUtils.isBlank(pattern) ? "HH:mm:ss" : pattern));
        }
    }

    /**
     * LocalDate 转 String
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String localDateToString(java.time.LocalDate localDate, String pattern) {
        return localDate.format(java.time.format.DateTimeFormatter.ofPattern(StringUtils.isBlank(pattern) ? "yyyy-MM-dd" : pattern));
    }

    /**
     * LocalDateTime 转 String
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(java.time.format.DateTimeFormatter.ofPattern(StringUtils.isBlank(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern));
    }

    /**
     * LocalTime 转 String
     *
     * @param localTime
     * @param pattern
     * @return
     */
    public static String localTimeToString(LocalTime localTime, String pattern) {
        return localTime.format(java.time.format.DateTimeFormatter.ofPattern(StringUtils.isBlank(pattern) ? "HH:mm:ss" : pattern));
    }

    /**
     * LocalDate 转 Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(java.time.LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalDateTime 转 LocalDate
     *
     * @param localDateTime
     * @return
     */
    public static java.time.LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    /**
     * LocalDateTime 转 LocalTime
     *
     * @param localDateTime
     * @return
     */
    public static LocalTime localDateTimeToLocalTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }

    /**
     * Date 转 LocalDate
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date
     * @return
     */
    public static java.time.LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将 Date 转 LocalDateTime
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将 Date 转 LocalTime
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date
     * @return
     */
    public static LocalTime dateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalTime();
    }

    /**
     * 计算两个LocalDateTime yyyy-MM-dd HH:mm:ss 之间的 时间间隔
     *
     * @param localDateTime1
     * @param localDateTime2
     * @return Map<String, Object>
     * millis:毫秒差
     * seconds:秒差
     * minutes:分钟差
     * hours:小时差
     * days:天数差
     * weeks:周差
     * months:月份差
     * quarters:季度差
     * years:年份差
     */
    public static Map<String, Object> minusToLocalDateTime(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        Duration duration = Duration.between(localDateTime1, localDateTime2);
        Map<String, Object> map = new HashMap<>(16);
        //毫秒差
        map.put("millis", duration.toMillis());
        //秒差
        map.put("seconds", duration.toMillis() / 1000);
        //分钟差
        map.put("minutes", duration.toMinutes());
        //小时差
        map.put("hours", duration.toHours());
        //天数差
        map.put("days", duration.toDays());
        //周差
        map.put("weeks", duration.toDays() / 7);
        Map<String, Object> map1 = minusToLocalDate(localDateTime1.toLocalDate(), localDateTime1.toLocalDate());
        //季度差
        map.put("quarters", map1.get("quarters"));
        //月份差
        map.put("months", map1.get("months"));
        //年份差
        map.put("years", map1.get("years"));
        return map;
    }

    /**
     * 计算两个LocalTime HH:mm:ss 之间的 时间间隔
     *
     * @param localTime1
     * @param localTime2
     * @return Map<String, Object>
     * millis:毫秒差
     * seconds:秒差
     * minutes:分钟差
     * hours:小时差
     */
    public static Map<String, Object> minusToLocalTime(LocalTime localTime1, LocalTime localTime2) {
        Duration duration = Duration.between(localTime1, localTime2);
        Map<String, Object> map = new HashMap<>(16);
        //毫秒差
        map.put("millis", duration.toMillis());
        //秒差
        map.put("seconds", duration.toMillis() / 1000);
        //分钟差
        map.put("minutes", duration.toMinutes());
        //小时差
        map.put("hours", duration.toHours());
        return map;
    }

    /**
     * 计算两个LocalDate yyyy-MM-dd 之间的 时间间隔
     *
     * @param localDate1 小日期
     * @param localDate2 大日期
     * @return Map<String, Object>
     * days:天数差
     * weeks:周差
     * months:月份差
     * quarters:季度差
     * years:年份差
     */
    public static Map<String, Object> minusToLocalDate(java.time.LocalDate localDate1, java.time.LocalDate localDate2) {
        Period period = localDate1.until(localDate2);
        Map<String, Object> map = new HashMap<>(16);
        //天数差
        map.put("days", period.getDays());
        //周差
        map.put("weeks", period.getDays() / 7);
        //月份差
        map.put("months", period.getMonths());
        // 季度差
        map.put("quarters", period.getMonths() / 3);
        //年份差
        map.put("years", period.getYears());
        return map;
    }


    public static void main(String[] args) {
        String a ="2021-01-28 14:00:00";
        String b ="2021-01-28 14:15:00";
        System.out.println(minusToLocalDateTime(stringToLocalDateTime(a,""),stringToLocalDateTime(b,"")));
    }
}
