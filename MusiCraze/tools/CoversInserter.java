package musicraze.tools;

import musicraze.dal.*;
import musicraze.model.*;

import java.util.Date;
import java.util.List;

public class CoversInserter  {

  public static void main(String[] args) throws Exception {
    SongsDao songsDao = SongsDao.getInstance();
    CoversDao coversDao = CoversDao.getInstance();
    AlbumsDao albumsDao = AlbumsDao.getInstance();
    ArtistsDao artistsDao = ArtistsDao.getInstance();

    Albums album1 = albumsDao.getAlbumById(1);
    Artists artist1 = artistsDao.getArtistById(1);
    
    
    Songs songForCovers1 = songsDao.create(new Songs("songForCovers1", artist1, album1));
    Songs songForCovers2 = songsDao.create(new Songs("songForCovers2", artist1, album1));

    
    Covers coverTest1 = coversDao.create(new Covers("Cover1", "JB1", "www.youtube.com/1", songForCovers1));
    System.out.format("Reading cover: CoverId:%s CoverName:%s PerformerName:%s YoutubeUrl:%s SongId:%s  \n",
        coverTest1.getCoverId(), coverTest1.getCoverName(), coverTest1.getPerformerName(), coverTest1.getYoutubeUrl(), coverTest1.getSong().getSongId());
    Covers coverTest2 = coversDao.create(new Covers("Cover2", "JB1", "www.youtube.com/2", songForCovers2));
    System.out.format("Reading cover: CoverId:%s CoverName:%s PerformerName:%s YoutubeUrl:%s SongId:%s  \n",
        coverTest2.getCoverId(), coverTest2.getCoverName(), coverTest2.getPerformerName(), coverTest2.getYoutubeUrl(), coverTest2.getSong().getSongId());

    // Covers coverByCoverId = coversDao.getCoverByCoverId(coverTest1.getCoverId());
    Covers coverByCoverId = coversDao.getCoverByCoverId(1);
    System.out.println("Get cover by CoverId: " + coverByCoverId.toString());

    // List<Covers> coversByCoverName = coversDao.getCoversByCoverName(coverTest2.getCoverName());   //Not getUsername!!!
    List<Covers> coversByCoverName = coversDao.getCoversByCoverName("Fix you");
    for (Covers cover: coversByCoverName) {
      System.out.println("Get coveers by CoverName: " + cover.toString());
    }

	// List<Covers> coversByPerformerName = coversDao.getCoversByPerformerName(coverTest2.getPerformerName());
    List<Covers> coversByPerformerName = coversDao.getCoversByPerformerName("The Fu");
    for (Covers cover: coversByPerformerName) {
      System.out.println("Get covers by PerformerName: " + cover.toString());
    }

    // Covers coverByYoutubeUrl = coversDao.getCoverByYoutubeUrl(coverTest1.getYoutubeUrl());
    Covers coverByYoutubeUrl = coversDao.getCoverByYoutubeUrl("www.youtube.com/2");
    System.out.println("Get cover by YoutubeUrl: " + coverByYoutubeUrl.toString());

    // List<Covers> coversBySongId = coversDao.getCoversBySongId(coverTest2.getSong().getSongId());
    List<Covers> coversBySongId = coversDao.getCoversBySongId(61);
    for (Covers cover: coversBySongId) {
      System.out.println("Get covers by SongId: " + cover.toString());
    }

    // Update Methods not tested. But probably won't need Update methods for Covers at front-end.
    
    Covers deletedCover1 = coversDao.delete(coverTest1);
    // Covers deletedCover2 = coversDao.delete(coverTest2);


    // if(deletedCover1 != null || deletedCover2 != null) {
    if (deletedCover1 !=null) {
      throw new Exception("Delete test failed.");
    } else {
      System.out.println("Delete test succeeded.");
    }
    System.out.println();

  }
}
