//
// Created by Trieu on 28/3/2017.
//

#include <stdio.h>
#include <memory.h>
#include <android/log.h>

#include "BinauralSound.h"


typedef struct {
    char  riff[4];//'RIFF'
    unsigned int riffSize;
    char  wave[4];//'WAVE'
    char  fmt[4];//'fmt '
    unsigned int fmtSize;
    unsigned short format;
    unsigned short channels;
    unsigned int samplesPerSec;
    unsigned int bytesPerSec;
    unsigned short blockAlign;
    unsigned short bitsPerSample;
    char  data[4];//'data'
    unsigned int dataSize;
}BasicWAVEHeader;

//WARNING: This Doesn't Check To See If These Pointers Are Valid
char* readWAV(char* filename,BasicWAVEHeader* header){
    char* buffer = 0;
    FILE* file = fopen(filename,"rb");
    if (!file) {
        return 0;
    }

    if (fread(header,sizeof(BasicWAVEHeader),1,file)){
        if (!(//these things *must* be valid with this basic header
                memcmp("RIFF",header->riff,4) ||
                memcmp("WAVE",header->wave,4) ||
                memcmp("fmt ",header->fmt,4)  ||
                memcmp("data",header->data,4)
        )){

            buffer = (char*)malloc(header->dataSize);
            if (buffer){
                if (fread(buffer,header->dataSize,1,file)){
                    fclose(file);
                    return buffer;
                }
                free(buffer);
            }
        }
    }
    fclose(file);
    return 0;
}

ALuint createBufferFromWave(char* data,BasicWAVEHeader header){

    ALuint buffer = 0;
    ALuint format = 0;
    switch (header.bitsPerSample){
        case 8:
            format = (header.channels == 1) ? AL_FORMAT_MONO8 : AL_FORMAT_STEREO8;
            break;
        case 16:
            format = (header.channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
            break;
        default:
            return 0;
    }

    alGenBuffers(1,&buffer);
    alBufferData(buffer,format,data,header.dataSize,header.samplesPerSec);
    return buffer;
}

void BinauralSound::openDevice() {
    const ALint context_attribs[] = { ALC_FREQUENCY, 22050, 0 };

    // Initialization
    device = alcOpenDevice(0);
    context = alcCreateContext(device, context_attribs);
    alcMakeContextCurrent(context);

    // set listener to (0,0,0)
    alListener3f(AL_POSITION, 0.0, 0.0, 0.0);
    alListener3f(AL_VELOCITY, 0.0, 0.0, 0.0);
}

ALuint BinauralSound::addSource(std::string filename) {
    ALuint buffer;
    BasicWAVEHeader header;
    char* data = readWAV(strdup(filename.c_str()),&header);

    if (data){
        //Now We've Got A Wave In Memory, Time To Turn It Into A Usable Buffer
        buffer = createBufferFromWave(data,header);
        if (!buffer){
            free(data);
            return 0;
        }

    } else {
        return 0;
    }

    // Create source from buffer and play it
    ALuint source = 0;
    alGenSources(1, &source );
    alSourcei(source, AL_BUFFER, buffer);
    return source;
}

void BinauralSound::setPosition(ALuint source,float x, float y, float z){
    __android_log_print(ANDROID_LOG_DEBUG, "setPosition", "source: %d", source);
    alSource3f(source, AL_POSITION, x, y, z);
}

void BinauralSound::setLoop(ALuint source, bool isLoop){
    if(isLoop){
        alSourcei(source, AL_LOOPING, AL_TRUE);
    } else {
        alSourcei(source, AL_LOOPING, AL_FALSE);
    }
}

void BinauralSound::playSound(ALuint source){
    alSourcePlay(source);
}

void BinauralSound::pauseSound(ALuint source){
    alSourcePause(source);
}

void BinauralSound::setListenerOrientation(float atX, float atY, float atZ, float upX, float upY,
                                           float upZ) {
    float listenerOri[6];
    listenerOri[0] = atX;
    listenerOri[1] = atY;
    listenerOri[2] = atZ;
    listenerOri[3] = upX;
    listenerOri[4] = upY;
    listenerOri[5] = upZ;

    alListenerfv(AL_ORIENTATION, listenerOri);
}

bool BinauralSound::isPlayingSound(ALuint source){
    int sourceState = AL_PLAYING;
    alGetSourcei(source, AL_SOURCE_STATE, &sourceState);
    return (sourceState == AL_PLAYING);
};

void BinauralSound::closeDevice(){
    device = alcGetContextsDevice(context);
    alcMakeContextCurrent(NULL);
    alcDestroyContext(context);
    alcCloseDevice(device);

}

bool isX = true;

void BinauralSound::testSound(){
    ALint context_attribs[] = { ALC_FREQUENCY, 22050, 0 };

    // Initialization
    ALCdevice* myDevice = alcOpenDevice(0);
    ALCcontext* myContext = alcCreateContext(myDevice, context_attribs);
    alcMakeContextCurrent(myContext);

    // set listener to (0,0,0)
    alListener3f(AL_POSITION, 0.0, 0.0, 0.0);
    alListener3f(AL_VELOCITY, 0.0, 0.0, 0.0);

    ALuint sound = addSource("/sdcard/tone.wav");


    if(isX){
        alSource3f(sound, AL_POSITION, 10, 0, 0);
    }
    else {
        alSource3f(sound, AL_POSITION, -10, 0, 0);
    }
    isX = !isX;
    alSourcePlay(sound);


}