package k2013.fit.hcmus.thesis.id539621.dialog;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by cpu60011-local on 08/05/2017.
 */

public class DialogGameFailed extends BaseDialog {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateBtnCancelBackground(R.drawable.d_gamefailed_btn_cancel);

        ColorStateList btnCancelColors = new ColorStateList(
                new int[][] {
                        new int[] {android.R.attr.state_pressed},
                        new int[] {}
                },
                new int[] {
                        getResources().getColor(R.color.themeLight),
                        getResources().getColor(R.color.themeLight)
                }
        );
        updateBtnCancelTextColor(btnCancelColors);

        updateBtnActionBackground(R.drawable.d_game_failed_btn_action);

        ColorStateList btnActionColors = new ColorStateList(
                new int[][] {
                        new int[] {android.R.attr.state_pressed},
                        new int[] {}
                },
                new int[] {
                        getResources().getColor(R.color.themeLight),
                        getResources().getColor(R.color.themeLightAccent)
                }
        );
        updateBtnActionTextColor(btnActionColors);
    }

    @Override
    protected int getBackground() {
        return R.drawable.d_gamefailed_background;
    }

    @Override
    protected int getImg() {
        return R.drawable.d_gamefailed_icon_failed;
    }

    @Override
    protected int getMsg() {
        return R.string.d_gamefailed_msg;
    }

    @Override
    protected int getBtnActionText() {
        return R.string.d_gamefailed_btn_action;
    }

    @Override
    protected int getBtnCancelText() {
        return 0;
    }

    @Override
    protected View.OnClickListener getAction() {
        return null;
    }
}
