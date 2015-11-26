package xatu.school.service;

import java.util.ArrayList;
import java.util.List;

import xatu.school.bean.SingleCourse;

/**
 * 学习界面 搜索单科课程 接口
 */
public interface ISearchSingleCourse {

    /**
     * 获取所有单科成绩
     *
     * @return 单科成绩集合
     */
    List<SingleCourse> getSingleCourses();
}
