package xatu.school.service;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import cz.msebera.android.httpclient.Header;
import feimeng.coursetableview.SimpleSection;
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
        List<SimpleSection> sections = new ArrayList<>();
        for(int i=1;i<=5;i++)
        {
            Element t=tr.get(i);
            for(int l=1;l<=5;l++)
            {
                Elements td=t.getElementsByTag("td");
                //Log.e("jsoup", i + " " + l + ":" + td.get(l).text());
                String str=td.get(l).text().replaceAll(" ","");
                //Log.e("jsoup", i + " " + l + ":" + str);
                if(!TextUtils.isEmpty(str))
                {
                    rule1(sections,str,i,l*2-1);
                    rule2(sections, str, i, l * 2 - 1);
                }
            }
        }
        Message msg = Message.obtain();
        msg.what = m.getControlCode();
        msg.obj=sections;
        msg.arg1 = Code.RESULT.TRUE;
        m.getHandler().sendMessage(msg);
    }
    private void rule1(List<SimpleSection> sections,String str,int d,int c) //规则类似于"第*周"
    {
        if(str.indexOf("第")!=0)
            return;
        String str1[]=str.split("第");
        for (String value:str1) {
            if(TextUtils.isEmpty(value)==true)
                continue;
            SimpleSection section = new SimpleSection();
            String str2[]=value.split(" ");
            int end=str2[0].indexOf("周");
            if(end<-1)
                continue;
            //Log.e("jsoup-r1",value+'-'+str2[0]+'-'+str2[1]);
            String CourseName=str2[1];
            String Teacher="";
            String Room=str2[2];
            int Day=d;
            int StartSection=c;
            int EndSection=c+1;
            String week=str2[0].substring(0, end);
            int WeekStart,WeekEnd;
            try {
                WeekStart = Integer.parseInt(week);
            }catch (NumberFormatException E)
            {
                WeekStart=1;
                WeekEnd=20;
            }
            WeekEnd=WeekStart;

            section.setCourseName(CourseName);
            section.setTeacher(Teacher);
            section.setRoom(Room);
            section.setDay(Day);// 星期一
            section.setStartSection(StartSection);// 1-2节
            section.setEndSection(EndSection);
            section.setWeekStart(WeekStart);// 1-10周
            section.setWeekEnd(WeekEnd);
            Log.e("jsoup-r1", CourseName+'-'+Teacher+'-'+Room+'-'+Day+'-'+StartSection+'-'+WeekStart);
            sections.add(section);
        }
    }
    private void rule2(List<SimpleSection> sections,String str,int d,int c)//规则类似于"（1-10）"
    {
        if(str.indexOf('(')!=0)
            return;
        String str1[]=str.split("\\(");
        for (String value:str1) {
            if(TextUtils.isEmpty(value)==true)
                continue;
            SimpleSection section = new SimpleSection();
            String str2[]=value.replace(')',' ').split(" ");
            //Log.e("jsoup-r2",value+'-'+str2[0]+'-'+str2[1]);
            String CourseName=str2[2];
            String Teacher=str2[1];
            String Room=str2[3];
            int Day=d;
            int StartSection=c;
            int EndSection=c+1;
            String week[]=str2[0].split("-");
            int WeekStart,WeekEnd;
            try {
                 WeekStart = Integer.parseInt(week[0]);
                 WeekEnd = Integer.parseInt(week[1]);
            }catch (NumberFormatException E)
            {
                 WeekStart=1;
                 WeekEnd=20;
            }
            section.setCourseName(CourseName);
            section.setTeacher(Teacher);
            section.setRoom(Room);
            section.setDay(Day);// 星期一
            section.setStartSection(StartSection);// 1-2节
            section.setEndSection(EndSection);
            section.setWeekStart(WeekStart);// 1-10周
            section.setWeekEnd(WeekEnd);
            Log.e("jsoup-r2", CourseName + '-' + Teacher + '-' + Room + '-' + Day + '-' + StartSection + '-' + WeekStart+'-'+WeekEnd);
            sections.add(section);
        }
    }
}

