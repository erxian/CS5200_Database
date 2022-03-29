package musicraze.model;

public class Covers {
  Integer CoverId;
  String CoverName;
  String PerformerName;
  String YoutubeUrl;
  Integer SongId;

  public Covers(Integer coverId, String coverName, String performerName, String youtubeUrl,
      Integer songId) {
    CoverId = coverId;
    CoverName = coverName;
    PerformerName = performerName;
    YoutubeUrl = youtubeUrl;
    SongId = songId;
  }

  public Covers(String coverName, String performerName, String youtubeUrl, Integer songId) {
    CoverName = coverName;
    PerformerName = performerName;
    YoutubeUrl = youtubeUrl;
    SongId = songId;
  }

  public Integer getCoverId() {
    return CoverId;
  }

  public void setCoverId(Integer coverId) {
    CoverId = coverId;
  }

  public String getCoverName() {
    return CoverName;
  }

  public void setCoverName(String coverName) {
    CoverName = coverName;
  }

  public String getPerformerName() {
    return PerformerName;
  }

  public void setPerformerName(String performerName) {
    PerformerName = performerName;
  }

  public String getYoutubeUrl() {
    return YoutubeUrl;
  }

  public void setYoutubeUrl(String youtubeUrl) {
    YoutubeUrl = youtubeUrl;
  }

  public Integer getSongId() {
    return SongId;
  }

  public void setSongId(Integer songId) {
    SongId = songId;
  }

  @Override
  public String toString() {
    return "Covers{" +
        "CoverId=" + CoverId +
        ", CoverName='" + CoverName + '\'' +
        ", PerformerName='" + PerformerName + '\'' +
        ", YoutubeUrl='" + YoutubeUrl + '\'' +
        ", SongId=" + SongId +
        '}';
  }
}
