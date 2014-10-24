package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Globals;

import org.joda.time.DateTime;

public class AddKickback extends Activity {
    Spinner month, day, year;

    String[] dayChoices;
    String[] monthChoices;

    DateTime currentTime;
    DateTime inTwoWeeks;

    private boolean start_am;
    private boolean end_am;

    EditText locationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kickback);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        month = (Spinner) findViewById(R.id.months_input);
        day = (Spinner) findViewById(R.id.day_input);
        year = (Spinner) findViewById(R.id.year_input);

        locationInput = (EditText) findViewById(R.id.activity_location);

        start_am = true;
        end_am = true;

        currentTime = new DateTime();
        inTwoWeeks = currentTime;
        inTwoWeeks = inTwoWeeks.plusDays(14);

        setYearSpinner();
        setMonthSpinner();
        setDaySpinner();

        //TODO Update spinners properly

        setSpinnerListeners();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_kickback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void setYearSpinner() { //COMPLETE
        String[] yearChoice;
        if (currentTime.year() != inTwoWeeks.year()) {
            yearChoice = new String[2];
            yearChoice[0] = Integer.toString(currentTime.getYear());
            yearChoice[1] = Integer.toString(inTwoWeeks.getYear());
        } else {
            yearChoice = new String[1];
            yearChoice[0] = Integer.toString(currentTime.getYear());
        }

        ArrayAdapter<String> yearAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearChoice);
        yearAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearAdapt);
    }

    private void setMonthSpinner() { //COMPLETE
        ArrayAdapter<String> monthAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthChoices);
        monthAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(monthAdapt);
    }

    private void setDaySpinner() { //COMPLETE
        ArrayAdapter<String> dayAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dayChoices);
        dayAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dayAdapt);
    }



    private void updateDaySpinner(){ //TODO REWORK SO THAT DAYS ARE ONLY WITHIN THE CURRENT DATE AND TWO WEEKS OUT. Based on month spinner listener

        /*
        int iMonth = month.getSelectedItemPosition()+1;
        int iYear = Integer.parseInt(year.getSelectedItem().toString());
        DateTime dt = new DateTime(iYear,iMonth,1,1,0);
        int monthDays = dt.dayOfMonth().getMaximumValue();

        dayChoices = new String[monthDays];
        for(int i = 0; i<monthDays; i++){
            dayChoices[i] = Integer.toString(i+1); //There has to be a better way of doing this
        }
        day.setSelection(currentTime.getDayOfMonth()-1);
        */
    }

    private void updateMonthSpinner(){ //Given the year and the current/two weeks date what months can be loaded into the spinner
        //TODO THIS

        updateDaySpinner();
    }

    public void endTimeToggle(View view) { // COMPLETE
        end_am = !((ToggleButton) view).isChecked();
    }
    public void startTimeToggle(View view) { //COMPLETE
        start_am = !((ToggleButton) view).isChecked();
    }

    private DateTime getStart(){ //TODO DOUBLE CHECK THESE WITH NEW IMPLEMENTATION

        /*
        Spinner startTimeHours =(Spinner)findViewById(R.id.start_time_hours);
        Spinner startTimeMinutes =(Spinner)findViewById(R.id.start_time_minutes);

        int iMonth = month.getSelectedItemPosition()+1;
        int iDay = Integer.parseInt(day.getSelectedItem().toString());
        int iYear = Integer.parseInt(year.getSelectedItem().toString());

        int iHours = Integer.parseInt(startTimeHours.getSelectedItem().toString());
        if(!start_am){
            iHours+=12;
        }
        int iMinutes = Integer.parseInt(startTimeMinutes.getSelectedItem().toString());

        return new DateTime(iYear,iMonth,iDay,iHours,iMinutes);
        */
    }
    private DateTime getEnd(){ //TODO DOUBLE CHECK THESE WITH NEW IMPLEMENTATION
        /*
        Spinner endTimeHours =(Spinner)findViewById(R.id.end_time_hours);
        Spinner endTimeMinutes =(Spinner)findViewById(R.id.end_time_minutes);

        int iMonth = month.getSelectedItemPosition()+1;
        int iDay = Integer.parseInt(day.getSelectedItem().toString());
        int iYear = Integer.parseInt(year.getSelectedItem().toString());

        int iHours = Integer.parseInt(endTimeHours.getSelectedItem().toString());
        if(!end_am){
            iHours+=12;
        }
        int iMinutes = Integer.parseInt(endTimeMinutes.getSelectedItem().toString());

        return new DateTime(iYear,iMonth,iDay,iHours,iMinutes);
        */
    }

    public void addNewKickback(View view){

        String location = locationInput.getText().toString();
        DateTime start = getStart();
        DateTime end = getEnd();

        if(end.getHourOfDay() == 0 || end.getHourOfDay() == 24) {
            end = end.plusDays(1);
        }

        if(end.isAfter(start)){ //TODO ADD MORE CHECKS FOR STUFF LIKE IF WE ARE TRYING TO ADD KICKBACKS WITH CONFLICTING DURATIONS
            Kickback creationKickback = new Kickback(start,end,location);
            Globals.addKickback(creationKickback);
            //TODO ACTUALLY CREATE THE KICKBACK RIGHT NOW WE ARE TROWING KICKBACK AWAY

            Context context = getApplicationContext();
            CharSequence text = "Kickback added!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Intent intent = new Intent(this, KickbacksSchedule.class);
            startActivity(intent);
            finish(); //Prevents user from coming back to this activity after submission
        } else {
            Context context = getApplicationContext();
            CharSequence text = "The end time is not after the start time! Please try again!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void cancelKickback(View view){
        Intent intent = new Intent(this, KickbacksSchedule.class);
        startActivity(intent);
    }

    public void setSpinnerListeners(){
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateDaySpinner(); //Load Day Spinner on month change
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do Nothing
            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateMonthSpinner(); //Load month spinner on year change
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do Nothing
            }
        });

    }

}
