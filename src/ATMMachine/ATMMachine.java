package ATMMachine;

import java.util.Scanner;

public class ATMMachine {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    //Init Bank
    Bank theBank = new Bank("Bank of The United States");

    //Init Client with a Savings Account
    Client firstClient = theBank.addClient("Donald", "Trump", "2016");

    //Adding a Checking Account for Donald Trump
    Account newAccount = new Account("Checking", firstClient, theBank);
    firstClient.addAccount(newAccount);
    theBank.addAccount(newAccount);
    
  }

}
