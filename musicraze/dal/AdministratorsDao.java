package musicraze.dal;

import musicraze.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorsDao extends PersonsDao{

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
}
