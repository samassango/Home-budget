package com.massango.homebudgetdemo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.AccountExpense;
import com.massango.background.BudgetList;
import com.massango.background.HomeBudgetDatabase;
import com.massango.background.ItemShopping;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;

public class ExpenseActivity extends Activity implements OnItemSelectedListener{
	HomeBudgetDatabase db;
    Spinner spBudgetList;
    ListView listExpense;
    SimpleCursorAdapter cursorAdapter;
    SimpleCursorAdapter adapter;
    TextView txtAmount,txtBalance,txtDebtAmount;;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense);
		// Show the Up button in the action bar.
		setupActionBar();
		db=new HomeBudgetDatabase(getApplicationContext());
		spBudgetList = (Spinner) findViewById(R.id.spnrBudgetList);
		listExpense=(ListView)findViewById(R.id.listExpenses);
		txtDebtAmount=(TextView)findViewById(R.id.lblTotalExp);
		txtBalance=(TextView)findViewById(R.id.lblBAmount);
		txtAmount=(TextView)findViewById(R.id.lblCurrentBalance);
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
		
		try {
			
			spBudgetList.setOnItemSelectedListener(this);
			//List<ItemShopping> listExpense=new ArrayList<ItemShopping>();
		    //db.openBD();
			//   listExpense=db.getShoppingList();
			//db.closeDB();
			//ArrayAdapter<ItemShopping> shoppingAdapter=new ArrayAdapter<ItemShopping>(getApplicationContext(), android.R.layout.simple_list_item_1, listExpense);
			/**db.openBD();
			Cursor c=db.getShoppingCursor();
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
				//expense_layout_list
				cursorAdapter=new SimpleCursorAdapter(this, R.layout.list_expense_layout, c, new String[]{"itemName","itemPrice","category"}, new int[] {  R.id.lblExpenseName,R.id.lblExpAmt,R.id.expCategory});
				//cursorAdapter=new SimpleCursorAdapter(this, R.layout.expense_layout_list, c, new String[]{"itemName","itemPrice","category"}, new int[] {  R.id.lblExpName,R.id.lblAmt,R.id.lblCat});
			    listExpense.setAdapter(cursorAdapter);
			}
			
			db.closeDB();
			*/
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error list expense", e.getMessage());
		}
	}
	 public double getAmt(String budgetName){
	    	Intent intent=getIntent();
	    //	String budgetName=intent.getStringExtra("BUDGET_NAME");
	    	double currBalance = 0;
			List<AccountExpense> lstAE = db.getAccount();
			for (int x = 0; x < lstAE.size(); x++) {
				AccountExpense a = lstAE.get(x);
				if (budgetName.equals(a.getBudget_Name())) {
					currBalance = a.getIncome();
					x = lstAE.size();
				}
			}
			return currBalance;
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
		getMenuInflater().inflate(R.menu.expense, menu);
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
		double amount;
		//try {
			db.openBD();
			Cursor c=db.getShoppingCursorBudget(arg0.getItemAtPosition(arg2).toString());
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
			
				cursorAdapter=new SimpleCursorAdapter(this, R.layout.list_expense_layout, c, new String[]{"itemName","itemPrice","category"}, new int[] {  R.id.lblExpenseName,R.id.lblExpAmt,R.id.expCategory});
				//cursorAdapter=new SimpleCursorAdapter(this, R.layout.expense_layout_list, c, new String[]{"itemName","itemPrice","category"}, new int[] {  R.id.lblExpName,R.id.lblAmt,R.id.lblCat});
			    listExpense.setAdapter(cursorAdapter);
			}else{
				Log.d("Information", "NO CURSOR");
			}
			
			db.closeDB();
		try{
			db.openBD();
			amount=db.getBudgetTotalAmount(arg0.getItemAtPosition(arg2).toString());
			db.closeDB();
			DecimalFormat df=new DecimalFormat("#.##");
			txtDebtAmount.setText("R"+df.format(amount));
			txtBalance.setText("R"+df.format(getAmt(arg0.getItemAtPosition(arg2).toString()))); 
			double currentBalance=getAmt(arg0.getItemAtPosition(arg2).toString())-amount;
			String currAmt="";
			if(currentBalance<0){
				currentBalance=currentBalance*-1;
				currAmt="R"+df.format(currentBalance)+" due to us";
			}else{
				currAmt="R"+df.format(currentBalance);
			}
			txtAmount.setText("R"+df.format(currentBalance));
		} catch (Exception e) {
			// TODO: handle exception
			
		}
			//Toast.makeText(getApplicationContext(),arg0.getItemAtPosition(arg2).toString() , Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
