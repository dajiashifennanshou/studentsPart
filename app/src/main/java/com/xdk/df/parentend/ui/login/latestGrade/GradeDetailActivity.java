package com.xdk.df.parentend.ui.login.latestGrade;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.adapter.GradeDetailAdapterr;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.data.CourseMark;
import com.xdk.df.parentend.data.HttpData;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/17.
 */
public class GradeDetailActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout backll;
    private ListView listView;
    private ArrayList<CourseMark> resultMarkslist;
    private ResultSet rs;
    private Connection con;
    private Statement sttm;
    private HttpData data;
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
            }
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_grade_detail);
        findView();
        backll.setOnClickListener(this);
        String sql = "select * from CourseMark where accountid = '" + SharedPreferenceHelper.getCurrentUser(this).getAccountid() + "'and schoolcode = '" + SharedPreferenceHelper.getCurrentUser(this).getSchoolcode() + "' and examination = '"+ getIntent().getStringExtra("exam")+"'";
        HttpHelper.getDada(this, handler, sql);
    }

    private void initData() {
        try {
            boolean flag = false;
            while (rs.next()) {
                flag = true;
                CourseMark courseMark = new CourseMark();
                courseMark.setCourse(rs.getString("course"));
                courseMark.setMark(rs.getDouble("mark"));
                courseMark.setClassrank(rs.getInt("classrank"));
                courseMark.setGraderank(rs.getInt("graderank"));
                resultMarkslist.add(courseMark);
            }
            if (!flag) {
                toastShort("没有查询到考试数据");
            }
            rs.close();
            sttm.close();
        } catch (SQLException e) {
            e.printStackTrace();
            toastShort(e.getMessage().toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        listView.setAdapter(new GradeDetailAdapterr(this, resultMarkslist));
    }

    private void findView() {
        resultMarkslist = new ArrayList<CourseMark>();
        listView = (ListView) findViewById(R.id.latest_detaile_detection_listView);
        backll = (LinearLayout) findViewById(R.id.latest_detaile_detection_back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.latest_detaile_detection_back:
                finish();
                break;
        }
    }
}
