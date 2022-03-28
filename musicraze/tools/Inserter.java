package musicraze.tools;

import musicraze.dal.*;
import musicraze.model.*;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Inserter {

  public static void main(String[] args) throws Exception {

    PersonsDao personsDao = PersonsDao.getInstance();
    UsersDao usersDao = UsersDao.getInstance();
    AdministratorsDao administratorsDao = AdministratorsDao.getInstance();
    
    // Test PersonsDao.create()
    Persons person1 = personsDao.create(new Persons("UserName1", "Password1", "FirstName1", "LastName1", "Email1"));
    
    // Test PersonsDao.getPersonByUserName()
    assertNotNull(personsDao.getPersonByUserName("UserName1"));
    
    // Test PersonsDao.updatePassword()
    person1 = personsDao.updatePassword(person1, "newPassword");
    assertEquals("newPassword", personsDao.getPersonByUserName("UserName1").getPassword());
    
    // Test PersonsDao.updateFirstName()
    person1 = personsDao.updateFirstName(person1, "newFirstName");
    assertEquals("newFirstName", personsDao.getPersonByUserName("UserName1").getFirstName());
    
    // Test PersonsDao.updateLastName()
    person1 = personsDao.updateLastName(person1, "newLastName");
    assertEquals("newLastName", personsDao.getPersonByUserName("UserName1").getLastName());
    
    // Test PersonsDao.updateEmail()
    person1 = personsDao.updateEmail(person1, "newEmail");
    assertEquals("newEmail", personsDao.getPersonByUserName("UserName1").getEmail());
    
    // Test PersonsDao.delete()
    person1 = personsDao.delete(person1);
    assertNull(personsDao.getPersonByUserName("UserName1"));
    
    // Test UsersDao.create()
    Date bornDate2 = new SimpleDateFormat("yyyy-MM-dd").parse("1985-12-05");
    Instant joinDate2 = Instant.parse("2022-03-27T12:30:00Z");
    Users user2 = usersDao.create(new Users("UserName2", "Password2", "FirstName2", "LastName2", "Email2", "Avatar2", "Bio2", bornDate2, joinDate2));
    
    // Test UsersDao.getUserByUserName()
    assertNotNull(usersDao.getUserByUserName("UserName2"));
    
    // Test UsersDao.updateAvatar()
    user2 = usersDao.updateAvatar(user2, "newAvatar");
    assertEquals("newAvatar", usersDao.getUserByUserName("UserName2").getAvatar());
    
    // Test UsersDao.updateBio()
    user2 = usersDao.updateBio(user2, "newBio");
    assertEquals("newBio", usersDao.getUserByUserName("UserName2").getBio());
    
    // Test UsersDao.updateBornDate()
    Date newBornDate = new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-14");
    user2 = usersDao.updateBornDate(user2, newBornDate);
    assertEquals(newBornDate, usersDao.getUserByUserName("UserName2").getBornDate());
    
    // Test UsersDao.delete()
    user2 = usersDao.delete(user2);
    assertNull(usersDao.getUserByUserName("UserName2"));
    
    // Test AdministratorsDao.create()
    Administrators administrator3 = administratorsDao.create(new Administrators("UserName3", "Password3", "FirstName3", "LastName3", "Email3"));
    
    // Test AdministratorsDao.getAdministratorByUserName()
    assertNotNull(administratorsDao.getAdministratorByUserName("UserName3"));
    
    // Test AdministratorsDao.delete()
    administrator3 = administratorsDao.delete(administrator3);
    assertNull(administratorsDao.getAdministratorByUserName("UserName3"));
  }
}
