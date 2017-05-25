package k2013.fit.hcmus.thesis.id539621.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cpu60011-local on 10/04/2017.
 */

public class Position implements Parcelable {
    private double mX;
    private double mY;
    private double mZ;

    public Position() {}

    public Position(double x, double y, double z) {
        mX = x;
        mY = y;
        mZ = z;
    }

    protected Position(Parcel in) {
        mX = in.readDouble();
        mY = in.readDouble();
        mZ = in.readDouble();
    }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    public double getX() {
        return mX;
    }

    public void setX(double mX) {
        this.mX = mX;
    }

    public double getY() {
        return mY;
    }

    public void setY(double mY) {
        this.mY = mY;
    }

    public double getZ() {
        return mZ;
    }

    public void setZ(double mZ) {
        this.mZ = mZ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(mX);
        parcel.writeDouble(mY);
        parcel.writeDouble(mZ);
    }
}
