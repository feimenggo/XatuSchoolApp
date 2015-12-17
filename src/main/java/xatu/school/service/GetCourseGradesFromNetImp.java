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
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import xatu.school.bean.BaseSingleCourse;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.InitMsg;
import xatu.school.bean.Semester;
import xatu.school.bean.SourceSingleCourse;
import xatu.school.utils.Code;

/**
 * Created by Administrator on 2015-10-25.
 */
public class GetCourseGradesFromNetImp implements IGetCourseGradesFromNet {

    @Override
    public void getCourseGrades(final InitMsg m) {

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
        String yuanshichengji = null;
        String zhuanhuanchengji = null;
        String jidian = null;
        String renkejiaoshi = null;
        String kaoshileixing = null;
        String kaoshishijian = null;
        String kaoshifangshi = null;
        String zhuangtai = null;
        String caozuo = null;
        String url = null;
        Document doc = Jsoup.parse(htmlOfsc);
        Elements link = doc.getElementsByTag("table");
        int i = 0;
        for (Element es : link) {
            Elements ele = es.getElementsByTag("td");
            for (Element e : ele) {
//                Log.e("html", e.text());
                if (count != 0) {
                    String str = e.text();
                    if (count == 1) {

                        int index = str.indexOf('-');
                        if (index > 0 && str.indexOf("学期") > 0) {
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
                        switch (count % 12) {
                            case 2:
                                xuefen = str;
                                break;
                            case 3:
                                yuanshichengji = str;
                                break;
                            case 4:
                                zhuanhuanchengji = str;
                                break;
                            case 5:
                                jidian = str;
                                break;
                            case 6:
                                renkejiaoshi = str;
                                break;
                            case 7:
                                kaoshileixing = str;
                                break;
                            case 8:
                                kaoshishijian = str;
                                break;
                            case 9:
                                kaoshifangshi = str;
                                break;
                            case 10:
                                zhuangtai = str;
                                break;
                            case 11: {
//                                Log.e("mmm", e.toString());
                                String reg[] = e.toString().split("\"");
                                url = reg[3].replaceAll("appraise", "apppost").replaceAll("amp;", "");
                                // Log.e("test url",url);
                                //http://222.25.1.101/student/
                                // apppost.asp?SRID=5675428&amp;tcid=94750&amp;atyid=30&amp;ccid=5116
                                caozuo = str.split(" ")[1];
                            }
                            break;
                            default:
                                break;
                        }
                }
                if (count == 0)
                    count++;
                else {
                    count = count % 11 + 1;
                    if (count == 1) {
                        SourceSingleCourse ss = new SourceSingleCourse(name, xuefen, yuanshichengji, zhuanhuanchengji, jidian, renkejiaoshi, kaoshileixing, kaoshishijian, kaoshifangshi, zhuangtai, caozuo, url);
                        int tmp = get_real_chengji(ss);
//                        Log.e("num", String.valueOf(tmp));
                        ss.setYuanshichengji(String.valueOf(tmp));
                        sc.getSemester().get(i - 1).addCourse(ss);
                    }

                }
            }
        }
        margeSC(sc, m);
//        Log.e("html", "OK");
    }

    static public int get_real_chengji(SourceSingleCourse c) {
        int max = 0;
        String reg = "\\d+";
        boolean b1 = Pattern.compile(reg).matcher(c.getZhuanhuanchengji()).find();
        boolean b2 = Pattern.compile(reg).matcher(c.getJidian()).find();
        boolean b3 = Pattern.compile(reg).matcher(c.getYuanshichengji()).find();

        if (b1) {
            int tmp = Integer.parseInt(replace(c.getZhuanhuanchengji()));
            max = max > tmp ? max : tmp;
        }
        if (b2) {
            int tmp = Integer.parseInt(replace(c.getJidian()));
            max = max > tmp ? max : tmp;
        }
        if (b3) {
            int tmp = Integer.parseInt(replace(c.getYuanshichengji()));
            max = max > tmp ? max : tmp;
        }
        return max;
    }

    public static String replace(String str) {
        return str.replace("  ", "").replace(" ", "").replace(" ", "");
    }

    private void margeSC(CourseGrades sc, InitMsg m) {
        for (int i = 0; i < sc.getSemester().size(); i++) {
            for (int l = i + 1; l < sc.getSemester().size(); l++) {
                if (sc.getSemester().get(i).getName().equals(sc.getSemester().get(l).getName())) {
                    for (BaseSingleCourse c : sc.getSemester().get(l).getSourceSingleCourses()) {
                        sc.getSemester().get(i).addCourse((SourceSingleCourse) c);
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
