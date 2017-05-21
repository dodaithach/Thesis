package k2013.fit.hcmus.thesis.id539621.sound;

/**
 * Created by Trieu on 14/5/2017.
 */


public class OpenAL {
    static {
        System.loadLibrary("native-lib-openal");
    }

    public static final int AL_NONE = 1;
    public static final int AL_FALSE = 0;
    public static final int AL_TRUE = 1;
    public static final int AL_SOURCE_RELATIVE = 0x202;
    public static final int AL_CONE_INNER_ANGLE = 0x1001;
    public static final int AL_CONE_OUTER_ANGLE = 0x1002;
    public static final int AL_PITCH = 0x1003;

    /**
     * Source or listener position.
     * Type:    ALfloat[3], ALint[3]
     * Default: {0, 0, 0}
     * <p>
     * The source or listener location in three dimensional space.
     * <p>
     * OpenAL, like OpenGL, uses a right handed coordinate system, where in a
     * frontal default view X (thumb) points right, Y points up (index finger), and
     * Z points towards the viewer/camera (middle finger).
     * <p>
     * To switch from a left handed coordinate system, flip the sign on the Z
     * coordinate.
     */
    public static final int AL_POSITION = 0x1004;

    /**
     * Source direction.
     * Type:    ALfloat[3], ALint[3]
     * Default: {0, 0, 0}
     * <p>
     * Specifies the current direction in local space.
     * A zero-length vector specifies an omni-directional source (cone is ignored).
     */
    public static final int AL_DIRECTION = 0x1005;

    /**
     * Source or listener velocity.
     * Type:    ALfloat[3], ALint[3]
     * Default: {0, 0, 0}
     * <p>
     * Specifies the current velocity in local space.
     */
    public static final int AL_VELOCITY = 0x1006;

    /**
     * Source looping.
     * Type:    ALboolean
     * Range:   [AL_TRUE, AL_FALSE]
     * Default: AL_FALSE
     * <p>
     * Specifies whether source is looping.
     */
    public static final int AL_LOOPING = 0x1007;

    /**
     * Source buffer.
     * Type:  ALuint
     * Range: any valid Buffer.
     * <p>
     * Specifies the buffer to provide sound samples.
     */
    public static final int AL_BUFFER = 0x1009;

    /**
     * Source or listener gain.
     * Type:  ALfloat
     * Range: [0.0 - ]
     * <p>
     * A value of 1.0 means unattenuated. Each division by 2 equals an attenuation
     * of about -6dB. Each multiplicaton by 2 equals an amplification of about
     * +6dB.
     * <p>
     * A value of 0.0 is meaningless with respect to a logarithmic scale; it is
     * silent.
     */
    public static final int AL_GAIN = 0x100A;

    /**
     * Minimum source gain.
     * Type:  ALfloat
     * Range: [0.0 - 1.0]
     * <p>
     * The minimum gain allowed for a source, after distance and cone attenation is
     * applied (if applicable).
     */
    public static final int AL_MIN_GAIN = 0x100D;

    /**
     * Maximum source gain.
     * Type:  ALfloat
     * Range: [0.0 - 1.0]
     * <p>
     * The maximum gain allowed for a source, after distance and cone attenation is
     * applied (if applicable).
     */
    public static final int AL_MAX_GAIN = 0x100E;

    /**
     * Listener orientation.
     * Type: ALfloat[6]
     * Default: {0.0, 0.0, -1.0, 0.0, 1.0, 0.0}
     * <p>
     * Effectively two three dimensional vectors. The first vector is the front (or
     * "at") and the second is the top (or "up").
     * <p>
     * Both vectors are in local space.
     */
    public static final int AL_ORIENTATION = 0x100F;

    /**
     * Source state (query only).
     * Type:  ALint
     * Range: [AL_INITIAL, AL_PLAYING, AL_PAUSED, AL_STOPPED]
     */
    public static final int AL_SOURCE_STATE = 0x1010;

    /**
     * Source state value.
     */
    public static final int AL_INITIAL = 0x1011;
    public static final int AL_PLAYING = 0x1012;
    public static final int AL_PAUSED = 0x1013;
    public static final int AL_STOPPED = 0x1014;

    /**
     * Source Buffer Queue size (query only).
     * Type: ALint
     * <p>
     * The number of buffers queued using alSourceQueueBuffers, minus the buffers
     * removed with alSourceUnqueueBuffers.
     */
    public static final int AL_BUFFERS_QUEUED = 0x1015;

    /**
     * Source Buffer Queue processed count (query only).
     * Type: ALint
     * <p>
     * The number of queued buffers that have been fully processed, and can be
     * removed with alSourceUnqueueBuffers.
     * <p>
     * Looping sources will never fully process buffers because they will be set to
     * play again for when the source loops.
     */
    public static final int AL_BUFFERS_PROCESSED = 0x1016;

    /**
     * Source reference distance.
     * Type:    ALfloat
     * Range:   [0.0 - ]
     * Default: 1.0
     * <p>
     * The distance in units that no attenuation occurs.
     * <p>
     * At 0.0, no distance attenuation ever occurs on non-linear attenuation models.
     */
    public static final int AL_REFERENCE_DISTANCE = 0x1020;

    /**
     * Source rolloff factor.
     * Type:    ALfloat
     * Range:   [0.0 - ]
     * Default: 1.0
     * <p>
     * Multiplier to exaggerate or diminish distance attenuation.
     * <p>
     * At 0.0, no distance attenuation ever occurs.
     */
    public static final int AL_ROLLOFF_FACTOR = 0x1021;

    /**
     * Outer cone gain.
     * Type:    ALfloat
     * Range:   [0.0 - 1.0]
     * Default: 0.0
     * <p>
     * The gain attenuation applied when the listener is outside of the source's
     * outer cone.
     */
    public static final int AL_CONE_OUTER_GAIN = 0x1022;

    /**
     * Source maximum distance.
     * Type:    ALfloat
     * Range:   [0.0 - ]
     * Default: +inf
     * <p>
     * The distance above which the source is not attenuated any further with a
     * clamped distance model, or where attenuation reaches 0.0 gain for linear
     * distance models with a default rolloff factor.
     */
    public static final int AL_MAX_DISTANCE = 0x1023;

    /**
     * Source buffer position, in seconds
     */
    public static final int AL_SEC_OFFSET = 0x1024;
    /**
     * Source buffer position, in sample frames
     */
    public static final int AL_SAMPLE_OFFSET = 0x1025;
    /**
     * Source buffer position, in bytes
     */
    public static final int AL_BYTE_OFFSET = 0x1026;

    /**
     * Source type (query only).
     * Type:  ALint
     * Range: [AL_STATIC, AL_STREAMING, AL_UNDETERMINED]
     * <p>
     * A Source is Static if a Buffer has been attached using AL_BUFFER.
     * <p>
     * A Source is Streaming if one or more Buffers have been attached using
     * alSourceQueueBuffers.
     * <p>
     * A Source is Undetermined when it has the NULL buffer attached using
     * AL_BUFFER.
     */
    public static final int AL_SOURCE_TYPE = 0x1027;

    /**
     * Source type value.
     */
    public static final int AL_STATIC = 0x1028;
    public static final int AL_STREAMING = 0x1029;
    public static final int AL_UNDETERMINED = 0x1030;

    /**
     * Buffer format specifier.
     */
    public static final int AL_FORMAT_MONO8 = 0x1100;
    public static final int AL_FORMAT_MONO16 = 0x1101;
    public static final int AL_FORMAT_STEREO8 = 0x1102;
    public static final int AL_FORMAT_STEREO16 = 0x1103;

    /**
     * Buffer frequency (query only).
     */
    public static final int AL_FREQUENCY = 0x2001;
    /**
     * Buffer bits per sample (query only).
     */
    public static final int AL_BITS = 0x2002;
    /**
     * Buffer channel count (query only).
     */
    public static final int AL_CHANNELS = 0x2003;
    /**
     * Buffer data size (query only).
     */
    public static final int AL_SIZE = 0x2004;

    /**
     * Buffer state.
     * <p>
     * Not for public use.
     */
    public static final int AL_UNUSED = 0x2010;
    public static final int AL_PENDING = 0x2011;
    public static final int AL_PROCESSED = 0x2012;


    /**
     * No error.
     */
    public static final int AL_NO_ERROR = 0;

    /**
     * Invalid name paramater passed to AL call.
     */
    public static final int AL_INVALID_NAME = 0xA001;

    /**
     * Invalid enum parameter passed to AL call.
     */
    public static final int AL_INVALID_ENUM = 0xA002;

    /**
     * Invalid value parameter passed to AL call.
     */
    public static final int AL_INVALID_VALUE = 0xA003;

    /**
     * Illegal AL call.
     */
    public static final int AL_INVALID_OPERATION = 0xA004;
    /**
     * Not enough memory.
     */
    public static final int AL_OUT_OF_MEMORY = 0xA005;


    /**
     * Context string: Vendor ID.
     */
    public static final int AL_VENDOR = 0xB001;
    /**
     * Context string: Version.
     */
    public static final int AL_VERSION = 0xB002;
    /**
     * Context string: Renderer ID.
     */
    public static final int AL_RENDERER = 0xB003;
    /**
     * Context string: Space-separated extension list.
     */
    public static final int AL_EXTENSIONS = 0xB004;


    /**
     * Doppler scale.
     * Type:    ALfloat
     * Range:   [0.0 - ]
     * Default: 1.0
     *
     * Scale for source and listener velocities.
     */
    public static final int AL_DOPPLER_FACTOR = 0xC000;
    public native void alDopplerFactor(float value);

/**
 * Doppler velocity (deprecated).
 *
 * A multiplier applied to the Speed of Sound.
 */
public static final int AL_DOPPLER_VELOCITY = 0xC001;
    public native void alDopplerVelocity(float value);

/**
 * Speed of Sound, in units per second.
 * Type:    ALfloat
 * Range:   [0.0001 - ]
 * Default: 343.3
 *
 * The speed at which sound waves are assumed to travel, when calculating the
 * doppler effect.
 */
public static final int AL_SPEED_OF_SOUND = 0xC003;
    public native void alSpeedOfSound(float value);

/**
 * Distance attenuation model.
 * Type:    ALint
 * Range:   [AL_NONE, AL_INVERSE_DISTANCE, AL_INVERSE_DISTANCE_CLAMPED,
 *           AL_LINEAR_DISTANCE, AL_LINEAR_DISTANCE_CLAMPED,
 *           AL_EXPONENT_DISTANCE, AL_EXPONENT_DISTANCE_CLAMPED]
 * Default: AL_INVERSE_DISTANCE_CLAMPED
 *
 * The model by which sources attenuate with distance.
 *
 * None     - No distance attenuation.
 * Inverse  - Doubling the distance halves the source gain.
 * Linear   - Linear gain scaling between the reference and max distances.
 * Exponent - Exponential gain dropoff.
 *
 * Clamped variations work like the non-clamped counterparts, except the
 * distance calculated is clamped between the reference and max distances.
 */
public static final int AL_DISTANCE_MODEL = 0xD000;
     public native void alDistanceModel(int distanceModel);

/** Distance model value. */
public static final int AL_INVERSE_DISTANCE = 0xD001;
            public static final int AL_INVERSE_DISTANCE_CLAMPED = 0xD002;
            public static final int AL_LINEAR_DISTANCE = 0xD003;
            public static final int AL_LINEAR_DISTANCE_CLAMPED = 0xD004;
            public static final int AL_EXPONENT_DISTANCE = 0xD005;
            public static final int AL_EXPONENT_DISTANCE_CLAMPED = 0xD006;

/** Renderer State management. */
     public native void alEnable(int capability);
     public native void alDisable(int capability);
     public native boolean alIsEnabled(int capability);

/** State retrieval. */
     public native String alGetString(int param);
//     public native void alGetBooleanv(int param, Boolean values);
//     public native void alGetIntegerv(int param, Integer values);
//     public native void alGetFloatv(int param, Float values);
//     public native void alGetDoublev(int param, Double values);
     public native boolean alGetBoolean(int param);
     public native int alGetInteger(int param);
     public native float alGetFloat(int param);
     public native double alGetDouble(int param);

    /**
     * Error retrieval.
     *
     * Obtain the first error generated in the AL context since the last check.
     */
     public native int alGetError();

    /**
     * Extension support.
     *
     * Query for the presence of an extension, and obtain any appropriate function
     * pointers and enum values.
     */
//     public native ALboolean alIsExtensionPresent(const ALchar *extname);
//     //public native void* alGetProcAddress(const ALchar *fname);
//     public native ALenum alGetEnumValue(const ALchar *ename);
//
//
///** Set Listener parameters */
//     public native void alListenerf(ALenum param, ALfloat value);
//     public native void alListener3f(ALenum param, ALfloat value1, ALfloat value2, ALfloat value3);
//     public native void alListenerfv(ALenum param, const ALfloat *values);
//     public native void alListeneri(ALenum param, ALint value);
//     public native void alListener3i(ALenum param, ALint value1, ALint value2, ALint value3);
//     public native void alListeneriv(ALenum param, const ALint *values);
//
///** Get Listener parameters */
//     public native void alGetListenerf(ALenum param, ALfloat *value);
//     public native void alGetListener3f(ALenum param, ALfloat *value1, ALfloat *value2, ALfloat *value3);
//     public native void alGetListenerfv(ALenum param, ALfloat *values);
//     public native void alGetListeneri(ALenum param, ALint *value);
//     public native void alGetListener3i(ALenum param, ALint *value1, ALint *value2, ALint *value3);
//     public native void alGetListeneriv(ALenum param, ALint *values);
//
//
///** Create Source objects. */
//     void alGenSources(ALsizei n, ALuint *sources);
///** Delete Source objects. */
//     void alDeleteSources(ALsizei n, const ALuint *sources);
//    /** Verify a handle is a valid Source. */
//     ALboolean alIsSource(ALuint source);
//
///** Set Source parameters. */
//     void alSourcef(ALuint source, ALenum param, ALfloat value);
//     void alSource3f(ALuint source, ALenum param, ALfloat value1, ALfloat value2, ALfloat value3);
//     void alSourcefv(ALuint source, ALenum param, const ALfloat *values);
//     void alSourcei(ALuint source, ALenum param, ALint value);
//     void alSource3i(ALuint source, ALenum param, ALint value1, ALint value2, ALint value3);
//     void alSourceiv(ALuint source, ALenum param, const ALint *values);
//
///** Get Source parameters. */
//     void alGetSourcef(ALuint source, ALenum param, ALfloat *value);
//     void alGetSource3f(ALuint source, ALenum param, ALfloat *value1, ALfloat *value2, ALfloat *value3);
//     void alGetSourcefv(ALuint source, ALenum param, ALfloat *values);
//     void alGetSourcei(ALuint source,  ALenum param, ALint *value);
//     void alGetSource3i(ALuint source, ALenum param, ALint *value1, ALint *value2, ALint *value3);
//     void alGetSourceiv(ALuint source,  ALenum param, ALint *values);
//
//
///** Play, replay, or resume (if paused) a list of Sources */
//     void alSourcePlayv(ALsizei n, const ALuint *sources);
///** Stop a list of Sources */
//     void alSourceStopv(ALsizei n, const ALuint *sources);
///** Rewind a list of Sources */
//     void alSourceRewindv(ALsizei n, const ALuint *sources);
///** Pause a list of Sources */
//     void alSourcePausev(ALsizei n, const ALuint *sources);
//
///** Play, replay, or resume a Source */
//     void alSourcePlay(ALuint source);
///** Stop a Source */
//     void alSourceStop(ALuint source);
///** Rewind a Source (set playback postiton to beginning) */
//     void alSourceRewind(ALuint source);
///** Pause a Source */
//     void alSourcePause(ALuint source);
//
///** Queue buffers onto a source */
//     void alSourceQueueBuffers(ALuint source, ALsizei nb, const ALuint *buffers);
///** Unqueue processed buffers from a source */
//     void alSourceUnqueueBuffers(ALuint source, ALsizei nb, ALuint *buffers);
//
//
///** Create Buffer objects */
//     void alGenBuffers(ALsizei n, ALuint *buffers);
///** Delete Buffer objects */
//     void alDeleteBuffers(ALsizei n, const ALuint *buffers);
//    /** Verify a handle is a valid Buffer */
//     ALboolean alIsBuffer(ALuint buffer);
//
///** Specifies the data to be copied into a buffer */
//     void alBufferData(ALuint buffer, ALenum format, const ALvoid *data, ALsizei size, ALsizei freq);
//
///** Set Buffer parameters, */
//     void alBufferf(ALuint buffer, ALenum param, ALfloat value);
//     void alBuffer3f(ALuint buffer, ALenum param, ALfloat value1, ALfloat value2, ALfloat value3);
//     void alBufferfv(ALuint buffer, ALenum param, const ALfloat *values);
//     void alBufferi(ALuint buffer, ALenum param, ALint value);
//     void alBuffer3i(ALuint buffer, ALenum param, ALint value1, ALint value2, ALint value3);
//     void alBufferiv(ALuint buffer, ALenum param, const ALint *values);
//
///** Get Buffer parameters. */
//     void alGetBufferf(ALuint buffer, ALenum param, ALfloat *value);
//     void alGetBuffer3f(ALuint buffer, ALenum param, ALfloat *value1, ALfloat *value2, ALfloat *value3);
//     void alGetBufferfv(ALuint buffer, ALenum param, ALfloat *values);
//     void alGetBufferi(ALuint buffer, ALenum param, ALint *value);
//     void alGetBuffer3i(ALuint buffer, ALenum param, ALint *value1, ALint *value2, ALint *value3);
//     void alGetBufferiv(ALuint buffer, ALenum param, ALint *values);
}
