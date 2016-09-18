package com.xdk.df.parentend.ui.login.studentsFood;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.data.Recipe;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.sql.ResultSet;
import java.sql.SQLException;


public class StudentFoodsActivity extends BaseActivity implements View.OnClickListener {


    TextView foodsZaoWeek1;
    TextView foodsWuWeek1;
    TextView foodsWanWeek1;
    TextView foodsyeWeek1;
    TextView foodsZaoWeek2;
    TextView foodsWuWeek2;
    TextView foodsyeWeek2;
    TextView foodsWanWeek2;
    TextView foodsZaoWeek3;
    TextView foodsWanWeek3;
    TextView foodsWuWeek3;
    TextView foodsyeWeek3;
    TextView foodsZaoWeek4;
    TextView foodsWuWeek4;
    TextView foodsWanWeek4;
    TextView foodsyeWeek4;
    TextView foodsZaoWeek5;
    TextView foodsWuWeek5;
    TextView foodsWanWeek5;
    TextView foodsyeWeek5;
    TextView foodsZaoWeek6;
    TextView foodsWuWeek6;
    TextView foodsWanWeek6;
    TextView foodsyeWeek6;
    TextView foodsZaoWeek7;
    TextView foodsWuWeek7;
    TextView foodsWanWeek7;
    TextView foodsyeWeek7;
    private LinearLayout backll;
    private Handler mhandler = new Handler();
    private Recipe zaoRecipe = new Recipe();
    private Recipe zhongRecipe = new Recipe();
    private Recipe wanRecipe = new Recipe();

    private void findView() {
        backll = (LinearLayout) findViewById(R.id.student_foods_back);
        foodsZaoWeek1 = (TextView) findViewById(R.id.foods_zao_week1);
        foodsZaoWeek2 = (TextView) findViewById(R.id.foods_zao_week2);
        foodsZaoWeek3 = (TextView) findViewById(R.id.foods_zao_week3);
        foodsZaoWeek4 = (TextView) findViewById(R.id.foods_zao_week4);
        foodsZaoWeek5 = (TextView) findViewById(R.id.foods_zao_week5);
        foodsZaoWeek6 = (TextView) findViewById(R.id.foods_zao_week6);
        foodsZaoWeek7 = (TextView) findViewById(R.id.foods_zao_week7);
        foodsWuWeek1 = (TextView) findViewById(R.id.foods_wu_week1);
        foodsWuWeek2 = (TextView) findViewById(R.id.foods_wu_week2);
        foodsWuWeek3 = (TextView) findViewById(R.id.foods_wu_week3);
        foodsWuWeek4 = (TextView) findViewById(R.id.foods_wu_week4);
        foodsWuWeek5 = (TextView) findViewById(R.id.foods_wu_week5);
        foodsWuWeek6 = (TextView) findViewById(R.id.foods_wu_week6);
        foodsWuWeek7 = (TextView) findViewById(R.id.foods_wu_week7);
        foodsWanWeek1 = (TextView) findViewById(R.id.foods_wu_week1);
        foodsWanWeek2 = (TextView) findViewById(R.id.foods_wu_week2);
        foodsWanWeek3 = (TextView) findViewById(R.id.foods_wu_week3);
        foodsWanWeek4 = (TextView) findViewById(R.id.foods_wu_week4);
        foodsWanWeek5 = (TextView) findViewById(R.id.foods_wu_week5);
        foodsWanWeek6 = (TextView) findViewById(R.id.foods_wu_week6);
        foodsWanWeek7 = (TextView) findViewById(R.id.foods_wu_week7);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_student_foods);
        findView();
        backll.setOnClickListener(this);
        initData();
    }

    private void initData() {
        String sql = "select * from Recipe where schoolcode = '" + SharedPreferenceHelper.getCurrentUser(this).getSchoolcode() + "'";
        HttpHelper.getResult(this, mhandler, sql, new HttpHelper.HttpCallBack() {
            @Override
            public void onResponse(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    boolean a = false;
                    if (!a) {
                        a = true;
                    }
                    if (rs.getString("meal").equals("早餐")) {
                        zaoRecipe.setAutoid(rs.getInt("autoid"));
                        zaoRecipe.setSchoolCode(rs.getString("schoolcode"));
                        zaoRecipe.setMeal(rs.getString("meal"));
                        zaoRecipe.setWeek1(rs.getString("week1"));
                        zaoRecipe.setWeek1(rs.getString("week2"));
                        zaoRecipe.setWeek1(rs.getString("week3"));
                        zaoRecipe.setWeek1(rs.getString("week4"));
                        zaoRecipe.setWeek1(rs.getString("week5"));
                        zaoRecipe.setWeek1(rs.getString("week6"));
                        zaoRecipe.setWeek1(rs.getString("week7"));
                    } else if (rs.getString("meal").equals("午餐")) {
                        zhongRecipe.setAutoid(rs.getInt("autoid"));
                        zhongRecipe.setSchoolCode(rs.getString("schoolcode"));
                        zhongRecipe.setMeal(rs.getString("meal"));
                        zhongRecipe.setWeek1(rs.getString("week1"));
                        zhongRecipe.setWeek1(rs.getString("week2"));
                        zhongRecipe.setWeek1(rs.getString("week3"));
                        zhongRecipe.setWeek1(rs.getString("week4"));
                        zhongRecipe.setWeek1(rs.getString("week5"));
                        zhongRecipe.setWeek1(rs.getString("week6"));
                        zhongRecipe.setWeek1(rs.getString("week7"));
                    } else {
                        wanRecipe.setAutoid(rs.getInt("autoid"));
                        wanRecipe.setSchoolCode(rs.getString("schoolcode"));
                        wanRecipe.setMeal(rs.getString("meal"));
                        wanRecipe.setWeek1(rs.getString("week1"));
                        wanRecipe.setWeek1(rs.getString("week2"));
                        wanRecipe.setWeek1(rs.getString("week3"));
                        wanRecipe.setWeek1(rs.getString("week4"));
                        wanRecipe.setWeek1(rs.getString("week5"));
                        wanRecipe.setWeek1(rs.getString("week6"));
                        wanRecipe.setWeek1(rs.getString("week7"));
                    }
                    if (a) {
                        setData();
                    }
                    ToastUtils.showShort(StudentFoodsActivity.this, "没有数据");
                }
            }

            private void setData() {
                foodsZaoWeek1.setText(zaoRecipe.getWeek1());
                foodsZaoWeek2.setText(zaoRecipe.getWeek2());
                foodsZaoWeek3.setText(zaoRecipe.getWeek3());
                foodsZaoWeek4.setText(zaoRecipe.getWeek4());
                foodsZaoWeek5.setText(zaoRecipe.getWeek5());
                foodsZaoWeek6.setText(zaoRecipe.getWeek6());
                foodsZaoWeek7.setText(zaoRecipe.getWeek7());
                foodsWuWeek1.setText(zhongRecipe.getWeek1());
                foodsWuWeek2.setText(zhongRecipe.getWeek2());
                foodsWuWeek3.setText(zhongRecipe.getWeek3());
                foodsWuWeek4.setText(zhongRecipe.getWeek4());
                foodsWuWeek5.setText(zhongRecipe.getWeek5());
                foodsWuWeek6.setText(zhongRecipe.getWeek6());
                foodsWuWeek7.setText(zhongRecipe.getWeek7());
                foodsWanWeek1.setText(wanRecipe.getWeek1());
                foodsWanWeek2.setText(wanRecipe.getWeek2());
                foodsWanWeek3.setText(wanRecipe.getWeek3());
                foodsWanWeek4.setText(wanRecipe.getWeek4());
                foodsWanWeek5.setText(wanRecipe.getWeek5());
                foodsWanWeek4.setText(wanRecipe.getWeek4());
                foodsWanWeek5.setText(wanRecipe.getWeek5());
                foodsWanWeek6.setText(wanRecipe.getWeek6());
                foodsWanWeek7.setText(wanRecipe.getWeek7());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.student_foods_back:
                finish();
                break;
        }
    }
}
