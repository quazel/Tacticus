package com.bramble.kickback.fragments;
import com.bramble.kickback.R;

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
    RadioButton sexButton;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_splash_screen_sign_up_cont, container, false);

        firstName = (EditText) view.findViewById(R.id.editTextFirstName);
        lastName = (EditText) view.findViewById(R.id.editTextLastName);
        birthday = (EditText) view.findViewById(R.id.editTextBirthday);
        sexGroup = (RadioGroup) view.findViewById(R.id.sexGroup);
        addListenerOnButton();

        return view;
    }

    public void addListenerOnButton() {

        sexGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = sexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                sexButton = (RadioButton) v.findViewById(selectedId);
            }

        });
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
        return this.sexButton.getText().toString();
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

    public void setSexButton(String firstName) {
        this.sexButton.setText(firstName);
    }

}
