package k2013.fit.hcmus.thesis.id539621.service;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import k2013.fit.hcmus.thesis.id539621.broadcast_receiver.MusicLocalBroadcastReceiver;

/**
 * Created by Trieu on 4/7/2017.
 */

public class MusicPlayerService extends Service {
    public static final int MODE_NOT_LOOP = 0;
    public static final int MODE_LOOP_ONE = 1;
    public static final int MODE_LOOP_ALL = 2;
    public static final int TOTAL_MODE = 3;

    public static final int MSG_WHAT = 1111;

    public class MusicBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

    private boolean mIsStarted = false;

    private MediaPlayer mMediaPlayer;
    private final IBinder mMusicBinder = new MusicBinder();

    int mLoopMode = MODE_LOOP_ONE;

    private List<File> mSoundList;
    private int mCurrentSoundIdx = 0;
    private boolean mIsPaused = false;
    private int mPausePosition = 0;

    private final Handler mAlarm = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.d("mylog", "MusicPlayerService.mAlarm.handleMessage()");

            if (msg.what == MSG_WHAT) {
                Log.d("mylog", "MusicPlayerService.stopSelf()");

                stopSelf();

                Intent intent = new Intent(MusicLocalBroadcastReceiver.ACTION);
                intent.putExtra(MusicLocalBroadcastReceiver.TAG_CLOSE, MusicLocalBroadcastReceiver.VALUE_CLOSE);

                LocalBroadcastManager.getInstance(MusicPlayerService.this).sendBroadcast(intent);
            }
        }
    };

    @Override
    public void onCreate(){
        super.onCreate();

        Log.d("mylog", "MusicPlayerService.onCreate()");

        if (!mIsStarted) {
            mMediaPlayer = new MediaPlayer();
            mSoundList = getListFiles(new File(Environment.getExternalStorageDirectory() + "/TinnitusRelief/Sounds"));

            mIsStarted = true;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("mylog", "MusicPlayerService.onStartCommand()");

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("mylog", "MusicPlayerService.onBind()");

        return mMusicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.d("mylog", "MusicPlayerService.onUnBind()");

        if (mIsPaused) {
            stopSelf();
        }

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("mylog", "MusicPlayerService.onDestroy()");

        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();

        if(files == null){
            files = parentDir.listFiles();
            if(files == null){
                return inFiles;
            }
        }

        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".ogg")){
                    inFiles.add(file);
                }
            }
        }

        return inFiles;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MEDIA PLAYER
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLoopMode(int mode) {
        mLoopMode = mode;
    }

    public int getLoopMode() {
        return mLoopMode;
    }

    public void play(final int idx) {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying() && mCurrentSoundIdx == idx)
                return;

            String fileName = mSoundList.get(idx).getPath();

            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(fileName);
                mMediaPlayer.prepare();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d("mylog", "MediaPlayer.OnCompletionListener");

                        switch (mLoopMode){
                            case MusicPlayerService.MODE_LOOP_ONE:
                                play(mCurrentSoundIdx);
                                break;

                            case MusicPlayerService.MODE_LOOP_ALL: {
                                playNext();

                                Intent intent = new Intent(MusicLocalBroadcastReceiver.ACTION);
                                intent.putExtra(MusicLocalBroadcastReceiver.TAG_UPDATE, mCurrentSoundIdx);

                                LocalBroadcastManager.getInstance(MusicPlayerService.this).sendBroadcast(intent);

                                break;
                            }

                            case MusicPlayerService.MODE_NOT_LOOP: {
                                Intent intent = new Intent(MusicLocalBroadcastReceiver.ACTION);

                                if (mCurrentSoundIdx == mSoundList.size() - 1) {
                                    mMediaPlayer.stop();
                                    mCurrentSoundIdx = 0;
                                    intent.putExtra(MusicLocalBroadcastReceiver.TAG_UPDATE, -1);
                                } else {
                                    playNext();
                                    intent.putExtra(MusicLocalBroadcastReceiver.TAG_UPDATE, mCurrentSoundIdx);
                                }

                                LocalBroadcastManager.getInstance(MusicPlayerService.this).sendBroadcast(intent);
                            }

                            default:
                                break;
                        }
                    }
                });

                if (mIsPaused && mCurrentSoundIdx == idx) {
                    mMediaPlayer.seekTo(mPausePosition);
                }

                mCurrentSoundIdx = idx;

                mMediaPlayer.start();

                mPausePosition = 0;
                mIsPaused = false;

            } catch (Exception e) {

            }
        }
    }

    public void pause() {
        mIsPaused = true;
        mMediaPlayer.pause();

        mPausePosition = mMediaPlayer.getCurrentPosition();
    }

    public boolean getIsPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public int getCurrentSoundIdx() {
        return mCurrentSoundIdx;
    }

    public void playNext() {
        if (mSoundList.size() > 0) {
            int newIdx = (mCurrentSoundIdx + 1) % mSoundList.size();

            pause();
            play(newIdx);
        }
    }

    public void playPrev() {
        if (mSoundList.size() > 0) {
            int newIdx = (mCurrentSoundIdx - 1 + mSoundList.size()) % mSoundList.size();

            pause();
            play(newIdx);
        }
    }

    public void updateTimer(int minutes) {
        Log.d("mylog", "updateTimer(): " + minutes);

        mAlarm.removeMessages(MSG_WHAT);

        if (minutes > 0) {
            mAlarm.sendEmptyMessageDelayed(MSG_WHAT, minutes * 60 * 1000);
        }
    }
}
