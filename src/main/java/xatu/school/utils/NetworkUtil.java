package xatu.school.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接
 * Created by penfi on 2015/11/9.
 */
public class NetworkUtil {
    /**
     * 检测是否已经连接网络。
     *
     * @param context 上下文
     * @return 当且仅当连上网络时返回true, 否则返回false。
     */
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null) && info.isAvailable();
    }
}
