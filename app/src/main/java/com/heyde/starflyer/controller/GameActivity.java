package com.heyde.starflyer.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.heyde.starflyer.model.LargeObstacle;
import com.heyde.starflyer.model.MapGen;
import com.heyde.starflyer.model.Spaceship;


public class GameActivity extends AppCompatActivity {

    GameView mGameView = null;
    MapGen mMapGen = null;
    Spaceship mSpaceship;
    LargeObstacle mLargeObstacle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpaceship = new Spaceship(this);
        mLargeObstacle = new LargeObstacle(this);
        mMapGen = new MapGen(this);
        mGameView = new GameView(this, mSpaceship, mMapGen);

        Log.i("STATUS", "GameActivity started");
        setContentView(mGameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapGen.resume();
        mGameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapGen.pause();
        mGameView.pause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float yAdjust = (mSpaceship.getBitmap().getHeight());// FIXME still strange. Use an emulator for better accuracy
        float yCoord = event.getRawY();

        float adjustedY = (yCoord-yAdjust);

        int finalY = Math.round(adjustedY);

        mSpaceship.setYPos(finalY);
        mSpaceship.updateHitbox();

        return true;
    }


}
