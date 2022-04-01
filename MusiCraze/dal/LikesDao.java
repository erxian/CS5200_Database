package musicraze.dal;

import musicraze.model.Likes;
import musicraze.model.Songs;
import musicraze.model.Users;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LikesDao {

    private static final String INSERT =
            "INSERT INTO Likes(UserName, SongId, CreatedAt) " +
                    "VALUES(?,?,?);";
    private static final String SELECT_BY_ID =
            "SELECT * FROM Likes WHERE LikeId = ?;";
    private static final String SELECT_BY_USERNAME =
            "SELECT * " +
                    "FROM Likes INNER JOIN Users " +
                    "ON Users.UserName = Likes.UserName " +
                    "WHERE Users.UserName = ?;";
    private static final String SELECT_BY_SONG_ID =
            "SELECT * " +
                    "FROM Likes INNER JOIN Songs " +
                    "ON Songs.SongId = Likes.SongId " +
                    "WHERE Songs.SongId = ?;";
    private static final String DELETE =
            "DELETE FROM Likes " +
                    "WHERE LikeId = ?;";
    private static LikesDao instance = null;
    protected  ConnectionManager connectionManager;

    protected LikesDao() {
        this.connectionManager = new ConnectionManager();
    }
    public static LikesDao getInstance() {
        if(instance == null) {
            instance = new LikesDao();
        }
        return instance;
    }


    /**
     * Create a like and insert into the database;
     * @param like
     * @return
     * @throws SQLException
     */
    public Likes create(Likes like) throws SQLException{
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = this.connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, like.getUser().getUserName());
            insertStmt.setInt(2, like.getSong().getSongId());
            insertStmt.setTimestamp(3, new Timestamp(like.getCreatedAt().getTime()));
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int likeId = -1;
            if(resultKey.next()) {
                likeId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key");
            }

            like.setLikeId(likeId);
            return like;
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


    /**
     * Get the like by its likeId
     * @param id
     * @return
     * @throws SQLException
     */
    public Likes getLikeByLikeId(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = this.connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_ID);
            selectStmt.setInt(1, id);
            results = selectStmt.executeQuery();
            if (results.next()) {
               int likeId = results.getInt("LikeId");
               String userName = results.getString("UserName");
               int songId = results.getInt("SongId");
               Date createdAt = results.getDate("CreatedAt");

               Users user = UsersDao.getInstance().getUserByUserName(userName);
               Songs song = SongsDao.getInstance().getSongById(songId);

               Likes like = new Likes(likeId, user, song, createdAt);
               return like;
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

    /**
     * Get the likes created by the UserName
     * @param userName
     * @return
     * @throws SQLException
     */
    public List<Likes> getLikesByUserName(String userName) throws SQLException {
        List<Likes> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = this.connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_USERNAME);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int likeId = results.getInt("LikeId");
                String resultUserName = results.getString("UserName");
                int songId = results.getInt("SongId");
                Date createdAt = results.getDate("CreatedAt");

                Users user = UsersDao.getInstance().getUserByUserName(resultUserName);
                Songs song = SongsDao.getInstance().getSongById(songId);

                Likes like = new Likes(likeId, user, song, createdAt);
                list.add(like);
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

    /**
     * Get the likes by the songId
     * @param id
     * @return
     * @throws SQLException
     */
    public List<Likes> getLikesBySongId(int id) throws SQLException{
        List<Likes> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = this.connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_SONG_ID);
            selectStmt.setInt(1, id);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int likeId = results.getInt("LikeId");
                String userName = results.getString("UserName");
                int songId = results.getInt("SongId");
                Date createdAt = results.getDate("CreatedAt");

                Users user = UsersDao.getInstance().getUserByUserName(userName);
                Songs song = SongsDao.getInstance().getSongById(songId);

                Likes like = new Likes(likeId, user, song, createdAt);
                list.add(like);
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


    /**
     * Delete this like
     * @param likes
     * @return
     * @throws SQLException
     */
    public Likes delete(Likes likes) throws SQLException{
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = this.connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE);
            deleteStmt.setInt(1, likes.getLikeId());
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
