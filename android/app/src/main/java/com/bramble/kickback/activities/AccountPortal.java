package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.fragments.AccountPortalIndex;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.fragments.Login;
import com.bramble.kickback.fragments.SignUpBiographical;
import com.bramble.kickback.fragments.SignUpCredentials;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.service.LoginService;
import com.bramble.kickback.service.SignUpService;
import com.bramble.kickback.util.Globals;

import java.io.IOException;

public class AccountPortal extends Activity {
    //Services
    private LoginService loginService;
    private SignUpService signUpService;
    // service intents
    private Intent loginServiceIntent;
    private Intent signUpServiceIntent;
    // fragment manager and transaction
    private FragmentManager fm;
    private FragmentTransaction ft;
    // fragments for use with fragment manager
    private AccountPortalIndex accountPortalIndex;
    private Login login;
    private SignUpCredentials signUpCredentials;
    private SignUpBiographical signUpBiographical;
    private LoadingBar loadingBar;
    // service connection to login service
    private ServiceConnection loginConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Because we have bound to an explicit
            // service that is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            LoginService.LocalBinder binder = (LoginService.LocalBinder) service;
            loginService = binder.getService();
        }
        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
        }
    };

    // service connection to sign up service
    private ServiceConnection signUpConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Because we have bound to an explicit
            // service that is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            SignUpService.LocalBinder binder = (SignUpService.LocalBinder) service;
            signUpService = binder.getService();
        }
        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // creating intents for use with changing currently displayed fragment
        signUpServiceIntent = new Intent(this, SignUpService.class);
        loginServiceIntent = new Intent(this, LoginService.class);
        // sets activity layout to the appropriate
        setContentView(R.layout.activity_account_portal);
        // creates fragment manager for use with transactions with set the current fragment
        fm = getFragmentManager();
        // creates fragments for use with method calls
        accountPortalIndex = new AccountPortalIndex();
        login = new Login();
        signUpCredentials = new SignUpCredentials();
        signUpBiographical = new SignUpBiographical();
        loadingBar = new LoadingBar();
        // creates a transaction
        ft = fm.beginTransaction();
        // add a fragment which becomes the current fragment
        ft.add(R.id.fragment_place, accountPortalIndex);
        // commits the transaction which confirms the changes made during the transaction
        // and makes the current fragment visible
        ft.commit();
    }

    // LOGIN PATH
    // when the login button is pressed
    public void toSignInPressed(View v){
        // starts the login service
        startService(loginServiceIntent);
        // binds this activity to the login service in order to receive and address to it
        bindService(loginServiceIntent, loginConnection, BIND_AUTO_CREATE);
        // starts a transaction
        FragmentTransaction ft = fm.beginTransaction();
        // replaces current fragment (accountPortalIndex) with the login fragment
        ft.replace(R.id.fragment_place, login, "loginTag");
        //ft.addToBackStack()
        ft.commit();
    }

    // when the cancel button is pressed (login)
    public void cancelSignInPressed(View v){
        // clears all the instance variables within login service
        loginService.clear();
        // stops the login service because the user has returned to the account index fragment
        stopService(loginServiceIntent);
        // begins transaction that replaces the login fragment with the account index fragment
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, accountPortalIndex);
        ft.commit();
        // resets the login fragment so it doesn't retain any of the inputs made by the user
        // during the duration of the login service
        login = new Login();
    }

    // when the login button is pressed (login)
    public void loginPressed(View v){
        // sets the instance variables in the login service to the inputs gathered
        // by the edit texts in the fragments
        loginService.setUsername(login.getUsernameText());
        loginService.setPassword(login.getPasswordText());
        // native checks to make sure all fields are properly filled out
        if (loginService.getUsername().equals("") || loginService.getPassword().equals("")) {
            Toast.makeText(this, "Please enter your username and password.", Toast.LENGTH_SHORT).show();
        }
        // if the inputs are appropriate
        else {
            ft = fm.beginTransaction();
            ft.add(R.id.loading_place, loadingBar, "loadingBarTag");
            ft.commit();
            new LoginTask().execute(loginService.getUsername(), loginService.getPassword());
            login.disableButtons();
        }
    }

    // SIGN UP PATH
    // when the sign up button is pressed
    public void toSignUpPressed(View v){
        // starts the sign up service
        startService(signUpServiceIntent);
        // binds this activity to the service
        bindService(signUpServiceIntent, signUpConnection, BIND_AUTO_CREATE);
        // starts a transaction
        ft = fm.beginTransaction();
        // replaces accountPortalIndex fragment with the signUpCredentials fragment.
        ft.replace(R.id.fragment_place, signUpCredentials, "signUpCredentialsTag");
        ft.commit();
    }

    // when cancel is pressed (sign up)
    public void cancelSignUpPressed(View v){
        // clears instance variables within sign up service
        signUpService.clear();
        // stops the sign up service
        stopService(signUpServiceIntent);
        // begins transaction that replaces the sign up credentials fragment with
        // the account portal index fragment
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, accountPortalIndex);
        ft.commit();
        // resets the inputs of the sign up fragments by turning them into new ones
        signUpCredentials = new SignUpCredentials();
        signUpBiographical = new SignUpBiographical();
    }

    // when the back button is pressed (sign up)
    public void backSignUpPressed(View v){
        // begins transaction that returns the user to the sign up credentials fragment
        // from the sign up biographical fragment
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, signUpCredentials, "signUpCredentialsTag");
        ft.commit();
    }

    // when the continue button is pressed (sign up)
    public void continueSignUpPressed(View v) {
        // NEEDS REWORK (more checks with server)

        // sets the instance variables inside the sign up service to the inputs
        // gathered in the sing up credentials fragment
        signUpService.setEmail(signUpCredentials.getEmailText());
        signUpService.setDesiredUsername(signUpCredentials.getDesiredUsernameText());
        signUpService.setDesiredPassword(signUpCredentials.getDesiredPasswordText());
        signUpService.setConfirmPassword(signUpCredentials.getConfirmDesiredPasswordText());
        signUpService.setPhoneNumber(signUpCredentials.getPhoneNumberText());
        // native checks on inputs gathered
        if(signUpService.getEmail().equals("")) {
            Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
        }
        else if (signUpService.getDesiredUsername().equals("") || signUpService.getDesiredPassword().equals("")) {
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
        else if(signUpService.getPhoneNumber().equals("")) {
            Toast.makeText(this, "Please enter your mobile phone number.", Toast.LENGTH_SHORT).show();
        }
        else {

            //
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, signUpBiographical, "signUpBiographicalTag");
            ft.commit();
        }
    }

    // when the sign up button is pressed (sign up)
    public void signUpPressed(View v){
        signUpService.setFirstName(signUpBiographical.getFirstNameText());
        signUpService.setLastName(signUpBiographical.getLastNameText());
        signUpService.setBirthday(signUpBiographical.getBirthdayText());
        signUpService.setSex(signUpBiographical.getSexText());

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
            // temporary variable vvv
            Globals.createUser(signUpService.getDesiredUsername(), signUpService.getFirstName(),
                               signUpService.getEmail(),signUpService.getPhoneNumber());
            //setContentView(R.layout.activity_loading);
            //new SignUpTask().execute(username, password);
            signUpBiographical.setFirstNameText(signUpService.getFirstName());
            signUpBiographical.setLastNameText(signUpService.getLastName());
            signUpBiographical.setBirthdayText(signUpService.getBirthday());
            signUpBiographical.setSexButton(signUpService.getSex());
            signUpCredentials.setDesiredUsernameText(signUpService.getDesiredUsername());
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
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();
                Toast.makeText(AccountPortal.this, "Invalid username or password.", Toast.LENGTH_LONG).show();
                login.setPasswordText("");
            }
        }
    }

    // Asynchronously sends a sign up request
    private class SignUpTask extends AsyncTask<String, Void, User> {
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
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_place, login, "loginTag");
                ft.commit();
                Toast.makeText(AccountPortal.this, "Invalid username or password.", Toast.LENGTH_LONG).show();
                loginService.setPassword("");
                login.enableButtons();
            }
        }
    }

}
