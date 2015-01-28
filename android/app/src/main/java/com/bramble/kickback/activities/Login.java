package com.bramble.kickback.activities;
import com.bramble.kickback.R;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.util.Globals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class Login extends Activity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button cancelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.buttonSignIn);
        cancelButton = (Button) findViewById(R.id.buttonCancelSignIn);
    }

    public void disableButtons() {
        loginButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }

    public void enableButtons() {
        loginButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }

    // when the cancel button is pressed (login)
    public void cancelSignInPressed(View v){
        finish();
        overridePendingTransition(0,0);
    }

    // when the login button is pressed (login)
    public void loginPressed(View v){
        String username = getUsernameText();
        String password = getPasswordText();
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "Please enter your username and password.", Toast.LENGTH_SHORT).show();
        }
        else {
            disableButtons();
            new LoginTask().execute(username, password);
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
                Intent intent = new Intent(Login.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(Login.this, "Invalid username or password.", Toast.LENGTH_LONG).show();
                Login.this.setPasswordText("");
                Login.this.enableButtons();
            }
        }
    }

    public String getUsernameText() {
        return username.getText().toString();
    }

    public String getPasswordText() {
        return password.getText().toString();
    }

    public void setUsernameText(String username) {
        this.username.setText(username);
    }

    public void setPasswordText(String password) {
        this.password.setText(password);
    }
}
