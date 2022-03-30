package musicraze.dal;

import musicraze.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorsDao extends PersonsDao {

  private static final String INSERT = "INSERT INTO Administrators(UserName) VALUES(?);";
  private static final String SELECT = "SELECT * FROM Administrators WHERE UserName=?;";
  private static final String DELETE = "DELETE FROM Administrators WHERE UserName=?;";
  private static AdministratorsDao instance = null;

  private AdministratorsDao() {
    super();
  }

  public static AdministratorsDao getInstance() {
    if (instance == null) {
      instance = new AdministratorsDao();
    }
    return instance;
  }

  public Administrators create(Administrators administrator) throws SQLException {
    Persons person = super.create(administrator);
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = super.connectionManager.getConnection();
      insertStmt = connection.prepareStatement(INSERT);
      insertStmt.setString(1, person.getUserName());
      insertStmt.executeUpdate();
      return administrator;
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

  public Administrators getAdministratorByUserName(String userName) throws SQLException {
    Persons person = super.getPersonByUserName(userName);
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = super.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(SELECT);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      if (results.next()) {
        return new Administrators(person);
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

  public List<Administrators> getAdministratorsByFirstName(String firstName) throws SQLException {
    List<Persons> persons = super.getPersonsByFirstName(firstName);
    List<Administrators> administrators = new ArrayList<>();
    for (Persons person : persons) {
      administrators.add(this.getAdministratorByUserName(person.getUserName()));
    }
    return administrators;
  }

  public Administrators delete(Administrators administrator) throws SQLException {
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = super.connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(DELETE);
      deleteStmt.setString(1, administrator.getUserName());
      int numRecordsAffected = deleteStmt.executeUpdate();
      if (numRecordsAffected == 0) {
        throw new SQLException("Unable to delete administrator.");
      }
      super.delete(administrator);
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

  public Administrators updatePassword(Administrators administrator, String newPassword)
      throws SQLException {
    super.updatePassword(administrator, newPassword);
    administrator.setPassword(newPassword);
    return administrator;
  }

  public Administrators updateFirstName(Administrators administrator, String newFirstName)
      throws SQLException {
    super.updateFirstName(administrator, newFirstName);
    administrator.setFirstName(newFirstName);
    return administrator;
  }

  public Administrators updateLastName(Administrators administrator, String newLastName)
      throws SQLException {
    super.updateLastName(administrator, newLastName);
    administrator.setLastName(newLastName);
    return administrator;
  }

  public Administrators updateEmail(Administrators administrator, String newEmail)
      throws SQLException {
    super.updateEmail(administrator, newEmail);
    administrator.setEmail(newEmail);
    return administrator;
  }
}
