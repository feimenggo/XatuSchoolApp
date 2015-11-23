package xatu.school.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import xatu.school.bean.CourseTable;
import xatu.school.bean.SourceSingleCourse;
import xatu.school.bean.Semester;
import xatu.school.bean.StudentInfo;

/**
 * Created by penfi on 2015/10/21.
 */
public class UniversityDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "university.db";// 数据库文件名字
    private static final int DB_VERSION = 1;// 数据库版本号


    private static UniversityDbOpenHelper mInstance;// 单例模式

    public static UniversityDbOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UniversityDbOpenHelper.class) {
                if (mInstance == null) {
                    mInstance = new UniversityDbOpenHelper(context);
                }
            }
        }
        return mInstance;
    }

    private UniversityDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建课程表
        String sql = "create table " + SourceSingleCourse.TABLE_NAME + " ( " +
                "_id integer primary key autoincrement , " +
                SourceSingleCourse.COLUMN_SEMESTER_ID + " integer , " +
                SourceSingleCourse.COLUMN_NAME + " text , " +
                SourceSingleCourse.COLUMN_KAOSHIFANGSHI + " text , " +
                SourceSingleCourse.COLUMN_KAOSHILEIXING + " text , " +
                SourceSingleCourse.COLUMN_KECHENGLEIBIE + " text , " +
                SourceSingleCourse.COLUMN_RENKEJIAOSHI + " text , " +
                SourceSingleCourse.COLUMN_YUANSHICHENGJI + " text , " +
                SourceSingleCourse.COLUMN_JIDIAN + " text , " +
                SourceSingleCourse.COLUMN_XUEFEN + " text , " +
                SourceSingleCourse.COLUMN_ZHUANGTAI + " text" +
                ")";
        sqLiteDatabase.execSQL(sql);
        // 创建学期表
        sql = "create table " + Semester.TABLE_NAME + " ( " +
                "_id integer primary key autoincrement , " +
                Semester.COLUMN_NAME + " text" +
                ")";
        sqLiteDatabase.execSQL(sql);
        // 创建学生信息表
        sql = "create table " + StudentInfo.TABLE_NAME + " ( " +
                "_id integer primary key autoincrement , " +
                StudentInfo.COLUMN_NAME + " text ," +
                StudentInfo.COLUMN_BANJI + " text , " +
                StudentInfo.COLUMN_SHENGRI + " text , " +
                StudentInfo.COLUMN_XIBU + " text , " +
                StudentInfo.COLUMN_XINGBIE + " text , " +
                StudentInfo.COLUMN_XUEHAO + " text , " +
                StudentInfo.COLUMN_ZHUANYE + " text" +
                ")";
        sqLiteDatabase.execSQL(sql);
        // 创建课程表表
        sql = "create table " + CourseTable.TABLE_NAME + " ( " +
                "_id integer primary key autoincrement , " +
                CourseTable.COLUMN_day + " integer , " +
                CourseTable.COLUMN_JIECI_ID + " integer , " +
                CourseTable.COLUMN_NAME + " text , " +
                CourseTable.COLUMN_ZHOUCI + " text , " +
                CourseTable.COLUMN_JIECI + " text , " +
                CourseTable.COLUMN_JIAOSHI + " text " +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
