package Supports;

import java.sql.*;

public class DataBase {
    private static final String url = "jdbc:mysql://localhost:3306/snake" +
            "?useSSL=false" +
            "&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "root";

    private static DataBase instance;

    private DataBase() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public static DataBase getInstance() throws ClassNotFoundException {
        if (instance == null)
            instance = new DataBase();
        return instance;
    }

    public ResultSet getAllRecords() {
        Connection connection;
        Statement statement;
        ResultSet resultSet;
//        ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from high_scores");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultSet;
    }

    public void insertNewRecord(String nickName, int score) {
        Connection connection;
        Statement statement;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            statement.executeUpdate("insert into snake.high_scores (nick_high_score, value_high_score) values ('" + nickName + "'," + score + ")");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Tvoya BD ne rabotaet, Lox");
        }

    }
}
