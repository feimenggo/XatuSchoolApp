package xatu.school.service;

import xatu.school.bean.CourseTable;
import xatu.school.bean.InitMsg;

/**
 * 课程表接口
 * Created by penfi on 2015/11/7.
 */
public interface ICourseTable {

    /**
     * 从网络获取所有课程表信息，异步返回CourseTable对象
     *
     * @param msg 异步消息
     */
    void getCourseTableFromWeb(InitMsg msg);

    /**
     * 根据当前第几周，返回课程表信息
     *
     * @param courseTables 所有课程表信息
     * @param week
     * @return
     */
    CourseTable getCourseTableByWeek(CourseTable courseTables, int week);
}
