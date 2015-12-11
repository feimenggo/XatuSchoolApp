package xatu.school.service;

import xatu.school.bean.InitMsg;

/**
 * 从网络获取课程成绩接口
 * Created by penfi on 2015/10/25.
 */
public interface IGetCourseGradesFromNet {
    /**
     * 获取课程成绩
     * 异步返回 课程成绩 对象
     */
    void getCourseGrades(InitMsg msg);
}
