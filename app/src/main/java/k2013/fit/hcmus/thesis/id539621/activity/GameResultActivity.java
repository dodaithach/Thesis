package k2013.fit.hcmus.thesis.id539621.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.texture.MD360BitmapTexture;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.game_operation.GameOperation;
import k2013.fit.hcmus.thesis.id539621.model.Position;
import k2013.fit.hcmus.thesis.id539621.sound.BinauralSound;

/**
 * Created by cpu60011-local on 23/05/2017.
 */

public class GameResultActivity extends BaseActivity {
    public static final String IMG_PATH = "IMG_PATH";
    public static final String GAME_RES = "GAME_RES";
    public static final String SOUND_ID = "SOUND_ID";
    public static final String POS_X = "POS_X";
    public static final String POS_Y = "POS_Y";
    public static final String TARGET_SOUND = "TARGET_SOUND";
    public static final String TARGET_POSITION = "TARGET_POSITION";
    public static final int REQ_CODE = 1111;
    public static final String RES_CODE = "RES_CODE";
    public static final int CODE_CANCEL = 0;
    public static final int CODE_ACTION = 1;

    private MDVRLibrary mVRLibrary;

    private int mGameResult = GameOperation.GAME_FAILED;
    private String mImgPath = "android.resource://k2013.fit.hcmus.thesis.id539621/raw/bergsjostolen";
    private int mDelX;
    private int mDelY;

    private Target mTarget;
    private String mTargetSound;
    private int mSoundId;
    private Position mTargetPosition;

    private final int PX_PER_W_DEG = 6;
    private final int PX_PER_H_DEG = 9;
    private final float mDensity = Resources.getSystem().getDisplayMetrics().density;
    private final float mScale = 0.8f;

    private boolean mIsInited = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_gameresult);

        Intent intent = getIntent();
        mGameResult = intent.getIntExtra(GameResultActivity.GAME_RES, GameOperation.GAME_FAILED);
        mImgPath = intent.getStringExtra(GameResultActivity.IMG_PATH);
        mSoundId = intent.getIntExtra(GameResultActivity.SOUND_ID, -1);
        mDelX = intent.getIntExtra(GameResultActivity.POS_X, 0);
        mDelY = intent.getIntExtra(GameResultActivity.POS_Y, 0);
        mTargetSound = intent.getStringExtra(GameResultActivity.TARGET_SOUND);
        mTargetPosition = intent.getParcelableExtra(GameResultActivity.TARGET_POSITION);

        Button btnAction = (Button) findViewById(R.id.gameresult_btnaction);
        Button btnCancel = (Button) findViewById(R.id.gameresult_btncancel);
        TextView title = (TextView) findViewById(R.id.gameresult_title);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.gameresult_container);

        if (mGameResult == GameOperation.GAME_FAILED || mGameResult == GameOperation.TIME_UP) {
            btnAction.setText(R.string.a_gameresult_btn_replay);

            if (mGameResult == GameOperation.GAME_FAILED) {
                title.setText(R.string.a_gameresult_msg_failed);
            } else {
                title.setText(R.string.a_gameresult_msg_timeup);
            }
            container.setBackgroundResource(R.drawable.d_gamefailed_background);

            btnCancel.setBackgroundResource(R.drawable.d_gamefailed_btn_cancel);

            ColorStateList btnCancelColors = new ColorStateList(
                    new int[][] {
                            new int[] {android.R.attr.state_pressed},
                            new int[] {}
                    },
                    new int[] {
                            getResources().getColor(R.color.themeLight),
                            getResources().getColor(R.color.themeLight)
                    }
            );
            btnCancel.setTextColor(btnCancelColors);

            btnAction.setBackgroundResource(R.drawable.d_game_failed_btn_action);

            ColorStateList btnActionColors = new ColorStateList(
                    new int[][] {
                            new int[] {android.R.attr.state_pressed},
                            new int[] {}
                    },
                    new int[] {
                            getResources().getColor(R.color.themeLight),
                            getResources().getColor(R.color.themeLightAccent)
                    }
            );
            btnAction.setTextColor(btnActionColors);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mIsInited) {
            mIsInited = true;

            mVRLibrary = createVRLibrary();
            Log.d("gameresult", "delX: " + mDelX + " - delY: " + mDelY);
            mVRLibrary.testScroll((int) (mDensity * mDelX * PX_PER_W_DEG * mScale), (int) (mDensity * mDelY * PX_PER_H_DEG * mScale));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVRLibrary.onResume(this);

        if (mTargetSound != null) {
            mSoundId = BinauralSound.addSource(mTargetSound);
            double distance = -Math.sqrt(mTargetPosition.getX() * mTargetPosition.getX() +
                                            mTargetPosition.getY() * mTargetPosition.getY() +
                                            mTargetPosition.getZ() * mTargetPosition.getZ());
            BinauralSound.setPosition(mSoundId, 0, 0, (float) distance);
            BinauralSound.setListenerOrientation(0,0,-1,0,1,0);
            BinauralSound.setLoop(mSoundId, true);
            BinauralSound.playSound(mSoundId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVRLibrary.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVRLibrary.onPause(this);

        if (mSoundId != 0) {
            BinauralSound.pauseSound(mSoundId);
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    public void gameResultOnClick(View v) {
        switch (v.getId()) {
            case R.id.gameresult_btncancel: {
                Intent intent = new Intent();
                intent.putExtra(GameResultActivity.RES_CODE, GameResultActivity.CODE_CANCEL);

                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    getParent().setResult(Activity.RESULT_OK, intent);
                }
                finish();

                break;
            }

            case R.id.gameresult_btnaction: {
                Intent intent = new Intent();
                intent.putExtra(GameResultActivity.RES_CODE, GameResultActivity.CODE_ACTION);

                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    getParent().setResult(Activity.RESULT_OK, intent);
                }
                finish();

                break;
            }

            default:
                break;
        }
    }

    private MDVRLibrary createVRLibrary() {
        return MDVRLibrary.with(this)
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_TOUCH)
                .asBitmap(new MDVRLibrary.IBitmapProvider() {
                    @Override
                    public void onProvideBitmap(final MD360BitmapTexture.Callback callback) {
                        loadImage(getUri(), callback);
                    }
                }).build(R.id.gameresult_glview);
    }

    private void loadImage(Uri uri, final MD360BitmapTexture.Callback callback) {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();

        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // notify if size changed
                mVRLibrary.onTextureResize(bitmap.getWidth(), bitmap.getHeight());

                // texture
                callback.texture(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        Picasso.with(getApplicationContext()).load(uri).resize(3072, 2048)
                .centerInside().into(mTarget);
    }

    private Uri getUri() {
        try {
            Uri res = Uri.parse(mImgPath);
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
