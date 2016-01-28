package xatu.school.service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;


import cz.msebera.android.httpclient.Header;
import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.InitMsg;
import xatu.school.bean.WebError;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;

/**
 * Created by Administrator on 2015-12-6.
 */
public class CourseEvaluateImp implements ICourseEvaluate {

    private InitMsg m;
    private EvaluateInfo evaluateInfo;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 999:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        CookieUtil.updateCookieTime(true);
                        PostInfo(m, evaluateInfo);
                    } else {
                        Log.i("Tag", "3");
                    }
                    break;
            }
        }
    };

    @Override
    public void evaluate(final InitMsg m, EvaluateInfo evaluateInfo) {
        Checkcookie(m);
        this.m = m;
        this.evaluateInfo = evaluateInfo;
    }

    private void PostInfo(final InitMsg m, EvaluateInfo evaluateInfo) {
        String url = "http://222.25.1.101/student/" + evaluateInfo.getSingleCourse().getUrl();
//        Log.e("test url", url);
        AsyncHttpClient clientget = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(m.getContext());
        clientget.setCookieStore(myCookieStore);
        clientget.addHeader("Referer", "http://222.25.1.101/student/Report.asp?tmid=1");
        clientget.addHeader("Host", "222.25.1.101");
        clientget.setConnectTimeout(10000);
        clientget.setTimeout(10000);
        clientget.post(url, getparams(evaluateInfo), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Message msg = Message.obtain();
                    msg.obj = WebError.renzhenpingjiao;
                    msg.what = m.getControlCode();
                    msg.arg1 = Code.RESULT.FALSE;
                    m.getHandler().sendMessage(msg);
                } else {
                    Log.i("Tag", "1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 302) {
                    Message msg = Message.obtain();
                    msg.obj = "正确";
                    msg.what = m.getControlCode();
                    msg.arg1 = Code.RESULT.TRUE;
                    m.getHandler().sendMessage(msg);

                } else {
                    Log.i("Tag", "2");
                }
            }
        });
    }

    private void Checkcookie(InitMsg m) {
        InitMsg msg = new InitMsg(m.getContext(), handler, 999);
        if (!CookieUtil.check()) {
            new StudentLoginImp().loginWithOcr(msg, CookieUtil.getUsername(), CookieUtil.getPassword());
        } else {
            Message mm = Message.obtain();
            mm.arg1 = Code.RESULT.TRUE;
            mm.what = 999;
            handler.sendMessage(mm);
        }

    }

    private RequestParams getparams(EvaluateInfo evaluateInfo) {
        char value[] = {'A', 'B', 'C', 'D', 'E'};
        String name1[] = {"R1_72", "R1_73", "R1_74", "R1_75", "R1_106", "R1_107", "R1_108", "R1_109", "R1_110", "R1_111"};
        String name2[] = {"R1_29", "R1_30", "R1_32", "R1_33", "R1_34", "R1_35", "R1_36", "R1_37", "R1_38", "R1_39", "R1_40", "R1_41", "R1_42", "R1_43"};

        RequestParams params = new RequestParams();
        for (int i = 0; i < 10; i++) {
            params.put(name1[i], String.valueOf(value[(evaluateInfo.getForm()[i]) - 1]));
            params.put(name2[i], String.valueOf(value[(evaluateInfo.getForm()[i]) - 1]));
        }
        for (int i = 10; i < 14; i++) {
            params.put(name2[i], value[i % 5 + 1]);
        }
        params.put("APIContenr", "");
        params.put("B1", "保存");
        return params;
    }
}
