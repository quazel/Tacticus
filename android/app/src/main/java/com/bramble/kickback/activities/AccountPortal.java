package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
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
import java.util.Calendar;

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
        //ft.replace(R.id.fragment_place, login, "loginTag");
        //ft.addToBackStack()
        ft.commit();
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
        //ft.replace(R.id.fragment_place, signUpCredentials, "signUpCredentialsTag");
        ft.commit();
    }

    // when the back button is pressed (sign up)
    public void backSignUpPressed(View v){
        // begins transaction that returns the user to the sign up credentials fragment
        // from the sign up biographical fragment
        ft = fm.beginTransaction();
        //ft.replace(R.id.fragment_place, signUpCredentials, "signUpCredentialsTag");
        ft.commit();
    }

}
