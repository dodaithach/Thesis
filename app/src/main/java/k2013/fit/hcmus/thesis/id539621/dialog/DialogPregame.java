package k2013.fit.hcmus.thesis.id539621.dialog;

import android.view.View;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by cpu60011-local on 08/05/2017.
 */

public class DialogPregame extends BaseDialog {
    @Override
    protected int getBackground() {
        return 0;
    }

    @Override
    protected int getImg() {
        return R.drawable.d_pregame_icon_music;
    }

    @Override
    protected int getMsg() {
        return R.string.d_pregame_msg;
    }

    @Override
    protected int getBtnActionText() {
        return R.string.d_pregame_btn_action;
    }

    @Override
    protected int getBtnCancelText() {
        return R.string.d_pregame_btn_cancel;
    }

    @Override
    protected View.OnClickListener getAction() {
        return null;
    }
}
