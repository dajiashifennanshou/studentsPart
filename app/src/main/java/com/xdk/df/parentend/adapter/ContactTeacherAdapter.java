package com.xdk.df.parentend.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.ClassTeacher;
import com.xdk.df.parentend.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */
public class ContactTeacherAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private ArrayList<ClassTeacher> list;
    private String phoneNumber;
    private View view;

    public ContactTeacherAdapter(Context context, ArrayList<ClassTeacher> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.activity_contact_teacher_list_item, null, true);
            viewHolder.nameTx = (TextView) convertView.findViewById(R.id.contact_teacher_name);
            viewHolder.positionTx = (TextView) convertView.findViewById(R.id.contact_teacher_position);
            viewHolder.phonekTx = (TextView) convertView.findViewById(R.id.contact_teacher_phone);
            viewHolder.messageImg = (ImageView) convertView.findViewById(R.id.contact_teacher_message);
            viewHolder.callPhoneImg = (ImageView) convertView.findViewById(R.id.contact_teacher_call);
            viewHolder.teacherTitle = (TextView) convertView.findViewById(R.id.contact_teacher_teachertitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ClassTeacher markerItem = (ClassTeacher) getItem(i);
        phoneNumber = markerItem.getTelephone();
        if (null != markerItem) {
            viewHolder.nameTx.setText(markerItem.getTeacher());
            viewHolder.positionTx.setText(markerItem.getCourse());
            viewHolder.phonekTx.setText(markerItem.getTelephone());
            viewHolder.teacherTitle.setText(markerItem.getTeachertitle());
        }
        viewHolder.callPhoneImg.setOnClickListener(this);
        viewHolder.messageImg.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View view) {
        if (phoneNumber.equals(null) || phoneNumber.equals("")) {
            ToastUtils.showShort(context, R.string.str_contact_teacher_no_phone);
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        switch (view.getId()) {
            case R.id.contact_teacher_call:
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                break;
            case R.id.contact_teacher_message:
                context.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber)));
                break;
        }
    }

    public class ViewHolder {
        private TextView nameTx;
        private TextView positionTx;
        private TextView phonekTx;
        private ImageView messageImg;
        private ImageView callPhoneImg;
        private TextView teacherTitle;
    }
}
