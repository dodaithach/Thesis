package k2013.fit.hcmus.thesis.id539621.model;

/**
 * Created by cpu60011-local on 10/04/2017.
 */

public class Position {
    private int mX;
    private int mY;
    private int mZ;

    public Position() {}

    public Position(int x, int y, int z) {
        mX = x;
        mY = y;
        mZ = z;
    }

    public int getX() {
        return mX;
    }

    public void setX(int mX) {
        this.mX = mX;
    }

    public int getY() {
        return mY;
    }

    public void setY(int mY) {
        this.mY = mY;
    }

    public int getZ() {
        return mZ;
    }

    public void setZ(int mZ) {
        this.mZ = mZ;
    }
}
