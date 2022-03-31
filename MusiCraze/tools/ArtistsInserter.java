package musicraze.tools;

import musicraze.dal.*;
import musicraze.model.*;
import java.util.List;

public class Inserter {

  public static void main(String[] args) throws Exception {
		ArtistsDao artistsDao = ArtistsDao.getInstance();
		

		// INSERT objects from our model.
		Artists evelyn = new Artists("evelynxu", "abcedf", "US", "SELF");
		evelyn = artistsDao.create(evelyn);
		System.out.format("Reading artist: id:%s name:%s  \n",
				evelyn.getArtistId(), evelyn.getArtistName());
		
		// Test get artist by id
		Artists artist_test1 = artistsDao.getArtistById(137447);
		System.out.format("Reading artist: id: %s name: %s  \n",
				artist_test1.getArtistId(), artist_test1.getArtistName());
		
		// Test get artists by name
		Artists evelynxu = new Artists("evelynxu", "xndhrhgjg", "Korea", "SELF");
		evelynxu = artistsDao.create(evelynxu);
		List<Artists> artistList = artistsDao.getArtistByName("evelynxu");
		for (Artists artist_test2 : artistList) {
			System.out.format("Listing artist: id: %s name: %s country: %s \n",
				artist_test2.getArtistId(), artist_test2.getArtistName(), artist_test2.getArtistCountry());
		}
		
		// Test artist name update
		Artists michael = new Artists("michaelchen", "888888", "US", "SELF");
		michael = artistsDao.create(michael);
		System.out.format("Reading artist: id: %s name: %s  \n",
				michael.getArtistId(), michael.getArtistName());
		
		michael = artistsDao.updateArtistName(michael, "TomChen");
		System.out.format("After updating, artist: id: %s name: %s \n",
				michael.getArtistId(), michael.getArtistName());
		
		
		// Test artist spotifyid update
		Artists bruce = new Artists("bruceLi", "000000", "US", "SELF");
		bruce = artistsDao.create(bruce);
		System.out.format("Reading artist: id: %s name: %s spotifyid: %s \n",
				bruce.getArtistId(), bruce.getArtistName(), bruce.getArtistSpotifyId());
		
		bruce = artistsDao.updateArtistSpotifyId(bruce, "77777777");
		System.out.format("After updating: id: %s name: %s spotifyid: %s \n",
				bruce.getArtistId(), bruce.getArtistName(), bruce.getArtistSpotifyId());
		
		
		// Test artist country update
		Artists jannie = new Artists("jannieTong", "9876543", "UK", "SELF");
		jannie = artistsDao.create(jannie);
		System.out.format("Reading artist: id: %s name: %s country: %s \n",
				jannie.getArtistId(), jannie.getArtistName(), jannie.getArtistCountry());
		
		jannie = artistsDao.updateArtistCountry(jannie, "Tailand");
		System.out.format("After updating: id: %s name: %s country: %s \n",
				jannie.getArtistId(), jannie.getArtistName(), jannie.getArtistCountry());
		
		
		// Test artist recordlabel update
		Artists rose = new Artists("roseWu", "9876543", "UK", "SELF");
		rose = artistsDao.create(rose);
		System.out.format("Reading artist: id: %s name: %s recordLabel: %s \n",
				rose.getArtistId(), rose.getArtistName(), rose.getArtistRecordLabel());
		
		rose = artistsDao.updateArtistRecordLabel(rose, "ROCK");
		System.out.format("Reading artist: id: %s name: %s recordLabel: %s \n",
				rose.getArtistId(), rose.getArtistName(), rose.getArtistRecordLabel());
  }
}
