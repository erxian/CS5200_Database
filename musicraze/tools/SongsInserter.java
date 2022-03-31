package musicraze.tools;

import musicraze.dal.*;
import musicraze.model.*;

public class SongsInserter {

    public static void main(String[] args) throws Exception {
        SongsDao songsDao = SongsDao.getInstance();

        Songs song1 = songsDao.create(new Songs("test", 12345, 2343));
        System.out.println(song1.getSongName() + " " + song1.getArtistId() + " " + song1.getArtistId());

        Songs song2 = songsDao.getSongById(23);

        System.out.println(song2.toString());

        song1 = songsDao.updateAlbumId(song1, 2);
        song1 = songsDao.updateArtistId(song1, 5);

        System.out.println(song1.getAlbumId());

        song1 = songsDao.delete(song1);
    }

}
