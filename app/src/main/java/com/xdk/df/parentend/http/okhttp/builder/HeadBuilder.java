package com.xdk.df.parentend.http.okhttp.builder;

import com.xdk.df.parentend.http.okhttp.OkHttpUtils;
import com.xdk.df.parentend.http.okhttp.request.OtherRequest;
import com.xdk.df.parentend.http.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
