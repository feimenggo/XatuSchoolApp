package xatu.school.service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;

import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.InitMsg;
import xatu.school.bean.WebError;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;

/**
 * Created by Administrator on 2015-12-6.
 */
public class CourseEvaluateImp2 implements ICourseEvaluate {

    private InitMsg m;
    private EvaluateInfo evaluateInfo;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 999:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        CookieUtil.updateCookieTime(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PostInfo(m, evaluateInfo);
                            }
                        }).start();
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

    private void PostInfo(final InitMsg msg, EvaluateInfo evaluateInfo) {
        String cookie = CookieUtil.getCookieContent();// 最近的Cookie
        String url = "http://222.25.1.101/student/" + evaluateInfo.getSingleCourse().getUrl();

        String Host = "222.25.1.101";
        String User_Agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0";
        String Accept = "image/png,image/*;q=0.8,*/*;q=0.5";
        String Accept_Language = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
        String Referer = "http://222.25.1.101/student/Report.asp?tmid=1";
        String Connection = "keep-alive";
        String Cache_Control = "max-age=0";
        // 创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        client.setFollowRedirects(false);// 禁止跟随重定向

        // 构建POST参数
        FormEncodingBuilder builder = getParams(evaluateInfo);

        // 创建Request对象
        Request request = new Request.Builder().url(url)
                .header("Host", Host)
                .header("User-Agent", User_Agent)
                .header("Accept", Accept)
                .header("Accept-Language", Accept_Language)
                .header("Referer", Referer)
                .header("Cookie", cookie)
                .header("Connection", Connection)
                .header("Cache-Control", Cache_Control).post(builder.build()).build();

        // 创建一个Call对象
        Call call = client.newCall(request);

        Message newMsg = Message.obtain();
        newMsg.what = m.getControlCode();
        try {
            Response response = call.execute();

//            Log.i("tag", "response：" + response + " re" + response.isRedirect());
//            Log.i("tag", "------------------------------------------------");
//            Headers responseHeaders = response.headers();
//            Log.i("tag", "response:" + response + ";redirect:" + response.isRedirect());
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.i("tag", responseHeaders.name(i) + ": " +
//                        responseHeaders.value(i));
//            }
//            Log.i("tag", "------------------------------------------------");
            String html = new String(response.body().bytes(), "GB2312");

            if (response.isRedirect()) {
                // 302 成功
                newMsg.arg1 = Code.RESULT.TRUE;
            } else {
                if (!response.isSuccessful())
                    throw new IOException("状态码：" + response.code());

                if (html.indexOf("请您认真评教") > 0) {
                    newMsg.arg1 = Code.RESULT.FALSE;
                    newMsg.obj = WebError.renzhenpingjiao;
                } else {
                    newMsg.arg1 = Code.RESULT.FALSE;
                    newMsg.obj = WebError.OTHER;
                }
            }
            // 发送消息
            msg.getHandler().sendMessage(newMsg);
        } catch (SocketTimeoutException e) {
            Log.i("tag", "评教：" + e.getMessage());

            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.TIMEOUT;
            msg.getHandler().sendMessage(newMsg);
        } catch (IOException e) {
            Log.i("tag", "评教：" + e.getMessage());

            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.FAIL;
            msg.getHandler().sendMessage(newMsg);
        }
    }

    private void Checkcookie(InitMsg m) {
        final InitMsg msg = new InitMsg(m.getContext(), handler, 999);
        if (!CookieUtil.check()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new StudentLoginImp2().loginWithOcr(msg, CookieUtil.getUsername(), CookieUtil.getPassword());
                }
            }).start();
        } else {
            Message mm = Message.obtain();
            mm.arg1 = Code.RESULT.TRUE;
            mm.what = 999;
            handler.sendMessage(mm);
        }

    }

    private FormEncodingBuilder getParams(EvaluateInfo evaluateInfo) {
        char value[] = {'A', 'B', 'C', 'D', 'E'};
        String name1[] = {"R1_72", "R1_73", "R1_74", "R1_75", "R1_106", "R1_107", "R1_108", "R1_109", "R1_110", "R1_111"};
        String name2[] = {"R1_29", "R1_30", "R1_32", "R1_33", "R1_34", "R1_35", "R1_36", "R1_37", "R1_38", "R1_39", "R1_40", "R1_41", "R1_42", "R1_43"};

        FormEncodingBuilder builder = new FormEncodingBuilder();

        for (int i = 0; i < 10; i++) {
            builder.add(name1[i], String.valueOf(value[(evaluateInfo.getForm()[i]) - 1]));
            builder.add(name2[i], String.valueOf(value[(evaluateInfo.getForm()[i]) - 1]));
        }
        for (int i = 10; i < 14; i++) {
            builder.add(name2[i], String.valueOf(value[i % 5 + 1]));
        }
        builder.add("APIContenr", "");
        builder.add("B1", "保存");
        return builder;
    }
}
