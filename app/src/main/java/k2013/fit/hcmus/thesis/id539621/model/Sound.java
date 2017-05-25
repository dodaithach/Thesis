package k2013.fit.hcmus.thesis.id539621.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cpu60011-local on 10/04/2017.
 */

public class Sound implements Parcelable {
    public static final int TYPE_INFINITE = 0;
    public static final int TYPE_REPEAT = 1;

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

    protected Sound(Parcel in) {
        mSoundPath = in.readString();
        mRepeatTime = in.readInt();
        mType = in.readInt();
        mPosition = in.readParcelable(Position.class.getClassLoader());
    }

    public static final Creator<Sound> CREATOR = new Creator<Sound>() {
        @Override
        public Sound createFromParcel(Parcel in) {
            return new Sound(in);
        }

        @Override
        public Sound[] newArray(int size) {
            return new Sound[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSoundPath);
        parcel.writeInt(mRepeatTime);
        parcel.writeInt(mType);
        parcel.writeParcelable(mPosition, i);
    }
}
