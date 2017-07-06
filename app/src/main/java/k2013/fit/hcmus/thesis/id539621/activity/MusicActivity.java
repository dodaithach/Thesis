package k2013.fit.hcmus.thesis.id539621.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.adapter.MusicRecyclerAdapter;
import k2013.fit.hcmus.thesis.id539621.adapter.MusicRecyclerItemClickListener;
import k2013.fit.hcmus.thesis.id539621.broadcast_receiver.MusicLocalBroadcastReceiver;
import k2013.fit.hcmus.thesis.id539621.dialog.DialogMusicTimer;
import k2013.fit.hcmus.thesis.id539621.service.MusicPlayerService;

public class MusicActivity extends BaseActivity implements DialogMusicTimer.TimerChangedListener,
        MusicRecyclerItemClickListener.OnRecyclerItemClickListener{

    private Button mBtnPlayPause;
    private Button mBtnPrev;
    private Button mBtnNext;
    private Button mBtnTimer;
    private Button mBtnRepeat;
    private RecyclerView mRecyclerView;

    private MusicPlayerService mPlayerService;
    private boolean mIsBound = false;
    private MusicLocalBroadcastReceiver mReceiver;

    private boolean mIsPlaying = false;
    private int mRepeatMode = MusicPlayerService.MODE_LOOP_ONE;
    private boolean mIsUsingTimer = false;
    private int mTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_music);

        mBtnPlayPause = (Button) findViewById(R.id.music_btnPlayPause);
        mBtnPrev = (Button) findViewById(R.id.music_btnPrev);
        mBtnNext = (Button) findViewById(R.id.music_btnNext);
        mBtnTimer = (Button) findViewById(R.id.music_btnTimer);
        mBtnRepeat = (Button) findViewById(R.id.music_btnRepeat);

        mRecyclerView = (RecyclerView) findViewById(R.id.a_music_recyclerview);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(false);

        List<File> soundList = getListFiles(new File(Environment.getExternalStorageDirectory() +
                                                        "/TinnitusRelief/Sounds"));
        String fileNames[] = new String[soundList.size()];
        for (int i = 0; i < soundList.size(); i++) {
            fileNames[i] = soundList.get(i).getName();
        }

        MusicRecyclerAdapter adapter = new MusicRecyclerAdapter(fileNames);
        mRecyclerView.addOnItemTouchListener(new MusicRecyclerItemClickListener(MusicActivity.this,
                                                                                mRecyclerView,
                                                                                MusicActivity.this));

        mRecyclerView.setAdapter(adapter);

        Intent playerIntent = new Intent(this, MusicPlayerService.class);
        startService(playerIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mIsBound) {
            Intent playerIntent = new Intent(this, MusicPlayerService.class);
            bindService(playerIntent, mMusicConnection, Context.BIND_AUTO_CREATE);
        }

        mReceiver = new MusicLocalBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(MusicLocalBroadcastReceiver.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("mylog", "MusicActivity.onDestroy()");

        unbindService(mMusicConnection);

        if (!mPlayerService.getIsPlaying()) {
            Intent playerIntent = new Intent(this, MusicPlayerService.class);
            stopService(playerIntent);
        }
    }

    public void musicOnClick(View v) {
        switch (v.getId()) {
            case R.id.music_btnPlayPause: {
                mIsPlaying = !mIsPlaying;

                if (mIsPlaying) {
                    mPlayerService.play(mPlayerService.getCurrentSoundIdx());

                    MusicRecyclerAdapter adapter = (MusicRecyclerAdapter) mRecyclerView.getAdapter();
                    adapter.setSelectedIdx(mPlayerService.getCurrentSoundIdx());
                    adapter.notifyDataSetChanged();
                    mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);
                } else {
                    mPlayerService.pause();
                    mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_play);
                }

                break;
            }

            case R.id.music_btnPrev: {
                mIsPlaying = true;
                mPlayerService.playPrev();

                MusicRecyclerAdapter adapter = (MusicRecyclerAdapter) mRecyclerView.getAdapter();
                adapter.setSelectedIdx(mPlayerService.getCurrentSoundIdx());
                adapter.notifyDataSetChanged();
                mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);

                break;
            }

            case R.id.music_btnNext: {
                mIsPlaying = true;
                mPlayerService.playNext();

                MusicRecyclerAdapter adapter = (MusicRecyclerAdapter) mRecyclerView.getAdapter();
                adapter.setSelectedIdx(mPlayerService.getCurrentSoundIdx());
                adapter.notifyDataSetChanged();
                mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);

                break;
            }

            case R.id.music_btnTimer: {
                DialogMusicTimer dialog = new DialogMusicTimer();
                dialog.show(getSupportFragmentManager(), DialogMusicTimer.TAG);

                break;
            }

            case R.id.music_btnRepeat: {
                mRepeatMode = (mRepeatMode + 1) % MusicPlayerService.TOTAL_MODE;

                if (mRepeatMode == MusicPlayerService.MODE_NOT_LOOP) {
                    mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_none);
                } else if (mRepeatMode == MusicPlayerService.MODE_LOOP_ONE){
                    mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_one);
                } else {
                    mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_all);
                }

                mPlayerService.setLoopMode(mRepeatMode);

                break;
            }

            case R.id.music_btnBack: {
                finish();

                break;
            }

            default:
                break;
        }
    }

    public void onReceiveLocalBroadcast(int idx) {
        if (idx == -1) {
            mIsPlaying = false;
            mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_play);
        } else {
            mIsPlaying = true;
            mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);
        }

        MusicRecyclerAdapter adapter = (MusicRecyclerAdapter) mRecyclerView.getAdapter();
        adapter.setSelectedIdx(idx);
        adapter.notifyDataSetChanged();
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

    @Override
    public void setTimer(int time) {
        mTimer = time;
    }

    @Override
    public int getTimer() {
        return mTimer;
    }

    @Override
    public void setTimerState(boolean mode) {
        mIsUsingTimer = mode;
    }

    @Override
    public boolean getTimerState() {
        return mIsUsingTimer;
    }

    @Override
    public void onItemClick(View v, int position) {
        MusicRecyclerAdapter adapter = (MusicRecyclerAdapter) mRecyclerView.getAdapter();

        adapter.setSelectedIdx(position);
        adapter.notifyDataSetChanged();

        // Change music
        mIsPlaying = true;
        mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);
        mPlayerService.play(position);
    }

    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayerService.MusicBinder binder = (MusicPlayerService.MusicBinder)service;

            mPlayerService = binder.getService();
            mIsBound = true;

            MusicRecyclerAdapter adapter = (MusicRecyclerAdapter) mRecyclerView.getAdapter();
            mIsPlaying = mPlayerService.getIsPlaying();

            if (mIsPlaying) {
                adapter.setSelectedIdx(mPlayerService.getCurrentSoundIdx());
                mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);
            } else {
                adapter.setSelectedIdx(-1);
                mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_play);
            }

            adapter.notifyDataSetChanged();

            mRepeatMode = mPlayerService.getLoopMode();

            if (mRepeatMode == MusicPlayerService.MODE_NOT_LOOP) {
                mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_none);
            } else if (mRepeatMode == MusicPlayerService.MODE_LOOP_ONE) {
                mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_one);
            } else {
                mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_all);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}
