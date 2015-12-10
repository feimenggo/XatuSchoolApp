package xatu.school.utils;

/**
 * 状态码类
 * Created by penfi on 2015/10/25.
 */
public class Code {
    /**
     * 操作码
     */
    public class CONTROL {
        public static final int CHECKCODE = 1;//验证码
        public static final int LOGIN = 2;//登录
        public static final int COURSEGRADES = 3;//课程成绩信息
        public static final int STUDENTINFO = 4;//学生个人信息
        public static final int COURSETABLE = 5;// 课程表信息
        public static final int LOGIN_WITH_OCR = 6;//登录，自动识别验证码

        public static final int COURSE_EVALUATE = 7;//单科课程评教
        public static final int GET_SINGLECOURSE = 8;//获得单科课程
    }

    /**
     * 返回码
     */
    public class RESULT {
        public static final int TRUE = 1;//成功
        public static final int FALSE = 0;//失败
    }
}