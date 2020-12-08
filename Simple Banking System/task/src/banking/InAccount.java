package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InAccount {
    static final int GET_BALANCE = 1;
    static final int ADD_INCOME = 2;
    static final int DO_TRANSFER = 3;
    static final int CLOSE_ACCOUNT = 4;
    static final int LOG_OUT = 5;
    static final int EXIT = 0;
    Scanner scanner;
    Card thisCard;
    SqlDatabaseHandler sqlDatabaseHandler;

    public InAccount(Scanner scanner, Card thisCard, SqlDatabaseHandler sqlDatabaseHandler) {
        this.scanner = scanner;
        this.thisCard = thisCard;
        this.sqlDatabaseHandler = sqlDatabaseHandler;
    }

    boolean accountHandler() {
        while (true) {
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");

            Integer commandInAccount = readInt(scanner, "", "Incorrect command");
            if (commandInAccount == null) {
                continue;
            }

            try (Connection con = sqlDatabaseHandler.dataSource.getConnection()) {
                switch (commandInAccount) {
                    case EXIT:
                        return true;

                    case GET_BALANCE:
                        System.out.println("Balance: " + getBalance(con, thisCard.getNumber()));
                        break;

                    case ADD_INCOME:
                        Integer incomeAmount = readInt(scanner, "Enter income:", "Incorrect amount! Enter integer number");
                        if (incomeAmount == null) {
                            break;
                        }
                        if (addIncome(con, thisCard.getNumber(), incomeAmount) != null) {
                            System.out.println("Income was added!");
                        }
                        break;

                    case DO_TRANSFER:
                        doTransfer(con);
                        break;

                    case CLOSE_ACCOUNT:
                        closeAccount(con, thisCard.getNumber());
                        break;

                    case LOG_OUT:
                        return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    int getBalance(Connection con, String cardNumber) {
        String QUERY = "SELECT balance FROM card WHERE (number = ?)";

        try (PreparedStatement statement = con.prepareStatement(QUERY)) {
            statement.setString(1, cardNumber);
            return statement.executeQuery().getInt("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    Integer addIncome(Connection con, String cardNumber, Integer incomeAmount) {
        String QUERY = "UPDATE card SET balance = ? WHERE (number = ?)";

        try (PreparedStatement statement = con.prepareStatement(QUERY)) {
            statement.setInt(1, getBalance(con, cardNumber) + incomeAmount);
            statement.setString(2, cardNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomeAmount;
    }

    void doTransfer(Connection con) {
        System.out.println("Transfer\n" +
                "Enter card number:");
        String targetCard = scanner.next();
        if (!Card.isCorrectCardNumber(targetCard)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            return;
        }

        String QUERY_IS_CARD_EXIST = "SELECT * FROM card WHERE (number = ?)";

        try (PreparedStatement statement = con.prepareStatement(QUERY_IS_CARD_EXIST)) {
            statement.setString(1, targetCard);
            if (!statement.executeQuery().next()) {
                System.out.println("Such a card does not exist.");
            } else {
                Integer moneyForTransfer = readInt(scanner, "Enter how much money you want to transfer:", "Incorrect amount! Enter integer number");
                if (moneyForTransfer == null) {
                    return;
                }
                if (getBalance(con, thisCard.getNumber()) >= moneyForTransfer) {
                    addIncome(con, targetCard, moneyForTransfer);
                    addIncome(con, thisCard.getNumber(), -moneyForTransfer);
                    System.out.println("Success!");
                } else {
                    System.out.println("Not enough money!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void closeAccount(Connection con, String cardNumber) {
        String QUERY = "DELETE FROM card WHERE (number = ?)";

        try (PreparedStatement statement = con.prepareStatement(QUERY)) {
            statement.setString(1, cardNumber);
            statement.executeUpdate();
            System.out.println("The account has been closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Integer readInt(Scanner scanner, String hello, String ifException) {
        try {
            System.out.println(hello);
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println(ifException);
        }
        return null;
    }

}
