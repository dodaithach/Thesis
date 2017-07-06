package k2013.fit.hcmus.thesis.id539621.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

import k2013.fit.hcmus.thesis.id539621.activity.MusicActivity;

/**
 * Created by thachdd on 07/07/2017.
 */

public class MusicLocalBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "MLBR";
    public static final String ACTION = "MLBR_ACTION";

    WeakReference<MusicActivity> mWeakReference;

    public MusicLocalBroadcastReceiver(MusicActivity activity) {
        mWeakReference = new WeakReference<MusicActivity>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MusicActivity activity = mWeakReference.get();
        if (activity == null || activity.isDestroyed()) {
            return;
        }

        int idx = intent.getIntExtra(MusicLocalBroadcastReceiver.TAG, -1);
        activity.onReceiveLocalBroadcast(idx);
    }
}
