package com.xdk.df.parentend.ui.login.payCheck;

import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PayCheckActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout frameLayout;
    private LinearLayout backll;
    private SparseArray<Fragment> fragmentList;
    private PayCheckTodayFragment todayFragment;
    private PaycheckTomonthFragment tomonthFragment;
    private PayCheckToweekFragment toweekFragment;
    private PayCheckTowMonthFragment towMonthFragment;
    /**
     * Tab显示内容TextView
     */
    private TextView mMonthTv, mTodayTv, mWeekTv, mtwoMonthTv;
    private LinearLayout monthll, todayll, weekll;
    private LinearLayout towMonthll;
    private int[] state = {0, 1, 2, 3};
    private int crurrentItem = 0;

    @Override
    public void initView() {
        setContentView(R.layout.avticity_pay_check);
        fragmentList = new SparseArray<Fragment>();
        todayFragment = new PayCheckTodayFragment();
        tomonthFragment = new PaycheckTomonthFragment();
        toweekFragment = new PayCheckToweekFragment();
        towMonthFragment = new PayCheckTowMonthFragment();
        fragmentList.clear();
        findView();
        backll.setOnClickListener(this);
        monthll.setOnClickListener(this);
        todayll.setOnClickListener(this);
        weekll.setOnClickListener(this);
//        towMonthll.setOnClickListener(this);
        setFragment();
        initFramne();
    }

    private void initFramne() {
        todayll.setBackgroundColor(getResources().getColor(R.color.seaShell));
        getSupportFragmentManager().beginTransaction().replace(R.id.pay_check_frame,todayFragment).commitAllowingStateLoss();
    }

    private void setFragment() {
        fragmentList.put(state[0],todayFragment);
        fragmentList.put(state[1],toweekFragment);
        fragmentList.put(state[2],tomonthFragment);
        fragmentList.put(state[3],towMonthFragment);
    }

    private void findView() {
        frameLayout = (FrameLayout) findViewById(R.id.pay_check_frame);
        monthll = (LinearLayout) findViewById(R.id.id_tab_month_ll);
        todayll = (LinearLayout) findViewById(R.id.id_tab_today_ll);
        weekll = (LinearLayout) findViewById(R.id.id_tab_week_ll);
//        towMonthll = (LinearLayout) findViewById(R.id.id_tab_towmonth_ll);
        backll = (LinearLayout) findViewById(R.id.pay_check_back);
        mMonthTv = (TextView) this.findViewById(R.id.id_month_tv);
        mTodayTv = (TextView) this.findViewById(R.id.id_today_tv);
        mWeekTv = (TextView) this.findViewById(R.id.id_week_tv);
//        mtwoMonthTv = (TextView) findViewById(R.id.id_tab_towmonth_Tx);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_check_back:
                finish();
                break;
            case R.id.id_tab_today_ll:
                if( crurrentItem == 0){
                    return;
                }
                switch (crurrentItem){
                    case 0:
                        todayll.setBackgroundColor(getResources().getColor(R.color.white));
                        mTodayTv.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 1:
                        weekll.setBackgroundColor(getResources().getColor(R.color.white));
                        mWeekTv.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 2:
                        monthll.setBackgroundColor(getResources().getColor(R.color.white));
                        mMonthTv.setTextColor(getResources().getColor(R.color.black));
                        break;
//                    case 3:
//                        towMonthll.setBackgroundColor(getResources().getColor(R.color.white));
//                        mtwoMonthTv.setTextColor(getResources().getColor(R.color.black));
//                        break;
                }
                todayll.setBackgroundColor(getResources().getColor(R.color.seaShell));
                mTodayTv.setTextColor(getResources().getColor(R.color.green));
                crurrentItem = 0;
                getSupportFragmentManager().beginTransaction().replace(R.id.pay_check_frame,fragmentList.get(0)).commitAllowingStateLoss();
                break;
            case R.id.id_tab_week_ll:
                if( crurrentItem == 1){
                    return;
                }
                switch (crurrentItem){
                    case 0:
                        todayll.setBackgroundColor(getResources().getColor(R.color.white));
                        mTodayTv.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 1:
                        weekll.setBackgroundColor(getResources().getColor(R.color.white));
                        mWeekTv.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 2:
                        monthll.setBackgroundColor(getResources().getColor(R.color.white));
                        mMonthTv.setTextColor(getResources().getColor(R.color.black));
                        break;
//                    case 3:
//                        towMonthll.setBackgroundColor(getResources().getColor(R.color.white));
//                        mtwoMonthTv.setTextColor(getResources().getColor(R.color.black));
//                        break;
                }
                weekll.setBackgroundColor(getResources().getColor(R.color.seaShell));
                mWeekTv.setTextColor(getResources().getColor(R.color.green));
                crurrentItem = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.pay_check_frame,fragmentList.get(1)).commitAllowingStateLoss();
                break;
            case R.id.id_tab_month_ll:
                if( crurrentItem == 2){
                    return;
                }
                switch (crurrentItem){
                    case 0:
                        todayll.setBackgroundColor(getResources().getColor(R.color.white));
                        mTodayTv.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 1:
                        weekll.setBackgroundColor(getResources().getColor(R.color.white));
                        mWeekTv.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 2:
                        monthll.setBackgroundColor(getResources().getColor(R.color.white));
                        mMonthTv.setTextColor(getResources().getColor(R.color.black));
                        break;
//                    case 3:
//                        towMonthll.setBackgroundColor(getResources().getColor(R.color.white));
//                        mtwoMonthTv.setTextColor(getResources().getColor(R.color.black));
//                        break;
                }
                monthll.setBackgroundColor(getResources().getColor(R.color.seaShell));
                mMonthTv.setTextColor(getResources().getColor(R.color.green));
                crurrentItem = 2;
                getSupportFragmentManager().beginTransaction().replace(R.id.pay_check_frame,fragmentList.get(2)).commitAllowingStateLoss();
                break;
//            case R.id.id_tab_towmonth_ll:
//                if( crurrentItem == 3){
//                    return;
//                }
//                switch (crurrentItem){
//                    case 0:
//                        todayll.setBackgroundColor(getResources().getColor(R.color.white));
//                        mTodayTv.setTextColor(getResources().getColor(R.color.black));
//                        break;
//                    case 1:
//                        weekll.setBackgroundColor(getResources().getColor(R.color.white));
//                        mWeekTv.setTextColor(getResources().getColor(R.color.black));
//                        break;
//                    case 2:
//                        monthll.setBackgroundColor(getResources().getColor(R.color.white));
//                        mMonthTv.setTextColor(getResources().getColor(R.color.black));
//                        break;
//                    case 3:
//                        towMonthll.setBackgroundColor(getResources().getColor(R.color.white));
//                        mtwoMonthTv.setTextColor(getResources().getColor(R.color.black));
//                        break;
//                }
//                towMonthll.setBackgroundColor(getResources().getColor(R.color.seaShell));
//                mtwoMonthTv.setTextColor(getResources().getColor(R.color.green));
//                crurrentItem = 3;
//                getSupportFragmentManager().beginTransaction().replace(R.id.pay_check_frame,fragmentList.get(3)).commitAllowingStateLoss();
//                break;
        }
    }

}
