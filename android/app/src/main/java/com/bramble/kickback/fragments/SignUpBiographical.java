package com.bramble.kickback.fragments;
import com.bramble.kickback.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SignUpBiographical extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up_biographical, container, false);
        // gathers all the edit texts featured in this fragment
        firstName = (EditText) view.findViewById(R.id.editTextFirstName);
        lastName = (EditText) view.findViewById(R.id.editTextLastName);
        birthday = (EditText) view.findViewById(R.id.editTextBirthday);
        sexGroup = (RadioGroup) view.findViewById(R.id.sexGroup);
        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);
        notSpecified = (RadioButton) view.findViewById(R.id.not_specified);
        sex = "";
        continueButton = (Button) view.findViewById(R.id.buttonSignUp);
        cancelButton = (Button) view.findViewById(R.id.buttonCancelSignUp);

        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (firstName.hasFocus()) {
                    firstName.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(firstName, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            }
        });
        firstName.requestFocus();

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
        if(sex.equals(male.getText())) {
            male.setChecked(true);
        }
        else if(sex.equals(female.getText())) {
            female.setChecked(true);
        }
        else {
            notSpecified.setChecked(true);
        }
    }
}
