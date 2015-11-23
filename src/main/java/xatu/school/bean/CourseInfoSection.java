package xatu.school.bean;

/**
 * 主界面 课程信息区 类
 *
 * Created by penfi on 2015/11/4.
 */
public class CourseInfoSection {
    private int total;// 总课程数
    private int pass;// 通过课程数
    private int passRate;// 通过率
    private String evaluation;// 评价

    public CourseInfoSection(int total, int pass, int passRate, String evaluation) {
        this.total = total;
        this.pass = pass;
        this.passRate = passRate;
        this.evaluation = evaluation;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getPassRate() {
        return passRate;
    }

    public void setPassRate(int passRate) {
        this.passRate = passRate;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
