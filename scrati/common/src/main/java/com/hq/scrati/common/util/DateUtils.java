package com.hq.scrati.common.util;

import com.hq.scrati.common.constants.TimeConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Zale on 2016/11/28.
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils{
    /** 最低年限 */
    public static final int MIN = 1930;
    /**
     * 验证小于当前日期 是否有效
     *
     * @param iYear
     *            待验证日期(年)
     * @param iMonth
     *            待验证日期(月 1-12)
     * @param iDate
     *            待验证日期(日)
     * @return 是否有效
     */
    public static boolean valiDate(int iYear, int iMonth, int iDate) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int datePerMonth;
        if (iYear < MIN || iYear >= year) {
            return false;
        }
        if (iMonth < 1 || iMonth > 12) {
            return false;
        }
        switch (iMonth) {
            case 4:
            case 6:
            case 9:
            case 11:
                datePerMonth = 30;
                break;
            case 2:
                boolean dm = ((iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0))
                        && (iYear > MIN && iYear < year);
                datePerMonth = dm ? 29 : 28;
                break;
            default:
                datePerMonth = 31;
        }
        return (iDate >= 1) && (iDate <= datePerMonth);
    }
    public static class TimeIntervalWrapper{
        private String date;
        private String stTime;
        private String edTime;

        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }
        public String getStTime() {
            return stTime;
        }
        public void setStTime(String stTime) {
            this.stTime = stTime;
        }
        public String getEdTime() {
            return edTime;
        }
        public void setEdTime(String edTime) {
            this.edTime = edTime;
        }
        public String toString(){
            return date+","+stTime+","+edTime;
        }
    }
    /**
     * 是否是同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1,Date date2){
        if(null == date1 || null == date2){
            throw new RuntimeException("输入日期不能为空");
        }
        String d1= DateUtils.format2Date(date1);
        String d2=DateUtils.format2Date(date2);
        //比较是否在同一天
        return d1.equals(d2);
    }

    /**
     * 获取今天开始
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTodayStart(){
        return getDayStart(new Date());
    }

    /**
     * 获取今天结束
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTodayEnd(){
        return getDayEnd(new Date());
    }

    /**
     * 获取某天开始
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String getDayStart(Date date){
        return DateUtils.format2EnViewOnlyDate(date)+" 00:00:00";
    }

    /**
     * 获取某天结束
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String getDayEnd(Date date){
        return DateUtils.format2EnViewOnlyDate(date)+" 23:59:59";
    }

    /**
     * 计算两个yyyymmddhhmmss时间之间的相差秒钟数目
     */
    public static Long dateDiffer(String yyyymmddhhmmss1,String yyyymmddhhmmss2){
        Date date1=parse2StandardDate(yyyymmddhhmmss1);
        Date date2=parse2StandardDate(yyyymmddhhmmss2);
        return Math.abs(date1.getTime()-date2.getTime());
    }

    /**
     * 是否是标准时间格式
     * @param time
     * @return
     */
    public static boolean isStandardTime(String time){
        return !ValidateUtils.isStrEmpty(time) && time.length() == 14;
    }

    /**
     * 获取变化的时间字符串
     * @param timeStr	时间字符串
     * @param timeChange	变化的时间
     * @return
     */
    public static String getTimeStrChange(String timeStr,long timeChange){
        Date date=parse2StandardDate(timeStr);
        date.setTime(date.getTime()+timeChange);
        return format2StandardDate(date);
    }

    /**
     * 获取日期区间中所有的时间
     * @param stDate 开始日期，yyyyMMdd
     * @param edDate 接入日期，yyyyMMdd
     * @return
     */
    public static List<DateUtils.TimeIntervalWrapper> getIntervalBetweenDate(String stDate,String edDate){
        Date stDa=DateUtils.parse2SimpleDate(stDate);
        Date edDa=DateUtils.parse2SimpleDate(edDate);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(stDa);

        List<DateUtils.TimeIntervalWrapper> list=new ArrayList<DateUtils.TimeIntervalWrapper>();
        while(calendar.getTime().getTime()<=edDa.getTime()){
            Date date=calendar.getTime();
            String cDate=DateUtils.format2Date(date);
            String cStD=cDate+"000000";
            String cEdD=cDate+"235959";

            DateUtils.TimeIntervalWrapper tiw=new DateUtils.TimeIntervalWrapper();
            tiw.setStTime(cStD);
            tiw.setEdTime(cEdD);
            tiw.setDate(cDate);
            list.add(tiw);
            calendar.add(Calendar.DATE, 1);
        }
        return list;
    }

    /**
     * 获取日期区间中所有周数据
     * @param stDate 开始日期，yyyyMMdd
     * @param edDate 接入日期，yyyyMMdd
     * @return
     */
    public static List<WeekDateWrapper> getWeekBetweenDate(String stDate,String edDate){
        Date stDa=DateUtils.parse2SimpleDate(stDate);
        Date edDa=DateUtils.parse2SimpleDate(edDate);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(stDa);

        List<WeekDateWrapper> list=new ArrayList<WeekDateWrapper>();

        //当前周
        WeekDateWrapper curWrapper=null;
        //当前周开始时间
        Date curStTime=null;
        //当前周结束时间
        Date curEdTime=null;
        //当前日期
        Date curDate=null;

        while(calendar.getTime().getTime()<=edDa.getTime()){
            if(null == curWrapper){
                curWrapper=new WeekDateWrapper();
            }
            //当前时间
            curDate=calendar.getTime();
            //当前周标示
            String weekKey=getWeekEnView(curDate);
            ReflectionUtils.setValue2Field(weekKey, curWrapper, curDate);
            if(null == curStTime){
                curStTime=curDate;
            }

            if(weekKey.equals(SUNDAY_KEY)){
                curEdTime=curDate;
                curWrapper.setStDate(curStTime);
                curWrapper.setEdDate(curEdTime);
                list.add(curWrapper);

                curWrapper=null;
                curStTime=null;
                curEdTime=null;
            }

            calendar.add(Calendar.DATE, 1);
        }

        if(null!=curWrapper){
            curEdTime=curDate;
            curWrapper.setStDate(curStTime);
            curWrapper.setEdDate(curEdTime);
            list.add(curWrapper);
        }

        return list;
    }


    public static final String SUNDAY_KEY="sunday";
    public static final  String[] WEEK_DAYS_CH = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final  String[] WEEK_DAYS_EN = {SUNDAY_KEY, "monday", "tuesday", "wednesday",
            "thursday", "friday", "saturday"};

    public static String getWeekEnView(Date date) {
        return getWeekView(date, WEEK_DAYS_EN);
    }
    public static String getWeekChView(Date date) {
        return getWeekView(date, WEEK_DAYS_CH);
    }

    public static String getWeekView(Date date,String[] weekDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }



    /**
     * 时间差是否大于条件
     * @param date1
     * @param date2
     * @param condition
     * @return
     */
    public static boolean isDiffTimeOverCondition(Date date1,Date date2,long condition){
        return date1.getTime()-date2.getTime()>condition;
    }

    /**
     * 绝对时间差是否大于条件
     * @param date1
     * @param date2
     * @param condition
     * @return
     */
    public static boolean isDiffTimeOverConditionAbsolute(Date date1,Date date2,long condition){
        return Math.abs(date1.getTime()-date2.getTime())>condition;
    }

    /**
     * 获取前一个月
     * @return
     */
    public static Date getPreMonth(){
        return getPreMonth(new Date());
    }

    /**
     * 获取指定时间的前一个月
     * @param inputDate
     * @return
     */
    public static Date getPreMonth(Date inputDate){
        return getAfterBeforeDate(inputDate, Calendar.MONTH, -1);
    }
    /**
     * 获取指定日期的前一天
     * @param inputDate
     * @return
     */
    public static Date getPreDay(Date inputDate){
        return getAfterBeforeDate(inputDate, Calendar.DATE, -1);
    }

    /**
     * 获取前一天的开始时间
     * @param inputDate
     * @return
     */
    public static String getPreDayStartStr(Date inputDate){
        Date day=getPreDay(inputDate);
        String time=format2Date(day);
        return time+"000000";
    }

    /**
     * 获取前一天的结束时间
     * @param inputDate
     * @return
     */
    public static String getPreDayStartEnd(Date inputDate){
        Date day=getPreDay(inputDate);
        String time=format2Date(day);
        return time+"235959";
    }


    /**
     * 获取前一天
     * @return
     */
    public static Date getPreDay(){
        return getPreDay(new Date());
    }


    /**
     * 获取之前或者之后的日期
     * @param inputDate
     * @param dateType
     * @param num
     * @return
     */
    public static Date getAfterBeforeDate(Date inputDate,int dateType,int num){
        Calendar cal=Calendar.getInstance();
        cal.setTime(inputDate);
        cal.add(dateType, -1);    //得到前一个月
        return cal.getTime();
    }

    /**
     * 获取前几周的星期一
     * @param preNum
     * @return
     */
    public static String getPreWeekMonTime(int preNum){
        return getPreWeekSunTime(new Date(),preNum,Calendar.MONDAY);
    }

    /**
     * 获取前几周的星期日
     * @param preNum
     * @return
     */
    public static String getPreWeekSunTime(int preNum){
        return getPreWeekSunTime(new Date(),preNum,Calendar.SUNDAY);
    }


    /**
     * 获取指定日期前几周的某天
     * @param preNum
     * @return
     */
    public static String getPreWeekSunTime(Date inputDate,int preNum,int week){
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = -preNum;
        cal.add(Calendar.DATE, n*7);
        //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK,week);
        return format2Date(cal.getTime());
    }




    /**
     * 离当前时间相隔多少秒
     * @param inputDate
     * @return
     */
    public static long passTimeSec(Date inputDate){
        Date nowDate=new Date();
        return (nowDate.getTime()-inputDate.getTime())/TimeConstants.SECOND_MILLI;
    }

    //默认的时间列表
    public final static TimePair [] DEFAULT_TIME_PAIR_LIST=new TimePair[]{
            new TimePair(TimeConstants.YEAR_MILLI,"year","yyyy"),
            new TimePair(TimeConstants.MONTH_MILLI,"month","MM"),
            new TimePair(TimeConstants.DAY_MILLI,"day","dd"),
            new TimePair(TimeConstants.HOUR_MILLI,"hour","HH"),
            new TimePair(TimeConstants.MINUTE_MILLI,"min","mm"),
            new TimePair(TimeConstants.SECOND_MILLI,"sec","ss"),
    };

    //默认的格式
    public final static String DEFAULT_TIME_EXPRESS="yyyy年,MM个月,dd天,HH小时,mm分,ss秒";
    //简化的格式
    public final static String SIMPLE_TIME_EXPRESS="yyyy年MM月dd天HH时mm分ss秒";

    /**
     * 格式化时间元素
     * @param element 时间元素
     * @return 格式化后的字符串
     */
    public static String formatTimeElement(TimeElement element){
        return formatTimeElement(element, DEFAULT_TIME_EXPRESS);
    }

    /**
     * 格式化时间元素
     * @param element 时间元素
     * @param timeExpression 时间表达式元素 yyyy MM dd HH mm ss
     * @return 格式化后的字符串
     */
    public static String formatTimeElement(TimeElement element,String timeExpression){

        for(TimePair timePair:DEFAULT_TIME_PAIR_LIST){
            Integer num=(Integer)
                    ReflectionUtils.getValueFromField(timePair.valueField, element);
            if(null == num){
                num=0;
            }
            timeExpression=timeExpression.replaceAll(timePair.desc, String.valueOf(num));
        }

        return timeExpression;
    }

    /**
     * 解析时间元素
     * @param timeDiffMills 时间（毫秒）
     * @return 时间元素字符串
     */
    public static String parseTimeElementAndForamt(long timeDiffMills){
        return parseTimeElementAndForamt(DEFAULT_TIME_EXPRESS, timeDiffMills);
    }

    /**
     * 解析时间元素
     * @param timeExpress 时间表达式，时间表达式元素 yyyy MM dd HH mm ss
     * @param timeDiffMills 时间（毫秒）
     * @return 时间元素字符串
     */
    public static String parseTimeElementAndForamt(String timeExpress,
            long timeDiffMills){
        TimeElement t=parseTimeElement(timeExpress, timeDiffMills);
        return formatTimeElement(t,timeExpress);
    }

    /**
     * 解析时间元素
     * @param timeDiffMills 时间（毫秒）
     * @return 时间元素对象
     */
    public static TimeElement parseTimeElement(long timeDiffMills){
        return parseTimeElement(DEFAULT_TIME_EXPRESS, timeDiffMills);
    }

    /**
     * 解析时间元素
     * @param timeExpress 时间表达式,时间表达式元素 yyyy MM dd HH mm ss
     * @param timeDiffMills 时间（毫秒）
     * @return 时间元素对象
     */
    public static TimeElement parseTimeElement(String timeExpress,long timeDiffMills){
        TimeElement timeElement=new TimeElement();
        long leftMill=timeDiffMills;

        for(TimePair timePair:DEFAULT_TIME_PAIR_LIST){
            if(-1!=timeExpress.indexOf(timePair.desc)){
                int value=(int)(leftMill/timePair.timeKey);
                ReflectionUtils.setFieldValueOnForce(timeElement, value, timePair.valueField);
                leftMill=leftMill-timePair.timeKey*value;
            }
        }

        return timeElement;
    }


    /**
     * 时间对
     * @author Karma
     */
    private static class TimePair{
        private long timeKey;
        private String valueField;
        private String desc;

        public TimePair(long timeKey, String valueField,String desc) {
            super();
            this.timeKey = timeKey;
            this.valueField = valueField;
            this.desc=desc;
        }
    }

    /**
     * 时间元素
     * @author Karma
     *
     */
    public static class TimeElement{
        //秒
        private Integer sec;
        //分
        private Integer min;
        //小时
        private Integer hour;
        //天
        private Integer day;
        //月
        private Integer month;
        //年
        private Integer year;

        public Integer getSec() {
            return sec;
        }
        public void setSec(Integer sec) {
            this.sec = sec;
        }
        public Integer getMin() {
            return min;
        }
        public void setMin(Integer min) {
            this.min = min;
        }
        public Integer getHour() {
            return hour;
        }
        public void setHour(Integer hour) {
            this.hour = hour;
        }
        public Integer getDay() {
            return day;
        }
        public void setDay(Integer day) {
            this.day = day;
        }
        public Integer getMonth() {
            return month;
        }
        public void setMonth(Integer month) {
            this.month = month;
        }
        public Integer getYear() {
            return year;
        }
        public void setYear(Integer year) {
            this.year = year;
        }
    }



    /**
     * 格式化为英文的浏览格式
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format2EnViewDate(){
        return format2EnViewDate(new Date());
    }


    /**
     * 格式化为中文的浏览格式
     * yyyy年MM月dd日 HH点mm分ss秒
     * @param date
     * @return
     */
    public static String format2ChViewDate(Date date){
        SimpleDateFormat CH_VIEW_DATE_FORMAT=new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");
        return formatDate(CH_VIEW_DATE_FORMAT, date);
    }

    /**
     * 格式化为日期yyyyMMdd
     * @param date
     * @return
     */
    public static String format2Date(Date date){
        SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyyMMdd");
        return formatDate(DATE_FORMAT, date);
    }

    /**
     * 格式化为简单的时间 HHmmss
     * @param date
     * @return
     */
    public static String format2OnlyTime(Date date){
        SimpleDateFormat STANDARD_TIME_FORMAT=new SimpleDateFormat("HHmmss");
        return formatDate(STANDARD_TIME_FORMAT, date);
    }

    /**
     * 格式化为简单的时间 MMdd
     * @param date
     * @return
     */
    public static String format2OnlyDate(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMdd");
        return dateFormat.format(date);
    }


    /**
     * 格式化为英文的浏览格式
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String format2EnViewDate(Date date){
        SimpleDateFormat EN_VIEW_DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatDate(EN_VIEW_DATE_FORMAT, date);
    }

    /**
     * 格式化日期 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String format2EnViewOnlyDate(Date date){
        SimpleDateFormat EN_VIEW_ONLY_DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd");
        return formatDate(EN_VIEW_ONLY_DATE_FORMAT, date);
    }


    /**
     * 格式化为中文的浏览格式
     * yyyy年MM月dd日 HH点mm分ss秒
     * @return
     */
    public static String format2ChViewDate(){
        return format2ChViewDate(new Date());
    }

    /**
     * 格式化为标准时间
     * yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String format2StandardDate(Date date){
        SimpleDateFormat STANDARD_DATE_FORMAT=new SimpleDateFormat("yyyyMMddHHmmss");
        return formatDate(STANDARD_DATE_FORMAT, date);
    }

    /**
     * 格式化为标准时间
     * yyyyMMddHHmmss
     * @return
     */
    public static String format2StandardDate(){
        return format2StandardDate(new Date());
    }

    /**
     * 解析为标准时间 yyyyMMddHHmmss
     * @return
     */
    public static Date parse2StandardDate(String dateStr){
        SimpleDateFormat STANDARD_DATE_FORMAT=new SimpleDateFormat("yyyyMMddHHmmss");
        return parseDate(STANDARD_DATE_FORMAT, dateStr);
    }


    /**
     * 解析为标准时间 HHmmss
     * @return
     */
    public static Date parse2StandardTime(String dateStr){
        SimpleDateFormat STANDARD_TIME_FORMAT=new SimpleDateFormat("HHmmss");
        return parseDate(STANDARD_TIME_FORMAT, dateStr);
    }


    /**
     * 解析为En时间yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parse2EnDate(String dateStr){
        SimpleDateFormat EN_VIEW_DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return parseDate(EN_VIEW_DATE_FORMAT, dateStr);
    }

    /**
     * 格式yyyyMMdd
     * @param dateStr
     * @return
     */
    public static Date parse2SimpleDate(String dateStr){
        SimpleDateFormat SIMPLE_DATE_FORMAT=new SimpleDateFormat("yyyyMMdd");
        return parseDate(SIMPLE_DATE_FORMAT, dateStr);
    }


    /**
     * 格式化日期
     * @param dateFormat
     * @param date
     * @return
     */
    public static String formatDate(SimpleDateFormat dateFormat,Date date){
        if(null == dateFormat || null == date){
            return null;
        }
        return dateFormat.format(date);
    }
    /**
     * 解析日期
     * @param dateFormat
     * @return
     */
    public static Date parseDate(SimpleDateFormat dateFormat,String dateStr){
        if(null == dateFormat || ValidateUtils.isStrEmpty(dateStr)){
            return null;
        }
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            ExceptionUtils.newRuntimeException(e);
        }
        return null;
    }

    /**
     * 标准时间转为En时间
     * yyyyMMddHHmmss 转到 yyyy-MM-dd HH:mm:ss
     * @param inputDate
     * @return
     */
    public static String transStandardDate2EnViewDate(String inputDate){
        Date date=parse2StandardDate(inputDate);
        return format2EnViewDate(date);
    }

    /**
     * En时间转为标准时间
     * yyyy-MM-dd HH:mm:ss 转到  yyyyMMddHHmmss
     * @param inputDate
     * @return
     */
    public static String transEnViewDate2StandardDate(String inputDate){
        Date date=parse2EnDate(inputDate);
        return format2StandardDate(date);
    }

    /**
     * 标准时间转为Ch时间
     * @param inputDate
     * @return
     */
    public static String transStandardDate2ChViewDate(String inputDate){
        Date date=parse2StandardDate(inputDate);
        return format2ChViewDate(date);
    }

    public static boolean isBetweenDate(Date inputDate,Date stDate,Date edDate){
        return inputDate.getTime()>=stDate.getTime() && inputDate.getTime()<edDate.getTime();
    }
    /**
     * 是否是工作日 1-5
     * @param inputDate
     * @return
     */
    public static boolean isWorkDate(Date inputDate){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(inputDate);
        int week=calendar.get(Calendar.DAY_OF_WEEK);
        return week == Calendar.MONDAY ||
                week == Calendar.TUESDAY ||
                week == Calendar.WEDNESDAY ||
                week == Calendar.THURSDAY ||
                week == Calendar.FRIDAY;
    }


    /**
     * 将字符串时间转换为数据库时间字符串
     * 格式 yyyy-mm-dd hh24:mi:ss
     * @return
     */
    public static String strDate2SqlDate(String dateStr){
        StringBuilder condition=new StringBuilder();
        condition.append("to_date('");
        condition.append(dateStr);
        condition.append("','yyyy-mm-dd hh24:mi:ss')");
        return condition.toString();
    }

    /**
     * 获取指定日期所在周的周一
     * @Methods Name getMonday
     * @return Date
     */
    public static Date getMonday(Date date){
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_WEEK, 2);//老外将周日定位第一天，周一取第二天
        return cDay.getTime();
    }
    /**
     * 获取指定日期所在周日
     * @Methods Name getSunday
     * @return Date
     */
    public static Date getSunday(Date date){
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        if(Calendar.DAY_OF_WEEK==cDay.getFirstDayOfWeek()){ //如果刚好是周日，直接返回
            return date;
        }else{//如果不是周日，加一周计算
            cDay.add(Calendar.DAY_OF_YEAR, 7);
            cDay.set(Calendar.DAY_OF_WEEK, 1);
            return cDay.getTime();
        }
    }

    /**
     * 得到本月第一天的日期
     * @Methods Name getFirstDayOfMonth
     * @return Date
     */
    public static Date getFirstDayOfMonth(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, 1);
        return cDay.getTime();
    }
    /**
     * 得到本月最后一天的日期
     * @Methods Name getLastDayOfMonth
     * @return Date
     */
    public static Date getLastDayOfMonth(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cDay.getTime();
    }
    /**
     * 得到本季度第一天的日期
     * @Methods Name getFirstDayOfQuarter
     * @return Date
     */
    public Date getFirstDayOfQuarter(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        int curMonth = cDay.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){
            cDay.set(Calendar.MONTH, Calendar.JANUARY);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){
            cDay.set(Calendar.MONTH, Calendar.APRIL);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
            cDay.set(Calendar.MONTH, Calendar.JULY);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            cDay.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cDay.getTime();
    }
    /**
     * 得到本季度最后一天的日期
     * @Methods Name getLastDayOfQuarter
     * @return Date
     */
    public Date getLastDayOfQuarter(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        int curMonth = cDay.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){
            cDay.set(Calendar.MONTH, Calendar.MARCH);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){
            cDay.set(Calendar.MONTH, Calendar.JUNE);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
            cDay.set(Calendar.MONTH, Calendar.AUGUST);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            cDay.set(Calendar.MONTH, Calendar.DECEMBER);
        }
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cDay.getTime();
    }

}
