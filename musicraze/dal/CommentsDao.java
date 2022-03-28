package musicraze.dal;

import musicraze.model.Comments;
import musicraze.model.Likes;
import musicraze.model.Persons;
import musicraze.model.Users;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsDao {
    private static final String INSERT =
            "INSERT INTO Comments(UserName, SongId, Content, CreatedAt) " +
                    "VALUES(?,?,?,?);";
    private static final String SELECT_BY_ID =
            "SELECT " +
                    "* FROM Comments " +
                    "WHERE CommentId = ?;";
    private static final String SELECT_BY_USERNAME =
            "SELECT * " +
                    "FROM Comments INNER JOIN Users" +
                    "ON Users.UserName = Comments.UserName " +
                    "WHERE Users.UserName = ?;";
    private static final String SELECT_BY_SONG_ID =
            "SELECT * " +
                    "FROM Comments INNER JOIN Songs " +
                    "ON Songs.SongId = Comments.SongId " +
                    "WHERE Songs.SongId = ?;";
    private static  final String UPDATE =
            "UPDATE Comments " +
                    "SET Content = ? " +
                    "WHERE CommentId = ?;";
    private static final String DELETE =
            "DELETE FROM Comments " +
                    "WHERE CommentId = ?;";

    private static CommentsDao instance = null;
    protected  ConnectionManager connectionManager;
    protected CommentsDao() {
        this.connectionManager = new ConnectionManager();
    }
    public static CommentsDao getInstance() {
        if(instance == null) {
            instance = new CommentsDao();
        }
        return instance;
    }

    /**
     * Create comment and insert into the database
     * @param comments
     * @return
     * @throws SQLException
     */
    public Comments create(Comments comments) throws SQLException {
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = this.connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, comments.getUser().getUserName());
            insertStmt.setInt(2, comments.getSong().getSongId());
            insertStmt.setString(3, comments.getContent());
            insertStmt.setDate(4, (java.sql.Date) (comments.getCreatedAt()));
            insertStmt.executeUpdate();

            int commentId = -1;
            if(resultKey.next()) {
                commentId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            comments.setCommentId(commentId);
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    /**
     * Get comment by its id
     * @param id
     * @return
     * @throws SQLException
     */
    public Comments getCommentByCommentId(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = this.connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_ID);
            selectStmt.setInt(1, id);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int commentId = results.getInt("CommentId");
                String userName = results.getString("UserName");
                int songId = results.getInt("SongId");
                String content = results.getString("Content");
                Date createdAt = results.getDate("CreatedAt");

                Users user = UsersDao.getInstance().getUserByUserName(userName);
                Songs song = SongsDao.getInstance().getSongBySongId(songId);
                Comments comment = new Comments(commentId, user, song, content, createdAt);
                return comment;
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
     * Get comments by their username
     * @param userName
     * @return
     * @throws SQLException
     */
    public List<Comments> getCommentsByUserName(String userName) throws SQLException {
        List<Comments> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = this.connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_USERNAME);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int commentId = results.getInt("CommentId");
                String resultUserName = results.getString("UserName");
                int songId = results.getInt("SongId");
                String content = results.getString("Content");
                java.util.Date createdAt = results.getDate("CreatedAt");

                Users user = UsersDao.getInstance().getUserByUserName(resultUserName);
                Songs song = SongsDao.getInstance().getSongById(songId);

                Comments comment = new Comments(commentId, user, song,content, createdAt);
                list.add(comment);
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
     * Get comments by the songId they belong to
     * @param id
     * @return
     * @throws SQLException
     */
    public List<Comments> getCommentsBySongId(int id) throws SQLException {
        List<Comments> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = this.connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_SONG_ID);
            selectStmt.setInt(1, id);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int commentId = results.getInt("commentId");
                String userName = results.getString("UserName");
                int songId = results.getInt("SongId");
                String content = results.getString("Content");
                java.util.Date createdAt = results.getDate("CreatedAt");

                Users user = UsersDao.getInstance().getUserByUserName(userName);
                Songs song = SongsDao.getInstance().getSongById(songId);

                Comments comment = new Likes(likeId, user, song, content, createdAt);
                list.add(comment);
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
     * Update the content of the comment
     * @param comment
     * @param newContent
     * @return
     * @throws SQLException
     */
    public Comments updateContent(Comments comment, String newContent) throws SQLException {
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = this.connectionManager.getConnection();
            updateStmt = connection.prepareStatement(UPDATE);
            updateStmt.setString(1, newContent);
            updateStmt.setInt(2, comment.getCommentId());
            updateStmt.executeUpdate();

            comment.setContent(newContent);
            return comment;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }


    /**
     * Delete this comment
     * @param comment
     * @return
     * @throws SQLException
     */
    public Comments delete(Comments comment) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = this.connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE);
            deleteStmt.setInt(1, comment.getCommentId());
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
