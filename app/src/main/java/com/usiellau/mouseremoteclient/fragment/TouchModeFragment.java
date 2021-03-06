package com.usiellau.mouseremoteclient.fragment;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.protocol.Parser;
import com.usiellau.mouseremoteclient.service.MouseControlService;
import com.usiellau.mouseremoteclient.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TouchModeFragment extends Fragment {
    private final String TAG = TouchModeFragment.class.getSimpleName();

    @BindView(R.id.touch_area)
    LinearLayout touchArea;

    int xStart = 0, yStart = 0, xRecord = 0, yRecord = 0, xMove = 0, yMove = 0;
    long time;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    time = System.currentTimeMillis();
                    xStart = (int)event.getX();
                    yStart = (int)event.getY();
                    xRecord = xStart;
                    yRecord = yStart;
                    break;
                case MotionEvent.ACTION_MOVE:
                    xMove = (int)event.getX() - xRecord;
                    yMove = (int)event.getY() - yRecord;
                    mouseControlBinder.mouseMoveRelativeTo(xMove, yMove);
                    xRecord = (int)event.getX();
                    yRecord = (int)event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    long currTime = System.currentTimeMillis();
                    if(currTime - time < 200) {
                        mouseControlBinder.mouseClick(Constant.BUTTON_LEFT);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private MouseControlService.MouseControlBinder mouseControlBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mouseControlBinder = (MouseControlService.MouseControlBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), MouseControlService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_touch, container, false);
        ButterKnife.bind(this,rootView);
        touchArea.setOnTouchListener(onTouchListener);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(serviceConnection);
    }
}
