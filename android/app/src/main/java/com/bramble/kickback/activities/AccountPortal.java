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
import com.bramble.kickback.util.Globals;

import java.io.IOException;

public class AccountPortal extends Activity {

    //from sign up credentials
    private String email;
    private String desiredUsername;
    private String desiredPassword;
    private String confirmPassword;

    // from sign up biographical
    private String firstName;
    private String lastName;
    private String birthday;
    private String sex;

    // from login
    private String username;
    private String password;

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
        ft = fm.beginTransaction();
        ft.add(R.id.fragment_place, signUpCredentials);
        ft.commit();
    }

    public void cancelSignInPressed(View v){
        ft = fm.beginTransaction();
        clearLoginEdittexts();
        ft.replace(R.id.fragment_place, accountPortalIndex);
        ft.commit();
    }

    public void cancelSignUpPressed(View v){
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, accountPortalIndex);
        ft.commit();
    }

    public void backSignUpPressed(View v){
        updateSignUpBiographicalFields();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, signUpCredentials);
        ft.commit();
    }

    public void continueSignUpPressed(View v) {
        updateSignUpCredentialsFields();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, signUpBiographical);
        ft.commit();
    }

    public void loginPressed(View v){

        // insert code to grab entered edittext and store it in the instance fields provided by this class
        updateLoginFields();

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

        // insert code to grab entered edittext and store it in the instance fields provided by this class
        updateSignUpBiographicalFields();

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
        else if(firstName.equals("")) {
            Toast.makeText(this, "Please enter your full name.", Toast.LENGTH_SHORT).show();
        }
        else if(!email.matches("^[a-zA-Z0-9_\\-+%\\.]+@[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z\\.]{2,6}$")){
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
        }
        else {
            // temporary variable vvvv
            Globals.createUser(username, firstName, email, "");
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }
    }

    public void updateLoginFields() {

        // for login
        if (((EditText) findViewById(R.id.editTextUsername)).getText().toString() != null) {
            this.username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
        }
        if (((EditText) findViewById(R.id.editTextPassword)).getText().toString() != null) {
            this.password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        }
    }

    public void updateSignUpCredentialsFields() {
        // for sign up cred
        if (((EditText) findViewById(R.id.editTextEmail)).getText().toString() != null) {
            this.email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        }
        if (((EditText) findViewById(R.id.editTextDesiredUsername)).getText().toString() != null) {
            this.desiredUsername = ((EditText) findViewById(R.id.editTextDesiredUsername)).getText().toString();
        }
        if (((EditText) findViewById(R.id.editTextDesiredPassword)).getText().toString() != null) {
            this.desiredPassword = ((EditText) findViewById(R.id.editTextDesiredPassword)).getText().toString();
        }
        if (((EditText) findViewById(R.id.editTextConfirmDesiredPassword)).getText().toString() != null) {
            this.confirmPassword = ((EditText) findViewById(R.id.editTextConfirmDesiredPassword)).getText().toString();
        }
    }

    public void updateSignUpBiographicalFields() {

        if(((EditText) findViewById(R.id.editTextFirstName)).getText().toString() != null) {
            this.firstName = ((EditText) findViewById(R.id.editTextFirstName)).getText().toString();
        }
        if(((EditText) findViewById(R.id.editTextLastName)).getText().toString() != null) {
            this.lastName = ((EditText) findViewById(R.id.editTextLastName)).getText().toString();
        }
        if(((EditText) findViewById(R.id.editTextBirthday)).getText().toString() != null) {
            this.birthday = ((EditText) findViewById(R.id.editTextBirthday)).getText().toString();
        }
        // insert update for sex variable when possible
    }

    public void clearLoginEdittexts() {
        if(((EditText) findViewById(R.id.editTextUsername)).getText().toString() != null) {
            ((EditText) findViewById(R.id.editTextUsername)).setText("");
        }
        if(((EditText) findViewById(R.id.editTextPassword)).getText().toString() != null) {
            ((EditText) findViewById(R.id.editTextPassword)).setText("");
        }
    }

    public void clearSignUpEdittexts() {
        
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

    // Getters
    public String getEmail() {
        return this.email;
    }

    public String getDesiredUsername() {
        return this.desiredUsername;
    }

    public String getDesiredPassword() {
        return this.desiredPassword;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getSex() {
        return this.sex;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    // Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDesiredUsername(String desiredUsername) {
        this.desiredUsername = desiredUsername;
    }

    public void setDesiredPassword(String desiredPassword) {
        this.desiredPassword = desiredPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
