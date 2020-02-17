package com.massango.background;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HomeBudgetDatabase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "homeBudgetDbase.db";
	private static final int DATABASE_VERSION = 1;

	// columns
	private static final String KEY_ID = "_id";
	private static final String KEY_BUDGET_NAME = "budgetName";
	private static final String KEY_BUDGET_DESC = "description";
	private static final String KEY_BUDGET_DATE = "date";
	private static final String KEY_BUDGET_INCOME = "income";
	private static final String KEY_BUDGET_NOTES = "note";
	private static final String KEY_BUDGET_TYPE = "type";
	private static final String KEY_BUDGET_CATEGORY = "category";
	private static final String KEY_BUDGET_AMOUNT = "amount";
	
	private static final String KEY_EXPENSE_NOTES = "notes";//this it's a budget name where the expense belong.
	private static final String KEY_EXPENSE_NAME = "expenseName";
	private static final String KEY_EXPENSE_CATEGORY = "category";
	private static final String KEY_EXPENSE_AMOUNT = "expenseAmount";
	private static final String KEY_EXPENSE_STATUS = "status_date";
	private static final String KEY_EXPENSE_BUDGETNAME = "budgetName";
	private static final String KEY_EXPENSE_DATE = "date";
	private static final String ROW_ITEM_NAME = "itemName";
	private static final String ROW_ITEM_PRICE = "itemPrice";
	private static final String ROW_ITEM_QUANTITY = "itemQuantity";
	private static final String ROW_ITEM_STATUS = "status";
	private static final String ROW_ITEM_CATEGORY = "category";
	private static final String ROW_ITEM_REFID = "refID";
	private static final String ROW_ITEM_BUDGETNAME = "budgetName";
	private static final String ROW_SETTINGS = "settings";
	
	// table names
	private static final String TBL_BUDGET_LIST = "tblbudgetList";
	private static final String TBL_ACCOUNT="tblAccount";
	private static final String TBL_CATEGORY="tblcategory";
	private static final String TBL_EXPENSE="tblExpense";
	private static final String TBL_ITEMS="tblItems";
	private static final String TBL_SETTINGS = "tblSettings";
	
	// create table
	private static final String CREATE_TABLE_BUDGET = "create table tblbudgetList(_id integer primary key autoincrement,budgetName text not null,description text not null)";
	private static final String CREATE_TABLE_ACCOUNT = "create table tblAccount(_id integer primary key autoincrement,date text not null,note text not null,income double not null,budgetName text not null)";
	private static final String CREATE_TABLE_CATEGORY = "create table tblcategory(_id integer primary key autoincrement,type text not null,category text not null,amount double not null)";
	private static final String CREATE_TABLE_EXPENSE = "create table tblExpense(_id integer primary key autoincrement,expenseName text not null,category text not null,date text not null,notes text not null,expenseAmount double not null,budgetName text not null)";
	private static final String CREATE_TABLE_ITEMS = "create table tblItems(_id integer primary key autoincrement,itemName text not null,category text not null,itemPrice double not null,itemQuantity integer not null,status boolean not null,refID Integer not null,budgetName text not null)";
	private static final String CREATE_TABLE_SETTINGS="create table tblSettings(_id integer primary key autoincrement,settings text not null)";
	
	
	
	
	
	
	
	//database
	SQLiteDatabase db;
	
	public HomeBudgetDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
       db.execSQL(CREATE_TABLE_BUDGET);
       db.execSQL(CREATE_TABLE_ACCOUNT);
       db.execSQL(CREATE_TABLE_CATEGORY);
       db.execSQL(CREATE_TABLE_EXPENSE);
       db.execSQL(CREATE_TABLE_ITEMS);
       db.execSQL(CREATE_TABLE_SETTINGS);
       
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
       db.execSQL("DROP TABLE IF EXISTS " + TBL_BUDGET_LIST);
       db.execSQL("DROP TABLE IF EXISTS " + TBL_ACCOUNT);
       db.execSQL("DROP TABLE IF EXISTS " + TBL_CATEGORY);
       db.execSQL("DROP TABLE IF EXISTS " + TBL_EXPENSE);
       db.execSQL("DROP TABLE IF EXISTS " + TBL_ITEMS);
       db.execSQL("DROP TABLE IF EXISTS " + TBL_SETTINGS);
       onCreate(db);
	}
    public HomeBudgetDatabase openBD(){
    	db=this.getWritableDatabase();
    	return this;
    }
    public void closeDB(){
    	close();
    }
    public long insertCategory(Category c)
    {
    	long id=0;
    	db=this.getWritableDatabase();
    	ContentValues values=new ContentValues();
    	values.put(KEY_BUDGET_TYPE, c.getType());
    	values.put(KEY_BUDGET_CATEGORY, c.getCategory());
    	values.put(KEY_BUDGET_AMOUNT,c.getAmount());
    	id=db.insert(TBL_CATEGORY, null, values);
    	
        return  id;
    }
    public long updateCategory(Category c)
    {
    	db=this.getWritableDatabase();
    	ContentValues values=new ContentValues();
    	values.put(KEY_BUDGET_TYPE, c.getType());
    	values.put(KEY_BUDGET_CATEGORY, c.getCategory());
		return db.update(TBL_CATEGORY, values, KEY_BUDGET_CATEGORY +"=?", new String[] {String.valueOf(c.getCategory())});
   
    }
    public List<Category> getCategory(){
    	db=this.getReadableDatabase();
    	List<Category> list=new ArrayList<Category>();
    	Category b;
    	Cursor c=db.query(TBL_CATEGORY, new String[]{KEY_ID,KEY_BUDGET_TYPE,KEY_BUDGET_CATEGORY,KEY_BUDGET_AMOUNT},null, null, null, null, null);
    	if(c.moveToFirst()){
    		do{
    			int id=Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID)));
    			double amount=Double.parseDouble(c.getString(c.getColumnIndex(KEY_BUDGET_AMOUNT)));
    		    String type=c.getString(c.getColumnIndex(KEY_BUDGET_TYPE));
    		    String category=c.getString(c.getColumnIndex(KEY_BUDGET_CATEGORY));
    			b=new Category(id, type, category, amount);
    			list.add(b);
    		}while(c.moveToNext());
    	}
		return list;	
    }
    public long insertAccount(AccountExpense a)
    {
    	long id=0;
    	db=this.getWritableDatabase();
    	ContentValues values=new ContentValues();
    	values.put(KEY_BUDGET_DATE, a.getDate());
    	values.put(KEY_BUDGET_INCOME, a.getIncome());
    	values.put(KEY_BUDGET_NOTES, a.getNotes());
    	values.put(KEY_BUDGET_NAME, a.getBudget_Name());
    	id=db.insert(TBL_ACCOUNT, null, values);
    	
        return  id;
    }
    public long insertData(BudgetList b)
    {
    	long id=0;
    	ContentValues values=new ContentValues();
    	
    	values.put(KEY_BUDGET_NAME, b.getBudgetName());
    	values.put(KEY_BUDGET_DESC, b.getDescription());
         id=db.insert(TBL_BUDGET_LIST, null, values);
    	
    	return id;
    }
    public List<AccountExpense> getAccount(){
    	db=this.getReadableDatabase();
    	List<AccountExpense> list=new ArrayList<AccountExpense>();
    	AccountExpense b;
    	Cursor c=db.query(TBL_ACCOUNT, new String[]{KEY_ID,KEY_BUDGET_INCOME,KEY_BUDGET_NOTES,KEY_BUDGET_DATE,KEY_BUDGET_NAME},null, null, null, null, null);
    	if(c.moveToFirst()){
    		do{
    			int id=Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID)));
    			double income=Double.parseDouble(c.getString(c.getColumnIndex(KEY_BUDGET_INCOME)));
    		    String notes=c.getString(c.getColumnIndex(KEY_BUDGET_NOTES));
    		    String date=c.getString(c.getColumnIndex(KEY_BUDGET_DATE));
    		    String bg=c.getString(c.getColumnIndex(KEY_BUDGET_NAME));
    			b=new AccountExpense(id,income,notes,date);
    			b.setBudget_Name(bg);
    			list.add(b);
    		}while(c.moveToNext());
    	}
		return list;	
    }
    public List<BudgetList> getBudget(){
    	db=this.getReadableDatabase();
    	List<BudgetList> list=new ArrayList<BudgetList>();
    	BudgetList b;
    	Cursor c=db.query(TBL_BUDGET_LIST, new String[]{KEY_ID,KEY_BUDGET_NAME,KEY_BUDGET_DESC},null, null, null, null, null);
    	if(c.moveToFirst()){
    		do{
    			int id=Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID)));
    			String budgetName=c.getString(c.getColumnIndex(KEY_BUDGET_NAME));
    		    String description=c.getString(c.getColumnIndex(KEY_BUDGET_DESC));
    			b=new BudgetList(id,budgetName, description);
    			list.add(b);
    		}while(c.moveToNext());
    	}
    	return list;
    }
    public long updateBudget(int id,String bName,String bDesc){
    	db=this.getWritableDatabase();
    	ContentValues values=new ContentValues();
    	values.put(KEY_BUDGET_NAME, bName);
    	values.put(KEY_BUDGET_DESC, bDesc);
		return db.update(TBL_BUDGET_LIST, values, KEY_ID +"=?", new String[] {String.valueOf(id)});
    }

	public long deleteCategory(long id) {
		// TODO Auto-generated method stub
		db=this.getWritableDatabase();
		return db.delete(TBL_CATEGORY, KEY_ID+"=?", new String[] {String.valueOf(id)});
	}

	public Cursor getCursorCategory() {
		// TODO Auto-generated method stub
		db=this.getReadableDatabase();
		//String sql="SELECT _id,type,category,'R'||amount FROM "+TBL_CATEGORY +"ORDER BY _id";
		Cursor c =db.query(TBL_CATEGORY, new String[]{KEY_ID,KEY_BUDGET_TYPE,KEY_BUDGET_CATEGORY,KEY_BUDGET_AMOUNT},null, null, null, null, null);
	    return c;
	}
	 public long updateCategory(int id,double amount){
	    	db=this.getWritableDatabase();
	    //String sql="UPDATE "+ TBL_CATEGORY+" SET amount=amount+"+amount+" WHERE "+KEY_ID+"="+id;
		//	db.execSQL(sql);
	    	db=this.getWritableDatabase();
	    	ContentValues values=new ContentValues();
	    	values.put(KEY_BUDGET_AMOUNT, amount);
	    	
			return db.update(TBL_CATEGORY, values, KEY_ID +"=?", new String[] {String.valueOf(id)});
	    
	    }
	public long insertExpense(AccountExpense a) {
		// TODO Auto-generated method stub
		long id=0;
    	db=this.getWritableDatabase();
    	ContentValues values=new ContentValues();
    	/**values.put(KEY_EXPENSE_DATE, a.getDate());
    	values.put(KEY_EXPENSE_AMOUNT, a.getIncome());
    	values.put(KEY_EXPENSE_NOTES, a.getNotes());
    	values.put(KEY_EXPENSE_NAME, a.getExpenseName());
    	values.put(KEY_EXPENSE_CATEGORY, a.getCategory());
    	values.put(KEY_EXPENSE_BUDGETNAME, a.getBudget_Name());
    	id=db.insert(TBL_EXPENSE, null, values);*/
    	
    	values.put(ROW_ITEM_PRICE, a.getIncome());
    	values.put(ROW_ITEM_QUANTITY, 1);
    	values.put(ROW_ITEM_REFID, 0);
        values.put(ROW_ITEM_STATUS, false);
    	values.put(ROW_ITEM_NAME, a.getExpenseName());
    	values.put(ROW_ITEM_CATEGORY, a.getCategory());
    	values.put(ROW_ITEM_BUDGETNAME, a.getBudget_Name());
		return id;
	}
/**	public List<AccountExpense> getAllExpenses()
	{
		db=this.getReadableDatabase();
    	List<AccountExpense> list=new ArrayList<AccountExpense>();
    	AccountExpense b;
    	Cursor c=db.query(TBL_EXPENSE, new String[]{KEY_ID,KEY_EXPENSE_DATE,KEY_EXPENSE_AMOUNT,KEY_EXPENSE_CATEGORY,KEY_EXPENSE_NOTES,KEY_EXPENSE_NAME},null, null, null, null, null);
    	if(c.moveToFirst()){
    		do{
    			int id=Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID)));
    			double income=Double.parseDouble(c.getString(c.getColumnIndex(KEY_EXPENSE_AMOUNT)));
    		    String budgetName=c.getString(c.getColumnIndex(KEY_EXPENSE_NOTES));
    		    String category=c.getString(c.getColumnIndex(KEY_EXPENSE_CATEGORY));
    		    String date=c.getString(c.getColumnIndex(KEY_EXPENSE_DATE));
    		    String expenseName=c.getString(c.getColumnIndex(KEY_EXPENSE_NAME));
    			b=new AccountExpense(id, income,category, budgetName, date, expenseName);
    			list.add(b);
    		}while(c.moveToNext());
    	}
    	return list;
		
	}*/
	public Cursor getAllExpensesCursor()
	{
		db=this.getReadableDatabase();
    	Cursor c=db.query(TBL_EXPENSE, new String[]{KEY_ID,KEY_EXPENSE_DATE,KEY_EXPENSE_AMOUNT,KEY_EXPENSE_CATEGORY,KEY_EXPENSE_NOTES,KEY_EXPENSE_NAME},null, null, null, null, null);
    	
    	return c;
		
	}
   public long deleteAllPreviousExpenses(){
	  // String sql="DELETE * FROM "+TBL_EXPENSE;
	return db.delete(TBL_EXPENSE, null, null);
	   
   }
   
  public void updateTBL_Category(){
	  db=this.getWritableDatabase();
	  double amount=0;
	  List<Category> list=getCategory();
	  for(int x=0;x<list.size();x++){
		  Category c=list.get(x);
		//  List<AccountExpense> lst=getAllExpenses();
		  List<ItemShopping> listEx=getShoppingList();
		 /** for(int i=0;i<lst.size();i++){
			  AccountExpense a=lst.get(i);
			  if(c.getCategory().equalsIgnoreCase(a.getCategory())){
				  amount+=a.getIncome();
				  i=lst.size();
			  }
		  }*/
		  for(int y=0;y<listEx.size();y++){
			  ItemShopping i=listEx.get(y);
			  if(c.getCategory().equalsIgnoreCase(i.getExpCategory())){
				  amount+=i.getAmount();
				  y=listEx.size();
			  }
		  }
		  updateCategory(c.getId(), amount);
		  amount=0;
	  }
  }
  public double getTotalAmountCat(){
	  double totalAmount=0;
	  List<Category> l=getCategory();
	  for(int i=0;i<l.size();i++){
		  totalAmount+=l.get(i).getAmount();
	  }
	return totalAmount; 
  }

public long addItemInDB(String string,String string2, double parseDouble, int parseInt,boolean recurring,int refID) {
	// TODO Auto-generated method stub
	ContentValues c=new ContentValues();
	c.put(ROW_ITEM_NAME, string);
	c.put(ROW_ITEM_CATEGORY, string2);
	c.put(ROW_ITEM_PRICE, parseDouble);
	c.put(ROW_ITEM_QUANTITY,parseInt );
	c.put(ROW_ITEM_STATUS,recurring );
	c.put(ROW_ITEM_REFID,refID );
	
	return db.insert(TBL_ITEMS, null, c);
}
public long addItemInDB(String string,String string2, double parseDouble, int parseInt,boolean recurring,int refID,String budgetName) {
	// TODO Auto-generated method stub
	ContentValues c=new ContentValues();
	c.put(ROW_ITEM_NAME, string);
	c.put(ROW_ITEM_CATEGORY, string2);
	c.put(ROW_ITEM_PRICE, parseDouble);
	c.put(ROW_ITEM_QUANTITY,parseInt );
	c.put(ROW_ITEM_STATUS,recurring );
	c.put(ROW_ITEM_REFID,refID );
	c.put(ROW_ITEM_BUDGETNAME,budgetName );
	return db.insert(TBL_ITEMS, null, c);
}
public Cursor getShoppingCursor(String category) {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	String sql="SELECT * FROM tblItems WHERE category='"+category+"'";
	Cursor cursor=db.rawQuery(sql, null);
	//Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS},null, null, null, null, null);
	return cursor;
}
public Cursor getShoppingCursorBudget(String budgetName) {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	String sql="SELECT * FROM tblItems WHERE budgetName='"+budgetName+"'";
	Cursor cursor=db.rawQuery(sql, null);
	//Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS},null, null, null, null, null);
	return cursor;
}
public Cursor getShoppingCursor(String category,String budgetName) {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	String sql="SELECT * FROM tblItems WHERE category='"+category+"' AND budgetName='"+budgetName+"'";
	Cursor cursor=db.rawQuery(sql, null);
	//Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS},null, null, null, null, null);
	return cursor;
}
public Cursor getShoppingCursor() {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	
	Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS},null, null, null, null, null);
	return c;
}
public List<ItemShopping> getShoppingList() {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	List<ItemShopping> itemS = new ArrayList<ItemShopping>();
	ItemShopping b;
	Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS,ROW_ITEM_BUDGETNAME},null, null, null, null, null);
	if(c.moveToFirst()){
		do{
			int id=Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID)));
			String itemName=c.getString(c.getColumnIndex(ROW_ITEM_NAME));
			String itemCategory=c.getString(c.getColumnIndex(ROW_ITEM_CATEGORY));
			int qty =Integer.parseInt(c.getString(c.getColumnIndex(ROW_ITEM_QUANTITY)));
		    double amount=Double.parseDouble(c.getString(c.getColumnIndex(ROW_ITEM_PRICE)));
		    boolean status=Boolean.parseBoolean(c.getString(c.getColumnIndex(ROW_ITEM_STATUS)));
		    String bgName=c.getString(c.getColumnIndex(ROW_ITEM_BUDGETNAME));
			b=new ItemShopping(id, itemName,itemCategory, qty, amount, status,bgName);
			itemS.add(b);
		}while(c.moveToNext());
	}
	
	return itemS;
}
public List<ItemShopping> getShoppingList(String budgetName,String category) {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
   // String sql="SELECT * FROM tblItems WHERE category='"+category+"' AND budgetName='"+budgetName+"'";
	// Cursor c=db.rawQuery(sql, null);
    List<ItemShopping> itemS = new ArrayList<ItemShopping>();
	ItemShopping b;
	//Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS,ROW_ITEM_BUDGETNAME},null, null, null, null, null);
	Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS,ROW_ITEM_BUDGETNAME},ROW_ITEM_CATEGORY+"='"+category+"' AND "+ROW_ITEM_BUDGETNAME+"='"+budgetName+"'", null, null, null, null);	
	if(c.moveToFirst()){
		do{
			int id=Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID)));
			String itemName=c.getString(c.getColumnIndex(ROW_ITEM_NAME));
			String itemCategory=c.getString(c.getColumnIndex(ROW_ITEM_CATEGORY));
			int qty =Integer.parseInt(c.getString(c.getColumnIndex(ROW_ITEM_QUANTITY)));
		    double amount=Double.parseDouble(c.getString(c.getColumnIndex(ROW_ITEM_PRICE)));
		    boolean status=Boolean.parseBoolean(c.getString(c.getColumnIndex(ROW_ITEM_STATUS)));
		    String bgName=c.getString(c.getColumnIndex(ROW_ITEM_BUDGETNAME));
			b=new ItemShopping(id, itemName,itemCategory, qty, amount, status,bgName);
			itemS.add(b);
		}while(c.moveToNext());
	}
	
	return itemS;
}
public double getTotalListAmount() {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	double totalAmount=0;
	Cursor c=db.query(TBL_ITEMS, new String[]{KEY_ID,ROW_ITEM_NAME,ROW_ITEM_CATEGORY,ROW_ITEM_QUANTITY,ROW_ITEM_PRICE,ROW_ITEM_STATUS},null, null, null, null, null);
	if(c.moveToFirst()){
		do{
			
		    double amount=Double.parseDouble(c.getString(c.getColumnIndex(ROW_ITEM_PRICE)));
		    totalAmount+=amount;
		}while(c.moveToNext());
	}
	
	return totalAmount;
}
public double getTotalListAmount(String category) {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	double totalAmount=0;
	String sql="SELECT * FROM tblItems WHERE category='"+category+"'";
	Cursor c=db.rawQuery(sql, null);
	if(c.moveToFirst()){
		do{
			
		    double amount=Double.parseDouble(c.getString(c.getColumnIndex(ROW_ITEM_PRICE)));
		    totalAmount+=amount;
		}while(c.moveToNext());
	}
	
	return totalAmount;
}
public double getBudgetTotalAmount(String budgetName) {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	double totalAmount=0;
	String sql="SELECT * FROM tblItems WHERE budgetName='"+budgetName+"'";
	Cursor c=db.rawQuery(sql, null);
	if(c.moveToFirst()){
		do{
			
		    double amount=Double.parseDouble(c.getString(c.getColumnIndex(ROW_ITEM_PRICE)));
		    totalAmount+=amount;
		}while(c.moveToNext());
	}
	
	return totalAmount;
}
public double getTotalListAmount(String category,String budgetName) {
	// TODO Auto-generated method stub
	db=this.getReadableDatabase();
	double totalAmount=0;
	String sql="SELECT * FROM tblItems WHERE category='"+category+"' AND budgetName='"+budgetName+"'";
	Cursor c=db.rawQuery(sql, null);
	if(c.moveToFirst()){
		do{
			
		    double amount=Double.parseDouble(c.getString(c.getColumnIndex(ROW_ITEM_PRICE)));
		    totalAmount+=amount;
		}while(c.moveToNext());
	}
	
	return totalAmount;
}
protected String getTodaysDate() {
	// TODO Auto-generated method stub
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("mm-dd-yyyy");
	return sdf.format(date.getDate());
}

public long updateExpense(AccountExpense a) {
	// TODO Auto-generated method stub
	db=this.getWritableDatabase();
	ContentValues values=new ContentValues();
	values.put(ROW_ITEM_PRICE, a.getIncome());
	return db.update(TBL_ITEMS, values, ROW_ITEM_BUDGETNAME +"=? AND "+ROW_ITEM_NAME+"=?", new String[] {a.getBudget_Name(),a.getExpenseName()});
}

public void addSettings(String[] settings) {
	// TODO Auto-generated method stub

	for(int x=0;x<settings.length;x++){	
		ContentValues v=new ContentValues();
	    db=this.getWritableDatabase();
		v.put(ROW_SETTINGS, settings[x]);
		db.insert(TBL_SETTINGS, null, v);
	}
}
public Cursor getSettings(){
	db=this.getReadableDatabase();
	Cursor c=db.query(TBL_SETTINGS,new String[]{ ROW_SETTINGS}, null, null, null, null, null);
	return c;
}

}
