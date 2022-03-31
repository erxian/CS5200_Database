package musicraze.model;

public class Songs {
    Integer SongId;
    String songName;
    Integer artistId;
    Integer albumId;
    Artists artist;

    public Songs(Integer songId, String songName, Integer artistId, Integer albumId) {
        SongId = songId;
        this.songName = songName;
        this.artistId = artistId;
        this.albumId = albumId;
    }

    public Songs(String songName, Integer artistId, Integer albumId) {
        this.songName = songName;
        this.artistId = artistId;
        this.albumId = albumId;
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

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Artists getArtist() {
        return artist;
    }

    public void setArtist(Artists artist) {
        this.artist = artist;
    }

}
