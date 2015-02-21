package com.bramble.kickback.activities;
import com.bramble.kickback.R;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Login extends Activity {

    private EditText email;
    private EditText password;
    private Button loginButton;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        email = (EditText) findViewById(R.id.editTextLoginEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.buttonSignIn);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    login();
                }
                return false;
            }
        });

        email.requestFocus();
    }


    public void disableButtons() {
        loginButton.setEnabled(false);
    }

    public void enableButtons() {
        loginButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountPortal.class);
        startActivity(intent);
        finish();
    }

    // when the login button is pressed (login)
    public void loginPressed(View v){
        login();
    }


    public void login() {
        String email = getEmailText();
        String password = getPasswordText();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Please enter your email and password.", Toast.LENGTH_SHORT).show();
        }
        else {
            disableButtons();
            ft = fm.beginTransaction();
            ft.add(R.id.loading_frame, loadingBar);
            ft.commit();
            loginButton.setText("LOGGING IN");
            new LoginTask().execute(email, password);
        }
    }

    // Asynchronously sends a login request
    private class LoginTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... params) {
            try {
                String result = new ConnectionHandler().login(params[0], params[1]);
                if (result.startsWith("200:")) {
                    result = result.replace("200:", "");
                    JSONObject json = new JSONObject(result);
                    User user = User.getUser();
                    user.setName(json.getString("name"));
                    user.setNickname(json.getString("nickname"));
                    user.setEmail(json.getString("email"));
                    user.setPhoneNumber(json.getString("phone_number"));
                    user.setSessionId(json.getString("session_id"));
                    user.setTemp(false);
                    SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("email", params[0]);
                    editor.putString("password", params[1]);
                    editor.apply();
                    return user;
                }
                else {
                    return null;
                }
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(User loggedUser) {
            if (loggedUser != null) {
                Intent intent = new Intent(Login.this, Main.class);
                startActivity(intent);
                finish();
            }
            else {
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();
                loginButton.setText("LOG IN");
                Toast.makeText(Login.this, "Invalid email or password.", Toast.LENGTH_LONG).show();
                Login.this.setPasswordText("");
                Login.this.enableButtons();
            }
        }
    }

    public String getEmailText() {
        return email.getText().toString();
    }

    public String getPasswordText() {
        return password.getText().toString();
    }

    public void setEmailText(String username) {
        this.email.setText(username);
    }

    public void setPasswordText(String password) {
        this.password.setText(password);
    }
}
