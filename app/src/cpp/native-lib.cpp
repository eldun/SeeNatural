//
// Created by Ev on 8/5/2020.
//

#include <jni.h>
#include <string>

#include "OboeSinePlayer.h"

extern "C" {
JNIEXPORT jstring JNICALL

Java_com_dunneev_seenatural_Activities_Clef_ClefActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Activities_Clef_ClefActivity_startOboeSineWave(JNIEnv *env,
                                                                           jobject thiz) {
    new OboeSinePlayer();
}
}