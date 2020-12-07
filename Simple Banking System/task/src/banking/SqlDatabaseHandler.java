package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SqlDatabaseHandler {
    String fileName;
    static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS card(\n" +
            "id INTEGER,\n" +
            "number TEXT,\n" +
            "pin TEXT,\n" +
            "balance INTEGER DEFAULT 0\n" +
            ");";
    static String QUERY_INSERT = "INSERT INTO card(id, number, pin) VALUES (?, ?, ?)";
    static String QUERY_SELECT = "SELECT * FROM card";
    static String QUERY_LOGIN = "SELECT number, pin FROM card";
    static String QUERY_GET_BALANCE = "SELECT balance FROM card WHERE (number = ?) AND (pin = ?)";

    public SqlDatabaseHandler(String fileName) {
        this.fileName = fileName;
    }

    public void createDatabase() {
        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        dataSource.setEncoding("UTF-8");

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
        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

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

    public void getCard(QueryType queryType) {
        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

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
