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
    //private Sensor senAccelerometer;
    //private Sensor senMagneticField;
    private Sensor senOrientation;

    public OrientationCallback callback;

    //private final float[] mAccelerometerReading = new float[3];
    //private final float[] mMagnetometerReading = new float[3];
    private final float[] mOrientationReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    public OrientationListener(Context context){
        senSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //senMagneticField = senSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        senOrientation = senSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        //senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        //senSensorManager.registerListener(this, senMagneticField , SensorManager.SENSOR_DELAY_NORMAL);

        senSensorManager.registerListener(this, senOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

//        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            System.arraycopy(event.values, 0, mAccelerometerReading,
//                    0, mAccelerometerReading.length);
//
//            if(callback!=null){
//                updateOrientationAngles();
//                callback.onOrientationChanged();
//            }
//        }
//        else if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//            System.arraycopy(event.values, 0, mMagnetometerReading,
//                    0, mMagnetometerReading.length);
//
//            if(callback!=null){
//                updateOrientationAngles();
//                callback.onOrientationChanged();
//            }
//        }

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
        //senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        //senSensorManager.registerListener(this, senMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this,senOrientation,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void updateOrientationAngles() {
        //senSensorManager.getRotationMatrix(mRotationMatrix, null,
        //        mAccelerometerReading, mMagnetometerReading);

        SensorManager.getRotationMatrixFromVector( mRotationMatrix, mOrientationReading );
        senSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
    }


    public float[] getRotationMatrix(){
        //senSensorManager.getRotationMatrix(mRotationMatrix, null,
        //        mAccelerometerReading, mMagnetometerReading);
        SensorManager.getRotationMatrixFromVector( mRotationMatrix, mOrientationReading );
        return mRotationMatrix;
    }

    public float[] getOrientationAngles(){
        SensorManager.getRotationMatrixFromVector( mRotationMatrix, mOrientationReading );
        senSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
        return mOrientationAngles;
    }
}

