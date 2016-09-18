package com.xdk.df.parentend.ui.login.contactTeacher;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.adapter.ContactTeacherAdapter;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.data.ClassTeacher;
import com.xdk.df.parentend.data.CurrentUser;
import com.xdk.df.parentend.data.HttpData;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class ContactTeacherActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout backll;
    private ArrayList<ClassTeacher> classTeachers;
    private ListView listView;
    private ResultSet rs;
    private TextView nothingTx,titleTx;
    private Connection con;
    private Statement sttm;
    private HttpData data;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!(msg.what == 1)) {
                data = (HttpData) msg.obj;
                con = data.getConnection();
                sttm = data.getStatement();
                rs = data.getResultSet();
                initData();
            } else {
                nothingTx.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        }
    };
    private void initData() {
        try {
            boolean flag = false;
            while (rs.next()) {
                if (!flag) {
                    flag = true;
                }
                ClassTeacher classTeacher = new ClassTeacher();
                classTeacher.setCourse(rs.getString("course"));
                classTeacher.setGrade(rs.getInt("grade"));
                classTeacher.setProfessional(rs.getString("professional"));
                classTeacher.setSchool(rs.getString("school"));
                classTeacher.setSchoolCode(rs.getString("schoolcode"));
                classTeacher.setsClass(rs.getInt("class"));
                String a  = rs.getString("telephone");
                classTeacher.setTelephone(rs.getString("telephone"));
                classTeacher.setTeacher(rs.getString("teacher"));
                classTeachers.add(classTeacher);
            }
            if (!flag) {
                nothingTx.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }else{
                Collections.sort(classTeachers);
                listView.setAdapter(new ContactTeacherAdapter(this,classTeachers));
            }
            rs.close();
            sttm.close();
        } catch (SQLException e) {
            ToastUtils.showShort(this, e.getMessage().toString());
            e.printStackTrace();
        } finally {
            if (con != null) {
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
        setContentView(R.layout.activity_contact_teacher);
        findView();
        backll.setOnClickListener(this);
        CurrentUser currentUser = SharedPreferenceHelper.getCurrentUser(this);
        String sql = "select * from ClassTeacher where schoolcode = '"+ currentUser.getSchoolcode()+"' and school = '"+
                currentUser.getSchool()+"' and professional = '"+currentUser.getProfessional()+"' and grade = '"+currentUser.getGrade()+
                "' and class = '"+currentUser.getSclass()+"'";
        titleTx.setText(currentUser.getProfessional()+currentUser.getGrade()+"级"+currentUser.getSclass()+"班");
        HttpHelper.getDada(this,handler,sql);
    }

    private void findView() {
        classTeachers = new ArrayList<ClassTeacher>();
        titleTx = (TextView) findViewById(R.id.contact_teacher_title);
        nothingTx = (TextView) findViewById(R.id.today_fragment_noting);
        listView = (ListView) findViewById(R.id.today_fragment_same_listview);
        backll = (LinearLayout) findViewById(R.id.contact_teacher_back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contact_teacher_back:
                finish();
                break;
        }
    }
}
