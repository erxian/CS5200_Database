package musicraze.tools;

import musicraze.dal.*;
import musicraze.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/**
 * main() runner, used for the app demo.
 * 
 * Instructions:
 * 1. Create a new MySQL schema and then run the CREATE TABLE statements from lecture:
 * http://goo.gl/86a11H.
 * 2. Update ConnectionManager with the correct user, password, and schema.
 */
public class AlbumsInserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances.
		AlbumsDao albumsDao = AlbumsDao.getInstance();
		
		
		// INSERT objects from our model.
		Albums album1 = new Albums("asasd45", "Yesterday once more", 1994, new Date(), 14535);
		album1 = albumsDao.create(album1);
		
		Albums album2 = new Albums("sdfgsdfg456", "Mayday", 1995, new Date(), 145885);
		album2 = albumsDao.create(album2);
		
		
		Albums album3 = new Albums("ss33asd45", "New World", 1999, new Date(), 145225);
		album3 = albumsDao.create(album3);
		
		
		// READ.
		List<Albums> pList1 = albumsDao.getAlbumsFromAlbumName("Mayday");
		
		for(Albums p : pList1) {
			System.out.format("Looping Albums: AlbumId:%s Name: %s ReleaseDate: %s Duration: %s \n",
					p.getAlbumId(), p.getName(), p.getReleaseDate().toString(), p.getDuration());
		}
		
		// DELETE
		albumsDao.delete(album2);

	}
	
}
