package xatu.school.service;


import xatu.school.bean.InitMsg;

/**
 * 学生信息接口
 * Created by penfi on 2015/10/25.
 */
public interface IStudentInfo {

    /**
     * 获取学生个人信息
     * 异步返回 StudentInfo
     */
    public void getStudentInfo(InitMsg msg);
}
