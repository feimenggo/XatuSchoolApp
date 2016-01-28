package xatu.school.service;

import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

import cz.msebera.android.httpclient.Header;
import xatu.school.bean.InitMsg;
import xatu.school.bean.StudentInfo;
import xatu.school.bean.WebError;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;

/**
 * Created by feimeng on 2016/1/27.
 */
public class StudentInfoImp2 implements IStudentInfo {
    @Override
    public void getStudentInfo(final InitMsg msg) {
        String cookie = CookieUtil.getCookieContent();// 最近的Cookie
        String url = "http://222.25.1.101/student/basinfo.asp";

        String Host = "222.25.1.101";
        String User_Agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0";
        String Accept = "image/png,image/*;q=0.8,*/*;q=0.5";
        String Accept_Language = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
        String Referer = "http://222.25.1.101/student/navtree.asp";
        String Connection = "keep-alive";
        String Cache_Control = "max-age=0";
        // 创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        client.setFollowRedirects(false);// 禁止跟随重定向

        // 创建Request对象
        Request request = new Request.Builder().url(url)
                .header("Host", Host)
                .header("User-Agent", User_Agent)
                .header("Accept", Accept)
                .header("Accept-Language", Accept_Language)
                .header("Referer", Referer)
                .header("Cookie", cookie)
                .header("Connection", Connection)
                .header("Cache-Control", Cache_Control).build();

        // 创建一个Call对象
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            if (!response.isSuccessful())
                throw new IOException("状态码：" + response.code());
            String htmlOfif = new String(response.body().bytes(), "GB2312");
            JsoupIF(htmlOfif, msg);
        } catch (SocketTimeoutException e) {
            Log.i("tag", "学生信息：" + e.getMessage());

            // 创建消息
            Message newMsg = Message.obtain();
            newMsg.what = msg.getControlCode();
            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.TIMEOUT;
            msg.getHandler().sendMessage(newMsg);
        } catch (IOException e) {
            Log.i("tag", "学生信息：" + e.getMessage());

            // 创建消息
            Message newMsg = Message.obtain();
            newMsg.what = msg.getControlCode();
            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.FAIL;
            msg.getHandler().sendMessage(newMsg);
        }
    }

    private void JsoupIF(String htmlOfif, InitMsg m) {
        String name = null;  //名字
        String xibu = null;  //系别
        String zhuanye = null; //专业
        String xingbie = null; //性别
        String banji = null;//班级
        String xuehao = null;//学号
        String shengri = null;//生日
        String path = null;//生日
        Bitmap zhaopian;//照片
        Document doc = Jsoup.parse(htmlOfif);
        Elements link = doc.getElementsByTag("td");
        int i = 1;
        for (Element s : link) {
            switch (i) {
                case 2:
                    xibu = s.text();
                    break;
                case 4:
                    xuehao = s.text();
                    break;
                case 7:
                    zhuanye = s.text();
                    break;
                case 9:
                    banji = s.text();
                    break;
                case 12:
                    name = s.text();
                    break;
                case 14:
                    xingbie = s.text();
                    break;
                case 16:
                    shengri = s.text();
                    break;
            }
            i++;
        }
        StudentInfo info = new StudentInfo(name, xibu, zhuanye, xingbie, banji, xuehao, shengri);

        Message msg = Message.obtain();
        msg.what = m.getControlCode();
        msg.arg1 = Code.RESULT.TRUE;
        msg.obj = info;
        m.getHandler().sendMessage(msg);
    }
}
