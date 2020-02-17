package com.massango.homebudgetdemo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.BudgetList;
import com.massango.background.HomeBudgetDatabase;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class BudgetActivity extends Activity implements OnItemSelectedListener {
    HomeBudgetDatabase db;
	Spinner sp;
    TextView txtBalance,txtExpense,txtBudgetAmount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budget);
		// Show the Up button in the action bar.
		setupActionBar();
		db=new HomeBudgetDatabase(getApplicationContext());
		sp=(Spinner)findViewById(R.id.spinner1);
		txtBalance=(TextView)findViewById(R.id.lblCurrentBalance);
		txtExpense=(TextView)findViewById(R.id.lblTotExpAmt);
		txtBudgetAmount=(TextView)findViewById(R.id.lblTotalBudgetAmt);
		try {
			List<BudgetList> list=new ArrayList<BudgetList>();
			db.openBD();
			  list=db.getBudget();
			db.closeDB();
			ArrayAdapter<BudgetList> adapter=new ArrayAdapter<BudgetList>(this, android.R.layout.simple_spinner_dropdown_item, list);
			sp.setAdapter(adapter);
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error occured adding budgets", e.getMessage());
		}
		try {
			sp.setOnItemSelectedListener(this);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error occured listener", e.getMessage());
		}
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
		getMenuInflater().inflate(R.menu.budget, menu);
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
		case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
	//	try {
			/*txtBalance.setText(0);
			txtBudgetAmount.setText(0);
			txtExpense.setText(0);*/
			Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_SHORT).show();
		//} catch (Exception e) {
			// TODO: handle exception
		//	Log.d("Error in spinner", e.getMessage());
		//}
	}
    public String convertToDecimal(double amt){
    	DecimalFormat df=new DecimalFormat("#.##");
    	return df.format(amt);
    }
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
