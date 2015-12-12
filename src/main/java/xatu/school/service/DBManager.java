package xatu.school.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xatu.school.activity.BaseApplication;
import xatu.school.bean.BaseSingleCourse;
import xatu.school.bean.CourseTable;
import xatu.school.bean.SingleCourse;
import xatu.school.bean.SourceSingleCourse;
import xatu.school.bean.Semester;
import xatu.school.bean.StudentInfo;
import xatu.school.bean.CourseGrades;
import xatu.school.db.UniversityDbOpenHelper;
import xatu.school.utils.SourceToSingleCourse;
import xatu.school.utils.StringUtil;

/**
 * 数据库管理器
 * Created by penfi on 2015/10/21.
 */
public class DBManager {
    private SQLiteDatabase mDb;

    public DBManager() {
        UniversityDbOpenHelper mDbHelper = BaseApplication.getDBHelper();
        this.mDb = mDbHelper.getWritableDatabase();
    }

    public long saveStudentInfo(StudentInfo info) {
        ContentValues values = new ContentValues();
        values.put(StudentInfo.COLUMN_NAME, info.getName());
        values.put(StudentInfo.COLUMN_BANJI, StringUtil.replace(info.getBanji()));
        values.put(StudentInfo.COLUMN_SHENGRI, StringUtil.replace(info.getShengri()));
        values.put(StudentInfo.COLUMN_YUANXI, StringUtil.replace(info.getYuanxi()));
        values.put(StudentInfo.COLUMN_XINGBIE, StringUtil.replace(info.getXingbie()));
        values.put(StudentInfo.COLUMN_XUEHAO, StringUtil.replace(info.getXuehao()));
        values.put(StudentInfo.COLUMN_ZHUANYE, StringUtil.replace(info.getZhuanye()));
        //        Log.i("test_semester", "学生ID：" + id);
        return mDb.insert(StudentInfo.TABLE_NAME, null, values);
    }

    /**
     * 将学期信息存入数据库
     */
    public void saveUniversity(CourseGrades courseGrades) {

        List<Semester> semesters = courseGrades.getSemester();
        ContentValues values = new ContentValues();
        long semesterId;
        for (Semester semester : semesters) {
            values.put(Semester.COLUMN_NAME, semester.getName());
            semesterId = mDb.insert(Semester.TABLE_NAME, null, values);
            values.clear();
//            Log.i("test_semester", semesterId + " 学期：" + semester.getName());
            saveCourse(values, semesterId, semester.getSourceSingleCourses());
        }
    }

    /**
     * 将单科课程存入数据库
     */
    private void saveCourse(ContentValues values, long semesterId, List<BaseSingleCourse> sourceSingleCourses) {
        for (BaseSingleCourse sourceSingleCourse : sourceSingleCourses) {
            SingleCourse singleCourse = SourceToSingleCourse.toSingleCourse((SourceSingleCourse) sourceSingleCourse);
            values.put(SingleCourse.COLUMN_SEMESTER_ID, semesterId);
            values.put(SingleCourse.COLUMN_NAME, singleCourse.getName());
            values.put(SingleCourse.COLUMN_XUEFEN, singleCourse.getXuefen());
            values.put(SingleCourse.COLUMN_CHENGJI, singleCourse.getChengji());
            values.put(SingleCourse.COLUMN_RENKEJIAOSHI, singleCourse.getRenkejiaoshi());
            values.put(SingleCourse.COLUMN_KAOSHILEIXING, singleCourse.getKaoshileixing());
            values.put(SingleCourse.COLUMN_EVALUATE_SCORE, singleCourse.getEvaluateScore());
            values.put(SingleCourse.COLUMN_URL, singleCourse.getUrl());
            values.put(SingleCourse.COLUMN_STATUS, singleCourse.getStatus());
            mDb.insert(SingleCourse.TABLE_NAME, null, values);
            values.clear();
        }
    }

    public void saveCourseTable(CourseTable courseTable) {
        ContentValues values = new ContentValues();
        CourseTable.Day day;
        CourseTable.Subject subject;
        for (int i = 1; i <= 5; i++) {
            day = courseTable.get(i);
            for (int j = 1; j <= 5; j++) {
                subject = day.get(j);
                values.put(CourseTable.COLUMN_day, i);
                values.put(CourseTable.COLUMN_JIECI_ID, j);
                values.put(CourseTable.COLUMN_NAME, subject.courseName);
                values.put(CourseTable.COLUMN_ZHOUCI, subject.zhouci);
                values.put(CourseTable.COLUMN_JIECI, subject.jieci);
                values.put(CourseTable.COLUMN_JIAOSHI, subject.jiaoshi);
                mDb.insert(CourseTable.TABLE_NAME, null, values);
                values.clear();
            }
        }
    }

    public StudentInfo getStudentInfo() {
        StudentInfo info;
        String sql = "select * from " + StudentInfo.TABLE_NAME + " where _id = 1";
        Cursor cursor = mDb.rawQuery(sql, null);
        cursor.moveToFirst();
        info = new StudentInfo(
                cursor.getString(cursor.getColumnIndex(StudentInfo.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(StudentInfo.COLUMN_YUANXI)),
                cursor.getString(cursor.getColumnIndex(StudentInfo.COLUMN_ZHUANYE)),
                cursor.getString(cursor.getColumnIndex(StudentInfo.COLUMN_XINGBIE)),
                cursor.getString(cursor.getColumnIndex(StudentInfo.COLUMN_BANJI)),
                cursor.getString(cursor.getColumnIndex(StudentInfo.COLUMN_XUEHAO)),
                cursor.getString(cursor.getColumnIndex(StudentInfo.COLUMN_SHENGRI)));
        cursor.close();
        return info;
    }

    public CourseGrades getCourseGrades() {
        CourseGrades courseGrades = new CourseGrades();
        String sql = "select * from " + Semester.TABLE_NAME;
        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            Cursor cursorCourse;
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String sqlCourse = "select * from " +
                        SingleCourse.TABLE_NAME + " where " + SingleCourse.COLUMN_SEMESTER_ID
                        + " = " + id;
                cursorCourse = mDb.rawQuery(sqlCourse, null);
                List<BaseSingleCourse> singleCourses = new ArrayList<>();
                if (cursorCourse.moveToFirst()) {
                    do {
                        singleCourses.add(new SingleCourse(
                                cursorCourse.getString(cursorCourse.getColumnIndex(SingleCourse.COLUMN_NAME)),
                                cursorCourse.getFloat(cursorCourse.getColumnIndex(SingleCourse.COLUMN_XUEFEN)),
                                cursorCourse.getInt(cursorCourse.getColumnIndex(SingleCourse.COLUMN_CHENGJI)),
                                cursorCourse.getString(cursorCourse.getColumnIndex(SingleCourse.COLUMN_RENKEJIAOSHI)),
                                cursorCourse.getString(cursorCourse.getColumnIndex(SingleCourse.COLUMN_KAOSHILEIXING)),
                                cursorCourse.getFloat(cursorCourse.getColumnIndex(SingleCourse.COLUMN_EVALUATE_SCORE)),
                                cursorCourse.getString(cursorCourse.getColumnIndex(SingleCourse.COLUMN_URL)),
                                cursorCourse.getInt(cursorCourse.getColumnIndex(SingleCourse.COLUMN_STATUS))
                        ));
                    } while (cursorCourse.moveToNext());
                }
                cursorCourse.close();
                courseGrades.addSemester(new Semester(
                        cursor.getString(cursor.getColumnIndex(Semester.COLUMN_NAME)), singleCourses));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseGrades;
    }

    public List<SingleCourse> getSingleCourses() {
        List<SingleCourse> singleCourses = new ArrayList<>();

        String sql = "select * from " + SingleCourse.TABLE_NAME;
        Cursor cursor = mDb.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                singleCourses.add(new SingleCourse(
                        cursor.getString(cursor.getColumnIndex(SingleCourse.COLUMN_NAME)),
                        cursor.getFloat(cursor.getColumnIndex(SingleCourse.COLUMN_XUEFEN)),
                        cursor.getInt(cursor.getColumnIndex(SingleCourse.COLUMN_CHENGJI)),
                        cursor.getString(cursor.getColumnIndex(SingleCourse.COLUMN_RENKEJIAOSHI)),
                        cursor.getString(cursor.getColumnIndex(SingleCourse.COLUMN_KAOSHILEIXING)),
                        cursor.getFloat(cursor.getColumnIndex(SingleCourse.COLUMN_EVALUATE_SCORE)),
                        cursor.getString(cursor.getColumnIndex(SingleCourse.COLUMN_URL)),
                        cursor.getInt(cursor.getColumnIndex(SingleCourse.COLUMN_STATUS))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return singleCourses;
    }

    public CourseTable getCourseTable() {
        CourseTable courseTable = new CourseTable();
        String sql = "select * from " + CourseTable.TABLE_NAME;
        Cursor cursor = mDb.rawQuery(sql, null);
        String courseName, zhouci, jieci, jiaoshi;
        CourseTable.Subject subject;
        if (cursor.moveToFirst()) {
            do {
                courseName = cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_NAME));// 课程名
                zhouci = cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_ZHOUCI));//周次
                jieci = cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_JIECI));// 节次
                jiaoshi = cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_JIAOSHI));// 教室

                subject = courseTable.get(cursor.getInt(cursor.getColumnIndex(CourseTable.COLUMN_day))).get(
                        cursor.getInt(cursor.getColumnIndex(CourseTable.COLUMN_JIECI_ID)));

                subject.courseName = courseName;
                subject.zhouci = zhouci;
                subject.jieci = jieci;
                subject.jiaoshi = jiaoshi;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseTable;
    }

    /**
     * 更新单科课程
     */
    public void updateSingleCourse(SingleCourse newSingleCourse) {
        String sql = "update " + SingleCourse.TABLE_NAME + " set " +
                SingleCourse.COLUMN_CHENGJI + " = " + newSingleCourse.getChengji() + ", " +
                SingleCourse.COLUMN_EVALUATE_SCORE + " = " + newSingleCourse.getEvaluateScore() + ", " +
                SingleCourse.COLUMN_STATUS + " = " + newSingleCourse.getStatus() + " where " +
                SingleCourse.COLUMN_NAME + " = '" + newSingleCourse.getName() + "'";
        mDb.execSQL(sql);
    }

    /**
     * 清空数据库所有的表
     */
    public void clearAllTable() {
        mDb.execSQL("delete from " + Semester.TABLE_NAME);  //清空学期表数据
        mDb.execSQL("update sqlite_sequence SET seq = 0 where name ='" +
                Semester.TABLE_NAME + "'");//设置自增长ID为0

        mDb.execSQL("delete from " + SingleCourse.TABLE_NAME);  //清空课程信息表数据
        mDb.execSQL("update sqlite_sequence SET seq = 0 where name ='" +
                SingleCourse.TABLE_NAME + "'");//设置自增长ID为0

        mDb.execSQL("delete from " + StudentInfo.TABLE_NAME);  //清空学生个人信息表数据
        mDb.execSQL("update sqlite_sequence SET seq = 0 where name ='" +
                StudentInfo.TABLE_NAME + "'");//设置自增长ID为0

        mDb.execSQL("delete from " + CourseTable.TABLE_NAME);  //清空课程表信息表数据
        mDb.execSQL("update sqlite_sequence SET seq = 0 where name ='" +
                CourseTable.TABLE_NAME + "'");//设置自增长ID为0
    }
}
