package com.hq.app.olaf.util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类
 *
 * @description : 对日期时间的操作封装
 */
public class DateUtil {
    private static ThreadLocal<SimpleDateFormat> sdfThreadLocal = new ThreadLocal<SimpleDateFormat>();

    private static ThreadLocal<SimpleDateFormat> hmSdfThreadLocal = new ThreadLocal<SimpleDateFormat>();

    private final static long ratio = 24 * 60 * 60 * 1000;

    private static SimpleDateFormat getSdf() {
        SimpleDateFormat sdf = sdfThreadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            sdfThreadLocal.set(sdf);
        }
        return sdf;
    }

    private static SimpleDateFormat getSdf1() {
        SimpleDateFormat sdf = hmSdfThreadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            hmSdfThreadLocal.set(sdf);
        }
        return sdf;
    }

    private static SimpleDateFormat getSdf2() {
        SimpleDateFormat sdf = hmSdfThreadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            hmSdfThreadLocal.set(sdf);
        }
        return sdf;
    }

    private static SimpleDateFormat getChSdf() {
        SimpleDateFormat sdf = hmSdfThreadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
            hmSdfThreadLocal.set(sdf);
        }
        return sdf;
    }

    private static SimpleDateFormat getNoYearChSdf() {
        SimpleDateFormat sdf = hmSdfThreadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("MM月dd日", Locale.getDefault());
            hmSdfThreadLocal.set(sdf);
        }
        return sdf;
    }

    // 时间差
    private static long span = 0;

    public static long getSpan() {
        return span;
    }

    public static void setSpan(long span) {
        DateUtil.span = span;
    }

    // 获取一个日期对象
    public static Date getDate() {
        return new Date();
    }

    // 获取一个日历对象
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    // 获取系统时间
    public static Date getSystemDateTime() {
        return new Date(getDate().getTime() + span);
    }

    // 获取格式化后的系统时间
    public static String getSystemDateTimeString() {
        return getSdf().format(getSystemDateTime());
    }

    public static String getSystemDateTimeHHMMString() {
        return getSdf1().format(getSystemDateTime());
    }

    public static String getSystemDateTimeYYMMDDString() {
        return getSdf2().format(getSystemDateTime());
    }

    public static Calendar getSystemCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getSystemDateTime());
        return cal;
    }

    public static Calendar getSystemCalendar(Calendar calendar) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getSystemDateTime(calendar.getTime()));
        return cal;
    }

    public static Date getSystemDateTime(Date date) {
        return new Date(date.getTime() - span);
    }

    public static String getDateString(long dateTime, String formatStr) {
        return getSimpleDateFormat(formatStr).format(dateTime);
    }

    public static String getDateString(Date dateTime, String formatStr) {
        return getSimpleDateFormat(formatStr).format(dateTime);
    }

    public static String getDateString(Calendar dateTime, String formatStr) {
        return getSimpleDateFormat(formatStr).format(dateTime.getTime());
    }

    public static SimpleDateFormat getSimpleDateFormat(String formatStr) {
        return new SimpleDateFormat(formatStr, Locale.getDefault());
    }

    public static String getCalendarStringByOffset(String oldTime, int Offset) {
        Date oldDate = getSimpleDateByParse(oldTime);
        Date value = new Date(oldDate.getTime() - Offset * ratio);
        return getDateString(value.getTime(), "yyyy-MM-dd HH:MM:SS");
    }

    public static String getDateStringByOffset(String oldTime, int Offset) {
        Date oldDate = getDateByParse(oldTime);
        Date value = new Date(oldDate.getTime() - Offset * ratio);
        return getDateString(value.getTime(), "yyyy-MM-dd");
    }

    public static String getCalendarStringByOffset(int calendarFieldValue, int Offset) {
        Calendar calendar = getCalendar();
        calendar.add(calendarFieldValue, Offset);
        if (calendarFieldValue == Calendar.WEEK_OF_YEAR) {
           // calendar.add(Calendar.DAY_OF_YEAR, -((calendar.get(Calendar.DAY_OF_WEEK) == 1) ? 8 : calendar.get(Calendar.DAY_OF_WEEK)) + 2);
        }
        return getDateString(calendar.getTime(), "yyyy-MM-dd 00:00:00");
    }

    public static String getSimpleDateString(Date dateTime) {
        return getSdf().format(dateTime);
    }

    public static String getSimpleDate2String(Date dateTime) {
        return getSdf2().format(dateTime);
    }

    public static String getSimpleChDateString(Date dateTime) {
        return getChSdf().format(dateTime);
    }

    public static String getSpecialDateString(Calendar calendar) {
        Calendar calendar1 = getCalendar();
        if (calendar1.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            return getNoYearChSdf().format(calendar.getTime());
        } else {
            return getChSdf().format(calendar.getTime());
        }
    }


    public static String getSimpleDateString(String dateTime) {
        return getSdf().format(getSimpleDateByParse(dateTime));
    }

    public static Date getSimpleDateByParse(String dateTime) {
        Date date = null;
        try {
            date = getSdf().parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateByParse(String dateTime) {
        Date date = null;
        try {
            date = getSdf2().parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getSimpleDateByParse(String dateTime, String formatStr) {
        Date date = null;
        try {
            date = getSimpleDateFormat(formatStr).parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 从yyyy-MM-dd HH:mm:ss 格式的时间里面取出 ddHHmmss
     */
    public static String getHHmmssByDateTime(String dateTime) {
        String[] datesplite = dateTime.split(" ");
        String[] before = datesplite[0].split("-");
        String[] after = datesplite[1].split(":");

        StringBuilder builder = new StringBuilder();
        builder.append(before[2]).append(after[0]).append(after[1]).append(after[2]);
        return builder.toString();
    }

    public static int getAge(String birthday) throws Exception {
        return getAge(getDateByParse(birthday));


    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth 
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth 
                age--;
            }
        }

        return age;
    }

    public static long getMinDate(long maxDate) {
        Date date = new Date(maxDate);
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        date = calendar.getTime();
        return date.getTime();
    }

    public static long getMaxDate(long minDate) {
        Date date = new Date(minDate);
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MONTH, 1);//月份减一
        date = calendar.getTime();
        return date.getTime();
    }

    public static String fomatDateByString(String time,String oldFormat,String newFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(oldFormat);//设置一个时间转换器
        SimpleDateFormat format = new SimpleDateFormat(newFormat);
        Date d = null;
        try {
            d = sf.parse(time);//将字符串s通过转换器转换为date类型
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(d);
    }

    public static String formatDateStr(String dateStr) {
        String result = "{0}-{1}-{2}";
        return MessageFormat.format(result, new Object[]{dateStr.substring(0, 4), dateStr.substring(4, 6), dateStr.substring(6, 8)});
    }

    public static String formatTimeStr(String timeStr) {
        String result = "{0}:{1}:{2}";
        return MessageFormat.format(result, new Object[]{ timeStr.substring(0, 2), timeStr.substring(2, 4), timeStr.substring(4, 6)});
    }

    public static String formatDateTimeStr(String dateTimeStr) {
        String result = "{0}-{1}-{2} {3}:{4}:{5}";
        return MessageFormat.format(result, new Object[]{dateTimeStr.substring(0, 4), dateTimeStr.substring(4, 6), dateTimeStr.substring(6, 8), dateTimeStr.substring(8, 10), dateTimeStr.substring(10, 12), dateTimeStr.substring(12, 14)});
    }
}
