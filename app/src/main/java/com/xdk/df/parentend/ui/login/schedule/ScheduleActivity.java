package com.xdk.df.parentend.ui.login.schedule;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.adapter.ScheduleAdapter;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.data.CourseArrangement;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout backll;
    private CourseArrangement zaoCourse = new CourseArrangement();
    private ArrayList<CourseArrangement> courseArrangements = new ArrayList<>();
    private Handler mHandle = new Handler();
    private ListView listView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_schedule_new);
        findView();
        backll.setOnClickListener(this);
        initData();
    }

    private void initData() {
        String sql = "select * from Schooltime where schoolcode = '"+ SharedPreferenceHelper.getCurrentUser(this).getSchoolcode()+"'";
        HttpHelper.getResult(this, mHandle, sql, new HttpHelper.HttpCallBack() {
            @Override
            public void onResponse(ResultSet rs) throws SQLException {
                Boolean flag =false;
                while (rs.next()) {
                    if(!flag){
                        flag = true;
                    }
                    CourseArrangement zaoCourse = new CourseArrangement();
                    zaoCourse.setAutoid(rs.getInt("autoid"));
                    zaoCourse.setSchoolcode(rs.getString("schoolCode"));
                    zaoCourse.setTimeend(rs.getString("timestart"));
                    zaoCourse.setTimestart(rs.getString("timeend"));
                    zaoCourse.setWeek1(rs.getString("week1"));
                    zaoCourse.setWeek2(rs.getString("week2"));
                    zaoCourse.setWeek3(rs.getString("week3"));
                    zaoCourse.setWeek4(rs.getString("week4"));
                    zaoCourse.setWeek5(rs.getString("week5"));
                    zaoCourse.setWeek6(rs.getString("week6"));
                    zaoCourse.setWeek7(rs.getString("week7"));
                    courseArrangements.add(zaoCourse);
                }
                if(flag){
                    setView();
                }else {
                    ToastUtils.showShort(ScheduleActivity.this,"没有课程数据");
                }
            }
        });
    }

    private void setView() {
        ScheduleAdapter adapter = new ScheduleAdapter(this,courseArrangements);
        listView.setAdapter(adapter);
    }

    private void findView() {
        backll = (LinearLayout) findViewById(R.id.schedule_back);
        listView = (ListView) findViewById(R.id.schedule_listview);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_back:
                finish();
                break;
        }
    }
}
