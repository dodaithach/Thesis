package k2013.fit.hcmus.thesis.id539621.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationCallback;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationListener;

public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private float[] mAngles = new float[3];
    private OrientationListener mOrientationListener;

    private GestureDetectorCompat mDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

        //textView.setText("haha");

        mOrientationListener = new OrientationListener(this);
        mOrientationListener.registerListener();

        mOrientationListener.callback = new OrientationCallback() {
            @Override
            public void onOrientationChanged() {
                mAngles = mOrientationListener.getOrientationAngles();
                textView.setText("x: " + mAngles[0] + " y: " + mAngles[1] + " z: " + mAngles[2]);
            }
        };

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    protected void onPause() {
        super.onPause();
        mOrientationListener.unregisterListener();
    }

    protected void onResume() {
        super.onResume();
        mOrientationListener.registerListener();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onScroll: " + event1.toString()+event2.toString());
            Log.d(DEBUG_TAG, "X: " + velocityX + " Y: " + velocityY);
            return true;
        }
    }
}