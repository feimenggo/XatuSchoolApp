package xatu.school.bean;

import android.content.Context;
import android.os.Handler;

/**
 * 自定义消息
 * Created by penfi on 2015/10/25.
 */
public class InitMsg {
    Context context; // 上下文
    Handler handler; // handler
    int controlCode; // 操作码

    public InitMsg(Context context, Handler handler, int controlCode) {
        this.context = context;
        this.handler = handler;
        this.controlCode = controlCode;
    }

    public Context getContext() {
        return context;
    }

    public Handler getHandler() {
        return handler;
    }

    public int getControlCode() {
        return controlCode;
    }
}
