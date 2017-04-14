package k2013.fit.hcmus.thesis.id539621.sound;

/**
 * Created by Trieu on 22/3/2017.
 */

public class BinauralSound {

    static {
        System.loadLibrary("native-lib");
    }

    public native void openDevice();
    public native int addSource(String filename);
    public native void setPosition(int source, float x, float y, float z);
    public native void setLoop(int source, boolean isLoop);
    public native void playSound(int source);
    public native void pauseSound(int source);
    public native void setListenerOrientation(float atX, float atY, float atZ, float upX, float up, float upZ);
    public native void closeDevice();

    public native void testSound();
}
