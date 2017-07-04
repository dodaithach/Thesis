package k2013.fit.hcmus.thesis.id539621.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.sound.SoundManager;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay;
    private Button btnPause;
    private Button btnPrev;
    private Button btnNext;

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        btnPlay = (Button)findViewById(R.id.music_btn_play);
        btnPause = (Button)findViewById(R.id.music_btn_pause);
        btnNext = (Button)findViewById(R.id.music_btn_next);
        btnPrev = (Button)findViewById(R.id.music_btn_prev);

        soundManager = new SoundManager();
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.music_btn_play:
                soundManager.play();
                break;
            case R.id.music_btn_pause:
                soundManager.pause();
                break;
            case R.id.music_btn_prev:
                soundManager.playPrevSoundTrack();
                break;
            case R.id.music_btn_next:
                soundManager.playNextSoundTrack();
        }
    }
}
