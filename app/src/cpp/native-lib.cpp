//
// Created by Ev on 8/5/2020.
//

#include <jni.h>
#include <string>

#include "audio/OboeSinePlayer.h"
#include "audio/StreamManager.h"
#include "audio/Callback.h"
#include "utils/logging.h"

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

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Activities_Clef_ClefActivity_startStreamManager(JNIEnv *env,
                                                                           jobject thiz) {
    StreamManager streamManager;
    streamManager.buildStream();
}
}