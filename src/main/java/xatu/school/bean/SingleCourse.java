package xatu.school.bean;

import java.io.Serializable;

/**
 * 单科课程类
 * Created by penfi on 2015/11/19.
 * 莫晨：序列化该类
 */
public class SingleCourse extends BaseSingleCourse implements Serializable {
    public static final String TABLE_NAME = "tb_course";// 课程表名

    public static final String COLUMN_SEMESTER_ID = "semester_id";// 学期号
    public static final String COLUMN_NAME = "name";// 课程名
    public static final String COLUMN_XUEFEN = "xuefen";// 学分
    public static final String COLUMN_CHENGJI = "chengji";// 成绩
    public static final String COLUMN_RENKEJIAOSHI = "renkejiaoshi";// 任课老师
    public static final String COLUMN_KAOSHILEIXING = "kaoshileixing";// 考试类型
    public static final String COLUMN_URL = "url";// 评价url
    public static final String COLUMN_STATUS = "status";// 状态

    private int id;// 在数据库中的id
    private String name;// 课程名
    private float xuefen;// 学分
    private int chengji;// 成绩(原始成绩)
    private String renkejiaoshi;// 任课教师
    private String kaoshileixing;// 考试类型：考试, 考查
    private String url;// 评价url
    private int status;// 状态：1->未考试(无)， 2->归档中(提交)， 3->未评价(归档&未评价)， 4->已评价(归档&已评价)

    public SingleCourse() {
    }

    public SingleCourse(String name, float xuefen, int chengji,
                        String renkejiaoshi, String kaoshileixing, String url, int status) {
        this.name = name;
        this.xuefen = xuefen;
        this.chengji = chengji;
        this.renkejiaoshi = renkejiaoshi;
        this.kaoshileixing = kaoshileixing;
        this.url = url;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public float getXuefen() {
        return xuefen;
    }

    public int getChengji() {
        return chengji;
    }

    public String getRenkejiaoshi() {
        return renkejiaoshi;
    }

    public String getKaoshileixing() {
        return kaoshileixing;
    }

    public String getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setXuefen(float xuefen) {
        this.xuefen = xuefen;
    }

    public void setChengji(int chengji) {
        this.chengji = chengji;
    }

    public void setRenkejiaoshi(String renkejiaoshi) {
        this.renkejiaoshi = renkejiaoshi;
    }

    public void setKaoshileixing(String kaoshileixing) {
        this.kaoshileixing = kaoshileixing;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}