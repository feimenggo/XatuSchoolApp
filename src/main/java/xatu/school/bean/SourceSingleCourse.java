package xatu.school.bean;

/**
 * 原始单科课程类
 * Created by Administrator on 2015-10-18.
 */
public class SourceSingleCourse {
    public static final String TABLE_NAME = "tb_course";// 课程表名
    public static final String COLUMN_SEMESTER_ID = "semester_id";// 学期号
    public static final String COLUMN_NAME = "name";// 课程名
    public static final String COLUMN_URL = "url";// 评价url
    public static final String COLUMN_YUANSHICHENGJI = "yuanshichengji";// 原始成绩
    public static final String COLUMN_RENKEJIAOSHI = "renkejiaoshi";// 任课老师
    public static final String COLUMN_KAOSHILEIXING = "kaoshileixing";// 考试类型
    public static final String COLUMN_KAOSHIFANGSHI = "kaoshifangshi";// 考试方式
    public static final String COLUMN_XUEFEN = "xuefen";// 学分
    public static final String COLUMN_JIDIAN = "jidian";// 绩点
    public static final String COLUMN_ZHUANGTAI = "zhuangtai";// 状态


    private String name;// 课程名
    private String xuefen;// 学分
    private String yuanshichengji;// 原始成绩
    private String zhuanhuanchengji;// 转换成绩
    private String jidian;
    private String renkejiaoshi;// 任课老师
    private String kaoshileixing;// 考试类型
    private String kaoshishijian;// 考试时间
    private String kaoshifangshi;// 考试方式
    private String zhuangtai;// 状态
    private String caozuo;// 操作
    private String url; //评教url

    // 用于课程表
    private String jieci;// 节次
    private String jiaoshi;// 教室

    /**
     * 用于数据库
     */
    public SourceSingleCourse(String name, String yuanshichengji, String renkejiaoshi,
                              String kaoshileixing, String zhuangtai, String kaoshifangshi,
                              String jidian, String xuefen) {
        this.name = name;
        this.yuanshichengji = yuanshichengji;
        this.renkejiaoshi = renkejiaoshi;
        this.kaoshileixing = kaoshileixing;
        this.zhuangtai = zhuangtai;
        this.kaoshifangshi = kaoshifangshi;
        this.jidian = jidian;
        this.xuefen = xuefen;
    }

    /**
     * 用于网络
     */
    public SourceSingleCourse(String name, String xuefen, String yuanshichengji,
                              String zhuanhuanchengji, String jidian, String renkejiaoshi,
                              String kaoshileixing, String kaoshishijian, String kaoshifangshi,
                              String zhuangtai, String caozuo, String url) {
        this.name = name;
        this.xuefen = xuefen;
        this.yuanshichengji = yuanshichengji;
        this.zhuanhuanchengji = zhuanhuanchengji;
        this.jidian = jidian;
        this.renkejiaoshi = renkejiaoshi;
        this.kaoshileixing = kaoshileixing;
        this.kaoshishijian = kaoshishijian;
        this.kaoshifangshi = kaoshifangshi;
        this.zhuangtai = zhuangtai;
        this.caozuo = caozuo;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getXuefen() {
        return xuefen;
    }

    public String getYuanshichengji() {
        return yuanshichengji;
    }

    public String getZhuanhuanchengji() {
        return zhuanhuanchengji;
    }

    public String getJidian() {
        return jidian;
    }

    public String getRenkejiaoshi() {
        return renkejiaoshi;
    }

    public String getKaoshileixing() {
        return kaoshileixing;
    }

    public String getKaoshishijian() {
        return kaoshishijian;
    }

    public String getKaoshifangshi() {
        return kaoshifangshi;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public String getCaozuo() {
        return caozuo;
    }

    public String getUrl() {
        return url;
    }

    public String getJieci() {
        return jieci;
    }

    public String getJiaoshi() {
        return jiaoshi;
    }

    public void setYuanshichengji(String yuanshichengji) {
        this.yuanshichengji = yuanshichengji;
    }
}
