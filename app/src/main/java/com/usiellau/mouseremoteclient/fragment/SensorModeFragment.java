package com.usiellau.mouseremoteclient.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usiellau.mouseremoteclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SensorModeFragment extends Fragment {
    private final String TAG = SensorModeFragment.class.getSimpleName();

    @BindView(R.id.tv_z)
    TextView tvZ;
    @BindView(R.id.tv_x)
    TextView tvX;
    @BindView(R.id.tv_y)
    TextView tvY;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            tvZ.setText("方位角：" + (float) (Math.round(event.values[0] * 100)) / 100);
            tvX.setText("倾斜角：" + (float) (Math.round(event.values[1] * 100)) / 100);
            tvY.setText("滚动角：" + (float) (Math.round(event.values[2] * 100)) / 100);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_sensor, container, false);
        ButterKnife.bind(this, rootView);
        SensorManager sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
        return rootView;
    }
}
