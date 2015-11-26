package xatu.school.service;

import java.util.ArrayList;

import xatu.school.activity.StudyFragment;

/**
 * 得到所有 课程名+学期 (例：高等数学:大一第一学期)
 */
public class SingleCourseInfoImp implements ISingleCourseInfo{
    @Override
    public ArrayList<String> getSingleCourseInfo() {
       ArrayList<String> AllcourseInfo=new ArrayList<String>();
       //获取信息放入 AllcourseInfo 中
       AllcourseInfo.add("高等数学:大一第一学期");
        AllcourseInfo.add("大学物理:大一第一学期");
        AllcourseInfo.add("大学英语:大一第一学期");
        AllcourseInfo.add("离散数学:大一第一学期");
        AllcourseInfo.add("C语言程序设计:大一第一学期");
       return AllcourseInfo; //返回一个ArrayList，存放的所有 课程名+学期
    }
}
