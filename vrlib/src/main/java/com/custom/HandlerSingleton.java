package com.custom;

/**
 * Created by cpu60011-local on 04/04/2017.
 */

public class HandlerSingleton {
    private static CustomHandler mHandler;

    synchronized public static void init(OnScrollCallback scrollCallback, OnSensorChangedCallback sensorChangedCallback) {
        mHandler = new CustomHandler();
        mHandler.setScrollCallback(scrollCallback);
        mHandler.setSensorChangedCallback(sensorChangedCallback);
    }

    synchronized public static CustomHandler getHandler() {
        return mHandler;
    }
}
