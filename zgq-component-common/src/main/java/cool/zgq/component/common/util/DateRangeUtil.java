package cool.zgq.component.common.util;

import cool.zgq.component.common.common.ErrorCode;
import cool.zgq.component.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 12:20
 * @Description 日期计算工具类
 */
public class DateRangeUtil {

    /**
     * @param currentDate
     * @param startDate
     * @param endDate
     * @return < 0 日期在在 时间范围之前； 0 日期在在范围之中 ； > 0 日期在在范围之后
     * ---------------（-2）----[startDate------------（0）---------endDate]-----------（2）——
     * ---------------（-1）----[startDate------------（0）-------------------------
     * ---------------------（0）-------------------endDate]------------1---
     */
    public static Integer judgeDateInRange(Date currentDate, Date startDate, Date endDate) {
        if (startDate != null & endDate != null) {
            if (startDate.compareTo(endDate) > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "开始时间不可大于结束时间");
            }
        }
        Integer a = 1;
        Integer b = -1;
        if (startDate != null) {
            a = currentDate.compareTo(getTodayStartTime(startDate)) < 0 ? -1 : 1;
        }
        if (endDate != null) {
            b = currentDate.compareTo(getTodayEndTime(endDate)) < 0 ? -1 : 1;
        }

        return a + b;
    }

    /**
     * currentDate 是否在 startDate 之后
     *
     * @param currentDate
     * @param startDate
     * @return
     */
    public static boolean isAfter(Date currentDate, Date startDate) {
        if (currentDate == null) {
            return false;
        }
        return getTodayStartTime(currentDate).compareTo(getTodayStartTime(startDate)) > 0;
    }

    /**
     * 判断  currentDate 是否和 startDate 相同
     *
     * @param currentDate
     * @param startDate
     * @return
     */
    public static boolean isEquals(Date currentDate, Date startDate) {
        if (currentDate == null) {
            return false;
        }
        return getTodayStartTime(currentDate).compareTo(getTodayStartTime(startDate)) == 0;
    }

    /**
     * currentDate 是否在 endDate 之前
     *
     * @param currentDate
     * @param endDate
     * @return
     */
    public static boolean isBefore(Date currentDate, Date endDate) {
        if (currentDate == null) {
            return false;
        }

        return getTodayEndTime(currentDate).compareTo(getTodayEndTime(endDate)) < 0;
    }

    /**
     * 计算两个日期的 天数间隔  包括第一天和最后一天
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer dayBetween(Date startDate, Date endDate) {

        startDate = getTodayStartTime(startDate);
        endDate = getTodayEndTime(endDate);

        Long d = (endDate.getTime() - startDate.getTime() + 1000L) / (60 * 60 * 24 * 1000L);
        return d.intValue();
    }

    /**
     * 计算两个日期的相隔月数,并计算多余的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer[] monthBetween(Date startDate, Date endDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(startDate);
        Calendar to = Calendar.getInstance();
        to.setTime(endDate);

        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);


        int month = (toYear - fromYear) * 12 + (to.get(Calendar.MONTH) - from.get(Calendar.MONTH));

        Integer day = to.get(Calendar.DAY_OF_MONTH) - from.get(Calendar.DAY_OF_MONTH) + 1;


        Integer maxDayOfMonth = to.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day.equals(maxDayOfMonth)) {
            month = month + 1;
            day = 0;
        }else if(day < 0){
            month = month - 1;
            day= maxDayOfMonth  + day - 1;
        }

        return new Integer[]{month, day};
    }
    /**
     * 获取当天开始时间 00.00
     *
     * @param startDate
     * @return
     */
    public static Date getTodayStartTime(Date startDate) {
        if (startDate == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当天结束时间 23.59
     *
     * @param endDate
     * @return
     */
    public static Date getTodayEndTime(Date endDate) {
        if (endDate == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 000);
        return c.getTime();
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取当年第一天
     *
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static Date getMonthFirst(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date currMonthFirst = calendar.getTime();
        return currMonthFirst;
    }

    public static Date getMonthLast(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date currMonthLast = calendar.getTime();
        return currMonthLast;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取开始结束月范围内的所有月份列表
     *
     * @param startMonth 开始月份yyyy/MM
     * @param endMonth   结束月份yyyy/MM
     * @return
     */
    public static List<String> getEveryMonthByRange(String startMonth, String endMonth) {
        startMonth = startMonth + "/01";
        endMonth = endMonth + "/01";
        Date sDate = DateUtil.parse(startMonth, DateUtil.SLASH_FORMAT);
        Date eDate = DateUtil.parse(endMonth, DateUtil.SLASH_FORMAT);
        List<String> dateList = new ArrayList<>();
        while (sDate.compareTo(eDate) < 0) {
            dateList.add(DateUtil.formatDate(sDate, DateUtil.SLASH_YM_FORMAT));
            sDate = DateUtil.addMonth(sDate, 1);
        }
        dateList.add(DateUtil.formatDate(sDate, DateUtil.SLASH_YM_FORMAT));
        return dateList;
    }


    //public static void main(String[] args) {
    //    Date after = DateUtil.parse("2021-01-06", "yyyy-MM-dd");
    //    Date before = DateUtil.parse("2020-12-13", "yyyy-MM-dd");
    //
    //    Date in1 = DateUtil.parse("2021-01-05", "yyyy-MM-dd");
    //    Date in2 = DateUtil.parse("2021-01-01", "yyyy-MM-dd");
    //    Date in3 = DateUtil.parse("2021-01-02", "yyyy-MM-dd");
    //
    //    Date startTime = DateUtil.parse("2021-01-08 23:32:11", "yyyy-MM-dd HH:mm:ss");
    //    Date endTime = DateUtil.parse("2021-01-08 23:32:11", "yyyy-MM-dd HH:mm:ss");
    //
    //
    //    System.out.println(dayBetween(startTime, endTime));
    //    System.out.println(monthBetween(startTime, endTime));
    //
    //    //之前
    //    System.out.println("before：" + judgeDateInRange(after, startTime, endTime));
    //    //之后
    //    System.out.println("after：" + judgeDateInRange(before, startTime, endTime));
    //    //之中
    //    System.out.println("in1：" + judgeDateInRange(in1, startTime, endTime));
    //    System.out.println("in2：" + judgeDateInRange(in2, startTime, endTime));
    //    System.out.println("in3：" + judgeDateInRange(in3, startTime, endTime));
    //
    //    //开始 、结束时间少参数测试
    //    //之后
    //    System.out.println("after：" + judgeDateInRange(after, null, endTime));
    //    //之前
    //    System.out.println("before：" + judgeDateInRange(before, startTime, null));
    //    System.out.println("in：" + judgeDateInRange(before, null, endTime));
    //
    //    //之中
    //    System.out.println("in：" + judgeDateInRange(after, startTime, null));
    //    System.out.println("in：" + judgeDateInRange(before, null, endTime));
    //    System.out.println("in：" + judgeDateInRange(in3, startTime, null));
    //    System.out.println("in：" + judgeDateInRange(in3, null, endTime));
    //}

}
