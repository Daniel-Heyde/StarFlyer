package com.heyde.starflyer.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.heyde.starflyer.R;

/**
 * Created by Daniel on 8/30/2016.
 */
public class Spaceship {
    private int mXPos;
    private int mYPos;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;
    private Rect hitbox;

    public Spaceship(Context context) {
        mXPos = 100;
        mYPos = 100;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.saucer);
        hitbox = new Rect(mXPos, mYPos, mBitmap.getWidth(), mBitmap.getHeight());
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
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

    public Rect getHitbox() {
        return hitbox;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public float getYPos() {

        return mYPos;
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


}

