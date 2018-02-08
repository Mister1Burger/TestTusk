package com.example.misterburger.testtusk;

import android.app.Application;

/**
 * Created by Burge on 06.02.2018.
 */

public class TestTusk extends Application {
    private static TestTusk testTusk;

    @Override
    public void onCreate() {
        super.onCreate();
        testTusk = this;
    }

    public static TestTusk getTestTusk(){
        return testTusk;
    }
}

