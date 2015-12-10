package xatu.school.control;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xatu.school.bean.BaseSingleCourse;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.FileBean;
import xatu.school.bean.InitMsg;
import xatu.school.bean.ScoreItem;
import xatu.school.bean.Semester;
import xatu.school.bean.SingleCourse;
import xatu.school.exception.EvaluateException;
import xatu.school.service.CourseEvaluateImp;
import xatu.school.service.DBManager;
import xatu.school.service.ICourseEvaluate;
import xatu.school.utils.CookieUtil;
import xatu.school.utils.Code;
import xatu.school.utils.CreateInitMsg;

/**
 * 所有成绩界面 控制器
 * Created by penfi on 2015/11/10.
 */
public class CourseGradesManager {
    private static CourseGradesManager mInstance;// 单例模式

    private CourseGradesManager() {
    }

    public static CourseGradesManager getInstance() {
        if (mInstance == null) {
            synchronized (LoginManager.class) {
                if (mInstance == null) {
                    mInstance = new CourseGradesManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取课程成绩信息
     */
    public List<FileBean> getCourseGradesInfo() {
        List<FileBean> data = new ArrayList<>();
        DBManager dbManager = new DBManager();
        CourseGrades courseGrades = dbManager.getCourseGrades();
        List<Semester> semesters = courseGrades.getSemester();
        Semester mSemester;
        int maxSemester = semesters.size() + 1;
        for (int semester = 0; semester < semesters.size(); semester++) {
            mSemester = semesters.get(semester);
            ScoreItem scoreItem = new ScoreItem();
            scoreItem.setSession(mSemester.getName());
            FileBean fileBean = new FileBean(semester + 1, 0, scoreItem);// 父
            data.add(fileBean);
            addSingleCourse(maxSemester, semester + 1, data, mSemester.getSourceSingleCourses());// 子
        }
        return data;
    }

    private void addSingleCourse(int maxSemester, int semester, List<FileBean> data, List<BaseSingleCourse> mSemester) {
        SingleCourse singleCourse;
        for (int courseId = 0; courseId < mSemester.size(); courseId++) {
            singleCourse = (SingleCourse) mSemester.get(courseId);
            String grade;
            switch (singleCourse.getStatus()) {
                case 1:
                    grade = "未考试";
                    break;
                case 2:
                    grade = "归档中";
                    break;
                case 3:
                    grade = "未评价";
                    break;
                case 4:
                    grade = String.valueOf(singleCourse.getChengji());
                    break;
                default:
                    grade = "未知错误";
                    break;
            }

            ScoreItem scoreItem = new ScoreItem();
            scoreItem.setCourseName(singleCourse.getName());
            scoreItem.setCouresScore(grade);
            FileBean fileBean = new FileBean(maxSemester, semester, scoreItem);
            data.add(fileBean);
        }
    }

    /**
     * 课程评教
     *
     * @param context      上下文
     * @param handler      handler
     * @param singleCourse 要评教的单科课程对象
     * @param radios       单选 整形数组 值：1->A,2->B,3->C,4->D,5->E
     */
    public void evaluate(Context context, Handler handler, SingleCourse singleCourse, int[] radios) {
        EvaluateInfo info = null;
        try {
            info = new EvaluateInfo(singleCourse, radios);

            ICourseEvaluate courseEvaluate = new CourseEvaluateImp();
            courseEvaluate.evaluate(CreateInitMsg.msg(context, handler, Code.CONTROL.COURSE_EVALUATE), info);
        } catch (EvaluateException e) {
            Message msg = Message.obtain();
            msg.what = Code.CONTROL.COURSE_EVALUATE;
            msg.arg1 = Code.RESULT.FALSE;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }
/*        finally {
            Log.i("evaluate", "课程评教 " + singleCourse.getName());
            for (int radio : radios) {
                Log.i("evaluate", "内容：" + radio);
            }
        }*/
    }

    /**
     * 根据课程名获得课程信息
     * 异步返回 原始单科课程 对象
     *
     * @param courseName 课程名
     */
    public void getSingleCourse(Context context, Handler handler, String courseName) {
        ICourseEvaluate courseEvaluate = new CourseEvaluateImp();
        courseEvaluate.getSingleCourse(CreateInitMsg.msg(context, handler, Code.CONTROL.GET_SINGLECOURSE), courseName);
    }

    /**
     * 将新的单科课程更新到数据库
     *
     * @param newSingleCourse 新的单科课程
     */
    public void updateSingleCourseToDB(SingleCourse newSingleCourse) {
        DBManager dbManager = new DBManager();
        dbManager.updateSingleCourse(newSingleCourse);
    }
}
