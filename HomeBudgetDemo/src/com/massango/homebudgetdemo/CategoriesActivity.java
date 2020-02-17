package com.massango.homebudgetdemo;

import java.util.ArrayList;
import java.util.List;

import com.example.homebudgetdemo.AddCatActivity;
import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.Category;
import com.massango.background.HomeBudgetDatabase;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class CategoriesActivity extends Activity {
	Button btnAddCategory;
	ListView lv;
	int[] posID;
	List<String> container;
	String[] categories = { "Home", "Auto", "Utilities", "Food", "Personal",
			"Activities" };
	com.massango.background.HomeBudgetDatabase db;
	List<Category> category;
	SimpleCursorAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		// Show the Up button in the action bar.
		setupActionBar();
		
db = new HomeBudgetDatabase(getApplicationContext());
		
        category=getCatList();
        //if(category!=null){
     /*   int x=0;
		container = new ArrayList<String>();
		//posID=new int[category.size()];
		for (Category cat : category) {
		//	posID[x]=cat.getId();
		container.add(cat.getCategory() + "\n" + cat.getType());
		//	x++;
		}*/
        
		
		lv = (ListView) findViewById(R.id.lstCategory);
		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			this, android.R.layout.simple_list_item_1,
				android.R.id.text1, container);

		lv.setAdapter(adapter);*/
		//====================================================================
		db.openBD();
		Cursor c=db.getCursorCategory();
		startManagingCursor(c);
		if(c.moveToFirst()){
			//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("amount"))+" "+c.getString(c.getColumnIndex("category")), Toast.LENGTH_LONG).show();
			adapter=new SimpleCursorAdapter(this, R.layout.rows, c, new String[]{"category","type"}, new int[] {  R.id.lblNumber,R.id.lblCateg});
		
		lv.setAdapter(adapter);
		}
		db.closeDB();
		//--------------------------------------------------------------------
		// lv.setBackgroundColor(Color.GREEN);
		final Intent editcategory = new Intent(getApplicationContext(),
				EditExpenseActivity.class);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adpter, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				
               /* String item=(String) lv.getItemAtPosition(pos);
                String category=item.substring(0, item.indexOf("\n"));
                String type=(String) item.subSequence(item.indexOf("\n")+1, item.length());
                editcategory.putExtra("ITEM", item);
                editcategory.putExtra("CAT", category);
               // editcategory.putExtra("ID", posID[pos]);
                editcategory.putExtra("TYPE", type);*/
				Cursor c = (Cursor) adpter.getItemAtPosition(pos);
				
				String category=c.getString(c.getColumnIndex("category"));
				String type=c.getString(c.getColumnIndex("type"));
				editcategory.putExtra("CAT", category);
	            editcategory.putExtra("TYPE", type);
                startActivity(editcategory);
			}
		});
		/**final Intent addcategory = new Intent(getApplicationContext(),
				AddCatActivity.class);
		btnAddCategory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(addcategory);
			}
		});*/
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
		getMenuInflater().inflate(R.menu.categories, menu);
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
		 Intent addcategory = new Intent(getApplicationContext(),
					AddCatActivity.class);	
					startActivity(addcategory);
			return true;
		case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	 public List<Category> getCatList(){
    	 List<Category> category= new  ArrayList<Category>();
    //	 db = new HomeBudgetDatabase(getApplicationContext());
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
