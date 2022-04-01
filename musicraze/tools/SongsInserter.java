package musicraze.tools;

import java.util.Date;

import musicraze.dal.*;
import musicraze.model.*;

public class SongsInserter {

    public static void main(String[] args) throws Exception {
        SongsDao songsDao = SongsDao.getInstance();
        AlbumsDao albumsDao = AlbumsDao.getInstance();
        ArtistsDao artistsDao = ArtistsDao.getInstance();
        
        // Create
        Albums album1 = albumsDao.getAlbumById(1);
        Artists artist1 = artistsDao.getArtistById(1);
        
        Songs song1= songsDao.create(new Songs("testSongName",artist1 ,album1));
        
        System.out.println(song1.getSongId() + " "+  song1.getSongName() + " " + song1.getArtist().getArtistId() + " " +
        		song1.getAlbum().getAlbumId());

        // Get song by Id
        Songs song2 = songsDao.getSongById(23);
        System.out.println(song2.getSongName() + " " + song2.getArtist().getArtistId() + " " +
        		song2.getAlbum().getAlbumId());
       
            
        // Update methods not needed and not sure about the argument.
//        song1 = songsDao.updateAlbumId(song1, 2);
//        song1 = songsDao.updateArtistId(song1, 5);

//        System.out.println(song1.getAlbumId());

        Songs deletedSong1 = songsDao.delete(song1);
        if(deletedSong1 !=null) {
        	System.out.println("Delete song1 failed.");
        } else {
        	System.out.println("Delete song1 succeeded.");
        }
    }

}
