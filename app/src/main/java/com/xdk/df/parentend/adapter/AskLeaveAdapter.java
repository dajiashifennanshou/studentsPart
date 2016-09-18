package com.xdk.df.parentend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.LeaveDetail;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public class AskLeaveAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LeaveDetail> list;

    public AskLeaveAdapter(Context context, ArrayList<LeaveDetail> list) {
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
            convertView = mInflater.inflate(R.layout.ask_leave_list_item, null, true);
            viewHolder.timeTx = (TextView) convertView.findViewById(R.id.ask_leave_type);
            viewHolder.placeTx = (TextView) convertView
                    .findViewById(R.id.ask_leave_reason);
            viewHolder.moneyTx = (TextView) convertView
                    .findViewById(R.id.ask_leave_content);
            viewHolder.restMoneyTx = (TextView) convertView.findViewById(R.id.ask_leave_class_agree);
            viewHolder.typeTx = (TextView) convertView.findViewById(R.id.ask_leave_result);
            viewHolder.waysTx = (TextView) convertView.findViewById(R.id.ask_leave_startTime);
            viewHolder.numberTx = (TextView) convertView.findViewById(R.id.ask_leave_endTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LeaveDetail markerItem = (LeaveDetail) getItem(i);
        if (null != markerItem) {
            viewHolder.timeTx.setText(markerItem.getLeavetype());
            viewHolder.waysTx.setText(context.getString(R.string.str_today_check_time, markerItem.getStartdate(), markerItem.getStarttime()));
            viewHolder.placeTx.setText(markerItem.getLeaevreason());
            if(markerItem.getResults() == 1){
                viewHolder.typeTx.setText(R.string.str_ask_leave_agree);
            }else if(markerItem.getResults() == 99){
                viewHolder.typeTx.setText(R.string.str_ask_leave_disagree);
            }
            viewHolder.restMoneyTx.setText(markerItem.getManager());
            viewHolder.moneyTx.setText(markerItem.getApproved());
            viewHolder.numberTx.setText(context.getString(R.string.str_today_check_time, markerItem.getEnddate(), markerItem.getEndtime()));
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView timeTx;
        private TextView placeTx;
        private TextView typeTx;
        private TextView moneyTx;
        private TextView restMoneyTx;
        private TextView numberTx;
        private TextView waysTx;
    }
}
