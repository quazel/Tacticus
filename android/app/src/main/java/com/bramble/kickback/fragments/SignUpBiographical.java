package com.bramble.kickback.fragments;
import com.bramble.kickback.R;
import com.bramble.kickback.service.SignUpService;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SignUpBiographical extends Fragment {

    EditText firstName;
    EditText lastName;
    EditText birthday;
    RadioGroup sexGroup;
    RadioButton male;
    RadioButton female;
    RadioButton notSpecified;
    String sex;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_splash_screen_sign_up_cont, container, false);

        firstName = (EditText) view.findViewById(R.id.editTextFirstName);
        lastName = (EditText) view.findViewById(R.id.editTextLastName);
        birthday = (EditText) view.findViewById(R.id.editTextBirthday);
        sexGroup = (RadioGroup) view.findViewById(R.id.sexGroup);
        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);
        notSpecified = (RadioButton) view.findViewById(R.id.not_specified);

        return view;
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

    public void setSexButton(String sex) {
        this.sex = sex;
    }

    public void clear() {
        firstName.setText("");
        lastName.setText("");
        birthday.setText("");
        male.setChecked(false);
        female.setChecked(false);
        notSpecified.setChecked(false);
    }
}
