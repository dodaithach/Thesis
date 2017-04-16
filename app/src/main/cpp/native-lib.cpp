#include <jni.h>
#include <string>

#include "openalsoft/include/AL/al.h"
#include "openalsoft/include/AL/alc.h"
#include "BinauralSound.h"

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_openDevice(
        JNIEnv *env,
        jobject /* this */) {
    BinauralSound::getInstance().openDevice();
}

extern "C"
JNIEXPORT jint JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_addSource(
        JNIEnv *env,
        jobject /* this */, jstring filename) {

    const char *nativeFilename = env->GetStringUTFChars(filename, 0);

    ALuint source = BinauralSound::getInstance().addSource(nativeFilename);

    env->ReleaseStringUTFChars(filename,nativeFilename);

    return source;
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_setPosition(
        JNIEnv *env,
        jobject /* this */, int source, float x, float y, float z) {
    BinauralSound::getInstance().setPosition(source,x,y,z);

}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_setLoop(
        JNIEnv *env,
        jobject /* this */, int source, jboolean isLoop) {

    BinauralSound::getInstance().setLoop(source,(bool)(isLoop == JNI_TRUE));

}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_playSound(
        JNIEnv *env,
        jobject /* this */, int source) {
    BinauralSound::getInstance().playSound(source);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_pauseSound(
        JNIEnv *env,
        jobject /* this */, int source) {
    BinauralSound::getInstance().pauseSound(source);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_setListenerOrientation(
        JNIEnv *env,
        jobject /* this */, float atX, float atY, float atZ, float upX, float upY,
                            float upZ) {
    BinauralSound::getInstance().setListenerOrientation(atX, atY, atZ, upX, upY, upZ);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_closeDevice(
        JNIEnv *env,
        jobject /* this */) {
    BinauralSound::getInstance().closeDevice();
}


extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_BinauralSound_testSound(
        JNIEnv *env,
        jobject /* this */) {
    BinauralSound::getInstance().testSound();
}