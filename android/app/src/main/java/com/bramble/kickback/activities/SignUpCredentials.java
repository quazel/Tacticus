package com.bramble.kickback.activities;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.containers.SignUpContainer;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.models.CountryCode;
import com.bramble.kickback.networking.ConnectionHandler;

import java.io.IOException;

public class SignUpCredentials extends Activity {

    private SignUpContainer signUpContainer;

    private EditText email;
    private EditText desiredNickname;
    private EditText desiredPassword;
    private EditText countryCode;
    private String countryNumber;
    private EditText phoneNumber;
    private Button continueButton;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    private CharSequence[] countryCodeArray;
    private String selected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_credentials);
        signUpContainer = SignUpContainer.getInstance();

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        email = (EditText) findViewById(R.id.editTextEmail);
        desiredNickname = (EditText) findViewById(R.id.editTextDesiredNickname);
        desiredPassword = (EditText) findViewById(R.id.editTextDesiredPassword);
        countryCode = (EditText) findViewById(R.id.editTextCountryCode);
        phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        continueButton = (Button) findViewById(R.id.buttonSignUp);

        setEmailText(signUpContainer.getDesiredEmail());
        setDesiredNicknameText(signUpContainer.getDesiredUsername());
        setDesiredPasswordText(signUpContainer.getPassword());
        setCountryCodeText(signUpContainer.getCountryCode());
        setPhoneNumberText(signUpContainer.getDesiredPhoneNumber());


        desiredPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    showCountryCodeDialog();
                }
                return false;
            }
        });


        phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    continueSignUp();
                }
                return false;
            }
        });

        countryCodeArray = CountryCode.getAllCanonicalNames();
        countryNumber = "1";
        selected = "United States";
    }

    public void disableButtons() {
        continueButton.setEnabled(false);
    }

    public void enableButtons() {
        continueButton.setEnabled(true);
    }

    // when the continue button is pressed (sign up)
        public void continueSignUpPressed(View v) {
        continueSignUp();
    }

    public void continueSignUp() {
        // sets the instance variables inside the sign up service to the inputs
        // gathered in the sign up credentials
        signUpContainer.setDesiredEmail(email.getText().toString());
        signUpContainer.setDesiredUsername(desiredNickname.getText().toString());
        signUpContainer.setPassword(desiredPassword.getText().toString());
        signUpContainer.setCountryCode(countryNumber);
        signUpContainer.setDesiredPhoneNumber(phoneNumber.getText().toString());
        signUpContainer.setFullPhoneNumber();
        // native checks on inputs gathered
        if(signUpContainer.getDesiredEmail().equals("")) {
            Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpContainer.getDesiredEmail().matches("^[a-zA-Z0-9_\\-+%\\.]+@[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z\\.]{2,6}$")){
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
        }
        else if (signUpContainer.getDesiredUsername().equals("") || signUpContainer.getPassword().equals("")) {
            Toast.makeText(this, "Please enter desired nickname and password.", Toast.LENGTH_SHORT).show();
        }
        else if(signUpContainer.getCountryCode().equals("")) {
            Toast.makeText(this, "Please select your country code.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpContainer.getDesiredUsername().matches("^[a-zA-Z0-9_]+$")) {
            Toast.makeText(this, "Nicknames may only contain letters, numbers, and underscores (_).", Toast.LENGTH_SHORT).show();
        }
        else if(signUpContainer.getPassword().length() < 6 || signUpContainer.getPassword().length() > 20){
            Toast.makeText(this, "Passwords must be between 6 and 20 characters in length.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpContainer.getPassword().matches("^[a-zA-Z0-9_\\-!@#$%^&*]+$")) {
            Toast.makeText(this, "Passwords may only contain letters, numbers, and the special characters !@#$%^&*-_.", Toast.LENGTH_SHORT).show();
        }
        else if(phoneNumber.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter your mobile phone number.", Toast.LENGTH_SHORT).show();
        }
        else {
            disableButtons();
            ft = fm.beginTransaction();
            ft.add(R.id.loading_frame, loadingBar);
            ft.commit();
            continueButton.setText("CONTINUING");
            new CheckCredentialTask().execute(getEmailText(), getPhoneNumberText());
        }
    }

    @Override
    public void onBackPressed() {
        signUpContainer.clear();
        Intent intent = new Intent(this, AccountPortal.class);
        startActivity(intent);
        finish();
    }

    public void countryCodePressed(View view) {
        showCountryCodeDialog();
    }

    // IMPORTANT
    public void showCountryCodeDialog() {
        // country picker code!
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpCredentials.this);
        builder.setTitle("Select Your Country");
        builder.setSingleChoiceItems(countryCodeArray, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                countryNumber = CountryCode.getCountryCodeByID(item) + "";
                selected = countryCodeArray[item].toString();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                countryCode.setText(selected);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public String getEmailText() {
        return this.email.getText().toString();
    }

    public String getDesiredNicknameText() {
        return this.desiredNickname.getText().toString();
    }

    public String getDesiredPasswordText() {
        return this.desiredPassword.getText().toString();
    }

    public String getCountryCode() {
        return this.countryCode.getText().toString();
    }

    public String getPhoneNumberText() {
        return this.phoneNumber.getText().toString();
    }

    public void setEmailText(String email) {
        this.email.setText(email);
    }

    public void setDesiredNicknameText(String desiredUsername) {
        this.desiredNickname.setText(desiredUsername);
    }

    public void setDesiredPasswordText(String desiredPassword) {
        this.desiredPassword.setText(desiredPassword);
    }

    public void setCountryCodeText(String countryCode) {
        this.countryCode.setText(countryCode);
    }

    public void setPhoneNumberText(String phoneNumber) {
        this.phoneNumber.setText(phoneNumber);
    }

    // Asynchronously sends a request to check uniqueness of credentials
    private class CheckCredentialTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return new ConnectionHandler().checkCredentials(params[0], params[1]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (result.startsWith("200:")) {
                    ft = fm.beginTransaction();
                    ft.remove(loadingBar);
                    ft.commit();
                    SignUpCredentials.this.enableButtons();
                    continueButton.setText("CONTINUE");
                    Intent intent = new Intent(SignUpCredentials.this, SignUpBiographical.class);
                    startActivity(intent);
                    finish();
                }
                else if (result.startsWith("401:")) {
                    ft = fm.beginTransaction();
                    ft.remove(loadingBar);
                    ft.commit();
                    Toast.makeText(SignUpCredentials.this, result.replace("401:", ""), Toast.LENGTH_LONG).show();
                    SignUpCredentials.this.enableButtons();
                    continueButton.setText("CONTINUE");
                }
            }
            else {
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();
                Toast.makeText(SignUpCredentials.this, "An error occurred.", Toast.LENGTH_LONG).show();
                SignUpCredentials.this.enableButtons();
                continueButton.setText("CONTINUE");
            }
        }
    }

}
