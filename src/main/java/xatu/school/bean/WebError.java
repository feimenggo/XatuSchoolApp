package xatu.school.bean;

/**
 * 用于与网络操作相关的错误
 * Created by penfi on 2015/11/5.
 */
public enum WebError {
    userAndPwdError,// 用户名或密码错误
    checkcodeError,// 验证码错误
    noResponse,// 服务器无响应
    other,// 未知错误
    renzhenpingjiao, //随便起的名字 ，你改下  请认真评教
}
