package xatu.school.bean;

/**
 * 原始单科课程类
 * Created by Administrator on 2015-10-18.
 */
public class SourceSingleCourse {
    public static final String TABLE_NAME = "tb_course";// 课程表名
    public static final String COLUMN_SEMESTER_ID = "semester_id";// 学期号
    public static final String COLUMN_NAME = "name";// 课程名
    public static final String COLUMN_KECHENGLEIBIE = "kechengleibie";// 课程类别
    public static final String COLUMN_YUANSHICHENGJI = "yuanshichengji";// 原始成绩
    public static final String COLUMN_RENKEJIAOSHI = "renkejiaoshi";// 任课老师
    public static final String COLUMN_KAOSHILEIXING = "kaoshileixing";// 考试类型
    public static final String COLUMN_KAOSHIFANGSHI = "kaoshifangshi";// 考试方式
    public static final String COLUMN_XUEFEN = "xuefen";// 学分
    public static final String COLUMN_JIDIAN = "jidian";// 绩点
    public static final String COLUMN_ZHUANGTAI = "zhuangtai";// 状态


    private String name;// 课程名
    private String xuefen;// 学分
    private String kechengleibie;// 课程类别
    private String yuanshichengji;// 原始成绩
    private String zhuanhuanchengji;// 转换成绩
    private String jidian;// 绩点
    private String mingci;// 名次
    private String renshu;// 人数
    private String renkejiaoshi;// 任课老师
    private String kaoshileixing;// 考试类型
    private String kaoshishijian;// 考试时间
    private String kaoshifangshi;// 考试方式
    private String zhuangtai;// 状态
    private String caozuo;// 操作

    // 用于课程表
    private String jieci;// 节次
    private String jiaoshi;// 教室

    /**
     * 用于数据库
     */
    public SourceSingleCourse(String name, String kechengleibie, String yuanshichengji, String renkejiaoshi,
                              String kaoshileixing, String zhuangtai, String kaoshifangshi, String jidian, String xuefen) {
        this.name = name;
        this.kechengleibie = kechengleibie;
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
    public SourceSingleCourse(String name, String xuefen, String kechengleibie, String yuanshichengji, String zhuanhuanchengji, String jidian, String mingci, String renshu, String renkejiaoshi, String kaoshileixing, String kaoshishijian, String kaoshifangshi, String zhuangtai, String caozuo) {
        this.name = name;
        this.xuefen = xuefen;
        this.kechengleibie = kechengleibie;
        this.yuanshichengji = yuanshichengji;
        this.zhuanhuanchengji = zhuanhuanchengji;
        this.jidian = jidian;
        this.mingci = mingci;
        this.renshu = renshu;
        this.renkejiaoshi = renkejiaoshi;
        this.kaoshileixing = kaoshileixing;
        this.kaoshishijian = kaoshishijian;
        this.kaoshifangshi = kaoshifangshi;
        this.zhuangtai = zhuangtai;
        this.caozuo = caozuo;
    }

    public String getJieci() {
        return jieci;
    }

    public void setJieci(String jieci) {
        this.jieci = jieci;
    }

    public String getJiaoshi() {
        return jiaoshi;
    }

    public void setJiaoshi(String jiaoshi) {
        this.jiaoshi = jiaoshi;
    }

    public String getName() {
        return name;
    }

    public String getXuefen() {
        return xuefen;
    }

    public String getKechengleibie() {
        return kechengleibie;
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

    public String getMingci() {
        return mingci;
    }

    public String getRenshu() {
        return renshu;
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
}
