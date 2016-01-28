package xatu.school.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;

import xatu.school.bean.InitMsg;
import xatu.school.bean.WebError;
import xatu.school.utils.CheckcodeOcr;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;

/**
 * 学生登录接口实现
 * Created by feimeng on 2016/1/27.
 */
public class StudentLoginImp2 implements IStudentLogin {
    @Override
    public void getCheckcodePic(InitMsg msg) {
//        String cookie = CookieUtil.getCookieContent();// 最近的Cookie
//        String url = "http://222.25.1.101/js/checkcode.asp";// 验证码地址
//
//        OkHttpClient client = new OkHttpClient();
//        String Host = "222.25.1.101";
//        String User_Agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0";
//        String Accept = "image/png,image/*;q=0.8,*/*;q=0.5";
//        String Accept_Language = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
//        String Referer = "http://222.25.1.101/index.htm";
//        String Connection = "keep-alive";
//        String Cache_Control = "max-age=0";
//        Request request = new Request.Builder().url(url)
//                .header("Host", Host)
//                .header("User-Agent", User_Agent)
//                .header("Accept", Accept)
//                .header("Accept-Language", Accept_Language)
//                .header("Referer", Referer)
//                .header("Cookie", cookie)
//                .header("Connection", Connection)
//                .header("Cache-Control", Cache_Control).build();
//        Call call = client.newCall(request);
//        try {
//            Response response = call.execute();
//            if (!response.isSuccessful())
//                throw new IOException("出错：" + response.code());
//            // 当前Cookie
//            String currCookie = response.header("Set-Cookie");
//            if (currCookie != null) {
//                // 更新Cookie
//                CookieUtil.updateCookieContent(currCookie);
//            }
//            // 图片文件
//            byte[] pic = response.body().bytes();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
//            Message newMsg = Message.obtain();
//            newMsg.what = msg.getControlCode();
//            if (bitmap == null) {
//                newMsg.arg1 = Code.RESULT.FALSE;
//            } else {
//                newMsg.arg1 = Code.RESULT.TRUE;
//            }
//            newMsg.obj = bitmap;
//            msg.getHandler().sendMessage(newMsg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void login(InitMsg msg, String username, String password, String checkcode) throws IOException {
        String cookie = CookieUtil.getCookieContent();// 最近的Cookie
        String url = "http://222.25.1.101/userlog.asp";
        String mynum = "1";

        String Host = "222.25.1.101";
        String User_Agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0";
        String Accept = "image/png,image/*;q=0.8,*/*;q=0.5";
        String Accept_Language = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
        String Referer = "http://222.25.1.101/index.htm";
        String Connection = "keep-alive";
        String Cache_Control = "max-age=0";
        // 创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        client.setFollowRedirects(false);// 禁止跟随重定向

        // 构建POST参数
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("username", username);
        builder.add("password", password);
        builder.add("CheckCode", checkcode);
        builder.add("mynum", mynum);
        builder.add("btnlog", "登 陆");

        // 创建Request对象
        Request request = new Request.Builder().url(url)
                .header("Host", Host)
                .header("User-Agent", User_Agent)
                .header("Accept", Accept)
                .header("Accept-Language", Accept_Language)
                .header("Referer", Referer)
                .header("Cookie", cookie)
                .header("Connection", Connection)
                .header("Cache-Control", Cache_Control).post(builder.build()).build();

        // 创建一个Call对象
        Call call = client.newCall(request);

        // 创建消息
        Message newMsg = Message.obtain();
        newMsg.what = msg.getControlCode();

        Response response = call.execute();
        if (response.isRedirect()) {
            // 重定向
            String res = response.header("Location");
            Log.i("tag", "Location：" + res);
            // 判断结果
            switch (res) {
                case "message.asp?id=2":
                    newMsg.arg1 = Code.RESULT.FALSE;
                    newMsg.obj = WebError.userAndPwdError;
                    Log.i("tag", "用户名或密码错误");
                    break;
                case "message.asp?id=3":
                    newMsg.arg1 = Code.RESULT.FALSE;
                    newMsg.obj = WebError.checkcodeError;
                    Log.i("tag", "验证码错误");
                    break;
                case "student/index.asp":
                    newMsg.arg1 = Code.RESULT.TRUE;
                    Log.i("tag", "登录成功");
                    break;
                default:
                    newMsg.arg1 = Code.RESULT.FALSE;
                    newMsg.obj = WebError.OTHER;
                    Log.i("tag", "其它错误");
                    break;
            }
        } else {
            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.OFTEN;
            Log.i("tag", "登录频繁：" + new String(response.body().bytes(), "GB2312"));
        }
        // 发送消息
        msg.getHandler().sendMessage(newMsg);
//        Log.i("tag", "------------------------------------------------");
//        Headers responseHeaders = response.headers();
//        Log.i("tag", "response:" + response + ";redirect:" + response.isRedirect());
//        for (int i = 0; i < responseHeaders.size(); i++) {
//            Log.i("tag", responseHeaders.name(i) + ": " +
//                    responseHeaders.value(i));
//        }
//        Log.i("tag", "------------------------------------------------");
    }

    @Override
    public void logout(InitMsg msg) {

    }

    @Override
    public void loginWithOcr(InitMsg msg, String username, String password) {
        //获取验证码
        String cookie = CookieUtil.getCookieContent();// 最近的Cookie
        String url = "http://222.25.1.101/js/checkcode.asp";// 验证码地址

        OkHttpClient client = new OkHttpClient();
        String Host = "222.25.1.101";
        String User_Agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0";
        String Accept = "image/png,image/*;q=0.8,*/*;q=0.5";
        String Accept_Language = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
        String Referer = "http://222.25.1.101/index.htm";
        String Connection = "keep-alive";
        String Cache_Control = "max-age=0";
        Request request = new Request.Builder().url(url)
                .header("Host", Host)
                .header("User-Agent", User_Agent)
                .header("Accept", Accept)
                .header("Accept-Language", Accept_Language)
                .header("Referer", Referer)
                .header("Cookie", cookie)
                .header("Connection", Connection)
                .header("Cache-Control", Cache_Control).build();
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            if (!response.isSuccessful())
                throw new IOException("验证码：" + response.code());
            // 当前Cookie
            String currCookie = response.header("Set-Cookie");
            if (currCookie != null) {
                // 更新Cookie
                CookieUtil.updateCookieContent(currCookie);
            }
            // 图片文件
            byte[] pic = response.body().bytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
            String text = null;
            try {
                text = CheckcodeOcr.getOcr(msg.getContext(), bitmap);
            } catch (Exception e) {
                Log.i("tag", "验证码解析错误");
                throw new IOException("验证码解析错误");
            }
//            Log.i("tag", "验证码:" + text);
            login(msg, username, password, text);
        } catch (SocketTimeoutException e) {
            Log.i("tag", "登录操作：" + e.getMessage());
            // 创建消息
            Message newMsg = Message.obtain();
            newMsg.what = msg.getControlCode();
            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.TIMEOUT;
            msg.getHandler().sendMessage(newMsg);
        } catch (IOException e) {
            Log.i("tag", "登录操作：" + e.getMessage());
            // 创建消息
            Message newMsg = Message.obtain();
            newMsg.what = msg.getControlCode();
            newMsg.arg1 = Code.RESULT.FALSE;
            newMsg.obj = WebError.FAIL;
            msg.getHandler().sendMessage(newMsg);
        }
    }
}
