//
// Created by Ev on 8/9/2020.
//

#ifndef SEENATURAL_STREAMMANAGER_H
#define SEENATURAL_STREAMMANAGER_H

#include "oboe/Oboe.h"

extern oboe::ManagedStream managedStream;

class StreamManager {
public:
    bool buildStream();

private:
    // Stream params
    static int constexpr kChannelCount = 2;
    static int constexpr kSampleRate = 48000;
};

#endif //SEENATURAL_STREAMMANAGER_H
