package com.heyde.starflyer.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.heyde.starflyer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainView) RelativeLayout mMainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        beginGame();
    }

    private void beginGame(){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }
}