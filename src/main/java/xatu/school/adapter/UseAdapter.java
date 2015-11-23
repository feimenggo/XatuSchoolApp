package xatu.school.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import xatu.school.R;
import xatu.school.bean.Node;

/**
 * Created by mmcc on 2015/11/3.
 */
public class UseAdapter<T> extends TreeListViewAdapter<T> {


    public UseAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (node.getParent() != null) {   //设置子节点内容
            convertView = mInflater.inflate(R.layout.score_child_item, parent, false);
            holder = new ViewHolder();
            holder.courseName = (TextView) convertView.findViewById(R.id.item_course_name);
            holder.courseScore = (TextView) convertView.findViewById(R.id.item_course_score);
            holder.courseName.setText(node.getName().getCourseName()); //设置课程名称
            holder.courseScore.setText(node.getName().getCouresScore());//设置课程分数
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

    static class ViewHolder {
        ImageView mIcon;
        TextView mText; //父节点名称（学期）
        TextView courseName;  //课程
        TextView courseScore; //课程分数
    }
}
