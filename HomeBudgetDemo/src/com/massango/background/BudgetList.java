package com.massango.background;

public class BudgetList {
	private String budgetName;
	private String description;
    private int id;
	public BudgetList(String budgetName) {
		super();
		this.budgetName = budgetName;
	}
   
	public BudgetList(String budgetName, String description) {
		super();
		this.budgetName = budgetName;
		this.description = description;
	}
    
	public BudgetList( int id,String budgetName, String description) {
		super();
		this.budgetName = budgetName;
		this.description = description;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	@Override
	public String toString() {
		return  budgetName + "\t"
				+ description ;
	}
    
}
