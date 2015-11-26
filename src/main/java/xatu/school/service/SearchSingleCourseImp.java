package xatu.school.service;

import java.util.List;

import xatu.school.bean.SingleCourse;

/**
 * 学习界面 搜索单科课程 实现
 */
public class SearchSingleCourseImp implements ISearchSingleCourse {
    @Override
    public List<SingleCourse> getSingleCourses() {
        DBManager dbManager = new DBManager();
        return dbManager.getSingleCourses();
    }
}
