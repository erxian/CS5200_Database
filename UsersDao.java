package musicraze.dal;

import musicraze.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class UsersDao extends PersonsDao {

  private static final String INSERT =
      "INSERT INTO Users(UserName,Avatar,Bio,BornDate,JoinDate) VALUES(?,?,?,?,?);";
  private static final String SELECT = "SELECT * FROM Users WHERE UserName=?;";
  private static final String DELETE = "DELETE FROM Users WHERE UserName=?;";
  private static final String UPDATE_AVATAR = "UPDATE Users SET Avatar=? WHERE UserName=?;";
  private static final String UPDATE_BIO = "UPDATE Users SET Bio=? WHERE UserName=?;";
  private static final String UPDATE_BORNDATE = "UPDATE Users SET BornDate=? WHERE UserName=?;";
  private static UsersDao instance = null;

  private UsersDao() {
    super();
  }

  public static UsersDao getInstance() {
    if (instance == null) {
      instance = new UsersDao();
    }
    return instance;
  }

  public Users create(Users user) throws SQLException {
    Persons person = super.create(user);
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = super.connectionManager.getConnection();
      insertStmt = connection.prepareStatement(INSERT);
      insertStmt.setString(1, person.getUserName());
      insertStmt.setString(2, user.getAvatar());
      insertStmt.setString(3, user.getBio());
      insertStmt.setDate(4, new java.sql.Date(user.getBornDate().getTime()));
      insertStmt.setTimestamp(5,
          Timestamp.from(user.getJoinDate() == null ? Instant.now() : user.getJoinDate()));
      insertStmt.executeUpdate();
      return user;
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

  public Users getUserByUserName(String userName) throws SQLException {
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
        String avatar = results.getString("Avatar");
        String bio = results.getString("Bio");
        Date bornDate = new Date(results.getDate("BornDate").getTime());
        Instant joinDate = results.getTimestamp("JoinDate").toInstant();
        return new Users(person, avatar, bio, bornDate, joinDate);
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

  public Users delete(Users user) throws SQLException {
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = super.connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(DELETE);
      deleteStmt.setString(1, user.getUserName());
      int numRecordsAffected = deleteStmt.executeUpdate();
      if (numRecordsAffected == 0) {
        throw new SQLException("Unable to delete user.");
      }
      super.delete(user);
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

  public Users updatePassword(Users user, String newPassword) throws SQLException {
    super.updatePassword(user, newPassword);
    user.setPassword(newPassword);
    return user;
  }

  public Users updateFirstName(Users user, String newFirstName) throws SQLException {
    super.updateFirstName(user, newFirstName);
    user.setFirstName(newFirstName);
    return user;
  }

  public Users updateLastName(Users user, String newLastName) throws SQLException {
    super.updateLastName(user, newLastName);
    user.setLastName(newLastName);
    return user;
  }

  public Users updateEmail(Users user, String newEmail) throws SQLException {
    super.updateEmail(user, newEmail);
    user.setEmail(newEmail);
    return user;
  }

  public Users updateAvatar(Users user, String newAvatar) throws SQLException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = super.connectionManager.getConnection();
      updateStmt = connection.prepareStatement(UPDATE_AVATAR);
      updateStmt.setString(1, newAvatar);
      updateStmt.setString(2, user.getUserName());
      updateStmt.executeUpdate();
      user.setAvatar(newAvatar);
      return user;
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

  public Users updateBio(Users user, String newBio) throws SQLException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = super.connectionManager.getConnection();
      updateStmt = connection.prepareStatement(UPDATE_BIO);
      updateStmt.setString(1, newBio);
      updateStmt.setString(2, user.getUserName());
      updateStmt.executeUpdate();
      user.setBio(newBio);
      return user;
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

  public Users updateBornDate(Users user, Date newBornDate) throws SQLException {
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = super.connectionManager.getConnection();
      updateStmt = connection.prepareStatement(UPDATE_BORNDATE);
      updateStmt.setDate(1, new java.sql.Date(newBornDate.getTime()));
      updateStmt.setString(2, user.getUserName());
      updateStmt.executeUpdate();
      user.setBornDate(newBornDate);
      return user;
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
