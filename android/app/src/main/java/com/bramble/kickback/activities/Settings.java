package com.bramble.kickback.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.User;
import com.bramble.kickback.util.Globals;


public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.settings_color)));

        User user = User.getUser();
        //mega formatting hacks
        // formats username in manage account
        String username = user.getUsername(); //dummy variable
        formatUsernameTextview(username);
        // formats phone number in manage accounts
        String number = user.getPhoneNumber(); //dummy variable
        formatPhoneNumberButton(number);
        //formats email in manage account
        String email = user.getEmail();
        formatEmail(email);
        //formats version in information
        String version = "0.0.00.0 © 2014 Bramble, LLC.";
        formatVersion(version);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    public void onEmailPressed(View view) {
        emailDialog(this);
    }

    public void emailDialog(Activity activity) {

        final User user = User.getUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Enter your email");

        final View view = getLayoutInflater().inflate(R.layout.email_dialog, null);

        final EditText input = (EditText) view.findViewById(R.id.email_field);
        input.append(user.getEmail());

        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user.setEmail(input.getText().toString());
                formatEmail(user.getEmail());
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog emailAlert = builder.create();
        emailAlert.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        emailAlert.show();
    }

    public void notificationSettingsPressed(View view) {
        Intent notificationIntent = new Intent(this, NotificationSettings.class);
        startActivity(notificationIntent);
    }

    public void logoutUser(View view) {
        logoutDialog(this);
    }

    public void logoutDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);


        builder.setMessage("Are you sure you would like to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Context context = Settings.this;
                Intent intent = new Intent(context, SplashScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }




    //temporary hacks for formatting
    public void formatUsernameTextview(String s) {
        TextView user = (TextView) findViewById(R.id.username);
        String firstUser = (String)user.getText();
        String nextUser = "<br><font color='#666666'>"+s+"</font>";
        user.setText(Html.fromHtml(firstUser + "\n" + "<small>" + nextUser + "</small>"));
    }

    public void formatPhoneNumberButton(String s) {
        Button phoneNumber = (Button) findViewById(R.id.phonenumber);
        String firstNumber = (String)phoneNumber.getText();
        String nextNumber = "<br><font color='#666666'>"+s+"</font>";
        phoneNumber.setText(Html.fromHtml(firstNumber + "\n"+"<small>"+nextNumber+"</small>"));
    }

    public void formatEmail(String s) {
        Button userEmail = (Button) findViewById(R.id.email);
        String firstEmail = "Email";
        String nextEmail = "<br><font color='#666666'>"+s+"</font>";
        userEmail.setText(Html.fromHtml(firstEmail + "\n"+"<small>"+nextEmail+"</small>"));
    }

    public void formatVersion(String s) {
        Button version = (Button) findViewById(R.id.version);
        String a = "Version";
        String b = "<br><font color='#666666'>"+s+"</font>";
        version.setText(Html.fromHtml(a + "\n"+"<small>"+b+"</small>"));
    }
}
