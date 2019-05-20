package com.usiellau.mouseremoteclient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.adapter.DeviceAdapter;
import com.usiellau.mouseremoteclient.adapter.OnItemClickListener;
import com.usiellau.mouseremoteclient.entity.DeviceInfo;
import com.usiellau.mouseremoteclient.sd.SDClient;
import com.usiellau.mouseremoteclient.sd.SDClientCallback;
import com.usiellau.mouseremoteclient.service.MouseControlService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @BindView(R.id.rv_device)
    RecyclerView rvDevice;

    private DeviceAdapter deviceAdapter;

    private ProgressDialog progressDialog;

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MainActivity.this, MouseControlService.class);
            String serverIp = deviceAdapter.getData().get(position).getIp();
            int serverPort = deviceAdapter.getData().get(position).getPort();
            intent.putExtra("serverIp", serverIp);
            intent.putExtra("serverPort", serverPort);
            startService(intent);
            Intent intent1 = new Intent(MainActivity.this, ControlActivity.class);
            startActivity(intent1);
        }

        @Override
        public void onItemLongClick(int position) {

        }
    };

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rvDevice.setLayoutManager(new LinearLayoutManager(this));
        if(deviceAdapter == null){
            deviceAdapter = new DeviceAdapter(null);
        }
        rvDevice.setAdapter(deviceAdapter);
        rvDevice.setItemAnimator(new DefaultItemAnimator());
        rvDevice.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        deviceAdapter.setOnItemClickListener(onItemClickListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                SDClient sdClient = new SDClient(new SDClientCallback() {
                    @Override
                    public void discoverDevices(ArrayList<DeviceInfo> deviceInfoList) {
                        closeProgressDialog();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                deviceAdapter.updateData(deviceInfoList);
                            }
                        });
                    }
                });
                showProgressDialog();
                sdClient.start();
                break;
                default:
                    break;
        }
        return true;
    }

    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("please wait...");
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        progressDialog.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MouseControlService.class);
        stopService(intent);
    }

}
