package xatu.school.utils;

import android.content.Context;
import android.os.Handler;

import xatu.school.bean.InitMsg;

/**
 * 创建异步消息工具类
 * Created by penfi on 2015/10/25.
 */
public class CreateInitMsg {
    public static InitMsg msg(Context context, Handler handler, int controlCode) {
        return new InitMsg(context, handler, controlCode);
    }
}
