package com.bramble.kickback.activities;
import com.bramble.kickback.R;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.service.SignUpService;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


public class SignUpCredentials extends Activity {

    private SignUpService signUpService;
    private Intent signUpServiceIntent;

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

    private EditText email;
    private EditText desiredUsername;
    private EditText desiredPassword;
    private EditText confirmDesiredPassword;
    private EditText phoneNumber;
    private Button continueButton;
    private Button cancelButton;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_credentials);
        signUpServiceIntent = new Intent(this, SignUpService.class);
        startService(signUpServiceIntent);
        bindService(signUpServiceIntent, signUpConnection, Context.BIND_AUTO_CREATE);

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        email = (EditText) findViewById(R.id.editTextEmail);
        desiredUsername = (EditText) findViewById(R.id.editTextDesiredUsername);
        desiredPassword = (EditText) findViewById(R.id.editTextDesiredPassword);
        confirmDesiredPassword = (EditText) findViewById(R.id.editTextConfirmDesiredPassword);
        phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        continueButton = (Button) findViewById(R.id.buttonSignUp);
        cancelButton = (Button) findViewById(R.id.buttonCancelSignUp);
    }

    public void disableButtons() {
        continueButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }

    public void enableButtons() {
        continueButton.setEnabled(true);
        continueButton.setEnabled(true);
    }

    // when the continue button is pressed (sign up)
        public void continueSignUpPressed(View v) {
        // sets the instance variables inside the sign up service to the inputs
        // gathered in the sign up credentials
        signUpService.setEmail(email.getText().toString());
        signUpService.setDesiredUsername(desiredUsername.getText().toString());
        signUpService.setDesiredPassword(desiredPassword.getText().toString());
        signUpService.setConfirmPassword(confirmDesiredPassword.getText().toString());
        signUpService.setPhoneNumber(phoneNumber.getText().toString());
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
            disableButtons();
            new CheckCredentialTask().execute(getDesiredUsernameText(), getEmailText(), getPhoneNumberText());
        }
    }

    // when cancel is pressed (sign up)
    public void cancelSignUpPressed(View v){
        // clears instance variables within sign up service
        signUpService.clear();
        // stops the sign up service
        stopService(signUpServiceIntent);
        finish();
        overridePendingTransition(0,0);
    }

    // Asynchronously sends a request to check uniqueness of credentials
    private class CheckCredentialTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return new ConnectionHandler().checkCredentials(params[0], params[1], params[2]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (result.startsWith("200:")) {
                    Intent intent = new Intent(SignUpCredentials.this, SignUpBiographical.class);
                    startActivity(intent);
                }
                else if (result.startsWith("401:")) {
                    Toast.makeText(SignUpCredentials.this, result.replace("401:", ""), Toast.LENGTH_LONG).show();
                    SignUpCredentials.this.enableButtons();
                }
            }
            else {
                Toast.makeText(SignUpCredentials.this, "An error occurred.", Toast.LENGTH_LONG).show();
                SignUpCredentials.this.enableButtons();
            }
        }
    }

    public String getEmailText() {
        return this.email.getText().toString();
    }

    public String getDesiredUsernameText() {
        return this.desiredUsername.getText().toString();
    }

    public String getDesiredPasswordText() {
        return this.desiredPassword.getText().toString();
    }

    public String getConfirmDesiredPasswordText() {
        return this.confirmDesiredPassword.getText().toString();
    }

    public String getPhoneNumberText() {
        return this.phoneNumber.getText().toString();
    }

    public void setEmailText(String email) {
        this.email.setText(email);
    }

    public void setDesiredUsernameText(String desiredUsername) {
        this.desiredUsername.setText(desiredUsername);
    }

    public void setDesiredPasswordText(String desiredPassword) {
        this.desiredPassword.setText(desiredPassword);
    }

    public void setConfirmDesiredPasswordText(String confirmDesiredPassword) {
        this.confirmDesiredPassword.setText(confirmDesiredPassword);
    }

    public void setPhoneNumberText(String phoneNumber) {
        this.phoneNumber.setText(phoneNumber);
    }

}
