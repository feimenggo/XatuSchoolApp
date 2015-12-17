package xatu.school.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 计算周数类
 * Created by penfi on 2015/11/7.
 */
public class WeekNumber {
    /**
     * 计算两个时间差的周数
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return 当前周数(标准周数加1)
     * @throws ParseException
     */
    public static int calWeekNumber(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(endDate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int days = Integer.parseInt(String.valueOf(between_days));
        return days / 7 + 1;
    }

    /**
     * 获取今日日期
     *
     * @return 日期 例如：2015-11-11
     */
    public static String getNowDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(new Date());
    }

    public static int getNowDayOfWeek() {
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        int day;
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                day = 1;
                break;
            case Calendar.TUESDAY:
                day = 2;
                break;
            case Calendar.WEDNESDAY:
                day = 3;
                break;
            case Calendar.THURSDAY:
                day = 4;
                break;
            case Calendar.FRIDAY:
                day = 5;
                break;
            default:
                day = 1;
                break;
        }
        return day;
    }

    /**
     * 得到学期类型：上学期或者下学期
     *
     * @return true 上学期，false 下学期
     */
    public static boolean getSemesterType() {
        Calendar cal = Calendar.getInstance();
        int mouth = cal.get(Calendar.MONTH);
        mouth++;// 加一才是正常月份
        return !(mouth >= 3 && mouth <= 8);
    }
}
