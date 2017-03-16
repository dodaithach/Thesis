#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_k2013_fit_hcmus_thesis_id539621_activities_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
