package xatu.school.service;

import android.os.Message;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.InitMsg;
import xatu.school.bean.WebError;
import xatu.school.utils.Code;

/**
 * Created by Administrator on 2015-12-6.
 */
public class CourseEvaluateImp implements ICourseEvaluate{
    @Override
    public void evaluate(final InitMsg m, EvaluateInfo evaluateInfo) {

        String url="http://222.25.1.101/student/"+evaluateInfo.getSingleCourse().getUrl();
        Log.e("test url",url);
        AsyncHttpClient clientget = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(m.getContext());
        clientget.setCookieStore(myCookieStore);
        clientget.addHeader("Referer", "http://222.25.1.101/student/Report.asp?tmid=1");
        clientget.addHeader("Host", "222.25.1.101");
        clientget.post(url,getparams(evaluateInfo),new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200)
                {
                    try {
                        String str = new String(responseBody,"GBK");
                        Log.e("ret  code", String.valueOf(statusCode));
                        Log.e("ret  url", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.obj= WebError.renzhenpingjiao;
                    msg.what = m.getControlCode();
                    msg.arg1 = Code.RESULT.FALSE;
                    m.getHandler().sendMessage(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode==302)
                {

                    try {
                        String str = new String(responseBody,"GBK");
                        Log.e("ret  code", String.valueOf(statusCode));
                        Log.e("ret  url", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.obj=new String("正确");
                    msg.what = m.getControlCode();
                    msg.arg1 = Code.RESULT.TRUE;
                    m.getHandler().sendMessage(msg);
                }
            }
        });
    }
    private RequestParams getparams(EvaluateInfo evaluateInfo)
    {
        char value[]={'A','B','C','D','E'};
        String name[]={"R1_72","R1_73","R1_74","R1_75","R1_106","R1_107","R1_108","R1_109","R1_110","R1_111"};
        RequestParams params = new RequestParams();
       for(int i=0;i<10;i++)
       {
           params.put(name[i],String.valueOf(value[(evaluateInfo.getForm()[i])-1]));
       }
        params.put("APIContenr","");
        params.put("B1","保存");
        Log.e("form", String.valueOf(params));
        return params;
    }
}
