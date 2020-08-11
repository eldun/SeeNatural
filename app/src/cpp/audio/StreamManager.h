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
    // Wave params, these could be instance variables in order to modify at runtime
    static float constexpr kAmplitude = 0.5f;
    static float constexpr kFrequency = 440;
    static float constexpr kPI = M_PI;
    static float constexpr kTwoPi = kPI * 2;
    static double constexpr mPhaseIncrement = kFrequency * kTwoPi / (double) kSampleRate;
    // Keeps track of where the wave is
    float mPhase = 0.0;
};

#endif //SEENATURAL_STREAMMANAGER_H
