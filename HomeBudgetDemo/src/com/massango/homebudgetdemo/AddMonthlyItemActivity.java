package com.massango.homebudgetdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.homebudgetdemo.CheckListActivity;
import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.AccountExpense;
import com.massango.background.Category;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class AddMonthlyItemActivity extends Activity {
	Button btnSave;
	EditText txtItemName, txtPrice, txtQuantity;
	CheckBox chkRecurring;
	Spinner spnrExpensesType;
	int refID = 0;
	HomeBudgetDatabase db;
	Intent intentCheckList;
	String budgetName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_monthly_item);
		// Show the Up button in the action bar.
		setupActionBar();
		db = new HomeBudgetDatabase(getApplicationContext());
		// getting the intent from the check list activity
		intentCheckList = getIntent();
		budgetName = intentCheckList.getStringExtra("SELECTED_NAME");
		// Toast.makeText(getApplicationContext(), budgetName,
		// Toast.LENGTH_SHORT).show();
		// initializing all the required components
		chkRecurring = (CheckBox) findViewById(R.id.checkBox1);
		btnSave = (Button) findViewById(R.id.btnSaveItem);
		txtItemName = (EditText) findViewById(R.id.txtItemName);
		txtPrice = (EditText) findViewById(R.id.txtPrice);
		txtQuantity = (EditText) findViewById(R.id.txtQuantity);
		spnrExpensesType = (Spinner) findViewById(R.id.spnrExpenseType);

		// getting the expense type from the database
		try {
			List<Category> list = new ArrayList<Category>();
			// db.openBD();
			list = db.getCategory();
			// db.closeDB();

			ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
					android.R.layout.simple_dropdown_item_1line, list);
			spnrExpensesType.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error in getting Expense", e.getMessage());
		}

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					// refID=Integer.parseInt(intentCheckList.getStringExtra("SELECTED_ID"));
					boolean recurring = false;
					recurring = chkRecurring.isChecked();

					if (txtItemName.getText().toString().isEmpty() == false) {
						if (txtPrice.getText().toString().isEmpty() == false) {
							if (txtQuantity.getText().toString().isEmpty() == false) {
							//	if (recurring) {
									long id = db.addItemInDB(txtItemName
											.getText().toString(),
											spnrExpensesType.getSelectedItem()
													.toString(), Double
													.parseDouble(txtPrice
															.getText()
															.toString()),
											Integer.parseInt(txtQuantity
													.getText().toString()),
											recurring, refID, budgetName);
									if (id > 0) {
										Toast.makeText(getApplicationContext(),
												"Expense added",
												Toast.LENGTH_SHORT).show();
										txtItemName.setText(null);
										txtPrice.setText(null);
										txtQuantity.setText(null);
									}
							/**	} else {
									db.openBD();
									AccountExpense a = new AccountExpense(Double.parseDouble(txtPrice.getText().toString()),spnrExpensesType.getSelectedItem().toString(),
											"no notes", getTodaysDate(), txtItemName.getText().toString());
									long id = db.insertExpense(a);
									if (id > 0) {
										txtItemName.setText(null);
										txtPrice.setText(null);
										txtQuantity.setText(null);
										Toast.makeText(getApplicationContext(),
												"Expense added",
												Toast.LENGTH_LONG).show();
									}
									db.closeDB();
								}*/
							} else {
								Toast.makeText(getApplicationContext(),
										"invalid Quantity", Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"invalid amount", Toast.LENGTH_SHORT)
									.show();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"invalid expense name", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (Exception e) {
					// TODO: handle exception
					Log.d("Error not saved", e.getMessage());
				}
			}
		});
		
	}

	protected String getTodaysDate() {
		// TODO Auto-generated method stub
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("mm-dd-yyyy");
		return sdf.format(date.getDate());
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
		getMenuInflater().inflate(R.menu.add_monthly_item, menu);
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
		case R.id.action_saveItem:
			try {
				// refID=Integer.parseInt(intentCheckList.getStringExtra("SELECTED_ID"));
				boolean recurring = false;
				recurring = chkRecurring.isChecked();

				if (txtItemName.getText().toString().isEmpty() == false) {
					if (txtPrice.getText().toString().isEmpty() == false) {
						if (txtQuantity.getText().toString().isEmpty() == false) {
						//	if (recurring) {
								long id = db.addItemInDB(txtItemName
										.getText().toString(),
										spnrExpensesType.getSelectedItem()
												.toString(), Double
												.parseDouble(txtPrice
														.getText()
														.toString()),
										Integer.parseInt(txtQuantity
												.getText().toString()),
										recurring, refID, budgetName);
								if (id > 0) {
									Toast.makeText(getApplicationContext(),
											"Expense added",
											Toast.LENGTH_SHORT).show();
									txtItemName.setText(null);
									txtPrice.setText(null);
									txtQuantity.setText(null);
								}
						/**	} else {
								db.openBD();
								AccountExpense a = new AccountExpense(Double.parseDouble(txtPrice.getText().toString()),spnrExpensesType.getSelectedItem().toString(),
										"no notes", getTodaysDate(), txtItemName.getText().toString());
								long id = db.insertExpense(a);
								if (id > 0) {
									txtItemName.setText(null);
									txtPrice.setText(null);
									txtQuantity.setText(null);
									Toast.makeText(getApplicationContext(),
											"Expense added",
											Toast.LENGTH_LONG).show();
								}
								db.closeDB();
							}*/
						} else {
							Toast.makeText(getApplicationContext(),
									"invalid Quantity", Toast.LENGTH_SHORT)
									.show();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"invalid amount", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"invalid expense name", Toast.LENGTH_SHORT)
							.show();
				}

			} catch (Exception e) {
				// TODO: handle exception
				Log.d("Error not saved", e.getMessage());
			}
			return true;
		case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
