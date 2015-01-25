package com.bramble.kickback.fragments;
import com.bramble.kickback.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class SignUpCredentials extends Fragment {

    EditText email;
    EditText desiredUsername;
    EditText desiredPassword;
    EditText confirmDesiredPassword;
    EditText phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_splash_screen_sign_up,
                     container, false);

        email = (EditText) view.findViewById(R.id.editTextEmail);
        desiredUsername = (EditText) view.findViewById(R.id.editTextDesiredUsername);
        desiredPassword = (EditText) view.findViewById(R.id.editTextDesiredPassword);
        confirmDesiredPassword = (EditText) view.findViewById(R.id.editTextConfirmDesiredPassword);
        phoneNumber = (EditText) view.findViewById(R.id.editTextPhoneNumber);

        email.requestFocus();

        return view;
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
