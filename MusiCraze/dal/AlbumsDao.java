package musicraze.dal;

import musicraze.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlbumsDao {
	  private static final String INSERT =
		      "INSERT INTO Albums(AlbumId, AlbumSpotifyId, Name, Year, ReleaseDate, Duration) VALUES(?,?,?,?,?,?);";
	  private static final String DELETE = "DELETE FROM Albums WHERE Albums.Name=?;";
	  private static AlbumsDao instance = null;
	  protected ConnectionManager connectionManager;

	  protected AlbumsDao() {
	    this.connectionManager = new ConnectionManager();
	  }

	  public static AlbumsDao getInstance() {
	    if (instance == null) {
	      instance = new AlbumsDao();
	    }
	    return instance;
	  }

	  public Albums create(Albums album) throws SQLException {
	    Connection connection = null;
	    PreparedStatement insertStmt = null;
	    try {
	      connection = this.connectionManager.getConnection();
	      insertStmt = connection.prepareStatement(INSERT);
	      insertStmt.setInt(1, album.getAlbumId());
	      insertStmt.setString(2, album.getAlbumSpotifyId());
	      insertStmt.setString(3, album.getName());
	      insertStmt.setInt(4, album.getYear());
	      insertStmt.setDate(5, new java.sql.Date(album.getReleaseDate().getTime()));
	      insertStmt.setInt(6, album.getDuration());
	      insertStmt.executeUpdate();
	      return album;
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
	  
	  
	  public Albums delete(Albums album) throws SQLException {
		    Connection connection = null;
		    PreparedStatement deleteStmt = null;
		    try {
		      connection = this.connectionManager.getConnection();
		      deleteStmt = connection.prepareStatement(DELETE);
		      deleteStmt.setString(1, album.getName());
		      int numRecordsAffected = deleteStmt.executeUpdate();
		      if (numRecordsAffected == 0) {
		        throw new SQLException("Unable to delete album.");
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
		  

	  
	  public List<Albums> getAlbumsFromAlbumName(String albumName)
				throws SQLException {
			List<Albums> albums = new ArrayList<Albums>();
			String selectAlbums =
				"SELECT * FROM Albums WHERE Albums.Name=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectAlbums);
				selectStmt.setString(1, albumName);
				results = selectStmt.executeQuery();
				while(results.next()) {
					int resultAlbumId = results.getInt("AlbumId");
					String resultAlbumName = results.getString("Name");
					String resultAlbumSpotifyId = results.getString("AlbumSpotifyId");
					int resultYear = results.getInt("Year");
					Date resultReleaseDate = new Date(results.getTimestamp("ReleaseDate").getTime());
					int duration = results.getInt("Duration");
		        	Albums album = new Albums(resultAlbumId, resultAlbumSpotifyId, resultAlbumName, resultYear,
						      resultReleaseDate, duration);
					albums.add(album);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(selectStmt != null) {
					selectStmt.close();
				}
				if(results != null) {
					results.close();
				}
			}
			return albums;
		}
	  
	  public List<Albums> getTopTenLikedAlbums()
				throws SQLException {
			List<Albums> albums = new ArrayList<Albums>();
			String selectAlbums =
				"SELECT Albums.Name, SUM(LIKES_PER_SONG.CNT) AS LIKES_PER_ALBUM"
				+ " FROM Albums INNER JOIN ("
				+ "   SELECT Songs.SongId, Songs.AlbumId, COUNT(*) AS CNT"
				+ "      FROM Songs"
				+ "          INNER JOIN Likes"
				+ "          On Songs.SongId = Likes.SongId"
				+ "      GROUP BY Songs.SongId"
				+ "      ORDER BY CNT DESC"
				+ "    ) AS LIKES_PER_SONG"
				+ "  ON Albums.AlbumId = LIKES_PER_SONG.AlbumId"
				+ "  GROUP BY Albums.AlbumId, Albums.Name"
				+ "  ORDER BY LIKES_PER_ALBUM DESC"
				+ "LIMIT 10;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectAlbums);
				results = selectStmt.executeQuery();
				while(results.next()) {
					int resultAlbumId = results.getInt("AlbumId");
					String resultAlbumName = results.getString("Name");
					String resultAlbumSpotifyId = results.getString("AlbumSpotifyId");
					int resultYear = results.getInt("Year");
					Date resultReleaseDate = new Date(results.getTimestamp("ReleaseDate").getTime());
					int duration = results.getInt("Duration");
		        	Albums album = new Albums(resultAlbumId, resultAlbumName, resultAlbumSpotifyId, resultYear,
						      resultReleaseDate, duration);
					albums.add(album);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(selectStmt != null) {
					selectStmt.close();
				}
				if(results != null) {
					results.close();
				}
			}
			return albums;
		}


	
}