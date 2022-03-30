package musicraze.model;

/**
 * CREATE TABLE Artists (
 *     ArtistId  INT AUTO_INCREMENT,
 *     ArtistName  VARCHAR(200),
 *     ArtistSpotifyId  VARCHAR(100),
 *     ArtistCountry  VARCHAR(100) DEFAULT NULL,
 *     ArtistRecordLabel  VARCHAR(100),
 *     CONSTRAINT pk_Artists_ArtistId PRIMARY KEY (ArtistId)
 * );
 */

public class Artists {
  protected int artistId;
  protected String artistName;
  protected String artistSpotifyId;
  protected String artistCountry;
  protected String artistRecordLabel;

  public Artists(int artistId, String artistName, String artistSpotifyId,
      String artistCountry, String artistRecordLabel) {
    this.artistId = artistId;
    this.artistName = artistName;
    this.artistSpotifyId = artistSpotifyId;
    this.artistCountry = artistCountry;
    this.artistRecordLabel = artistRecordLabel;
  }

  public int getArtistId() {
    return artistId;
  }

  public String getArtistName() {
    return artistName;
  }

  public String getArtistSpotifyId() {
    return artistSpotifyId;
  }

  public String getArtistCountry() {
    return artistCountry;
  }

  public String getArtistRecordLabel() {
    return artistRecordLabel;
  }

  public void setArtistId(int artistId) {
    this.artistId = artistId;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  public void setArtistSpotifyId(String artistSpotifyId) {
    this.artistSpotifyId = artistSpotifyId;
  }

  public void setArtistCountry(String artistCountry) {
    this.artistCountry = artistCountry;
  }

  public void setArtistRecordLabel(String artistRecordLabel) {
    this.artistRecordLabel = artistRecordLabel;
  }
}
