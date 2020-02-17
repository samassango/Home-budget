package com.massango.background;

public class ItemShopping {
  private int id;
  private String itemName;
  private String expCategory;
  private int qty;
  private double amount;
  private boolean status;
  private String budgetName;
  
public ItemShopping(int id, String itemName, int qty, double amount,
		boolean status) {
	super();
	this.id = id;
	this.itemName = itemName;
	this.qty = qty;
	this.amount = amount;
	this.status = status;
}
public ItemShopping(int id, String itemName, double amount, boolean status) {
	super();
	this.id = id;
	this.itemName = itemName;
	this.amount = amount;
	this.status = status;
}

public ItemShopping(int id, String itemName, String expCategory, int qty,
		double amount, boolean status) {
	super();
	this.id = id;
	this.itemName = itemName;
	this.expCategory = expCategory;
	this.qty = qty;
	this.amount = amount;
	this.status = status;
}

public ItemShopping(int id, String itemName, String expCategory, int qty,
		double amount, boolean status, String budgetName) {
	super();
	this.id = id;
	this.itemName = itemName;
	this.expCategory = expCategory;
	this.qty = qty;
	this.amount = amount;
	this.status = status;
	this.budgetName = budgetName;
}
public String getBudgetName() {
	return budgetName;
}
public void setBudgetName(String budgetName) {
	this.budgetName = budgetName;
}
public String getExpCategory() {
	return expCategory;
}
public void setExpCategory(String expTYPE) {
	this.expCategory = expTYPE;
}
public int getId() {
	return id;
}

public int getQty() {
	return qty;
}
public void setQty(int qty) {
	this.qty = qty;
}
public void setId(int id) {
	this.id = id;
}
public String getItemName() {
	return itemName;
}
public void setItemName(String itemName) {
	this.itemName = itemName;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public boolean isStatus() {
	return status;
}
public void setStatus(boolean status) {
	this.status = status;
}
@Override
public String toString() {
	return  qty+ "\t | " + itemName + "\t | " + amount ;
}
  
  
}
