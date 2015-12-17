package xatu.school.activity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import xatu.school.bean.CourseGrades;
import xatu.school.bean.CourseTable;
import xatu.school.bean.StudentInfo;
import xatu.school.control.LoginManager;
import xatu.school.utils.Code;

/**
 * 登录界面 后台服务
 * Created by penfi on 2015/11/10.
 */
public class SchoolLoginService extends Service {
    public MyBinder mBinder = new MyBinder();
    private int time = 3;

    @Override
    public void onCreate() {
//        Log.i("login_service", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i("login_service", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
//        Log.i("login_service", "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
//        Log.i("login_service", "onBind");
        return mBinder;
    }

    class MyBinder extends Binder {

        public void saveStudentInfo(final StudentInfo studentInfo) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoginManager.getInstance().saveStudentInfo(studentInfo);
                    SchoolLoginService.this.sendBroadcast(Code.CONTROL.STUDENTINFO);
                }
            }).start();
        }

        public void saveCourseGradesInfo(final CourseGrades courseGrades) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoginManager.getInstance().saveCourseGradesInfo(courseGrades);
                    SchoolLoginService.this.sendBroadcast(Code.CONTROL.COURSEGRADES);
                }
            }).start();
        }

        public void saveCourseTableInfo(final CourseTable courseTable) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoginManager.getInstance().saveCourseTable(courseTable);
                    SchoolLoginService.this.sendBroadcast(Code.CONTROL.COURSETABLE);
                }
            }).start();
        }
    }

    private void sendBroadcast(int coursetable) {
        if (--time == 0) {
            Intent intent = new Intent();
            intent.setAction(LoginActivity.MyReceiver.RECEIVER);
            sendBroadcast(intent);
        }
    }
}