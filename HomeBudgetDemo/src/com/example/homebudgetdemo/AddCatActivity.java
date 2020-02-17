package com.example.homebudgetdemo;



import com.massango.background.Category;
import com.massango.background.HomeBudgetDatabase;
import com.massango.homebudgetdemo.CategoriesActivity;

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

public class AddCatActivity extends Activity {
	 Spinner spinner;
	  Button btnAddCategory;
	  EditText txtCatName;
	  String[] type={"Expense","Income"};
	  HomeBudgetDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_cat);
		// Show the Up button in the action bar.
		setupActionBar();
		db=new HomeBudgetDatabase(getApplicationContext());
		spinner=(Spinner)findViewById(R.id.spinnerExp);
		btnAddCategory=(Button)findViewById(R.id.btnCreate);
		txtCatName=(EditText)findViewById(R.id.txtCatName);
	     ArrayAdapter<String> adapdter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, type);
			//txtCompleteBudget.setThreshold(0);
	     spinner.setAdapter(adapdter);
	     
	    // spinner.setOnItemSelectedListener(this);
	     btnAddCategory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String Cattype=spinner.getSelectedItem().toString();
				String name=txtCatName.getText().toString();
				Intent addcategory = new Intent(getApplicationContext(),
						CategoriesActivity.class);
				Category c=new Category(Cattype, name,0.00);
				db.closeDB();
				long i=db.insertCategory(c);
				db.closeDB();
				if(i>0){
					startActivity(addcategory);
					Toast.makeText(getApplicationContext(), "new category added successfully", Toast.LENGTH_SHORT).show();
				}
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
		getMenuInflater().inflate(R.menu.add_cat, menu);
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
