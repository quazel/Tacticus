package com.bramble.kickback;

import android.app.Application;

public class KickbackApplication extends Application {

    private static KickbackApplication instance;

    public KickbackApplication() {
        instance = this;
    }

    public static KickbackApplication getInstance() {
        return instance;
    }

}
