package com.xdk.df.parentend.ui.login.newmessage;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;

public class NewMessageActivity extends BaseActivity implements View.OnClickListener{
    private TextView toWeekTv, toMonthTv;
    private LinearLayout backll;
    private FrameLayout containerFrame;
    private MessagePersonFragment tomonthFragment;
    private MessageClassFragment toweekFragment;
    private boolean flag = true;


    @Override
    public void initView() {
        setContentView(R.layout.activity_new_message);
        findView();
        backll.setOnClickListener(this);
        toMonthTv.setOnClickListener(this);
        toWeekTv.setOnClickListener(this);
        toWeekTv.setTextColor(getResources().getColor(R.color.green));
        toWeekTv.setBackgroundColor(getResources().getColor(R.color.seaShell));
        getSupportFragmentManager().beginTransaction().replace(R.id.new_message_frame, toweekFragment).commitAllowingStateLoss();
    }

    private void findView() {
        toMonthTv = (TextView) findViewById(R.id.new_message_person);
        toWeekTv = (TextView) findViewById(R.id.new_message_class);
        backll = (LinearLayout) findViewById(R.id.new_message_back);
        containerFrame = (FrameLayout) findViewById(R.id.new_message_frame);
        tomonthFragment = new MessagePersonFragment();
        toweekFragment = new MessageClassFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_message_back:
                finish();
                break;
            case R.id.new_message_class:
                if (flag) {
                    return;
                }
                toMonthTv.setTextColor(getResources().getColor(R.color.black));
                toMonthTv.setBackgroundColor(getResources().getColor(R.color.white));
                toWeekTv.setTextColor(getResources().getColor(R.color.green));
                toWeekTv.setBackgroundColor(getResources().getColor(R.color.seaShell));
                getSupportFragmentManager().beginTransaction().replace(R.id.new_message_frame, toweekFragment).commitAllowingStateLoss();
                flag = true;
                break;
            case R.id.new_message_person:
                if (!flag) {
                    return;
                }
                toWeekTv.setTextColor(getResources().getColor(R.color.black));
                toWeekTv.setBackgroundColor(getResources().getColor(R.color.white));
                toMonthTv.setTextColor(getResources().getColor(R.color.green));
                toMonthTv.setBackgroundColor(getResources().getColor(R.color.seaShell));
                getSupportFragmentManager().beginTransaction().replace(R.id.new_message_frame, tomonthFragment).commitAllowingStateLoss();
                flag = false;
                break;
        }
    }
}
