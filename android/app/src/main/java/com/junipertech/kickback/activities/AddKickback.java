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
import org.joda.time.format.DateTimeFormat;

public class AddKickback extends Activity {
    Spinner month, day, year;

    String[] dayChoices;
    String[] monthChoices;

    DateTime currentTime;
    DateTime inTwoWeeks;

    private boolean start_am;
    private boolean end_am;

    ArrayAdapter<String> monthAdapt;
    ArrayAdapter<String> dayAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kickback);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        month = (Spinner)findViewById(R.id.months_input);
        day = (Spinner)findViewById(R.id.day_input);
        year = (Spinner)findViewById(R.id.year_input);

        //currentTime = new DateTime(2014,12,25,2,2); REMOVE FOR END OF YEAR ROLLOVER TESTING ONLY
        currentTime = new DateTime();
        inTwoWeeks = currentTime.plusDays(13); //ONLY ADDING 13 Because it is inclusive to current day

        start_am = true;
        end_am = true;

        setupSpinners();

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

    private void updateDaySpinner(){ //TODO MAKE THIS WORK
        int selectedMonth = Integer.parseInt(month.getSelectedItem().toString());

        int currentDay = currentTime.getDayOfMonth();
        int soonDay = inTwoWeeks.getDayOfMonth();

        int from;
        int to;

        if(selectedMonth == currentTime.getMonthOfYear()){
            if(currentDay<soonDay){
                from = currentDay;
                to = soonDay;

                dayChoices = new String[14];
                int index = 0;
                for(int i=from;i<=to;i++){
                    dayChoices[index] = Integer.toString(i);
                    index++;
                }
            }else{
                from = currentDay;
                to = currentTime.dayOfMonth().getMaximumValue();

                dayChoices = new String[1+(to-from)];
                int index = 0;
                for(int i=from;i<=to;i++){
                    dayChoices[index] = Integer.toString(i);
                    index++;
                }
            }
        }else{
            from = 1;
            to = soonDay;

            dayChoices = new String[1+(to-from)];
            int index = 0;
            for(int i=from;i<=to;i++){
                dayChoices[index] = Integer.toString(i);
                index++;
            }
        }

        dayAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dayChoices);
        dayAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dayAdapt);

    }

    private void updateMonthSpinner(){
        int currentMonth = currentTime.getMonthOfYear();
        int soonMonth = inTwoWeeks.getMonthOfYear();

        if(currentMonth == soonMonth){ //If the years are the same
            monthChoices = new String[1];
            monthChoices[0] = Integer.toString(currentMonth);
            //Todo Set spinner unchangeable
        }else{
            int yearSelected = Integer.parseInt(year.getSelectedItem().toString());
            if(yearSelected == currentTime.getYear() && yearSelected == inTwoWeeks.getYear()){
                monthChoices = new String[2];
                monthChoices[0] = Integer.toString(currentMonth);
                monthChoices[1] = Integer.toString(soonMonth);
            }else{
                monthChoices = new String[1];
                if(yearSelected == currentTime.getYear()){
                    monthChoices[0] = Integer.toString(currentMonth);
                }else{ //Its the two weeks time
                    monthChoices[0] = Integer.toString(soonMonth);
                }
            }

        }

        monthAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monthChoices);
        monthAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(monthAdapt);
    }

    private void setupSpinners(){
        //First setup the year spinner
        int currentYear = currentTime.getYear();
        int soonYear = inTwoWeeks.getYear();

        String[] yearChoices;
        if(currentYear == soonYear){ //If the years are the same
            yearChoices = new String[1];
            yearChoices[0] = Integer.toString(currentYear);
            //Todo Set spinner unchangeable
        }else{
            yearChoices = new String[2];
            yearChoices[0] = Integer.toString(currentYear);
            yearChoices[1] = Integer.toString(soonYear);
        }
        ArrayAdapter<String> yearAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, yearChoices);
        yearAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearAdapt);

        //Now setup the month spinner
        updateMonthSpinner();

        //Finally setup the day spinner
        updateDaySpinner();

        //Afterwards set the spinner listeners
        setSpinnerListeners();

    }

    private void setSpinnerListeners(){
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
                updateMonthSpinner(); //Load Day Spinner on month change
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do Nothing
            }
        });
    }

    public void endTimeToggle(View view) {
        end_am = !((ToggleButton) view).isChecked();
    }
    public void startTimeToggle(View view) {
        start_am = !((ToggleButton) view).isChecked();
    }

    private DateTime getStart(){
        Spinner startTimeHours =(Spinner)findViewById(R.id.start_time_hours);
        Spinner startTimeMinutes =(Spinner)findViewById(R.id.start_time_minutes);

        int monthSelected = Integer.parseInt(month.getSelectedItem().toString());
        int daySelected = Integer.parseInt(day.getSelectedItem().toString());
        int yearSelected = Integer.parseInt(year.getSelectedItem().toString());

        int hourSelected = Integer.parseInt(startTimeHours.getSelectedItem().toString());
        if(!start_am){
            hourSelected+=12;
        }
        int minuteSelected = Integer.parseInt(startTimeMinutes.getSelectedItem().toString());

        return new DateTime(yearSelected,monthSelected,daySelected,hourSelected,minuteSelected);
    }
    private DateTime getEnd(){
        Spinner endTimeHours =(Spinner)findViewById(R.id.end_time_hours);
        Spinner endTimeMinutes =(Spinner)findViewById(R.id.end_time_minutes);

        int monthSelected = Integer.parseInt(month.getSelectedItem().toString());
        int daySelected = Integer.parseInt(day.getSelectedItem().toString());
        int yearSelected = Integer.parseInt(year.getSelectedItem().toString());

        int hourSelected = Integer.parseInt(endTimeHours.getSelectedItem().toString());
        if(!end_am){
            hourSelected+=12;
        }
        int minuteSelected = Integer.parseInt(endTimeMinutes.getSelectedItem().toString());

        return new DateTime(yearSelected,monthSelected,daySelected,hourSelected,minuteSelected);
    }

    public void addNewKickback(View view){

        EditText locationInfo = (EditText) findViewById(R.id.activity_location);
        String location = locationInfo.getText().toString();
        DateTime start = getStart();
        DateTime end = getEnd();

        if(end.getHourOfDay() == 0 || end.getHourOfDay() == 24) { //TODO CHECK THIS
            end = end.plusDays(1);
        }

        if(end.isAfter(start)){
            if(true){ //TODO CHECK KICKBACK OVERLAP HERE
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
            }else{
                Context context = getApplicationContext();
                CharSequence text = "The kickback being created conflicts with another";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }else{
            Context context = getApplicationContext();
            CharSequence text = "The end time is before the start!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        //No overlap with other kickbacks

    }

    public void cancelKickback(View view){
        Intent intent = new Intent(this, KickbacksSchedule.class);
        startActivity(intent);
    }

}
 
/*
Context context = getApplicationContext();
CharSequence text = "TEXT";
int duration = Toast.LENGTH_SHORT;
Toast toast = Toast.makeText(context, text, duration);
toast.show();
*/