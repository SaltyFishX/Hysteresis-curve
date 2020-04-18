package com.example.asus.shiboqi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SurfaceHolder holder;
    private Paint paint;
    final int HEIGHT = 1000;
    final int WIDTH = 900;
    final int X_OFFSET = 5;
    private int cx = X_OFFSET;
    private int i=0;
    private List<Point> points =new ArrayList<>();

    //实际Y轴的位置
    int centerY = HEIGHT / 2;
    Timer timer  = new Timer();
    TimerTask task = null;
    int[] a={9,20,27,30,38,49,57,66,79,93,
            102,
            113,
            124,
            132,
            140,
            149,
            155,
            162,
            173,
            179,
            186,
            191,
            196,
            201,
            206,
            207,
            207,
            203,
            199,
            195,
            190,
            183,
            174,
            163,
            148,
            130,
            108,
            84,
            69,
            59,
            48,
            33,
            7,
            -19,
            -45,
            -70,
            -94,
            -117,
            -139,
            -162,
            -176,
            -193,
            -206,
            -207,
            -206,
            -203,
            -199,
            -195,
            -189,
            -183,
            -174,
            -163,
            -148,
            -129,
            -106,
            -82,
            -67,
            -57,
            -47,
            -31,
            -5,
            21,
            48,
            73,
            97,
            120,
            142,
            163,
            179,
            193,
            205,
            206,
    };
    int[] a1={10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,170,180,190,200,210,220,230,240,250,260,270,280,290,300};
    int[] b1={10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,170,180,190,200,210,220,230,240,250,260,270,280,290,300};

    int[] b={22,
            42,
            51,
            55,
            64,
            76,
            84,
            93,
            105,
            118,
            126,
            137,
            147,
            156,
            164,
            174,
            181,
            189,
            202,
            210,
            220,
            227,
            235,
            244,
            252,
            255,
            251,
            231,
            210,
            189,
            168,
            147,
            126,
            105,
            84,
            63,
            42,
            21,
            9,
            0,
            -9,
            -21,
            -42,
            -63,
            -84,
            -105,
            -126,
            -147,
            -168,
            -190,
            -210,
            -231,
            -252,
            -254,
            -250,
            -231,
            -210,
            -189,
            -167,
            -147,
            -126,
            -106,
            -84,
            -63,
            -42,
            -21,
            -8,
            0,
            8,
            21,
            42,
            63,
            85,
            105,
            127,
            147,
            168,
            191,
            210,
            231,
            252,
            254,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        getSupportActionBar().hide();// 隐藏ActionBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar  即全屏
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int Width=dm.widthPixels;
        final int height=dm.heightPixels;
        final SurfaceView surfaceView = (SurfaceView)findViewById(R.id.show);
        //初始化SurfaceHolder对象
        holder = surfaceView.getHolder();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        Button sin = (Button)findViewById(R.id.sin);
        Button cos = (Button)findViewById(R.id.cos);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                drawkBack(holder);
                i=0;
                if (task != null)
                {
                    task.cancel();
                }
                final Canvas[] canvas = {new Canvas()};

                task = new TimerTask() {
                    @Override
                    public void run() {
                        int width =Width/2;
                        int height1=height/2;
                        canvas[0] = holder.lockCanvas(new Rect(0,0,Width,height));
                        canvas[0].drawPoint(width+b[i], height1-a[i], paint);
                        Point point =new Point(width+b[i],height1-a[i]);
                        points.add(point);

                        i++;
                        if (i > 80)
                        {
                            DrawUtil.drawCurvesFromPoints(canvas[0],points,0.6,Color.WHITE,2f);
                            task.cancel();
                            task = null;
                        }
                        holder.unlockCanvasAndPost(canvas[0]);
                    }
                };
                timer.schedule(task, 0, 30);
            }
        };

        sin.setOnClickListener(listener);
        cos.setOnClickListener(listener);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                drawkBack(holder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                timer.cancel();
            }
        });


    }

    private void drawkBack(SurfaceHolder holder)
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int Width=dm.widthPixels;
        int height=dm.heightPixels;
        Canvas canvas = holder.lockCanvas();
        //绘制白色背景
        canvas.drawColor(Color.BLACK);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(2);
        Paint b=new Paint();
        b.setTextSize(20);
        b.setColor(Color.WHITE);
        Paint a =new Paint();
        a.setTextSize(20);
        a.setTextAlign(Paint.Align.CENTER);
        a.setColor(Color.WHITE);
        //绘制坐标
        canvas.drawLine(0, height/2,Width, height/2, p);
        canvas.drawLine(Width/2, 0,  Width/2, height, p);
        canvas.drawLine(Width/2-50,height/2,Width/2-50,height/2-10,b);
        canvas.drawLine(Width/2-100,height/2,Width/2-100,height/2-10,b);
        canvas.drawLine(Width/2-150,height/2,Width/2-150,height/2-10,b);
        canvas.drawLine(Width/2-200,height/2,Width/2-200,height/2-10,b);
        canvas.drawLine(Width/2-250,height/2,Width/2-250,height/2-10,b);
        canvas.drawLine(Width/2-300,height/2,Width/2-300,height/2-10,b);
        canvas.drawLine(Width/2-350,height/2,Width/2-350,height/2-10,b);
        canvas.drawLine(Width/2-400,height/2,Width/2-400,height/2-10,b);
        canvas.drawLine(Width/2-450,height/2,Width/2-450,height/2-10,b);
        canvas.drawText("-50",Width/2-50,height/2+20,a);
        canvas.drawText("-100",Width/2-100,height/2+20,a);
        canvas.drawText("-150",Width/2-150,height/2+20,a);
        canvas.drawText("-200",Width/2-200,height/2+20,a);
        canvas.drawText("-250",Width/2-250,height/2+20,a);
        canvas.drawText("-300",Width/2-300,height/2+20,a);
        canvas.drawText("-350",Width/2-350,height/2+20,a);
        canvas.drawText("-400",Width/2-400,height/2+20,a);
        canvas.drawText("-450",Width/2-450,height/2+20,a);
        canvas.drawLine(Width/2, 0,  Width/2, height, p);
        canvas.drawLine(Width/2+50,height/2,Width/2+50,height/2-10,b);
        canvas.drawLine(Width/2+100,height/2,Width/2+100,height/2-10,b);
        canvas.drawLine(Width/2+150,height/2,Width/2+150,height/2-10,b);
        canvas.drawLine(Width/2+200,height/2,Width/2+200,height/2-10,b);
        canvas.drawLine(Width/2+250,height/2,Width/2+250,height/2-10,b);
        canvas.drawLine(Width/2+300,height/2,Width/2+300,height/2-10,b);
        canvas.drawLine(Width/2+350,height/2,Width/2+350,height/2-10,b);
        canvas.drawLine(Width/2+400,height/2,Width/2+400,height/2-10,b);
        canvas.drawLine(Width/2+450,height/2,Width/2+450,height/2-10,b);
        canvas.drawText("50",Width/2+50,height/2+20,a);
        canvas.drawText("100",Width/2+100,height/2+20,a);
        canvas.drawText("150",Width/2+150,height/2+20,a);
        canvas.drawText("200",Width/2+200,height/2+20,a);
        canvas.drawText("250",Width/2+250,height/2+20,a);
        canvas.drawText("300",Width/2+300,height/2+20,a);
        canvas.drawText("350",Width/2+350,height/2+20,a);
        canvas.drawText("400",Width/2+400,height/2+20,a);
        canvas.drawText("450",Width/2+450,height/2+20,a);
        canvas.drawLine(Width/2+10,height/2-50,Width/2,height/2-50,b);
        canvas.drawLine(Width/2+10,height/2-100,Width/2,height/2-100,b);
        canvas.drawLine(Width/2+10,height/2-150,Width/2,height/2-150,b);
        canvas.drawLine(Width/2+10,height/2-200,Width/2,height/2-200,b);
        canvas.drawLine(Width/2+10,height/2-250,Width/2,height/2-250,b);
        canvas.drawLine(Width/2+10,height/2-300,Width/2,height/2-300,b);
        canvas.drawLine(Width/2+10,height/2-350,Width/2,height/2-350,b);
        canvas.drawLine(Width/2+10,height/2-400,Width/2,height/2-400,b);
        canvas.drawLine(Width/2+10,height/2-450,Width/2,height/2-450,b);
        canvas.drawText("50",Width/2-20,height/2-50,a);
        canvas.drawText("100",Width/2-20,height/2-100,a);
        canvas.drawText("150",Width/2-20,height/2-150,a);
        canvas.drawText("200",Width/2-20,height/2-200,a);
        canvas.drawText("250",Width/2-20,height/2-250,a);
        canvas.drawLine(Width/2+10,height/2+50,Width/2,height/2+50,a);
        canvas.drawLine(Width/2+10,height/2+100,Width/2,height/2+100,a);
        canvas.drawLine(Width/2+10,height/2+150,Width/2,height/2+150,a);
        canvas.drawLine(Width/2+10,height/2+200,Width/2,height/2+200,a);
        canvas.drawLine(Width/2+10,height/2+250,Width/2,height/2+250,a);
        canvas.drawText("-50",Width/2-20,height/2+50,a);
        canvas.drawText("-100",Width/2-20,height/2+100,a);
        canvas.drawText("-150",Width/2-20,height/2+150,a);
        canvas.drawText("-200",Width/2-20,height/2+200,a);
        canvas.drawText("-250",Width/2-20,height/2+250,a);
        canvas.drawText("x",Width-15,height/2,a);
        canvas.drawText("y",Width/2+5,15,b);
        holder.unlockCanvasAndPost(canvas);
        holder.lockCanvas(new Rect(0,0,0,0));
        holder.unlockCanvasAndPost(canvas);
    }
}

