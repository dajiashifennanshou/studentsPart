package com.xdk.df.parentend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.CourseArrangement;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ScheduleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CourseArrangement> list;

    public ScheduleAdapter(Context context, ArrayList<CourseArrangement> list) {
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
            convertView = mInflater.inflate(R.layout.item_schedule_list, null, true);
            viewHolder.timeTx = (TextView) convertView.findViewById(R.id.item_schedule_time);
            viewHolder.week1Tx = (TextView) convertView.findViewById(R.id.item_schedule_week1);
            viewHolder.week2Tx = (TextView) convertView.findViewById(R.id.item_schedule_week2);
            viewHolder.week3Tx = (TextView) convertView.findViewById(R.id.item_schedule_week3);
            viewHolder.week4Tx = (TextView) convertView.findViewById(R.id.item_schedule_week4);
            viewHolder.week5Tx = (TextView) convertView.findViewById(R.id.item_schedule_week5);
            viewHolder.week6Tx = (TextView) convertView.findViewById(R.id.item_schedule_week6);
            viewHolder.week7Tx = (TextView) convertView.findViewById(R.id.item_schedule_week7);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CourseArrangement markerItem = (CourseArrangement) getItem(i);
        if (null != markerItem) {
            viewHolder.timeTx.setText(markerItem.getTimestart()+"-"+markerItem.getTimeend());
            viewHolder.week1Tx.setText(markerItem.getWeek1());
            viewHolder.week2Tx.setText(markerItem.getWeek2());
            viewHolder.week3Tx.setText(markerItem.getWeek3());
            viewHolder.week4Tx.setText(markerItem.getWeek4());
            viewHolder.week5Tx.setText(markerItem.getWeek5());
            viewHolder.week6Tx.setText(markerItem.getWeek6());
            viewHolder.week7Tx.setText(markerItem.getWeek7());
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView timeTx;
        private TextView week1Tx;
        private TextView week2Tx;
        private TextView week3Tx;
        private TextView week4Tx;
        private TextView week5Tx;
        private TextView week6Tx;
        private TextView week7Tx;
    }
}
