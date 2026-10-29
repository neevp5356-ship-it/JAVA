import java.util.Scanner;
import model.*;
import service.BankService;
import exception.BankException;
import util.Validator;

public class MiniBank {
    public static void main(String[] args) {
        BankInfo header = new BankInfo("MiniBank Central", "Ahmedabad Branch - Est. 2026");
        System.out.println("==================================================");
        System.out.println(" " + header.name() + " | " + header.branch());
        System.out.println(" Developed by: PATEL NEEV MEHULKUMAR (25AIML055)");
        System.out.println("==================================================");

        BankService bankService = new BankService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- MINI BANK MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Statement");
            System.out.println("6. List All Accounts");
            System.out.println("7. Generate Daily Report");
            System.out.println("8. Exit");
            System.out.print("Choose an option (1-8): ");

            String choiceStr = scanner.nextLine().trim();
            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 8.");
                continue;
            }

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.STATEMENT;
                case 6 -> MenuOption.LIST_ACCOUNTS;
                case 7 -> MenuOption.REPORT;
                case 8 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid menu option number. Please re-enter.");
                continue;
            }

            try {
                switch (option) {
                    case OPEN_ACCOUNT -> {
                        System.out.print("Enter Account Type (Savings / Current / FD): ");
                        String type = scanner.nextLine().trim();
                        System.out.print("Enter Owner Name: ");
                        String name = scanner.nextLine().trim();
                        System.out.print("Enter Opening Balance (₹): ");
                        long bal = Long.parseLong(scanner.nextLine().trim());
                        long extra = 0;
                        if (type.equalsIgnoreCase("Savings")) {
                            System.out.print("Enter Min Balance Limit: ");
                            extra = Long.parseLong(scanner.nextLine().trim());
                        } else if (type.equalsIgnoreCase("Current")) {
                            System.out.print("Enter Overdraft Limit: ");
                            extra = Long.parseLong(scanner.nextLine().trim());
                        } else if (type.equalsIgnoreCase("FD")) {
                            System.out.print("Is Matured? (1 for true, 0 for false): ");
                            extra = Long.parseLong(scanner.nextLine().trim());
                        }
                        Account acc = bankService.openAccount(type, name, bal, extra);
                        System.out.println("Account successfully opened! Details: " + acc);
                    }
                    case DEPOSIT -> {
                        System.out.print("Enter Account Number: ");
                        String accNum = scanner.nextLine().trim();
                        System.out.print("Enter Amount to Deposit (₹): ");
                        long amt = Long.parseLong(scanner.nextLine().trim());
                        bankService.deposit(accNum, amt);
                        System.out.println("Deposit successful!");
                    }
                    case WITHDRAW -> {
                        System.out.print("Enter Account Number: ");
                        String accNum = scanner.nextLine().trim();
                        System.out.print("Enter Amount to Withdraw (₹): ");
                        long amt = Long.parseLong(scanner.nextLine().trim());
                        bankService.withdraw(accNum, amt);
                        System.out.println("Withdrawal successful!");
                    }
                    case TRANSFER -> {
                        System.out.print("Enter Source Account Number: ");
                        String from = scanner.nextLine().trim();
                        System.out.print("Enter Destination Account Number: ");
                        String to = scanner.nextLine().trim();
                        System.out.print("Enter Amount to Transfer (₹): ");
                        long amt = Long.parseLong(scanner.nextLine().trim());
                        bankService.transfer(from, to, amt);
                        System.out.println("Transfer successful!");
                    }
                    case STATEMENT -> {
                        System.out.print("Enter Account Number: ");
                        String accNum = scanner.nextLine().trim();
                        System.out.println(bankService.getStatement(accNum));
                    }
                    case LIST_ACCOUNTS -> {
                        System.out.println("\n--- ALL BANK ACCOUNTS ---");
                        var list = bankService.listAccounts();
                        if (list.isEmpty()) {
                            System.out.println("No accounts found.");
                        } else {
                            for (Account a : list) {
                                System.out.println(a);
                            }
                        }
                    }
                    case REPORT -> {
                        System.out.println(bankService.generateReport());
                    }
                    case EXIT -> {
                        bankService.saveState();
                        System.out.println("Thank you for using MiniBank. Goodbye!");
                        running = false;
                    }
                }
            } catch (BankException e) {
                System.out.println("[Banking Error] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[System Error] " + e.getMessage());
            }
        }
        scanner.close();
    }
}
