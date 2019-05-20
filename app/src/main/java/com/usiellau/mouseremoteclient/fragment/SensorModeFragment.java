package com.usiellau.mouseremoteclient.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.entity.Coordinate;
import com.usiellau.mouseremoteclient.entity.ScreenSize;
import com.usiellau.mouseremoteclient.service.MouseControlService;
import com.usiellau.mouseremoteclient.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class SensorModeFragment extends Fragment {
    private final String TAG = SensorModeFragment.class.getSimpleName();

    @BindView(R.id.btn_calibration)
    Button btnCalibration;
    @BindView(R.id.tv_z)
    TextView tvZ;
    @BindView(R.id.tv_x)
    TextView tvX;
    @BindView(R.id.tv_y)
    TextView tvY;
    @BindView(R.id.tv_calibration_z)
    TextView tvCalibrationZ;
    @BindView(R.id.tv_calibration_x)
    TextView tvCalibrationX;
    @BindView(R.id.switch_xz)
    SwitchCompat switchXZ;
    @BindView(R.id.tv_calibration_y)
    TextView tvCalibrationY;
    @BindView(R.id.btn_left_click)
    Button btnLeftClick;
    @BindView(R.id.btn_right_click)
    Button btnRightClick;

    private ScreenSize screenSize;

    private boolean isCalibration = false;

    private final int maxZOffset = 30;
    private final int maxXOffset = 30;
    private final int maxYOffset = 30;

    private int currentZAngle = 0;
    private int currentXAngle = 0;
    private int currentYAngle = 0;
    private int calibrationZ = 0;
    private int calibrationX = 0;
    private int calibrationY = 0;
    private int calibrationDistance = 0;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            currentZAngle = (Math.round(event.values[0] * 100)) / 100;
            currentXAngle = (Math.round(event.values[1] * 100)) / 100;
            currentYAngle = (Math.round(event.values[2] * 100)) / 100;
            tvZ.setText("方位角：" + currentZAngle);
            tvX.setText("倾斜角：" + currentXAngle);
            tvY.setText("滚动角：" + currentYAngle);
            if(isCalibration){
                Coordinate c = calculateCoordinate();
                mouseControlBinder.mouseMoveTo(c.getX(), c.getY());
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private MouseControlService.MouseControlBinder mouseControlBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mouseControlBinder = (MouseControlService.MouseControlBinder)service;
            screenSize = mouseControlBinder.getScreenSize();

            SensorManager sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
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
        View rootView = inflater.inflate(R.layout.fragment_mode_sensor, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnCheckedChanged(R.id.switch_xz)
    void onCheckChangedSwitchXY(CompoundButton buttonView, boolean isChecked){
        isCalibration = false;
        Toast.makeText(getActivity(), "请重新校准", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_calibration)
    void onClickBtnCalibration(){
        calibrationZ = currentZAngle;
        calibrationX = currentXAngle;
        calibrationY = currentYAngle;
        calibrationDistance = 3;
        isCalibration = true;
        tvCalibrationZ.setText(String.valueOf(calibrationZ));
        tvCalibrationX.setText(String.valueOf(calibrationX));
        tvCalibrationY.setText(String.valueOf(calibrationY));
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
    public void onDestroyView() {
        super.onDestroyView();
        isCalibration = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(serviceConnection);
    }

    private Coordinate calculateCoordinate(){
        int x = calculateCoordinateX();
        int y = calculateCoordinateY();
        return new Coordinate(x, y);
    }

    private int calculateCoordinateX(){
        int x;
        if(!switchXZ.isChecked()){
            int currYOffset = -(currentYAngle - calibrationY);
            x = screenSize.getWidth() / (maxYOffset * 2) * currYOffset + screenSize.getWidth() / 2;
        }else{
            int curZ = currentZAngle;
            int currZOffset = curZ - calibrationZ;
            if(curZ > 270 && calibrationZ < 90){
                currZOffset = -(360 - currZOffset);
            }
            if(curZ < 90 && calibrationZ > 270){
                currZOffset = 360 + currZOffset;
            }

            x = screenSize.getWidth() / (maxZOffset * 2) * currZOffset + screenSize.getWidth() / 2;
            if(x < 0) x = 0;
            if(x > screenSize.getWidth()) x = screenSize.getWidth();
        }
        return x;
    }

    private int calculateCoordinateY(){
        int currXOffset = currentXAngle - calibrationX;
        int y = screenSize.getHeight() / (maxXOffset * 2) * currXOffset + screenSize.getHeight() / 2;
        return y;
    }
}
