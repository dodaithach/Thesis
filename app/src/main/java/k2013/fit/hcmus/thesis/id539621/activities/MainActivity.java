package k2013.fit.hcmus.thesis.id539621.activities;


import android.database.sqlite.SQLiteOpenHelper;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationCallback;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationListener;
import k2013.fit.hcmus.thesis.id539621.sound.BinauralSound;

/**
 * Created by Trieu on 5/4/2017.
 */

public class MainActivity extends FragmentActivity {
    private TextView textView;
    private float[] mAngles = new float[3];
    private OrientationListener mOrientationListener;
    private float[] mViewMatrix = new float[16];
    private float[] mCurrentRotation = new float[16];
    private float[] mCurrentRotationPost = new float[16];
    private float[] mTempMatrix = new float[16];

    private BinauralSound binauralSound;
    private int sound1;
    private int sound2;

    int  temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

        mOrientationListener = new OrientationListener(this);
        mOrientationListener.registerListener();

        mOrientationListener.callback = new OrientationCallback() {
            @Override
            public void onOrientationChanged(float azimuth, float pitch, float roll) {
                Log.d("Orient sensor: ", "azimuth: " + Math.toDegrees(azimuth) + " pitch: " + Math.toDegrees(pitch) + " roll: " + Math.toDegrees(roll) );

                final float eyeX = 0;
                final float eyeY = 0;
                final float eyeZ = 0;
                final float lookX = 0;
                final float lookY = 0;
                final float lookZ = -1.0f;
                final float upX = 0.0f;
                final float upY = 1.0f;
                final float upZ = 0.0f;
                Matrix.setIdentityM(mViewMatrix, 0);
                Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

                Log.d("test A&R" , String.format("azimuth: %f roll: %f", (float)(azimuth/Math.PI*180), (float)(roll/Math.PI*180) + 90));

                Matrix.setIdentityM(mCurrentRotation, 0);
                Matrix.rotateM(mCurrentRotation, 0, (float)(azimuth/Math.PI*180), 1.0f, 0.0f, 0.0f);
                Matrix.setIdentityM(mCurrentRotationPost, 0);
                Matrix.rotateM(mCurrentRotationPost, 0, (float)(roll/Math.PI*180) + 90, 0.0f, 1.0f, 0.0f);

                Matrix.setIdentityM(mTempMatrix, 0);
                Matrix.multiplyMM(mTempMatrix, 0, mCurrentRotation, 0, mCurrentRotationPost, 0);
                System.arraycopy(mTempMatrix, 0, mCurrentRotation, 0, 16);

                Matrix.multiplyMM(mTempMatrix, 0, mViewMatrix, 0, mCurrentRotation, 0);
                System.arraycopy(mTempMatrix, 0, mViewMatrix, 0, 16);

                Log.d("Test matrix",String.format("%f %f %f %f %f %f", -mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
                        mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]));


                binauralSound.setListenerOrientation(-mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
                        mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]);
            }
        };


        binauralSound = new BinauralSound();

        binauralSound.openDevice();

        binauralSound.setListenerOrientation(0,0,-1,0,1,0);

        //sound1 = binauralSound.addSource("/sdcard/pcm.wav");
        sound2 = binauralSound.addSource("/sdcard/tone.wav");

        binauralSound.setLoop(sound2, false);

       // binauralSound.setPosition(sound2, 0, 10, 0);



        final Button btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("Btn Click", "sound: " + sound2);
                //binauralSound.playSound(sound1);
//                switch (temp){
//                    case 0:
//                        binauralSound.setPosition(sound2, 10, 0, 0);
//                        break;
//                    case 1:
//                        binauralSound.setPosition(sound2, -10, 0, 0);
//                        break;
//                    case 2:
//                        binauralSound.setPosition(sound2, 0, 10, 0);
//                        break;
//                    case 3:
//                        binauralSound.setPosition(sound2, 0, -10, 0);
//                        break;
//
//                }
//
//                temp = (temp + 1)%4;

                binauralSound.setPosition(sound2, 0, 0, -10);

                binauralSound.playSound(sound2);

                //binauralSound.testSound();
            }
        });



    }

    protected void onPause() {
        super.onPause();
        if(mOrientationListener != null) {
            mOrientationListener.unregisterListener();
        }
    }

    protected void onResume() {
        super.onResume();
        if(mOrientationListener != null){
            mOrientationListener.registerListener();
        }
    }

    public static double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {         // rows from m1
            for(int j = 0; j < mRColLength; j++) {     // columns from m2
                for(int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }
}