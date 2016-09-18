package com.xdk.df.parentend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.MainActivity;
import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.CheckDetail;
import com.xdk.df.parentend.data.ResultMark;
import com.xdk.df.parentend.ui.login.latestGrade.GradeDetailActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/17.
 */
public class GradecheckAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<ResultMark> list;
    private View view;
    private String currentExam;

    public GradecheckAdapter(Context context, ArrayList<ResultMark> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.activity_grade_list_item, null,true);
            viewHolder.nameTx = (TextView) convertView.findViewById(R.id.grade_list_item_name);
            viewHolder.markTx = (TextView) convertView
                    .findViewById(R.id.grade_list_item_mark);
            viewHolder.rankTx = (TextView) convertView
                    .findViewById(R.id.grade_list_item_rank);
            viewHolder.classRankTx = (TextView) convertView.findViewById(R.id.grade_list_item_clasrank);
            viewHolder.averageTx = (TextView) convertView.findViewById(R.id.grade_list_item_average);
            viewHolder.rankChangeTx = (TextView) convertView.findViewById(R.id.grade_list_item_rankchange);
            viewHolder.timeTx = (TextView) convertView.findViewById(R.id.grade_list_item_time);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.grade_list_item_ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

         ResultMark markerItem = (ResultMark) getItem(i);
        currentExam = markerItem.getExamination();
        if (null != markerItem) {
            viewHolder.nameTx.setText(markerItem.getExamination());
            viewHolder.rankChangeTx.setText(markerItem.getRankchanged()+"");
            viewHolder.averageTx.setText(markerItem.getAverage()+"");
            viewHolder.timeTx.setText(markerItem.getExadate());
            viewHolder.markTx.setText(markerItem.getMark()+"");
            viewHolder.classRankTx.setText(markerItem.getGraderank()+"");
            viewHolder.rankTx.setText(markerItem.getClassrank()+"");
        }
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context,GradeDetailActivity.class);
                intent.putExtra("exam",currentExam);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        private TextView nameTx;
        private TextView markTx;
        private TextView rankTx;
        private TextView classRankTx;
        private TextView rankChangeTx;
        private TextView averageTx;
        private TextView timeTx;
        private LinearLayout linearLayout;
    }
}
