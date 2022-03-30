package musicraze.dal;

import musicraze.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonsDao {

  private static final String INSERT =
      "INSERT INTO Persons(UserName,Password,FirstName,LastName,Email) VALUES(?,?,?,?,?);";
  private static final String SELECT_USERNAME = "SELECT * FROM Persons WHERE UserName=?;";
  private static final String SELECT_FIRSTNAME = "SELECT * FROM Persons WHERE FirstName=?;";
  private static final String DELETE = "DELETE FROM Persons WHERE UserName=?;";
  private static final String UPDATE_PASSWORD = "UPDATE Persons SET Password=? WHERE UserName=?;";
  private static final String UPDATE_FIRSTNAME = "UPDATE Persons SET FirstName=? WHERE UserName=?;";
  private static final String UPDATE_LASTNAME = "UPDATE Persons SET LastName=? WHERE UserName=?;";
  private static final String UPDATE_EMAIL = "UPDATE Persons SET Email=? WHERE UserName=?;";
  private static PersonsDao instance = null;
  protected ConnectionManager connectionManager;

  protected PersonsDao() {
    this.connectionManager = new ConnectionManager();
  }

  public static PersonsDao getInstance() {
    if (instance == null) {
      instance = new PersonsDao();
    }
    return instance;
  }

  public Persons create(Persons person) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = this.connectionManager.getConnection();
      insertStmt = connection.prepareStatement(INSERT);
      insertStmt.setString(1, person.getUserName());
      insertStmt.setString(2, person.getPassword());
      insertStmt.setString(3, person.getFirstName());
      insertStmt.setString(4, person.getLastName());
      insertStmt.setString(5, person.getEmail());
      insertStmt.executeUpdate();
      return person;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (insertStmt != null) {
        insertStmt.close();
      }
    }
  }

  public Persons getPersonByUserName(String userName) throws SQLException {
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(SELECT_USERNAME);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      if (results.next()) {
        String password = results.getString("Password");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String email = results.getString("Email");
        return new Persons(userName, password, firstName, lastName, email);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return null;
  }

  public List<Persons> getPersonsByFirstName(String firstName) throws SQLException {
    List<Persons> persons = new ArrayList<>();
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(SELECT_FIRSTNAME);
      selectStmt.setString(1, firstName);
      results = selectStmt.executeQuery();
      while (results.next()) {
        String userName = results.getString("UserName");
        String password = results.getString("Password");
        String lastName = results.getString("LastName");
        String email = results.getString("Email");
        persons.add(new Persons(userName, password, firstName, lastName, email));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return persons;
  }

  public Persons delete(Persons person) throws SQLException {
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = this.connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(DELETE);
      deleteStmt.setString(1, person.getUserName());
      int numRecordsAffected = deleteStmt.executeUpdate();
      if (numRecordsAffected == 0) {
        throw new SQLException("Unable to delete person.");
      }
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }

  public Persons updatePassword(Persons person, String newPassword) throws SQLException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = this.connectionManager.getConnection();
      updateStmt = connection.prepareStatement(UPDATE_PASSWORD);
      updateStmt.setString(1, newPassword);
      updateStmt.setString(2, person.getUserName());
      updateStmt.executeUpdate();
      person.setPassword(newPassword);
      return person;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  public Persons updateFirstName(Persons person, String newFirstName) throws SQLException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = this.connectionManager.getConnection();
      updateStmt = connection.prepareStatement(UPDATE_FIRSTNAME);
      updateStmt.setString(1, newFirstName);
      updateStmt.setString(2, person.getUserName());
      updateStmt.executeUpdate();
      person.setFirstName(newFirstName);
      return person;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  public Persons updateLastName(Persons person, String newLastName) throws SQLException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = this.connectionManager.getConnection();
      updateStmt = connection.prepareStatement(UPDATE_LASTNAME);
      updateStmt.setString(1, newLastName);
      updateStmt.setString(2, person.getUserName());
      updateStmt.executeUpdate();
      person.setLastName(newLastName);
      return person;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  public Persons updateEmail(Persons person, String newEmail) throws SQLException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = this.connectionManager.getConnection();
      updateStmt = connection.prepareStatement(UPDATE_EMAIL);
      updateStmt.setString(1, newEmail);
      updateStmt.setString(2, person.getUserName());
      updateStmt.executeUpdate();
      person.setEmail(newEmail);
      return person;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }
}
