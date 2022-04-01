package musicraze.dal;

import musicraze.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlbumsDao {
	  private static final String INSERT =
		      "INSERT INTO Albums(AlbumSpotifyId, Name, Year, ReleaseDate, Duration) VALUES(?,?,?,?,?);";
	  private static final String DELETE = "DELETE FROM Albums WHERE AlbumId=?;";
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
	    ResultSet resultKey = null;
	    try {
	      connection = this.connectionManager.getConnection();
	      insertStmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
	      
	      insertStmt.setString(1, album.getAlbumSpotifyId());
	      insertStmt.setString(2, album.getName());
	      insertStmt.setInt(3, album.getYear());
	      insertStmt.setDate(4, new java.sql.Date(album.getReleaseDate().getTime()));
	      insertStmt.setInt(5, album.getDuration());
	      insertStmt.executeUpdate();
	      
	      resultKey = insertStmt.getGeneratedKeys();

	      int albumId = -1;
	      if (resultKey.next()) {
	    	  albumId = resultKey.getInt(1);
	       } else {
	          throw new SQLException("Unable to retrieve auto-generated key");
	       }
	      
	      album.setAlbumId(albumId);
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
	      if (resultKey != null) {
	          resultKey.close();
	      }
	    }
	  }
	  
	  
	  public Albums delete(Albums album) throws SQLException {
		    Connection connection = null;
		    PreparedStatement deleteStmt = null;
		    try {
		      connection = this.connectionManager.getConnection();
		      deleteStmt = connection.prepareStatement(DELETE);
		      deleteStmt.setInt(1, album.getAlbumId());
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
		  
	  public Albums getAlbumById(int albumId) throws SQLException {
		  String selectAlbum = "SELECT * " +
					"From Albums " +
				    "WHERE AlbumId=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAlbum);
			selectStmt.setInt(1, albumId);
			results = selectStmt.executeQuery();
			
			
			if (results.next()) {
				String albumName = results.getString("Name");
				String albumSpotifyId = results.getString("AlbumSpotifyId");
				int year = results.getInt("Year");
				Date releaseDate = new Date(results.getTimestamp("ReleaseDate").getTime());
				int duration = results.getInt("Duration");
				Albums album = new Albums(albumId, albumSpotifyId, albumName, year,
			      releaseDate, duration);
				
				return album;
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
		return null;
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