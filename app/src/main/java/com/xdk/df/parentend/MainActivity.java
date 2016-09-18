package com.xdk.df.parentend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.ui.login.LoginActivity;
import com.xdk.df.parentend.ui.login.changePass.ChangePassActivity;
import com.xdk.df.parentend.ui.login.contactTeacher.ContactTeacherActivity;
import com.xdk.df.parentend.ui.login.latestGrade.LatestDetectionActivity;
import com.xdk.df.parentend.ui.login.leaveInput.AskLeaveActivity;
import com.xdk.df.parentend.ui.login.more.MoreActivity;
import com.xdk.df.parentend.ui.login.newmessage.NewMessageActivity;
import com.xdk.df.parentend.ui.login.payCheck.PayCheckActivity;
import com.xdk.df.parentend.ui.login.payMoney.PayMoneyActivity;
import com.xdk.df.parentend.ui.login.schedule.ScheduleActivity;
import com.xdk.df.parentend.ui.login.schoolInfo.SchoolInfoActivity;
import com.xdk.df.parentend.ui.login.studentInfo.StudentInfoActivity;
import com.xdk.df.parentend.ui.login.studentsFood.StudentFoodsActivity;
import com.xdk.df.parentend.ui.login.todayCheck.TodayCheckActivity;
import com.xdk.df.parentend.utils.CurrentUserHelper;
import com.xdk.df.parentend.utils.ExitAppHelper;
import com.xdk.df.parentend.utils.ImageDownLoader;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.widget.CircleImageView;

import java.io.ByteArrayOutputStream;

/**
 * 家长端主页面
 */

public class MainActivity extends BaseActivity {
    private ExitAppHelper appExitHelper;
    private ImageView studentPhoto;
    private TextView studentInfo;
    private CircleImageView circleImageView;
    private byte[] studentbm;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            studentbm = (byte[]) msg.obj;
            if(studentbm != null){
                studentPhoto.setImageBitmap(BitmapFactory.decodeByteArray(studentbm, 0, studentbm.length));
            }else{
                toastShort("请采集照片");
            }
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        findView();
        String sql = "SELECT * FROM UserPicture where picid = '" + SharedPreferenceHelper.getCurrentUser(this).getAccountid() + "'and schoolcode = '" + SharedPreferenceHelper.getCurrentUser(this).getSchoolcode() + "'";
        ImageDownLoader loader =new ImageDownLoader(this);
        if(loader.showCacheBitmap(sql) == null){
            HttpHelper.getCurrentUserPhoto(this, handler, studentPhoto);
        }else{
            studentPhoto.setImageBitmap(loader.showCacheBitmap(sql));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            loader.showCacheBitmap(sql).compress(Bitmap.CompressFormat.PNG,100,outputStream);
            studentbm = outputStream.toByteArray();
        }
        studentInfo.setText(getString(R.string.str_main_top_student_info, SharedPreferenceHelper.getCurrentUser(this).getGrade(), SharedPreferenceHelper.getCurrentUser(this).getSclass(), SharedPreferenceHelper.getCurrentUser(this).getName()));
    }

    private void findView() {
        appExitHelper = new ExitAppHelper(this);
        circleImageView = (CircleImageView) findViewById(R.id.main_img_student_photo);
        studentInfo = (TextView) findViewById(R.id.main_tx_student_info);
        studentPhoto = (ImageView) findViewById(R.id.main_img_student_photo);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (appExitHelper.onClickBack(keyCode, event)) return true;
        return super.onKeyDown(keyCode, event);
    }

    public void optionClick(View view) {
        if (!CurrentUserHelper.getUserVip(this)) {
            SharedPreferenceHelper.putCurrentUser(this, null);
            goActivity(LoginActivity.class);
            toastShort(R.string.str_user_no_vip);
            return;
        }
        switch (view.getId()) {
            case R.id.main_today_check:
                goActivity(TodayCheckActivity.class);
                break;
            case R.id.main_new_check:
                goActivity(LatestDetectionActivity.class);
                break;
            case R.id.main_more:
                goActivity(MoreActivity.class);
                break;
            case R.id.main_student_foods:
                goActivity(PayCheckActivity.class);
                break;
            case R.id.main_student_subject:
                goActivity(AskLeaveActivity.class);
                break;
            case R.id.main_new_message:
                goActivity(NewMessageActivity.class);
                break;
            case R.id.main_class_life:
                goActivity(StudentFoodsActivity.class);
                break;
            case R.id.main_contact_teacher:
                goActivity(ContactTeacherActivity.class);
                break;
            case R.id.main_contact_us:
                goActivity(ScheduleActivity.class);
                break;
            case R.id.main_school_introduce:
                goActivity(SchoolInfoActivity.class);
                break;
            case R.id.main_student_info:
            case R.id.main_img_student_photo:
                Intent intent = new Intent(this,StudentInfoActivity.class);
                intent.putExtra("studentimg",studentbm);
                startActivity(intent);
                break;
            case R.id.main_pay_money:
                goActivity(PayMoneyActivity.class);
                break;
        }
    }
}
