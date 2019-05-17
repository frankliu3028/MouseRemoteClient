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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.entity.Coordinate;
import com.usiellau.mouseremoteclient.entity.ScreenSize;
import com.usiellau.mouseremoteclient.service.MouseControlService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private ScreenSize screenSize;

    private boolean isCalibration = false;

    private final int maxZOffset = 45;
    private final int maxXOffset = 45;

    private int currentZAngle = 0;
    private int currentXAngle = 0;
    private int calibrationZ = 0;
    private int calibrationX = 0;
    private int calibrationDistance = 0;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            currentZAngle = (Math.round(event.values[0] * 100)) / 100;
            currentXAngle = (Math.round(event.values[1] * 100)) / 100;
            tvZ.setText("方位角：" + currentZAngle);
            tvX.setText("倾斜角：" + currentXAngle);
            //tvY.setText("滚动角：" + (float) (Math.round(event.values[2] * 100)) / 100);
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

    @OnClick(R.id.btn_calibration)
    void onClickBtnCalibration(){
        calibrationZ = currentZAngle;
        calibrationX = currentXAngle;
        calibrationDistance = 3;
    }

    private Coordinate calculateCoordinate(){
        int x = calculateCoordinateX();
        int y = calculateCoordinateY();
        return new Coordinate(x, y);
    }

    private int calculateCoordinateX(){
        int curZ = currentZAngle;
        if(curZ > calibrationZ){
            curZ -= 360;
        }
        int currZOffset = curZ - calibrationZ;

        int x = screenSize.getWidth() / (maxZOffset * 2) * currZOffset + screenSize.getWidth() / 2;
        if(x < 0) x = 0;
        if(x > screenSize.getWidth()) x = screenSize.getWidth();
        return x;
    }

    private int calculateCoordinateY(){
        int currXOffset = currentXAngle - calibrationX;
        int y = screenSize.getHeight() / (maxXOffset * 2) * currXOffset + screenSize.getHeight() / 2;
        return y;
    }
}
