package com.usiellau.mouseremoteclient.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.service.MouseControlService;
import com.usiellau.mouseremoteclient.utils.Constant;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class ClickModeFragment extends Fragment {
    private final String TAG = ClickModeFragment.class.getSimpleName();

    @BindView(R.id.btn_up)
    Button btnUp;
    @BindView(R.id.btn_left)
    Button btnLeft;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.btn_down)
    Button btnDown;
    @BindView(R.id.btn_left_click)
    Button btnLeftClick;
    @BindView(R.id.btn_right_click)
    Button btnRightClick;

    private Timer timer = new Timer();

    private MouseControlService.MouseControlBinder mouseControlBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mouseControlBinder = (MouseControlService.MouseControlBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private final int period = 30;
    private final int unitDistance = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), MouseControlService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_click, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private TimerTask timerTaskUp, timerTaskDown, timerTaskLeft, timerTaskRight;

    @OnTouch(R.id.btn_up)
    boolean onTouchBtnUp(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                timerTaskUp = new TimerTask() {
                    @Override
                    public void run() {
                        mouseControlBinder.mouseMoveRelativeTo(0, -unitDistance);
                    }
                };
                timer.schedule(timerTaskUp, 0, period);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                timerTaskUp.cancel();
                timer.purge();
                break;
                default:
                    break;
        }
        return true;
    }

    @OnTouch(R.id.btn_down)
    boolean onTouchBtnDown(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                timerTaskDown = new TimerTask() {
                    @Override
                    public void run() {
                        mouseControlBinder.mouseMoveRelativeTo(0, unitDistance);
                    }
                };
                timer.schedule(timerTaskDown, 0, period);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                timerTaskDown.cancel();
                timer.purge();
                break;
            default:
                break;
        }
        return true;
    }

    @OnTouch(R.id.btn_left)
    boolean onTouchBtnLeft(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                timerTaskLeft = new TimerTask() {
                    @Override
                    public void run() {
                        mouseControlBinder.mouseMoveRelativeTo(-unitDistance, 0);
                    }
                };
                timer.schedule(timerTaskLeft, 0, period);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                timerTaskLeft.cancel();
                timer.purge();
                break;
            default:
                break;
        }
        return true;
    }

    @OnTouch(R.id.btn_right)
    boolean onTouchBtnRight(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                timerTaskRight = new TimerTask() {
                    @Override
                    public void run() {
                        mouseControlBinder.mouseMoveRelativeTo(unitDistance, 0);
                    }
                };
                timer.schedule(timerTaskRight, 0, period);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                timerTaskRight.cancel();
                timer.purge();
                break;
            default:
                break;
        }
        return true;
    }

    @OnTouch(R.id.btn_left_click)
    boolean onTouchBtnLeftClick(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mouseControlBinder.mousePressDown(Constant.BUTTON_LEFT);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                mouseControlBinder.mousePressUp(Constant.BUTTON_LEFT);
                break;
            default:
                break;
        }
        return true;
    }

    @OnTouch(R.id.btn_right_click)
    boolean onTouchBtnRightClick(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mouseControlBinder.mousePressDown(Constant.BUTTON_RIGHT);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                mouseControlBinder.mousePressUp(Constant.BUTTON_RIGHT);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(serviceConnection);
    }

}
