package com.massango.homebudgetdemo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.homebudgetdemo.CalendarActivity;
import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.Category;
import com.massango.background.HomeBudgetDatabase;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	String[] categories = { "Home", "Auto", "Utilities", "Food", "Personal",
			"Activities", "Salary" };
	HomeBudgetDatabase db;
	private List<HashMap<String, String>> mList;
	private SimpleAdapter mAdapter;
	final private String LISTMENU = "listmenu";
	final private String FLAG = "flag";

	int[] mIconsList = new int[] { R.drawable.budget_list,
			R.drawable.icon_money, R.drawable.transaction_list2,
			R.drawable.ic_expenses, R.drawable.category_1, R.drawable.ic_acc,
			R.drawable.ic_reports, R.drawable.ic_account };
	String[] menuList;
	// Within which the entire activity is enclosed
	DrawerLayout mDrawerLayout;

	// ListView represents Navigation Drawer
	ListView mDrawerList;

	// ActionBarDrawerToggle indicates the presence of Navigation Drawer in the
	// action bar
	ActionBarDrawerToggle mDrawerToggle;

	// Title of the action bar
	String mTitle = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new HomeBudgetDatabase(getApplicationContext());

		if (getCatList().size() == 0) {
			Category ca;
			db.openBD();
			ca = new Category("Expense", categories[0], 0.00);
			db.insertCategory(ca);
			db.closeDB();
			db.openBD();
			ca = new Category("Expense", categories[1], 0.00);
			db.insertCategory(ca);
			db.closeDB();
			db.openBD();
			ca = new Category("Expense", categories[2], 0.00);
			db.insertCategory(ca);
			db.closeDB();
			db.openBD();
			ca = new Category("Expense", categories[3], 0.00);
			db.insertCategory(ca);
			db.closeDB();
			db.openBD();
			ca = new Category("Expense", categories[4], 0.00);
			db.insertCategory(ca);
			db.closeDB();
			db.openBD();
			ca = new Category("Expense", categories[5], 0.00);
			db.insertCategory(ca);
			db.closeDB();
			db.openBD();
			ca = new Category("Income", categories[6], 0.00);
			db.insertCategory(ca);
			db.closeDB();
		}

		// =========================================

		mTitle = (String) getTitle();

		// Getting reference to the DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// Getting reference to the ActionBarDrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();

			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Menu");
				invalidateOptionsMenu();
			}

		};

		// Setting DrawerToggle on DrawerLayout
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		menuList = getResources().getStringArray(R.array.rivers);
		// Creating an ArrayAdapter to add items to the listview mDrawerList
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), R.layout.drawer_list_item, getResources()
						.getStringArray(R.array.rivers));
		// -----------------------------------------------
		mList = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < 8; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(LISTMENU, menuList[i]);
			hm.put(FLAG, Integer.toString(mIconsList[i]));
			mList.add(hm);
		}

		// Keys used in Hashmap
		String[] from = { FLAG, LISTMENU };

		// Ids of views in listview_layout
		int[] to = { R.id.flag, R.id.country };

		// Instantiating an adapter to store each items
		// R.layout.drawer_layout defines the layout of each item
		mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from,
				to);
		// Setting the adapter on mDrawerList
		mDrawerList.setAdapter(mAdapter);
		// ------------------------------------------------
		// Setting the adapter on mDrawerList
		// mDrawerList.setAdapter(adapter);

		// Enabling Home button
		getActionBar().setHomeButtonEnabled(true);

		// Enabling Up navigation
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Setting item click listener for the listview mDrawerList
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Getting an array of rivers
				String[] menuArray = getResources().getStringArray(
						R.array.rivers);

				// Currently selected river
				mTitle = menuArray[position];
				if (mTitle.equals("Budget list")) {
					Intent intent = new Intent(getApplicationContext(),
							BudgetListActivity.class);
					startActivity(intent);
				} else if (mTitle.equals("Income")) {
					Intent intent = new Intent(getApplicationContext(),
							IncomeActivity.class);
					startActivity(intent);
				} else if (mTitle.equals("Transactions list")) {
					Intent intent = new Intent(getApplicationContext(),
							ExpenseActivity.class);
					startActivity(intent);
				} else if (mTitle.equals("Expenses")) {
					Intent intent = new Intent(getApplicationContext(),
							TransectionActivity.class);
					startActivity(intent);
				} else if (mTitle.equals("Categories (Types)")) {
					Intent intent = new Intent(getApplicationContext(),
							CategoriesActivity.class);
					startActivity(intent);
				} else if (mTitle.equals("Budget Account")) {
					Intent intent = new Intent(getApplicationContext(),
							BudgetActivity.class);
					startActivity(intent);
				} else if (mTitle.equals("Reports")) {
					Intent intent = new Intent(getApplicationContext(),
							ReportActivity.class);
					startActivity(intent);
				} else if (mTitle.equals("Calendar")) {
					Intent intent = new Intent(getApplicationContext(),
							CalendarActivity.class);
					startActivity(intent);
				}

			}
		});
		// =========================================
		Button btnCreateBudget = (Button) findViewById(R.id.btnCreateBudget);
		btnCreateBudget.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						CreateActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	/** Handling the touch event of app icon */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			switch (item.getItemId()) {
			case R.id.action_Exit:
				getExitSystem();
				// onBackPressed();
				return true;
			case R.id.action_settings:
				Intent intentSettings = new Intent(getApplicationContext(),
						SettingsActivity.class);
				startActivity(intentSettings);
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		getExitSystem();
		super.onBackPressed();

	}

	protected void getExitSystem() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this);

		// tell the Dialog to use the dialog.xml as it's layout description
		dialog.setContentView(R.layout.my_positive_dialog);
		dialog.setTitle("Exit Application");
		TextView txt = (TextView) dialog.findViewById(R.id.strInfoMessage);
		txt.setText("Are you sure you want to Exit \n" + " Click Yes to Exit!");
		Button dialogButton = (Button) dialog.findViewById(R.id.btnOK);
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(RESULT_OK);
				dialog.dismiss();
				// Toast.makeText(getApplicationContext(),
				// "Download was successfully...", Toast.LENGTH_SHORT).show();
			}
		});

		dialog.show();

	}

	/** Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
