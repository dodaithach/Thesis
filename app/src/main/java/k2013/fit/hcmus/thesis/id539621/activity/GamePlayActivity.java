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

import java.util.Vector;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.game_operation.GameOperation;
import k2013.fit.hcmus.thesis.id539621.game_operation.GamePlayParams;
import k2013.fit.hcmus.thesis.id539621.model.Position;
import k2013.fit.hcmus.thesis.id539621.model.Sound;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationCallback;
import k2013.fit.hcmus.thesis.id539621.sound.BinauralSound;

public class GamePlayActivity extends BaseActivity implements OnScrollCallback, OrientationCallback {
    private View mPointer;
    private GameOperation mGame;
    private int modeGame;

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

    private float[] mViewMatrix = new float[16];
    private float[] mCurrentRotation = new float[16];
    private float[] mCurrentRotationPost = new float[16];
    private float[] mCurrentRotationZ = new float[16];
    private float[] mTempMatrix = new float[16];


    private int mTargetSound;
    private Vector<Integer> mDistractSounds;

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
        params.setMode(GamePlayParams.MODE_SENSOR);
        params.setBackgroundImg("android.resource://k2013.fit.hcmus.thesis.id539621/drawable/bergsjostolen");
        params.setTargetSound(new Sound("/sdcard/pcm.wav", 20, Sound.TYPE_REPEAT ,new Position(0,0,10)));

        mGame = new GameOperation(this, params);

        modeGame = GamePlayParams.MODE_SENSOR;
        //Sound
        BinauralSound.openDevice();
        BinauralSound.setListenerOrientation(0,0,-1,0,1,0);


        if(params.getTargetSound() != null){
            mTargetSound = BinauralSound.addSource(params.getTargetSound().getSoundPath());
            BinauralSound.setPosition(mTargetSound, params.getTargetSound().getPosition() );
            if(params.getTargetSound().getType() == Sound.TYPE_REPEAT){
                BinauralSound.setLoop(mTargetSound, true);
            }
            else {
                BinauralSound.setLoop(mTargetSound, false);
            }
            BinauralSound.playSound(mTargetSound);
        }
        if(params.getDistractSounds() != null){
            for (Sound sound: params.getDistractSounds()) {
                int soundTemp = BinauralSound.addSource(sound.getSoundPath());
                BinauralSound.setPosition(soundTemp, sound.getPosition());
                mDistractSounds.add(soundTemp);
                if(sound.getType() == Sound.TYPE_REPEAT){
                    BinauralSound.setLoop(soundTemp, true);
                }
                else {
                    BinauralSound.setLoop(soundTemp, false);
                }
                BinauralSound.playSound(soundTemp);
            }
        }

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
        BinauralSound.closeDevice();
    }

    float delX = 0.0f, delY = 0.0f;
    @Override
    public void customOnScroll(float velocityX, float velocityY) {

        if(modeGame == GamePlayParams.MODE_TOUCH) {
            delX = delX - ((int) velocityX) / Resources.getSystem().getDisplayMetrics().density * 0.2f;
            delY = delY - ((int) velocityY) / Resources.getSystem().getDisplayMetrics().density * 0.2f;

            Log.d("", "dX: " + delX + " dY: " + delY);

            changeListenerOrientation(-delY, -delX, 0);
        }
    }

    @Override
    public void onOrientationChanged(float[] rotationMatrix) {
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        Matrix.setIdentityM(mTempMatrix, 0);
        Matrix.multiplyMM(mTempMatrix, 0, mViewMatrix, 0, rotationMatrix, 0);

        System.arraycopy(mTempMatrix, 0, mViewMatrix, 0, 16);

        Log.d("Test matrix",String.format("%f %f %f %f %f %f", -mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
                mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]));

        mGame.updateLookAt(-mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10]);
        BinauralSound.setListenerOrientation(-mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
                mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]);
    }

    private void changeListenerOrientation(double horizontal, double vertical, double z){

        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
        Matrix.setIdentityM(mCurrentRotation, 0);
        Matrix.rotateM(mCurrentRotation, 0, (float)horizontal, 1.0f, 0.0f, 0.0f);
        Matrix.setIdentityM(mCurrentRotationPost, 0);
        Matrix.rotateM(mCurrentRotationPost, 0, (float)vertical, 0.0f, 1.0f, 0.0f);

        Matrix.setIdentityM(mCurrentRotationZ, 0);
        Matrix.rotateM(mCurrentRotationZ, 0, (float)z, 0.0f, 0.0f, 1.0f);

        Matrix.setIdentityM(mTempMatrix, 0);
        Matrix.multiplyMM(mTempMatrix, 0, mCurrentRotation, 0, mCurrentRotationPost, 0);

        Matrix.multiplyMM(mCurrentRotation, 0, mTempMatrix, 0, mCurrentRotationZ, 0);
        //System.arraycopy(mTempMatrix, 0, mCurrentRotation, 0, 16);

        Matrix.multiplyMM(mTempMatrix, 0, mViewMatrix, 0, mCurrentRotation, 0);
        System.arraycopy(mTempMatrix, 0, mViewMatrix, 0, 16);

        Log.d("Test matrix",String.format("%f %f %f %f %f %f", -mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
                mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]));

        mGame.updateLookAt(-mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10]);
        BinauralSound.setListenerOrientation(-mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
                mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]);

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