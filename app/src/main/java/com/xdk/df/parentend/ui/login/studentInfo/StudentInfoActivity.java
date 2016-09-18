package com.xdk.df.parentend.ui.login.studentInfo;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.widget.CircleImageView;

import java.text.DecimalFormat;

public class StudentInfoActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout backll;
    private TextView nameTx, codeTx, yuanxiTx, zhuanyeTx, classTx, susheTx, restMoneyTx, timeTx;
    private CircleImageView photoView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_student_info);
        findView();
        initData();
        backll.setOnClickListener(this);
    }

    private void initData() {
        byte[] studentbm =(byte[]) getIntent().getByteArrayExtra("studentimg");
        if(studentbm != null){
            photoView.setImageBitmap(BitmapFactory.decodeByteArray(studentbm, 0, studentbm.length));
        }
        nameTx.setText(SharedPreferenceHelper.getCurrentUser(this).getName());
        codeTx.setText(SharedPreferenceHelper.getCurrentUser(this).getStudentid());
        yuanxiTx.setText(SharedPreferenceHelper.getCurrentUser(this).getSchool());
        zhuanyeTx.setText(SharedPreferenceHelper.getCurrentUser(this).getProfessional());
        classTx.setText(SharedPreferenceHelper.getCurrentUser(this).getGrade() + "级" + SharedPreferenceHelper.getCurrentUser(this).getSclass() + "班");
        susheTx.setText(SharedPreferenceHelper.getCurrentUser(this).getApertment() + SharedPreferenceHelper.getCurrentUser(this).getRoom());
        DecimalFormat df   = new DecimalFormat("######0.00");
        restMoneyTx.setText( df.format(SharedPreferenceHelper.getCurrentUser(this).getCardmoney()) + "");
        timeTx.setText(SharedPreferenceHelper.getCurrentUser(this).getValiddate());
    }

    private void findView() {
        nameTx = (TextView) findViewById(R.id.student_info_name);
        codeTx = (TextView) findViewById(R.id.student_info_code);
        yuanxiTx = (TextView) findViewById(R.id.student_info_yuanxi);
        zhuanyeTx = (TextView) findViewById(R.id.student_info_zhuanye);
        classTx = (TextView) findViewById(R.id.student_info_class);
        susheTx = (TextView) findViewById(R.id.student_info_sushe);
        restMoneyTx = (TextView) findViewById(R.id.student_info_restMoney);
        timeTx = (TextView) findViewById(R.id.student_info_time);
        backll = (LinearLayout) findViewById(R.id.student_info_back);
        photoView = (CircleImageView) findViewById(R.id.student_info_img);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.student_info_back:
                finish();
                break;
        }
    }
}
