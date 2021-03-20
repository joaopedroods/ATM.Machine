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
        ATMMachine.withdrawlFunds(currentClient, scanner);
        break;

      case 3:
        ATMMachine.depositFunds(currentClient, scanner);
        break;

      case 4:
        ATMMachine.transferFunds(currentClient, scanner);
        break;
    }

    //In the end, we display the menu again (unless the cliente decides to quit
    //Let's use a recursive call
    if (choice != 5) {
      ATMMachine.printClientMenu(currentClient, scanner);
    }
  }















}
