package com.xdk.df.parentend.http.okhttp.callback;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class BitmapCallback extends Callback<Bitmap>
{
    public BitmapCallback(Activity activity) {
        super(activity);
    }

    @Override
    public Bitmap parseNetworkResponse(Response response , int id) throws Exception
    {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
