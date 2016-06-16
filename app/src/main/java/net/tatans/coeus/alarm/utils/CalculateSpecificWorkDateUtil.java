package net.tatans.coeus.alarm.utils;

/**
 * Created by Administrator on 2016/6/8.
 */

import net.tatans.coeus.network.tools.TatansLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 计算 特定 规则的 工作日 (可以根据具体业务规则修改得到工作日)
 *
 * @author susan
 */
public class CalculateSpecificWorkDateUtil {


    private static final String TAG = CalculateSpecificWorkDateUtil.class
            .getName();
    private static CalculateSpecificWorkDateUtil cswdu = null;


    private CalculateSpecificWorkDateUtil() {


    }


    public static CalculateSpecificWorkDateUtil getInstance() {
        if (cswdu == null) {
            cswdu = new CalculateSpecificWorkDateUtil();
        }
        return cswdu;
    }

    /**
     * @return 周末是工作日的列表
     * @title 获取周六和周日是工作日的情况（手工维护）
     * 注意，日期必须写全：
     * 2015-1-4必须写成：2015-01-04
     * @author
     */
    private List<String> getWeekendIsWorkDateList() {
        List<String> list = new ArrayList<String>();
        list.add("2016-02-06");
        list.add("2016-02-14");
        list.add("2016-06-12");
        list.add("2016-09-18");
        list.add("2016-10-08");
        list.add("2016-10-09");
        return list;
    }

    /**
     * @return 平时是假期的列表
     * @title 获取周一到周五是假期的情况（手工维护）
     * 注意，日期必须写全：
     * 2009-1-4必须写成：2009-01-04
     * @author
     */
    private List<String> getWeekdayIsHolidayList() {
        List<String> list = new ArrayList<String>();
        list.add("2016-01-01");
        list.add("2016-02-08");
        list.add("2016-02-09");
        list.add("2016-02-10");
        list.add("2016-02-11");
        list.add("2016-02-12");
        list.add("2016-04-04");
        list.add("2016-05-01");
        list.add("2016-06-09");
        list.add("2016-06-10");
        list.add("2016-09-15");
        list.add("2016-09-16");
        list.add("2016-10-03");
        list.add("2016-10-04");
        list.add("2016-10-05");
        list.add("2016-10-06");
        list.add("2016-10-07");
        return list;
    }


    /**
     * @param calendar 日期
     * @return 是工作日返回true，非工作日返回false
     * @title 判断是否为工作日
     * @detail 工作日计算:
     * 1、正常工作日，并且为非假期
     * 2、周末被调整成工作日
     * @author
     */
    public boolean isWorkDay(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            //平时
            return !getWeekdayIsHolidayList().contains(sdf.format(calendar.getTime()));
        } else {
            //周末
            return getWeekendIsWorkDateList().contains(sdf.format(calendar.getTime()));
        }
    }

    //获得下一个工作日
    public String getNextWorkday(String strDate) {
        String workDay = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        //传进来的日期 往后加一天
        cal.add(Calendar.DAY_OF_YEAR, 1);
        for (int i = 0; i < 15; i++) {
            if (isWorkDay(cal)) {
                Date time = cal.getTime();
                workDay = sdf.format(time);
                System.out.println("输出下一个工作日：" + workDay);
                break;
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        return workDay;
    }

    //计算下一个工作日还有多少天
    public int getNextWorkdayCount(Calendar cal) {
        String nextWorkDay, currentDay;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDay = sdf.format(cal.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        //传进来的日期 往后加一天
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int i = 0;
        for (; i < 15; i++) {
            if (isWorkDay(calendar)) {
                Date time = calendar.getTime();
                nextWorkDay = sdf.format(time);
                TatansLog.d("antony", currentDay + "输出下一个工作日：" + nextWorkDay);
                break;
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        TatansLog.d("antony", "i=" + i);
        return i + 1;
    }

}
