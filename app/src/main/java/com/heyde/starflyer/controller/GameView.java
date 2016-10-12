package com.heyde.starflyer.controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.heyde.starflyer.R;
import com.heyde.starflyer.model.MapGen;
import com.heyde.starflyer.model.LargeObstacle;
import com.heyde.starflyer.model.SmallObstacle;
import com.heyde.starflyer.model.Spaceship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel on 8/31/2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Canvas mCanvas;
    private Paint mPaint;
    private Spaceship mSpaceship;
    Thread GameThread = null;
    private SurfaceHolder mHolder;
    private MapGen mMapGen;
    private LargeObstacle mLargeObstacle;
    private SmallObstacle mSmallObstacle;
    volatile boolean playing; // volatile = readable from other threads
    private List<LargeObstacle> mLargeOnscreenObstacle;
    private List<SmallObstacle> mSmallOnscreenObstacle;
    private Random random;
    private Context mContext;


    public GameView(Context context, Spaceship spaceship, MapGen mapGen) {
        super(context);
        mLargeOnscreenObstacle = new ArrayList<>(5);
        mSmallOnscreenObstacle = new ArrayList<>(10);
        mSpaceship = spaceship;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mMapGen = mapGen;
        Log.i("STATUS", "GameView Started");
        mLargeOnscreenObstacle.clear();
        mSmallOnscreenObstacle.clear();
        random = new Random();
        mContext = context;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void pause() { // check developer.android.com "Pausing and Resuming an Activity"
        playing = false;
        try {
            GameThread.join();
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void run() {
        Log.i("STATUS", "In run()");
        controlGameSpeed(1000);
        while (playing) {
            controlGameSpeed(20);
            checkForUpdates();
            postInvalidate(); // that is, invalidate scene and draw again
            moveObstacles();
            checkForCollision();
        }

    }

    private void checkForUpdates() {
        if (mLargeOnscreenObstacle.size() < 5) {
            int spawnCheck = random.nextInt(100);
            if (spawnCheck == 0) {
                mLargeObstacle = mMapGen.getLargeObstacle();
                mLargeOnscreenObstacle.add(mLargeObstacle);
            }
        }
        if (mSmallOnscreenObstacle.size() < 8) {
            int spawnCheck = random.nextInt(100);
            if (spawnCheck == 0) {
                mSmallObstacle = mMapGen.getSmallObstacle();
                mSmallOnscreenObstacle.add(mSmallObstacle);
            }
        }
    }

    private void moveObstacles() {
        if (!mSmallOnscreenObstacle.isEmpty()) {
            for (int obstacleIndex = 0; obstacleIndex < mSmallOnscreenObstacle.size(); obstacleIndex++) {
                SmallObstacle smallObstacle = mSmallOnscreenObstacle.get(obstacleIndex);
                smallObstacle.moveObstacle();
                smallObstacle.updateHitbox();
                if (smallObstacle.getXPos() < -50) {
                    mSmallOnscreenObstacle.remove(obstacleIndex);
                }
            }
        }
        if (!mLargeOnscreenObstacle.isEmpty()) {
            for (int obstacleIndex = 0; obstacleIndex < mLargeOnscreenObstacle.size(); obstacleIndex++) {
                LargeObstacle largeObstacle = mLargeOnscreenObstacle.get(obstacleIndex);
                largeObstacle.moveObstacle();
                largeObstacle.updateHitbox();
                if (largeObstacle.getXPos() < -200) {
                    mLargeOnscreenObstacle.remove(obstacleIndex);
                }
            }
        }
    }


    private void controlGameSpeed(int millis) {
        try {
            GameThread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkForCollision() {
        for (SmallObstacle smallObstacle : mSmallOnscreenObstacle) {
            if (smallObstacle.getHitbox().intersect(mSpaceship.getHitbox())) {
                mSpaceship.setBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explosion));
                pause();
            }
        }
        for (LargeObstacle largeObstacle : mLargeOnscreenObstacle) {
            if (largeObstacle.getHitbox().intersect(mSpaceship.getHitbox())) {
                mSpaceship.setBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explosion));
                pause();
            }
        }
    }

    public void resume() {
        playing = true;
        GameThread = new Thread(this);
        GameThread.start();
        if (GameThread.isAlive()) {

            Log.i("STATUS", "Game Thread started");
        } else {
            Log.i("STATUS", "Game Thread NOT started");

        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("STATUS", "In SurfaceCreated()");
        setWillNotDraw(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mHolder.getSurface().isValid()) {
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 0, 50, 50));
            mCanvas.drawBitmap(mSpaceship.getBitmap(), mSpaceship.getXPos(), mSpaceship.getYPos(), mPaint);
//            mCanvas.drawRect(mSpaceship.getHitbox(), mPaint); // draws hitbox

            for (LargeObstacle largeObstacle : mLargeOnscreenObstacle) {
                mCanvas.drawBitmap(largeObstacle.getBitmap(), largeObstacle.getXPos(), largeObstacle.getYPos(), mPaint);
//                mCanvas.drawRect(largeObstacle.getHitbox(), mPaint); // draws hitbox
            }

            for (SmallObstacle smallObstacle : mSmallOnscreenObstacle) {
                mCanvas.drawBitmap(smallObstacle.getBitmap(), smallObstacle.getXPos(), smallObstacle.getYPos(), mPaint);
//                mCanvas.drawRect(smallObstacle.getHitbox(), mPaint); // draws hitbox
            }


            mHolder.unlockCanvasAndPost(mCanvas);

        } else {
            Log.e("ERROR", "Surface Invalid!");
        }
    }


}


