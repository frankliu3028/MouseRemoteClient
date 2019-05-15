package com.usiellau.mouseremoteclient.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.fragment.ClickModeFragment;
import com.usiellau.mouseremoteclient.fragment.SensorModeFragment;
import com.usiellau.mouseremoteclient.fragment.TouchModeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControlActivity extends AppCompatActivity {
    private final String TAG = ControlActivity.class.getSimpleName();

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private ClickModeFragment clickModeFragment;
    private TouchModeFragment touchModeFragment;
    private SensorModeFragment sensorModeFragment;

    private Fragment currentSelectFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ButterKnife.bind(this);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_mode_click:
                if(currentSelectFragment instanceof ClickModeFragment){
                    break;
                }
                if(clickModeFragment == null){
                    clickModeFragment = new ClickModeFragment();
                }
                replaceFragment(clickModeFragment);
                getSupportActionBar().setTitle("ClickMode");
                currentSelectFragment = clickModeFragment;
                break;
            case R.id.action_mode_touch:
                if(currentSelectFragment instanceof TouchModeFragment){
                    break;
                }
                if(touchModeFragment == null){
                    touchModeFragment = new TouchModeFragment();
                }
                replaceFragment(touchModeFragment);
                getSupportActionBar().setTitle("TouchMode");
                currentSelectFragment = touchModeFragment;

                break;
            case R.id.action_mode_sensor:
                if(currentSelectFragment instanceof SensorModeFragment){
                    break;
                }
                if(sensorModeFragment == null){
                    sensorModeFragment = new SensorModeFragment();
                }
                replaceFragment(sensorModeFragment);
                getSupportActionBar().setTitle("SensorMode");
                currentSelectFragment = sensorModeFragment;

                break;
                default:
                    break;
        }
        return true;
    }
}
