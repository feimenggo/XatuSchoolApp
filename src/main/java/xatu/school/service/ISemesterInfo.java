package xatu.school.service;

import android.content.Context;
import android.os.Handler;

import xatu.school.bean.InitMsg;

/**
 * 学期信息接口
 * Created by penfi on 2015/10/25.
 */
public interface ISemesterInfo {
    /**
     * 获取学期信息
     * 异步返回 Semester<List>
     *
     */

    public void getSemesters(InitMsg msg);
}
