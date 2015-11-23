package xatu.school.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 学期类
 * Created by Administrator on 2015-10-18.
 */
public class Semester {
    public static final String TABLE_NAME = "tb_semester";// 学期表名
    public static final String COLUMN_NAME = "name";// 学期名

    private String name;// 学期名
    private List<SourceSingleCourse> sourceSingleCourses = new ArrayList<SourceSingleCourse>();// 该学期的单科课程集合

    public Semester() {
    }

    public Semester(String name, List<SourceSingleCourse> sourceSingleCourses) {
        this.name = name;
        this.sourceSingleCourses = sourceSingleCourses;
    }

    public void setName(String name) {
        this.name = new String(name);
    }

    public String getName() {
        return this.name;
    }

    public List<SourceSingleCourse> getSourceSingleCourses() {
        return sourceSingleCourses;
    }

    public boolean isEmpty() {
        return sourceSingleCourses.isEmpty();
    }

    public void addCourse(SourceSingleCourse co) {
        sourceSingleCourses.add(co);
    }

    public void dll() {
        sourceSingleCourses.clear();
    }

}
