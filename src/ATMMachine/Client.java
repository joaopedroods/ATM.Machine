package ATMMachine;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Client {

  private String firstName;
  private String lastName;
  private String uuid; //Universal Unique Identifier - It's simply a code given to indentify the client
  private byte pinHash[];
  private ArrayList<Account> clientAccounts;

  public Client (String firstName, String lastName, String pin, Bank clientBank) {
    this.firstName = firstName;
    this.lastName = lastName;

    //Storing the pin's MD5 hash instead of the original value is safer
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      this.pinHash = messageDigest.digest(pin.getBytes());
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error! Caught NoSuchAlgorithmException");
      e.printStackTrace();
      System.exit(1);
    }

    this.uuid = clientBank.getNewUserUUID();
    this.clientAccounts = new ArrayList<Account>();

    //Printing that the new client has been registered
    System.out.printf("New cliente %s, %s with ID %s created!\n", lastName, firstName, this.uuid);
  }

  public void addAccount (Account account) {
    this.clientAccounts.add(account);
  }

  public String getUUID() {
    return this.uuid;
  }

  public boolean validatePin (String pin) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      return MessageDigest.isEqual(messageDigest.digest(pin.getBytes()), this.pinHash);
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error! Caught NoSuchAlgorithmException");
      e.printStackTrace();
      System.exit(1);
    }
    return false;
  }

  public String getFirstName() {
    return firstName;
  }

  public void printAccountsSummary() {
    System.out.printf("\n\n%s's accounts summary", this.firstName);

    for (int a = 0; a < this.clientAccounts.size(); a++) {
      System.out.printf("%d - %s\n", a + 1, this.clientAccounts.get(a).getSummaryLine());
    }
    System.out.println();
  }

  public int numAccounts() {
    return this.clientAccounts.size();
  }

  public void printAcctTransHistory (int acctIdx) {
    this.clientAccounts.get(acctIdx).printTransHistory();
  }

  public double getAccountBalance (int accIdx) {
    return this.clientAccounts.get(accIdx).getBalance();
  }

  public String getAcctUUID (int acctIdx) {
    return this.clientAccounts.get(acctIdx).getUUID();
  }

  public void addAccountTransaction (int acctIdx, double amount, String memo) {
    this.clientAccounts.get(acctIdx).addTransactions(amount);
  }
}
