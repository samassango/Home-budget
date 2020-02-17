package com.example.homebudgetdemo;

import com.massango.background.HomeBudgetDatabase;

import android.R.drawable;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;

public class SettingsActivity extends Activity {
	HomeBudgetDatabase db;
	SimpleCursorAdapter adapter;
	ListView lstSetting;
    String[] settings ={"About","Help","FAQ","Contact","Developers"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.
		setupActionBar();
		db=new HomeBudgetDatabase(getApplicationContext());
		lstSetting=(ListView)findViewById(R.id.lstSettings);
		try {
			db.openBD();
			db.addSettings(settings);
			db.closeDB();
			
			
     	} catch (Exception e) {
			// TODO: handle exception
		}
		/**try {
			db.openBD();
			Cursor c=db.getSettings();
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
				adapter=new SimpleCursorAdapter(this, R.layout.settings_layout, c, new String[]{"settings"}, new int[] {  R.id.lblSetting});
			}
			lstSetting.setAdapter(adapter);
			db.closeDB();
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		int[] resource={drawable.ic_input_add,drawable.ic_dialog_email,drawable.ic_dialog_alert,drawable.ic_input_get};
		ArrayAdapter<String> homeAdapter=new ArrayAdapter<String>(this, R.layout.settings_layout,R.id.lblSetting, settings);
		
		lstSetting.setAdapter(homeAdapter);
		lstSetting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String aboutUs=arg0.getItemAtPosition(arg2).toString();
				if(aboutUs.equals(settings[0])){
					getAboutUs();
				}else if(aboutUs.equals(settings[1])){
					getHelp();
				}else if(aboutUs.equals(settings[2])){
					getFAQ();
				}else if(aboutUs.equals(settings[3])){
					getContact();
				}else if(aboutUs.equals(settings[4])){
					getDeveloper();
				}
				
				//getAboutUs();
			}
			
		});
	}
	protected void getContact() {
		// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(this);

				// tell the Dialog to use the dialog.xml as it's layout description
				dialog.setContentView(R.layout.cantact_dialog);
				dialog.setTitle("Contact");
				dialog.show();

	}
	protected void getFAQ() {
		// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(this);

				// tell the Dialog to use the dialog.xml as it's layout description
				dialog.setContentView(R.layout.frequntly_ask_dialog);
				dialog.setTitle("Frequently Ask Question");
				dialog.show();

	}
	protected void getHelp() {
		// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(this);

				// tell the Dialog to use the dialog.xml as it's layout description
				dialog.setContentView(R.layout.help_dialog);
				dialog.setTitle("Help");
				dialog.show();

	}
	protected void getDeveloper() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this);

		// tell the Dialog to use the dialog.xml as it's layout description
		dialog.setContentView(R.layout.developer_dialog);
		dialog.setTitle("Developer");
		dialog.show();

	}
	protected void getAboutUs() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this);

		// tell the Dialog to use the dialog.xml as it's layout description
		dialog.setContentView(R.layout.about_dialog);
		dialog.setTitle("About Us");
		TextView txt = (TextView) dialog.findViewById(R.id.txtabout);
		txt.setText(" This application was developed to assist\n"
		+" users since we are now moving to mobile infact mobile is\n"+
				" taking over everything now its happening on mobile device"+
		" \n such as tables,smart phones\n"+
				"\tHope you will enjoy this version of app\n"+
		  " SURNAME & INITIALS: SA Massango\n"+

          " STUDENT NUMBER: 212405272\n"+

          " EMAIL ADDRESS: samassango@gmail.com\n"+

          " CONTACT NUMBERS: 0729632594");
		
		dialog.show();

	}
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

}
