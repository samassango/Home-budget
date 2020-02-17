package com.massango.homebudgetdemo;

import java.util.ArrayList;
import java.util.List;

import com.example.homebudgetdemo.CheckListActivity;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class BudgetListActivity extends Activity {
    HomeBudgetDatabase db;
    ListView lv;
    List<BudgetList> lbudget;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budget_list);
		// Show the Up button in the action bar.
		setupActionBar();
		lv=(ListView)findViewById(R.id.lstBudget);
		db=new HomeBudgetDatabase(getApplicationContext());
		lbudget =new ArrayList<BudgetList>();
		try{
			db.openBD();
			lbudget=db.getBudget();
			db.closeDB();
		}catch(Exception ex){
			Log.d("Error in Budget list", ex.getMessage());
		}
		ArrayAdapter<BudgetList> adapter=new ArrayAdapter<BudgetList>(this, android.R.layout.simple_list_item_1,android.R.id.text1, lbudget);
		lv.setAdapter(adapter);
	   
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				BudgetList objBudgetList=(BudgetList) adapter.getItemAtPosition(position);
				String strBudget=(String) objBudgetList.toString();
				//int refID=(int)objBudgetList.getId();
				Intent transActivity=new Intent(getApplicationContext(),CheckListActivity.class);
				transActivity.putExtra("SELECTED_BUDGET", strBudget);
				//transActivity.putExtra("SELECTED_INDEX", adapter.getSelectedItemPosition());
			//	transActivity.putExtra("SELECTED_ID",refID );
				Log.d("Does exist", ""+adapter.getSelectedItemPosition());
				startActivity(transActivity);
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
		getMenuInflater().inflate(R.menu.budget_list, menu);
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
