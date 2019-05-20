package com.usiellau.mouseremoteclient.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.usiellau.mouseremoteclient.entity.ScreenSize;
import com.usiellau.mouseremoteclient.network.Client;
import com.usiellau.mouseremoteclient.network.ClientCallback;
import com.usiellau.mouseremoteclient.protocol.BasicProtocol;

public class MouseControlService extends Service {
    private final String TAG = MouseControlService.class.getSimpleName();

    private MouseControlBinder mouseControlBinder = new MouseControlBinder();

    private Client client;

    private String serverIp;
    private int serverPort;

    private ClientCallback clientCallback = new ClientCallback() {
        @Override
        public void receivePacket(BasicProtocol basicProtocol) {

        }

        @Override
        public void connectSuccess() {

        }

        @Override
        public void connectFailure() {

        }
    };

    public class MouseControlBinder extends Binder{
        public void mouseMoveTo(int x, int y){
            client.mouseMoveTo(x, y);
        }

        public void mouseMoveRelativeTo(int x, int y){
            client.mouseMoveRelativeTo(x, y);
        }

        public void mousePressDown(int button){
            client.mousePressDown(button);
        }

        public void mousePressUp(int button){
            client.mousePressUp(button);
        }

        public void mouseClick(int button){
            client.mouseClick(button);
        }

        public ScreenSize getScreenSize(){
            return client.getScreenSize();
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mouseControlBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                client = new Client(serverIp, serverPort, clientCallback);
                client.start();
            }
        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            serverIp = intent.getStringExtra("serverIp");
            serverPort = intent.getIntExtra("serverPort", -1);
        }
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(client != null){
            client.close();
        }
    }
}
