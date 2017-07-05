package k2013.fit.hcmus.thesis.id539621.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.adapter.GameSelectionRecyclerAdapter;
import k2013.fit.hcmus.thesis.id539621.model.GameLevel;
import k2013.fit.hcmus.thesis.id539621.dialog.DialogHelper;

public class GameSelectionActivity extends BaseActivity {

    private GameSelectionRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_gameselection);


        String intentString = getIntent().getStringExtra("GameLevels");

        Gson gson = new Gson();
        GameLevel[] levels = gson.fromJson(intentString,GameLevel[].class);

        mRecyclerView = (RecyclerView) findViewById(R.id.gameselection_recyclerview);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(false);

        mAdapter = new GameSelectionRecyclerAdapter(levels);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void gameSelectionOnClick(View v) {
        switch (v.getId()) {
            case R.id.gameselection_btn_close: {
                finish();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case DialogHelper.REQ_CODE_DIALOG_GAME_SUCCESS:
                    int res = data.getIntExtra(DialogHelper.RES_TITLE, DialogHelper.RES_CODE_CANCEL);
                    if (res == RESULT_CANCELED) {
                        finish();
                    }
                    break;
            }
        }
    }
}
