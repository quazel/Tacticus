package com.junipertech.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.*;


public class AccountSettings extends Activity {

    private String tempEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // formats username in account settings
        String username = "temp username"; //dummy variable
        formatUsernameTextview(username);
        // formats phone number in account settings
        String number = "0-000-000-0000"; //dummy variable
        formatPhoneNumberButton(number);
        //formats email in account settings
        tempEmail = "burgle@burgle.com";//dummy variable
        formatEmail(tempEmail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_settings, menu);
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

    public void onMobileNumberPressed(View view) {

    }

    public void onEmailPressed(View view) {
        emailDialog(this);
    }

    public void emailDialog(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Enter your email");

        final View view = getLayoutInflater().inflate(R.layout.email_dialog, null);

        final EditText input = (EditText) view.findViewById(R.id.email_field);
        input.append(tempEmail);

        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tempEmail = input.getText().toString();
                formatEmail(tempEmail);
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog emailAlert = builder.create();
        emailAlert.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        emailAlert.show();
    }

    public void formatUsernameTextview(String s) {
        TextView user = (TextView) findViewById(R.id.account_settings_username);
        String firstUser = (String)user.getText();
        String nextUser = "<br><font color='#c9c9c9'>"+s+"</font>";
        user.setText(Html.fromHtml(firstUser + "\n" + "<small>" + nextUser + "</small>"));
    }

    public void formatPhoneNumberButton(String s) {
        Button phoneNumber = (Button) findViewById(R.id.account_settings_phone_number);
        String firstNumber = (String)phoneNumber.getText();
        String nextNumber = "<br><font color='#c9c9c9'>"+s+"</font>";
        phoneNumber.setText(Html.fromHtml(firstNumber + "\n"+"<small>"+nextNumber+"</small>"));
    }

    public void formatEmail(String s) {
        Button userEmail = (Button) findViewById(R.id.account_settings_email);
        String firstEmail = "Email";
        String nextEmail = "<br><font color='#c9c9c9'>"+s+"</font>";
        userEmail.setText(Html.fromHtml(firstEmail + "\n"+"<small>"+nextEmail+"</small>"));
    }
}
