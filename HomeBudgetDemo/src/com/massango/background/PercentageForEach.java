package com.massango.background;

import java.text.DecimalFormat;

public class PercentageForEach {
    private int refId;
    private String strCategory;
    private double expenseAmount;
    private double expensePercentage;
	public PercentageForEach(int refId, String strCategory,
			double expenseAmount, double expensePercentage) {
		super();
		this.refId = refId;
		this.strCategory = strCategory;
		this.expenseAmount = expenseAmount;
		this.expensePercentage = expensePercentage;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String getStrCategory() {
		return strCategory;
	}
	public void setStrCategory(String strCategory) {
		this.strCategory = strCategory;
	}
	public double getExpenseAmount() {
		return expenseAmount;
	}
	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}
	public double getExpensePercentage() {
		return expensePercentage;
	}
	public void setExpensePercentage(double expensePercentage) {
		this.expensePercentage = expensePercentage;
	}
	@Override
	public String toString() {
		DecimalFormat datF=new DecimalFormat("0.00");
		DecimalFormat decF=new DecimalFormat("#.#");
		return  strCategory
				+ "\t\t R" + datF.format(expenseAmount) + "\t\t ("
				+ decF.format(expensePercentage) +"%)";
	}
    
}
