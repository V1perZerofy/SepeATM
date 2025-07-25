import essentials.*;
import utils.*;

import java.io.IOException;
import java.util.*;


public class Main{
    public static void main(String[] args) {
        SerializationUtil serializationUtil = SerializationUtil.getInstance();
        try {
            Bank bank = serializationUtil.loadBank("bank_data.json");
            System.out.println("Bank data loaded successfully.");
            runATM(bank);
        } catch (IOException e) {
            System.out.println("No existing bank data found. Starting a new bank instance.");
        }
        runATM(new Bank("MyBank"));
    }

    public static void runATM(Bank bank) {
        SerializationUtil serializationUtil = SerializationUtil.getInstance();
        while (true) {
            System.out.println("Welcome to the ATM!");
            System.out.println("# Please select an option:");
            System.out.println("1. Create Account");
            System.out.println("2. Access Account");
            System.out.println("3. Exit");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            int choice;
            try{
                choice = Integer.parseInt(input);
            } catch ( NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Creating a new account...");
                    System.out.print("Enter your name: ");
                    String name = scanner.next();
                    System.out.print("Enter your address: ");
                    String address = scanner.next();
                    System.out.print("Enter your phone number: ");
                    String phoneNumber = scanner.next();

                    Customer customer = new Customer(name, address, phoneNumber);
                    Random random = new Random();
                    String accountNumber = "ACCT" + (10000 + random.nextInt(90000)); // Generate a random account number
                    Account newAccount = new Account(accountNumber, customer);
                    bank.addAccount(newAccount);
                    System.out.println("Account created successfully! Account Number: " + accountNumber);
                    try {
                        serializationUtil.saveBank(bank, "bank_data.json");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    System.out.println("Please enter your account number:");
                    String inputAccountNumber = scanner.next();
                    Account account = bank.findAccountByNumber(inputAccountNumber);
                    if (account != null) {
                        while (true) {
                            System.out.println("Welcome " + account.getOwner().getName() + "!");
                            System.out.println("Balance: " + account.getBalance());
                            System.out.println("# Please select an option:");
                            System.out.println("1. Deposit Money");
                            System.out.println("2. Withdraw Money");
                            System.out.println("3. Transfer Money");
                            System.out.println("4. Check Balance");
                            System.out.println("5. Exit");

                            int accountChoice = scanner.nextInt();
                            switch (accountChoice) {
                                case 1:
                                    System.out.print("Enter amount to deposit: ");
                                    double depositAmount = scanner.nextDouble();
                                    account.deposit(depositAmount);
                                    System.out.println("Deposited: " + depositAmount);
                                    try {
                                        serializationUtil.saveBank(bank, "bank_data.json");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                case 2:
                                    System.out.print("Enter amount to withdraw: ");
                                    double withdrawAmount = scanner.nextDouble();
                                    if (account.withdraw(withdrawAmount)) {
                                        System.out.println("Withdrawn: " + withdrawAmount);
                                    } else {
                                        System.out.println("Insufficient funds.");
                                    }
                                    try {
                                        serializationUtil.saveBank(bank, "bank_data.json");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                case 3:
                                    System.out.print("Enter target account number for transfer: ");
                                    String targetAccountNumber = scanner.next();
                                    Account targetAccount = bank.findAccountByNumber(targetAccountNumber);
                                    if (targetAccount != null) {
                                        System.out.print("Enter amount to transfer: ");
                                        double transferAmount = scanner.nextDouble();
                                        account.transfer(targetAccount, transferAmount);
                                        System.out.println("Transferred: " + transferAmount + " to " + targetAccount.getOwner().getName());
                                    } else {
                                        System.out.println("Target account not found.");
                                    }
                                    try {
                                        serializationUtil.saveBank(bank, "bank_data.json");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                case 4:
                                    System.out.println("Current Balance: " + account.getBalance());
                                    try {
                                        serializationUtil.saveBank(bank, "bank_data.json");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                case 5:
                                    System.out.println("Exiting account access.");
                                    try {
                                        serializationUtil.saveBank(bank, "bank_data.json");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                            if (accountChoice == 5) {
                                break; // Exit the inner loop to return to the main menu
                            }
                        }
                    } else {
                        System.out.println("Account not found. Please try again.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting the ATM. Thank you for using our services!");
                    scanner.close();
                    try {
                        serializationUtil.saveBank(bank, "bank_data.json");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Bank data saved successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}