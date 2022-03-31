package musicraze.tools;

import musicraze.dal.*
import musicraze.model.*

import java.util.List;

public class LikesCommentsInserter  {

  public static void main(String[] args) throws Exception {
    SongsDao songsDao = SongsDao.getInstance();
    CoversDao coversDao = CoversDao.getInstance();

    Date bornDateForLike = new SimpleDateFormat("yyyy-MM-dd").parse("1985-12-05");
    Instant joinDateForLike = Instant.parse("2022-03-27T12:30:00Z");
    Users userForLikesComments1 = usersDao.create(new Users("UserNameTestForLikes1", "Password2", "FirstName2", "LastName2",
        "Email2", "Avatar2", "Bio2", bornDateForLike, joinDateForLike));
    Users userForLikesComments2 = usersDao.create(new Users("UserNameTestForLikes2", "Password2", "FirstName2", "LastName2",
        "Email2", "Avatar2", "Bio2", bornDateForLike, joinDateForLike));

    Songs songForCovers1 = songsDao.create(new Songs("songForCovers1", 12345, 2343));
    Songs songForCovers2 = songsDao.create(new Songs("songForCovers2", 12345, 2343));

    Covers coverTest1 = coversDao.create("Cover1", "JB1", "www.youtube.com/1", songForCovers1.getSongId());
    System.out.format("Reading cover: CoverId:%s CoverName:%s PerformerName:%s YoutubeUrl:%s SongId:%s  \n",
        coverTest1.getCoverId(), coverTest1.getCoverName(), coverTest1.getPerformerName(), coverTest1.getYoutubeUrl(), coverTest1.getSongId());
    Covers coverTest2 = coversDao.create("Cover2", "JB1", "www.youtube.com/2", songForCovers2.getSongId());
    System.out.format("Reading cover: CoverId:%s CoverName:%s PerformerName:%s YoutubeUrl:%s SongId:%s  \n",
        coverTest2.getCoverId(), coverTest2.getCoverName(), coverTest2.getPerformerName(), coverTest2.getYoutubeUrl(), coverTest2.getSongId());

    Covers coverByCoverId = coversDao.getCoverByCoverId(coverTest1.getCoverId());
    System.out.println("Get cover by CoverId: " + coverByCoverId.toString());

    List<Covers> coversByCoverName = coversDao.getCoversByCoverName(coverTest2.getUserName());
    for (Covers cover: coversByCoverName) {
      System.out.println("Get coveers by CoverName: " + cover.toString());
    }

    List<Covers> coversByPerformerName = coversDao.getCoversByPerformerName(coverTest2.getPerformerName());
    for (Covers cover: coversByPerformerName) {
      System.out.println("Get covers by PerformerName: " + cover.toString());
    }

    Covers coverByYoutubeUrl = coversDao.getCoverByYoutubeUrl(coverTest1.getYoutubeUrl());
    System.out.println("Get cover by YoutubeUrl: " + coverByYoutubeUrl.toString());

    List<Covers> coversBySongId = coversDao.getCoversBySongId(coverTest2.getSongId());
    for (Covers cover: coversBySongId) {
      System.out.println("Get covers by SongId: " + cover.toString());
    }

    Covers deletedCover1 = coversDao.delete(coverTest1);
    Covers deletedCover1 = coversDao.delete(coverTest2);


    if(deletedCover1 != null || deletedCover1 != null) {
      throw new Exception("Delete test for failed.");
    } else {
      System.out.println("Delete test succeeded.");
    }
    System.out.println();

}
