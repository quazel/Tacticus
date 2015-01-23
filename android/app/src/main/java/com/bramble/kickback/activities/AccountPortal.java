package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.fragments.AccountPortalIndex;
import com.bramble.kickback.fragments.Login;
import com.bramble.kickback.fragments.SignUpBiographical;
import com.bramble.kickback.fragments.SignUpCredentials;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.service.SignUpService;
import com.bramble.kickback.util.Globals;

import java.io.IOException;

public class AccountPortal extends Activity {

    // from login
    private String username;
    private String password;

    //Service
    SignUpService signUpService;
    Intent signUpServiceIntent;

    // fragment instance variables
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment accountPortalIndex;
    Fragment login;
    Fragment signUpCredentials;
    Fragment signUpBiographical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // start service

        signUpServiceIntent = new Intent(this, SignUpService.class);
        setContentView(R.layout.activity_account_portal);

        fm = getFragmentManager();

        accountPortalIndex = new AccountPortalIndex();
        login = new Login();
        signUpCredentials = new SignUpCredentials();
        signUpBiographical = new SignUpBiographical();

        ft = fm.beginTransaction();

        ft.add(R.id.fragment_place, accountPortalIndex);
        ft.commit();
    }

    public void toSignInPressed(View v){
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_place, login);
        //ft.addToBackStack()
        ft.commit();
    }

    public void toSignUpPressed(View v){
        startService(signUpServiceIntent);
        ft = fm.beginTransaction();
        ft.add(R.id.fragment_place, signUpCredentials);
        ft.commit();
    }

    public void cancelSignInPressed(View v){
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, accountPortalIndex);
        ft.commit();
    }

    public void cancelSignUpPressed(View v){
        stopService(signUpServiceIntent);
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, accountPortalIndex);
        ft.commit();
    }

    public void backSignUpPressed(View v){
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, signUpCredentials);
        ft.commit();
    }

    public void continueSignUpPressed(View v) {
        if (signUpService.getDesiredUsername().equals("") || signUpService.getDesiredPassword().equals("")) {
            Toast.makeText(this, "Please enter desired username and password.", Toast.LENGTH_SHORT).show();
        }
        else if(signUpService.getConfirmPassword().equals("")){
            Toast.makeText(this, "Please confirm password.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpService.getDesiredUsername().matches("^[a-zA-Z0-9_]+$")) {
            Toast.makeText(this, "Usernames may only contain letters, numbers, and underscores (_).", Toast.LENGTH_SHORT).show();
        }
        else if(signUpService.getDesiredPassword().length() < 6 || signUpService.getDesiredPassword().length() > 20){
            Toast.makeText(this, "Passwords must be between 6 and 20 characters in length.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpService.getDesiredPassword().matches("^[a-zA-Z0-9_\\-!@#$%^&*]+$")) {
            Toast.makeText(this, "Passwords may only contain letters, numbers, and the special characters !@#$%^&*-_.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpService.getDesiredPassword().equals(signUpService.getConfirmPassword())){
            Toast.makeText(this, "Entered passwords are not the same.", Toast.LENGTH_SHORT).show();
        }
        else {
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, signUpBiographical);
            ft.commit();
        }
    }

    public void loginPressed(View v){

        // insert code to grab entered edittext and store it in the instance fields provided by this class

        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "Please enter your username and password.", Toast.LENGTH_SHORT).show();
        }
        else if(!username.matches("^[a-zA-Z0-9_]+$")) {
            Toast.makeText(this, "Usernames may only contain letters, numbers, and underscores (_).", Toast.LENGTH_SHORT).show();
        }
        else {
            setContentView(R.layout.activity_loading);
            new LoginTask().execute(username, password);
        }
    }

    public void signUpPressed(View v){

        if(signUpService.getFirstName().equals("")) {
            Toast.makeText(this, "Please enter your first name.", Toast.LENGTH_SHORT).show();
        }
        else if(signUpService.getLastName().equals("")) {
            Toast.makeText(this, "Please enter your last name.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpService.getEmail().matches("^[a-zA-Z0-9_\\-+%\\.]+@[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z\\.]{2,6}$")){
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
        }
        else {
            // temporary variable vvvv
            Globals.createUser(signUpService.getDesiredUsername(), signUpService.getFirstName(),
                               signUpService.getEmail(),"phone number");
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }
    }


    // Asynchronously sends a login request

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
                Intent intent = new Intent(AccountPortal.this, Home.class);
                startActivity(intent);
                finish();
            }
            else {
                setContentView(R.layout.activity_splash_screen_sign_in);
                Toast.makeText(AccountPortal.this, "Invalid username or password.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
