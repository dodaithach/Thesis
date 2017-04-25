package k2013.fit.hcmus.thesis.id539621.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.HandlerSingleton;
import com.custom.OnScrollCallback;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.game_operation.GameOperation;
import k2013.fit.hcmus.thesis.id539621.game_operation.GamePlayParams;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationCallback;

public class GamePlayActivity extends BaseActivity implements OnScrollCallback, OrientationCallback {
    private View mPointer;
    private GameOperation mGame;

    private RelativeLayout mPopUpLayout;
    private ImageView mPopUpImage;
    private TextView mPopUpMessage;
    private Button mPopUpBtnCancel;
    private Button mPopUpBtnAction;

    private final float eyeX = 0;
    private final float eyeY = 0;
    private final float eyeZ = 0;
    private final float lookX = 0;
    private final float lookY = 0;
    private final float lookZ = -1.0f;
    private final float upX = 0.0f;
    private final float upY = 1.0f;
    private final float upZ = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        mPointer = findViewById(R.id.gameplay_pointer);
        mPointer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //mGame.scrollToPosition(500,500);
                mGame.finish(GamePlayActivity.this, false);
                return false;
            }
        });

        mPopUpLayout = (RelativeLayout) findViewById(R.id.gameplay_finishPopUp);
        mPopUpImage = (ImageView) findViewById(R.id.gameplay_finishPopUp_img);
        mPopUpMessage = (TextView) findViewById(R.id.gameplay_finishPopUp_msg);
        mPopUpBtnCancel = (Button) findViewById(R.id.gameplay_finishPopUp_btnCancel);
        mPopUpBtnAction = (Button) findViewById(R.id.gameplay_finishPopUp_btnAction);

        mPopUpBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelGame();
            }
        });

        mPopUpBtnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
            }
        });

        HandlerSingleton.init(this, null);

        GamePlayParams params = new GamePlayParams();
        params.setTime(180000);
        params.setMode(GamePlayParams.MODE_TOUCH);
        mGame = new GameOperation(this, params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGame.isInited()) {
            mGame.initGame();
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

    float delX = 0.0f, delY = 0.0f;
    @Override
    public void customOnScroll(float velocityX, float velocityY) {
        delX = delX - ((int)velocityX) / Resources.getSystem().getDisplayMetrics().density * 0.2f;
        delY = delY - ((int)velocityY) / Resources.getSystem().getDisplayMetrics().density * 0.2f;

        Log.d("", "dX: " + delX + " dY: " + delY);

        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        Matrix.setIdentityM(mCurrentRotation, 0);
        Matrix.rotateM(mCurrentRotation, 0, delY, 1.0f, 0.0f, 0.0f);
        Matrix.setIdentityM(mCurrentRotationPost, 0);
        Matrix.rotateM(mCurrentRotationPost, 0, delX, 0.0f, 1.0f, 0.0f);

        Matrix.setIdentityM(mTempMatrix, 0);
        Matrix.multiplyMM(mTempMatrix, 0, mCurrentRotation, 0, mCurrentRotationPost, 0);
        System.arraycopy(mTempMatrix, 0, mCurrentRotation, 0, 16);

        Matrix.multiplyMM(mTempMatrix, 0, mViewMatrix, 0, mCurrentRotation, 0);
        System.arraycopy(mTempMatrix, 0, mViewMatrix, 0, 16);

        mGame.updateLookAt(-mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10]);
    }

    private float[] mViewMatrix = new float[16];
    private float[] mCurrentRotation = new float[16];
    private float[] mCurrentRotationPost = new float[16];
    private float[] mTempMatrix = new float[16];
    @Override
    public void onOrientationChanged(float azimuth, float pitch, float roll) {
        Log.d("Orient sensor: ", "azimuth: " + Math.toDegrees(azimuth) + " pitch: " + Math.toDegrees(pitch) + " roll: " + Math.toDegrees(roll) );

        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        Matrix.setIdentityM(mCurrentRotation, 0);
        Matrix.rotateM(mCurrentRotation, 0, (float)(azimuth/Math.PI*180), 1.0f, 0.0f, 0.0f);
        Matrix.setIdentityM(mCurrentRotationPost, 0);
        Matrix.rotateM(mCurrentRotationPost, 0, (float)(roll/Math.PI*180) + 90, 0.0f, 1.0f, 0.0f);

        Matrix.setIdentityM(mTempMatrix, 0);
        Matrix.multiplyMM(mTempMatrix, 0, mCurrentRotation, 0, mCurrentRotationPost, 0);
        System.arraycopy(mTempMatrix, 0, mCurrentRotation, 0, 16);

        Matrix.multiplyMM(mTempMatrix, 0, mViewMatrix, 0, mCurrentRotation, 0);
        System.arraycopy(mTempMatrix, 0, mViewMatrix, 0, 16);

        mGame.updateLookAt(-mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10]);
    }

    /*************************************** GAMEPLAY FUNCTIONS ***********************************/
    public void timeTick() {
//        Log.d("mylog", "timeTick()");
    }

    public void timeFinish() {
        Log.d("mylog", "timeFinish()");
        mGame.finish(this, true);
    }

    public void showPopUp(boolean mode) {
        // Retrieve data from GameOperation
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        boolean isCorrect = sp.getBoolean(GameOperation.SP_IS_CORRECT, false);

        updatePopUp(isCorrect);

        if (mode) {
            mPopUpLayout.setVisibility(View.VISIBLE);
        } else {
            mPopUpLayout.setVisibility(View.GONE);
        }
    }

    public void updatePopUp(boolean isCorrect) {
        if (isCorrect) {
            mPopUpMessage.setText(getResources().getString(R.string.gameplay_popup_msg_success));
            mPopUpBtnAction.setText(getResources().getString(R.string.gameplay_popup_btn_next));
        } else {
            mPopUpMessage.setText(getResources().getString(R.string.gameplay_popup_msg_failed));
            mPopUpBtnAction.setText(getResources().getString(R.string.gameplay_popup_btn_replay));
        }
    }

    public void cancelGame() {
        finish();
    }

    public void nextAction() {
        // Retrieve data from GameOperation
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        boolean isCorrect = sp.getBoolean(GameOperation.SP_IS_CORRECT, false);
        clearSharedPreference();

        if (isCorrect) {
            nextGame();
        } else {
            replay();
        }
    }

    public void nextGame() {

    }

    public void replay() {

    }

    public void clearSharedPreference() {
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}