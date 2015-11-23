package xatu.school.bean;

/**
 * Created by mmcc on 2015/11/4.
 */
public class MyCourse {
    private String mTime;
    private String mCourse;
    private String mPlace;

    public MyCourse(){
    }
    public MyCourse(String mCourse, String mPlace, String mTime) {
        this.mCourse = mCourse;
        this.mPlace = mPlace;
        this.mTime = mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public void setmCourse(String mCourse) {
        this.mCourse = mCourse;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getmTime() {

        return mTime;
    }

    public String getmCourse() {
        return mCourse;
    }

    public String getmPlace() {
        return mPlace;
    }
}
