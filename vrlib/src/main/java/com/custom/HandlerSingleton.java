package com.custom;

/**
 * Created by cpu60011-local on 04/04/2017.
 */

public class HandlerSingleton {
    private static CustomHandler mHandler;

    public static void init(OnScrollCallback scrollCallback, OnSensorChangedCallback sensorChangedCallback) {
        mHandler = new CustomHandler();
        mHandler.setScrollCallback(scrollCallback);
        mHandler.setSensorChangedCallback(sensorChangedCallback);
    }

    public static CustomHandler getHandler() {
        return mHandler;
    }
}
