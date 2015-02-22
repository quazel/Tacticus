package com.bramble.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bramble.kickback.R;

public class Settings extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void settingsBackButtonPressed(View view) {
        finish();
    }
}
