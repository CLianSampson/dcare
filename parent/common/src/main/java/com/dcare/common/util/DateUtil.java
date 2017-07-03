package com.dcare.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {

    public static final String TIME_NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";
    public static final String TIME_NORMAL_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSSSSS_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDD_FORMAT = "yyyyMMdd";
    public static final String YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String HHMMSS_FORMAT = "HH:mm:ss";
    public static final String YYYY_MM_FORMAT = "yyyy-MM";

    public static final String SPLIT = "-";
    
    public static final Date stringToDate(String dateTimeString) {
        SimpleDateFormat formattxt = new SimpleDateFormat(DateUtil.TIME_NORMAL_FORMAT);
        Date date = null;
        try {
            if (null != dateTimeString) {
                date = formattxt.parse(dateTimeString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    //将时间转换成只有时分秒的格式
    public static final String getHourMinuteSecond(Date date) {
    	
    	DateFormat dateFormat  = DateFormat.getTimeInstance();
    	String string = dateFormat.format(date);
    	
    	
    	return string;
	
	}

    public static final Date stringToDateFormat(String dateTimeString, String format) {
        SimpleDateFormat formattxt = new SimpleDateFormat(format);
        Date date = null;
        try {
            if (null != dateTimeString) {
                date = formattxt.parse(dateTimeString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatCurrentTime(Date date, String format) {
        String rtv = null;
        if (null != date) {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(date);
        }

        return rtv;
    }

    public static String formatCurrentTime(long timeStamp, String format) {
        String rtv = null;
        if (timeStamp > 0) {
            Date date = new Date(timeStamp);
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(date);
        }

        return rtv;
    }

    /**
     * 时间分差
     */
    public static long getMinute(Date startDate) {
        Date nowDate = new Date();

        long minute = 0;
        long dift = nowDate.getTime() - startDate.getTime();
        minute = dift / 1000 / 60;
        return minute;
    }

    public static String getPath(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        return f.format(date);
    }

    public static String customDate(Date date, String custom) {
        SimpleDateFormat f = new SimpleDateFormat(custom);
        return f.format(date);
    }

    /**
     * 根据时间差和指定时间  计算出日期
     */
    public static Date getDateByTimeLag(Integer minute, Date startDate, String format) {
        long dift = minute * 1000 * 60;
        long endTimeStamp = startDate.getTime() + dift;
        SimpleDateFormat formattxt = new SimpleDateFormat(format);
        Date endDate = null;
        try {
            endDate = formattxt.parse(formattxt.format(endTimeStamp)); // 时间戳转换成时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDate;
    }


    /**
     * 获取时间戳毫秒级字符串
     */
    public static String getTimeStampStr_YYYYMMDDHHMMSSSSS_FORMAT() {
        SimpleDateFormat f = new SimpleDateFormat(DateUtil.YYYYMMDDHHMMSSSSS_FORMAT);
        String now = f.format(new Date());
        return now;
    }

    /**
     * 获取时间戳秒级字符串
     */
    public static String getTimeStampStr_YYYYMMDDHHMMSS_FORMAT() {
        SimpleDateFormat f = new SimpleDateFormat(DateUtil.YYYYMMDDHHMMSS_FORMAT);
        String now = f.format(new Date());
        return now;
    }
    
    //获取指定日期的时分秒   然后转换成当前日期的时间戳
    public static long getTimeStamp(Date date) throws ParseException {

        Date nowDate = new Date();

        DateFormat dateFormat = DateFormat.getDateInstance();// 日期格式，精确到日

        DateFormat timeFormat = DateFormat.getTimeInstance();// 只显示出时分秒

        DateFormat datetimeFormat = DateFormat.getDateTimeInstance();// 可以精确到时分秒

        return datetimeFormat.parse(dateFormat.format(nowDate) + " " + timeFormat.format(date)).getTime();
    }

    public static String getCurrentDateTime_TIME_NORMAL_FORMAT() {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat(TIME_NORMAL_FORMAT);
        return f.format(d);
    }

    public static String getDateStr_YYYYMMDD_FORMAT() {
        SimpleDateFormat f = new SimpleDateFormat(DateUtil.YYYYMMDD_FORMAT);
        String now = f.format(new Date());
        return now;
    }
    
    /***
     * 
     * getDateStr_YYYYMMDD_FORMAT:(指定日期格式化). <br/>
     * @author houdong
     * @param date
     * @return
     * @since JDK 1.7
     */
    public static String getDateStr_YYYYMMDD_FORMAT(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DateUtil.YYYYMMDD_FORMAT);
        String now = f.format(date);
        return now;
    }
    
    public static String getDateStr_YYYYMMDD_HH_MM_FORMAT(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DateUtil.TIME_NORMAL_MINUTE_FORMAT);
        String now = f.format(date);
        return now;
    }

    public static String getDateStr_YYYY_MM_DD_FORMAT() {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat(YYYY_MM_DD_FORMAT);
        return f.format(d);
    }
    
    public static String getDateStr_YYYY_MM_DD_FORMAT(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(YYYY_MM_DD_FORMAT);
        return f.format(date);
    }
    
    public static String getDateStr_YYYY_MM_FORMAT(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(YYYY_MM_FORMAT);
        return f.format(date);
    }
    
    public static String getDateStr_HH_MM_FORMAT(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(HHMMSS_FORMAT);
        return f.format(date);
    }
    
    public static String getTomorrowDateStr_YYYY_MM_DD_FORMAT() {
        Date d = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        SimpleDateFormat f = new SimpleDateFormat(YYYY_MM_DD_FORMAT);
        return f.format(d);
    }
	/**
	 * 
	 * @描述:根据开始时间和结束时间返回时间段内的时间集合 
	 * @方法名: getDatesBetweenTwoDate
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @返回类型 List<Date>
	 * @创建人 fugang
	 * @创建时间 2016年10月19日上午10:08:24
	 * @修改备注
	 */
	 public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
	        List<Date> lDate = new ArrayList<Date>();  
	        lDate.add(beginDate);// 把开始时间加入集合  
	        Calendar cal = Calendar.getInstance();  
	        // 使用给定的 Date 设置此 Calendar 的时间  
	        cal.setTime(beginDate);  
	        boolean bContinue = true;  
	        while (bContinue) {  
	            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
	            cal.add(Calendar.DAY_OF_MONTH, 1);  
	            // 测试此日期是否在指定日期之后  
	            if (endDate.after(cal.getTime())) {  
	                lDate.add(cal.getTime());  
	            } else {  
	                break;  
	            }  
	        }  
	        lDate.add(endDate);// 把结束时间加入集合  
	        return lDate;  
	    } 
	 
    /*计算两个日期之间的天数*/
    public static long getDayNumBetweenDate(Date startDate, Date endDate) {
        SimpleDateFormat f = new SimpleDateFormat(YYYY_MM_DD_FORMAT);

        String startDateStr = f.format(startDate);
        String endDateStr = f.format(endDate);

        long dayNum = 0;
        try {
            Date start = f.parse(startDateStr);
            Date end = f.parse(endDateStr);

            long startTimeStamp = start.getTime();
            long endTimeStamp = end.getTime();

            dayNum = (endTimeStamp - startTimeStamp) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayNum;
    }

    /*根据今天算出明天*/
    public static Date getTomorrowDate_TIME_NORMAL_FORMAT(Date today) {
        Date tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000);
        return tomorrow;
    }
    
    //根据今天算明天开始时间
    public static Date getTomorrowStartTime(Date today) {
    	 Calendar calendar = new GregorianCalendar();
    	 
 	     calendar.setTime(today);
 	     calendar.set(Calendar.HOUR_OF_DAY,0);
 	     calendar.set(Calendar.MINUTE,0);
 	     calendar.set(Calendar.SECOND,0);
 	     calendar.set(Calendar.MILLISECOND,0);
 	     
         return calendar.getTime();
	}
    
    //根据今天算昨天
    public static Date getYesterDate(Date today) {
    	
    	 Calendar calendar = new GregorianCalendar();
    	 calendar.setTime(today);
    	 calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
    	 
    	 Date date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	 String dateString = formatter.format(date);
    	 return date;
	}
    
    //根据今天算昨天最后时间23：59：59
    public static Date getYesterdayEndTime(Date today) {
    	 Calendar calendar = new GregorianCalendar();
 
 	     calendar.setTime(today);

 	     calendar.set(Calendar.HOUR_OF_DAY,23);
 	     calendar.set(Calendar.MINUTE,59);
 	     calendar.set(Calendar.SECOND,59);
 	     calendar.set(Calendar.MILLISECOND,999);
 	     
 	     return calendar.getTime();
	}
    

    public static Boolean checkTimePeriods(Date date, Date startDate, Date endDate) {
        String startDateStr = formatCurrentTime(startDate, HHMMSS_FORMAT);
        String endDateStr = formatCurrentTime(endDate, HHMMSS_FORMAT);
        Date startDateFormat = stringToDateFormat(startDateStr, HHMMSS_FORMAT);
        Date endDateFormat = stringToDateFormat(endDateStr, HHMMSS_FORMAT);

        Boolean result = false;
        if (date.before(endDateFormat) && date.after(startDateFormat)) {
            result = true;
        }
        return result;
    }

    /**
     * 根据两个时间获得时间分差
     */
    public static long getMinute(Date startDate, Date endDate) {
        String startDateStr = formatCurrentTime(startDate, HHMMSS_FORMAT);
        String endDateStr = formatCurrentTime(endDate, HHMMSS_FORMAT);
        Date startDateFormat = stringToDateFormat(startDateStr, HHMMSS_FORMAT);
        Date endDateFormat = stringToDateFormat(endDateStr, HHMMSS_FORMAT);

        long minute;
        long dift = endDateFormat.getTime() - startDateFormat.getTime();
        minute = dift / 1000 / 60;
        return minute;
    }
    

    public static Date parseYYYYMMDD(String dateStr){
    	return stringToDateFormat(dateStr, YYYY_MM_DD_FORMAT);
    }

    /**
     * 
     * verifyDateFormat: 校验字符串是否符合指定日期格式
     * @author cl
     * @param dateString
     * @param dateFormat
     * @return
     * @since JDK 1.7
     */
    public static boolean verifyDateFormat(String dateString,String dateFormat){
    	boolean rtv  = true;
    	
    	SimpleDateFormat format=new SimpleDateFormat(dateFormat);

    	try{
    		format.parse(dateString);
    	} 
    	catch (ParseException e) {
    		rtv=false;
    	}
    	
    	return rtv;
    }

    /**
     * 
     * getFristDateForMoth: 获取当月第一天日期
     * @author cl
     * @return  String
     * @since JDK 1.7
     */
	public static String getFristDateForMoth(){
		 Calendar cal = Calendar.getInstance(); 
		  cal.setTime(new Date()); 
		  cal.set(Calendar.DAY_OF_MONTH, 1); 
		  return  new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(cal.getTime());
	}
	

	
	/**
	 * 
	 * getLastDateForMoth: 获取当月最后一天日期
	 * @author cl
	 * @return
	 * @since JDK 1.7
	 */
	public static String getLastDateForMoth(){
		 Calendar cal = Calendar.getInstance(); 
		  cal.setTime(new Date()); 
		  cal.set(Calendar.DAY_OF_MONTH, 1); 
		  cal.roll(Calendar.DAY_OF_MONTH, -1);  
		  return  new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(cal.getTime());
	}
	
	
	/**
	 * 
	 * getLastDateForMoth: 获取指定月最后一天日期
	 * @author houdong
	 * @return
	 * @since JDK 1.7
	 */
	public static String getLastDateOfMonth(String month){
		  SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM");  
		  Calendar cal = Calendar.getInstance(); 
		  try {
			cal.setTime(mf.parse(month));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  cal.roll(Calendar.DAY_OF_MONTH, -1);  
		  return  new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}
	
	/**将时间转换成字符串   tip: 是时间   不是日期时间
	 * @param date
	 * @return str
	 * @throws ParseException
	 * 2016年5月25日 上午9:42:56
	 */
	public static String getTimeToStr(Date  date) throws ParseException{
		SimpleDateFormat formattxt = new SimpleDateFormat( DateUtil.HHMMSS_FORMAT);
		return formattxt.format(date);
	}
	
	public static Date getDate_YYYY_MM_DD_FORMAT(){
    	String dateStr = DateUtil.formatCurrentTime(new Date(), DateUtil.YYYY_MM_DD_FORMAT);
        Date date = DateUtil.stringToDateFormat(dateStr, DateUtil.YYYY_MM_DD_FORMAT);
        return date;
    }

    /**
     * add by James on 2017.02.24
     * 获取调度的开始日期时间
     */
    public static Date getScheduleDateTime(Date scheduleDate, Date startTime){
        if (StringUtil.isNullOrBlank(scheduleDate)) {
            return null;
        }
        if (StringUtil.isNullOrBlank(startTime)) {
            return null;
        }
        String scheduleDateStr = DateUtil.formatCurrentTime(scheduleDate, DateUtil.YYYY_MM_DD_FORMAT);
        String scheduleTimeStr = DateUtil.formatCurrentTime(startTime, DateUtil.HHMMSS_FORMAT);
        String dataTimeStr = scheduleDateStr + " " + scheduleTimeStr;
        Date scheduleDateTime = DateUtil.stringToDateFormat(dataTimeStr, DateUtil.TIME_NORMAL_FORMAT);
        return scheduleDateTime;
    }
    
    
    
    /***
     * 
     * getBetweenMonths:(获取相差的月份). <br/>
     * @author houdong
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     * @since JDK 1.7
     */
   
    public static List<String> getBetweenMonths(String minDate, String maxDate) {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		try {
			min.setTime(sdf.parse(minDate));
			max.setTime(sdf.parse(maxDate));
		} catch (ParseException e) {
			e.printStackTrace();	
		}
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
		 result.add(sdf.format(curr.getTime()));
		 curr.add(Calendar.MONTH, 1);
		}

		return result;
	}
    
    /** 
     * 判断某一时间是否在一个区间内 
     *  
     * @param sourceTime 
     *            时间区间,半闭合,如[10:00-20:00) 
     * @param curTime 
     *            需要判断的时间 如10:00 
     * @return  
     * @throws IllegalArgumentException 
     */  
    public static boolean isInTime(String sourceTime, String curTime) {  
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {  
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);  
        }  
        if (curTime == null || !curTime.contains(":")) {  
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);  
        }  
        String[] args = sourceTime.split("-");  
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
        try {  
            long now = sdf.parse(curTime).getTime();  
            long start = sdf.parse(args[0]).getTime();  
            long end = sdf.parse(args[1]).getTime();  
            if (args[1].equals("00:00")) {  
                args[1] = "24:00";  
            }  
            if (end < start) {  
                if (now >= end && now < start) {  
                    return false;  
                } else {  
                    return true;  
                }  
            }   
            else {  
                if (now >= start && now < end) {  
                    return true;  
                } else {  
                    return false;  
                }  
            }  
        } catch (ParseException e) {  
            e.printStackTrace();  
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);  
        }  
      
    }
    
}
