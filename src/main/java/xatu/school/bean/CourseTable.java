package xatu.school.bean;

/**
 * 课程表类
 * Created by penfi on 2015/11/6.
 */
public class CourseTable {
    public static final String TABLE_NAME = "tb_coursetable";
    public static final String COLUMN_day = "day";// 天
    public static final String COLUMN_NAME = "name";// 课程名
    public static final String COLUMN_ZHOUCI = "zhouci";// 周次
    public static final String COLUMN_JIECI = "jieci";// 节次
    public static final String COLUMN_JIAOSHI = "jiaoshi";// 教室
    public static final String COLUMN_TYPE = "type";// 类型 1：系统，2：自定义
    public static final String COLUMN_JIECI_ID = "jieciid";// 节次编号

    public static final int MONDAY = 1;// 星期一
    public static final int TUESDAY = 2;// 星期二
    public static final int WEDNESDAY = 3;// 星期三
    public static final int THURSDAY = 4;// 星期四
    public static final int FRIDAY = 5;// 星期五

    /**
     * 内部类 科目类
     */
    public static class Subject {
        public String courseName = "";// 课程名
        public int day = 0;// 星期
        public String zhouci = "";//周次
        public String jieci = "";// 节次
        public String jiaoshi = "";// 教室

        public void init() {
            courseName = "";
            zhouci = "";
            jieci = "";
            jiaoshi = "";
        }
    }

    /**
     * 内部类 一天类
     */
    public class Day {
        Subject m1 = new Subject();//12节
        Subject m2 = new Subject();//34节
        Subject m3 = new Subject();//56节
        Subject m4 = new Subject();//78节
        Subject m5 = new Subject();//910节

        public Subject get(int i) {
            if (i == 1)
                return m1;
            else if (i == 2)
                return m2;
            else if (i == 3)
                return m3;
            else if (i == 4)
                return m4;
            else if (i == 5)
                return m5;
            return null;
        }
    }

    private Day mMonday = new Day();
    private Day mTuesday = new Day();
    private Day mWednesday = new Day();
    private Day mThursday = new Day();
    private Day mFriday = new Day();

    public Day get(int i) {
        if (i == 1)
            return mMonday;
        else if (i == 2)
            return mTuesday;
        else if (i == 3)
            return mWednesday;
        else if (i == 4)
            return mThursday;
        else if (i == 5)
            return mFriday;
        return null;
    }
}