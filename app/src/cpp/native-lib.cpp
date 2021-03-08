//
// Created by Ev on 8/5/2020.
//

#include <jni.h>
#include <string>

#include <android/log.h>

#include "audio/OboeSinePlayer.h"
#include "audio/StreamManager.h"
#include "audio/Callback.h"
#include "utils/logging.h"

// parselib includes
#include <stream/MemInputStream.h>
#include <wav/WavStreamReader.h>

// iolib includes
#include <player/OneShotSampleSource.h>
#include <player/SimpleMultiPlayer.h>

static const char* LOG_TAG = "native-lib";

extern "C" {

static iolib::SimpleMultiPlayer simpleMultiPlayer;


//JNIEXPORT jstring JNICALL
//
//Java_com_dunneev_seenatural_Activities_Clef_ClefActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_setupAudioStreamNative(JNIEnv *env,
                                                                                       jobject thiz,
                                                                                       jint num_channels,
                                                                                       jint sample_rate) {
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s", "init()");
    simpleMultiPlayer.setupAudioStream(num_channels, sample_rate);
}


JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_teardownAudioStreamNative(
        JNIEnv *env, jobject thiz) {

    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s", "deinit()");
    simpleMultiPlayer.teardownAudioStream();
}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_loadWavAssetNative(JNIEnv *env,
                                                                                   jobject thiz,
                                                                                   jbyteArray data_bytes) {
    int len = env->GetArrayLength(data_bytes);

    unsigned char *buf = new unsigned char[len];
    env->GetByteArrayRegion(data_bytes, 0, len, reinterpret_cast<jbyte *>(buf));

    parselib::MemInputStream stream(buf, len);

    parselib::WavStreamReader reader(&stream);
    reader.parse();

    iolib::SampleBuffer *sampleBuffer = new iolib::SampleBuffer();
    sampleBuffer->loadSampleData(&reader);

    iolib::OneShotSampleSource *source = new iolib::OneShotSampleSource(sampleBuffer, 0.0);
    simpleMultiPlayer.addSampleSource(source, sampleBuffer);

    delete[] buf;
}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_unloadWavAssetsNative(JNIEnv *env,
                                                                                      jobject thiz) {
    simpleMultiPlayer.unloadSampleData();
}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_triggerDown(JNIEnv *env,
                                                                            jobject thiz,
                                                                            jint piano_key) {
    simpleMultiPlayer.triggerDown(piano_key);
}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_triggerUp(JNIEnv *env, jobject thiz,
                                                                          jint piano_key) {
//    simpleMultiPlayer.triggerUp(piano_key);
}

JNIEXPORT jboolean JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_getOutputReset(JNIEnv *env,
                                                                               jobject thiz) {
    return simpleMultiPlayer.getOutputReset();
}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_clearOutputReset(JNIEnv *env,
                                                                                 jobject thiz) {
    simpleMultiPlayer.clearOutputReset();
}

JNIEXPORT void JNICALL
Java_com_dunneev_seenatural_Utilities_SoundPlayer_restartStream(JNIEnv *env,
                                                                              jobject thiz) {
    simpleMultiPlayer.resetAll();
    if (simpleMultiPlayer.openStream()){
        __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "openStream successful");
    } else {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "openStream failed");
    }
}

}