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

  public double getAmount() {
    return this.amount;
  }

  public String getSummaryLine() {
    if (this.amount >= 0) {
      return String.format("%s : $%.02f", this.timeStamp.toString(), this.amount);
    } else {
      return String.format("%s : $(%.02f)", this.timeStamp.toString(), this.amount);
    }
  }

}
