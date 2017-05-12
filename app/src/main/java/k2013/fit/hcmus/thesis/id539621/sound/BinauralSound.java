package k2013.fit.hcmus.thesis.id539621.sound;

import k2013.fit.hcmus.thesis.id539621.model.Position;

/**
 * Created by Trieu on 22/3/2017.
 */

public class BinauralSound {

    static {
        System.loadLibrary("native-lib");
    }

    public static void setPosition(int source, Position p){
        setPosition(source, (float)p.getX(), (float)p.getY(), (float)p.getZ());
    }

    public static native void openDevice();
    public static native int addSource(String filename);
    public static native void setPosition(int source, float x, float y, float z);
    public static native void setLoop(int source, boolean isLoop);
    public static native void playSound(int source);
    public static native void pauseSound(int source);
    public static native void setListenerOrientation(float atX, float atY, float atZ, float upX, float up, float upZ);
    public static native boolean isPlayingSound(int source);
    public static native void clearAll();
    public static native void closeDevice();

    public native void testSound();
}
