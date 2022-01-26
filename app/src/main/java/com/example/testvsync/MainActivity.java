package com.example.testvsync;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private CanvasView mCanvasView;
    private VSyncMonitor mVSyncMonitor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        mCanvasView = findViewById(R.id.id_canvas);
        findViewById(R.id.id_test_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testStart();
            }
        });
    }

    long mLastTime = 0;
    long mLastTime2 = 0;
    private void testStart() {
        if (mVSyncMonitor == null) {
            mVSyncMonitor = new VSyncMonitor(this, new VSyncMonitor.Listener() {
                @Override
                public void onVSync(VSyncMonitor monitor, long vsyncTimeMicros) {
                    mVSyncMonitor.requestUpdate();
                    long currTime = System.currentTimeMillis();
                    long delay = currTime - mLastTime;
                    mLastTime = currTime;

                    long ct2 = vsyncTimeMicros / 1000;
                    long d2 = ct2 - mLastTime2;
                    mLastTime2 = ct2;
                    //android.util.Log.e("gqg2:", "mCanvasView.invalidate delay=" + delay + "; d2=" + d2);
                    mCanvasView.addFrameInterval(delay);

                    //mCanvasView.invalidate();
                    mCanvasView.postInvalidateOnAnimation();
                }
            }, 60);
            mVSyncMonitor.requestUpdate();
        }
    }
}