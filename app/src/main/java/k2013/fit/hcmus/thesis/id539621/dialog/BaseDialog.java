package k2013.fit.hcmus.thesis.id539621.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.activity.BaseActivity;

/**
 * Created by thachdo on 5/7/2017.
 */

public abstract class BaseDialog extends BaseActivity {
    private View mBackground;
    private ImageView mImg;
    private TextView mMsg;
    private Button mBtnCancel;
    private Button mBtnAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_template);

        mBackground = findViewById(R.id.dialog_rootView);
        mImg = (ImageView) findViewById(R.id.dialog_img);
        mMsg = (TextView) findViewById(R.id.dialog_title);
        mBtnCancel = (Button) findViewById(R.id.dialog_btn_cancel);
        mBtnAction = (Button) findViewById(R.id.dialog_btn_action);

        mBackground.setBackgroundResource(getBackground());
        mImg.setBackgroundResource(getImg());
        mMsg.setText(getMsg());
        mBtnAction.setOnClickListener(getAction());

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(DialogHelper.RES_TITLE, DialogHelper.RES_CODE_CANCEL);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    protected abstract int getBackground();
    protected abstract int getImg();
    protected abstract int getMsg();
    protected abstract View.OnClickListener getAction();
}
