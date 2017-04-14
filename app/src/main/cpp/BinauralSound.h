//
// Created by Trieu on 28/3/2017.
//

#ifndef THESIS_BINAURALSOUND_H
#define THESIS_BINAURALSOUND_H


#include "openalsoft/include/AL/al.h"
#include "openalsoft/include/AL/alc.h"
#include <stdio.h>
#include <string>

class BinauralSound {
private:
    ALCdevice* device;
    ALCcontext* context;
    BinauralSound() {}
    BinauralSound(BinauralSound const&);
    void operator=(BinauralSound const&);
public:
    void openDevice();
    ALuint addSource(std::string filename);
    void setPosition(ALuint source,float x, float y, float z);
    void setLoop(ALuint source, bool isLoop);
    void playSound(ALuint source);
    void pauseSound(ALuint source);
    void setListenerOrientation(float atX, float atY, float atZ, float upX, float upY, float upZ);
    void closeDevice();

    void testSound();

    static BinauralSound& getInstance()
    {
        static BinauralSound instance; // Guaranteed to be destroyed.
        // Instantiated on first use.
        return instance;
    }
};


#endif //THESIS_BINAURALSOUND_H
