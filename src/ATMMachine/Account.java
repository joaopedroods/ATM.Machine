package ATMMachine;

import java.util.ArrayList;

public class Account {

  private String accountType; //To identify if it's a Checking Account or a Savings Account
  private String accountUuid;
  private Client holder;
  private ArrayList<Transactions> accountTransactions;
  private String memo;

  public Account (String accountType, Client holder, Bank accountBank) {
    this.accountType = accountType;
    this.holder = holder;
    this.accountUuid = accountBank.getNewAccountUUID();
    this.accountTransactions = new ArrayList<Transactions>();
    this.memo = "";
  }

  public String getUUID() {
    return this.accountUuid;
  }

  public String getSummaryLine() {
    //Getting the Account's balance
    double balance = this.getBalance();

    //Formatting the summary line for negative balance
    if (balance >= 0) {
      return String.format("%s - $%.2f - %s", this.accountUuid, balance, this.accountType);
    } else {
      return String.format("%s - $(%.2f) - %s", this.accountUuid, balance, this.accountType);
    }
  }

  public double getBalance() {
    double balance = 0;
    for (Transactions t : this.accountTransactions) {
      balance += t.getAmount();
    }
    return balance;
  }

  public void printTransHistory() {
    System.out.printf("\nTransaction history for account %s\n", this.accountUuid);
    for (int t = this.accountTransactions.size() - 1; t >= 0; t--) {
      System.out.print(this.accountTransactions.get(t).getSummaryLine());
    }
    System.out.println();
  }

  public void addTransactions (double amount) {
    //Create new transaction object and add it to our list
    Transactions newTrans = new Transactions(amount, this);
    this.accountTransactions.add(newTrans);
  }

}
