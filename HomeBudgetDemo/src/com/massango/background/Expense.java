package com.massango.background;

import java.util.List;


public class Expense implements MyAccount{
   private int expenseID;
   private String expenseCategory;
   private String expenseDate;
   private String expenseName;
   private double expenseAmount;
   private String expenseNotes;
   private double totalAmount=0;
   private List<AccountExpense> amountObject;
public Expense() {
	super();
}

public Expense(String expenseDate, String expenseName, double expenseAmount,
		String expenseNotes) {
	super();
	this.expenseDate = expenseDate;
	this.expenseName = expenseName;
	this.expenseAmount = expenseAmount;
	this.expenseNotes = expenseNotes;
}

public Expense(List<AccountExpense> amountObject) {
	super();
	this.amountObject = amountObject;
}

public Expense(String expenseCategory, String expenseDate, String expenseName,
		double expenseAmount, String expenseNotes,
		List<AccountExpense> amountObject) {
	super();
	this.expenseCategory = expenseCategory;
	this.expenseDate = expenseDate;
	this.expenseName = expenseName;
	this.expenseAmount = expenseAmount;
	this.expenseNotes = expenseNotes;
	this.amountObject = amountObject;
}

public Expense(int expenseID, String expenseDate, String expenseName,
		double expenseAmount, String expenseNotes) {
	super();
	this.expenseID = expenseID;
	this.expenseDate = expenseDate;
	this.expenseName = expenseName;
	this.expenseAmount = expenseAmount;
	this.expenseNotes = expenseNotes;
}

public Expense(int expenseID, String expenseCategory, String expenseDate,
		String expenseName, double expenseAmount, String expenseNotes,
		List<AccountExpense> amountObject) {
	super();
	this.expenseID = expenseID;
	this.expenseCategory = expenseCategory;
	this.expenseDate = expenseDate;
	this.expenseName = expenseName;
	this.expenseAmount = expenseAmount;
	this.expenseNotes = expenseNotes;
	this.amountObject = amountObject;
}

public int getExpenseID() {
	return expenseID;
}

public void setExpenseDate(String expenseDate) {
	this.expenseDate = expenseDate;
}

public void setExpenseID(int expenseID) {
	this.expenseID = expenseID;
}

public String getExpenseDate() {
	return expenseDate;
}

public String getExpenseName() {
	return expenseName;
}

public void setExpenseName(String expenseName) {
	this.expenseName = expenseName;
}

public double getExpenseAmount() {
	return expenseAmount;
}

public void setExpenseAmount(double expenseAmount) {
	this.expenseAmount = expenseAmount;
}

public String getExpenseNotes() {
	return expenseNotes;
}

public void setExpenseNotes(String expenseNotes) {
	this.expenseNotes = expenseNotes;
}

@Override
public double calculateTotalExpence() {
	// TODO Auto-generated method stub
	
	for(AccountExpense accountObj:amountObject){
		totalAmount+=accountObj.getIncome();
	}
	return totalAmount;
}

@Override
public double debitOrCreditAmount(double incomeAmount) {
	// TODO Auto-generated method stub
	double expenseAmount=0;
	expenseAmount=incomeAmount-totalAmount;
	return expenseAmount;
}


}
