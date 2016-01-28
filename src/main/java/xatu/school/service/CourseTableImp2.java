package xatu.school.service;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;

import xatu.school.bean.CourseTable;
import xatu.school.bean.InitMsg;
import xatu.school.bean.WebError;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;

/**
 * Created by feimeng on 2016/1/27.
 */
public class CourseTableImp2 implements ICourseTable {
    @Override
    public void getCourseTableFromWeb(final InitMsg msg) {
        String cookie = CookieUtil.getCookieContent();// 最近的Cookie
        String url = "http://222.25.1.101/student/KCB.asp";

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
            String htmlOfc = new String(response.body().bytes(), "GB2312");
            JsoupC(htmlOfc, msg);

        } catch (SocketTimeoutException e) {
            Log.i("tag", "课程表信息：" + e.getMessage());

            // 创建消息
            Message newMsg = Message.obtain();
            newMsg.what = msg.getControlCode();
            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.TIMEOUT;
            msg.getHandler().sendMessage(newMsg);
        } catch (IOException e) {
            Log.i("tag", "课程表信息：" + e.getMessage());

            // 创建消息
            Message newMsg = Message.obtain();
            newMsg.what = msg.getControlCode();
            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.FAIL;
            msg.getHandler().sendMessage(newMsg);
        }
    }

    @Override
    public CourseTable getCourseTableByWeek(CourseTable cst, int week) {
        for (int i = 1; i <= 5; i++) {
            for (int l = 1; l <= 5; l++) {
                if (!TextUtils.isEmpty(cst.get(i).get(l).zhouci)) {
                    String str1 = cst.get(i).get(l).zhouci.split("-")[0];
                    String str2 = cst.get(i).get(l).zhouci.split("-")[1];
                    if (!(week >= Integer.parseInt(str1) && week <= Integer.parseInt(str2)))
                        cst.get(i).get(l).init();
                }
            }
        }
        return cst;
    }


    private void JsoupC(String htmlOfc, InitMsg m) {
        int tmp;
        CourseTable ct = new CourseTable();
        Document doc = Jsoup.parse(htmlOfc);
        Element table = doc.getElementsByTag("table").get(1);
        Elements tr = table.getElementsByTag("tr");

        for (int i = 1; i <= 5; i++) {
            Element t = tr.get(i);
            for (int l = 1; l <= 5; l++) {
                Elements td = t.getElementsByTag("td");
                //Log.e("jsoup",i+" "+l+":"+td.get(l).text());
                String str = td.get(l).text().replaceAll(" ", "");
                str = str.replace('(', ' ');
                str = str.replace(')', ' ');
//                Log.e("jsoup", i + " " + l + ":" + str);
                if (!TextUtils.isEmpty(str)) {
                    String str1 = str.split(" ")[1];
                    if (str1.indexOf("-") <= 0)
                        continue;
                    String str2 = str.split(" ")[3];
                    String str3;
                    if (str.split(" ").length > 3)
                        str3 = str.split(" ")[str.split(" ").length - 1];
                    else
                        str3 = null;
                    ct.get(l).get(i).zhouci = str1;
                    ct.get(l).get(i).courseName = str2;
                    ct.get(l).get(i).jiaoshi = str3;
                    tmp = 2 * i - 1;
                    ct.get(l).get(i).jieci = tmp + "-" + (tmp + 1);
                }
            }
        }
        Message msg = Message.obtain();
        msg.what = m.getControlCode();
        msg.obj = ct;
        msg.arg1 = Code.RESULT.TRUE;
        m.getHandler().sendMessage(msg);
    }
}
