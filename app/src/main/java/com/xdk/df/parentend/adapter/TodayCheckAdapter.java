package com.xdk.df.parentend.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.CheckDetail;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.ui.login.todayCheck.ShowPhotoActivity;
import com.xdk.df.parentend.utils.BitmapCompressor;
import com.xdk.df.parentend.utils.FileUtils;
import com.xdk.df.parentend.utils.ImageDownLoader;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/8/16.
 */
public class TodayCheckAdapter extends BaseAdapter{
    private Activity context;
    private ArrayList<CheckDetail> list;
    private View view;
    private Handler handler;
    private String currentId;

    public TodayCheckAdapter(Context context, ArrayList<CheckDetail> list, Handler handler) {
        this.context = (Activity) context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.fragment_check_item, null, true);
            viewHolder.placeTx = (TextView) convertView.findViewById(R.id.fragment_todaycheck_item_place);
            viewHolder.stateTx = (TextView) convertView
                    .findViewById(R.id.fragment_todaycheck_item_state);
            viewHolder.timeTx = (TextView) convertView
                    .findViewById(R.id.fragment_todaycheck_item_time);
            viewHolder.addressTx = (TextView) convertView.findViewById(R.id.fragment_todaycheck_item_address);
            viewHolder.showPhoto = (TextView) convertView.findViewById(R.id.fragment_todaycheck_item_showphoto);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CheckDetail markerItem = (CheckDetail) getItem(i);
        if (null != markerItem) {
            if (markerItem.getHappentime() == null) {
                viewHolder.timeTx.setText(markerItem.getHappendate());
            } else {
                viewHolder.timeTx.setText(markerItem.getHappendate() + " " + markerItem.getHappentime());
            }
            viewHolder.stateTx.setText(markerItem.getCheckaspect());
            viewHolder.placeTx.setText(markerItem.getChecktype());
            viewHolder.addressTx.setText(markerItem.getHappenaddress());
            currentId = "c"+String.valueOf(markerItem.getAutoid());
            viewHolder.showPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentId = "c"+String.valueOf(markerItem.getAutoid());
                    Intent intent = new Intent(context, ShowPhotoActivity.class);
                    intent.putExtra("currentId",currentId);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView timeTx;
        private TextView placeTx;
        private TextView stateTx;
        private TextView addressTx;
        private TextView showPhoto;
    }
}
