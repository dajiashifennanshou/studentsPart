package com.xdk.df.parentend.ui.login.payMoney;

import android.os.Bundle;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.http.okhttp.OkHttpUtils;
import com.xdk.df.parentend.http.okhttp.callback.Callback;
import com.xdk.df.parentend.http.okhttp.callback.StringCallback;
import com.xdk.df.parentend.http.wait.DialogPolicy;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class PayMoneyActivity extends BaseActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_money);
        textView = (TextView) findViewById(R.id.money_pay_tx);
    }

    @Override
    public void initView() {
        String url = "http://120.24.227.222:8080/xdkWeb/";
        HttpHelper.getOkhttp(this, url, new StringCallback(this) {
            @Override
            public void onResponse(String response, int id) {
                textView.setText(response.toString());
            }
        });
    }
}
