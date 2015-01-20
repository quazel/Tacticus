package com.bramble.kickback.service;

import android.app.Service;
import android.os.Binder;

public class Notifier extends Service {




    public class LocalBinder extends Binder {
        public Notifier getService() { return Notifier.this; }
    }


}
