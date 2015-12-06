
package xatu.school.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import xatu.school.R;
import xatu.school.bean.RadioCheck;

/**
 * 功能：题目列表的适配器
 */
public class ProblemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<RadioCheck> list;
    private int selectRadio[];

    public ProblemAdapter(Context context, ArrayList<RadioCheck> list,int selectRadio[]) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.selectRadio=selectRadio;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RadioCheck check = list.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.evaluate_list, null);
            holder = new ViewHolder();
            holder.content = (TextView) convertView.findViewById(R.id.evaluate_content);
            holder.result_a = (RadioButton) convertView
                    .findViewById(R.id.id_A);
            holder.result_b = (RadioButton) convertView
                    .findViewById(R.id.id_B);
            holder.result_c = (RadioButton) convertView
                    .findViewById(R.id.id_C);
            holder.result_d = (RadioButton) convertView
                    .findViewById(R.id.id_D);
            holder.result_e = (RadioButton) convertView
                    .findViewById(R.id.id_E);
            holder.radioGroup = (RadioGroup) convertView
                    .findViewById(R.id.id_radiogroup);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.content.setText(check.getContent());

        holder.radioGroup.setTag(position);
        holder.radioGroup.setOnCheckedChangeListener(null);
        if (RadioCheck.answers[0].equals(check.getAnswer())) {
            holder.radioGroup.check(holder.result_a.getId());
        } else if (RadioCheck.answers[1].equals(check.getAnswer())) {
            holder.radioGroup.check(holder.result_b.getId());
        } else if (RadioCheck.answers[2].equals(check.getAnswer())) {
            holder.radioGroup.check(holder.result_c.getId());
        } else if (RadioCheck.answers[3].equals(check.getAnswer())) {
            holder.radioGroup.check(holder.result_d.getId());
        }else if (RadioCheck.answers[4].equals(check.getAnswer())) {
            holder.radioGroup.check(holder.result_e.getId());
        }else {
            holder.radioGroup.clearCheck();
        }
        final RadioGroup radioGroup = holder.radioGroup;
        holder.radioGroup
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioCheck radioCheck = list.get(((Integer) group
                                .getTag()));
                        if (group == radioGroup) {
                            switch (checkedId) {
                                case R.id.id_A:
                                    selectRadio[position]=1;
                                    radioCheck.setAnswer(RadioCheck.answers[0]);
                                    break;
                                case R.id.id_B:
                                    selectRadio[position]=2;
                                    radioCheck.setAnswer(RadioCheck.answers[1]);
                                    break;
                                case R.id.id_C:
                                    selectRadio[position]=3;
                                    radioCheck.setAnswer(RadioCheck.answers[2]);
                                    break;
                                case R.id.id_D:
                                    selectRadio[position]=4;
                                    radioCheck.setAnswer(RadioCheck.answers[3]);
                                    break;
                                case R.id.id_E:
                                    selectRadio[position]=5;
                                    radioCheck.setAnswer(RadioCheck.answers[4]);
                                    break;
                            }
                        }
                    }
                });

        return convertView;
    }

    class ViewHolder {
        TextView content;

        RadioButton result_a;
        RadioButton result_b;
        RadioButton result_c;
        RadioButton result_d;
        RadioButton result_e;

        RadioGroup radioGroup;
    }

}
