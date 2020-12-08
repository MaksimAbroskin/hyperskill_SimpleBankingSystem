package banking;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String fileName = CmdLineArgsParse.parseArgs(args);
        String fileNameTest = "db1.s3db";
        SqlDatabaseHandler sqlDatabaseHandler = new SqlDatabaseHandler(fileNameTest);
        sqlDatabaseHandler.createDatabase();
        outloop:
        while (true) {
//            System.out.println(cardsDatabase);
            System.out.println("\n1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");

            int command;
            try {
                command = scanner.nextInt();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                System.out.println("Incorrect command - InputMismatchException");
                continue;
            } catch (NoSuchElementException e) {
                System.out.println("NoSuchElementException");
                break;
            }

            switch (command) {
                case 0:
                    System.out.println("Bye!");
                    break outloop;
                case 1:
                    Card newCard = AccountHandler.createAccount();
                    if (newCard != null) {
                        sqlDatabaseHandler.addNewCard(newCard);
                    } else {
                        System.out.println("Couldn't create new account");
                    }
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String number = scanner.next();
                    System.out.println("Enter your PIN:");
                    String pin = scanner.next();
                    Card card = sqlDatabaseHandler.login(number, pin);
                    if (card != null) {
                        InAccount inAccount = new InAccount(scanner, card, sqlDatabaseHandler);
                        inAccount.accountHandler();
                    }
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}
