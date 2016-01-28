package xatu.school.bean;

/**
 * 用于与网络操作相关的错误
 * Created by penfi on 2015/11/5.
 */
public enum WebError {
    userAndPwdError,// 用户名或密码错误
    checkcodeError,// 验证码错误
    TIMEOUT,// 连接超时
    OFTEN,// 登录频繁
    FAIL,// 访问失败
    NO_RESPONSE,// 服务器无响应
    OTHER,// 未知错误
    renzhenpingjiao //随便起的名字 ，你改下  请认真评教
}
