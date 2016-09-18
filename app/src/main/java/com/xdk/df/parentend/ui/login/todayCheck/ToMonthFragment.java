package com.xdk.df.parentend.ui.login.todayCheck;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseFragment;
import com.xdk.df.parentend.data.CheckDetail;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.ToastUtils;
import com.xdk.df.parentend.widget.ReportArc;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ToMonthFragment extends BaseFragment {
    private LinearLayout normalrl;
    private LinearLayout goll;
    private LinearLayout latell;
    private TextView normalTx;
    private TextView lateTx;
    private TextView unnormalTx;
    private View view;
    private ArrayList<CheckDetail> listCheck;
    private ArrayList<CheckDetail> todaylist;
    private ArrayList<CheckDetail> todaylistNomal;
    private ArrayList<CheckDetail> todaylistUnnomall;
    private ArrayList<CheckDetail> todaylistLate;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                ToastUtils.showShort(context, getString(R.string.str_today_check_noinfo));
                noCheckInfo();
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
                if (flag == -1 ||flag ==0) {
                    todaylist.add(listCheck.get(i));
                }
            }
        }
        for (int i = 0; i < todaylist.size(); i++) {
            if (todaylist.get(i).getChecktype().equals(getString(R.string.str_today_check_nomal))) {
                todaylistNomal.add(todaylist.get(i));
            } else if (todaylist.get(i).getChecktype().equals(getString(R.string.str_today_check_unnomal))) {
                todaylistUnnomall.add(todaylist.get(i));
            } else if (todaylist.get(i).getChecktype().equals(getString(R.string.str_today_check_late))) {
                todaylistLate.add(todaylist.get(i));
            }
        }
        if (todaylist.size() == 0) {
            noCheckInfo();
        } else {
            normalTx.setText(todaylistNomal.size() + "");
            unnormalTx.setText(todaylistUnnomall.size() + "");
            lateTx.setText(todaylistLate.size() + "");
            ReportArc reportArc;
            ReportArc reportArc1;
            ReportArc reportArc2;
            if (todaylistNomal.size() == 0) {
                reportArc = new ReportArc(context, 0, 1, "%", getString(R.string.str_today_check_nomal));
            } else {
                double d = Double.valueOf(todaylistNomal.size()) / Double.valueOf(todaylist.size());
                String a = String.valueOf(d) + "000000".substring(2, 4);
                int n = Integer.valueOf(a);
                reportArc = new ReportArc(context, n, 1, "%", getString(R.string.str_today_check_nomal));
            }
            if (todaylistLate.size() == 0) {
                reportArc1 = new ReportArc(context, 0, 1, "%", getString(R.string.str_today_check_late));
            } else {
                double d = Double.valueOf(todaylistLate.size()) / Double.valueOf(todaylist.size());
                String a = String.valueOf(d) + "000000".substring(2, 4);
                int n = Integer.valueOf(a);
                reportArc1 = new ReportArc(context, n, 1, "%", getString(R.string.str_today_check_late));
            }
            if (todaylistUnnomall.size() == 0) {
                reportArc2 = new ReportArc(context, 0, 1, "%", getString(R.string.str_today_check_unnomal));
            } else {
                double d = Double.valueOf(todaylistUnnomall.size()) / Double.valueOf(todaylist.size());
                String a = String.valueOf(d) + "000000".substring(2, 4);
                int n = Integer.valueOf(a);
                reportArc2 = new ReportArc(context, n, 1, "%", getString(R.string.str_today_check_unnomal));
            }
            normalrl.addView(reportArc);
            latell.addView(reportArc1);
            goll.addView(reportArc2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_today, null);
        return view;
    }

    @Override
    public void initView() {
        findView();
        HttpHelper.getCheckDetail(getActivity(), handler);
    }

    private void findView() {
        normalrl = (LinearLayout) view.findViewById(R.id.today_fragment_normal_check);
        latell = (LinearLayout) view.findViewById(R.id.today_fragment_late_check);
        goll = (LinearLayout) view.findViewById(R.id.today_fragment_go_check);
        normalTx = (TextView) view.findViewById(R.id.today_fragment_normal_times);
        lateTx = (TextView) view.findViewById(R.id.today_fragment_late_times);
        unnormalTx = (TextView) view.findViewById(R.id.today_fragment_unnormal_times);
        todaylist = new ArrayList<CheckDetail>();
        todaylistLate = new ArrayList<CheckDetail>();
        todaylistNomal = new ArrayList<CheckDetail>();
        todaylistUnnomall = new ArrayList<CheckDetail>();
    }

    private void noCheckInfo() {
        ReportArc reportArc = new ReportArc(context, 0, 1, "%", getString(R.string.str_today_check_nomal));
        ReportArc reportArc1 = new ReportArc(context, 0, 2, "%", getString(R.string.str_today_check_late));
        ReportArc reportArc2 = new ReportArc(context, 0, 3, "%", getString(R.string.str_today_check_unnomal));
        normalrl.addView(reportArc);
        latell.addView(reportArc1);
        goll.addView(reportArc2);
        normalTx.setText("0");
        unnormalTx.setText("0");
        lateTx.setText("0");
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
}
