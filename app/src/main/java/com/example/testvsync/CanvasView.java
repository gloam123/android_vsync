package com.example.testvsync;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Queue;

public class CanvasView extends FrameLayout {

    public CanvasView(@NonNull Context context) {
        super(context);
        init();
    }

    public CanvasView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroun(canvas);
    }

    private LinkedList<Long> mFrameTimes;
    long mLastTime = 0;

    public void addFrameInterval(long delay) {
        mFrameTimes.addFirst(delay);
        if (mFrameTimes.size() > 200) {
            mFrameTimes.removeLast();
        }
    }

    final int GRID_WIDTH = 128;
    final int GRID_COLOR = Color.argb(128, 128, 128, 128);
    final int FRAME_TIME_COLOR = Color.rgb(255, 64, 64);
    final int FRAME_LINE_SIZE = 3;
    final int PIXCEL_UNIT = 10;
    final int FRAME_LINE_LEFT_MARGIN = 84;
    final int FONT_SIZE = 28;

    private void init() {
        setWillNotDraw(false);
        mFrameTimes = new LinkedList<Long>();
    }

    private void drawBackgroun(Canvas canvas) {
        long currTime = System.currentTimeMillis();
        long delay = currTime - mLastTime;
        mLastTime = currTime;
        //android.util.Log.e("gqg2:", "onDraw delay=" + delay);

        int height = canvas.getHeight();
        int width = canvas.getWidth();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(GRID_COLOR);
        for (int y=height - GRID_WIDTH; y>=0; y-=GRID_WIDTH) {
            canvas.drawLine(0, y, width, y, paint);
        }

        long time = 0;
        paint.setColor(Color.BLACK);
        paint.setTextSize(FONT_SIZE);
        for (int y=height; y>=0; y-=GRID_WIDTH) {
            canvas.drawText(String.valueOf(time) + "ms",  7, y, paint);
            time += PIXCEL_UNIT;
        }

        paint.setColor(FRAME_TIME_COLOR);
        paint.setStrokeWidth(FRAME_LINE_SIZE);

        int x = width;
        int index = 0;
        while (x >= FRAME_LINE_LEFT_MARGIN && index < mFrameTimes.size()) {
            long t = mFrameTimes.get(index);
            canvas.drawLine(x, height, x, height - t*PIXCEL_UNIT, paint);

            x -= FRAME_LINE_SIZE + 3;
            index++;
        }
    }
}
