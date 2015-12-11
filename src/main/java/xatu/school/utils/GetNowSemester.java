package xatu.school.utils;

import java.util.Calendar;
import java.util.List;

import xatu.school.bean.CourseGrades;
import xatu.school.bean.Semester;

/**
 * Created by Administrator on 2015-12-11.
 */
public class GetNowSemester {
    public int Get(CourseGrades courseGrades)
    {
        int count=0;
        int size=courseGrades.getSemester().size();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;

        //>=8 1 3 5 7
        //2-8
        if(month>=2 && month<=8)
            count=size%2==0?size:size-1;
        else
            count=size%2==0?size-1:size;
        return count;
    }
}
