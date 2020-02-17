package com.massango.background;

public class Category {
   private int id;
   private String type;
   private String category;
   private double amount=0.0;
public Category(String type, String category) {
	super();
	this.type = type;
	this.category = category;
}

public Category(String type, String category, double amount) {
	super();
	this.type = type;
	this.category = category;
	this.amount = amount;
}

public Category(int id, String type, String category, double amount) {
	super();
	this.id = id;
	this.type = type;
	this.category = category;
	this.amount = amount;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public double getAmount() {
	return amount;
}

public void setAmount(double amount) {
	this.amount = amount;
}

@Override
public String toString() {
	return  category ;
}

}
