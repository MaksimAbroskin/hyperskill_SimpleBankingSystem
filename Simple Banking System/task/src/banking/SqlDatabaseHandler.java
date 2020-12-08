package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SqlDatabaseHandler {
    String fileName;
    SQLiteDataSource dataSource;
    static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS card(\n" +
            "id INTEGER,\n" +
            "number TEXT,\n" +
            "pin TEXT,\n" +
            "balance INTEGER DEFAULT 0\n" +
            ");";
    static String QUERY_INSERT = "INSERT INTO card(id, number, pin) VALUES (?, ?, ?)";
    static String QUERY_SELECT = "SELECT * FROM card";
    static String QUERY_LOGIN = "SELECT * FROM card WHERE (number = ?) AND (pin = ?)";
    static String QUERY_GET_BALANCE = "SELECT balance FROM card WHERE (number = ?) AND (pin = ?)";

    public SqlDatabaseHandler(String fileName) {
        this.fileName = fileName;
        String url = "jdbc:sqlite:" + fileName;
        this.dataSource = new SQLiteDataSource();
        this.dataSource.setUrl(url);
        this.dataSource.setEncoding("UTF-8");
    }

    public void createDatabase() {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate(QUERY_CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewCard(Card card) {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(QUERY_INSERT)) {
                preparedStatement.setInt(1, card.getId());
                preparedStatement.setString(2, card.getNumber());
                preparedStatement.setString(3, card.getPin());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Card login(String number, String pin) {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(QUERY_LOGIN)) {
                preparedStatement.setString(1, number);
                preparedStatement.setString(2, pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("You have successfully logged in!");
                    return new Card(resultSet.getInt("id"),
                            resultSet.getString("number"),
                            resultSet.getString("pin"),
                            resultSet.getInt("balance"));
                } else {
                    System.out.println("Wrong card number or PIN!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getCard() {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                ResultSet resultSet = statement.executeQuery(QUERY_SELECT);
                while (resultSet.next()) {
                    System.out.println("id = " + resultSet.getInt("id"));
                    System.out.println("number = " + resultSet.getString("number"));
                    System.out.println("pin = " + resultSet.getString("pin"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
