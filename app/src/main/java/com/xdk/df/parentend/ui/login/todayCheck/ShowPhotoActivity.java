package com.xdk.df.parentend.ui.login.todayCheck;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.BitmapCompressor;
import com.xdk.df.parentend.utils.ImageDownLoader;

public class ShowPhotoActivity extends BaseActivity {
    private ImageView imageView;
    private String currentId;
    private LinearLayout backll;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_show_photo);
        imageView = (ImageView) findViewById(R.id.fragment_todaycheck_item_portrait);
        backll = (LinearLayout) findViewById(R.id.show_photo_back);
        backll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPhotoActivity.this.finish();
            }
        });
        currentId = getIntent().getStringExtra("currentId");
        ImageDownLoader loader = new ImageDownLoader(this);
        if (loader.showCacheBitmap(currentId) != null) {
            imageView.setImageBitmap(loader.showCacheBitmap(currentId));
        } else {
            String sql = "SELECT * FROM UserPicture where picid = '" + currentId + "'";
            HttpHelper.getCheckPortrait(this, handler, sql, new HttpHelper.PortraitCallback() {
                @Override
                public void onResponse(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(BitmapCompressor.comp(bitmap));
                }
            }, currentId);
        }
    }
}
