package xatu.school.bean;

/**
 * Created by mmcc on 2015/11/16.
 */
public class ScoreItem {
    private String session;
    private String courseName;
    private String couresScore;
    private int courseId;   //该课程在数据库的id

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCouresScore(String couresScore) {
        this.couresScore = couresScore;
    }

    public String getSession() {
        return session;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCouresScore() {
        return couresScore;
    }
}
