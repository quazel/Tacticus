package com.bramble.kickback.fragments;
import com.bramble.kickback.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Login extends Fragment{

    EditText username;
    EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_splash_screen_sign_in,
                     container, false);

        username = (EditText) view.findViewById(R.id.editTextUsername);
        password = (EditText) view.findViewById(R.id.editTextPassword);

        return view;
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
