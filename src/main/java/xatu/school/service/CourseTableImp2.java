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
import java.util.ArrayList;
import java.util.List;

import feimeng.coursetableview.Section;
import feimeng.coursetableview.SimpleSection;
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
