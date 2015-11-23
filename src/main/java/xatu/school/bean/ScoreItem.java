package xatu.school.bean;

/**
 * Created by mmcc on 2015/11/16.
 */
public class ScoreItem {
    private String session;
    private String courseName;
    private String couresScore;

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
