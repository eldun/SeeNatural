//
// Created by Ev on 8/9/2020.
//

#include "StreamManager.h"
#include "Callback.h"
#include "../utils/logging.h"

oboe::ManagedStream managedStream;
Callback callback;

bool StreamManager::buildStream(){
    oboe::AudioStreamBuilder builder;

    // The builder set methods can be chained for convenience.
    builder.setSharingMode(oboe::SharingMode::Exclusive)
            ->setPerformanceMode(oboe::PerformanceMode::LowLatency)
            ->setChannelCount(kChannelCount)
            ->setSampleRate(kSampleRate)
            ->setFormat(oboe::AudioFormat::Float)
            ->setCallback(&callback)
            ->openManagedStream(managedStream);
    oboe::Result result = managedStream->requestStart();
    if (result != oboe::Result::OK){
        LOGE("Failed to start stream. Error: %s", convertToText(result));
        return false;
    }

    LOGI("AudioStream started successfully.");
    return true;
}