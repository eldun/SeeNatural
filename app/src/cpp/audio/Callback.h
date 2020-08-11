//
// Created by Ev on 8/10/2020.
//

#ifndef SEENATURAL_CALLBACK_H
#define SEENATURAL_CALLBACK_H

#include "oboe/Oboe.h"


class Callback : public oboe::AudioStreamCallback {
public:
    oboe::DataCallbackResult
    onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames);

};

#endif //SEENATURAL_CALLBACK_H
