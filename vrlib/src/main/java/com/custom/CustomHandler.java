package com.custom;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by cpu60011-local on 04/04/2017.
 */

public class CustomHandler extends Handler {
    public static final int TOUCH = 0;
    public static final int SENSOR = 1;

    public static final String TOUCH_PARAMS = "touch_params";

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

        switch (msg.what) {
            case TOUCH: {
                double[] params = msg.getData().getDoubleArray(TOUCH_PARAMS);

                if (params!= null && params.length > 0 && mScrollCallback.get() != null) {
                    mScrollCallback.get().customOnScroll((float) params[0], (float) params[1]);
                }

                break;
            }

            case SENSOR: {

                break;
            }

            default:
                break;
        }
    }
}
