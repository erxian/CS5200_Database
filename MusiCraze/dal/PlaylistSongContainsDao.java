package musicraze.dal;

import musicraze.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistSongContainsDao {
	protected ConnectionManager connectionManager;
	private static PlaylistSongContainsDao instance = null;
	
	protected PlaylistSongContainsDao() {
		connectionManager = new ConnectionManager();
	}
	public static PlaylistSongContainsDao getInstance() {
		if(instance == null) {
			instance = new PlaylistSongContainsDao();
		}
		return instance;
	}

	// Scenario: When a User adds a certain Song to a certain Playlist.
	public PlaylistSongContains create(PlaylistSongContains contain) throws SQLException {
		String insertPlaylistSongContain =
				"INSERT INTO PlaylistSongContains(PlaylistId, SongId) " +
				"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPlaylistSongContain,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, contain.getPlaylist().getPlaylistId());
			insertStmt.setInt(2, contain.getSong().getSongId());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int containId = -1;
			if(resultKey.next()) {
				containId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			contain.setContainId(containId);
			
			// Update the UpdatedAt field of this playlist, into which this new song is just added:
			PlaylistsDao playlistsDao = PlaylistsDao.getInstance();
			playlistsDao.updateUpdatedAt(contain.getPlaylist());
			
			return contain;
		} catch (SQLException e) {
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

	// Scenario: 
	// On a User's homepage, all of the User's playlistIds and playlistNames, descriptions are displayed.
	// By clicking a playlistId, all of the playlist's containing songs will be displayed.
	// This method returns a list of PlaylistSongContains.
	// However, when rendering the webpage, we can display only the songs' information, which is also stored in
	// the list of PlaylistSongContains.
	public List<PlaylistSongContains> getSongsForPlaylist(Playlists playlist) throws SQLException {
		List<PlaylistSongContains> contains = new ArrayList<PlaylistSongContains>();
		String selectContains =
				"SELECT ContainId, PlaylistId, SongId " +
				"FROM PlaylistSongContains " +
				"WHERE PlaylistId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectContains);
			selectStmt.setInt(1, playlist.getPlaylistId());
			results = selectStmt.executeQuery();
			
			SongsDao songsDao = SongsDao.getInstance();	
			while(results.next()) {
				int containId = results.getInt("ContainId");
				int songId = results.getInt("SongId");
				Songs song = songsDao.getSongById(songId);
				PlaylistSongContains contain = new PlaylistSongContains(containId, playlist, song);
				
				contains.add(contain);
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
		return contains;
	}
	
	// Used for testing: Whether deleting a song from a playlist would update the playlist's UpdatedAt field.
	public PlaylistSongContains getContainById(int containId) throws SQLException {
		String selectContains =
				"SELECT ContainId, PlaylistId, SongId " +
				"FROM PlaylistSongContains " +
				"WHERE ContainId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectContains);
			selectStmt.setInt(1, containId);
			results = selectStmt.executeQuery();
			
			SongsDao songsDao = SongsDao.getInstance();	
			PlaylistsDao playlistsDao = PlaylistsDao.getInstance();	
			if(results.next()) {
				
				int songId = results.getInt("SongId");
				int playlistId = results.getInt("PlaylistId");
				Songs song = songsDao.getSongById(songId);
				Playlists playlist = playlistsDao.getPlaylistById(playlistId);
				PlaylistSongContains contain = new PlaylistSongContains(containId, playlist, song);
				
				return contain;
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
	
	
	
	
	// Scenario:
	// On a User's homepage, all of the User's playlistIds and playlistNames, descriptions are displayed.
	// By clicking a playlistId, all of the playlist's containing songs will be displayed.
	// When the User wants to delete a song from this playlist, 
	// this method can be called.
	public PlaylistSongContains delete(PlaylistSongContains contain) throws SQLException{
		String deleteContain = "DELETE FROM PlaylistSongContains WHERE PlaylistId=? AND SongId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteContain);
			deleteStmt.setInt(1, contain.getPlaylist().getPlaylistId());
			deleteStmt.setInt(2, contain.getSong().getSongId());
			
			deleteStmt.executeUpdate();

			// Update the UpdatedAt field of this playlist, from which this new song is just deleted:
			PlaylistsDao playlistsDao = PlaylistsDao.getInstance();
			playlistsDao.updateUpdatedAt(contain.getPlaylist());
			
			
			// Return null so the caller can no longer operate on this instance.
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
	
	
	// To Be Continued: Scenario:
	// On a User's homepage, all of the User's playlistIds and playlistNames, descriptions are displayed.
	// By clicking a playlistId, all of the playlist's containing songs will be displayed.
	// When the User wants to move a song from this playlist to another playlist,
	// this method can be called.
//	public PlaylistSongContains updatePlaylistId(PlaylistSongContains contain) {
//		String updatePlaylistId = 
//			"UPDATE PlaylistSongContains SET PlaylistId=? " +
//		    "WHERE ContainId=?";
//		
//		
//		
//	}
	
	
	
	



}

