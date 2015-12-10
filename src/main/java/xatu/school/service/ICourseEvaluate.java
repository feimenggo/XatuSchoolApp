package xatu.school.service;

import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.InitMsg;
import xatu.school.bean.SourceSingleCourse;

/**
 * 课程评教 接口
 * Created by penfi on 2015/12/2.
 */
public interface ICourseEvaluate {
    /**
     * 评教操作
     *
     * @param evaluateInfo 单科课程评教信息对象
     */
    void evaluate(InitMsg msg, EvaluateInfo evaluateInfo);

    /**
     * 根据课程名获得课程信息
     * 异步返回 原始单科课程 对象
     *
     * @param CourseName 课程名
     * @return 原始单科课程 对象
     */
    SourceSingleCourse getSingleCourse(InitMsg msg, String CourseName);

}
