package com.massango.background;

public class AccountExpense {
	private int id;
	private double income;
	private String category;
	private String notes;
	private String date;
	private String expenseName;
	private String budget_Name;
	public AccountExpense() {
		// TODO Auto-generated constructor stub
	}

	public AccountExpense(int id, double income, String notes,
			String date) {
		super();
		this.id = id;
		this.income = income;
		this.notes = notes;
		this.date = date;
	}
	public AccountExpense(double income, String notes,
			String date) {
		super();
		this.income = income;
		this.notes = notes;
		this.date = date;
	}
	public AccountExpense(double income, String category, String notes,
			String date) {
		super();
		this.income = income;
		this.category = category;
		this.notes = notes;
		this.date = date;
	}
    
	public AccountExpense(double income, String category, String notes,
			String date, String expenseName) {
		super();
		this.income = income;
		this.category = category;
		this.notes = notes;
		this.date = date;
		this.expenseName = expenseName;
	}

	public AccountExpense(int id, double income, String category, String notes,
			String date, String expenseName) {
		super();
		this.id = id;
		this.income = income;
		this.category = category;
		this.notes = notes;
		this.date = date;
		this.expenseName = expenseName;
	}

	public AccountExpense(int id, double income, String notes, String date,
			String expenseName) {
		super();
		this.id = id;
		this.income = income;
		this.notes = notes;
		this.date = date;
		this.expenseName = expenseName;
	}

	public String getBudget_Name() {
		return budget_Name;
	}

	public void setBudget_Name(String budget_Name) {
		this.budget_Name = budget_Name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
