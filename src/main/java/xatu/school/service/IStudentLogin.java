package xatu.school.service;


import xatu.school.bean.InitMsg;


/**
 * 学生登录接口
 *
 * @author 李鹏飞
 */
public interface IStudentLogin {

    /**
     * 获取验证码图片
     * 在调用login方法前，先调用此方法得到验证码图片
     * 返回Bitmap
     */
    void getCheckcodePic(InitMsg msg);

    /**
     * 学生登录
     *
     * @param username  学号
     * @param password  密码
     * @param checkcode 验证码
     *                  不需返回对象
     */
    void login(InitMsg msg, String username, String password, String checkcode);

    /**
     * 学生登出(预留方法)
     * 返回登出状态
     */
    void logout(InitMsg msg);
}
