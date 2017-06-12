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
}BasicWAVEHeader;

//Chunks
struct chunk_t
{
    char ID[4]; //"data" = 0x61746164
    uint32_t size;  //Chunk data bytes
};

//WARNING: This Doesn't Check To See If These Pointers Are Valid
char* readWAV(char* filename,BasicWAVEHeader* header, chunk_t &chunk){
    char* buffer = 0;
    FILE* file = fopen(filename,"rb");
    if (!file) {
        __android_log_print(ANDROID_LOG_DEBUG, "readWav", "can't read file %s", filename);
        return 0;
    }

    if (fread(header,sizeof(BasicWAVEHeader),1,file)){
        __android_log_print(ANDROID_LOG_DEBUG, "readWav", "file %s checking header", filename);

        if(memcmp("RIFF",header->riff,4)){
            __android_log_print(ANDROID_LOG_DEBUG, "readWav", "file %s header RIFF different: %s", filename, header->riff);
        }
        if(memcmp("WAVE",header->wave,4)){
            __android_log_print(ANDROID_LOG_DEBUG, "readWav", "file %s header WAVE different: %s", filename, header->wave);
        }
        if(memcmp("fmt ",header->fmt,4)){
            __android_log_print(ANDROID_LOG_DEBUG, "readWav", "file %s header FMT different: %s", filename, header->fmt);
        }
//        if(memcmp("data",header->data,4)){
//            __android_log_print(ANDROID_LOG_DEBUG, "readWav", "file %s header DATA different: %s", filename, header->data);
//        }

        if (!(//these things *must* be valid with this basic header
                memcmp("RIFF",header->riff,4) ||
                memcmp("WAVE",header->wave,4) ||
                memcmp("fmt ",header->fmt,4) // ||
                //memcmp("data",header->data,4)
        )){
            __android_log_print(ANDROID_LOG_DEBUG, "unsigned long size", "sizeof(unsigned long): %d", sizeof(chunk.size));
            __android_log_print(ANDROID_LOG_DEBUG, "chunk size", "sizeof(chunk): %d", sizeof(chunk));
            while (true)
            {
                fread(&chunk, sizeof(chunk), 1, file);
                printf("%c%c%c%c\t" "%li\n", chunk.ID[0], chunk.ID[1], chunk.ID[2], chunk.ID[3], chunk.size);
                if (*(unsigned int *)&chunk.ID == 0x61746164)
                    break;
                //skip chunk data bytes
                fseek(file, chunk.size, SEEK_CUR);
            }

            __android_log_print(ANDROID_LOG_DEBUG, "readWav", "file %s header valid", filename);
            buffer = (char*)malloc(chunk.size);
            if (buffer){
                if (fread(buffer,chunk.size,1,file)){
                    fclose(file);
                    return buffer;
                }
                free(buffer);
            }
        } else {
            __android_log_print(ANDROID_LOG_DEBUG, "readWav", "file %s header invalid", filename);
        }
    }
    fclose(file);
    return 0;
}

ALuint createBufferFromWave(char* data,BasicWAVEHeader header, chunk_t chunk){

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
    alBufferData(buffer,format,data,chunk.size,header.samplesPerSec);
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
    chunk_t chunk;
    char* data = readWAV(strdup(filename.c_str()),&header, chunk);

    if (data){
        //Now We've Got A Wave In Memory, Time To Turn It Into A Usable Buffer
        buffer = createBufferFromWave(data,header, chunk);
        if (!buffer){
            __android_log_print(ANDROID_LOG_DEBUG, "addSource", "cant creat buffer from wave");
            free(data);
            return 0;
        }
        __android_log_print(ANDROID_LOG_DEBUG, "addSource", "create ok");

    } else {
        __android_log_print(ANDROID_LOG_DEBUG, "addSource", "no buffer");
        return 0;
    }

    // Create source from buffer and play it
    ALuint source = 0;
    alGenSources(1, &source );
    alSourcei(source, AL_BUFFER, buffer);

    buffers.push_back(buffer);
    sources.push_back(source);

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

void BinauralSound::setVolume(ALuint source, float volume) {
    alSourcef(source, AL_GAIN, volume);
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

void BinauralSound::clearAll() {
    for(int i = 0; i < sources.size(); i++){
        alDeleteSources(1, &sources[i]);
    }

    for(int i = 0; i < buffers.size(); i++){
        alDeleteBuffers(1, &buffers[i]);
    }
}

void BinauralSound::closeDevice(){
    for(int i = 0; i < sources.size(); i++){
        alDeleteSources(1, &sources[i]);
    }

    for(int i = 0; i < buffers.size(); i++){
        alDeleteBuffers(1, &buffers[i]);
    }

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