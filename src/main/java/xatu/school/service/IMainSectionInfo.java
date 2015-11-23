package xatu.school.service;

import xatu.school.bean.CourseInfoSection;
import xatu.school.bean.CourseGrades;

/**
 * 主界面 得到各个区的信息
 * Created by penfi on 2015/11/4.
 */
public interface IMainSectionInfo {

    /**
     * 得到课程信息区要显示的数据
     *
     * @param courseGrades 所有课程
     * @return 课程信息区 对象
     */
    public CourseInfoSection getCourseInfoSection(CourseGrades courseGrades);
}
