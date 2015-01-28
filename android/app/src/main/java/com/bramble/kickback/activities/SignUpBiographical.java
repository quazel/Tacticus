package com.bramble.kickback.activities;
import com.bramble.kickback.R;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.service.SignUpService;
import com.bramble.kickback.util.Globals;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;


public class SignUpBiographical extends Activity {

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

    // items on the fragment
    private EditText firstName;
    private EditText lastName;
    private EditText birthday;
    private RadioGroup sexGroup;
    private RadioButton male;
    private RadioButton female;
    private RadioButton notSpecified;
    private String sex;
    private Button continueButton;
    private Button cancelButton;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_biographical);
        signUpServiceIntent = new Intent(this, SignUpService.class);
        bindService(signUpServiceIntent, signUpConnection, Context.BIND_AUTO_CREATE);

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        // gathers all the edit texts featured in this fragment
        firstName = (EditText) findViewById(R.id.editTextFirstName);
        lastName = (EditText) findViewById(R.id.editTextLastName);
        birthday = (EditText) findViewById(R.id.editTextBirthday);
        sexGroup = (RadioGroup) findViewById(R.id.sexGroup);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        notSpecified = (RadioButton) findViewById(R.id.not_specified);
        continueButton = (Button) findViewById(R.id.buttonSignUp);
        cancelButton = (Button) findViewById(R.id.buttonCancelSignUp);

        firstName.setText(signUpService.getFirstName());
        lastName.setText(signUpService.getLastName());
        birthday.setText(signUpService.getBirthday());
        sex = signUpService.getSex();
        setSexButton(sex);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    sex = "male";
                    break;
            case R.id.female:
                if (checked)
                    sex = "female";
                    break;
            case R.id.not_specified:
                if (checked)
                    sex = "not specified";
                    break;
        }
    }


    // when the sign up button is pressed (sign up)
    public void signUpPressed(View v){
        signUpService.setFirstName(firstName.getText().toString());
        signUpService.setLastName(lastName.getText().toString());
        signUpService.setBirthday(birthday.getText().toString());
        signUpService.setSex(getSexText());

        if(signUpService.getFirstName().equals("")) {
            Toast.makeText(this, "Please enter your first name.", Toast.LENGTH_SHORT).show();
        }
        else if(signUpService.getLastName().equals("")) {
            Toast.makeText(this, "Please enter your last name.", Toast.LENGTH_SHORT).show();
        }
        else if(!signUpService.getEmail().matches("^[a-zA-Z0-9_\\-+%\\.]+@[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z\\.]{2,6}$")){
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
        }
        else {
            Globals.createUser(signUpService.getDesiredUsername(), signUpService.getFirstName(),
                    signUpService.getEmail(), signUpService.getPhoneNumber());

            firstName.setText(signUpService.getFirstName());
            lastName.setText(signUpService.getLastName());
            birthday.setText(signUpService.getBirthday());
            setSexButton(signUpService.getSex());

            new SignUpTask().execute(signUpService.getDesiredUsername(), signUpService.getDesiredPassword(),
                    signUpService.getEmail(), signUpService.getPhoneNumber(),
                    signUpService.getFirstName() + " " + signUpService.getLastName(),
                    signUpService.getBirthday(), signUpService.getSex());
            disableButtons();
        }
    }

    // when the back button is pressed (sign up)
    public void backSignUpPressed(View v){
       finish();
    }

    public void disableButtons() {
        continueButton.setEnabled(false);
        cancelButton.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);
        notSpecified.setEnabled(false);
    }

    public void enableButtons() {
        continueButton.setEnabled(true);
        cancelButton.setEnabled(true);
        sexGroup.setEnabled(true);
        male.setEnabled(true);
        female.setEnabled(true);
        notSpecified.setEnabled(true);
    }

    public void setSexButton(String sex) {
        if(sex.equals("male")) {
            male.setChecked(true);
        }
        else if(sex.equals("female")) {
            female.setChecked(true);
        }
        else if(sex.equals("not specified")){
            notSpecified.setChecked(true);
        }
    }

    public void birthdayPressed(View view) {
        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, R.style.MyTheme, new BirthdatePickerDialog(), year, month, day).show();
    }

    private class BirthdatePickerDialog implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar occurs = Calendar.getInstance();
            occurs.set(Calendar.YEAR, year);
            occurs.set(Calendar.MONTH, month);
            occurs.set(Calendar.DAY_OF_MONTH, day);
            setBirthdayText(year + "-" + month+1 + "-" + day);
        }
    }

    // Asynchronously sends a sign up request
    private class SignUpTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... params) {
            try {
                String result = new ConnectionHandler().register(params[0], params[1], params[2],
                        params[3], params[4], params[5],
                        params[6]);
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
                Intent intent = new Intent(SignUpBiographical.this, Home.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(SignUpBiographical.this, "An error occurred.", Toast.LENGTH_LONG).show();
                SignUpBiographical.this.enableButtons();
            }
        }
    }

    public String getFirstNameText() {
        return this.firstName.getText().toString();
    }

    public String getLastNameText() {
        return this.lastName.getText().toString();
    }

    public String getBirthdayText() {
        return this.birthday.getText().toString();
    }

    public String getSexText() {
        return sex;
    }

    public void setFirstNameText(String firstName) {
        this.firstName.setText(firstName);
    }

    public void setLastNameText(String lastName) {
        this.lastName.setText(lastName);
    }

    public void setBirthdayText(String birthday) {
        this.birthday.setText(birthday);
    }
}
