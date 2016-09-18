package com.xdk.df.parentend.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.xdk.df.parentend.R;

/**
 * 圆弧计分、各种率
 *
 */
public class ReportArc extends View {

    private Paint paint_black, paint_white, paint_big_text, paint_unit_text, paint_title_text;
    private RectF rectf;
    private float tb;
    private int blackColor = Color.parseColor("#dbdbdb"); // 底黑色
    private int textBlackColor = Color.parseColor("#8b8b8b"); // 底黑色
    private int mainColor = Color.parseColor("#3bc15a");
    private int score, mcolor;
    private String unit, title;
    private float arc_y = 0f;
    private int score_text;

    //score是百分数，数字、mcolor是外圈进度条以及数字的颜色，unit是百分号，title是数字下面的文字
    public ReportArc(Context context, int score, int mcolor, String unit, String title) {
        super(context);
        init(score,mcolor,unit,title);
    }

    public void init(int score, int mcolor, String unit, String title) {
        this.score = score;
        this.mcolor = mcolor;
        this.unit = unit;
        this.title = title;

        if (mcolor == 1){
            mainColor = Color.parseColor("#3bc15a");
        }else if (mcolor == 2){
            mainColor = Color.parseColor("#fc630c");
        }if (mcolor == 3){
            mainColor = Color.parseColor("#fe4263");
        }

        Resources res = getResources();
        tb = res.getDimension(R.dimen.margin_10);

        //圆圈基本颜色
        paint_black = new Paint();
        paint_black.setAntiAlias(true);
        paint_black.setColor(blackColor);
        paint_black.setStrokeWidth(tb * 0.2f);
        paint_black.setStyle(Paint.Style.STROKE);

        // 大数字 90
        paint_big_text = new Paint();
        paint_big_text.setAntiAlias(true);
        paint_big_text.setColor(mainColor);
        paint_big_text.setTextSize(tb * 3.2f);
        paint_big_text.setTextAlign(Paint.Align.CENTER);
        paint_big_text.setStyle(Paint.Style.FILL);

        // "%"
        paint_unit_text = new Paint();
        paint_unit_text.setAntiAlias(true);
        paint_unit_text.setColor(mainColor);
        paint_unit_text.setTextSize(tb * 1.2f);
        paint_unit_text.setTextAlign(Paint.Align.CENTER);
        paint_unit_text.setStyle(Paint.Style.FILL);

        //成交率、试衣率等字体
        paint_title_text = new Paint();
        paint_title_text.setAntiAlias(true);
        paint_title_text.setColor(textBlackColor);
        paint_title_text.setTextSize(tb * 1.4f);
        paint_title_text.setTextAlign(Paint.Align.CENTER);
        paint_title_text.setStyle(Paint.Style.FILL_AND_STROKE);

        //圆圈带颜色
        paint_white = new Paint();
        paint_white.setAntiAlias(true);
        paint_white.setColor(mainColor);
        paint_white.setTextSize(tb * 4.0f);
        paint_white.setStrokeWidth(tb * 0.2f);
        paint_white.setTextAlign(Paint.Align.CENTER);
        paint_white.setStyle(Paint.Style.STROKE);

        //外圈圆的位置、直径70dp、距离顶部、左边各5dp
        rectf = new RectF();
        rectf.set(tb*0.5f, tb*0.5f, tb * 7.5f, tb * 7.5f);

        setLayoutParams(new ViewGroup.LayoutParams((int) (tb * 10.0f), (int) (tb * 10.0f)));

        this.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        new thread();
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
    }

    protected void onDraw(Canvas c) {
        super.onDraw(c);

        //画最外圈灰色的圆
        c.drawArc(rectf, -90, 360, false, paint_black);
        //画最外圈，带颜色的进度条圆
        c.drawArc(rectf, -90, arc_y, false, paint_white);
        //画百分比数字
        c.drawText("" + score_text, tb * 4.0f, tb * 4.5f, paint_big_text);
        //画百分号
        c.drawText(unit, tb * 6.0f, tb * 3.0f, paint_unit_text);
        //画下面的文字，成交率等等
        c.drawText(title, tb * 4.0f, tb * 6.2f, paint_title_text);
        //画进度条圆末端的小圆点
        c.drawCircle((float) (4.0f * tb + 3.5f * tb * Math.sin(3.6 * score * Math.PI / 180)),
                (float) (4.0f * tb - 3.5f * tb * Math.cos(3.6 * score * Math.PI / 180)), 10, paint_big_text);
    }

    class thread implements Runnable {
        private Thread thread;
        private int statek;
        int count;

        public thread() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (true) {
                switch (statek) {
                    case 0:
                        try {
                            Thread.sleep(200);
                            statek = 1;
                        } catch (InterruptedException e) {
                        }
                        break;
                    case 1:
                        try {
                            Thread.sleep(15);
                            arc_y += 3.6f;
                            score_text++;
                            count++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (count >= score)
                    break;
            }
        }
    }

}