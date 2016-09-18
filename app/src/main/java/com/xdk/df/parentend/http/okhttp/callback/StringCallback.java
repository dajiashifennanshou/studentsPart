package com.xdk.df.parentend.http.okhttp.callback;

import android.app.Activity;

import com.xdk.df.parentend.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class StringCallback extends Callback<String>
{
    public StringCallback(Activity activity) {
        super(activity);
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }

}
