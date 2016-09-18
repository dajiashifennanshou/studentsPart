package com.xdk.df.parentend.ui.login.leaveInput;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/18.
 */
public class AskLeaveActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout backll;
    private TextView toWeekTv, toMonthTv;
    private FrameLayout containerFrame;
    private ToMonthLeaveFragment tomonthFragment;
    private ToWeekLeaveFragment toweekFragment;
    private boolean flag = true;


    @Override
    public void initView() {
        setContentView(R.layout.activty_ask_leave);
        findView();
        backll.setOnClickListener(this);
        toMonthTv.setOnClickListener(this);
        toWeekTv.setOnClickListener(this);
        toWeekTv.setTextColor(getResources().getColor(R.color.green));
        toWeekTv.setBackgroundColor(getResources().getColor(R.color.seaShell));
        getSupportFragmentManager().beginTransaction().replace(R.id.ask_leave_frame, toweekFragment).commitAllowingStateLoss();
    }

    private void findView() {
        toMonthTv = (TextView) findViewById(R.id.ask_leave_tomonth);
        toWeekTv = (TextView) findViewById(R.id.ask_leave_toweek);
        backll = (LinearLayout) findViewById(R.id.ask_leave_back);
        containerFrame = (FrameLayout) findViewById(R.id.ask_leave_frame);
        tomonthFragment = new ToMonthLeaveFragment();
        toweekFragment = new ToWeekLeaveFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ask_leave_back:
                finish();
                break;
            case R.id.ask_leave_toweek:
                if (flag) {
                    return;
                }
                toMonthTv.setTextColor(getResources().getColor(R.color.black));
                toMonthTv.setBackgroundColor(getResources().getColor(R.color.white));
                toWeekTv.setTextColor(getResources().getColor(R.color.green));
                toWeekTv.setBackgroundColor(getResources().getColor(R.color.seaShell));
                getSupportFragmentManager().beginTransaction().replace(R.id.ask_leave_frame, toweekFragment).commitAllowingStateLoss();
                flag = true;
                break;
            case R.id.ask_leave_tomonth:
                if (!flag) {
                    return;
                }
                toWeekTv.setTextColor(getResources().getColor(R.color.black));
                toWeekTv.setBackgroundColor(getResources().getColor(R.color.white));
                toMonthTv.setTextColor(getResources().getColor(R.color.green));
                toMonthTv.setBackgroundColor(getResources().getColor(R.color.seaShell));
                getSupportFragmentManager().beginTransaction().replace(R.id.ask_leave_frame, tomonthFragment).commitAllowingStateLoss();
                flag = false;
                break;
        }
    }
}
