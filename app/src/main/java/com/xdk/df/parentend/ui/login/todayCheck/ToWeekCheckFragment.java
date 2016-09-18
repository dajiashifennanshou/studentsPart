package com.xdk.df.parentend.ui.login.todayCheck;

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
import com.xdk.df.parentend.adapter.TodayCheckAdapter;
import com.xdk.df.parentend.base.BaseFragment;
import com.xdk.df.parentend.data.CheckDetail;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ToWeekCheckFragment extends BaseFragment {
    private View view;
    private ListView listView;
    private ArrayList<CheckDetail> listCheck;
    private ArrayList<CheckDetail> todaylist;
    private TextView notingTx;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                notingTx.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                listCheck = (ArrayList<CheckDetail>) msg.obj;
                getTodayCheck();
            }
        }
    };
    private void getTodayCheck(){
        for (int i = 0; i < listCheck.size(); i++) {
            int vip = HttpHelper.compare_date(listCheck.get(i).getHappendate(), new SimpleDateFormat("yyyy-MM-dd").format(getCurrentWeekDayStartTime()));
            if (vip == 0 || vip == 1) {
                int flag = HttpHelper.compare_date(listCheck.get(i).getHappendate(), new SimpleDateFormat("yyyy-MM-dd").format(getCurrentWeekDayEndTime()));
                if(flag == 0|| flag == -1){
                    todaylist.add(listCheck.get(i));
                }
            }
        }
        if(todaylist.size() == 0){
            notingTx.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            Collections.sort(todaylist);
            listView.setAdapter(new TodayCheckAdapter(context,todaylist,handler));
        }
    }
    @Override
    public void initView() {
        todaylist = new ArrayList<CheckDetail>();
        listCheck = new ArrayList<CheckDetail>();
        listView = (ListView) view.findViewById(R.id.today_fragment_same_listview);
        notingTx = (TextView) view.findViewById(R.id.today_fragment_noting);
        HttpHelper.getCheckDetail(getActivity(), handler);
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
