package com.usiellau.mouseremoteclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usiellau.mouseremoteclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode_click, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
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
