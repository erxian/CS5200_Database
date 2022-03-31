package musicraze.dal;

import musicraze.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistsDao {
	protected ConnectionManager connectionManager;
	protected static PlaylistsDao instance = null;
	
	// Singleton Pattern
	private PlaylistsDao() {
		this.connectionManager = new ConnectionManager();
		
	}
	public static PlaylistsDao getInstance() {
		if (instance == null) {
			instance = new PlaylistsDao();
		}
		return instance;
	}
	
	// Scenario: A certain user creates a new playlist. 
	// Input: playlist: constructed from only userName,playlistName and description.
	public Playlists create(Playlists playlist) throws SQLException {
		String insertPlaylist = 
			"INSERT INTO Playlists(UserName, PlaylistName, Description) " +
			"Values(?,?,?)";
		
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPlaylist,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, playlist.getUser().getUserName());
			insertStmt.setString(2, playlist.getPlaylistName());
			insertStmt.setString(3, playlist.getDescription());
			
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int playlistId;
			if (resultKey.next()) {
				playlistId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			
			// Update the playlist object here before returning it.
			playlist.setPlaylistId(playlistId);
			
			// Call PlaylistsDao's getPlaylistById method,
			// retrieve this playlist's createdAt and updatedAt field.
			playlist.setCreatedAt(getPlaylistById(playlistId).getCreatedAt());
			playlist.setUpdatedAt(getPlaylistById(playlistId).getUpdatedAt());
			
			return playlist;
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	
	public Playlists updatePlaylistName(Playlists playlist, String newName) throws SQLException {
		String updatePlaylist = 
				"UPDATE Playlists SET PlaylistName=?, UpdatedAt=? " +
				"WHERE PlaylistId=?";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePlaylist);
			updateStmt.setString(1, newName);
			Date newUpdatedAt = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newUpdatedAt.getTime()));
			updateStmt.setInt(3, playlist.getPlaylistId());
			updateStmt.executeUpdate();
			
			playlist.setPlaylistName(newName);
			playlist.setUpdatedAt(newUpdatedAt);
			
			return playlist;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public Playlists updateDescription(Playlists playlist, String newDescription) throws SQLException {
		String updatePlaylist = 
				"UPDATE Playlists SET Description=?, UpdatedAt=?" +
				"WHERE PlaylistId=?";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePlaylist);
			updateStmt.setString(1, newDescription);
			Date newUpdatedAt = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newUpdatedAt.getTime()));
			updateStmt.setInt(3, playlist.getPlaylistId());
			updateStmt.executeUpdate();
			
			playlist.setPlaylistName(newDescription);
			playlist.setUpdatedAt(newUpdatedAt);
			
			return playlist;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	// This method will be called by PlaylistSongContainsDao's create, update, delete method.
	// i.e. Whenever a song is added to or deleted from this playlist,
	// the updatedAt timestamp should be reset.
	public Playlists updateUpdatedAt(Playlists playlist) throws SQLException {
		String updatePlaylist = 
				"UPDATE Playlists SET UpdatedAt=?" +
				"WHERE PlaylistId=?";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePlaylist);

			Date newUpdatedAt = new Date();
			updateStmt.setTimestamp(1, new Timestamp(newUpdatedAt.getTime()));
			updateStmt.setInt(2, playlist.getPlaylistId());
			
			updateStmt.executeUpdate();
			playlist.setUpdatedAt(newUpdatedAt);
			
			return playlist;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	// Scenario: On a User's homepage, all of the User's playlists are displayed.
	// When click on one playlist's delete link, delete that playlist.
	public Playlists delete(Playlists playlist) throws SQLException {
		String deletePlaylist = "DELETE FROM Playlists WHERE PlaylistId=?";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePlaylist);
			
			deleteStmt.setInt(1, playlist.getPlaylistId());
			
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	
	public Playlists getPlaylistById(int playlistId) throws SQLException {
		String selectPlaylist = "SELECT PlaylistId, UserName, PlaylistName, Description, CreatedAt, UpdatedAt " +
				"From Playlists " +
			    "WHERE PlaylistId=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPlaylist);
			selectStmt.setInt(1, playlistId);
			results = selectStmt.executeQuery();
			
			
			if (results.next()) {
				String userName = results.getString("UserName");
				UsersDao usersDao = UsersDao.getInstance();
				Users user = usersDao.getUserByUserName(userName);
				String playlistName = results.getString("PlaylistName");
				String description = results.getString("Description");
				Date createdAt = new Date(results.getTimestamp("CreatedAt").getTime());
				Date updatedAt = new Date(results.getTimestamp("UpdatedAt").getTime());
				
				Playlists playlist = new Playlists(playlistId, user, playlistName,
						description, createdAt, updatedAt);
				return playlist;
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
	
	
	
	// TODO: Move this Scenario to PlaylistSongContainsDao: getSongsByPlaylistId method.
	// Scenario: On a User's homepage, all of the User's playlists are displayed.
	// When click on one playlistId, direct to another page 
	// that shows all of this playlist's containing songs.
	public List<Playlists> getPlaylistsForUser(Users user) throws SQLException {
		List<Playlists> playlists = new ArrayList<Playlists>();
		String selectPlaylists = 
			"SELECT PlaylistId, PlaylistName, Description, CreatedAt, UpdatedAt " + 
			"From Playlists " +
		    "WHERE UserName=?";
	
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPlaylists);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			
			while (results.next()) {
				int playlistId = results.getInt("PlaylistId");
				String playlistName = results.getString("PlaylistName");
				String description = results.getString("Description");
				Date createdAt = new Date(results.getTimestamp("CreatedAt").getTime());
				Date updatedAt = new Date(results.getTimestamp("UpdatedAt").getTime());
				
				Playlists playlist = new Playlists(playlistId, user, playlistName,
						description, createdAt, updatedAt);
				playlists.add(playlist);
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
		return playlists;
	}
	
	// Scenario: When a User is searching for all playlists named "Road-trip music"(including other Users' playlists).
	public List<Playlists> getPlaylistsByName(String playlistName) throws SQLException {
		List<Playlists> playlists = new ArrayList<Playlists>();
		String selectPlaylists = 
			"SELECT PlaylistId, UserName, PlaylistName, Description, CreatedAt, UpdatedAt " +
			"FROM Playlists " + 
			"WHERE PlaylistName=?";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPlaylists);
			selectStmt.setString(1, playlistName);
			results = selectStmt.executeQuery();
			
			UsersDao usersDao = UsersDao.getInstance();
			while (results.next()) {
				int playlistId = results.getInt("PlaylistId");
				String userName = results.getString("UserName");
				Users user = usersDao.getUserByUserName(userName);
				
				String description = results.getString("Description");
				Date createdAt = new Date(results.getTimestamp("CreatedAt").getTime());
				Date updatedAt = new Date(results.getTimestamp("UpdatedAt").getTime());
				
				Playlists playlist = new Playlists(playlistId, user, playlistName,
						description, createdAt, updatedAt);
				playlists.add(playlist);
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
		return playlists;
	}
	
}
