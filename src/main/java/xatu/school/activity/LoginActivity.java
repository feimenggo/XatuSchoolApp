package xatu.school.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xatu.school.R;
import xatu.school.bean.CourseTable;
import xatu.school.bean.StudentInfo;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.WebError;
import xatu.school.control.LoginManager;
import xatu.school.utils.CheckcodeOcr;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;
import xatu.school.utils.NetworkUtil;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mUsername;// 账号
    private EditText mPassword;// 密码
    private ImageView mIvCheckcode;// 验证码图片
    private EditText mCheckcode;// 验证码
    private Button mLogin;// 登录按钮
    private FrameLayout mProgress;// 进度条
    private TextView mProgressContent;// 进度条显示文字

    private String mUsernameValue;// 用户名
    private String mPasswordValue;// 密码

    private SchoolLoginService.MyBinder binder;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == Code.RESULT.TRUE) {
                switch (msg.what) {
                    case Code.CONTROL.CHECKCODE:// 获取验证码
                        Bitmap bitmap = (Bitmap) msg.obj;
                        mIvCheckcode.setImageBitmap(bitmap);
                        if (mIvCheckcode.isOpaque())
                            mIvCheckcode.setImageAlpha(255);

                        // 识别验证码
                        String text = null;
                        try {
                            text = CheckcodeOcr.getOcr(LoginActivity.this, bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mCheckcode.setText(text);

                        break;
                    case Code.CONTROL.LOGIN:// 登录
                        // 修改登录状态 (PersistentCookieStore 会自动保存Cookie信息)
                        BaseApplication.getEditor().putBoolean(BaseApplication.SP_IS_LOGIN, true);

                        // 保存登录信息
                        BaseApplication.getEditor().putString(BaseApplication.SP_USERNAME, mUsernameValue);
                        BaseApplication.getEditor().putString(BaseApplication.SP_PASSWORD, mPasswordValue);
                        // 更新cookie时间
//                        BaseApplication.getEditor().putLong(BaseApplication.SP_COOKIE_TIME, System.currentTimeMillis());
                        CookieUtil.updateCookieTime(false);
                        BaseApplication.getEditor().apply();

                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        mProgressContent.setText("正在加载数据...");

                        //从网络获取学生信息
                        LoginManager.getInstance().getStudentInfoFromWeb(LoginActivity.this, mHandler);
                        //从网络获取全年级课程成绩
                        LoginManager.getInstance().getCourseGradesFromWeb(LoginActivity.this, mHandler);
                        //从网络获取课程表信息
                        LoginManager.getInstance().getCourseTableFromWeb(LoginActivity.this, mHandler);
                        break;
                    case Code.CONTROL.STUDENTINFO:// 得到个人信息
                        // 将学生信息存入数据库
                        binder.saveStudentInfo((StudentInfo) msg.obj);
                        break;
                    case Code.CONTROL.COURSEGRADES:// 得到大学年级信息
                        // 将年级信息存入数据库
                        binder.saveCourseGradesInfo((CourseGrades) msg.obj);
                        break;
                    case Code.CONTROL.COURSETABLE:// 得到课程表信息
                        binder.saveCourseTableInfo((CourseTable) msg.obj);
                        break;
                    case Code.CONTROL.LOGIN_WITH_OCR://登录，自动识别验证码
                        // 修改登录状态 (PersistentCookieStore 会自动保存Cookie信息)
                        BaseApplication.getEditor().putBoolean(BaseApplication.SP_IS_LOGIN, true);

                        // 保存登录信息
                        BaseApplication.getEditor().putString(BaseApplication.SP_USERNAME, mUsernameValue);
                        BaseApplication.getEditor().putString(BaseApplication.SP_PASSWORD, mPasswordValue);
                        // 更新cookie时间
                        CookieUtil.updateCookieTime(false);
                        BaseApplication.getEditor().apply();

                        mProgressContent.setText("正在加载数据...");

                        //从网络获取学生信息
                        LoginManager.getInstance().getStudentInfoFromWeb(LoginActivity.this, mHandler);
                        //从网络获取全年级课程成绩
                        LoginManager.getInstance().getCourseGradesFromWeb(LoginActivity.this, mHandler);
                        //从网络获取课程表信息
                        LoginManager.getInstance().getCourseTableFromWeb(LoginActivity.this, mHandler);
                        break;
                }
            } else {
                String error;
                if (msg.obj != null) {
                    switch ((WebError) msg.obj) {
                        case userAndPwdError:
                            error = "用户名或密码错误";
                            reset(true);
                            break;
                        case checkcodeError:
                            error = "验证码错误";
                            reset(false);
                            break;
                        case noResponse:
                            error = "服务器无响应";
                            reset(false);
                            break;
                        case other:
                            error = "未知错误";
                            reset(false);
                            break;
                        default:
                            error = "未知错误来源";
                            reset(false);
                            break;
                    }
                } else {
                    error = "未知错误来源";
                }
                reset(true);
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (SchoolLoginService.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private MyReceiver receiver;

    private void reset(boolean cleanUser) {
        // 清空输入信息
        mCheckcode.setText("");
        if (cleanUser) {
            mUsername.setText("");
            mPassword.setText("");
            //将焦点设置到学号
            mUsername.requestFocus();
        } else {
            mCheckcode.requestFocus();
        }

        //隐藏进度条
        mProgress.setVisibility(View.GONE);
        //激活登录按钮
        mLogin.setClickable(true);
        //隐藏当前验证码
        mIvCheckcode.setImageAlpha(0);
        //获取新的验证码
        LoginManager.getInstance().checkcode(LoginActivity.this, mHandler);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();// 视图初始化
        initEvent();// 事件初始化
//        initCheckcode();// 获取验证码
        initReceiver();// 初始化服务
    }

    private void initReceiver() {
        //绑定服务
        startService();
        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyReceiver.RECEIVER);
        this.registerReceiver(receiver, filter);
    }

    private void stopReceiver() {
        unregisterReceiver(receiver);
    }

    /**
     * 事件初始化
     */
    private void initEvent() {
        mLogin.setOnClickListener(this);
        mIvCheckcode.setOnClickListener(this);
    }

    /**
     * 视图初始化
     */
    private void initViews() {
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mCheckcode = (EditText) findViewById(R.id.et_checkcode);
        mIvCheckcode = (ImageView) findViewById(R.id.iv_checkcode);
        mLogin = (Button) findViewById(R.id.btn_login);
        mProgress = (FrameLayout) findViewById(R.id.login_progress);
        mProgressContent = (TextView) findViewById(R.id.progressbar_content);
    }


    /**
     * 跳转到主界面
     */
    private void goToMainActivity() {
        mProgress.setVisibility(View.GONE);
        // 跳转到主界面
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        // 销毁登录界面
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_checkcode:
                changeCheckcode();
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }


    private void startService() {
        Intent intent = new Intent(LoginActivity.this, SchoolLoginService.class);//显示调用
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void stopService() {
        unbindService(conn);
    }

    /**
     * 更换验证码
     */
    private void changeCheckcode() {
        initCheckcode();
    }

    /**
     * 登录
     */
    private void login() {
        // 检测网络
        if (!checkNetwork()) return;

        String username = mUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(LoginActivity.this, "学号不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
//        String checkcode = mCheckcode.getText().toString();
//        if (TextUtils.isEmpty(checkcode)) {
//            Toast.makeText(LoginActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
//            return;
//        }
        mProgressContent.setText("正在登录...");
        //显示进度条
        mProgress.setVisibility(View.VISIBLE);

        mUsernameValue = username;
        mPasswordValue = password;
        //学生登录操作
//        LoginManager.getInstance().login(this, mHandler, username,
//                password, checkcode);
        //使用登录自动识别验证码
        LoginManager.getInstance().login(this, mHandler, username, password);
    }

    /**
     * 验证码初始化
     */
    public void initCheckcode() {
        if (!checkNetwork()) return;
        //获取验证码 操作
        LoginManager.getInstance().checkcode(this, mHandler);
    }

    private boolean checkNetwork() {
        if (NetworkUtil.isConnectingToInternet(this)) {
            return true;
        } else {
            // 不存在网络时，进行的操作
            Toast.makeText(LoginActivity.this, "请先连接网络", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        stopService();// 关闭服务
        stopReceiver();// 关闭广播
        super.onDestroy();
    }

    /**
     * 获取广播数据
     */
    public class MyReceiver extends BroadcastReceiver {
        public static final String RECEIVER = "xatu.school.LoginService";

        @Override
        public void onReceive(Context context, Intent intent) {
            goToMainActivity();
        }
    }
}