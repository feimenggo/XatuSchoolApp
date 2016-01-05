package xatu.school.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import xatu.school.R;
import xatu.school.bean.FileBean;
import xatu.school.bean.Node;
import xatu.school.bean.SingleCourse;

/**
 * Created by mmcc on 2015/11/3.
 */
public class UseAdapter<T> extends TreeListViewAdapter<T> {

    private List<SingleCourse> AllCourseObj; //课程的所有信息

    private OnEvaluateClick onEvaluateClick;

    public interface OnEvaluateClick {
        void onEvaluateClick(SingleCourse singleCourse);
    }

    public void setEvaluateClick(OnEvaluateClick onclick) {
        this.onEvaluateClick = onclick;
    }

    private OnLongClick onLongClick;

    public void setOnClick(OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    public interface OnLongClick {
        void onlongClick(SingleCourse singleCourse);
    }

    public UseAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel, List<SingleCourse> allCourse) throws IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
        this.AllCourseObj = allCourse;
    }

    @Override
    public View getConvertView(Node node, final int position,List<FileBean> mDatas, View convertView, ViewGroup parent) {
       FileBean currentCourse=null;
        int size=mDatas.size();
        for(int i=0;i<size;i++)
       {
           if(mDatas.get(i).getLabel().getCourseId()==node.getName().getCourseId())
           {
               currentCourse=mDatas.get(i);
           }
       }
        final ViewHolder holder;
        //  SingleCourse singleCourse1=AllCourseObj.get(position);
        if (node.getParent() != null) {   //设置子节点内容
            convertView = mInflater.inflate(R.layout.score_child_item, parent, false);
            holder = new ViewHolder();
            holder.courseName = (TextView) convertView.findViewById(R.id.item_course_name);
            holder.courseScore = (TextView) convertView.findViewById(R.id.item_course_score);
            holder.courseName.setText(currentCourse.getLabel().getCourseName()); //设置课程名称
            final SingleCourse singleCourse = getSingleCourse(node.getName().getCourseId());
            if (singleCourse.getStatus() == 3) {
                holder.courseScore.setText("点击评价");
                int color = mContext.getResources().getColor(R.color.colorPrimary);
                holder.courseScore.setTextColor(color);
                holder.courseScore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEvaluateClick != null) {
                            onEvaluateClick.onEvaluateClick(singleCourse);
                        }
                    }
                });
            }else{
                holder.courseScore.setText(currentCourse.getLabel().getCouresScore());//设置课程分数
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLongClick != null) {
                        onLongClick.onlongClick(singleCourse);
                    }
                }
            });
        } else {         //设置父节点内容
            //当没有第三个参数时。list_item里的最外层的布局无效，若为true会返回parent，将list_item加在parent布局里
            convertView = mInflater.inflate(R.layout.score_item, parent, false);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.mText = (TextView) convertView.findViewById(R.id.item_text);
            holder.mText.setText(node.getName().getSession());
            if (node.getIcon() == -1) {
                holder.mIcon.setVisibility(View.INVISIBLE);
            } else {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(node.getIcon());
            }
        }
        return convertView;
    }

    //得到当前的科目对象
    private SingleCourse getSingleCourse(int courseId) {
        SingleCourse singleCourse = null;
        if (AllCourseObj != null) {
            int len = AllCourseObj.size();
            for (int i = 0; i < len; i++) {
                if (courseId == (AllCourseObj.get(i).getId())) {
                    singleCourse = AllCourseObj.get(i);
                }
            }
        }
        return singleCourse;
    }

    static class ViewHolder {
        ImageView mIcon;
        TextView mText; //父节点名称（学期）
        TextView courseName;  //课程
        TextView courseScore; //课程分数
    }
}
