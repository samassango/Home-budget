package com.massango.homebudgetdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.AccountExpense;
import com.massango.background.BudgetList;
import com.massango.background.HomeBudgetDatabase;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class IncomeActivity extends Activity {
	Button btnAdd, btnCancel;
	EditText txtDate, txtIncomeAmount, txtNotes;
	HomeBudgetDatabase db;
	String bgName;
    Spinner spBudgetList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income);
		// Show the Up button in the action bar.
		setupActionBar();

		db = new HomeBudgetDatabase(getApplicationContext());
/*
		Intent bg = getIntent();
		bgName = bg.getStringExtra("BUDGET_NAME");
		if (bgName.equals("General (R)")) {
			Toast.makeText(getApplicationContext(),
					"Select Budget name before adding income!!!",
					Toast.LENGTH_SHORT).show();
			Intent intentMain = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intentMain);
		}*/
		txtDate = (EditText) findViewById(R.id.txtTodayDate);
		txtIncomeAmount = (EditText) findViewById(R.id.txtAvialableAmt);
		txtNotes = (EditText) findViewById(R.id.txtAvaiBalance);
		spBudgetList=(Spinner)findViewById(R.id.spnnrBudgetIncom);
		
		btnAdd = (Button) findViewById(R.id.btnUpdateAmt);
		btnCancel = (Button) findViewById(R.id.btnCancel2);

		txtDate.setText(getTodayDate());
        
		try {
			List<BudgetList> budgetL;
			db.openBD();
			budgetL = db.getBudget();
			db.closeDB();
			List<String> budgetList = new ArrayList<String>();
			for (BudgetList b : budgetL) {
				budgetList.add(b.toString());
			}
			ArrayAdapter<String> adapterBudget = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, budgetList);
			spBudgetList.setAdapter(adapterBudget);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error budget list", e.getMessage());
		}
		
		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// isDataValid(txtIncomeAmount, txtNotes);
				String date = txtDate.getText().toString();
				String income = txtIncomeAmount.getText().toString();
				String notes = txtNotes.getText().toString();
				if (income.equals("")) {
					Toast.makeText(getApplicationContext(), "Invalid Amount",
							Toast.LENGTH_SHORT).show();
				} else {
					if (notes.equals("")) {
						Toast.makeText(getApplicationContext(),
								"Invalid notes", Toast.LENGTH_SHORT).show();
					} else {
						//if (!income.equals("") && !notes.equals("")) {
							db.openBD();
							AccountExpense a = new AccountExpense(Double
									.parseDouble(income), notes, date);
							a.setBudget_Name(spBudgetList.getSelectedItem().toString());
							long id = db.insertAccount(a);
							if (id > 0) {
								Toast.makeText(getApplicationContext(),
										"Account updated successfully",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(
										getApplicationContext(),
										MainActivity.class);
								startActivity(intent);
							}
							db.closeDB();
						//}
					}
				}
			}
		});
		final Intent intent1 = new Intent(getApplicationContext(),
				MainActivity.class);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(intent1);
			}
		});
	}
public void isDataValid(EditText amount, EditText notes) {
	// boolean isValid=true;
	// Intent intentIncom=new Intent(getApplicationContext(),
	// IncomeActivity.class);
	if (amount.getText().toString().isEmpty()) {
		// isValid=false;
		Toast.makeText(getApplicationContext(), "Invalid Amount",
				Toast.LENGTH_SHORT).show();

	} else if (notes.getText().toString().isEmpty()) {
		// isValid=false;
		Toast.makeText(getApplicationContext(), "Invalid notes",
				Toast.LENGTH_SHORT).show();

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
		getMenuInflater().inflate(R.menu.income, menu);
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
		case R.id.action_save:
			try {
				saveData();
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("Error in saving data", e.getMessage());
			}
			
			return true;
        case R.id.action_back2:
        	Intent intent1 = new Intent(getApplicationContext(),
    				MainActivity.class);
    				startActivity(intent1);
			return true;
        case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void saveData() {
		// TODO Auto-generated method stub
		String date = txtDate.getText().toString();
		String income = txtIncomeAmount.getText().toString();
		String notes = txtNotes.getText().toString();
		if (income.equals("")) {
			Toast.makeText(getApplicationContext(), "Invalid Amount",
					Toast.LENGTH_SHORT).show();
		} else {
			if (notes.equals("")) {
				Toast.makeText(getApplicationContext(),
						"Invalid notes", Toast.LENGTH_SHORT).show();
			} else {
				//if (!income.equals("") && !notes.equals("")) {
					db.openBD();
					AccountExpense a = new AccountExpense(Double
							.parseDouble(income), notes, date);
					a.setBudget_Name(spBudgetList.getSelectedItem().toString());
					long id = db.insertAccount(a);
					if (id > 0) {
						Toast.makeText(getApplicationContext(),
								"Account updated successfully",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(
								getApplicationContext(),
								MainActivity.class);
						startActivity(intent);
					}
					db.closeDB();
				//}
			}
		}
	}
	public String getTodayDate() {
		Date date = new Date();
		String today = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
		today = sdf.format(date);
		return today;
	}
}
