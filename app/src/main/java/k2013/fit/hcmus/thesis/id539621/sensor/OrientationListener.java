package k2013.fit.hcmus.thesis.id539621.sensor;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by Trieu on 20/3/2017.
 */

public class OrientationListener implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senOrientation;
    private static float[] mTmp = new float[16];
    Context context;
    public OrientationCallback callback;

    private final float[] mOrientationReading = new float[3];

    private final float[] mRotationMatrix = new float[16];

    public OrientationListener(Context context){
        senSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        senOrientation = senSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        this.context = context;
        //senSensorManager.registerListener(this, senOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            System.arraycopy(event.values, 0, mOrientationReading, 0, mOrientationReading.length);


            Log.d("Trieu", "x: " + event.values[0] + "y: " + event.values[1] + "z: " + event.values[2] );

            if(callback!=null){
                WindowManager windowManager =  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

                Configuration config = context.getResources().getConfiguration();

                int rotation = windowManager.getDefaultDisplay().getRotation();
                sensorRotationVector2Matrix(mOrientationReading, rotation, mRotationMatrix);

                callback.onOrientationChanged(mRotationMatrix);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void unregisterListener() {
        senSensorManager.unregisterListener(this);
    }

    public void registerListener() {
        senSensorManager.registerListener(this,senOrientation,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public static void sensorRotationVector2Matrix(float[] orientationReading, int rotation, float[] output) {
        float[] values = orientationReading;
        switch (rotation){
            case Surface.ROTATION_0:
            case Surface.ROTATION_180: /* Notice: not supported for ROTATION_180! */
                SensorManager.getRotationMatrixFromVector(output, values);
                break;
            case Surface.ROTATION_90:
                SensorManager.getRotationMatrixFromVector(mTmp, values);
                SensorManager.remapCoordinateSystem(mTmp, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, output);
                break;
            case Surface.ROTATION_270:
                SensorManager.getRotationMatrixFromVector(mTmp, values);
                SensorManager.remapCoordinateSystem(mTmp, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, output);
                break;
        }
        Matrix.rotateM(output, 0, 90.0F, 1.0F, 0.0F, 0.0F);
    }
}

