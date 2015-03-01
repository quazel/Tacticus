package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.User;

public class AdvancedSettings extends Activity {

    private CheckBox thirdPartyMessenger;
    private CheckBox updateContacts;
    private CheckBox useDefaultMessage;

    private RelativeLayout changeDefaultMessage;

    private TextView defaultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        thirdPartyMessenger = (CheckBox) findViewById(R.id.third_party_messenger_checkbox);
        updateContacts = (CheckBox) findViewById(R.id.update_contacts_checkbox);
        useDefaultMessage = (CheckBox) findViewById(R.id.use_default_message_checkbox);

        changeDefaultMessage = (RelativeLayout) findViewById(R.id.default_message);

        defaultMessage = (TextView) findViewById(R.id.default_message_textview);

        //Temporary settings
        // defaults represented
        updateContacts.setChecked(true);
        useDefaultMessage.setChecked(true);
        defaultMessage.setText("Let's Kickback");
    }

    public void thirdPartyMessengerPressed(View view) {
        updateAdvancedSettings();
    }

    public void updateContactsPressed(View view) {
        updateAdvancedSettings();
    }

    public void useDefaultMessagePressed(View view) {
        updateAdvancedSettings();
    }

    public void changeDefaultMessagePressed(View view){
        AlertDialog.Builder builder = new   AlertDialog.Builder(AdvancedSettings.this);
        AlertDialog alertDialog = builder.create();
        alertDialog.setMessage("The default message is used when you text your friends.");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        int maxLength = 40;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        input.setFilters(fArray);
        input.setText("Let's Kickback");
        alertDialog.setView(input);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        // code that checks to see if the email is valid or used by another account
                        // if it passes checks make it the new email and close alert dialog

                    }

                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });
        alertDialog.show();
    }

    public void advancedSettingsBackButtonPressed(View view) {
        finish();
    }

    public void updateAdvancedSettings() {
        if(!useDefaultMessage.isChecked()) {
            changeDefaultMessage.setVisibility(View.GONE);
        }
        else if(useDefaultMessage.isChecked()) {
            changeDefaultMessage.setVisibility(View.VISIBLE);
        }
    }
}
