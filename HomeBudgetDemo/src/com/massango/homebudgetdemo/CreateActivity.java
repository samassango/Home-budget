package com.massango.homebudgetdemo;



import java.text.SimpleDateFormat;
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
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends Activity {
	Button btnAddBudget, btnCancel;
    EditText txtBudgetName;
    EditText txtBudgetDesc;
    HomeBudgetDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);

		db=new HomeBudgetDatabase(getApplicationContext());
		
		btnAddBudget = (Button) findViewById(R.id.btnAddBudget);
		btnCancel = (Button) findViewById(R.id.btnCancel);
        txtBudgetName=(EditText)findViewById(R.id.txtBudgetName);
      //  txtBudgetDesc=(EditText)findViewById(R.id.txtDescription);
        
      /*  try {
	    	db.openBD();
			  List<AccountExpense> acc=db.getAccount();
			EditText  txtAmt = (EditText) findViewById(R.id.txtAvailableAmount);
			txtAmt.setText("R"+acc.get(acc.size()-1).getIncome());
			db.closeDB();
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("Error", e.getMessage());
		}*/
        
        final Intent intent=new Intent(getApplicationContext(),BudgetListActivity.class);
		btnAddBudget.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try{
				long id;
                String bgtName=txtBudgetName.getText().toString();
                String desc=getTodaysDate();
                db.openBD();
                BudgetList b=new BudgetList(bgtName, desc);
                id=db.insertData(b);
                db.closeDB();
                if(id>0){
                	Toast.makeText(getApplicationContext(),"created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                	Toast.makeText(getApplicationContext(),"budget was not created successfully", Toast.LENGTH_SHORT).show();
                }
				}catch(Exception ex){
				 Log.d("Error occured", ex.getMessage());
				}
			}
		});
		final Intent intentMain=new Intent(getApplicationContext(),MainActivity.class);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intentMain);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}
	
	/* (non-Javadoc)
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
		case R.id.action_create:
			Intent intent=new Intent(getApplicationContext(),BudgetListActivity.class);
			try{
				long id;
                String bgtName=txtBudgetName.getText().toString();
                String desc=getTodaysDate();
                db.openBD();
                BudgetList b=new BudgetList(bgtName, desc);
                id=db.insertData(b);
                db.closeDB();
                if(id>0){
                	Toast.makeText(getApplicationContext(),"created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                	Toast.makeText(getApplicationContext(),"budget was not created successfully", Toast.LENGTH_SHORT).show();
                }
				}catch(Exception ex){
				 Log.d("Error occured", ex.getMessage());
				}
			return true;
		case R.id.action_back5:
			Intent mainIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(mainIntent);
		    return true;
		case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}

	public String getTodaysDate()
	{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
		return sdf.format(date);
	}

}
