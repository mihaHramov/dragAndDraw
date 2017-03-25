package com.example.miha.draganddraw;

import android.graphics.PointF;

import java.io.Serializable;

/**
 * Created by miha on 22.03.17.
 */

public class Box implements Serializable{
    public static final String serializableName = "BoxSerializableName";
    private PointF mOrigin;
    private PointF mCurrent;

    public  Box(PointF origin){
        mCurrent = mOrigin = origin;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF mCurrent) {
        this.mCurrent = mCurrent;

    }
}
