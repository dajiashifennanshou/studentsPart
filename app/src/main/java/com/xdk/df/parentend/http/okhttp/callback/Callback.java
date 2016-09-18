package com.xdk.df.parentend.http.okhttp.callback;


import android.content.Context;

import com.xdk.df.parentend.application.XdkApplication;
import com.xdk.df.parentend.http.wait.DialogPolicy;
import com.xdk.df.parentend.utils.ToastUtils;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T>
{
    Context activity;
    private DialogPolicy waitPolicy;
    public Callback(Context activity){
        this.activity = activity;
        waitPolicy = new DialogPolicy(activity);
    }
    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request, int id)
    {
        waitPolicy.displayLoading();
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter(int id)
    {
        waitPolicy.disappear();
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total , int id)
    {

    }

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateReponse(Response response, int id)
    {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response, int id) throws Exception;

    public void onError(Call call, Exception e, int id){
        ToastUtils.showShort(activity,e.getMessage().toString());
    }

    public abstract void onResponse(T response, int id);


    public static Callback CALLBACK_DEFAULT = new Callback(XdkApplication.getContext())
    {

        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception
        {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {

        }

        @Override
        public void onResponse(Object response, int id)
        {

        }
    };

}