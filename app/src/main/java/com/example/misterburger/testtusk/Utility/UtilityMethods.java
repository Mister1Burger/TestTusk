package com.example.misterburger.testtusk.Utility;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.misterburger.testtusk.TestTusk;

/**
 * Created by Burge on 06.02.2018.
 */

public class UtilityMethods {public static boolean isNetworkAvailable() {

    ConnectivityManager connectivityManager = (ConnectivityManager) TestTusk.getTestTusk()
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    return connectivityManager.getActiveNetworkInfo() != null
            && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
}
}
