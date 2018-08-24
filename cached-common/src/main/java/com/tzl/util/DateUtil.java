package com.tzl.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * <p></p>
 *
 * @author tan
 * @date 2017/3/31
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final String DEFAULT_DATE = "yyyy-MM-dd";

    private static final String DEFAULT_TIME = "HH:mm:ss";

    private static final String DEFAULT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    
    public static final String FORMAT_0 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_1 = "yyyy-MM-dd";
    public static final String FORMAT_2 = "HH:mm:ss";
    public static final String FORMAT_3 = "MM/dd/yyyy HH:mm:ss";
    // Z为时区
    public static final String FORMAT_4 = "yyyy-MM-dd HH:mm:ssZ";
    public static final String FORMAT_5 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String FORMAT_6 = "yyyyMMdd";
    public static final String FORMAT_7 = "yyMMddHHmmss";
    public static final String FORMAT_8 = "yyyyMMddHHmmss";

    
    
    /**
     * 以下方法可以获取线程安全的SimpleDateFormat
     * #####################################################
     */

    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern 日期格式
     * @return
     */
    public static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 日期格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return getSdf(DEFAULT_DATE).format(date);
    }

    /**
     * 返回今天的日期  yyyy-MM-dd
     * @return
     */
    public static String getTodayDateStr(){
        return getSdf("yyyy-MM-dd").format(new Date());
    }

    /**
     * 返回昨天的日期  yyyy-MM-dd
     * @return
     */
    public static String getYesterdayDateStr(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return getSdf("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 返回明天的日期  yyyy-MM-dd
     * @return
     */
    public static String getTomorrowDateStr(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        return getSdf("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 返回明天的日期  yyyy-MM-dd
     * @return
     */
    public static String getNearDate(Date date,int nearCount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,nearCount);
        return getSdf("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 解析成Date
     * @param value
     * @return
     */
    public static Date parse(String value) {
        try {
            if(value != null) {
                return getSdf(DEFAULT_DATE_TIME).parse(value);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 解析成Date
     * @param value
     * @return
     */
    public static Date parseDate(String value) {
        try {
            if(value != null) {
                return getSdf(DEFAULT_DATE).parse(value);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 解析成Date
     * @param value
     * @return
     */
    public static Date parseTime(String value) {
        try {
            if(value != null) {
                return getSdf(DEFAULT_TIME).parse(value);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public static String generateBatchNum() {
        return getSdf("yyMMddHHMMssSSS").format(new Date());
    }
    
    
    /**
     * 按 {@link yyMMddHHmmss} 格式转换符字符串
     * 
     * @param dateStr
     * @return
     * @throws Exception
     */
    public static Date parse2Date(String dateStr) throws Exception {
        return parse2Date(dateStr, FORMAT_7);
    }

    /**
     * 按 {@link yyMMddHHmmss} 格式转换符字符串
     * 
     * @param dataStr
     * @return
     */
    public static Date format4Date(String dateStr) throws Exception {
        return parse2Date(dateStr, FORMAT_3);
    }

    /**
     * 指定格式转换日期字符串
     * 
     * 
     * @param dateStr
     * @param pattern
     *            see {@link DateUtils.FORMAT_X}
     * @return
     * @throws Exception
     */
    public static Date parse2Date(String dateStr, String pattern) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateStr);
    }

    /**
     * 按指定格式转换日期为字符串
     *
     * @param date
     * @param parrent
     * @return 日期字符串
     */
    public static String getStringDate(Date date, String parrent) {
        SimpleDateFormat sdf = new SimpleDateFormat(parrent);
        return sdf.format(date);
    }

    /**
     * utc时间转换为本地时间
     *
     * @param utcTime
     *            eg：20130502T03:32:11Z
     * @return 本地时间Date
     */
    public static Date utc2Local(String utcTime) {
        String utcTimePatten = "yyyyMMdd'T'HH:mm:ss'Z'";
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gpsUTCDate;
    }

    /**
     * 按毫秒数获取天数、小时、分钟、秒
     *
     * @param time
     * @return
     */
    public static String formatByTime(long time) {
        long diff = time / 1000;
        long _m = 60;
        long _h = 60 * _m;
        long _d = 24 * _h;

        long day = diff / _d;
        long h = diff % _d / _h;
        long m = diff % _d % _h / _m;
        long s = diff % _d % _h % _m;
        return day + "天" + h + "小时" + m + "分钟" + s + "秒";
    }

    private static long UTC_GMT_INTERVAL = get1900ToGMTInterval();

    /**
     * 获取1900年 1 月 1 日 00:00:00至1970 年 1 月 1 日 00:00:00之间相隔毫秒数<br>
     * GMT:格林威治标准时间，以英国伦敦郊区皇家格林威治天文台的标准时间。从1970 年 1 月 1 日 00:00:00
     * 
     * @return
     */
    public static long get1900ToGMTInterval() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT_0);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
            System.out.println(dateFormat.getTimeZone());

            Date date = dateFormat.parse("1900-01-01 00:00:00");
            System.out.println(dateFormat.format(date));

            return Math.abs(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * <pre>
     * 将交易库记录的时间戳转换为GMT格式时间显示
     * 1、交易系统的时间是以1900-01-01 00:00:00开始算起的世界统一时间
     * 2、交易库记得的时间格式是1900-01-01 00:00:00起的微秒数
     * 3、因为中国处于东八区，换算时要+8小时
     * 4、GMT时间是以1970-01-01 00：00：00开始算起
     * 5、JDK 的日期库使用GMT时间
     *
     * &#64;param tradeTime 以1900-01-01 00:00:00开始算起微秒数
     * &#64;return
     * &#64;throws Exception
     * </pre>
     */
    public static Date convertTradeTimeToGMT(long tradeTime) {
        // 微秒数转毫秒数
        long seconds = tradeTime / 1000;
        // 取得GMT时间的时间戳
        long gmtTime = seconds - UTC_GMT_INTERVAL;
        Date time = new Date(gmtTime);

        System.out.println(getStringDate(time, FORMAT_0));
        return time;
    }

    public static String formatTradeTimeToGMT(long tradeTime, String format) {
        Date time = convertTradeTimeToGMT(tradeTime);
        return getStringDate(time, format);
    }

    /**
     * 转换Unix时间戳为GMT格式日期
     * 
     * @param unixTimestamp
     * @return
     */
    public static Date convertUnixTimestame(long unixTimestamp) {
        long milliseconds = unixTimestamp * 1000;
        return new Date(milliseconds);
    }
    
    
     /** 
     * 使用参数Format将字符串转为Date 
     */  
    public static Date parse(String strDate, String pattern)  
    {  
        try {
            return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(  
                    pattern).parse(strDate);
        } catch (ParseException e) {
            logger.error(" date parse failed message:{} ",e);
            return  null;
        }  
    }  

    public static Date getMaxTimeStamp(){
        String str = "2037-01-01 00:00:00";
        DateFormat df = new SimpleDateFormat(DEFAULT_DATE_TIME);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
