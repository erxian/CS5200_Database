package musicraze.model;

public class Songs {
    Integer SongId;
    String songName;
    Artists artist;
    Albums album;

    public Songs(Integer songId, String songName, Artists artist, Albums album) {
        SongId = songId;
        this.songName = songName;
        this.artist = artist;
        this.album = album;
    }

    public Songs(String songName, Artists artist, Albums album) {
        this.songName = songName;
        this.artist = artist;
        this.album = album;
    }

	public Integer getSongId() {
		return SongId;
	}

	public void setSongId(Integer songId) {
		SongId = songId;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public Artists getArtist() {
		return artist;
	}

	public void setArtist(Artists artist) {
		this.artist = artist;
	}

	public Albums getAlbum() {
		return album;
	}

	public void setAlbum(Albums album) {
		this.album = album;
	}

}
