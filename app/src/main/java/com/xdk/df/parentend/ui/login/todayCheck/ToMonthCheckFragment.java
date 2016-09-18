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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ToMonthCheckFragment extends BaseFragment {
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

    private void getTodayCheck() {
        for (int i = 0; i < listCheck.size(); i++) {
            int vip = HttpHelper.compare_date(listCheck.get(i).getHappendate(), new SimpleDateFormat("yyyy-MM-dd").format(getCurrentMonthStartTime()));
            if (vip == 0 || vip == 1) {
                int flag = HttpHelper.compare_date(listCheck.get(i).getHappendate(), new SimpleDateFormat("yyyy-MM-dd").format(getCurrentMonthEndTime()));
                if (flag == -1 || flag == 0) {
                    todaylist.add(listCheck.get(i));
                }
            }
        }
        if (todaylist.size() == 0) {
            notingTx.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            Collections.sort(todaylist);
            listView.setAdapter(new TodayCheckAdapter(context, todaylist, handler));
        }
    }

    @Override
    public void initView() {
        todaylist = new ArrayList<CheckDetail>();
        listCheck = new ArrayList<CheckDetail>();
        listView = (ListView) view.findViewById(R.id.today_fragment_same_listview);
        notingTx = (TextView) view.findViewById(R.id.today_fragment_noting);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.today_fragment_same_refresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.green), getResources().getColor(R.color.aliceblue), getResources().getColor(R.color.yellow), getResources().getColor(R.color.aquamarine));
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.lightgray));
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
     * 获得本月的开始时间，即2012-01-01
     *
     * @return
     */
    public Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     *
     * @return
     */
    public Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

//    @Override
//    public void onRefresh() {
//        HttpHelper.getCheckDetail(getActivity(), handler);
//        flag = true;
//    }
}
