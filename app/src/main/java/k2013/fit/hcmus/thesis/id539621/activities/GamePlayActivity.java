package k2013.fit.hcmus.thesis.id539621.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.custom.HandlerSingleton;
import com.custom.OnScrollCallback;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.game_operation.GameOperation;
import k2013.fit.hcmus.thesis.id539621.game_operation.GamePlayParams;

public class GamePlayActivity extends MD360PlayerActivity implements OnScrollCallback{
    private View mPointer;

    private GameOperation mGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPointer = findViewById(R.id.gameplay_pointer);
        mPointer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mGame.finish(GamePlayActivity.this);
                return false;
            }
        });

        HandlerSingleton.init(this, null);

        GamePlayParams params = new GamePlayParams();
        params.setTime(5000);
        mGame = new GameOperation(this, params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGame.init();
    }

    protected void onResume() {
        super.onResume();
        mGame.resume(this);
    }

    protected void onPause() {
        super.onPause();
        mGame.pause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGame.destroy();
    }

    float roll = 0.0f, pitch = 0.0f;
    float delX = 0.0f, delY = 0.0f;
    @Override
    public void customOnScroll(float velocityX, float velocityY) {

        delX = delX - ((int)velocityX) / Resources.getSystem().getDisplayMetrics().density * 0.2f;
        delY = delY - ((int)velocityY) / Resources.getSystem().getDisplayMetrics().density * 0.2f;

        roll = delX - ((int)(delX/360))*360;
        pitch = delY - ((int)(delY/360))*360;
    }

    /*************************************** GAMEPLAY FUNCTIONS ***********************************/
    public void timeTick() {
        Log.d("mylog", "timeTick()");
    }

    public void timeFinish() {
        Log.d("mylog", "timeFinish()");
        mGame.stop(this);
    }
}