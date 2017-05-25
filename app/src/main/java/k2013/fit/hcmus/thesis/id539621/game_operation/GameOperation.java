package k2013.fit.hcmus.thesis.id539621.game_operation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;

import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.texture.MD360BitmapTexture;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.activity.GamePlayActivity;
import k2013.fit.hcmus.thesis.id539621.model.Position;
import k2013.fit.hcmus.thesis.id539621.sensor.OrientationListener;

/**
 * Created by cpu60011-local on 11/04/2017.
 */

public class GameOperation {
    public static final int GAME_FAILED = 0;
    public static final int GAME_SUCCESS = 1;
    public static final int TIME_UP = -1;

    public static final int TIME_TICK = 500;
    public static final String SP_IS_CORRECT = "IS_CORRECT";

    private GamePlayParams mParams;
    private WeakReference<GamePlayActivity> mWeakReference;
    private Target mTarget;
    private MDVRLibrary mVRLibrary;
    private OrientationListener mOrientationListener;
    private int mMode;

    private CountDownTimer mTimer;
    private long mRemainingTime = 0;
    private Position mCurLookAt = new Position(0,0,-1);

    private boolean mIsInited = false;

    public GameOperation() {}

    public GameOperation(GamePlayActivity activity, GamePlayParams params) {

        mWeakReference = new WeakReference<GamePlayActivity>(activity);
        mParams = params;
        mRemainingTime = mParams.getTime();

        if (params.getMode() == GamePlayParams.MODE_SENSOR) {
            mMode = MDVRLibrary.INTERACTIVE_MODE_MOTION;
        } else {
            mMode = MDVRLibrary.INTERACTIVE_MODE_TOUCH;
        }

        mOrientationListener = new OrientationListener(activity);
        mOrientationListener.callback = activity;
    }

    /************************************* GAME STATE FUNCTIONS ***********************************/
    public void initGame() {

        mVRLibrary = createVRLibrary();
        mIsInited = true;

    }

    public void pause(Context context) {
        mVRLibrary.onPause(context);
        mTimer.cancel();

        if (mMode == MDVRLibrary.INTERACTIVE_MODE_MOTION) {
            if(mOrientationListener != null) {
                mOrientationListener.unregisterListener();
            }
        }
    }

    public void resume(Context context) {
        mVRLibrary.onResume(context);

        if (mMode == MDVRLibrary.INTERACTIVE_MODE_MOTION) {
            if(mOrientationListener != null){
                mOrientationListener.registerListener();
            }
        }

        mTimer = new CountDownTimer(mRemainingTime, TIME_TICK) {
            @Override
            public void onTick(long millisUntilFinished) {
                mRemainingTime = millisUntilFinished;
                getActivity().timeTick();
            }

            @Override
            public void onFinish() {
                getActivity().timeFinish();
            }
        }.start();
    }

    public void stop(Context context) {
        mVRLibrary.onPause(context);
    }

    public void finish(Context context, boolean isTimeUp) {
        mTimer.cancel();

        boolean isCorrect = false;

        if (!isTimeUp) {
            // calculate result
            isCorrect = calcResult();

            if (isCorrect) {
                storeGameResult(GameOperation.GAME_SUCCESS);
            } else {
                storeGameResult(GameOperation.GAME_FAILED);
            }
        } else {
            storeGameResult(GameOperation.TIME_UP);
        }

        stop(context);

        GamePlayActivity activity = getActivity();
        if (!activity.isDestroyed() && !activity.isFinishing()) {
            activity.showGameResult();
        }
    }

    public void destroy() {
        mVRLibrary.onDestroy();
    }

    /************************************ SUPPORT FUNCTIONS ***************************************/
    public MDVRLibrary createVRLibrary() {
        return MDVRLibrary.with(getActivity())
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(mMode)
                .asBitmap(new MDVRLibrary.IBitmapProvider() {
                    @Override
                    public void onProvideBitmap(final MD360BitmapTexture.Callback callback) {
                        loadImage(getUri(), callback);
                    }
                }).build(R.id.gameplay_glview);
    }

    private MDVRLibrary getVRLibrary() {
        return mVRLibrary;
    }

    private void loadImage(Uri uri, final MD360BitmapTexture.Callback callback) {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();

        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // notify if size changed
                getVRLibrary().onTextureResize(bitmap.getWidth(), bitmap.getHeight());

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
        Picasso.with(getActivity().getApplicationContext()).load(uri).resize(3072, 2048)
                .centerInside().into(mTarget);
    }

    private Uri getUri() {
        try {
            Uri res = Uri.parse(mParams.getBackgroundImg());
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public GamePlayActivity getActivity() {
        return mWeakReference.get();
    }


    public void updateLookAt(double x, double y, double z) {
        mCurLookAt.setX(x);
        mCurLookAt.setY(y);
        mCurLookAt.setZ(z);
    }

    public boolean isInited() {
        return mIsInited;
    }

    public boolean calcResult() {

        double a1 = mParams.getTargetSound().getPosition().getX();
        double a2 = mParams.getTargetSound().getPosition().getY();
        double a3 = mParams.getTargetSound().getPosition().getZ();
        double b1 = mCurLookAt.getX();
        double b2 = mCurLookAt.getY();
        double b3 = mCurLookAt.getZ();

        double alpha = Math.acos((a1*b1 + a2*b2 + a3*b3)/(Math.sqrt(a1*a1 + a2*a2 + a3*a3)*Math.sqrt(b1*b1 + b2*b2 + b3*b3)));

        if(Math.toDegrees(alpha) >= -10 && Math.toDegrees(alpha) <= 10) {
            return true;
        }

        return false;
    }

    private void storeGameResult(int result) {
        GamePlayActivity activity = getActivity();
        if (!activity.isDestroyed() && !activity.isFinishing()) {
            SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();

            editor.putInt(SP_IS_CORRECT, result);
            editor.commit();
        }
    }

    public void switchMode(int mode){
        mParams.setMode(mode);
        if (mode == GamePlayParams.MODE_SENSOR) {
            mMode = MDVRLibrary.INTERACTIVE_MODE_MOTION;
            if(mOrientationListener != null) {
                mOrientationListener.registerListener();
            }
        } else {
            mMode = MDVRLibrary.INTERACTIVE_MODE_TOUCH;
            mOrientationListener.unregisterListener();
        }
        mVRLibrary.switchInteractiveMode(getActivity(),mMode);
    }
}
