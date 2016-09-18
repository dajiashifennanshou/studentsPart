package com.xdk.df.parentend.ui.login.todayCheck;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.xdk.df.parentend.widget.RefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/16.
 */
public class TodayCheckFragment extends BaseFragment {
    private View view;
    private ListView listView;
    private ArrayList<CheckDetail> listCheck;
    private ArrayList<CheckDetail> todaylist;
    private TextView notingTx;
    private RefreshLayout refreshLayout;
    private TodayCheckAdapter adapter;
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
            int vip = HttpHelper.compare_date(listCheck.get(i).getHappendate(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (vip == 0) {
                todaylist.add(listCheck.get(i));
            }
        }
        if (todaylist.size() == 0) {
            notingTx.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            Collections.sort(todaylist);
            adapter = new TodayCheckAdapter(context, todaylist, handler);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void initView() {
        todaylist = new ArrayList<CheckDetail>();
        listCheck = new ArrayList<CheckDetail>();
        listView = (ListView) view.findViewById(R.id.today_fragment_same_listview);
        notingTx = (TextView) view.findViewById(R.id.today_fragment_noting);
        HttpHelper.getCheckDetail(getActivity(), handler);
//        refreshLayout = (RefreshLayout) view.findViewById(R.id.today_fragment_same_refresh);
//        refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.lightgray));
//        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.green), getResources().getColor(R.color.aliceblue), getResources().getColor(R.color.yellow), getResources().getColor(R.color.aquamarine));
//        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.setLoading(false);
//                    }
//                }, 2000);
//            }
//        });
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_check_same, null);
        return view;
    }
}
