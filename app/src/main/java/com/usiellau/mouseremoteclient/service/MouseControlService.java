package com.usiellau.mouseremoteclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.usiellau.mouseremoteclient.network.Client;

public class MouseControlService extends Service {
    private final String TAG = MouseControlService.class.getSimpleName();

    private MouseControlBinder mouseControlBinder = new MouseControlBinder();

    private Client client;

    private class MouseControlBinder extends Binder{

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mouseControlBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
