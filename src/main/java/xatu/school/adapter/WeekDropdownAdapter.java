package xatu.school.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xatu.school.R;

/**
 * Created by feimeng on 2016/2/16.
 */
public class WeekDropdownAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private String[] weeks;
    private int nowWeek;
    private int selectWeek;
    private int color;

    public WeekDropdownAdapter(Context context, String[] weeks, int nowWeek) {
        this.inflater = LayoutInflater.from(context);
        this.weeks = weeks;
        this.nowWeek = this.selectWeek = nowWeek;
        this.color = context.getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public int getCount() {
        return weeks.length;
    }

    @Override
    public Object getItem(int i) {
        return weeks[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelectWeek(int week) {
        this.selectWeek = week;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.coursetable_dropdown_item, null);
            viewHolder.titleWeekContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.wrap = (RelativeLayout) convertView.findViewById(R.id.rl_wrap);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == nowWeek - 1) {
            viewHolder.titleWeekContent.setText(weeks[position] + "(本周)");
        } else {
            viewHolder.titleWeekContent.setText(weeks[position]);
        }
        if (position == selectWeek - 1) {
            viewHolder.titleWeekContent.setTextColor(Color.WHITE);
            viewHolder.wrap.setBackgroundColor(color);
        } else {
            viewHolder.titleWeekContent.setTextColor(color);
            viewHolder.wrap.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView titleWeekContent;
        RelativeLayout wrap;
    }
}
