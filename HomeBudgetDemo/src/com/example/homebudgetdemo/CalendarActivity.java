package com.example.homebudgetdemo;

import com.massango.homebudgetdemo.AddMonthlyItemActivity;
import com.massango.homebudgetdemo.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
import android.support.v4.app.NavUtils;

public class CalendarActivity extends Activity {
	 CalendarView calendar;
	 String date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		// Show the Up button in the action bar.
		setupActionBar();
		calendar=(CalendarView)findViewById(R.id.calendarView1);
		initializeCalendar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	public void initializeCalendar() {
		
	       
		// sets whether to show the week number.
		calendar.setShowWeekNumber(false);

		// sets the first day of week according to Calendar.
		// here we set Monday as the first day of the Calendar
		calendar.setFirstDayOfWeek(2);

		//The background color for the selected week.
		calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.darkgreen));
		
		//sets the color for the dates of an unfocused month. 
		calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));
	   // calendar.setFocusedMonthDateColor(getResources().getColor(R.color.transparent));
		//sets the color for the separator line between weeks.
		calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));
		
		//sets the color for the vertical bar shown at the beginning and at the end of the selected date.
		calendar.setSelectedDateVerticalBar(R.color.grey);
		
		//sets the listener to be notified upon selected date change.
		calendar.setOnDateChangeListener(new OnDateChangeListener() {

			//show the selected date as a toast
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
				String monthsArray[] ={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
				date=day + "/" + monthsArray[month] + "/" + year;
				Toast.makeText(getApplicationContext(), day + "/" + monthsArray[month] + "/" + year, Toast.LENGTH_LONG).show();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_addExp:
			
			return true;
		case R.id.action_Cal_Back: 
			Intent intentMain=new Intent(getApplicationContext(),MainActivity.class);
			startActivity(intentMain);
			return true;
		case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
