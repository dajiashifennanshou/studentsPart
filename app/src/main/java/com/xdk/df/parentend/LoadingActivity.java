package com.xdk.df.parentend;

import android.os.AsyncTask;
import android.os.Handler;
import android.widget.TextView;

import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.ui.login.LoginActivity;
import com.xdk.df.parentend.utils.CurrentUserHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by df on 2016/8/5.
 */
public class LoadingActivity extends BaseActivity {
    private long currentTime;
    private static final long loadingTime = 2000;
    private Handler handler = new Handler();

    @Override
    public void initView() {
        setContentView(R.layout.activity_loading);
        new LoadingTask().execute("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    class LoadingTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            currentTime = System.currentTimeMillis();
            if (SharedPreferenceHelper.getCurrentUser(LoadingActivity.this) == null) {
                return false;
            } else {
                long a = SharedPreferenceHelper.getLoginTime(LoadingActivity.this);
                if (currentTime - SharedPreferenceHelper.getLoginTime(LoadingActivity.this) > 24 * 60 * 60 * 1000) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            toastShort(R.string.str_user_info_overTime);
                        }
                    });
                    SharedPreferenceHelper.putCurrentUser(LoadingActivity.this, null);
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (System.currentTimeMillis() - currentTime < loadingTime) {
                if (aBoolean) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (CurrentUserHelper.getUserVip(LoadingActivity.this)) {
                                goActivity(MainActivity.class);
                                SharedPreferenceHelper.putLoginTime(LoadingActivity.this, System.currentTimeMillis());
                                finish();
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showShort(LoadingActivity.this, R.string.str_user_no_vip);
                                    }
                                });
                                goActivity(LoginActivity.class);
                            }
                        }
                    }, loadingTime - (System.currentTimeMillis() - currentTime));
                } else {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            goActivity(LoginActivity.class);
                            finish();
                        }
                    }, loadingTime - (System.currentTimeMillis() - currentTime));
                }

            } else {
                if (aBoolean) {
                    if (CurrentUserHelper.getUserVip(LoadingActivity.this)) {
                        goActivity(MainActivity.class);
                        SharedPreferenceHelper.putLoginTime(LoadingActivity.this, System.currentTimeMillis());
                        finish();
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShort(LoadingActivity.this, R.string.str_user_no_vip);
                            }
                        });
                        goActivity(LoginActivity.class);
                    }
                } else {
                    goActivity(LoginActivity.class);
                    finish();
                }
            }
        }
    }
}
