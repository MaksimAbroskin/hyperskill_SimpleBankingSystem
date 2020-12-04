package banking;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Card> cardsDatabase = new ArrayList<>();

    public static void main(String[] args) {
        outloop:
        while (true) {
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
                        cardsDatabase.add(newCard);
                    } else {
                        System.out.println("Couldn't create new account");
                    }
                    System.out.println("cardsDatabase" + Arrays.toString(new ArrayList[]{cardsDatabase}));
                    break;
                case 2:
                    AccountHandler accountHandler = new AccountHandler(cardsDatabase, scanner);
                    accountHandler.loginAccount();
            }
        }
    }

    static Card createAccount(String bin) {
        Card newCard = new Card();
        System.out.println("\nYour card has been created\n" +
                "Your card number:\n" + newCard.getNumber());
        System.out.println("Your card PIN:\n" + newCard.getPin());
        return newCard;
    }

    static Card loginAccount() {
        System.out.println("Enter your card number:");
        String userCardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        int userPin = scanner.nextInt();

        for (Card card : cardsDatabase) {
            if (card.getNumber().equals(userCardNumber)) {
                if (card.getPin().equals(userPin)) {
                    System.out.println("You have successfully logged in!");
                    return card;
                } else {
                    break;
                }
            }
        }
        System.out.println("Wrong card number or PIN!");
        return null;
    }
}
