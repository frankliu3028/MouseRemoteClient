package com.usiellau.mouseremoteclient.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.Sender;

public class RemoteActivity extends AppCompatActivity {
    private String TAG = "RemoteActivity";
    int xRecord = 0, yRecord = 0;
    private Sender sender;
    long time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        View contentView = findViewById(android.R.id.content);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        time = System.currentTimeMillis();
                        xRecord = (int)motionEvent.getX();
                        yRecord = (int)motionEvent.getY();
                        Log.d(TAG,"action down, x:"+motionEvent.getX()+"y:"+motionEvent.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x1 = (int)motionEvent.getX() - xRecord;
                        int y1 = (int)motionEvent.getY() - yRecord;
                        Log.d(TAG,"action move, x:"+motionEvent.getX()+"y:"+motionEvent.getY());
                        Log.d(TAG,"action move, x1:"+x1+"y1:"+y1);
                        sendMoveCmd(x1, y1);
                        break;
                    case MotionEvent.ACTION_UP:
                        long currTime = System.currentTimeMillis();
                        if(currTime - time < 200)sendClickCmd();
                        int x2 = (int)motionEvent.getX() - xRecord;
                        int y2 = (int)motionEvent.getY() - yRecord;
                        Log.d(TAG,"action up, x:"+motionEvent.getX()+"y:"+motionEvent.getY());
                        Log.d(TAG,"action up, x2:"+x2+"y1:"+y2);
                        break;


                    default:
                        break;
                }
                return true;
            }
        });

        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        int port = intent.getIntExtra("port",10106);
        sender = new Sender(ip, port);
        sender.start();

    }

    public void sendMoveCmd(final int x, final int y){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sender.sendRelativePos(x,y);
            }
        }).start();
    }
    public void sendClickCmd(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sender.sendClick();
            }
        }).start();
    }
}
