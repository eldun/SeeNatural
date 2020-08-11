//
// Created by Ev on 8/10/2020.
//

#include "Callback.h"
#include "../utils/logging.h"

oboe::DataCallbackResult Callback::onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) {

    // We requested AudioFormat::Float so we assume we got it.
    // For production code always check what format
    // the stream has and cast to the appropriate type.
    auto *outputData = static_cast<float *>(audioData);

    // Generate random numbers (white noise) centered around zero.
    const float amplitude = 0.2f;
    for (int i = 0; i < numFrames; ++i){
        outputData[i] = ((float)drand48() - 0.5f) * 2 * amplitude;
    }

    return oboe::DataCallbackResult::Continue;
}