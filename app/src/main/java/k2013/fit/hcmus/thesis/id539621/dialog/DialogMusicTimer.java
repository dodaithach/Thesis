package k2013.fit.hcmus.thesis.id539621.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by cpu60011-local on 05/07/2017.
 */

public class DialogMusicTimer extends AppCompatDialogFragment {
    public static final String TAG = "DIALOG_MUSIC_TIMER";

    private static final int MAX_SEEK_BAR = 105;
    private static final int DEFAULT_SEEK_BAR = 15;

    public interface TimerChangedListener {
        public void setTimer(int time);
        public int getTimer();

        public void setTimerState(boolean mode);
        public boolean getTimerState();
    }

    private TimerChangedListener mListener;

    private SeekBar mSeekBar;
    private Switch mSwitch;
    private LinearLayout mGroupTimer;
    private TextView mTextMinute;

    private final SeekBar.OnSeekBarChangeListener mSeekBarChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                progress += DEFAULT_SEEK_BAR;

                mListener.setTimer(progress);
                mTextMinute.setText("" + progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private final CompoundButton.OnCheckedChangeListener mSwitchChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            enableSeekBar(isChecked, DEFAULT_SEEK_BAR);
            mListener.setTimerState(isChecked);
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mListener = (TimerChangedListener) getActivity();

        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.d_music_timer, container, false);

        mTextMinute = (TextView) dialog.findViewById(R.id.d_music_txt_minute);
        mGroupTimer = (LinearLayout) dialog.findViewById(R.id.d_music_group_timer);

        mSeekBar = (SeekBar) dialog.findViewById(R.id.d_music_seekbar);
        mSwitch = (Switch) dialog.findViewById(R.id.d_music_switch);

        mSwitch.setChecked(mListener.getTimerState());
        mSwitch.setOnCheckedChangeListener(mSwitchChangedListener);

        mSeekBar.setMax(MAX_SEEK_BAR);
        enableSeekBar(mListener.getTimerState(), mListener.getTimer());

        return dialog;
    }

    private void enableSeekBar(boolean isEnable, int value) {
        mSeekBar.setEnabled(isEnable);

        if (isEnable) {
            mSeekBar.setProgress(value - DEFAULT_SEEK_BAR);
            mSeekBar.setOnSeekBarChangeListener(mSeekBarChangedListener);

            mTextMinute.setText("" + value);
            mGroupTimer.setVisibility(View.VISIBLE);
        } else {
            mGroupTimer.setVisibility(View.GONE);
        }
    }
}
