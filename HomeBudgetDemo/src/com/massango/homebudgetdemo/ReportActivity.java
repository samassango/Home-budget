package com.massango.homebudgetdemo;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.homebudgetdemo.R;
import com.example.homebudgetdemo.SettingsActivity;
import com.massango.background.AccountExpense;
import com.massango.background.BudgetList;
import com.massango.background.Category;
import com.massango.background.HomeBudgetDatabase;
import com.massango.background.PercentageForEach;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import au.com.bytecode.opencsv.CSVWriter;

public class ReportActivity extends Activity {

	HomeBudgetDatabase db;
	Button btnPie, btnBar;
	TextView txtTotIncome, txtTotExpense, txtBalance;
	ListView lstCatagory;
	String budgetName;
	private String[] categoryArray;
	String emailAddress;
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		final Context context = this;
		TextView txtTodaysDate;
		ProgressDialog barProgressDialog;
		Handler updateBarHandler;
		FileWriter writer;
		File root;
		File file;
		File sdCardsDir;

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		// Show the Up button in the action bar.
		setupActionBar();
		// openChart();
				db = new HomeBudgetDatabase(getApplicationContext());
				
		 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				root=Environment.getExternalStorageDirectory();
				sdCardsDir=new File(root+"/com.budget.homebudget.File");
				sdCardsDir.mkdir();
				file=new File(sdCardsDir,"budgetStatement.csv");
				
				updateBarHandler = new Handler();
			//=============================================	
				
				try {
					writeAllExample();
			
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
		  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				Intent intentMain = getIntent();
				//budgetName = intentMain.getStringExtra("BUDGET_NAME");
				
				/*if(budgetName.equals("General (R)")){
					Toast.makeText(getApplicationContext(), "Select Budget name before adding income!!!", Toast.LENGTH_SHORT).show();
				   Intent intentM=new Intent(getApplicationContext(), MainActivity.class);
				   startActivity(intentM);
				}*/
				try {

					db.openBD();
					db.updateTBL_Category();
					db.closeDB();

				} catch (Exception e) {
					// TODO: handle exception
					Log.d("Error occured", e.getMessage());
				}

				
				txtTotIncome = (TextView) findViewById(R.id.txtIncomeAmnt);
				txtTotExpense = (TextView) findViewById(R.id.txtTotExpenseAmt);
				txtBalance = (TextView) findViewById(R.id.txtCurrentBalance);
				lstCatagory = (ListView) findViewById(R.id.lstCategoryAvi);
				List<BudgetList> listCat = db.getBudget();
				ArrayList<String> l = new ArrayList<String>();
				for (BudgetList c : listCat) {
					l.add(c.toString());
				}
				Spinner sp = (Spinner) findViewById(R.id.spBudgetName);
				ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_dropdown_item, l);
				sp.setAdapter(adapterSpinner);
		   try{
				budgetName=sp.getSelectedItem().toString();
				
				double currBalance = 0;
				List<AccountExpense> lstAE = db.getAccount();
				for (int x = 0; x < lstAE.size(); x++) {
					AccountExpense a = lstAE.get(x);
					if (budgetName.equals(a.getBudget_Name())) {
						currBalance = a.getIncome();
						x = lstAE.size();
					}
				}
				double balance = currBalance - db.getTotalAmountCat();
				String str = "";
				if (balance > 0) {
					str = " due to you";
				} else {
					str = " due to us";
					balance=balance*-1;
				}
				DecimalFormat datF = new DecimalFormat("0.00");
				// NumberFormat datF=NumberFormat.getCurrencyInstance();
				txtTotIncome.setText("R" + datF.format(currBalance));
				txtTotExpense.setText("R" + datF.format(db.getTotalAmountCat()));
				txtBalance.setText("R" + datF.format(balance) + str);/**/
				ArrayAdapter<PercentageForEach> adapterPerc = new ArrayAdapter<PercentageForEach>(
						this, android.R.layout.simple_list_item_1, android.R.id.text1,
						getPercentageList());
				lstCatagory.setAdapter(adapterPerc);
		   }catch(Exception ex){
			   Log.d("Error occured", ex.getMessage());
		   }
				/**btnBar.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						//Intent bar = new Intent(getApplicationContext(),
						//		BarChartActivity.class);
						//startActivity(bar);
						openChart();
					}
				});
				btnPie.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent pie = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(pie);
					}
				});*/
	}
	  private void openChart(){
	        
	        int[] intX;
	        double[] expenseArray;
	        List<Category> lc=db.getCategory();
	        categoryArray=new String[lc.size()];
	        intX=new int[lc.size()];
	        expenseArray=new double[lc.size()];
	        for(int i=0;i<lc.size();i++){
	        	categoryArray[i]=lc.get(i).getCategory();
	        	expenseArray[i]=lc.get(i).getAmount();
	        	intX[i]=i;
	        }
	        // Creating an  XYSeries for Expense
	        XYSeries expenseSeries = new XYSeries("Expense");
	        // Adding data to Income and Expense Series
	        for(int i=0;i<intX.length;i++){
	            expenseSeries.add(i,expenseArray[i]);
	        }
	 
	        // Creating a dataset to hold each series
	        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	       
	        dataset.addSeries(expenseSeries);
	 
	        // Creating XYSeriesRenderer to customize expenseSeries
	        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
	       // expenseRenderer.setColor(Color.rgb(220, 80, 80));
	        expenseRenderer.setColor(Color.rgb(130, 130, 230));
	        expenseRenderer.setFillPoints(true);
	        expenseRenderer.setLineWidth(1);
	        expenseRenderer.setDisplayChartValues(true);
	 
	        // Creating a XYMultipleSeriesRenderer to customize the whole chart
	        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
	        multiRenderer.setXLabels(0);
	        multiRenderer.setChartTitle("Expense Chart");
	        multiRenderer.setXTitle("Budget Expense");
	        multiRenderer.setYTitle("Amount in ZAR (R)");
	        multiRenderer.setZoomButtonsVisible(true);
	        for(int i=0; i< intX.length;i++){
	           // multiRenderer.addXTextLabel(i, categoryArray[i]);
	        	multiRenderer.addTextLabel(i, categoryArray[i]);
	        }
	 
	        // Adding incomeRenderer and expenseRenderer to multipleRenderer
	        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
	        // should be same
	        multiRenderer.addSeriesRenderer(expenseRenderer);
	 
	        // Creating an intent to plot bar chart using dataset and multipleRenderer
	        Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), dataset, multiRenderer, Type.DEFAULT);
	 
	        // Start Activity
	        startActivity(intent);

		//=============================================================
	}
	public List<PercentageForEach> getPercentageList() {
		List<PercentageForEach> lst = new ArrayList<PercentageForEach>();
		List<Category> list = db.getCategory();
		Double totally = db.getTotalAmountCat();

		for (int i = 0; i < list.size(); i++) {
			Double x = list.get(i).getAmount();
			double perc = (x / totally) * 100;
			PercentageForEach percentageForEach = new PercentageForEach(list
					.get(i).getId(), list.get(i).getCategory(), x, perc);
			lst.add(percentageForEach);
		}
		return lst;

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
		getMenuInflater().inflate(R.menu.report, menu);
		return true;
	}
//========================================================================
public void writeAllExample() throws IOException {
		
		CSVWriter writer = new CSVWriter(new FileWriter(file));
         List<PercentageForEach> l=getPercentageList();
         String statement="Budget Statement\n".toUpperCase();
         String issuedDate="Issued in :"+getTodaysDate();
         writer.writeNext(new String[]{statement,issuedDate});
		List<String[]> data = new ArrayList<String[]>();
		for(int i=0;i<l.size();i++){
			PercentageForEach p=l.get(i);
			String expAmt="R"+p.getExpenseAmount(); 
			String perc=p.getExpensePercentage()+"%";
        	 data.add(new String[] {p.getStrCategory(),expAmt ,perc });
         }

		writer.writeAll(data);
		String totC="Total Cost Expenses is R" +db.getTotalAmountCat();
		String incAmt="\nTotal income Amount R"+getAmt();
		String afterDeduction="\nCurrent balance : R"+(getAmt()- db.getTotalAmountCat());
		writer.writeNext(new String[] {totC,incAmt,afterDeduction});
		writer.close();
	}
    public double getAmt(){
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
	protected void getDownloadableFile(final String string) {
		// TODO Auto-generated method stub
		final Dialog dialog=new Dialog(context);

		//tell the Dialog to use the dialog.xml as it's layout description 
            dialog.setContentView(R.layout.mydialog); 
            dialog.setTitle("Download Budget Statement"); 
               TextView txt = (TextView) dialog.findViewById(R.id.txt); 
                 txt.setText("Download path:\n"+string+"/budgetStatement.csv"); 
              Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton); 
                dialogButton.setOnClickListener(new View.OnClickListener() { 
		         @Override 
                 public void onClick(View v) {
		        		launchRingDialog(v,string);
		        		launchBarDialog(v);
                       dialog.dismiss(); 
                    //  Toast.makeText(getApplicationContext(), "Download was successfully...", Toast.LENGTH_SHORT).show();
		              } 
                 }); 
              dialog.show(); 

	}
	private String getTodaysDate() {
		// TODO Auto-generated method stub
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		return sdf.format(date);
	}

	protected void writeCsvData(String f, String g, String h) throws IOException {
		// TODO Auto-generated method stub
		String line=String.format("%f%f%f%f\n", f, g,h);
		writer.write(line);
	}
	protected void writeCsvHeader(String string, String string2, String string3) throws IOException {
		// TODO Auto-generated method stub
		String line=String.format("%f%f%f%f\n", string, string2,string3);
		writer.write(line);
	}
	
	public void sentToEmail(String email) {
		// TODO Auto-generated method stub
    try {
		Uri u1 =   Uri.fromFile(file);

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Budget statement ");
		sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Your Budget statement is attached.");
		sendIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(sendIntent, "Select your Email Provider :"));

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
	}
	
//========================================================================
	 //==========================================================================

	 public void launchRingDialog(View view,String string) { 
       final ProgressDialog ringProgressDialog = ProgressDialog.show(ReportActivity.this, "Please wait ...", "Downloading File ...", true); 
        ringProgressDialog.setCancelable(true); 
       new Thread(new Runnable() { 
           @Override 


	             public void run() { 
               try { 
               	
               	//---SD Card Storage---
   				File sdCard = Environment.getExternalStorageDirectory();
   				getDownloadableFile(sdCard.getAbsolutePath() +
   						"/MyFiles");
                   // Here you should write your time consuming task... 
                    // Let the progress ring for 10 seconds... 
                    Thread.sleep(1978978); 
                } catch (Exception e) { 
                } 
               ringProgressDialog.dismiss(); 
           } 
       }).start(); 
  } 
    public void launchBarDialog(View view) { 
       barProgressDialog = new ProgressDialog(ReportActivity.this); 
       barProgressDialog.setTitle("Please wait..."); 
       barProgressDialog.setMessage("Download in progress ..."); 
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL); 
       barProgressDialog.setProgress(0); 
        barProgressDialog.setMax(20); 
        barProgressDialog.show(); 
       new Thread(new Runnable() { 
            @Override 
            public void run() { 
              try { 
                   // Here you should write your time consuming task... 
                 while (barProgressDialog.getProgress() <= barProgressDialog.getMax()) { 
                        Thread.sleep(2000); 
                        updateBarHandler.post(new Runnable() { 
                            public void run() { 
                                barProgressDialog.incrementProgressBy(2); 
                              }  
	                           }); 

		if (barProgressDialog.getProgress() == barProgressDialog.getMax()) { 
	                            barProgressDialog.dismiss();
                     } 
	                 } 
                
	                } catch (Exception e) { 
                   } 
	            } 
	         }).start(); 
      // Toast.makeText(getApplicationContext(), "Download was successfully...", Toast.LENGTH_SHORT).show();
       
   } 
	//==========================================================================
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
		case R.id.action_Bar_Graph:
			openChart();
			return true;
		case R.id.action_back:
			Intent mainIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(mainIntent);
		    return true;
		case R.id.action_download:
			getDownloadableFile(sdCardsDir.getAbsolutePath());
			return true;
		case R.id.action_email:
			getEmailSystem();
		//	if(!emailAddress.isEmpty()){
			//sentToEmail(emailAddress);
		//	}
			return true;
		case R.id.action_settings:
            Intent intentSettings=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intentSettings);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	protected void getEmailSystem() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this);
      
		// tell the Dialog to use the dialog.xml as it's layout description
		dialog.setContentView(R.layout.input_email);
		dialog.setTitle("Send Budget Statement");
		
		//txt.setText("Are you sure you want to Exit \n" + " Click Yes to Exit!");
		Button dialogButton = (Button) dialog.findViewById(R.id.btnSendToEmail);
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText txt = (EditText) dialog.findViewById(R.id.txtEmailAddress);
				emailAddress=txt.getText().toString();
				sentToEmail(emailAddress);
				dialog.dismiss();
				
				// Toast.makeText(getApplicationContext(),
				// "Download was successfully...", Toast.LENGTH_SHORT).show();
			}
		});

		dialog.show();
      
	}

}
