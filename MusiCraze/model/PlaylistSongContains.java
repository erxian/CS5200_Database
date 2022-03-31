package musicraze.model;

public class PlaylistSongContains {
	protected int containId;
	protected Playlists playlist;
	protected Songs song;
	
	// Constructor1: Takes in all fields as input
	public PlaylistSongContains(int containId, Playlists playlist, Songs song) {
		this.containId = containId;
		this.playlist = playlist;
		this.song = song;
	}
	
	// Constructor2: Exclude fields that will be given default value in MySQL layer, 
	// i.e. the auto-generated key: containId.
	public PlaylistSongContains(Playlists playlist, Songs song) {
		this.playlist = playlist;
		this.song = song;
	}
	
	// Constructor3: Used when we only have a reference key to a PlaylistSongContains relation.
	// No such scenario -> Constructor 3 not needed!
	
	
	
	public int getContainId() {
		return containId;
	}

	public void setContainId(int containId) {
		this.containId = containId;
	}

	public Playlists getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlists playlist) {
		this.playlist = playlist;
	}

	public Songs getSong() {
		return song;
	}

	public void setSong(Songs song) {
		this.song = song;
	}


	
	
	
	
	
}
