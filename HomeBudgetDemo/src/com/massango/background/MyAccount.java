package com.massango.background;

import java.util.List;

public interface MyAccount {
  public abstract double calculateTotalExpence();
  public abstract double debitOrCreditAmount(double incomeAmount);
}
