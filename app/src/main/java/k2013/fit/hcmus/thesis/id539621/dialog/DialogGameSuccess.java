package k2013.fit.hcmus.thesis.id539621.dialog;

import android.view.View;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by thachdo on 5/7/2017.
 */

public class DialogGameSuccess extends BaseDialog {
    @Override
    protected int getBackground() {
        return R.drawable.rounded_background;
    }

    @Override
    protected int getImg() {
        return R.drawable.unicorn;
    }

    @Override
    protected int getMsg() {
        return R.string.gameplay_popup_msg_success;
    }

    @Override
    protected View.OnClickListener getAction() {
        return null;
    }
}
