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

    Client currentClient;
    while (true) {

      //Stay in the login prompt until successful login
      currentClient = ATMMachine.mainMenuPrompt(theBank, scanner);

      //Stay in main menu until user quits
      ATMMachine.printClientMenu(currentClient, scanner);
    }
  }

  public static Client mainMenuPrompt (Bank theBank, Scanner scanner) {
    String userID;
    String pin;
    Client authUser; //Authenticated User

    //Prompt the client for client ID/pin combo until a correct one is reached
    do {
      System.out.printf("\n\n Welcome to %s\n\n", theBank.getBankName());

      System.out.print("Enter user ID: ");
      userID = scanner.nextLine();
      System.out.print("Enter pin: ");
      pin = scanner.nextLine();

      //Try to get the Client corresponding to the ID an pin
      authUser = theBank.clientLogin(userID, pin); //If the login is correct, it returns an Client. If it's not, it returns null
      if (authUser == null) {
        System.out.println("Incorrect user ID or pin" + "Please, try again.");
      }
    } while (authUser == null); //Keep looping until successful login
    return authUser;
  }

  public static void printClientMenu (Client currentClient, Scanner scanner) {
    //Printing a summary of the client's accounts
    currentClient.printAccountsSummary();

    int choice;
    //User menu
    do {
      System.out.printf("Welcome, %s. What would you like to do next?", currentClient.getFirstName());
      System.out.println("1 - Show account transaction history");
      System.out.println("2 - Withdrawl money");
      System.out.println("3 - Make a deposit");
      System.out.println("4 - Transfer");
      System.out.println("5 - Quit");
      System.out.println();
      System.out.print("Enter choice: ");
      choice = scanner.nextInt();

      if (choice < 1 || choice > 5) {
        System.out.println("Invalid choice. Please choose 1, 2, 3, 4 or 5");
      }
    } while (choice < 1 || choice > 5);

    //Processing the choice
    switch (choice) {
      case 1:
        ATMMachine.showTransactionHistory(currentClient, scanner);
        break;

      case 2:
        ATMMachine.withdrawFunds(currentClient, scanner);
        break;

      case 3:
        ATMMachine.depositFunds(currentClient, scanner);
        break;

      case 4:
        ATMMachine.transferFunds(currentClient, scanner);
        break;
    }

    //In the end, we display the menu again (unless the client decides to quit
    //Let's use a recursive call
    if (choice != 5) {
      ATMMachine.printClientMenu(currentClient, scanner);
    }
  }

  public static void showTransactionHistory (Client currentClient, Scanner scanner) {
    //What account does the client want to look at?
    int theAcct;
    do {
      System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ", currentClient.numAccounts());
      theAcct = scanner.nextInt() - 1;
      if (theAcct > 0 || theAcct >= currentClient.numAccounts()) {
        System.out.println("Invalid account! Please try again.");
      }
    } while (theAcct > 0 || theAcct >= currentClient.numAccounts());

    //Once we get the account, let's print the transaction history
    currentClient.printAcctTransHistory(theAcct);

  }

  public static void transferFunds (Client currentClient, Scanner scanner) {
    int fromAcc;
    int toAcc;
    double amount;
    double accBalance;

    //Getting the account to transfer from
    do {
      System.out.print("Enter the number (1-%d) of the account to transfer from: ");
      fromAcc = scanner.nextInt() - 1;
      if (fromAcc < 0 || fromAcc >= currentClient.numAccounts()) {
        System.out.println("Invalid account! Please try again.");
      }
    } while (fromAcc < 0 || fromAcc >= currentClient.numAccounts());
    accBalance = currentClient.getAccountBalance(fromAcc);

    //Getting the account to transfer to
    do {
      System.out.print("Enter the number (1-%d) of the account to transfer to: ");
      toAcc = scanner.nextInt() - 1;
      if (toAcc < 0 || toAcc >= currentClient.numAccounts()) {
        System.out.println("Invalid account! Please try again.");
      }
    } while (toAcc < 0 || toAcc >= currentClient.numAccounts());

    //Getting the amount to be transferred
    do {
      System.out.printf("Enter the amount to transfer (Max. available: $%.02f): $", accBalance);
      amount = scanner.nextDouble();
      if (amount < 0 ) {
        System.out.println("Amount must be greater than zero.");
      } else if (amount > accBalance) {
        System.out.printf("Amount must not be greater than balance of $%.02f.\n", accBalance);
      }
    } while (amount < 0 || amount > accBalance);

    //Finally, let's do the transfer
    currentClient.addAccountTransaction(fromAcc, amount * (-1), String.format("Transfer to account %s", currentClient.getAcctUUID(toAcc)));
    currentClient.addAccountTransaction(toAcc, amount, String.format("Tranfer to account %s", currentClient.getAcctUUID(fromAcc)));

  }

  public static void withdrawFunds (Client currentClient, Scanner scanner) {
    int fromAcc;
    double amount;
    double accBalance;
    String memo;

    //Getting the account to transfer from
    do {
      System.out.print("Enter the number (1-%d) of the account to transfer from: ");
      fromAcc = scanner.nextInt() - 1;
      if (fromAcc < 0 || fromAcc >= currentClient.numAccounts()) {
        System.out.println("Invalid account! Please try again.");
      }
    } while (fromAcc < 0 || fromAcc >= currentClient.numAccounts());
    accBalance = currentClient.getAccountBalance(fromAcc);

    //Getting the amount to be transferred
    do {
      System.out.printf("Enter the amount to transfer (Max. available: $%.02f): $", accBalance);
      amount = scanner.nextDouble();
      if (amount < 0 ) {
        System.out.println("Amount must be greater than zero.");
      } else if (amount > accBalance) {
        System.out.printf("Amount must not be greater than balance of $%.02f.\n", accBalance);
      }
    } while (amount < 0 || amount > accBalance);

    scanner.nextLine();

    //Memo
    System.out.println("Enter a memo: ");
    memo = scanner.nextLine();

    //Now we do the withdraw
    currentClient.addAccountTransaction(fromAcc, accBalance * (-1), memo);
  }

  public static void depositFunds (Client currentClient, Scanner scanner) {
    int toAcc;
    double amount;
    double accBalance;
    String memo;

    //Getting the account to transfer from
    do {
      System.out.print("Enter the number (1-%d) of the account to transfer from: ");
      toAcc = scanner.nextInt() - 1;
      if (toAcc < 0 || toAcc >= currentClient.numAccounts()) {
        System.out.println("Invalid account! Please try again.");
      }
    } while (toAcc < 0 || toAcc >= currentClient.numAccounts());
    accBalance = currentClient.getAccountBalance(toAcc);

    //Getting the amount to be transferred
    do {
      System.out.printf("Enter the amount to transfer (Max. available: $%.02f): $", accBalance);
      amount = scanner.nextDouble();
      if (amount < 0 ) {
        System.out.println("Amount must be greater than zero.");
      } else if (amount > accBalance) {
        System.out.printf("Amount must not be greater than balance of $%.02f.\n", accBalance);
      }
    } while (amount < 0 || amount > accBalance);

    scanner.nextLine();

    //Memo
    System.out.println("Enter a memo: ");
    memo = scanner.nextLine();

    //Now we do the withdraw
    currentClient.addAccountTransaction(toAcc, accBalance, memo);
  }
}
