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

import java.util.ArrayList;

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

    ArrayList<Kickback> otherKickbacks = Globals.kickbacks; //TODO VARIABLE THAT STORES THE OTHER KICKBACKS WE WANT TO COMPARE OURS TO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kickback);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        month = (Spinner)findViewById(R.id.months_input);
        day = (Spinner)findViewById(R.id.day_input);

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

    private void updateDaySpinner(){
        int selectedMonth = monthToInteger(month.getSelectedItem().toString());

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

        if(currentMonth == soonMonth){ //If the months are the same
            monthChoices = new String[1];
            monthChoices[0] = currentTime.monthOfYear().getAsText();
            //Todo Set spinner unchangeable
        }else{
            monthChoices = new String[2];
            monthChoices[0] = currentTime.monthOfYear().getAsText();
            monthChoices[1] = inTwoWeeks.monthOfYear().getAsText();
        }

        monthAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monthChoices);
        monthAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(monthAdapt);
    }

    private void setupSpinners(){
        //No more Year Spinner

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

        int monthSelected = monthToInteger(month.getSelectedItem().toString());
        int daySelected = Integer.parseInt(day.getSelectedItem().toString());
        int yearSelected = getYearSelected(monthSelected);

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

        int monthSelected = monthToInteger(month.getSelectedItem().toString());
        int daySelected = Integer.parseInt(day.getSelectedItem().toString());
        int yearSelected = getYearSelected(monthSelected);

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
            boolean thereAreNoKickbackConflicts = true;
            for(int i = 0; i<otherKickbacks.size();i++){
                if(otherKickbacks.get(i).getStart().isAfter(start) && otherKickbacks.get(i).getStart().isBefore(end) || otherKickbacks.get(i).getStop().isAfter(start) && otherKickbacks.get(i).getStop().isBefore(end)){
                    thereAreNoKickbackConflicts = false;
                }
            }


            if(thereAreNoKickbackConflicts){
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

    }

    public void cancelKickback(View view){
        Intent intent = new Intent(this, KickbacksSchedule.class);
        startActivity(intent);
    }

    private int monthToInteger(String month){
        if (month.toLowerCase().compareTo("january") == 0) {
            return 1;
        }else if(month.toLowerCase().compareTo("february") == 0) {
            return 2;
        }else if(month.toLowerCase().compareTo("march") == 0) {
            return 3;
        }else if(month.toLowerCase().compareTo("april") == 0) {
            return 4;
        }else if(month.toLowerCase().compareTo("may") == 0) {
            return 5;
        }else if(month.toLowerCase().compareTo("june") == 0) {
            return 6;
        }else if(month.toLowerCase().compareTo("july") == 0) {
            return 7;
        }else if(month.toLowerCase().compareTo("august") == 0) {
            return 8;
        }else if(month.toLowerCase().compareTo("september") == 0) {
            return 9;
        }else if(month.toLowerCase().compareTo("october") == 0) {
            return 10;
        }else if(month.toLowerCase().compareTo("november") == 0) {
            return 11;
        }else{
            return 12;
        }
    }

    private int getYearSelected(int selectedMonth){
        if(selectedMonth == currentTime.getMonthOfYear()){
            return currentTime.getYear();
        }else{
            return inTwoWeeks.getYear();
        }
    }

}