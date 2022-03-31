package musicraze.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import musicraze.model.Artists;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistsDao {
  protected ConnectionManager connectionManager;
  private static ArtistsDao instance = null;

  private static final String INSERT = "INSERT INTO Artists(ArtistName, ArtistSpotifyId, ArtistCountry, ArtistRecordLabel) VALUES(?,?,?,?);";
  private static final String GETBYARTISTID = "SELECT * FROM Artists WHERE ArtistId = ?;";
  private static final String GETBYARTISTNAME = "SELECT * FROM Artists WHERE ArtistName = ?;";
  private static final String DELETE = "DELETE FROM Artists WHERE ArtistId = ?;";
  private static final String UPDATE_ARTISTNAME = "UPDATE Artists SET ArtistName = ? WHERE ArtistId = ?;";
  private static final String UPDATE_ARTISTCOUNTRY = "UPDATE Artists SET ArtistCountry = ? WHERE ArtistId = ?;";
  private static final String UPDATE_ARTISTRECORDLABEL= "UPDATE Artists SET ArtistRecordLabel = ? WHERE ArtistId = ?;";
  private static final String UPDATE_ARTISTSPOTIFYID= "UPDATE Artists SET ArtistSpotifyId = ? WHERE ArtistId = ?;";



  protected ArtistsDao() {
    this.connectionManager = new ConnectionManager();
  }

  public static ArtistsDao getInstance() {
    if (instance == null) {
      instance = new ArtistsDao();
    }
    return instance;
  }

  public Artists create(Artists artists) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, artists.getArtistName());
      insertStmt.setString(2, artists.getArtistSpotifyId());
      insertStmt.setString(3, artists.getArtistCountry());
      insertStmt.setString(4, artists.getArtistRecordLabel());

      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();

      int artistId = -1;
      if (resultKey.next()) {
        artistId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key");
      }

      artists.setArtistId(artistId);
      return artists;
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
      if (resultKey != null) {
        resultKey.close();
      }
    }
  }

  public Artists getArtistById(Integer artistId) throws SQLException {
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(GETBYARTISTID);
      selectStmt.setInt(1, artistId);
      results = selectStmt.executeQuery();
      if (results.next()) {
        Integer artistid = results.getInt("ArtistId");
        String artistName = results.getString("ArtistName");
        String artistSpotifyId = results.getString("ArtistSpotifyId");
        String artistCountry = results.getString("ArtistCountry");
        String artistRecordLabel = results.getString("ArtistRecordLabel");

        return new Artists(artistid, artistName, artistSpotifyId, artistCountry, artistRecordLabel);
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
  
  
  public List<Artists> getArtistByName(String artistName) throws SQLException {
	  	List<Artists> artists = new ArrayList<Artists>();
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	      connection = this.connectionManager.getConnection();
	      selectStmt = connection.prepareStatement(GETBYARTISTNAME);
	      selectStmt.setString(1, artistName);
	      results = selectStmt.executeQuery();
	      while (results.next()) {
	        Integer artistid = results.getInt("ArtistId");
	        String artistname = results.getString("ArtistName");
	        String artistSpotifyId = results.getString("ArtistSpotifyId");
	        String artistCountry = results.getString("ArtistCountry");
	        String artistRecordLabel = results.getString("ArtistRecordLabel");
	        	
	        Artists artist = new Artists(artistid, artistname, artistSpotifyId, artistCountry, artistRecordLabel);
	        artists.add(artist);
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
	    return artists;
	  }
  

  public Artists updateArtistName(Artists artists, String artistName) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(UPDATE_ARTISTNAME);
      insertStmt.setString(1, artistName);
      insertStmt.setInt(2, artists.getArtistId());
      insertStmt.executeUpdate();
      artists.setArtistName(artistName);
      return artists;
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

  public Artists updateArtistSpotifyId(Artists artists, String spotifyId) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(UPDATE_ARTISTSPOTIFYID);
      insertStmt.setString(1, spotifyId);
      insertStmt.setInt(2, artists.getArtistId());
      insertStmt.executeUpdate();
      artists.setArtistSpotifyId(spotifyId);
      ;
      return artists;
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


  public Artists updateArtistCountry(Artists artists, String country) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(UPDATE_ARTISTCOUNTRY);
      insertStmt.setString(1, country);
      insertStmt.setInt(2, artists.getArtistId());
      insertStmt.executeUpdate();
      artists.setArtistCountry(country);
      return artists;
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

  public Artists updateArtistRecordLabel(Artists artists, String artistRecordLabel) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(UPDATE_ARTISTRECORDLABEL);
      insertStmt.setString(1, artistRecordLabel);
      insertStmt.setInt(2, artists.getArtistId());
      insertStmt.executeUpdate();
      artists.setArtistRecordLabel(artistRecordLabel);
      ;
      return artists;
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

  public Artists delete(Artists artists) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(DELETE);
      insertStmt.setInt(1, artists.getArtistId());
      insertStmt.executeUpdate();
      return null;
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
}
