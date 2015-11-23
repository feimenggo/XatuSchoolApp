package xatu.school.service;

import android.os.Message;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;


import cz.msebera.android.httpclient.Header;
import xatu.school.bean.CourseTable;
import xatu.school.bean.InitMsg;
import xatu.school.utils.Code;

/**
 * Created by Administrator on 2015-11-7.
 */
public class CourseTableImp implements ICourseTable {
    @Override
    public void getCourseTableFromWeb(final InitMsg m) {
        AsyncHttpClient clientget = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(m.getContext());
        clientget.setCookieStore(myCookieStore);
        clientget.addHeader("Referer", "http://222.25.1.101/student/navtree.asp");
        clientget.addHeader("Host", "222.25.1.101");
        clientget.get("http://222.25.1.101/student/KCB.asp", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String htmlOfc;
                try {
                    htmlOfc = new String(responseBody, "GB2312");
                    JsoupC(htmlOfc,m);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.e("info", String.valueOf(statusCode));
                Message msg = Message.obtain();
                msg.what = m.getControlCode();
                msg.obj = null;
                msg.arg1 = Code.RESULT.FALSE;
                m.getHandler().sendMessage(msg);
            }
        });
    }

    @Override
    public CourseTable getCourseTableByWeek(CourseTable cst, int week) {
        for(int i=1;i<=5;i++)
        {
            for(int l=1;l<=5;l++)
            {
                if(!TextUtils.isEmpty(cst.get(i).get(l).zhouci))
                {
                    String str1=cst.get(i).get(l).zhouci.split("-")[0];
                    String str2=cst.get(i).get(l).zhouci.split("-")[1];
                    if(!(week>=Integer.parseInt(str1) && week<=Integer.parseInt(str2)))
                        cst.get(i).get(l).init();
                }
            }
        }
        return cst;
    }




    private void JsoupC(String htmlOfc, InitMsg m)
    {
        int tmp;
        CourseTable ct=new CourseTable();
        Document doc = Jsoup.parse(htmlOfc);
        Element table =doc.getElementsByTag("table").get(1);
        Elements tr = table.getElementsByTag("tr");

        for(int i=1;i<=5;i++)
        {
            Element t=tr.get(i);
            for(int l=1;l<=5;l++)
            {
                Elements td=t.getElementsByTag("td");
                //Log.e("jsoup",i+" "+l+":"+td.get(l).text());
                String str=td.get(l).text().replaceAll(" ","");
                str=str.replace('(',' ');
                str=str.replace(')',' ');
//                Log.e("jsoup", i + " " + l + ":" + str);
                if(!TextUtils.isEmpty(str))
                {
                    String str1=str.split(" ")[1];
                    if(str1.indexOf("-")<=0)
                        continue;
                    String str2=str.split(" ")[3];
                    String str3;
                    if(str.split(" ").length>3)
                        str3=str.split(" ")[str.split(" ").length-1];
                    else
                        str3=null;
                    ct.get(l).get(i).zhouci=str1;
                    ct.get(l).get(i).courseName=str2;
                    ct.get(l).get(i).jiaoshi=str3;
                    tmp=2*i-1;
                    ct.get(l).get(i).jieci=tmp+"-"+(tmp+1);
                }
            }
        }
        Message msg = Message.obtain();
        msg.what = m.getControlCode();
        msg.obj=ct;
        msg.arg1 = Code.RESULT.TRUE;
        m.getHandler().sendMessage(msg);
    }
}
