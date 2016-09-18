package com.xdk.df.parentend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.MoneyDeatil;
import com.xdk.df.parentend.ui.login.payCheck.PayCheckToweekFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PayCheckItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MoneyDeatil> list;
    private String flag = null;

    public PayCheckItemAdapter(Context context, ArrayList<MoneyDeatil> list) {
        this.context = context;
        this.list = list;
    }

    public PayCheckItemAdapter(Context context, ArrayList<MoneyDeatil> list, String a) {
        this.context = context;
        this.list = list;
        flag = a;
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
            convertView = mInflater.inflate(R.layout.activity_pay_check_list_item, null, true);
            viewHolder.timeTx = (TextView) convertView.findViewById(R.id.pay_check_time);
            viewHolder.placeTx = (TextView) convertView
                    .findViewById(R.id.pay_check_place);
            viewHolder.moneyTx = (TextView) convertView
                    .findViewById(R.id.pay_check_money);
            viewHolder.restMoneyTx = (TextView) convertView.findViewById(R.id.pay_check_rest_money);
            viewHolder.typeTx = (TextView) convertView.findViewById(R.id.pay_check_type);
            viewHolder.waysTx = (TextView) convertView.findViewById(R.id.pay_check_ways);
            viewHolder.numberTx = (TextView) convertView.findViewById(R.id.pay_check_number);
            if (flag != null) {
                viewHolder.paytx = (TextView) convertView.findViewById(R.id.pay_check_class);
                viewHolder.timeTx1 = (TextView) convertView.findViewById(R.id.pay_check_time1);
                viewHolder.placeTx1 = (TextView) convertView
                        .findViewById(R.id.pay_check_place1);
                viewHolder.moneyTx1 = (TextView) convertView
                        .findViewById(R.id.pay_check_money1);
                viewHolder.typeTx1 = (TextView) convertView.findViewById(R.id.pay_check_type1);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MoneyDeatil markerItem = (MoneyDeatil) getItem(i);
        if (null != markerItem) {
            if (flag != null) {
                if (flag.equals(PayCheckToweekFragment.WEEK)) {
                    viewHolder.paytx.setText("消费明细");
                    viewHolder.timeTx1.setText("消费时间");
                    viewHolder.placeTx1.setText("消费地点");
                    viewHolder.moneyTx1.setText("消费金额");
                    viewHolder.typeTx1.setText("消费类型");
                } else {
                    viewHolder.paytx.setText("充值明细");
                    viewHolder.timeTx1.setText("充值时间");
                    viewHolder.placeTx1.setText("充值地点");
                    viewHolder.moneyTx1.setText("充值金额");
                    viewHolder.typeTx1.setText("充值类型");
                }
            }
            viewHolder.timeTx.setText(context.getString(R.string.str_today_check_time, markerItem.getHappendate(), markerItem.getHappentime()));
            viewHolder.waysTx.setText(markerItem.getMoneystyle());
            viewHolder.placeTx.setText(markerItem.getHappenaddress());
            viewHolder.typeTx.setText(markerItem.getMoneytype());
            viewHolder.restMoneyTx.setText(markerItem.getNewmoney() + "");
            if (flag != null) {
                if (flag.equals(PayCheckToweekFragment.WEEK)) {
                    if(markerItem.getMoney()<0){
                        viewHolder.moneyTx.setText(markerItem.getMoney() + "");
                    }
                } else {
                    if(markerItem.getMoney()>0){
                        viewHolder.moneyTx.setText(markerItem.getMoney() + "");
                    }
                }
            }else{
                viewHolder.moneyTx.setText(markerItem.getMoney() + "");
            }
            viewHolder.numberTx.setText(markerItem.getHappenwindow());
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
        private TextView paytx;
        private TextView timeTx1;
        private TextView placeTx1;
        private TextView typeTx1;
        private TextView moneyTx1;
    }
}
