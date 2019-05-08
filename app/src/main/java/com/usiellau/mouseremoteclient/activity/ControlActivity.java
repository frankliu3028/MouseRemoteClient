package com.usiellau.mouseremoteclient.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.usiellau.mouseremoteclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlActivity extends AppCompatActivity {
    private final String TAG = ControlActivity.class.getSimpleName();

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_up)
    void onClickBtnUp(){

    }

    @OnClick(R.id.btn_left)
    void onClickBtnLeft(){

    }

    @OnClick(R.id.btn_right)
    void onClickBtnRight(){

    }

    @OnClick(R.id.btn_down)
    void onClickBtnDown(){

    }

    @OnClick(R.id.btn_left_click)
    void onClickBtnLeftClick(){

    }

    @OnClick(R.id.btn_right_click)
    void onClickBtnRightClick(){

    }
}
