package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.util.Globals;

import java.io.IOException;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences prefs = getSharedPreferences("KickbackPreferences", 0);
        Globals.readContacts(getContentResolver());

        if(prefs.contains("username")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setContentView(R.layout.activity_splash_screen_sign_in);

                }
            }, SPLASH_TIME_OUT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
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
     //attempts at using button navigation


    public void cancelSignUpPressed(View v){
        setContentView(R.layout.activity_splash_screen_sign_in);
    }

    public void signInPressed(View v){
        EditText usernameField = (EditText) findViewById(R.id.editTextUsername);
        EditText passwordField = (EditText) findViewById(R.id.editTextPassword);
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "Please enter your username and password.", Toast.LENGTH_SHORT).show();
        }
        else if(!username.matches("^[a-zA-Z0-9_]+$")) {
            Toast.makeText(this, "Usernames may only contain letters, numbers, and underscores (_).", Toast.LENGTH_SHORT).show();
        }
        else {
            new LoginTask().execute(username, password);
        }
    }

    public void toSignUpPressed(View v){
        setContentView(R.layout.activity_splash_screen_sign_up);
    }

    public void signUpPressed(View v){

        EditText usernameField = (EditText) findViewById(R.id.editTextDesiredUsername);
        EditText passwordField = (EditText) findViewById(R.id.editTextDesiredPassword);
        EditText confirmPasswordField = (EditText) findViewById(R.id.editTextConfirmDesiredPassword);
        EditText nameField = (EditText) findViewById(R.id.editTextName);
        EditText emailField = (EditText) findViewById(R.id.editTextEmail);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "Please enter desired username and password.", Toast.LENGTH_SHORT).show();
        }
        else if(confirmPassword.equals("")){
            Toast.makeText(this, "Please confirm password.", Toast.LENGTH_SHORT).show();
        }
        else if(!username.matches("^[a-zA-Z0-9_]+$")) {
            Toast.makeText(this, "Usernames may only contain letters, numbers, and underscores (_).", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 6 || password.length() > 20){
            Toast.makeText(this, "Passwords must be between 6 and 20 characters in length.", Toast.LENGTH_SHORT).show();
        }
        else if(!password.matches("^[a-zA-Z0-9_\\-!@#$%^&*]+$")) {
            Toast.makeText(this, "Passwords may only contain letters, numbers, and the special characters !@#$%^&*-_.", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Entered passwords are not the same.", Toast.LENGTH_SHORT).show();
        }
        else if(name.equals("")) {
            Toast.makeText(this, "Please enter your full name.", Toast.LENGTH_SHORT).show();
        }
        else if(!email.matches("^[a-zA-Z0-9_\\-+%\\.]+@[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z\\.]{2,6}$")){
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
        }
        else {
            // temporary variable vvvv
            Globals.createUser(usernameField.getText().toString(),nameField.getText().toString(), emailField.getText().toString(), "");
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }
    }

    private class LoginTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... params) {
            try {
                String result = new ConnectionHandler().login(params[0], params[1]);
                if (result != null) {
                    User user = new User(params[0]);
                    user.setSessionId(result);
                    return user;
                }
                else {
                    return null;
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(User loggedUser) {
            if (loggedUser != null) {
                Globals.theUser = loggedUser;
                Intent intent = new Intent(SplashScreen.this, Home.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(SplashScreen.this, "Invalid username or password.", Toast.LENGTH_LONG).show();
            }
        }

    }
}
