package com.xdk.df.parentend.ui.login.newmessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseFragment;
import com.xdk.df.parentend.data.CurrentUser;
import com.xdk.df.parentend.data.HttpData;
import com.xdk.df.parentend.data.Notice;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/23.
 */
public class MessageClassFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private TextView titleTx, nameTx, timeTx, contentTx, useTimeTx, notingTx;
    private Button nextBt, lastBt;
    private LinearLayout mainll;
    private ResultSet rs;
    private Connection con;
    private Statement sttm;
    private int state = 0;
    private String currentNoticeid, same;
    private String maxNoticeid;
    private HttpData data;
    private Notice notice;
    private boolean hasMessage = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!(msg.what == 1)) {
                data = (HttpData) msg.obj;
                con = data.getConnection();
                sttm = data.getStatement();
                rs = data.getResultSet();
                initData();
            } else {
                if(hasMessage){
                    ToastUtils.showShort(context,"没有数据了");
                    lastBt.setVisibility(View.GONE);
                }else{
                    notingTx.setVisibility(View.VISIBLE);
                    mainll.setVisibility(View.GONE);
                }
            }
        }
    };

    private void initData() {
        try {
            boolean flag = false;
            while (rs.next()) {
                if (!flag) {
                    flag = true;
                }
                notice.setContent(rs.getString("content"));
                notice.setIssuedate(rs.getString("issuedate"));
                notice.setIssuer(rs.getString("issuer"));
                notice.setValiddate(rs.getString("validdate"));
                notice.setMotif(rs.getString("motif"));
                notice.setNoticeid(rs.getString("noticeid"));
            }
            if (!flag) {
                if(hasMessage){
                    ToastUtils.showShort(context,"没有数据了");
                    lastBt.setVisibility(View.GONE);
                }else{
                    notingTx.setVisibility(View.VISIBLE);
                    mainll.setVisibility(View.GONE);
                }
            } else {
                if (mainll.getVisibility() == View.INVISIBLE) {
                    mainll.setVisibility(View.VISIBLE);
                }
                titleTx.setText(notice.getMotif());
                nameTx.setText(notice.getIssuer());
                timeTx.setText(notice.getIssuedate());
                contentTx.setText(notice.getContent());
                useTimeTx.setText("有效期：" + notice.getValiddate());
                currentNoticeid = notice.getNoticeid();
                if (maxNoticeid == null) {
                    maxNoticeid = notice.getNoticeid();
                }
                if (maxNoticeid.equals(notice.getNoticeid())) {
                    nextBt.setVisibility(View.GONE);
                }
            }
            rs.close();
            sttm.close();
        } catch (SQLException e) {
            ToastUtils.showShort(context, e.getMessage().toString());
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initView() {
        findView();
        nextBt.setOnClickListener(this);
        lastBt.setOnClickListener(this);
        CurrentUser user = SharedPreferenceHelper.getCurrentUser(context);
        same = "select MAX(noticeid) from v_notice where  schoolcode = '" + user.getSchoolcode() + "' and grade = '" + user.getGrade() +
                "' and class = '" + user.getSclass() + "' and professional = '" + user.getProfessional() + "' and accountid = '' " + " and validdate >= '" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'";
        String sql1 = "select * from v_notice where noticeid = ( " + same + ")" + " and  schoolcode = '" + user.getSchoolcode() + "'";
        HttpHelper.getDada(getActivity(), handler, sql1);
    }

    private void findView() {
        notice = new Notice();
        titleTx = (TextView) view.findViewById(R.id.message_class_fragment_title);
        nameTx = (TextView) view.findViewById(R.id.message_class_fragment_name);
        timeTx = (TextView) view.findViewById(R.id.message_class_fragment_time);
        contentTx = (TextView) view.findViewById(R.id.message_class_fragment_content);
        useTimeTx = (TextView) view.findViewById(R.id.message_class_fragment_useTime);
        nextBt = (Button) view.findViewById(R.id.message_class_fragment_next);
        lastBt = (Button) view.findViewById(R.id.message_class_fragment_last);
        notingTx = (TextView) view.findViewById(R.id.message_class_fragment_noting);
        mainll = (LinearLayout) view.findViewById(R.id.message_class_fragment_main);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_message_class, null);
        return view;
    }

    @Override
    public void onClick(View view) {
        CurrentUser user = SharedPreferenceHelper.getCurrentUser(context);
        switch (view.getId()) {
            case R.id.message_class_fragment_last:
                String sql = "select * from v_notice where noticeid = ( " + same + "and noticeid <" + currentNoticeid + ")" + " and  schoolcode = '" + user.getSchoolcode() + "'";
                HttpHelper.getDada(getActivity(), handler, sql);
                state = 1;
                nextBt.setVisibility(View.VISIBLE);
                hasMessage = true;
                break;
            case R.id.message_class_fragment_next:
                String sa = "select MIN(noticeid) from v_notice where  schoolcode = '" + user.getSchoolcode() + "' and grade = '" + user.getGrade() +
                        "' and class = '" + user.getSclass() + "' and professional = '" + user.getProfessional() + "' and accountid = '' " + " and validdate >= '" +
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' and noticeid > '" + currentNoticeid + "'";
                String sql1 = "select * from v_notice where noticeid = ( " + sa + ")" + " and  schoolcode = '" + user.getSchoolcode() + "'";
                HttpHelper.getDada(getActivity(), handler, sql1);
                hasMessage = false;
                if(lastBt.getVisibility() != View.VISIBLE){
                    lastBt.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
