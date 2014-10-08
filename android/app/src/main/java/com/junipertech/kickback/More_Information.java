package com.junipertech.kickback;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class More_Information extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more__information);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // formats phone number in account settings
        String v = "v0.0.00.1"; //dummy variable
        formatVersionButton(v);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.more__information, menu);
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

    public void privacy_policy_btn(View view)
    {

    }

    public void terms_of_use_btn(View view)
    {

    }

    public void version_btn(View view)
    {

    }

    public void formatVersionButton(String s)
    {
        // formats phone number in account settings
        Button geoButton = (Button) findViewById(R.id.account_advanced_geolocation);
        String firstGeo = (String)geoButton.getText();

        String nextGeo = "<br><font color='#c9c9c9'>"+s+"</font>";
        geoButton.setText(Html.fromHtml(firstGeo + "\n" + "<small>" + nextGeo + "</small>"));;
    }
}
