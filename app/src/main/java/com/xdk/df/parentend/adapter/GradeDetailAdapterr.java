package com.xdk.df.parentend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.CourseMark;
import com.xdk.df.parentend.data.ResultMark;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/17.
 */
public class GradeDetailAdapterr extends BaseAdapter {
    private Context context;
    private ArrayList<CourseMark> list;
    private View view;

    public GradeDetailAdapterr(Context context, ArrayList<CourseMark> list) {
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
            convertView = mInflater.inflate(R.layout.activity_grade_detail_item, null,true);
            viewHolder.nameTx = (TextView) convertView.findViewById(R.id.grade_detail_list_item_name);
            viewHolder.markTx = (TextView) convertView
                    .findViewById(R.id.grade_detail_list_item_mark);
            viewHolder.rankTx = (TextView) convertView
                    .findViewById(R.id.grade_detail_list_item_classrank);
            viewHolder.classRankTx = (TextView) convertView.findViewById(R.id.grade_detail_list_item_graderank);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CourseMark markerItem = (CourseMark) getItem(i);
        if (null != markerItem) {
            viewHolder.nameTx.setText(markerItem.getCourse());
            viewHolder.markTx.setText(markerItem.getMark()+"");
            viewHolder.classRankTx.setText(markerItem.getGraderank()+"");
            viewHolder.rankTx.setText(markerItem.getClassrank()+"");
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView nameTx;
        private TextView markTx;
        private TextView rankTx;
        private TextView classRankTx;
    }
}
