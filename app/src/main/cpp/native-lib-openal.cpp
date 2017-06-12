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
    alDopplerFactor(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDopplerVelocity(
        JNIEnv *env,
        float value) {
    alDopplerVelocity(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alSpeedOfSound(
        JNIEnv *env,
        float value) {
    alSpeedOfSound(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDistanceModel(
        JNIEnv *env,
        int value) {
    alDistanceModel(value);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alEnable(
        JNIEnv *env,
        int capability) {
    alEnable(capability);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDisable(
        JNIEnv *env,
        int capability) {
    alDisable(capability);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alIsEnabled(
        JNIEnv *env,
        int capability) {
    if (alIsEnabled(capability) == AL_TRUE){
        return JNI_TRUE;
    }
    return JNI_FALSE;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetString(
        JNIEnv *env,
        int param) {
    const char* alString = alGetString(param);
    jstring jstrBuf = (env)->NewStringUTF(alString);
    return jstrBuf;
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetBoolean(
        JNIEnv *env,
        int param) {
    ALboolean temp = alGetBoolean(param);
    if(temp == AL_TRUE){
        return JNI_TRUE;
    }
    return JNI_FALSE;
}

extern "C"
JNIEXPORT int JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetInteger(
        JNIEnv *env,
        int param) {
    return alGetInteger(param);
}

extern "C"
JNIEXPORT jfloat JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetFloat(
        JNIEnv *env,
        int param) {
    return alGetFloat(param);
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetDouble(
        JNIEnv *env,
        int param) {
    return alGetDouble(param);
}

extern "C"
JNIEXPORT int JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetError(
        JNIEnv *env) {
    return alGetError();
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alIsExtensionPresent(JNIEnv *env, jstring extname){
    const char *nativeString = env->GetStringUTFChars(extname, 0);
    ALboolean temp = alIsExtensionPresent(nativeString);
    env->ReleaseStringUTFChars(extname, nativeString);
    if(temp == AL_TRUE){
        return JNI_TRUE;
    }
    return JNI_FALSE;
}
//public native void* alGetProcAddress(String fname);
extern "C"
JNIEXPORT int JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGetEnumValue(JNIEnv *env, jstring ename){
    const char *nativeString = env->GetStringUTFChars(ename, 0);
    ALenum temp = alGetEnumValue(nativeString);
    env->ReleaseStringUTFChars(ename, nativeString);

    return temp;
}

extern "C"
JNIEXPORT int JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alListenerf(JNIEnv *env, int param, float value){
    alListenerf(param,value);
}
extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alListener3f(JNIEnv *env, int param, float value1, float value2, float value3){
    alListener3f(param, value1, value2, value3);
}
extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alListenerfv(JNIEnv *env, int param, jfloatArray values){
    jfloat* flt1 = env->GetFloatArrayElements( values,0);
    alListenerfv(param,flt1);
    delete(flt1);
}
extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alListeneri(JNIEnv *env, int param, int value){
    alListeneri(param,value);
}
extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alListener3i(JNIEnv *env, int param, int value1, int value2, int value3){
    alListener3i(param,value1,value2,value3);
}
extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alListeneriv(JNIEnv *env, int param, jintArray values){
    jint* temp = env->GetIntArrayElements( values,0);
    alListeneriv(param,temp);
    delete(temp);
}

extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alGenSources(JNIEnv *env, int n, jintArray sources){
    jint* temp = env->GetIntArrayElements( sources,0);
    //alGenSources(n,temp);
}
/** Delete Source objects. */
extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alDeleteSources(int n, jintArray sources){

}
/** Verify a handle is a valid Source. */
extern "C"
JNIEXPORT void JNICALL
Java_k2013_fit_hcmus_thesis_id539621_sound_OpenAL_alIsSource(int source){

}