package musicraze.model;

public class Covers {
  Integer coverId;
  String coverName;
  String performerName;
  String youtubeUrl;
  Songs song;

  public Covers(Integer coverId, String coverName, String performerName, String youtubeUrl,
      Songs song) {
    this.coverId = coverId;
    this.coverName = coverName;
    this.performerName = performerName;
    this.youtubeUrl = youtubeUrl;
    this.song = song;
  }

  public Covers(String coverName, String performerName, String youtubeUrl, Songs song) {
    this.coverName = coverName;
    this.performerName = performerName;
    this.youtubeUrl = youtubeUrl;
    this.song = song;
  }


  public Integer getCoverId() {
	return coverId;
  }
	
	public void setCoverId(Integer coverId) {
		this.coverId = coverId;
	}
	
	public String getCoverName() {
		return coverName;
	}
	
	public void setCoverName(String coverName) {
		this.coverName = coverName;
	}
	
	public String getPerformerName() {
		return performerName;
	}
	
	public void setPerformerName(String performerName) {
		this.performerName = performerName;
	}
	
	public String getYoutubeUrl() {
		return youtubeUrl;
	}
	
	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}
	
	public Songs getSong() {
		return song;
	}
	
	public void setSong(Songs song) {
		this.song = song;
	}

@Override
  public String toString() {
    return "Covers{" +
        "CoverId=" + this.coverId +
        ", CoverName='" + this.coverName + '\'' +
        ", PerformerName='" + this.performerName + '\'' +
        ", YoutubeUrl='" + this.youtubeUrl + '\'' +
        ", SongId=" + this.song.getSongId() +
        '}';
  }
}
