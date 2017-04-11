package k2013.fit.hcmus.thesis.id539621.model;

/**
 * Created by cpu60011-local on 10/04/2017.
 */

public class Position {
    private double mX;
    private double mY;
    private double mZ;

    public Position() {}

    public Position(double x, double y, double z) {
        mX = x;
        mY = y;
        mZ = z;
    }

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
}
