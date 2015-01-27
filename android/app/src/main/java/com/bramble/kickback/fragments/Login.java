package com.bramble.kickback.fragments;
import com.bramble.kickback.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Fragment{

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login,
                     container, false);

        username = (EditText) view.findViewById(R.id.editTextUsername);
        password = (EditText) view.findViewById(R.id.editTextPassword);
        loginButton = (Button) view.findViewById(R.id.buttonSignIn);
        cancelButton = (Button) view.findViewById(R.id.buttonCancelSignIn);


        //InputMethodManager imm = (InputMethodManager)
                //getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);

        return view;
    }

    public void disableButtons() {
        loginButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }

    public void enableButtons() {
        loginButton.setEnabled(true);
        cancelButton.setEnabled(true);
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
