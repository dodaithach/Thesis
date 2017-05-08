package k2013.fit.hcmus.thesis.id539621.dialog;

import android.view.View;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by thachdo on 5/7/2017.
 */

public class DialogGameSuccess extends BaseDialog {
    @Override
    protected int getBackground() {
        return R.drawable.d_gamesuccess_background;
    }

    @Override
    protected int getImg() {
        return R.drawable.test;
    }

    @Override
    protected int getMsg() {
        return R.string.d_gamesuccess_msg;
    }

    @Override
    protected int getBtnActionText() {
        return R.string.d_gamesuccess_btn_action;
    }

    @Override
    protected View.OnClickListener getAction() {
        return null;
    }
}
