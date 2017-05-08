package k2013.fit.hcmus.thesis.id539621.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.HandlerSingleton;
import com.custom.OnScrollCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.dialog.BaseDialog;
import k2013.fit.hcmus.thesis.id539621.dialog.DialogGamePause;
import k2013.fit.hcmus.thesis.id539621.dialog.DialogHelper;
import k2013.fit.hcmus.thesis.id539621.game_operation.GameOperation;
import k2013.fit.hcmus.thesis.id539621.game_operation.GamePlayParams;
import k2013.fit.hcmus.thesis.id539621.model.GameLevel;
import k2013.fit.hcmus.thesis.id539621.model.Position;
import k2013.fit.hcmus.thesis.id539621.model.Sound;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationCallback;
import k2013.fit.hcmus.thesis.id539621.sound.BinauralSound;

public class GamePlayActivity extends BaseActivity implements OnScrollCallback, OrientationCallback {
    private View mPointer;
    private GameOperation mGame;
    private int modeGame;

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
    private int mBackgroundSound;
    private Vector<Integer> mDistractSounds;

    private int totalTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_gameplay);

        mPointer = findViewById(R.id.gameplay_btnShoot);
        mPointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mGame.scrollToPosition(500,500);
                mGame.finish(GamePlayActivity.this, false);
            }
        });


        SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        if (sharedPreferences != null){
            modeGame = sharedPreferences.getInt("gameMode", GamePlayParams.MODE_TOUCH);
        }

        //modeGame = GamePlayParams.MODE_TOUCH;

        HandlerSingleton.init(this, null);


        GameLevel level = (GameLevel)getIntent().getSerializableExtra("Level");

        Log.d("GameData", "Level: " + level.getLevel() + " , distract sound: " + level.getDistract_sound());

        GamePlayParams params = new GamePlayParams();
        params.setTime(20000);
        params.setMode(modeGame);
        params.setBackgroundImg("android.resource://k2013.fit.hcmus.thesis.id539621/drawable/bergsjostolen");


        //Set target sound
        Random r = new Random();
        int targetDistance = r.nextInt(11) + 5;
        int targetAlpha = r.nextInt(361);

        Position targetPosition = new Position(targetDistance * Math.sin(Math.toRadians(targetAlpha)), 0,
                targetDistance * Math.cos(Math.toRadians(targetAlpha)));

        if(level.isHas_horizontal()) {
            float y = r.nextFloat()*2 - 1;
            targetPosition.setY(y);
        }

        List<File> files = getListFiles(new File(Environment.getExternalStorageDirectory() + "/FindItData/Package1/Target"));
        int targetSoundPosition = r.nextInt(files.size());

        params.setTargetSound(new Sound(files.get(targetSoundPosition).getPath(), 20, Sound.TYPE_REPEAT, targetPosition));

        //SET background sound
        if(level.isHas_background_sound()){
            Log.d("621Sound", "has backgroundSound");
            List<File> backgroundSoundFiles = getListFiles(new File(Environment.getExternalStorageDirectory() + "/FindItData/Package1/BackgroundSound"));
            Log.d("621Sound", "has backgroundSound size: " + backgroundSoundFiles.size());
            int backgroundSoundPosition = r.nextInt(backgroundSoundFiles.size());
            params.setBackgroundSound(new Sound(backgroundSoundFiles.get(backgroundSoundPosition).getPath(), 20, Sound.TYPE_REPEAT, new Position(0,0,0)));
        }

        mGame = new GameOperation(this, params);
        //Sound
        BinauralSound.openDevice();
        BinauralSound.setListenerOrientation(0,0,-1,0,1,0);

        //Load target sound
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
            Log.d("","sound " + mTargetSound + " is playing");
        }

        //Load background sound
        if(params.getBackgroundSound() != null){
            mBackgroundSound = BinauralSound.addSource(params.getBackgroundSound().getSoundPath());
            Log.d("621Sound", "path: " + params.getBackgroundSound().getSoundPath());
            //BinauralSound.setPosition(mBackgroundSound, params.getBackgroundSound().getPosition() );
            BinauralSound.setLoop(mBackgroundSound, true);
            BinauralSound.playSound(mBackgroundSound);
        }

        //Load distract sound
        mDistractSounds = new Vector<>();
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

        Log.d("mylog", "GamePlayActivity.onResume()");
        mGame.resume(this);

        BinauralSound.playSound(mTargetSound);
        BinauralSound.playSound(mBackgroundSound);
        for (int distractsound: mDistractSounds){
            BinauralSound.playSound(distractsound);
        }
    }

    protected void onPause() {
        super.onPause();

        BinauralSound.pauseSound(mTargetSound);
        BinauralSound.pauseSound(mBackgroundSound);
        for (int distractsound: mDistractSounds){
            BinauralSound.pauseSound(distractsound);
        }

        Log.d("mylog", "GamePlayActivity.onPause()");
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

        //Log.d("Test matrix",String.format("%f %f %f %f %f %f", -mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
        //        mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]));

        mGame.updateLookAt(mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10]);
        BinauralSound.setListenerOrientation(mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
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

        //Log.d("Test matrix",String.format("%f %f %f %f %f %f", -mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
        //        mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]));

        mGame.updateLookAt(mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10]);
        BinauralSound.setListenerOrientation(mViewMatrix[8], -mViewMatrix[9], -mViewMatrix[10],
                mViewMatrix[4], mViewMatrix[5], mViewMatrix[6]);

    }

    public void gamePlayOnClick(View v) {
        switch (v.getId()) {
            case R.id.gameplay_btnPause: {
                Intent intent = new Intent(this, DialogGamePause.class);
                startActivityForResult(intent, DialogHelper.REQ_CODE_DIALOG_GAME_PAUSE);
                break;
            }

            case R.id.gameplay_btnShoot: {

                break;
            }

            case R.id.gameplay_btnSwitch: {

            }

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DialogHelper.REQ_CODE_DIALOG_GAME_PAUSE: {
                if (requestCode != Activity.RESULT_OK) {
                    break;
                }

                int res = data.getIntExtra(DialogHelper.RES_TITLE, DialogHelper.RES_CODE_CANCEL);
                if (res == DialogHelper.RES_CODE_CANCEL) {
                    finish();
                }

                break;
            }

            case DialogHelper.REQ_CODE_DIALOG_GAME_SUCCESS: {

                break;
            }

            case DialogHelper.REQ_CODE_DIALOG_GAME_FAILED: {

                break;
            }

            case DialogHelper.REQ_CODE_DIALOG_PREGAME: {

                break;
            }

            default:
                break;
        }
    }

    /*************************************** GAMEPLAY FUNCTIONS ***********************************/
    public void timeTick() {
        totalTime++;
        Log.d("timeTick", String.format("time: %d", totalTime));
    }

    public void timeFinish() {
        mGame.finish(this, true);
    }

    public void showPopUp(boolean mode) {
        // Retrieve data from GameOperation
//        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
//        boolean isCorrect = sp.getBoolean(GameOperation.SP_IS_CORRECT, false);
//
//        updatePopUp(isCorrect);
//
//        if (mode) {
//            mPopUpLayout.setVisibility(View.VISIBLE);
//        } else {
//            mPopUpLayout.setVisibility(View.GONE);
//        }

        Intent intent = new Intent(this, BaseDialog.class);
        startActivity(intent);
    }

    public void updatePopUp(boolean isCorrect) {
//        if (isCorrect) {
//            mPopUpMessage.setText(getResources().getString(R.string.gameplay_popup_msg_success));
//            mPopUpBtnAction.setText(getResources().getString(R.string.gameplay_popup_btn_next));
//        } else {
//            mPopUpMessage.setText(getResources().getString(R.string.d_gamefailed_msg));
//            mPopUpBtnAction.setText(getResources().getString(R.string.d_gamefailed_btn_action));
//        }
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

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".wav")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

}