package xatu.school.service;

import java.util.ArrayList;

import xatu.school.activity.StudyFragment;

/**
 * 得到所有 学期+课程名 (例：大一第一学期高等数学)
 */
public class SingleCourseInfoImp implements ISingleCourseInfo{
    @Override
    public ArrayList<String> getSingleCourseInfo() {
       ArrayList<String> AllcourseInfo=new ArrayList<String>();
       //获取信息放入 AllcourseInfo 中
       AllcourseInfo.add("大一第一学期高等数学");
        AllcourseInfo.add("大一第一学期大学物理");
        AllcourseInfo.add("大一第一学期大学英语");
        AllcourseInfo.add("大一第一学期离散数学");
        AllcourseInfo.add("大一第一学期C语言程序设计");
       return AllcourseInfo; //返回一个ArrayList，存放的所有 学期+课程名
    }
}
