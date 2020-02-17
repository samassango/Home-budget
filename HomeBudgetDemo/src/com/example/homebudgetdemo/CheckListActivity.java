package com.example.homebudgetdemo;

import java.util.ArrayList;
import java.util.List;

import com.massango.background.BudgetList;
import com.massango.background.Category;
import com.massango.background.HomeBudgetDatabase;
import com.massango.background.ItemShopping;
import com.massango.homebudgetdemo.AddMonthlyItemActivity;

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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class CheckListActivity extends Activity implements OnItemSelectedListener{
    HomeBudgetDatabase db;
    TextView txtAmount;
    ListView txtListShopping;
    Intent intentBList;
    List<ItemShopping> lstShoppingList;
    SimpleCursorAdapter adapter;
    double amount=0;
    Spinner spnrExpensesType;
    String categoryName;
    String stringBudgetName;
    int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_list);
		// Show the Up button in the action bar.
		setupActionBar();
		db=new HomeBudgetDatabase(getApplicationContext());
		txtAmount=(TextView)findViewById(R.id.txtAmount);
		txtListShopping=(ListView)findViewById(R.id.lstShoppingList);
		spnrExpensesType=(Spinner)findViewById(R.id.spnrBudgetName);
		//getting the expense type from the database
		try {
			List<Category> list=new ArrayList<Category>();
			//db.openBD();
		     list =db.getCategory();
			//db.closeDB();
			
			ArrayAdapter<Category> adapter=new ArrayAdapter<Category>(this, android.R.layout.simple_dropdown_item_1line, list);
			spnrExpensesType.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error in getting Expense", e.getMessage());
		}
		intentBList=getIntent();
		stringBudgetName=intentBList.getStringExtra("SELECTED_BUDGET");
	//	id=Integer.parseInt(intentBList.getStringExtra("SELECTED_ID"));
	//	Toast.makeText(getApplicationContext(), "name is "+stringBudgetName, Toast.LENGTH_LONG).show();
		lstShoppingList =new ArrayList<ItemShopping>();
		try{
			/*db.openBD();
			lstShoppingList=db.getShoppingList();
			db.closeDB();
			
			db.openBD();
			 amount = db.getTotalListAmount();
			db.closeDB();*/
			spnrExpensesType.setOnItemSelectedListener(this);
			/**
			//----------------------------------------------
			db.openBD();
			Cursor c=db.getShoppingCursor(spnrExpensesType.getSelectedItem().toString());
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
				adapter=new SimpleCursorAdapter(this, R.layout.expenselayout, c, new String[]{"itemName","itemPrice"}, new int[] {  R.id.lblExpensesName,R.id.lblAmount});
			
			}
			txtListShopping.setAdapter(adapter);
			db.closeDB();
			//------------------------------------------------
			
			db.openBD();
			Cursor c=db.getShoppingCursor();
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
				adapter=new SimpleCursorAdapter(this, R.layout.expenselayout, c, new String[]{"itemName","itemPrice"}, new int[] {  R.id.lblExpensesName,R.id.lblAmount});
			
			}
			txtListShopping.setAdapter(adapter);
			db.closeDB();*/
			//txtAmount.setText("R"+amount);
		}catch(Exception ex){
			Log.d("Error in Budget list", ex.getMessage());
		}
	//	ArrayAdapter<ItemShopping> adapter=new ArrayAdapter<ItemShopping>(this, android.R.layout.simple_list_item_1,android.R.id.text1, lstShoppingList);
	//	txtListShopping.setAdapter(adapter);
	   
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
		getMenuInflater().inflate(R.menu.check_list, menu);
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
		case R.id.action_add:
			
			Intent intentExpense=new Intent(getApplicationContext(),AddMonthlyItemActivity.class);
			intentExpense.putExtra("SELECTED_NAME",stringBudgetName);
			//intentExpense.putExtra("SELECTED_ID", id);
		Log.d("Check list info",stringBudgetName);
			startActivity(intentExpense);
			return true;
		case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long refId) {
		// TODO Auto-generated method stub
		try {
			/**db.openBD();
			Cursor c=db.getShoppingCursor(arg0.getItemAtPosition(position).toString(),id);
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
				adapter=new SimpleCursorAdapter(this, R.layout.expenselayout, c, new String[]{"itemName","itemPrice"}, new int[] {  R.id.lblExpensesName,R.id.lblAmount});
			     	}
			txtListShopping.setAdapter(adapter);
			db.closeDB();*/
			//stringBudgetName
			/**db.openBD();
			Cursor c=db.getShoppingCursor(arg0.getItemAtPosition(position).toString());
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
				adapter=new SimpleCursorAdapter(this, R.layout.expenselayout, c, new String[]{"itemName","itemPrice"}, new int[] {  R.id.lblExpensesName,R.id.lblAmount});
			     	}
			txtListShopping.setAdapter(adapter);
			db.closeDB();*/
			db.openBD();
			Cursor c=db.getShoppingCursor(arg0.getItemAtPosition(position).toString(),stringBudgetName);
			startManagingCursor(c);
			if(c.moveToFirst()){
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("itemName"))+" "+c.getString(c.getColumnIndex("itemPrice")), Toast.LENGTH_LONG).show();
				adapter=new SimpleCursorAdapter(this, R.layout.expenselayout, c, new String[]{"itemName","itemPrice"}, new int[] {  R.id.lblExpName,R.id.lblAmt});
			     	}
			txtListShopping.setAdapter(adapter);
			db.closeDB();
			db.openBD();
			 amount = db.getTotalListAmount(arg0.getItemAtPosition(position).toString(),stringBudgetName);
			db.closeDB();
			txtAmount.setText("R"+amount);
			Toast.makeText(getApplicationContext(), arg0.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error", e.getMessage());
		}
		
	}	

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
