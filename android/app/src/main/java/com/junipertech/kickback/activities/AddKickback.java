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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kickback);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        month = (Spinner)findViewById(R.id.months_input);
        day = (Spinner)findViewById(R.id.day_input);
        year = (Spinner)findViewById(R.id.year_input);

        currentTime = new DateTime();
        inTwoWeeks = currentTime.plusDays(13); //ONLY ADDING 13 Because it is inclusive to current day

        start_am = true;
        end_am = true;

        setupSpinners();
        //setSpinnerListeners();



        //updateDaySpinner();
        //ArrayAdapter<String> dayAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dayChoices);
        //dayAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //day.setAdapter(dayAdapt);





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

    }

    private void updateMonthSpinner(){
        String currentMonth = Integer.toString(currentTime.getMonthOfYear());
        String soonMonth = Integer.toString(inTwoWeeks.getMonthOfYear());
        if(currentMonth.compareTo(soonMonth) == 0){ //If the years are the same
            monthChoices = new String[1];
            monthChoices[0] = currentMonth;
            //Todo Set spinner unchangeable
        }else{
            monthChoices = new String[2];
            monthChoices[0] = currentMonth;
            monthChoices[1] = soonMonth;
        }
    }

    private void setupSpinners(){
        //First setup the year spinner
        String currentYear = Integer.toString(currentTime.getYear());
        String soonYear = Integer.toString(inTwoWeeks.getYear());
        String[] yearChoices;
        if(currentYear.compareTo(soonYear) == 0){ //If the years are the same
            yearChoices = new String[1];
            yearChoices[0] = currentYear;
            //Todo Set spinner unchangeable
        }else{
            yearChoices = new String[2];
            yearChoices[0] = currentYear;
            yearChoices[1] = soonYear;
        }
        ArrayAdapter<String> yearAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, yearChoices);
        yearAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearAdapt);

        //Now setup the month spinner
        //TODO ACCOUNT FOR YEAR ROLLOVER
        updateMonthSpinner();
        ArrayAdapter<String> monthAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monthChoices);
        monthAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(monthAdapt);

        //Finally setup the day spinner
        updateDaySpinner();
        ArrayAdapter<String> dayAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dayChoices);
        dayAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dayAdapt);

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
        return new DateTime();
    }
    private DateTime getEnd(){
        return new DateTime();
    }

    public void addNewKickback(View view){

        EditText locationInfo = (EditText) findViewById(R.id.activity_location);
        String location = locationInfo.getText().toString();
        DateTime start = getStart();
        DateTime end = getEnd();

        if(end.getHourOfDay() == 0 || end.getHourOfDay() == 24) { //TODO CHECK THIS
            end = end.plusDays(1);
        }

        //Check if it is a valid kickback
        //Start is before end time
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