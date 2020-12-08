package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    void accountHandler() {
        while (true) {
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");

            int commandInAccount;
            try {
                commandInAccount = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Incorrect command");
                continue;
            }

            switch (commandInAccount) {
                case EXIT:
                    return;
                case GET_BALANCE:
                    getBalance();
                    break;
                case ADD_INCOME:

                    break;
                case DO_TRANSFER:

                    break;
                case CLOSE_ACCOUNT:

                    break;
                case LOG_OUT:

                    break;
            }

        }
    }

    void getBalance() {
        String QUERY = "SELECT balance FROM card WHERE (number = ?) AND (pin = ?)";

        try (Connection con = sqlDatabaseHandler.dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(QUERY)) {
                statement.setString(1, thisCard.getNumber());
                statement.setString(2, thisCard.getPin());
                System.out.println("Balance: " + statement.executeQuery().getInt("balance"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}