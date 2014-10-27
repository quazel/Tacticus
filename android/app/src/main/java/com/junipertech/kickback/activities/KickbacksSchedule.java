package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Globals;
import com.junipertech.kickback.util.Util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;


public class KickbacksSchedule extends Activity {

    ArrayList<Kickback> kickbacks = Globals.kickbacks;
    ArrayList<LinearLayout> layouts;
    ArrayList<TextView> textViews;
    DateTimeFormatter st;
    DateTimeFormatter nd;
    DateTimeFormatter rd;
    DateTimeFormatter th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kickback_schedule);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        layouts = new ArrayList<LinearLayout>();
        textViews = new ArrayList<TextView>();

        createArrayOfTitles();
        createDateFormatters();
        setTitles();
        createArrayOfLists();

        if (kickbacks.size() == 0)

        addKickbacks();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kickback_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add)
        {
            Intent addIntent = new Intent(this, AddKickback.class);
            startActivity(addIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addKickbacks() {
        //temp reference variable, needs to be replaced with current datetime
        DateTime reference = DateTime.now();

        for(int i = 0; i < kickbacks.size(); i++) {
            Button bt = new Button(this);
            bt.setText(setLabel(kickbacks.get(i).getTimeRange(),kickbacks.get(i).getLocation()));
            bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            bt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            bt.setLines(3);
            int padding = Util.dpToPixels(getResources(), 9);
            bt.setPadding(padding, 0, padding, 0);

            try {
                layouts.get(Days.daysBetween(reference.toLocalDate(),
                        kickbacks.get(i).getStart().toLocalDate()).getDays())
                        .addView(bt);
            }
            catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }

    public Spanned setLabel(String time, String loc) {
        String location = "<br><font color='#c9c9c9'>"+loc+" </font>";
        return Html.fromHtml(time + "\n" + "<small>" + location + "</small>");
    }

    public void createDateFormatters(){
        st = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("st")

                .toFormatter();


        nd = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("nd")
                .toFormatter();

        rd = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("rd")
                .toFormatter();

        th = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("th")
                .toFormatter();
    }

    public void setTitles() {
        for (int i = 0; i < 14; i++) {
            DateTime day = DateTime.now().plusDays(i);
            int j = day.getDayOfMonth() % 10;
            String formatted;
            switch (j) {
                case 1:
                    formatted = day.toString(st);
                    break;
                case 2:
                    formatted = day.toString(nd);
                    break;
                case 3:
                    formatted = day.toString(rd);
                    break;
                default:
                    formatted = day.toString(th);
            }
            textViews.get(i).setText(formatted);
        }
    }

    public void createArrayOfTitles() {
        Resources r = getResources();
        String name = getPackageName();

        for(int i = 1; i < 15; i++) {
            //this goes through every title id and adds it to an array
            this.textViews.add((TextView) findViewById(r.getIdentifier("schedule_" + i, "id", name)));
        }
    }

    public void createArrayOfLists() {
        Resources r = getResources();
        String name = getPackageName();

        for(int i = 1; i < 15; i++) {
            //this goes through every linearlayout id and adds it to an array
            this.layouts.add((LinearLayout) findViewById(r.getIdentifier("list_" + i, "id", name)));
        }

    }

}
