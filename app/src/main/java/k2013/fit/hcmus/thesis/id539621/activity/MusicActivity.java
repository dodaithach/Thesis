package k2013.fit.hcmus.thesis.id539621.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        mRecyclerView.setAdapter(adapter);
    }

    public void musicOnClick(View v) {
        switch (v.getId()) {
            case R.id.music_btnPlayPause: {
                mIsPlaying = !mIsPlaying;

                if (mIsPlaying) {
                    mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_pause);
                } else {
                    mBtnPlayPause.setBackgroundResource(R.drawable.a_music_btn_play);
                }

                break;
            }

            case R.id.music_btnPrev: {

                break;
            }

            case R.id.music_btnNext: {

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

    }
}
