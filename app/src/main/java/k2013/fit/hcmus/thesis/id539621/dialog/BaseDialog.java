package k2013.fit.hcmus.thesis.id539621.dialog;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        if (getBackground() != 0) {
            mBackground.setBackgroundResource(getBackground());
        }

        if (getImg() != 0) {
            mImg.setBackgroundResource(getImg());
        }

        if (getMsg() != 0) {
            mMsg.setText(getMsg());
        }

        if (getBtnActionText() != 0) {
            mBtnAction.setText(getBtnActionText());
        }

        if (getAction() != null) {
            mBtnAction.setOnClickListener(getAction());
        }

        if (getBtnCancelText() != 0) {
            mBtnCancel.setText(getBtnCancelText());
        }

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(DialogHelper.RES_TITLE, DialogHelper.RES_CODE_CANCEL);

                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    getParent().setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // discard onBackPressed
    }

    protected abstract int getBackground();
    protected abstract int getImg();
    protected abstract int getMsg();
    protected abstract int getBtnActionText();
    protected abstract int getBtnCancelText();
    protected abstract View.OnClickListener getAction();

    protected void updateBtnCancelBackground(int backgroundId) {
        mBtnCancel.setBackgroundResource(backgroundId);
    }

    protected void updateBtnCancelTextColor(ColorStateList colors) {
        mBtnCancel.setTextColor(colors);
    }

    protected void updateBtnActionBackground(int backgroundId) {
        mBtnAction.setBackgroundResource(backgroundId);
    }

    protected void updateBtnActionTextColor(ColorStateList colors) {
        mBtnAction.setTextColor(colors);
    }

    protected void enableBtnCancel(boolean mode) {
        if (mode) {
            mBtnCancel.setClickable(true);
            mBtnCancel.setBackgroundResource(R.drawable.btn_default_cancel);
            ColorStateList btnCancelColors = new ColorStateList(
                    new int[][] {
                            new int[] {android.R.attr.state_pressed},
                            new int[] {}
                    },
                    new int[] {
                            getResources().getColor(R.color.themeLight),
                            getResources().getColor(R.color.themeAccent)
                    }
            );
            mBtnCancel.setTextColor(btnCancelColors);
        } else {
            mBtnCancel.setClickable(false);
            mBtnCancel.setBackgroundResource(R.drawable.btn_disable);
            mBtnCancel.setTextColor(getResources().getColor(R.color.themeLight));
        }
    }

    protected void enableBtnAction(boolean mode) {
        if (mode) {
            mBtnAction.setClickable(true);
            mBtnAction.setBackgroundResource(R.drawable.btn_default_action);
            ColorStateList btnActionColors = new ColorStateList(
                    new int[][] {
                            new int[] {android.R.attr.state_pressed},
                            new int[] {}
                    },
                    new int[] {
                            getResources().getColor(R.color.themeLight),
                            getResources().getColor(R.color.themePrimary)
                    }
            );
            mBtnAction.setTextColor(btnActionColors);
        } else {
            mBtnAction.setClickable(false);
            mBtnAction.setBackgroundResource(R.drawable.btn_disable);
            mBtnAction.setTextColor(getResources().getColor(R.color.themeLight));
        }
    }
}
