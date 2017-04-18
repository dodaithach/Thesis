package k2013.fit.hcmus.thesis.id539621.game_operation;

import java.util.ArrayList;

import k2013.fit.hcmus.thesis.id539621.model.Sound;

/**
 * Created by cpu60011-local on 10/04/2017.
 */

public class GamePlayParams {
    public static final int MODE_TOUCH = 0;
    public static final int MODE_SENSOR = 1;

    private int mTime;
    private String mBackgroundImg;
    private String mTargetImg;
    private Sound mTargetSound;
    private ArrayList<Sound> mDistractSounds;
    private int mMode;

    public GamePlayParams() {
        mTime = 0;
        mBackgroundImg = "";
        mTargetImg = "";
        mTargetSound = null;
        mDistractSounds = null;
        mMode = MODE_TOUCH;
    }

    public GamePlayParams(int time,
                          String backgroundImg,
                          String targetImg,
                          Sound targetSound,
                          ArrayList<Sound> distractSounds,
                          int mode) {
        mTime = time;
        mBackgroundImg = backgroundImg;
        mTargetImg = targetImg;
        mTargetSound = targetSound;
        mDistractSounds = distractSounds;
        mMode = mode;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int mTime) {
        this.mTime = mTime;
    }

    public String getBackgroundImg() {
        return mBackgroundImg;
    }

    public void setBackgroundImg(String mBackgroundImg) {
        this.mBackgroundImg = mBackgroundImg;
    }

    public String getTargetImg() {
        return mTargetImg;
    }

    public void setTargetImg(String mTargetImg) {
        this.mTargetImg = mTargetImg;
    }

    public Sound getTargetSound() {
        return mTargetSound;
    }

    public void setTargetSound(Sound mTargetSound) {
        this.mTargetSound = mTargetSound;
    }

    public ArrayList<Sound> getDistractSounds() {
        return mDistractSounds;
    }

    public void setDistractSounds(ArrayList<Sound> mDistractSounds) {
        this.mDistractSounds = mDistractSounds;
    }

    public void setMode(int mode) {
        mMode = mode;
    }

    public int getMode() {
        return mMode;
    }
}
