package musicraze.model;

import java.util.Date;

public class Albums {
	private String albumSpotifyId;
	private int albumId;
	private String name;
	private int year;
	private Date releaseDate;
	private int duration;
	
	/**
	 * @param albumSpotifyId
	 * @param name
	 * @param year
	 * @param releaseDate
	 * @param duration
	 */
	public Albums(int albumId, String albumSpotifyId, String name, int year, Date releaseDate, int duration) {
		this.albumId = albumId;
		this.albumSpotifyId = albumSpotifyId;
		this.name = name;
		this.year = year;
		this.releaseDate = releaseDate;
		this.duration = duration;
	}

	/**
	 * @param albumSpotifyId
	 * @param name
	 * @param year
	 * @param releaseDate
	 * @param duration
	 */
	public Albums(String albumSpotifyId, String name, int year, Date releaseDate, int duration) {
		super();
		this.albumSpotifyId = albumSpotifyId;
		this.name = name;
		this.year = year;
		this.releaseDate = releaseDate;
		this.duration = duration;
	}

	/**
	 * @return the albumSpotifyId
	 */
	public String getAlbumSpotifyId() {
		return albumSpotifyId;
	}

	/**
	 * @param albumSpotifyId the albumSpotifyId to set
	 */
	public void setAlbumSpotifyId(String albumSpotifyId) {
		this.albumSpotifyId = albumSpotifyId;
	}

	/**
	 * @return the albumId
	 */
	public int getAlbumId() {
		return albumId;
	}

	/**
	 * @param albumId the albumId to set
	 */
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the releaseDate
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}


	
	
}