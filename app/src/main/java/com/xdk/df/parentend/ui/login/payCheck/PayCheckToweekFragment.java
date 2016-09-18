package com.xdk.df.parentend.ui.login.payCheck;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.adapter.PayCheckItemAdapter;
import com.xdk.df.parentend.base.BaseFragment;
import com.xdk.df.parentend.data.HttpData;
import com.xdk.df.parentend.data.MoneyDeatil;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PayCheckToweekFragment extends BaseFragment {
    private View view;
    private ListView listView;
    private ArrayList<MoneyDeatil> moneyDeatils;
    private TextView notingTx;
    private ResultSet rs;
    private Connection con;
    private Statement sttm;
    private HttpData data;
    public static final String WEEK = "xiaofei";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!(msg.what == 1)) {
                data = (HttpData) msg.obj;
                con = data.getConnection();
                sttm = data.getStatement();
                rs = data.getResultSet();
                initData();
            }else {
                listView.setVisibility(View.GONE);
                notingTx.setVisibility(View.VISIBLE);
            }
        }
    };

    private void initData() {
        try {
            boolean flag =false;
            while (rs.next()){
                if(!flag){
                    flag = true;
                }
                MoneyDeatil de = new MoneyDeatil();
                de.setMoneytype(rs.getString("moneytype"));
                de.setMoneystyle(rs.getString("moneystyle"));
                de.setMoney(rs.getDouble("money"));
                de.setNewmoney(rs.getDouble("newmoney"));
                de.setHappentime(rs.getString("happentime"));
                de.setHappenaddress(rs.getString("happenaddress"));
                de.setHappendate(rs.getString("happendate"));
                de.setHappenwindow(rs.getString("happenwindow"));
                moneyDeatils.add(de);
                de = null;
            }
            if (flag){
                Collections.sort(moneyDeatils);
                listView.setAdapter(new PayCheckItemAdapter(context,moneyDeatils,WEEK));
            }else{
                listView.setVisibility(View.GONE);
                notingTx.setVisibility(View.VISIBLE);
            }
            rs.close();
            sttm.close();
        } catch (SQLException e) {
            ToastUtils.showShort(context,e.getMessage().toString());
            e.printStackTrace();
        }finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initView() {
        findView();
        moneyDeatils = new ArrayList<>();
        String sql = "select * from MoneyDetail where accountid = '" + SharedPreferenceHelper.getCurrentUser(context).getAccountid() + "'and schoolcode = '" + SharedPreferenceHelper.getCurrentUser(context).getSchoolcode() + "' a" +
                "nd happendate Between  '" + new SimpleDateFormat("yyyy-MM-dd").format(getCurrentWeekDayStartTime()) + "' and '"+new SimpleDateFormat("yyyy-MM-dd").format(getCurrentWeekDayEndTime())+"'";
        HttpHelper.getDada(getActivity(), handler, sql);
    }

    private void findView() {
        listView = (ListView) view.findViewById(R.id.today_fragment_same_listview);
        notingTx = (TextView) view.findViewById(R.id.today_fragment_noting);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_check_same, null);
        return view;
    }
    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public   Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public   Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }
}
