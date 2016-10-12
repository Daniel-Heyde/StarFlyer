package com.heyde.starflyer.model;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.heyde.starflyer.controller.GameView;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Daniel on 9/9/2016.
 */
public class MapGen implements Runnable {

    private LargeObstacle mLargeObstacle;
    private SmallObstacle mSmallObstacle;
    private List<SmallObstacle> mSmallObstacleList;
    private List<LargeObstacle> mLargeObstacleList;
    Context mContext;
    GameView mGameView;
    Thread GenThread = null;
    volatile Boolean generating;
    Boolean hasSpace;
    int x = 3000;

    public MapGen(Context context) {
        mContext = context;
        mLargeObstacleList = new ArrayList<>();
        mSmallObstacleList = new ArrayList<>();
        hasSpace = true;
    }

    @Override
    public void run() { //FIXME use this thread for continuous spawning.
        while (generating){
            spawnSmallObstacle();
            spawnLargeObstacle();
//            if(mLargeObstacleList.size()>10){
//
//            }
        }
    }

    private void controlGenSpeed() {
        try {
            GenThread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void resume() {
        generating = true;
        GenThread = new Thread(this);
        GenThread.start();
    }

    public void pause() { // check developer.android.com "Pausing and Resuming an Activity"
        generating = false;
        try {
            GenThread.join();
        } catch (InterruptedException e) {

        }
    }

    public SmallObstacle getSmallObstacle() {
        SmallObstacle obstacle = mSmallObstacleList.get(0);
        mSmallObstacleList.remove(0);
        return obstacle;
    }

    public LargeObstacle getLargeObstacle() {
        LargeObstacle obstacle = mLargeObstacleList.get(0);
        mLargeObstacleList.remove(0);
        return obstacle;
    }


    public void spawnSmallObstacle() {
        if (mSmallObstacleList.size() < 10) {

            Random random = new Random();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            int heightCap = displayMetrics.heightPixels;// FIXME fix this

            SmallObstacle smallObstacle = new SmallObstacle(mContext);
            int heightRandom = random.nextInt(1080);// placeholder value
            smallObstacle.setXPos(x);
            smallObstacle.setYPos(heightRandom);
            mSmallObstacleList.add(smallObstacle);
            Log.i("STATUS", "Adding smallObstacle");
        }
    }


    public void spawnLargeObstacle() {
        if (mLargeObstacleList.size() < 10) {

            Random random = new Random();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            int heightCap = displayMetrics.heightPixels;// FIXME fix this

            LargeObstacle largeObstacle = new LargeObstacle(mContext);
            int heightRandom = random.nextInt(1080);// placeholder value
            largeObstacle.setXPos(x);
            largeObstacle.setYPos(heightRandom);
            mLargeObstacleList.add(largeObstacle);
            Log.i("STATUS", "Adding largeObstacle");
        }
    }


    //TODO scale obstacles


}
