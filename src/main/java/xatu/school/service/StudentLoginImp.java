package xatu.school.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import xatu.school.bean.InitMsg;
import xatu.school.bean.WebError;
import xatu.school.utils.CheckcodeOcr;
import xatu.school.utils.Code;

/**
 * Created by Administrator on 2015-10-25.
 */
public class StudentLoginImp implements IStudentLogin {
    String error=new String();
    @Override
    public void getCheckcodePic(final InitMsg m) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore=new PersistentCookieStore(m.getContext());
        client.setCookieStore(myCookieStore);
        client.get("http://222.25.1.101/js/checkcode.asp", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                BitmapFactory factory = new BitmapFactory();
                Bitmap bitmap = factory.decodeByteArray(responseBody, 0, responseBody.length);
                Message msg = Message.obtain();
                msg.what = m.getControlCode();
                if (bitmap == null) {
                    msg.arg1 = Code.RESULT.FALSE;
                } else {
                    msg.arg1 = Code.RESULT.TRUE;
                }
                msg.obj = bitmap;
                m.getHandler().sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable errorr) {
//                Log.i("TAG", "get picture  onFailure");
            }
        });
    }

    @Override
    public void login(final InitMsg m, String username, String password, String checkcode) {

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("CheckCode",checkcode);
        params.put("mynum","1");

        String url=new String();
        url="http://222.25.1.101/userlog.asp?username="+username+"&password="+password+"&CheckCode="+checkcode+"&mynum=1&btnlog=%B5%C7+%C2%BD";
//        Log.e("url", url);

        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore=new PersistentCookieStore(m.getContext());
        client.setCookieStore(myCookieStore);

        client.addHeader("Referer", "http://222.25.1.101/index.htm");
        client.addHeader("Host","222.25.1.101");
        client.post("http://222.25.1.101/userlog.asp", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Message msg = Message.obtain();
//                Log.e("lo", String.valueOf(statusCode));
                try {
                    String htmlOfsc = new String(responseBody, "GB2312");
//                    Log.e("lo", htmlOfsc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(statusCode==404)
                    msg.obj= WebError.noResponse;
                msg.what = m.getControlCode();
                msg.arg1 = Code.RESULT.FALSE;
                msg.obj=WebError.other;
                m.getHandler().sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.e("lo", String.valueOf(statusCode));
                Message msg = Message.obtain();
                msg.what = m.getControlCode();;
                try {
                    String htmlOfsc = new String(responseBody, "GB2312");
                    //Log.e("lo", htmlOfsc);
                    //Log.e("lo", String.valueOf(htmlOfsc.indexOf("message.asp?id=2")));
                    if(htmlOfsc.indexOf("message.asp?id=2")>0)
                    {
                        msg.arg1 = Code.RESULT.FALSE;
                        msg.obj=WebError.userAndPwdError;
                    }
                    else if(htmlOfsc.indexOf("message.asp?id=3")>0)
                    {
                        msg.arg1 = Code.RESULT.FALSE;
                        msg.obj=WebError.checkcodeError;
                    }
                    else if(htmlOfsc.indexOf("student/index.asp")>0)
                    {
                        msg.arg1 = Code.RESULT.TRUE;
                        msg.obj=new String("正确");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                m.getHandler().sendMessage(msg);
            }
        });
    }

    @Override
    public void logout(InitMsg msg) {

    }

    @Override
    public void loginWithOcr(final InitMsg m, final String username, final String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore=new PersistentCookieStore(m.getContext());
        client.setCookieStore(myCookieStore);
        client.get("http://222.25.1.101/js/checkcode.asp", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                BitmapFactory factory = new BitmapFactory();
                Bitmap bitmap = factory.decodeByteArray(responseBody, 0, responseBody.length);
                String text = null;
                try {
                    text = CheckcodeOcr.getOcr(m.getContext(), bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                login(m,username,password,text);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable errorr) {
                //Log.i("TAG", "get picture  onFailure");
            }
        });
    }
}
