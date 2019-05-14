package com.usiellau.mouseremoteclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.service.MouseControlService;

import java.net.InetSocketAddress;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @BindView(R.id.et_server_ip)
    EditText etServerIp;
    @BindView(R.id.et_server_port)
    EditText etServerPort;
    @BindView(R.id.btn_connect)
    Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_connect)
    void onClickBtnConnect(){
        Intent intent = new Intent(this, MouseControlService.class);
        String serverIp = etServerIp.getText().toString();
        int serverPort = Integer.valueOf(etServerPort.getText().toString());
        intent.putExtra("serverIp", serverIp);
        intent.putExtra("serverPort", serverPort);
        startService(intent);
        Intent intent1 = new Intent(this, ControlActivity.class);
        startActivity(intent1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MouseControlService.class);
        stopService(intent);
    }
}
