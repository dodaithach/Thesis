package k2013.fit.hcmus.thesis.id539621.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.texture.MD360BitmapTexture;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.dialog.DialogHelper;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by cpu60011-local on 23/05/2017.
 */

public class GameResultActivity extends BaseActivity {
    public static final String IMG_PATH = "IMG_PATH";
    public static final String GAME_RES = "GAME_RES";
    public static final String SOUND_ID = "SOUND_ID";
    public static final int REQ_CODE = 1111;
    public static final String RES_CODE = "RES_CODE";
    public static final int CODE_CANCEL = 0;
    public static final int CODE_ACTION = 1;

    private MDVRLibrary mVRLibrary;

    private boolean mGameResult;
    private int mSoundId;
    private String mImgPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_gameresult);

        Intent intent = getIntent();
        mGameResult = intent.getBooleanExtra(GameResultActivity.GAME_RES, false);
        mImgPath = intent.getStringExtra(GameResultActivity.IMG_PATH);
        mSoundId = intent.getIntExtra(GameResultActivity.SOUND_ID, -1);

        Log.d("mylog", "image path: " + mImgPath);

        Button btnAction = (Button) findViewById(R.id.gameresult_btnaction);
        TextView title = (TextView) findViewById(R.id.gameresult_title);

        if (mGameResult) {
            btnAction.setText(R.string.a_gameresult_btn_next);
            title.setText(R.string.a_gameresult_msg_success);
        } else {
            btnAction.setText(R.string.a_gameresult_btn_replay);
            title.setText(R.string.a_gameresult_msg_failed);
        }

        mVRLibrary = createVRLibrary();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVRLibrary.onResume(this);
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

        Target target = new Target() {
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
                .centerInside().memoryPolicy(NO_CACHE, NO_STORE).into(target);
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
