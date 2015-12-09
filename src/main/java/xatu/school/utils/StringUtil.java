package xatu.school.utils;

/**
 * 字符串工具类
 * Created by penfi on 2015/11/24.
 */
public class StringUtil {
    public static String filterSpace(String str) {
        String a = str.trim();
        return str;
    }

    /**
     * 去掉字符串中来自数据库的空白
     */
    public static String replace(String str) {
        return str.replace("  ", "").replace(" ", "").replace(" ", "").replace("　","");
    }
}
