package k2013.fit.hcmus.thesis.id539621.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.adapter.MusicRecyclerAdapter;
import k2013.fit.hcmus.thesis.id539621.adapter.MusicRecyclerItemClickListener;
import k2013.fit.hcmus.thesis.id539621.dialog.DialogMusicTimer;
import k2013.fit.hcmus.thesis.id539621.sound.SoundManager;

public class MusicActivity extends BaseActivity implements DialogMusicTimer.TimerChangedListener,
        MusicRecyclerItemClickListener.OnRecyclerItemClickListener{

    private Button mBtnPlayPause;
    private Button mBtnPrev;
    private Button mBtnNext;
    private Button mBtnTimer;
    private Button mBtnRepeat;
    private RecyclerView mRecyclerView;

    private SoundManager soundManager;
    private Intent playIntent;
    //binding
    private boolean musicBound=false;


    private boolean mIsPlaying = false;
    private boolean mIsRepeatOne = false;

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

        String[] fakeData = {"Remember the name", "Running", "Despacito"};
        MusicRecyclerAdapter adapter = new MusicRecyclerAdapter(fakeData);
        mRecyclerView.addOnItemTouchListener(new MusicRecyclerItemClickListener(MusicActivity.this,
                                                                                mRecyclerView,
                                                                                MusicActivity.this));

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, SoundManager.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);

            startService(playIntent);
            Log.d("Trieu", "playIntent null");
        }
        Log.d("Trieu","ok");
    }

    public void musicOnClick(View v) {
        switch (v.getId()) {
            case R.id.music_btnPlayPause: {
                mIsPlaying = !mIsPlaying;

                if (musicBound && soundManager!= null) {
                    if (mIsPlaying) {
                        soundManager.play();
                        mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);
                    } else {
                        soundManager.pause();
                        mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_play);
                    }
                }

                break;
            }

            case R.id.music_btnPrev: {
                soundManager.playPrevSoundTrack();
                break;
            }

            case R.id.music_btnNext: {
                soundManager.playNextSoundTrack();
                break;
            }

            case R.id.music_btnTimer: {
                DialogMusicTimer dialog = new DialogMusicTimer();
                dialog.show(getSupportFragmentManager(), DialogMusicTimer.TAG);

                break;
            }

            case R.id.music_btnRepeat: {
                mIsRepeatOne = !mIsRepeatOne;

                if (mIsRepeatOne) {

                    mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_one);
                } else {
                    mBtnRepeat.setBackgroundResource(R.drawable.a_music_btn_repeat_all);
                }

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

        if (adapter.getSelectedIdx() != position) {
            adapter.setSelectedIdx(position);
            adapter.notifyDataSetChanged();

            // Change music
        }
    }

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SoundManager.MusicBinder binder = (SoundManager.MusicBinder)service;
            //get service
            soundManager = binder.getService();
            musicBound = true;
            if(soundManager == null){
                Log.d("Trieu", "sound manager null");
            } else {
                Log.d("Trieu", "Sound manager not null");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(musicConnection);
    }
}
