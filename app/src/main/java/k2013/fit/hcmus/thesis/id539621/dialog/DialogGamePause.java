package k2013.fit.hcmus.thesis.id539621.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by cpu60011-local on 08/05/2017.
 */

public class DialogGamePause extends BaseDialog {
    @Override
    protected int getBackground() {
        return 0;
    }

    @Override
    protected int getImg() {
        return R.drawable.d_gamepause_icon_pause;
    }

    @Override
    protected int getMsg() {
        return R.string.d_gamepause_msg;
    }

    @Override
    protected int getBtnActionText() {
        return R.string.d_gamepause_btn_action;
    }

    @Override
    protected int getBtnCancelText() {
        return 0;
    }

    @Override
    protected View.OnClickListener getAction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(DialogHelper.RES_TITLE, DialogHelper.RES_CODE_ACTION);

                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    getParent().setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        };
    }
}
