package com.xdk.df.parentend.ui.login.todayCheck;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class TodayCheckActivity extends BaseActivity implements View.OnClickListener {
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private LinearLayout backll;
    private ViewPager mPageVp;
    /**
     * Tab显示内容TextView
     */
    private TextView mTabChatTv, mTabContactsTv, mTabFriendTv;
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex = 0;
//    private TodayFragment todayFragment;
//    private ToWeekFragment toYearFragment;
//    private ToMonthFragment toMonthFragment;
    private TodayCheckFragment todayFragment;
    private ToWeekCheckFragment toYearFragment;
    private ToMonthCheckFragment toMonthFragment;
    private LinearLayout todayLy;
    private LinearLayout monthLy;
    private LinearLayout yearLy;

    @Override
    public void initView() {
        setContentView(R.layout.activity_today_check);
        backll = (LinearLayout) findViewById(R.id.today_check_back);
        todayLy = (LinearLayout) findViewById(R.id.id_tab_chat_ll);
        monthLy = (LinearLayout) findViewById(R.id.id_tab_friend_ll);
        yearLy = (LinearLayout) findViewById(R.id.id_tab_contacts_ll);
        todayLy.setOnClickListener(this);
        monthLy.setOnClickListener(this);
        yearLy.setOnClickListener(this);
        mTabContactsTv = (TextView) this.findViewById(R.id.id_contacts_tv);
        mTabChatTv = (TextView) this.findViewById(R.id.id_chat_tv);
        mTabFriendTv = (TextView) this.findViewById(R.id.id_friend_tv);
        mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
        setOnclick();
        todayFragment = new TodayCheckFragment();
        toYearFragment = new ToWeekCheckFragment();
        toMonthFragment = new ToMonthCheckFragment();
        mFragmentList.add(todayFragment);
        mFragmentList.add(toYearFragment);
        mFragmentList.add(toMonthFragment);
        mFragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);
        todayLy.setBackgroundColor(getResources().getColor(R.color.seaShell));
        mPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mTabChatTv.setTextColor(getResources().getColor(R.color.mediumseagreen));
                        todayLy.setBackgroundColor(getResources().getColor(R.color.seaShell));
                        break;
                    case 1:
                        mTabFriendTv.setTextColor(getResources().getColor(R.color.mediumseagreen));
                        monthLy.setBackgroundColor(getResources().getColor(R.color.seaShell));
                        break;
                    case 2:
                        mTabContactsTv.setTextColor(getResources().getColor(R.color.mediumseagreen));
                        yearLy.setBackgroundColor(getResources().getColor(R.color.seaShell));
                        break;
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setOnclick() {
        backll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        mTabChatTv.setTextColor(Color.BLACK);
        mTabFriendTv.setTextColor(Color.BLACK);
        mTabContactsTv.setTextColor(Color.BLACK);
        todayLy.setBackgroundColor(getResources().getColor(R.color.white));
        monthLy.setBackgroundColor(getResources().getColor(R.color.white));
        yearLy.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tab_chat_ll:
                if(currentIndex ==0){
                    return;
                }
                resetTextView();
                mTabChatTv.setTextColor(getResources().getColor(R.color.mediumseagreen));
                todayLy.setBackgroundColor(getResources().getColor(R.color.seaShell));
                currentIndex = 0;
                break;
            case R.id.id_tab_friend_ll:
                if(currentIndex ==1){
                    return;
                }
                resetTextView();
                mTabFriendTv.setTextColor(getResources().getColor(R.color.mediumseagreen));
                monthLy.setBackgroundColor(getResources().getColor(R.color.seaShell));
                currentIndex = 1;
                break;
            case R.id.id_tab_contacts_ll:
                if(currentIndex ==2){
                    return;
                }
                resetTextView();
                mTabContactsTv.setTextColor(getResources().getColor(R.color.mediumseagreen));
                yearLy.setBackgroundColor(getResources().getColor(R.color.seaShell));
                currentIndex = 2;
                break;
        }
        mPageVp.setCurrentItem(currentIndex);
    }
}
