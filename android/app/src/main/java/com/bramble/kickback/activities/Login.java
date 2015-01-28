package com.bramble.kickback.activities;
import com.bramble.kickback.R;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.util.Globals;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Login extends Activity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button cancelButton;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.buttonSignIn);
        cancelButton = (Button) findViewById(R.id.buttonCancelSignIn);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    commenceLogin();
                }
                return false;
            }
        });

        username.requestFocus();
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
    }

    // when the login button is pressed (login)
    public void loginPressed(View v){
        commenceLogin();
    }


    public void commenceLogin() {
        String username = getUsernameText();
        String password = getPasswordText();
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "Please enter your username and password.", Toast.LENGTH_SHORT).show();
        }
        else {
            disableButtons();
            ft = fm.beginTransaction();
            ft.add(R.id.loading_frame, loadingBar);
            ft.commit();
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
                startActivity(intent);
                finish();
            }
            else {
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();
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
