package com.usiellau.mouseremoteclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private InetSocketAddress serverAddr;

    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ServerScanner sc = new ServerScanner(MainActivity.this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        serverAddr = sc.scan();
                        if(serverAddr == null)return;
                        Log.d(TAG, "server address is:"+serverAddr.getHostName()+"port:"+serverAddr.getPort());
                        String ip = serverAddr.getHostName();
                        int port = serverAddr.getPort();
                        Intent intent = new Intent(MainActivity.this, RemoteActivity.class);
                        intent.putExtra("ip",ip);
                        intent.putExtra("port",port);
                        startActivity(intent);
                    }
                }).start();


            }
        });
    }
}
