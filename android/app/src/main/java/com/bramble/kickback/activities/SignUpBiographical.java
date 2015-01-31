package com.bramble.kickback.activities;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.containers.SignUpContainer;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;


public class SignUpBiographical extends Activity {

    private SignUpContainer signUpContainer;

    // items on the fragment
    private EditText firstName;
    private EditText lastName;
    private EditText birthday;
    private String birthdayString;
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

        signUpContainer = SignUpContainer.getInstance();

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

        setFirstNameText(signUpContainer.getFirstName());
        setLastNameText(signUpContainer.getLastName());
        setBirthdayText(convertBirthday(signUpContainer.getBirthdate()));
        birthdayString = signUpContainer.getBirthdate();
        setSex(signUpContainer.getSex());

        lastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    showBirthdayLogin();
                }
                return false;
            }
        });
    }

    // when the sign up button is pressed (sign up)
    public void signUpPressed(View v){
        signUpContainer.setFirstName(firstName.getText().toString());
        signUpContainer.setLastName(lastName.getText().toString());
        signUpContainer.setBirthdate(birthdayString);
        signUpContainer.setSex(getSexText());

        if(signUpContainer.getFirstName().equals("")) {
            Toast.makeText(this, "Please enter your first name.", Toast.LENGTH_SHORT).show();
        }
        else if(signUpContainer.getLastName().equals("")) {
            Toast.makeText(this, "Please enter your last name.", Toast.LENGTH_SHORT).show();
        }
        else if(signUpContainer.getBirthdate().equals("")) {
            Toast.makeText(this, "Please enter your birth date.", Toast.LENGTH_SHORT).show();
        }
        else if(signUpContainer.getSex().equals("")) {
            Toast.makeText(this, "Please select your sex.", Toast.LENGTH_SHORT).show();
        }
        else {
            firstName.setText(signUpContainer.getFirstName());
            lastName.setText(signUpContainer.getLastName());
            birthday.setText(convertBirthday(signUpContainer.getBirthdate()));
            setSexButton(signUpContainer.getSex());

            ft = fm.beginTransaction();
            ft.add(R.id.loading_frame, loadingBar);
            ft.commit();
            new SignUpTask().execute(signUpContainer.getDesiredUsername(), signUpContainer.getPassword(),
                    signUpContainer.getDesiredEmail(), signUpContainer.getDesiredPhoneNumber(),
                    signUpContainer.getFirstName() + " " + signUpContainer.getLastName(),
                    signUpContainer.getBirthdate(), signUpContainer.getSex());
            disableButtons();
        }
    }

    // when the back button is pressed (sign up)
    public void backSignUpPressed(View v){
        signUpContainer.setFirstName(firstName.getText().toString());
        signUpContainer.setLastName(lastName.getText().toString());
        signUpContainer.setBirthdate(birthdayString);
        signUpContainer.setSex(getSexText());
        Intent intent = new Intent(this, SignUpCredentials.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        signUpContainer.setFirstName(firstName.getText().toString());
        signUpContainer.setLastName(lastName.getText().toString());
        signUpContainer.setBirthdate(birthdayString);
        signUpContainer.setSex(getSexText());
        Intent intent = new Intent(this, SignUpCredentials.class);
        startActivity(intent);
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

    public void birthdayPressed(View view) {
        showBirthdayLogin();
    }

    public void showBirthdayLogin() {
        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog =new DatePickerDialog(this, new BirthdatePickerDialog(), year, month, day);
        dialog.getDatePicker().setMaxDate(current.getTimeInMillis());
        dialog.show();
    }

    public String convertBirthday(String b) {
        if(!b.equals("")) {
            String[] tokens = b.split("-");
            return tokens[1] + "/" + tokens[2] + "/" + tokens[0];
        }
        else return "";
    }

    private class BirthdatePickerDialog implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar occurs = Calendar.getInstance();
            occurs.set(Calendar.YEAR, year);
            occurs.set(Calendar.MONTH, month);
            occurs.set(Calendar.DAY_OF_MONTH, day);
            birthdayString = (year + "-" + (month+1) + "-" + day);
            setBirthdayText((month+1)+"/"+day+"/"+year);
            lastName.clearFocus();
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
                    JSONObject json = new JSONObject(result);
                    User user = User.getUser();
                    user.setName(json.getString("name"));
                    user.setNickname(json.getString("username"));
                    user.setEmail(json.getString("email"));
                    user.setPhoneNumber(json.getString("phone_number"));
                    user.setTemp(true);
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
                Intent intent = new Intent(SignUpBiographical.this, Main.class);
                startActivity(intent);
                finish();
            }
            else {
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();
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

    public void setSex(String sex) {
        this.sex = sex;
        setSexButton(this.sex);
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

    public void onMaleClicked(View view) {
        setSex("male");
    }

    public void onFemaleClicked(View view) {
        setSex("female");
    }

    public void onNotSpecifiedClicked(View view) {
        setSex("not specified");
    }
}
