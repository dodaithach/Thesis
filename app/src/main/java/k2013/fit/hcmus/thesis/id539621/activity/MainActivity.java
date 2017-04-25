package k2013.fit.hcmus.thesis.id539621.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = (TextView) findViewById(R.id.main_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/pacifico-regular.ttf");
        title.setTypeface(tf);
    }

    public void mainOnClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_play: {
                Intent intent = new Intent(this, GameSelectionActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.main_btn_help: {

                break;
            }

            default:
                break;
        }
    }
}
