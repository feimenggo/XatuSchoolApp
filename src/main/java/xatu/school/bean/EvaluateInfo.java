package xatu.school.bean;

import xatu.school.exception.EvaluateException;
import xatu.school.utils.EvaluateCheckForm;

/**
 * 单科课程评教信息类
 * Created by penfi on 2015/12/2.
 */
public class EvaluateInfo {
    private SingleCourse singleCourse;// 单科课程对象
    private int[] radios;// 单选 整形数组 值：1->A,2->B,3->C,4->D,5->E

    // 教学质量评价 表单单选标题
    public static String[] formRadioTitles = new String[]{"1、你认为教师教学态度是否认真负责？", "2、教师讲课是否激发了你对学习本课程的兴趣？ ", "3、你认为教师的教学方法、教学手段是否有助于你对课程的学习？", "4、你认为本课程的组织、时间利用是否合适？ ", "5、你认为课堂信息量是否合适？ ", "6、你认为教师是否善于引导学生积极思考，师生互动好？ ", "7、你认为教师是否注意维护课堂纪律，课堂气氛利于学习？", "8、你是否认为教师对讲授内容熟练、不照本宣科？ ", "9、你对教师在课堂和课后对你提供课程方面的帮助是否满意？ ", "10、你对本门课程的总体教学状况评价怎样？ "};

    public EvaluateInfo(SingleCourse singleCourse, int[] form) throws EvaluateException {
        this.singleCourse = singleCourse;
        String check;
        if ((check = EvaluateCheckForm.checkForm(form)) != null) {
            throw new EvaluateException(check);
        }
        this.radios = form;
    }


    public SingleCourse getSingleCourse() {
        return singleCourse;
    }

    public int[] getForm() {
        return radios;
    }
}
