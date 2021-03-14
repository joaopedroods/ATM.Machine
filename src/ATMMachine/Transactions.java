package ATMMachine;

import java.util.Date;

public class Transactions {

  private double amount;
  private Date timeStamp;
  private Account inAccount;

  public Transactions (double amount, Account inAccount) {
    this.amount = amount;
    this.inAccount = inAccount;
    this.timeStamp = new Date();
  }



}
