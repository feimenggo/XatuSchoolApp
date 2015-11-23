package xatu.school.service;

import android.graphics.Bitmap;
import android.os.Message;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import xatu.school.bean.InitMsg;
import xatu.school.bean.StudentInfo;
import xatu.school.utils.Code;

/**
 * Created by Administrator on 2015-10-25.
 */
public class StudentInfoImp implements IStudentInfo {
    @Override
    public void getStudentInfo(final InitMsg m) {
        AsyncHttpClient clientget = new AsyncHttpClient();

        PersistentCookieStore myCookieStore = new PersistentCookieStore(m.getContext());
        clientget.setCookieStore(myCookieStore);
        clientget.addHeader("Referer", "http://222.25.1.101/student/navtree.asp");
        clientget.addHeader("Host", "222.25.1.101");
        clientget.get("http://222.25.1.101/student/basinfo.asp", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String htmlOfif = null;
                try {
                    htmlOfif = new String(responseBody, "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JsoupIF(htmlOfif, m);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Message msg = Message.obtain();
                msg.what = m.getControlCode();
                msg.obj = null;
                msg.arg1 = Code.RESULT.FALSE;
                m.getHandler().sendMessage(msg);
            }
        });
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
        msg.obj = info;
        msg.arg1 = Code.RESULT.TRUE;
        m.getHandler().sendMessage(msg);
    }
}
