package musicraze.model;

import java.util.Date;

public class Playlists {
	protected int playlistId;
	protected Users user;
	protected String playlistName;
	protected String description;
	protected Date createdAt;
	protected Date updatedAt;
	
	
	// Constructor1: Takes in all fields as input
	public Playlists(int playlistId, Users user, String playlistName, String description, Date createdAt,
			Date updatedAt) {
		this.playlistId = playlistId;
		this.user = user;
		this.playlistName = playlistName;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Constructor2: Exclude fields that will be given default value in MySQL layer, 
	// i.e. the auto-generated key: playlistId, and the default timestamps: createdAt, updatedAt.
	public Playlists(Users user, String playlistName, String description) {
		this.user = user;
		this.playlistName = playlistName;
		this.description = description;
	}
	
	// Constructor3: Used when we only have a reference key to a playlist. 
	//(e.g. On a User's homepage, all of the User's playlistIds and playlistNames, descriptions are displayed.
	// By clicking a playlistId, all of the playlist's containing songs will be displayed.
	// In this scenario, only playlistId is given to the PlaylistSongContainsDao's getSongsByPlaylistId method,
	// in which a Playlists object needs to be created with playlistId as the only argument.)
	public Playlists(int playlistId) {
		this.playlistId = playlistId;
	}

	
	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
	
	
	
	
	
	
}
