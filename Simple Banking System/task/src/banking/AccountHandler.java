package banking;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountHandler {
    ArrayList<Card> cards;
    Scanner scanner;
    Card handledCard;

    public AccountHandler(ArrayList<Card> cards, Scanner scanner) {
        this.cards = cards;
        this.scanner = scanner;
    }

    static Card createAccount(String bin) {
        Card newCard = new Card(bin);
        if (newCard.number.matches("\\d{16}")) {
            System.out.println("\nYour card has been created\n" +
                    "Your card number:\n" + newCard.getNumber());
            System.out.println("Your card PIN:\n" + newCard.getPin());
            return newCard;
        } else {
            return null;
        }
    }

    void loginAccount() {
        System.out.println("Enter your card number:");
        String userCardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        int userPin = scanner.nextInt();

        for (Card card: cards) {
            if (card.getNumber().equals(userCardNumber)) {
                if (card.getPin().equals(userPin)) {
                    System.out.println("You have successfully logged in!");
                    handledCard = card;
                    inAccount(handledCard);
                    return;
                } else {
                    break;
                }
            }
        }
        System.out.println("Wrong card number or PIN!");
    }

    void inAccount(Card card) {
        while (true) {
            System.out.println("\n1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");

            int command;
            try {
                command = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Incorrect command");
                continue;
            }

            switch (command) {
                case 0:
                    return;
                case 1:
                    System.out.println("Balance: " + card.getBalance());
                    break;
                case 2:
                    System.out.println("You have successfully logged out!");
                    return;
            }

        }
    }

}
