package com.xdk.df.parentend.ui.login.schoolInfo;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.data.SchoolIntroduction;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.DensityUtil;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchoolInfoActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout backll;
    private ViewPager viewPager;
    private LinearLayout circleViewContainer;
    private List<ImageView> imglist;
    private SchoolIntroduction introduction = new SchoolIntroduction();
    private Handler mhandler = new Handler();
    private int currentPage = 0;
    private TextView contentTx;
    private Thread threadPlay;
    private Boolean flag = true;
    private List<SchoolIntroduction> schoolIntroductions = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_school_info);
        findView();
        backll.setOnClickListener(this);
        initData();
    }

    private void initData() {
        String sql = "select * from SchoolInfo where schoolcode = '" + SharedPreferenceHelper.getCurrentUser(this).getSchoolcode() + "'";
        HttpHelper.getResult(this, mhandler, sql, new HttpHelper.HttpCallBack() {
            @Override
            public void onResponse(ResultSet rs) throws SQLException {
                boolean flag = false;
                while (rs.next()) {
                    flag = true;
                    introduction.setAutoid(rs.getInt("autoid"));
                    introduction.setNote(rs.getString("note"));
                    introduction.setSchoolcode(rs.getString("schoolcode"));
                    schoolIntroductions.add(introduction);
                }
                if (flag) {
                    setData();
                } else {
                    ToastUtils.showShort(SchoolInfoActivity.this, "没有数据");
                }
            }
        });
    }

    private void setData() {
        initImgView();
    }

    private void initImgView() {
        for (SchoolIntroduction introduction : schoolIntroductions) {
            String sql = "select * from UserPicture where picid = 'S" + introduction.getAutoid() + "'";
            HttpHelper.getPicture(this, mhandler, sql, new HttpHelper.HttpPictureCallBack() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    ImageView view = new ImageView(SchoolInfoActivity.this);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    view.setImageBitmap(bitmap);
                    imglist.add(view);
                    if (imglist.size() == schoolIntroductions.size()) {
                        initAdapter();
                    }
                }
            }, "s" + introduction.getAutoid());
        }
        addPoints(schoolIntroductions.size());
//        ImageView view = new ImageView(this);
//        view.setScaleType(ImageView.ScaleType.FIT_XY);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pager1);
//        view.setImageBitmap(bitmap);
//        ImageView view2 = new ImageView(this);
//        view2.setScaleType(ImageView.ScaleType.FIT_XY);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.pager2);
//        view2.setImageBitmap(bitmap2);
//        ImageView view3 = new ImageView(this);
//        view3.setScaleType(ImageView.ScaleType.FIT_XY);
//        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.pager3);
//        view3.setImageBitmap(bitmap3);
//        imglist.add(view);
//        imglist.add(view2);
//        imglist.add(view3);
//        addPoints(3);
    }

    private void addPoints(int size) {
        for (int i = 0; i < size; i++) {
            //引导页下方的点
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);            //设置圆点大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dp2px(this, 8), DensityUtil.dp2px(this, 8));
            if (i > 0) {
                params.leftMargin = DensityUtil.dp2px(this, 8);
            }
            point.setLayoutParams(params);
            circleViewContainer.addView(point);
        }
    }

    private void initAdapter() {
        ImgAdapter adapter = new ImgAdapter(imglist);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (imglist.size() == 1) {
                    return;
                }
                if (imglist.size() == 2) {
                    switch (position) {
                        case 0:
                            if (currentPage == 0) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(0).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 0;
                            break;
                        case 1:
                            if (currentPage == 1) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(1).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 1;
                            break;
                    }
                    return;
                }
                if (imglist.size() == 3) {
                    switch (position) {
                        case 0:
                            if (currentPage == 0) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(0).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 0;
                            break;
                        case 1:
                            if (currentPage == 1) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(1).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 1;
                            break;
                        case 2:
                            if (currentPage == 2) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(2).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 2;
                            break;
                    }
                }
                if (imglist.size() == 4) {
                    switch (position) {
                        case 0:
                            if (currentPage == 0) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(0).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 0;
                            break;
                        case 1:
                            if (currentPage == 1) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(1).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 1;
                            break;
                        case 2:
                            if (currentPage == 2) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(2).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 2;
                            break;
                        case 3:
                            if (currentPage == 3) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(3).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 3;
                            break;
                    }
                }
                if (imglist.size() == 5) {
                    switch (position) {
                        case 0:
                            if (currentPage == 0) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(0).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 0;
                            break;
                        case 1:
                            if (currentPage == 1) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(1).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 1;
                            break;
                        case 2:
                            if (currentPage == 2) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(2).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 2;
                            break;
                        case 3:
                            if (currentPage == 3) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(3).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 3;
                            break;
                        case 4:
                            if (currentPage == 4) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(4).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 4;
                            break;
                    }
                }
                if (imglist.size() == 4) {
                    switch (position) {
                        case 0:
                            if (currentPage == 0) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(0).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 0;
                            break;
                        case 1:
                            if (currentPage == 1) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(1).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 1;
                            break;
                        case 2:
                            if (currentPage == 2) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(2).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 2;
                            break;
                        case 3:
                            if (currentPage == 3) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(3).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 3;
                            break;
                        case 4:
                            if (currentPage == 4) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(4).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 4;
                            break;
                        case 5:
                            if (currentPage == 5) {
                                return;
                            }
                            circleViewContainer.getChildAt(currentPage).setBackgroundResource(R.drawable.shape_point_gray);
                            circleViewContainer.getChildAt(5).setBackgroundResource(R.drawable.shape_point_gree);
                            currentPage = 5;
                            break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flag = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = true;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        circleViewContainer.getChildAt(0).setBackgroundResource(R.drawable.shape_point_gree);
        contentTx.setText(schoolIntroductions.get(currentPage).getNote());
        if (imglist.size() > 1) {
            threadPlay = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (flag) {
                            try {
                                threadPlay.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mhandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (!flag) {
                                        return;
                                    }
                                    if (imglist.size() == 2) {
                                        switch (currentPage) {
                                            case 0:
                                                viewPager.setCurrentItem(1);
                                                currentPage = 1;
                                                break;
                                            case 1:
                                                viewPager.setCurrentItem(0);
                                                currentPage = 0;
                                                break;
                                        }
                                    }
                                    if (imglist.size() == 3) {
                                        switch (currentPage) {
                                            case 0:
                                                viewPager.setCurrentItem(1);
                                                currentPage = 1;
                                                break;
                                            case 1:
                                                viewPager.setCurrentItem(2);
                                                currentPage = 2;
                                                break;
                                            case 2:
                                                viewPager.setCurrentItem(0);
                                                currentPage = 0;
                                                break;
                                        }
                                    }
                                    if (imglist.size() == 4) {
                                        switch (currentPage) {
                                            case 0:
                                                viewPager.setCurrentItem(1);
                                                currentPage = 1;
                                                break;
                                            case 1:
                                                viewPager.setCurrentItem(2);
                                                currentPage = 2;
                                                break;
                                            case 2:
                                                viewPager.setCurrentItem(2);
                                                currentPage = 2;
                                                break;
                                            case 3:
                                                viewPager.setCurrentItem(0);
                                                currentPage = 0;
                                                break;
                                        }
                                    }
                                    if (imglist.size() == 5) {
                                        switch (currentPage) {
                                            case 0:
                                                viewPager.setCurrentItem(1);
                                                currentPage = 1;
                                                break;
                                            case 1:
                                                viewPager.setCurrentItem(2);
                                                currentPage = 2;
                                                break;
                                            case 2:
                                                viewPager.setCurrentItem(2);
                                                currentPage = 2;
                                                break;
                                            case 3:
                                                viewPager.setCurrentItem(3);
                                                currentPage = 3;
                                                break;
                                            case 4:
                                                viewPager.setCurrentItem(0);
                                                currentPage = 0;
                                                break;
                                        }
                                    }
                                    if (imglist.size() == 6) {
                                        switch (currentPage) {
                                            case 0:
                                                viewPager.setCurrentItem(1);
                                                currentPage = 1;
                                                break;
                                            case 1:
                                                viewPager.setCurrentItem(2);
                                                currentPage = 2;
                                                break;
                                            case 2:
                                                viewPager.setCurrentItem(2);
                                                currentPage = 2;
                                                break;
                                            case 3:
                                                viewPager.setCurrentItem(3);
                                                currentPage = 3;
                                                break;
                                            case 4:
                                                viewPager.setCurrentItem(4);
                                                currentPage = 4;
                                                break;
                                            case 5:
                                                viewPager.setCurrentItem(0);
                                                currentPage = 0;
                                                break;
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });
            threadPlay.start();
        }
    }

    private void findView() {
        imglist = new ArrayList<ImageView>();
        contentTx = (TextView) findViewById(R.id.school_info_content);
        backll = (LinearLayout) findViewById(R.id.school_info_back);
        viewPager = (ViewPager) findViewById(R.id.school_info_viewPager);
        circleViewContainer = (LinearLayout) findViewById(R.id.school_ll_container);
        contentTx = (TextView) findViewById(R.id.school_info_content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.school_info_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        threadPlay = null;
        super.onDestroy();
    }

    class ImgAdapter extends PagerAdapter {
        private List<ImageView> list = new ArrayList<>();

        public ImgAdapter(List<ImageView> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position), 0);//添加页卡
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
