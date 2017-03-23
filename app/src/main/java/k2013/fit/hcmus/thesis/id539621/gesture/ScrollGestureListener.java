package k2013.fit.hcmus.thesis.id539621.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Trieu on 22/3/2017.
 */

public class ScrollGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String DEBUG_TAG = "Gestures";

    private float scale = 1000;
    private float azimuth = 0.0f;
    private float pitch = 0.0f;

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2,
                            float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString()+event2.toString());
        Log.d(DEBUG_TAG, "X: " + velocityX + " Y: " + velocityY);

        azimuth = azimuth - velocityX/scale;
        pitch = pitch - velocityY/scale;

        return true;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}