package xatu.school.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;

import xatu.school.R;
import xatu.school.utils.StringUtil;

public class StudentInfo {
    public static final String TABLE_NAME = "tb_student_info";// 学生信息 表名

    public static final String COLUMN_NAME = "name";// 名字
    public static final String COLUMN_XIBU = "yuanxi";// 系别
    public static final String COLUMN_ZHUANYE = "zhuanye";// 专业
    public static final String COLUMN_XINGBIE = "xingbie";// 性别
    public static final String COLUMN_BANJI = "banji";// 班级
    public static final String COLUMN_XUEHAO = "xuehao";// 学号
    public static final String COLUMN_SHENGRI = "shengri";// 生日

    String name; //名字
    String yuanxi; //院系
    String zhuanye; //专业
    String xingbie; //性别
    String banji; //班级
    String xuehao; //学号
    String shengri; //生日

    public StudentInfo(String name, String yuanxi, String zhuanye, String xingbie, String banji, String xuehao, String shengri) {
        this.name = name;
        this.yuanxi = yuanxi;
        this.zhuanye = zhuanye;
        this.xingbie = StringUtil.replace(xingbie);
        this.banji = StringUtil.replace(banji);
        this.xuehao = StringUtil.replace(xuehao);
        this.shengri = StringUtil.replace(shengri);
    }

    public String getName() {
        return name;
    }

    public String getYuanxi() {
        return yuanxi;
    }

    public String getZhuanye() {
        return zhuanye;
    }

    public String getXingbie() {
        return xingbie;
    }

    public String getBanji() {
        return banji;
    }

    public String getXuehao() {
        return xuehao;
    }

    public String getShengri() {
        return shengri;
    }

    /**
     * 得到用户头像
     *
     * @param context 上下文
     * @return drawable对象
     */
    public Drawable getHeadIcon(Context context) {
        if (xingbie.contains("男"))// 要解决空白符问题
            return context.getResources().getDrawable(R.mipmap.head_boy);
        return context.getResources().getDrawable(R.mipmap.head_boy);
    }
}
