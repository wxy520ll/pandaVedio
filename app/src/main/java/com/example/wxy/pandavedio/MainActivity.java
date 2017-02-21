package com.example.wxy.pandavedio;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wxy.pandavedio.view.FullScreenVideoView;

import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

public class MainActivity extends AppCompatActivity {
    float mRawX=0;
    float mRawY=0;
    float x=0;
    float y=0;
    public Button button;
    WindowManager.LayoutParams wmParams;
    WindowManager windowManager;
    FullScreenVideoView fullScreenVideoView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,VideoActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            String url=data.getExtras().getString("url");
            showVideo(url);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showVideo(String url){
        final View view=View.inflate(getApplication(),R.layout.windowitem,null);
        windowManager=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.x=220;
        wmParams.y=220;
        wmParams.width = 600;
        wmParams.height = 300;
        wmParams.gravity= Gravity.LEFT|Gravity.TOP;
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 设置window type
        wmParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wmParams.format = PixelFormat.TRANSLUCENT;
        fullScreenVideoView= (FullScreenVideoView) view.findViewById(R.id.video);
        imageView= (ImageView) view.findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeViewImmediate(view);
            }
        });
        fullScreenVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                         mRawX = event.getRawX();
                         mRawY = event.getRawY();
                        x=wmParams.x;
                        y=wmParams.y;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x1=event.getRawX()-mRawX;
                        float y1=event.getRawY()-mRawY;
                        wmParams.x= (int) (x+x1);
                        wmParams.y= (int) (y+y1);
                        windowManager.updateViewLayout(view,wmParams);
                       // Log.d("4577155","x轴差 "+String.valueOf(x1)+"    "+"y轴差 "+String.valueOf(y1)+"  "+"wmParams.x ："+String.valueOf(wmParams.x));
                        break;
                    case MotionEvent.ACTION_UP:
                        wmParams.flags=FLAG_NOT_FOCUSABLE;
                        windowManager.updateViewLayout(view,wmParams);
                        break;
                }
                return true;
            }
        });
        fullScreenVideoView.setVideoURI(Uri.parse(url));
        fullScreenVideoView.start();
        windowManager.addView(view, wmParams);
    }
}
