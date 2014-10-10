package com.junipertech.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.junipertech.kickback.R;


public class MoreInformation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // formats phone number in account settings
        String v = "v0.0.00.1"; //dummy variable
        formatVersionButton(v);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.more_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPrivacyPressed(View view) {

    }

    public void onTermsOfUsePressed(View view) {

    }

    public void onVersionPressed(View view) {

    }

    public void formatVersionButton(String s) {
        // formats phone number in account settings
        Button more_info_btn = (Button) findViewById(R.id.more_info_version_info);
        String firstVersion = (String)more_info_btn.getText();

        String nextVersion = "<br><font color='#c9c9c9'>"+s+"</font>";
        more_info_btn.setText(Html.fromHtml(firstVersion + "\n" + "<small>" + nextVersion + "</small>"));;
    }
}
