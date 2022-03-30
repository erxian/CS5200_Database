package musicraze.dal;

import musicraze.model.Covers;
import musicraze.model.Songs;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoversDao {
  protected ConnectionManager connectionManager;
  private static CoversDao instance = null;

  private static final String INSERT =
      "INSERT INTO Covers(CoverName, PerformerName, YoutubeUrl, SongId) " +
          "VALUES(?,?,?,?);";
  private static final String GET_BY_COVER_ID =
      "SELECT * FROM Covers WHERE CoverId = ?;";
  private static final String GET_BY_COVER_NAME =
      "SELECT * FROM Covers WHERE CoverName = ?;";
  private static final String GET_BY_PERFORMER_NAME =
      "SELECT * FROM Covers WHERE PerformerName = ?;";
  private static final String GET_BY_YOUTUBE_URL =
      "SELECT * FROM Covers WHERE YoutubeUrl = ?;";
  private static final String GET_BY_SONG_ID =
      "SELECT * FROM Covers INNER JOIN Songs ON Covers.SongId = Songs.SongId WHERE Songs.SongId = ?;";
  private static final String DELETE =
      "DELETE FROM Covers WHERE CoverId = ?;";

  protected CoversDao() {
    this.connectionManager = new ConnectionManager();
  }
  public static CoversDao getInstance() {
    if(instance == null) {
      instance = new CoversDao();
    }
    return instance;
  }

  public Covers create(Covers cover) throws SQLException {
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = this.connectionManager.getConnection();
      insertStmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, cover.getCoverName());
      insertStmt.setString(2, cover.getPerformerName());
      insertStmt.setString(3, cover.getYoutubeUrl());
      insertStmt.setInt(4, cover.getSongId());
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int coverId = -1;
      if(resultKey.next()) {
        coverId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key");
      }

      cover.setCoverId(coverId);
      return cover;
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

  public Covers getCoverByCoverId(int id) throws SQLException{
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(GET_BY_COVER_ID);
      selectStmt.setInt(1, id);
      results = selectStmt.executeQuery();
      if (results.next()) {
        int resultCoverId = results.getInt("CoverId");
        String coverName = results.getString("CoverName");
        String performerName = results.getString("PerformerName");
        String youtubeUrl = results.getString("YoutubeUrl");
        int songId = results.getInt("SongId");

        Songs song = SongsDao.getInstance().getSongById(songId);

        Covers cover = new Covers(resultCoverId, coverName, performerName, youtubeUrl, songId);
        return cover;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return null;
  }

  public List<Covers> getCoversByCoverName(String coverName) throws SQLException {
    List<Covers> list = new ArrayList<>();
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(GET_BY_COVER_NAME);
      selectStmt.setString(1, coverName);
      results = selectStmt.executeQuery();

      while (results.next()) {
        int coverId = results.getInt("CoverId");
        String resultCoverName = results.getString("CoverName");
        String performerName = results.getString("PerformerName");
        String youtubeUrl = results.getString("YoutubeUrl");
        int songId = results.getInt("SongId");

        Songs song = SongsDao.getInstance().getSongById(songId);

        Covers cover = new Covers(coverId, resultCoverName, performerName, youtubeUrl, songId);
        list.add(cover);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return list;
  }

  public List<Covers> getCoversByPerformerName(String performerName) throws SQLException {
    List<Covers> list = new ArrayList<>();
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(GET_BY_COVER_NAME);
      selectStmt.setString(1, performerName);
      results = selectStmt.executeQuery();

      while (results.next()) {
        int coverId = results.getInt("CoverId");
        String coverName = results.getString("CoverName");
        String resultPerformerName = results.getString("PerformerName");
        String youtubeUrl = results.getString("YoutubeUrl");
        int songId = results.getInt("SongId");

        Songs song = SongsDao.getInstance().getSongById(songId);

        Covers cover = new Covers(coverId, coverName, resultPerformerName, youtubeUrl, songId);
        list.add(cover);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return list;
  }

  public Covers getCoverByYoutubeUrl(String youtubeUrl) throws SQLException{
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(GET_BY_YOUTUBE_URL);
      selectStmt.setString(1, youtubeUrl);
      results = selectStmt.executeQuery();
      if (results.next()) {
        int coverId = results.getInt("CoverId");
        String coverName = results.getString("CoverName");
        String performerName = results.getString("PerformerName");
        String resultYoutubeUrl = results.getString("YoutubeUrl");
        int songId = results.getInt("SongId");

        Songs song = SongsDao.getInstance().getSongById(songId);

        Covers cover = new Covers(coverId, coverName, performerName, resultYoutubeUrl, songId);
        return cover;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return null;
  }

  public List<Covers> getCoversBySongId(int id) throws SQLException{
    List<Covers> list = new ArrayList<>();
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = this.connectionManager.getConnection();
      selectStmt = connection.prepareStatement(GET_BY_SONG_ID);
      selectStmt.setInt(1, id);
      results = selectStmt.executeQuery();

      while (results.next()) {
        int coverId = results.getInt("CoverId");
        String coverName = results.getString("CoverName");
        String performerName = results.getString("PerformerName");
        String youtubeUrl = results.getString("YoutubeUrl");
        int resultSongId = results.getInt("SongId");

        Songs song = SongsDao.getInstance().getSongById(songId);

        Covers cover = new Covers(coverId, coverName, performerName, youtubeUrl, resultSongId);
        list.add(cover);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return list;
  }

  public Covers delete(Covers cover) throws SQLException{
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = this.connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(DELETE);
      deleteStmt.setInt(1, cover.getCoverId());
      deleteStmt.executeUpdate();
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }

}
