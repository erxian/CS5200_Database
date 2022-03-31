package musicraze.tools;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import musicraze.dal.*;
import musicraze.model.*;

public class PlaylistsInserter {
	
	
	public static void main(String[] args) throws Exception {
		// Instantiate DAO instances.
		SongsDao songsDao = SongsDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();
		PlaylistsDao playlistsDao = PlaylistsDao.getInstance();
		PlaylistSongContainsDao containsDao = PlaylistSongContainsDao.getInstance();
		
		// INSERT objects from our model.
		// dao.create <==> INSERT statement 

		Users userZwill = usersDao.getUserByUserName("zwillgooselr"); // the last record in users.csv;
	    
		
		// Create Playlist: userZwill creats 3 new playlists:
		
	    Playlists playlist1 = new Playlists(userZwill, "Workout", "workout music");
	    playlist1 = playlistsDao.create(playlist1);
	    
	    Playlists playlist2 = new Playlists(userZwill, "Workout", "Some more workout music");
	    playlist2 = playlistsDao.create(playlist2);
		
	    Playlists playlist3 = new Playlists(userZwill, "Happy Drive", "xxxx");
	    playlist3 = playlistsDao.create(playlist3);
	    
	    Playlists playlist4 = new Playlists(userZwill, "Hip hop", "!!!!");
	    playlist4 = playlistsDao.create(playlist4);

	    
	    // Update playlist3:		
		playlistsDao.updatePlaylistName(playlist3, "RoadTrip Music");
		playlistsDao.updateDescription(playlist3, "Some good music for road trip!!");
		
		
		
		// Test whether updating a playlist's name/description would update the playlist's UpdatedAt field.
		// Update playlist3:
//		playlistsDao.updatePlaylistName(playlistsDao.getPlaylistById(134), "RoadTrip Music");
//		playlistsDao.updateDescription(playlistsDao.getPlaylistById(134), "Some good music for road trip!!");
		
	    
		 // Get playlists for userZwill: (Should have playlist1, playlist2, playlist3)
 	    List<Playlists> listOfPlaylists1 = playlistsDao.getPlaylistsForUser(userZwill);
 	    for(Playlists pl : listOfPlaylists1) {
			System.out.format("Get Playlists For UserZwill: userName:%s playlistId:%s playlistName:%s Description: %s \n",
			pl.getUser().getUserName(), pl.getPlaylistId(), pl.getPlaylistName(), pl.getDescription());
		}
 	    
 	    
 	    // Get playlists by playlistName: Workout (should have playlist1, playlist2)
 	    List<Playlists> listOfPlaylists2 = playlistsDao.getPlaylistsByName("Workout");
 	    for(Playlists pl : listOfPlaylists2) {
			System.out.format("Get Playlists By PlaylistName: userName:%s playlistId:%s playlistName:%s Description: %s \n",
			pl.getUser().getUserName(), pl.getPlaylistId(), pl.getPlaylistName(), pl.getDescription());
		}
 	    
 	    
 	    // Get playlist by Id: 1
 	    Playlists playlist5 = playlistsDao.getPlaylistById(1);
 	   System.out.format("Get Playlists By Id: playlistId:%s playlistName:%s Description: %s \n",
 			   playlist5.getPlaylistId(), playlist5.getPlaylistName(),playlist5.getDescription());
	 
 	    // User2 Adds song11, song12 to playlist1
 	    Songs song11 = songsDao.getSongById(11);
 	    PlaylistSongContains contain1 = new PlaylistSongContains(playlist1, song11);
 	    contain1 =  containsDao.create(contain1);
 	    
 	    Songs song12 = songsDao.getSongById(12);
 	    PlaylistSongContains contain2 = new PlaylistSongContains(playlist1, song12);
 	    contain2 =  containsDao.create(contain2);
 	    
 	    
 	    // User2 Adds song11, song13 to playlist2
 	    PlaylistSongContains contain3 = new PlaylistSongContains(playlist2, song11);
	    contain3 =  containsDao.create(contain3);
	    
 	    Songs song13 = songsDao.getSongById(13);
 	    PlaylistSongContains contain4 = new PlaylistSongContains(playlist2, song13);
 	    contain4 =  containsDao.create(contain4);
		
		
 	    // Test whether adding a song to a playlist would update the playlist's UpdatedAt field.
 	    // User2 Adds song11, song12 to playlist1
// 	    Songs song11 = songsDao.getSongById(11);
// 	    PlaylistSongContains contain1 = new PlaylistSongContains(playlistsDao.getPlaylistById(132), song11);
// 	    contain1 =  containsDao.create(contain1);
// 	    
// 	    Songs song12 = songsDao.getSongById(12);
// 	    PlaylistSongContains contain2 = new PlaylistSongContains(playlistsDao.getPlaylistById(132), song12);
// 	    contain2 =  containsDao.create(contain2);
// 	    
// 	    
// 	    // User2 Adds song11, song13 to playlist2
// 	    PlaylistSongContains contain3 = new PlaylistSongContains(playlistsDao.getPlaylistById(133), song11);
//	    contain3 =  containsDao.create(contain3);
//	    
// 	    Songs song13 = songsDao.getSongById(13);
// 	    PlaylistSongContains contain4 = new PlaylistSongContains(playlistsDao.getPlaylistById(133), song13);
// 	    contain4 =  containsDao.create(contain4);
 	    
 	    
 	    // Get all songs for playlist1 (should contain song11 & song12)
 	   List<PlaylistSongContains> listOfContains1 = containsDao.getSongsForPlaylist(playlist1);
 	   for(PlaylistSongContains c : listOfContains1) {
 		  System.out.format("Get Songs For Playlist: playlistId:%s playlistName:%s songId:%s songName:%s\n",
 		    c.getPlaylist().getPlaylistId(), c.getPlaylist().getPlaylistName(),
 		    c.getSong().getSongId(), c.getSong().getSongName());
 	   }
 	    
 	   
	    // Get all songs for playlist2 (should contain song11 & song13)
	   List<PlaylistSongContains> listOfContains2 = containsDao.getSongsForPlaylist(playlist2);
	   for(PlaylistSongContains c : listOfContains2) {
		  System.out.format("Get Songs For Playlist: playlistId:%s playlistName:%s songId:%s songName:%s\n",
		    c.getPlaylist().getPlaylistId(), c.getPlaylist().getPlaylistName(),
		    c.getSong().getSongId(), c.getSong().getSongName());
	   }
 	   
 	    // Deletes song11 from playlist2
 	    containsDao.delete(contain3);
 	    System.out.format("Song11 deleted from playlist2.\n");
 	    
 	    
 	   //  Test whether deleting a song from a playlist would update the playlist's UpdatedAt field.
 	   // Deletes song11 from playlist2
// 	    containsDao.delete(containsDao.getContainById(1003));
// 	    System.out.format("Song11 deleted from playlist2.\n");


 	    
 	    // Get all songs for playlist2 (NOTE: should ONLY contain song13)
 	   List<PlaylistSongContains> listOfContains3 = containsDao.getSongsForPlaylist(playlist2);
 	   for(PlaylistSongContains c : listOfContains3) {
 		  System.out.format("Get Songs For Playlist: playlistId:%s playlistName:%s songId:%s songName:%s\n",
 		    c.getPlaylist().getPlaylistId(), c.getPlaylist().getPlaylistName(),
 		    c.getSong().getSongId(), c.getSong().getSongName());
 	   }
 	    
 	    // Deletes playlist4
		playlistsDao.delete(playlist4);
		System.out.format("playlist4: \"Hip Hop\" deleted from playlist4.\n");
		
		
		
		

	}

}
