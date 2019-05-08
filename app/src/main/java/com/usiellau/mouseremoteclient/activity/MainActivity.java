package com.usiellau.mouseremoteclient.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.usiellau.mouseremoteclient.R;

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

            }
        });
    }
}
