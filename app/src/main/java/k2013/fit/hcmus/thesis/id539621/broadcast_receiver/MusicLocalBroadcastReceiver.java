package k2013.fit.hcmus.thesis.id539621.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.ref.WeakReference;

import k2013.fit.hcmus.thesis.id539621.activity.MusicActivity;
import k2013.fit.hcmus.thesis.id539621.service.MusicPlayerService;

/**
 * Created by thachdd on 07/07/2017.
 */

public class MusicLocalBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG_UPDATE = "MLBR_UPDATE";
    public static final String TAG_CLOSE = "MLBR_CLOSE";
    public static final int VALUE_CLOSE = 1;
    public static final String ACTION = "MLBR_ACTION";

    WeakReference<MusicActivity> mWeakReference;

    public MusicLocalBroadcastReceiver(MusicActivity activity) {
        mWeakReference = new WeakReference<MusicActivity>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("mylog", "MusicLocalBroadcastReceiver.onReceive()");

        MusicActivity activity = mWeakReference.get();
        if (activity == null || activity.isDestroyed()) {
            Log.d("mylog", "MusicLocalBroadcastReceiver.activity == null");
            return;
        }

        int isClosed = intent.getIntExtra(MusicLocalBroadcastReceiver.TAG_CLOSE, -1);

        if (isClosed == MusicLocalBroadcastReceiver.VALUE_CLOSE) {
            Log.d("mylog", "MusicLocalBroadcastReceiver.activity.finish()");
            activity.finish();
        } else {
            int idx = intent.getIntExtra(MusicLocalBroadcastReceiver.TAG_UPDATE, -1);
            activity.onReceiveLocalBroadcast(idx);
        }
    }
}
