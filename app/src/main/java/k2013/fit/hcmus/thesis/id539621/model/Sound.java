package k2013.fit.hcmus.thesis.id539621.model;

/**
 * Created by cpu60011-local on 10/04/2017.
 */

public class Sound {
    public final int TYPE_INFINITE = 0;
    public final int TYPE_REPEAT = 1;

    private String mSoundPath;
    private int mRepeatTime;
    private int mType;
    private Position mPosition;

    public Sound() {
        mSoundPath = "";
        mRepeatTime = 0;
        mType = TYPE_INFINITE;
    }

    public Sound(String soundPath, int repeatTime, int type, Position position) {
        mSoundPath = soundPath;
        mRepeatTime = repeatTime;

        switch (type) {
            case TYPE_INFINITE:
                mType = TYPE_INFINITE;
                break;

            case TYPE_REPEAT:
                mType = TYPE_REPEAT;
                break;

            default:
                mType = TYPE_INFINITE;
        }

        mPosition = position;
    }

    public String getSoundPath() {
        return mSoundPath;
    }

    public void setSoundPath(String mSoundPath) {
        this.mSoundPath = mSoundPath;
    }

    public int getRepeatTime() {
        return mRepeatTime;
    }

    public void setRepeatTime(int mRepeatTime) {
        this.mRepeatTime = mRepeatTime;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public Position getPosition() {
        return mPosition;
    }

    public void setPosition(Position mPosition) {
        this.mPosition = mPosition;
    }
}
