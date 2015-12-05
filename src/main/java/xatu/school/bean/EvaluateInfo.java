package xatu.school.bean;

/**
 * 单科课程评教信息类
 * Created by penfi on 2015/12/2.
 */
public class EvaluateInfo {
    private SingleCourse singleCourse;// 单科课程对象
    private int[] radios;// 单选 整形数组 值：1->A,2->B,3->C,4->D,5->E

    // 教学质量评价 内容
    public static String[] form = new String[]{"你认为教师教学态度是否认真负责？", "教师讲课是否激发了你对学习本课程的兴趣？ ", "你认为教师的教学方法、教学手段是否有助于你对课程的学习？", "你认为本课程的组织、时间利用是否合适？ ", "你认为课堂信息量是否合适？ ", "你认为教师是否善于引导学生积极思考，师生互动好？ ", "你认为教师是否注意维护课堂纪律，课堂气氛利于学习？", "你是否认为教师对讲授内容熟练、不照本宣科？ ", "你对教师在课堂和课后对你提供课程方面的帮助是否满意？ ", "你对本门课程的总体教学状况评价怎样？ "};

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
