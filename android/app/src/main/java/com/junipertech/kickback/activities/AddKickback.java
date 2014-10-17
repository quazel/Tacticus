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
    DateTime currentTime;

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
        month.setSelection(currentTime.getMonthOfYear()-1);

        setYearSpinner();

        //setDaySpinner
        updateDaySpinner();
        ArrayAdapter<String> dayAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dayChoices);
        dayAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dayAdapt);

        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateDaySpinner(); //Load Day Spinner on month change
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do Nothing
            }
        });

        start_am = true;
        end_am = true;

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
        int iMonth = month.getSelectedItemPosition()+1;
        int iYear = Integer.parseInt(year.getSelectedItem().toString());
        DateTime dt = new DateTime(iYear,iMonth,1,1,0);
        int monthDays = dt.dayOfMonth().getMaximumValue();

        dayChoices = new String[monthDays];
        for(int i = 0; i<monthDays; i++){
            dayChoices[i] = Integer.toString(i+1); //There has to be a better way of doing this
        }
        day.setSelection(currentTime.getDayOfMonth()-2); //Why the heck is this 2?
    }

    private void setYearSpinner(){
        String thisYear = Integer.toString(currentTime.getYear());
        String nextYear = Integer.toString(currentTime.getYear()+1);
        final String[] yearChoices = {thisYear,nextYear};
        ArrayAdapter<String> yearAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, yearChoices);
        yearAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearAdapt);
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

        int iMonth = month.getSelectedItemPosition()+1;
        int iDay = Integer.parseInt(day.getSelectedItem().toString());
        int iYear = Integer.parseInt(year.getSelectedItem().toString());

        int iHours = Integer.parseInt(startTimeHours.getSelectedItem().toString());
        if(!start_am){
            iHours+=12;
        }
        int iMinutes = Integer.parseInt(startTimeMinutes.getSelectedItem().toString());

        return new DateTime(iYear,iMonth,iDay,iHours,iMinutes);
    }
    private DateTime getEnd(){
        Spinner endTimeHours =(Spinner)findViewById(R.id.end_time_hours);
        Spinner endTimeMinutes =(Spinner)findViewById(R.id.end_time_minutes);
        Spinner startTimeHours =(Spinner)findViewById(R.id.start_time_hours);
        Spinner startTimeMinutes =(Spinner)findViewById(R.id.start_time_minutes);

        int iMonth = month.getSelectedItemPosition()+1;
        int iDay = Integer.parseInt(day.getSelectedItem().toString());
        int iYear = Integer.parseInt(year.getSelectedItem().toString());

        int iHours = Integer.parseInt(endTimeHours.getSelectedItem().toString());
        if(!end_am){
            iHours+=12;
        }
        int iMinutes = Integer.parseInt(endTimeMinutes.getSelectedItem().toString());

        return new DateTime(iYear,iMonth,iDay,iHours,iMinutes);
    }

    public void addNewKickback(View view){

        EditText locationInfo = (EditText) findViewById(R.id.activity_location);
        String location = locationInfo.getText().toString();
        DateTime start = getStart();
        DateTime end = getEnd();
        if(end.isAfter(start)){
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

}
