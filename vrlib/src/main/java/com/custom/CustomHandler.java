package com.custom;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by cpu60011-local on 04/04/2017.
 */

public class CustomHandler extends Handler {
    private WeakReference<OnScrollCallback> mScrollCallback;
    private WeakReference<OnSensorChangedCallback> mSensorChangedCallback;

    public void setScrollCallback(OnScrollCallback callback) {
        mScrollCallback = new WeakReference<OnScrollCallback>(callback);
    }

    public void setSensorChangedCallback(OnSensorChangedCallback callback) {
        mSensorChangedCallback = new WeakReference<OnSensorChangedCallback>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        if (mScrollCallback.get() != null) {
            mScrollCallback.get().customOnScroll();
        }
    }
}
