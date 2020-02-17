package com.massango.homebudgetdemo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.AccountExpense;
import com.massango.background.BudgetList;
import com.massango.background.Category;
import com.massango.background.HomeBudgetDatabase;
import com.massango.background.ItemShopping;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TransectionActivity extends Activity implements
		OnItemSelectedListener {
	private TextView tvDisplayDate;
	private DatePicker dpResult;
	private Button btnChangeDate;

	private int year;
	private int month;
	private int day;

	List<Category> category;

	static final int DATE_DIALOG_ID = 999;

	ArrayAdapter<String> adapterPossibleExpense;
	EditText txtAmount;
	Spinner spBudgetList;
	Spinner spExpense;
	Spinner spCategory;
	String selectedBudget;
	String selectedIndex;
	HomeBudgetDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transection);
		setCurrentDateOnView();
		addListenerOnButton();
		db = new HomeBudgetDatabase(getApplicationContext());
		Intent intent = getIntent();
		spCategory = (Spinner) findViewById(R.id.spnrCategories);
		spExpense = (Spinner) findViewById(R.id.spnrPExpense);

		Button btnSave = (Button) findViewById(R.id.btnSave2);
		Button btnCancel = (Button) findViewById(R.id.btnCancel2);
		txtAmount = (EditText) findViewById(R.id.txtExpenseAmount);
		spBudgetList = (Spinner) findViewById(R.id.spnrBudgetList);

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

		// -------------------------------------------------
		try {
			List<Category> list = new ArrayList<Category>();
			// db.openBD();
			list = db.getCategory();
			// db.closeDB();

			ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
					android.R.layout.simple_dropdown_item_1line, list);
			spCategory.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error in getting Category", e.getMessage());
		}
		selectedBudget = spCategory.getSelectedItem().toString();
		// --------------------------------------------------
		try {

			spCategory.setOnItemSelectedListener(this);

			// List<ItemShopping> itemArraylist = new ArrayList<ItemShopping>();

			// List<String> itemList = new ArrayList<String>();

			// db.openBD();
			// itemArraylist = db.getShoppingList(selectedBudget,"Home");
			// itemArraylist = db.getShoppingList();

			// db.closeDB();
			/**
			 * List<String> itemList = new ArrayList<String>(); for
			 * (ItemShopping b : itemArraylist) {
			 * if(spBudgetList.getSelectedItem
			 * ().toString().equalsIgnoreCase(b.getBudgetName())){
			 * if(spCategory.
			 * getSelectedItem().toString().equalsIgnoreCase(b.getExpCategory
			 * ())){ itemList.add(b.getItemName()); } } //Log.d("Info",
			 * b.getItemName()); }
			 * 
			 * 
			 * String[] strList = new String[itemArraylist.size()]; for (int x =
			 * 0; x < strList.length; x++) { strList[x] =
			 * itemArraylist.get(x).getItemName(); } ArrayAdapter<String>
			 * stringAdapter = new ArrayAdapter<String>(this,
			 * android.R.layout.simple_spinner_dropdown_item, strList);
			 * spExpense.setAdapter(stringAdapter);
			 */
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error in getting Expense", e.getMessage());
		}
		// ---------------------------------------------------------------------------------
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					String date = tvDisplayDate.getText().toString();
					String note = spBudgetList.getSelectedItem().toString();
					if (note != null) {
						String expenseName = spExpense.getSelectedItem()
								.toString();

						String catName = selectedBudget;
						if (!String.valueOf(txtAmount.getText().toString())
								.isEmpty()) {
							double amount = Double.parseDouble(txtAmount
									.getText().toString());
							if (String.valueOf(note) != null) {
								db.openBD();
								AccountExpense a = new AccountExpense(amount,
										catName, note, date, expenseName);
								// long id = db.insertExpense(a);
								long id = db.updateExpense(a);
								if (id > 0) {
									txtAmount.setText("");
									Toast.makeText(getApplicationContext(),
											"Expense added to " + expenseName,
											Toast.LENGTH_LONG).show();

								}

								db.closeDB();
							} else {
								Toast.makeText(getApplicationContext(),
										"Budget name invalid ",
										Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Amount invalid ", Toast.LENGTH_LONG)
									.show();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Add expense first", Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(),
							"Add expense first", Toast.LENGTH_LONG).show();
				}

			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
			}
		});
	}

	// display current date
	public void setCurrentDateOnView() {

		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
		dpResult = (DatePicker) findViewById(R.id.dpResult);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));

		// set current date into datepicker
		dpResult.init(year, month, day, null);

	}

	public void addListenerOnButton() {

		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

		btnChangeDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});
		/*
		 * btnChangeDate.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * showDialog(DATE_DIALOG_ID);
		 * 
		 * }
		 * 
		 * });
		 */

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

			// set selected date into datepicker also
			dpResult.init(year, month, day, null);

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transection, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
		case R.id.action_addExpense:
			Intent intentAddExp = new Intent(getApplicationContext(),
					AddMonthlyItemActivity.class);
			intentAddExp.putExtra("SELECTED_NAME", spBudgetList
					.getSelectedItem().toString());
			startActivity(intentAddExp);
			return true;
		case R.id.action_back4:
			Intent intentBack = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intentBack);
			return true;
		case R.id.action_settings:
			Intent intentSettings = new Intent(getApplicationContext(),
					SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapter, View v, int pos, long id) {
		// TODO Auto-generated method stub
		try {
			List<ItemShopping> itemArraylist = new ArrayList<ItemShopping>();
			db.openBD();
			// itemArraylist =
			// db.getShoppingList(selectedBudget,adapter.getItemAtPosition(pos).toString());
			itemArraylist = db.getShoppingList();
			db.closeDB();

			// Log.d("Info",
			// spBudgetList.getSelectedItem().toString()+" "+adapter.getItemAtPosition(pos).toString());

			List<String> itemList = new ArrayList<String>();
			for (ItemShopping b : itemArraylist) {
				// if(selectedBudget.equals(b.getBudgetName())){
				// if(adapter.getItemAtPosition(pos).toString().equalsIgnoreCase(b.getExpCategory())){
				itemList.add(b.getItemName());
				// Log.d("Info", b.getItemName());
				// }
				// }

			}
			/**
			 * String[] strList=new String[itemArraylist.size()]; for(int
			 * x=0;x<strList.length;x++){
			 * strList[x]=itemArraylist.get(x).getItemName(); }
			 */
			ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, itemList);
			spExpense.setAdapter(stringAdapter);
		} catch (Exception ex) {
			Log.d("Budget List Error", ex.getMessage());
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public List<Category> getCatList() {
		List<Category> category = new ArrayList<Category>();
		// db = new HomeBudgetDatabase(getApplicationContext());
		try {
			db.openBD();
			category = db.getCategory();
			db.closeDB();
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("Error:", e.getMessage());
		}
		return category;
	}
}
