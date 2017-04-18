package k2013.fit.hcmus.thesis.id539621.activities;

import android.content.res.Resources;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.custom.HandlerSingleton;
import com.custom.OnScrollCallback;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.game_operation.GameOperation;
import k2013.fit.hcmus.thesis.id539621.game_operation.GamePlayParams;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationCallback;

public class GamePlayActivity extends MD360PlayerActivity implements OnScrollCallback, OrientationCallback {
    private View mPointer;

    private GameOperation mGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPointer = findViewById(R.id.gameplay_pointer);
        mPointer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mGame.finish(GamePlayActivity.this);
                return false;
            }
        });

        HandlerSingleton.init(this, null);

        GamePlayParams params = new GamePlayParams();
        params.setTime(5000);
        params.setMode(GamePlayParams.MODE_SENSOR);
        mGame = new GameOperation(this, params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGame.isInited()) {
            mGame.init();
        }
    }

    protected void onResume() {
        super.onResume();
        mGame.resume(this);
    }

    protected void onPause() {
        super.onPause();
        mGame.pause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGame.destroy();
    }

    float roll = 0.0f, pitch = 0.0f;
    float delX = 0.0f, delY = 0.0f;
    @Override
    public void customOnScroll(float velocityX, float velocityY) {

        delX = delX - ((int)velocityX) / Resources.getSystem().getDisplayMetrics().density * 0.2f;
        delY = delY - ((int)velocityY) / Resources.getSystem().getDisplayMetrics().density * 0.2f;

        roll = delX - ((int)(delX/360))*360;
        pitch = delY - ((int)(delY/360))*360;
    }

    private float[] mViewMatrix = new float[16];
    private float[] mCurrentRotation = new float[16];
    private float[] mCurrentRotationPost = new float[16];
    private float[] mTempMatrix = new float[16];
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
    }

    /*************************************** GAMEPLAY FUNCTIONS ***********************************/
    public void timeTick() {
        Log.d("mylog", "timeTick()");
    }

    public void timeFinish() {
        Log.d("mylog", "timeFinish()");
        mGame.stop(this);
    }
}