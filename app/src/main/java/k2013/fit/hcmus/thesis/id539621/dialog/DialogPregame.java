package k2013.fit.hcmus.thesis.id539621.dialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.lang.ref.WeakReference;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.sound.BinauralSound;

/**
 * Created by cpu60011-local on 08/05/2017.
 */

public class DialogPregame extends BaseDialog {

    private int mTargetSound;
    private String mTargetSoundPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mTargetSoundPath = intent.getStringExtra(DialogHelper.REQ_TITLE_DIALOG_PREGAME_SOUND_ID);
        mTargetSound = BinauralSound.addSource(mTargetSoundPath);

        this.enableBtnAction(true);
        this.enableBtnCancel(true);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        playSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BinauralSound.pauseSound(mTargetSound);
    }

    @Override
    protected int getBackground() {
        return 0;
    }

    @Override
    protected int getImg() {
        return R.drawable.d_pregame_icon_music;
    }

    @Override
    protected int getMsg() {
        return R.string.d_pregame_msg;
    }

    @Override
    protected int getBtnActionText() {
        return R.string.d_pregame_btn_action;
    }

    @Override
    protected int getBtnCancelText() {
        return R.string.d_pregame_btn_cancel;
    }

    @Override
    protected View.OnClickListener getAction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPregame.this.playSound();
            }
        };
    }

    public void playSound() {
        if (mTargetSound == -1) {
            return;
        }

        BinauralSound.setLoop(mTargetSound, false);
        BinauralSound.playSound(mTargetSound);
    }

    public int getTargetSound() {
        return mTargetSound;
    }
}
