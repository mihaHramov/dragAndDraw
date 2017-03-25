package com.example.miha.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by miha on 22.03.17.
 */

public class BoxDrawingView extends View {
    public static final String TAG = "BoxDrawingView";
    public static final String BoxSerializableList = "BoxSerializableList";
    private Box mCurrentBox;
    private ArrayList<Box> mBoxes = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;
    int lastFingerIndex = 0;
    int lastFingerID = 0;
    int firsFingerIndex = 0;
    int firsFingerID = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());
        Log.i(TAG, "Received event at x=" + curr.x +
                ", y=" + curr.y + ":");


        // event.getPointerId();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, " ACTION_DOWN");
                // Reset drawing state
                mCurrentBox = new Box(curr);
                mBoxes.add(mCurrentBox);
                firsFingerIndex = event.getActionIndex();
                firsFingerID = event.getPointerId(firsFingerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, " ACTION_MOVE");
                if (mCurrentBox != null) {
                    if (firsFingerID == event.getPointerId(event.getActionIndex())) {
                        mCurrentBox.setCurrent(curr);//получил новые координаты
                    }else{
                        //// TODO: 23.03.17  rotate rectangle

                    }
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, " mnogo palec");
                lastFingerIndex = event.getActionIndex();
                lastFingerID = event.getPointerId(lastFingerIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, " ostalsya odin");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, " ACTION_UP");
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, " ACTION_CANCEL");
                mCurrentBox = null;
                break;
        }
        return true;
    }

    public BoxDrawingView(Context context) {
        this(context, null);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putParcelable("superState", super.onSaveInstanceState());

        bundle.putSerializable(Box.serializableName, mCurrentBox);
        bundle.putSerializable("susl", mBoxes);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {

            Bundle bundle = (Bundle) state;

            mCurrentBox = (Box) bundle.getSerializable(Box.serializableName);
            mBoxes = (ArrayList<Box>) bundle.getSerializable("susl");

           // state = bundle.getParcelable("superState");

        }

        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(mBackgroundPaint);
        for (Box box : mBoxes) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);

        }
    }

    // Используется при заполнении представления по разметке XML
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);


    }
}
