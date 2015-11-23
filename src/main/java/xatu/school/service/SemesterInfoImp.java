package xatu.school.service;

import android.os.Message;

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
import xatu.school.bean.CourseGrades;
import xatu.school.bean.InitMsg;
import xatu.school.bean.Semester;
import xatu.school.bean.SourceSingleCourse;
import xatu.school.utils.Code;

/**
 * Created by Administrator on 2015-10-25.
 */
public class SemesterInfoImp implements ISemesterInfo {

    @Override
    public void getSemesters(final InitMsg m) {

        AsyncHttpClient clientget = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(m.getContext());
        clientget.setCookieStore(myCookieStore);
        clientget.addHeader("Referer", "http://222.25.1.101/student/index.asp");
        clientget.addHeader("Host", "222.25.1.101");
        clientget.get("http://222.25.1.101/student/Report.asp?tmid=1", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String htmlOfsc = new String(responseBody, "GB2312");
                    // Log.e("html", htmlOfsc);
                    jsoupSC(htmlOfsc, m);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.e("html", String.valueOf(statusCode));
                Message msg = Message.obtain();
                msg.what = m.getControlCode();
                msg.obj = null;
                msg.arg1 = Code.RESULT.FALSE;
                m.getHandler().sendMessage(msg);
            }
        });
    }

    private void jsoupSC(String htmlOfsc, InitMsg m) {
        int count = 0;
        CourseGrades sc = new CourseGrades();
        String name = null;
        String xuefen = null;
        String kechengleibie = null;
        String yuanshichengji = null;
        String zhuanhuanchengji = null;
        String jidian = null;
        String mingci = null;
        String renshu = null;
        String renkejiaoshi = null;
        String kaoshileixing = null;
        String kaoshishijian = null;
        String kaoshifangshi = null;
        String zhuangtai = null;
        String caozuo = null;
        Document doc = Jsoup.parse(htmlOfsc);
        Elements link = doc.getElementsByTag("table");
        List<Semester> xq = new ArrayList<Semester>();
        int i = 0;
        for (Element es : link) {
            Elements ele = es.getElementsByTag("td");
            for (Element e : ele) {
//                Log.e("html", e.text());
                if (count != 0) {
                    String str = e.text();
                    if (count == 1) {

                        int index = str.indexOf('-');
                        if (index == 2) {
                            sc.addSemester(new Semester());
                            sc.getSemester().get(i).setName(str);
                            i++;
                            continue;
                        } else {
                            String s[] = str.split(" ");
                            if (s.length == 2)
                                name = s[1];
                            else {
                                s = str.split("&nbsp");
                                if (s.length == 2)
                                    name = s[1];
                                else
                                    name = s[0];
                            }
                        }
                    } else
                        switch (count % 14) {
                            case 2:
                                xuefen = str;
                                break;
                            case 3:
                                kechengleibie = str;
                                break;
                            case 4:
                                yuanshichengji = str;
                                break;
                            case 5:
                                zhuanhuanchengji = str;
                                break;
                            case 6:
                                jidian = str;
                                break;
                            case 7:
                                mingci = str;
                                break;
                            case 8:
                                renshu = str;
                                break;
                            case 9:
                                renkejiaoshi = str;
                                break;
                            case 10:
                                kaoshileixing = str;
                                break;
                            case 11:
                                kaoshishijian = str;
                                break;
                            case 12:
                                kaoshifangshi = str;
                                break;
                            case 13:
                                zhuangtai = str;
                                break;
                            case 14:
                                caozuo = str;
                                break;
                            default:
                                break;
                        }
                }
                if (count == 0) count++;
                else {
                    count = count % 14 + 1;
                    if (count == 1)
                        sc.getSemester().get(i - 1).addCourse(new SourceSingleCourse(name, xuefen, kechengleibie, yuanshichengji, zhuanhuanchengji, jidian, mingci, renshu, renkejiaoshi, kaoshileixing, kaoshishijian, kaoshifangshi, zhuangtai, caozuo));

                }
            }
        }
        margeSC(sc, m);
//        Log.e("html", "OK");
    }

    private void margeSC(CourseGrades sc, InitMsg m) {
        for (int i = 0; i < sc.getSemester().size(); i++) {
            for (int l = i + 1; l < sc.getSemester().size(); l++) {
                if (sc.getSemester().get(i).getName().equals(sc.getSemester().get(l).getName()) == true) {
                    for (SourceSingleCourse c : sc.getSemester().get(l).getSourceSingleCourses()) {
                        sc.getSemester().get(i).addCourse(c);
                    }
                    sc.getSemester().remove(l);
                    --l;
                }
            }
        }
        Message msg = Message.obtain();
        msg.what = m.getControlCode();
        msg.obj = sc;
        msg.arg1 = Code.RESULT.TRUE;
        m.getHandler().sendMessage(msg);
    }
}
