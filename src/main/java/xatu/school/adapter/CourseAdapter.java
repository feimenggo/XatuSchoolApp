package xatu.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xatu.school.R;
import xatu.school.bean.MyCourse;

/**
 * 主界面 课程表
 * Created by penfi on 2015/11/4.
 */
public class CourseAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyCourse> datas;
    private LayoutInflater mInflater;

    public CourseAdapter(Context context, ArrayList<MyCourse> datas) {
        this.context = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.day_course, parent, false);
            holder = new ViewHolder();
            holder.tv_time = (TextView) convertView.findViewById(R.id.id_time);
            holder.tv_course = (TextView) convertView.findViewById(R.id.id_course);
            holder.tv_place = (TextView) convertView.findViewById(R.id.id_place);
            holder.tv_time.setText(datas.get(position).getmTime());
            holder.tv_course.setText(datas.get(position).getmCourse());
            holder.tv_place.setText(datas.get(position).getmPlace());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_time, tv_course, tv_place;
    }
}
