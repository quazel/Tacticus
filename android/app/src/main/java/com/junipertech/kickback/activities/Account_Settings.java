package com.junipertech.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.junipertech.kickback.R;


public class Account_Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // formats username in account settings

        String username = "temp username"; //dummy variable
        formatUsernameTextview(username);
        // formats phone number in account settings
        String number = "0-000-000-0000"; //dummy variable
        formatPhoneNumberButton(number);
        //formats email in account settings
        String email = "dummyemail@temp.com"; //dummy variable
        formatEmail(email);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account__settings, menu);
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

    public void mobile_number_btn(View view)
    {

    }

    public void email_btn(View view)
    {
        emailDialog(this);
    }

    public void emailDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Enter your email");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Save", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void formatUsernameTextview(String s)
    {
        TextView user = (TextView) findViewById(R.id.account_settings_username);
        String firstUser = (String)user.getText();
        String nextUser = "<br><font color='#c9c9c9'>"+s+"</font>";
        user.setText(Html.fromHtml(firstUser + "\n" + "<small>" + nextUser + "</small>"));
    }

    public void formatPhoneNumberButton(String s)
    {
        Button phoneNumber = (Button) findViewById(R.id.account_settings_phone_number);
        String firstNumber = (String)phoneNumber.getText();
        String nextNumber = "<br><font color='#c9c9c9'>"+s+"</font>";
        phoneNumber.setText(Html.fromHtml(firstNumber + "\n"+"<small>"+nextNumber+"</small>"));
    }

    public void formatEmail(String s)
    {
        Button userEmail = (Button) findViewById(R.id.account_settings_email);
        String firstEmail = (String)userEmail.getText();
        String nextEmail = "<br><font color='#c9c9c9'>"+s+"</font>";
        userEmail.setText(Html.fromHtml(firstEmail + "\n"+"<small>"+nextEmail+"</small>"));
    }
}
