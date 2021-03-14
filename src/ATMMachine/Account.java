package ATMMachine;

import java.util.ArrayList;

public class Account {

  private String accountType; //To identify if it's a Checking Account or a Savings Account
  private String accountUuid;
  private Client holder;
  private ArrayList<Transactions> accountTransactions;

  public Account (String accountType, Client holder, Bank accountBank) {
    this.accountType = accountType;
    this.holder = holder;
    this.accountUuid = accountBank.getNewAccountUUID();
    this.accountTransactions = new ArrayList<Transactions>();

    //Adding the account to the holder and bank lists
    holder.addAccount(this);
    accountBank.addAccount(this);
  }

  public String getUUID() {
    return this.accountUuid;
  }

}
