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
}
