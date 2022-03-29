package musicraze.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

  private static final String USER = "test";
  private static final String PASSWORD = "test";
  private static final String HOST_NAME = "localhost";
  private static final int PORT = 3306;
  private static final String SCHEMA = "MusiCraze";
  private static final String TIMEZONE = "UTC";

  public Connection getConnection() throws SQLException {

    Connection connection = null;
    
    try {
      Properties connectionProperties = new Properties();
      connectionProperties.put("user", USER);
      connectionProperties.put("password", PASSWORD);
      connectionProperties.put("serverTimezone", TIMEZONE);
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new SQLException(e);
      }
      connection = DriverManager.getConnection(
          "jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + SCHEMA + "?useSSL=false",
          connectionProperties);
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return connection;
  }

  public void closeConnection(Connection connection) throws SQLException {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
