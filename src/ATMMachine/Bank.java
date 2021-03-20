package ATMMachine;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

  private String bankName;
  private ArrayList<Client> bankClients;
  private ArrayList<Account> bankAccounts;

  /* Even though the ArrayList<Client> already
  * has a list of Clients and each one of those
  * has a list of Accounts, it's more convenient
  * and easy to keep track of it (the accounts)
  * as a ArrayList itself.
  * */

  public Bank (String bankName) {
    this.bankName = bankName;
    this.bankClients = new ArrayList<Client>();
    this.bankAccounts = new ArrayList<Account>();
  }

  public String getNewUserUUID() {
    String uuid;
    Random random = new Random();
    int len = 6;
    boolean nonUnique; //This tells us if the UUID is already been used (true) or not (false)

    do {
      uuid = "";
      for (int i = 0; i < len; i++) {
        uuid += ((Integer) random.nextInt(10)).toString();
      }

      nonUnique = false;
      for (Client c : this.bankClients) {
        if (uuid.compareTo(c.getUUID()) == 0) {
          nonUnique = true;
          break;
        }
      }
    } while (nonUnique);

    return uuid;
  }

  public String getNewAccountUUID() {
    String uuid;
    Random random = new Random();
    int len = 10;
    boolean nonUnique;

    do {
      uuid = "";
      for (int i = 0; i < len; i++) {
        uuid += ((Integer) random.nextInt(10)).toString();
      }

      nonUnique = false;
      for (Account a : this.bankAccounts) {
        if (uuid.compareTo(a.getUUID()) == 0) {
          nonUnique = true;
          break;
        }
      }
    } while (nonUnique);

    return uuid;

  }

  public void addAccount (Account account) {
    this.bankAccounts.add(account);
  }

  public Client addClient (String firstName, String lastName, String pin) {
    //Creating the Client using its personal informations
    Client newClient = new Client(firstName, lastName, pin, this);
    this.bankClients.add(newClient);

    //Creating the Client's Savings Account
    Account newAccount = new Account("Savings", newClient, this);
    newClient.addAccount(newAccount);
    this.addAccount(newAccount);

    return newClient;
  }

  public  Client clientLogin (String userID, String pin) {
    //Seacrhing through the Cliente ArrayList to check
    //if we can find the correct userID
    for (Client c : this.bankClients) {
      if (c.getUUID().compareTo(userID) == 0 && c.validatePin(pin)) {
        return c;
      }
    }
    return null;
  }

  public String getBankName() {
    return bankName;
  }
}
