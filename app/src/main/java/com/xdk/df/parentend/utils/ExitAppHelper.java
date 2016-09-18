package com.xdk.df.parentend.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.application.XdkApplication;

public class ExitAppHelper {
    private Activity context;
    //--Handler消息字段
    private static final int MSG_EXIT = 1; // 双击返回退出
    private static final int MSG_EXIT_WAIT = MSG_EXIT + 1; // 双击返回退出延时
    /**
     * 消息处理
     */
    private Handler mHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_EXIT:
                    if (mHandle.hasMessages(MSG_EXIT_WAIT)) {
                        context.moveTaskToBack(true);
                        context.finish();
                        XdkApplication.getInstance().exit();
                    } else {
                        ToastUtils.showShort(context, R.string.str_user_logout);
                        mHandle.sendEmptyMessageDelayed(MSG_EXIT_WAIT, 2000);
                    }
                    break;
                case MSG_EXIT_WAIT:
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    public ExitAppHelper(Activity activity) {
        this.context = activity;
    }

    public boolean onClickBack(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            mHandle.sendEmptyMessage(MSG_EXIT);
            return true;
        } else {
            return false;
        }
    }
}
