package xatu.school.bean;

/**
 * 单科课程评教信息类
 * Created by penfi on 2015/12/2.
 */
public class EvaluateInfo {
    private SingleCourse singleCourse;// 单科课程对象
    private int[] radios;// 单选 整形数组 值：1->A,2->B,3->C,4->D,5->E

    public EvaluateInfo(SingleCourse singleCourse, int[] form) {
        this.singleCourse = singleCourse;
        this.radios = form;
    }

    public SingleCourse getSingleCourse() {
        return singleCourse;
    }

    public int[] getForm() {
        return radios;
    }
}
