package k2013.fit.hcmus.thesis.id539621.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Trieu on 20/3/2017.
 */

public class OrientationListener implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senOrientation;

    public OrientationCallback callback;

    private final float[] mOrientationReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    public OrientationListener(Context context){
        senSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        senOrientation = senSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        senSensorManager.registerListener(this, senOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            System.arraycopy(event.values, 0, mOrientationReading, 0, mOrientationReading.length);

            Log.d("Trieu", "x: " + event.values[0] + "y: " + event.values[1] + "z: " + event.values[2] );

            if(callback!=null){
                float[] angles = getOrientationAngles();
                callback.onOrientationChanged(angles[0], angles[1], angles[2]);
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

    public void updateOrientationAngles() {
        SensorManager.getRotationMatrixFromVector( mRotationMatrix, mOrientationReading );
        senSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
    }


    public float[] getRotationMatrix(){
        SensorManager.getRotationMatrixFromVector( mRotationMatrix, mOrientationReading );
        return mRotationMatrix;
    }

    public float[] getOrientationAngles(){
        SensorManager.getRotationMatrixFromVector( mRotationMatrix, mOrientationReading );
        senSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
        return mOrientationAngles;
    }
}

