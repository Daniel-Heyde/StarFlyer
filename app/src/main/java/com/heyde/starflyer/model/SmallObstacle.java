package com.heyde.starflyer.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.heyde.starflyer.R;

import java.util.Random;

/**
 * Created by Daniel on 9/7/2016.
 */
public class SmallObstacle {
    private int mXPos;
    private int mYPos;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;
    private int mSpeed;
    private Rect hitbox;

    public SmallObstacle(Context context) {
        mXPos = 100;
        mYPos = 100;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.smallasteroid);
        Random random = new Random();
        mSpeed = 5;
        hitbox = new Rect(mXPos, mYPos, mBitmap.getWidth(), mBitmap.getHeight());

    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setSmallBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public float getYPos() {

        return mYPos;
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public void setYPos(int YPos) {
        mYPos = YPos;
    }

    public float getXPos() {

        return mXPos;
    }

    public void updateHitbox(){
        hitbox.left = mXPos;
        hitbox.top = mYPos;
        hitbox.right = mXPos+mBitmap.getWidth();
        hitbox.bottom = mYPos+mBitmap.getHeight();
    }


    public void setXPos(int xPos) {
        mXPos = xPos;
    }

    public void moveObstacle() {
        mXPos -= mSpeed;
    }
}
