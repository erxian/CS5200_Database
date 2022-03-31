package musicraze.tools;

import musicraze.dal.CommentsDao;
import musicraze.dal.LikesDao;
import musicraze.dal.SongsDao;
import musicraze.dal.UsersDao;
import musicraze.model.Comments;
import musicraze.model.Likes;
import musicraze.model.Songs;
import musicraze.model.Users;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class LikesCommentsInserter  {

    public static void main(String[] args) throws Exception {
        UsersDao usersDao = UsersDao.getInstance();
        SongsDao songsDao = SongsDao.getInstance();
        LikesDao likesDao = LikesDao.getInstance();
        CommentsDao commentsDao = CommentsDao.getInstance();


        // Test UsersDao.create()
        Date bornDateForLike = new SimpleDateFormat("yyyy-MM-dd").parse("1985-12-05");
        Instant joinDateForLike = Instant.parse("2022-03-27T12:30:00Z");
        Users userForLikesComments1 = usersDao.create(new Users("UserNameTestForLikes1", "Password2", "FirstName2", "LastName2",
                "Email2", "Avatar2", "Bio2", bornDateForLike, joinDateForLike));
        Users userForLikesComments2 = usersDao.create(new Users("UserNameTestForLikes2", "Password2", "FirstName2", "LastName2",
                "Email2", "Avatar2", "Bio2", bornDateForLike, joinDateForLike));

        Songs songForLikesComments1 = songsDao.create(new Songs("songTestForLike1", 12345, 2343));
        Songs songForLikesComments2 = songsDao.create(new Songs("songTestForLike2", 12345, 2343));


        /****************TEST FOR LikesDao*******************/
        // Create Likes
        Likes likeTest1 = likesDao.create(new Likes(userForLikesComments1, songForLikesComments1, new Date()));
        Likes likeTest2 = likesDao.create(new Likes(userForLikesComments2, songForLikesComments2, new Date()));
        Likes likeTest3 = likesDao.create(new Likes(userForLikesComments2, songForLikesComments1, new Date()));

        // Get Like by id
        Likes likeById = likesDao.getLikeByLikeId(likeTest1.getLikeId());
        System.out.println("Get like by id: " + likeById.toString());

        // Get likes by userName
        List<Likes> listLikesByUserName = likesDao.getLikesByUserName(userForLikesComments2.getUserName());
        for(Likes like: listLikesByUserName) {
            System.out.println("Get likes by userName: " + like.toString());
        }

        // Get likes by songId
        List<Likes> listLikesBySongId = likesDao.getLikesBySongId(songForLikesComments1.getSongId());
        for(Likes like: listLikesBySongId) {
            System.out.println("Get likes by songId: " + like.toString());
        }

        // Delete Likes
        Likes deletedLike1 = likesDao.delete(likeTest1);
        Likes deletedLike2 = likesDao.delete(likeTest2);
        Likes deletedLike3 = likesDao.delete(likeTest3);


        if(deletedLike1 != null || deletedLike2 != null || deletedLike3 != null) {
            throw new Exception("Delete test for likes failed.");
        } else {
            System.out.println("Delete test succeeded.");
        }
        System.out.println();

        /****************TEST FOR CommentsDao*******************/
        // Create
        Comments commentTest1 = commentsDao.create(new Comments(userForLikesComments1, songForLikesComments1, "User1 comment song1", new Date()));
        Comments commentTest2 = commentsDao.create(new Comments(userForLikesComments1, songForLikesComments1, "User1 comment song1 again", new Date()));
        Comments commentTest3 = commentsDao.create(new Comments(userForLikesComments2, songForLikesComments1, "User2 comment song1", new Date()));

        // Get comment by comment id
        Comments commentGetById = commentsDao.getCommentByCommentId(commentTest1.getCommentId());
        System.out.println("Get comment by id: " + commentGetById.toString());

        // Get comments by userName
        List<Comments> commentsByUserName = commentsDao.getCommentsByUserName(userForLikesComments1.getUserName());
        for(Comments comment: commentsByUserName) {
            System.out.println("Get comments by username: " + comment.toString());
        }

        // Get comments by songId
        List<Comments> commentsBySongId = commentsDao.getCommentsBySongId(songForLikesComments1.getSongId());
        for(Comments comment: commentsBySongId) {
            System.out.println("Get comments by songId: " + comment.toString());
        }

        // Update comment
        System.out.println("Comment1 before update: " + commentsDao.getCommentByCommentId(commentTest1.getCommentId()).toString());
        commentsDao.updateContent(commentTest1, "updated content");
        System.out.println("Comment1 after updated: " + commentsDao.getCommentByCommentId(commentTest1.getCommentId()).toString());


        // Delete comments
        Comments deletedComment1 = commentsDao.delete(commentTest1);
        Comments deletedComment2 = commentsDao.delete(commentTest2);
        Comments deletedComment3 = commentsDao.delete(commentTest3);
        if(deletedComment1 != null || deletedComment2 != null || deletedComment3 != null) {
            throw new Exception("Delete comments failed.");
        } else {
            System.out.println("Delete comments test succeeded.");
        }


        // Delete test Users and songs
        usersDao.delete(userForLikesComments1);
        usersDao.delete(userForLikesComments2);
        songsDao.delete(songForLikesComments1);
        songsDao.delete(songForLikesComments2);
    }

}
