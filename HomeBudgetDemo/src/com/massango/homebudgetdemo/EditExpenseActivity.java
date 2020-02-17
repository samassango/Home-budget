package com.massango.homebudgetdemo;

import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.Category;
import com.massango.background.HomeBudgetDatabase;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EditExpenseActivity extends Activity {
	Button btnDel, btnUp;
	String[] arr = { "Expense", "Income" };
	Spinner sp;
	EditText txtCategory;
	HomeBudgetDatabase db;
	Intent intent;
	long id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_expense);
		// Show the Up button in the action bar.
		setupActionBar();
		
		db = new HomeBudgetDatabase(getApplicationContext());
		btnDel = (Button) findViewById(R.id.btnDeleteEdit);
		btnUp = (Button) findViewById(R.id.btnDoneEdit);
		sp = (Spinner) findViewById(R.id.spExpTrans);
		ArrayAdapter<String> adapdter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_spinner_dropdown_item, arr);
		// txtCompleteBudget.setThreshold(0);
		sp.setAdapter(adapdter);
		txtCategory = (EditText) findViewById(R.id.txtCategory);
		intent = getIntent();
		String item = intent.getStringExtra("ITEM");
		String cat = intent.getStringExtra("CAT");
		String type = intent.getStringExtra("TYPE");
	   // id=Long.parseLong(intent.getStringExtra("ID"));;
		if (type.equalsIgnoreCase("Expense")) {
			sp.setSelection(0);
		} else {
			sp.setSelection(1);
		}
		txtCategory.setText(cat);
		
		btnUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String catType = sp.getSelectedItem().toString();
				String category = txtCategory.getText().toString();
				db.openBD();
				Category c=new Category(catType, category);
                  long refId= db.updateCategory(c);
				if(refId>0){
					Intent intnt=new Intent(getApplicationContext(), CategoriesActivity.class);
				    startActivity(intnt);
					Toast.makeText(getApplicationContext(), "changes was successfully", Toast.LENGTH_SHORT).show();
				}
				db.closeDB();
			}
		});
		btnDel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				db.openBD();
                 // db.deleteCategory(id);
				db.closeDB();
			}
		});

		
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
		getMenuInflater().inflate(R.menu.edit_expense, menu);
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

}
