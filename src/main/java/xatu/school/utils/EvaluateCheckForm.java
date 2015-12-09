package xatu.school.utils;

import xatu.school.exception.EvaluateException;

/**
 * 评价表单格式检查
 * Created by penfi on 2015/12/9.
 */
public class EvaluateCheckForm {
    public static String checkForm(int[] form) {
        int na = 0, nb = 0, nc = 0, nd = 0, ne = 0;// 每个选项的数目

        String result = null;
        // 遍历表单
        for (int radio : form) {
            switch (radio) {
                case 1:
                    na++;
                    break;
                case 2:
                    nb++;
                    break;
                case 3:
                    nc++;
                    break;
                case 4:
                    nd++;
                    break;
                case 5:
                    ne++;
                    break;
            }
        }
        // 判断数量
        int size = form.length - 3;
        if (na >= 5) {
            result = "选A的个数要小于5";
        } else if (nb >= size) {
            result = "B选的太多了";
        } else if (nc >= size) {
            result = "C选的太多了";
        } else if (nd >= size) {
            result = "D选的太多了";
        } else if (ne >= size) {
            result = "E选的太多了";
        }
        return result;
    }
}
