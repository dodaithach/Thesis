//
// Created by Trieu on 14/5/2017.
//

#include <jni.h>
#include <string>

#include "openalsoft/include/AL/al.h"
#include "openalsoft/include/AL/alc.h"

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDopplerFactor(
        JNIEnv *env,
        float value) {
    //alDopplerFactor(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDopplerVelocity(
        JNIEnv *env,
        float value) {
    //alDopplerVelocity(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alSpeedOfSound(
        JNIEnv *env,
        float value) {
    //alDopplerVelocity(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDistanceModel(
        JNIEnv *env,
        int value) {
    //alDistanceModel(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alEnable(
        JNIEnv *env,
        int capability) {
    //alEnable(capability);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDisable(
        JNIEnv *env,
        int capability) {
    //alDisable(capability);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alIsEnabled(
        JNIEnv *env,
        int capability) {
//    if (alIsEnabled(capability) == AL_TRUE){
//        return JNI_TRUE;
//    }
    return JNI_FALSE;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetString(
        JNIEnv *env,
        int param) {
//    const char* alString = alGetString(param);
    jstring jstrBuf = (env)->NewStringUTF("");
    return jstrBuf;
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetBoolean(
        JNIEnv *env,
        int param) {
//    ALboolean temp = alGetBoolean(param);
//    if(temp == AL_TRUE){
//        return JNI_TRUE;
//    }
    return JNI_FALSE;
}

extern "C"
JNIEXPORT int JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetInteger(
        JNIEnv *env,
        int param) {
    //return alGetInteger(param);
    return 0;
}

extern "C"
JNIEXPORT jfloat JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetFloat(
        JNIEnv *env,
        int param) {
    //return alGetFloat(param);
    return 0;
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetDouble(
        JNIEnv *env,
        int param) {
    //return alGetDouble(param);
    return 0;
}

extern "C"
JNIEXPORT int JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetError(
        JNIEnv *env) {
    //return alGetError();
    return 0;
}