package com.test.macowu.jni;

/**
 *
 * Created by macowu on 15/10/3.
 */
public class NdkJniUtils {

    static {
        System.loadLibrary("hellojni");
    }

    public native String helloFromJNI();

    public native String getDeviceInfo();
}
