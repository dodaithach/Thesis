package k2013.fit.hcmus.thesis.id539621.sound;

/**
 * Created by Trieu on 14/5/2017.
 */


public class OpenAL {
 static {
 System.loadLibrary("native-lib-openal");
 }

 public static int AL_NONE = 0;

/** Boolean False. */
 public static int AL_FALSE = 0;

/** Boolean True. */
 public static int AL_TRUE = 1;


/**
 * Relative source.
 * Type: ALboolean
 * Range: [AL_TRUE, AL_FALSE]
 * Default: AL_FALSE
 *
 * Specifies if the Source has relative coordinates.
 */
public static int AL_SOURCE_RELATIVE = 0x202;


/**
 * Inner cone angle, in degrees.
 * Type: int, float
 * Range: [0 - 360]
 * Default: 360
 *
 * The angle covered by the inner cone, where the source will not attenuate.
 */
public static int AL_CONE_INNER_ANGLE = 0x1001;

/**
 * Outer cone angle, in degrees.
 * Range: [0 - 360]
 * Default: 360
 *
 * The angle covered by the outer cone, where the source will be fully
 * attenuated.
 */
public static int AL_CONE_OUTER_ANGLE = 0x1002;

/**
 * Source pitch.
 * Type: float
 * Range: [0.5 - 2.0]
 * Default: 1.0
 *
 * A multiplier for the frequency (sample rate) of the source's buffer.
 */
public static int AL_PITCH = 0x1003;

/**
 * Source or listener position.
 * Type: float[3], int[3]
 * Default: {0, 0, 0}
 *
 * The source or listener location in three dimensional space.
 *
 * OpenAL, like OpenGL, uses a right handed coordinate system, where in a
 * frontal default view X (thumb) points right, Y points up (index finger), and
 * Z points towards the viewer/camera (middle finger).
 *
 * To switch from a left handed coordinate system, flip the sign on the Z
 * coordinate.
 */
public static int AL_POSITION = 0x1004;

/**
 * Source direction.
 * Type: float[3], int[3]
 * Default: {0, 0, 0}
 *
 * Specifies the current direction in local space.
 * A zero-length vector specifies an omni-directional source (cone is ignored).
 */
public static int AL_DIRECTION = 0x1005;

/**
 * Source or listener velocity.
 * Type: float[3], int[3]
 * Default: {0, 0, 0}
 *
 * Specifies the current velocity in local space.
 */
public static int AL_VELOCITY = 0x1006;

/**
 * Source looping.
 * Type: ALboolean
 * Range: [AL_TRUE, AL_FALSE]
 * Default: AL_FALSE
 *
 * Specifies whether source is looping.
 */
public static int AL_LOOPING = 0x1007;

/**
 * Source buffer.
 * Type: int
 * Range: any valid Buffer.
 *
 * Specifies the buffer to provide sound samples.
 */
public static int AL_BUFFER = 0x1009;

/**
 * Source or listener gain.
 * Type: float
 * Range: [0.0 - ]
 *
 * A value of 1.0 means unattenuated. Each division by 2 equals an attenuation
 * of about -6dB. Each multiplicaton by 2 equals an amplification of about
 * +6dB.
 *
 * A value of 0.0 is meaningless with respect to a logarithmic scale; it is
 * silent.
 */
public static int AL_GAIN = 0x100A;

/**
 * Minimum source gain.
 * Type: float
 * Range: [0.0 - 1.0]
 *
 * The minimum gain allowed for a source, after distance and cone attenation is
 * applied (if applicable).
 */
public static int AL_MIN_GAIN = 0x100D;

/**
 * Maximum source gain.
 * Type: float
 * Range: [0.0 - 1.0]
 *
 * The maximum gain allowed for a source, after distance and cone attenation is
 * applied (if applicable).
 */
public static int AL_MAX_GAIN = 0x100E;

/**
 * Listener orientation.
 * Type: float[6]
 * Default: {0.0, 0.0, -1.0, 0.0, 1.0, 0.0}
 *
 * Effectively two three dimensional vectors. The first vector is the front (or
 * "at") and the second is the top (or "up").
 *
 * Both vectors are in local space.
 */
public static int AL_ORIENTATION = 0x100F;

/**
 * Source state (query only).
 * Type: int
 * Range: [AL_INITIAL, AL_PLAYING, AL_PAUSED, AL_STOPPED]
 */
public static int AL_SOURCE_STATE = 0x1010;

/** Source state value. */
public static int AL_INITIAL = 0x1011;
 public static int AL_PLAYING = 0x1012;
 public static int AL_PAUSED = 0x1013;
 public static int AL_STOPPED = 0x1014;

/**
 * Source Buffer Queue size (query only).
 * Type: int
 *
 * The number of buffers queued using alSourceQueueBuffers, minus the buffers
 * removed with alSourceUnqueueBuffers.
 */
public static int AL_BUFFERS_QUEUED = 0x1015;

/**
 * Source Buffer Queue processed count (query only).
 * Type: int
 *
 * The number of queued buffers that have been fully processed, and can be
 * removed with alSourceUnqueueBuffers.
 *
 * Looping sources will never fully process buffers because they will be set to
 * play again for when the source loops.
 */
public static int AL_BUFFERS_PROCESSED = 0x1016;

/**
 * Source reference distance.
 * Type: float
 * Range: [0.0 - ]
 * Default: 1.0
 *
 * The distance in units that no attenuation occurs.
 *
 * At 0.0, no distance attenuation ever occurs on non-linear attenuation models.
 */
public static int AL_REFERENCE_DISTANCE = 0x1020;

/**
 * Source rolloff factor.
 * Type: float
 * Range: [0.0 - ]
 * Default: 1.0
 *
 * Multiplier to exaggerate or diminish distance attenuation.
 *
 * At 0.0, no distance attenuation ever occurs.
 */
public static int AL_ROLLOFF_FACTOR = 0x1021;

/**
 * Outer cone gain.
 * Type: float
 * Range: [0.0 - 1.0]
 * Default: 0.0
 *
 * The gain attenuation applied when the listener is outside of the source's
 * outer cone.
 */
 public static int AL_CONE_OUTER_GAIN = 0x1022;

/**
 * Source maximum distance.
 * Type: float
 * Range: [0.0 - ]
 * Default: +inf
 *
 * The distance above which the source is not attenuated any further with a
 * clamped distance model, or where attenuation reaches 0.0 gain for linear
 * distance models with a default rolloff factor.
 */
public static int AL_MAX_DISTANCE = 0x1023;

/** Source buffer position, in seconds */
public static int AL_SEC_OFFSET = 0x1024;
/** Source buffer position, in sample frames */
public static int AL_SAMPLE_OFFSET = 0x1025;
/** Source buffer position, in bytes */
public static int AL_BYTE_OFFSET = 0x1026;

/**
 * Source type (query only).
 * Type: int
 * Range: [AL_STATIC, AL_STREAMING, AL_UNDETERMINED]
 *
 * A Source is Static if a Buffer has been attached using AL_BUFFER.
 *
 * A Source is Streaming if one or more Buffers have been attached using
 * alSourceQueueBuffers.
 *
 * A Source is Undetermined when it has the NULL buffer attached using
 * AL_BUFFER.
 */
public static int AL_SOURCE_TYPE = 0x1027;

/** Source type value. */
public static int AL_STATIC = 0x1028;
 public static int AL_STREAMING = 0x1029;
 public static int AL_UNDETERMINED = 0x1030;

/** Buffer format specifier. */
public static int AL_FORMAT_MONO8 = 0x1100;
 public static int AL_FORMAT_MONO16 = 0x1101;
 public static int AL_FORMAT_STEREO8 = 0x1102;
 public static int AL_FORMAT_STEREO16 = 0x1103;

/** Buffer frequency (query only). */
public static int AL_FREQUENCY = 0x2001;
/** Buffer bits per sample (query only). */
public static int AL_BITS = 0x2002;
/** Buffer channel count (query only). */
public static int AL_CHANNELS = 0x2003;
/** Buffer data size (query only). */
public static int AL_SIZE = 0x2004;

/**
 * Buffer state.
 *
 * Not for public use.
 */
 public static int AL_UNUSED = 0x2010;
    public static int AL_PENDING = 0x2011;
    public static int AL_PROCESSED = 0x2012;


/** No error. */
public static int AL_NO_ERROR = 0;

/** Invalid name paramater passed to AL call. */
public static int AL_INVALID_NAME = 0xA001;

/** Invalid enum parameter passed to AL call. */
public static int AL_INVALID_ENUM = 0xA002;

/** Invalid value parameter passed to AL call. */
public static int AL_INVALID_VALUE = 0xA003;

/** Illegal AL call. */
 public static int AL_INVALID_OPERATION = 0xA004;

/** Not enough memory. */
 public static int AL_OUT_OF_MEMORY = 0xA005;


/** Context string: Vendor ID. */
 public static int AL_VENDOR = 0xB001;
/** Context string: Version. */
 public static int AL_VERSION = 0xB002;
/** Context string: Renderer ID. */
 public static int AL_RENDERER = 0xB003;
/** Context string: Space-separated extension list. */
 public static int AL_EXTENSIONS = 0xB004;


/**
 * Doppler scale.
 * Type: float
 * Range: [0.0 - ]
 * Default: 1.0
 *
 * Scale for source and listener velocities.
 */
 public static int AL_DOPPLER_FACTOR = 0xC000;
public native void  alDopplerFactor(float value);

/**
 * Doppler velocity (deprecated).
 *
 * A multiplier applied to the Speed of Sound.
 */
public static int AL_DOPPLER_VELOCITY = 0xC001;
    public native void alDopplerVelocity(float value);

/**
 * Speed of Sound, in units per second.
 * Type: float
 * Range: [0.0001 - ]
 * Default: 343.3
 *
 * The speed at which sound waves are assumed to travel, when calculating the
 * doppler effect.
 */
public static int AL_SPEED_OF_SOUND = 0xC003;
 public native void alSpeedOfSound(float value);

/**
 * Distance attenuation model.
 * Type: int
 * Range: [AL_NONE, AL_INVERSE_DISTANCE, AL_INVERSE_DISTANCE_CLAMPED,
 * AL_LINEAR_DISTANCE, AL_LINEAR_DISTANCE_CLAMPED,
 * AL_EXPONENT_DISTANCE, AL_EXPONENT_DISTANCE_CLAMPED]
 * Default: AL_INVERSE_DISTANCE_CLAMPED
 *
 * The model by which sources attenuate with distance.
 *
 * None - No distance attenuation.
 * Inverse - Doubling the distance halves the source gain.
 * Linear - Linear gain scaling between the reference and max distances.
 * Exponent - Exponential gain dropoff.
 *
 * Clamped variations work like the non-clamped counterparts, except the
 * distance calculated is clamped between the reference and max distances.
 */
public static int AL_DISTANCE_MODEL = 0xD000;
 public native void alDistanceModel(int distanceModel);

/** Distance model value. */
public static int AL_INVERSE_DISTANCE = 0xD001;
 public static int AL_INVERSE_DISTANCE_CLAMPED = 0xD002;
 public static int AL_LINEAR_DISTANCE = 0xD003;
 public static int AL_LINEAR_DISTANCE_CLAMPED = 0xD004;
 public static int AL_EXPONENT_DISTANCE = 0xD005;
 public static int AL_EXPONENT_DISTANCE_CLAMPED = 0xD006;

/** Renderer State management. */
 public native void alEnable(int capability);
 public native void alDisable(int capability);
 public native boolean alIsEnabled(int capability);

/** State retrieval. */
 public native String alGetString(int param);
 //public native void alGetBooleanv(int param, ALboolean *values);
 //public native void alGetIntegerv(int param, int *values);
 //public native void alGetFloatv(int param, float *values);
 //public native void alGetDoublev(int param, ALdouble *values);
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
 public native boolean alIsExtensionPresent(String extname);
 //public native void* alGetProcAddress(String fname);
 public native int alGetEnumValue(String ename);


/** Set Listener parameters */
 public native void alListenerf(int param, float value);
 public native void alListener3f(int param, float value1, float value2, float value3);
 public native void alListenerfv(int param, float[] values);
 public native void alListeneri(int param, int value);
 public native void alListener3i(int param, int value1, int value2, int value3);
 public native void alListeneriv(int param, int[] values);

/** Get Listener parameters */
// public native void alGetListenerf(int param, float[] value);
// public native void alGetListener3f(int param, float value1, float value2, float value3);
// public native void alGetListenerfv(int param, float[] values);
// public native void alGetListeneri(int param, int[] value);
// public native void alGetListener3i(int param, int value1, int value2, int value3);
// public native void alGetListeneriv(int param, int[] values);


/** Create Source objects. */
 public native void alGenSources(int n, int[] sources);
/** Delete Source objects. */
 public native void alDeleteSources(int n, int[] sources);
 /** Verify a handle is a valid Source. */
 public native boolean alIsSource(int source);
//
///** Set Source parameters. */
// public native void alSourcef(int source, int param, float value);
// public native void alSource3f(int source, int param, float value1, float value2, float value3);
// public native void alSourcefv(int source, int param, float[] values);
// public native void alSourcei(int source, int param, int value);
// public native void alSource3i(int source, int param, int value1, int value2, int value3);
// public native void alSourceiv(int source, int param, int[] values);
//
///** Get Source parameters. */
// //public native void alGetSourcef(int source, int param, float *value);
// //public native void alGetSource3f(int source, int param, float *value1, float *value2, float *value3);
// //public native void alGetSourcefv(int source, int param, float *values);
// //public native void alGetSourcei(int source, int param, int *value);
// //public native void alGetSource3i(int source, int param, int *value1, int *value2, int *value3);
// //public native void alGetSourceiv(int source, int param, int *values);
//
//
///** Play, replay, or resume (if paused) a list of Sources */
// public native void alSourcePlayv(int n, int[] sources);
///** Stop a list of Sources */
// public native void alSourceStopv(int n, int[] sources);
///** Rewind a list of Sources */
// public native void alSourceRewindv(int n, int[] sources);
///** Pause a list of Sources */
// public native void alSourcePausev(int n, int[] sources);
//
///** Play, replay, or resume a Source */
// public native void alSourcePlay(int source);
///** Stop a Source */
// public native void alSourceStop(int source);
///** Rewind a Source (set playback postiton to beginning) */
// public native void alSourceRewind(int source);
///** Pause a Source */
// public native void alSourcePause(int source);
//
///** Queue buffers onto a source */
// public native void alSourceQueueBuffers(int source, int nb, int[] buffers);
///** Unqueue processed buffers from a source */
// public native void alSourceUnqueueBuffers(int source, int nb, int[] buffers);
//
//
///** Create Buffer objects */
// public native void alGenBuffers(int n, int[] buffers);
///** Delete Buffer objects */
// public native void alDeleteBuffers(int n, int[] buffers);
// /** Verify a handle is a valid Buffer */
// public native boolean alIsBuffer(int buffer);
//
///** Specifies the data to be copied into a buffer */
// //public native void alBufferData(int buffer, int format, const ALvoid *data, ALsizei size, ALsizei freq);
//
///** Set Buffer parameters, */
// public native void alBufferf(int buffer, int param, float value);
// public native void alBuffer3f(int buffer, int param, float value1, float value2, float value3);
// public native void alBufferfv(int buffer, int param, float[] values);
// public native void alBufferi(int buffer, int param, int value);
// public native void alBuffer3i(int buffer, int param, int value1, int value2, int value3);
// public native void alBufferiv(int buffer, int param, int[] values);
//
///** Get Buffer parameters. */
//// public native void alGetBufferf(int buffer, int param, float *value);
//// public native void alGetBuffer3f(int buffer, int param, float *value1, float *value2, float *value3);
//// public native void alGetBufferfv(int buffer, int param, float *values);
//// public native void alGetBufferi(int buffer, int param, int *value);
//// public native void alGetBuffer3i(int buffer, int param, int *value1, int *value2, int *value3);
//// public native void alGetBufferiv(int buffer, int param, int *values);


}
