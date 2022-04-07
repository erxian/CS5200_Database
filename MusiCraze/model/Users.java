package musicraze.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Users extends Persons {

  private String avatar;
  private String bio;
  private Date bornDate;
  private Instant joinDate;


  public Users(String userName) {
    super(userName);
  }

  public Users(String userName, String password, String firstName, String lastName, String email,
      String avatar, String bio, Date bornDate, Instant joinDate) {
    super(userName, password, firstName, lastName, email);
    this.avatar = avatar;
    this.bio = bio;
    this.bornDate = bornDate;
    this.joinDate = joinDate;
  }

  public Users(Persons person, String avatar, String bio, Date bornDate, Instant joinDate) {
    super(person.getUserName(), person.getPassword(), person.getFirstName(), person.getLastName(),
        person.getEmail());
    this.avatar = avatar;
    this.bio = bio;
    this.bornDate = bornDate;
    this.joinDate = joinDate;
  }

  public String getAvatar() {
    return this.avatar;
  }

  public String getBio() {
    return this.bio;
  }

  public Date getBornDate() {
    return this.bornDate;
  }

  public String getBornDateStr() {
    return new SimpleDateFormat("yyyy-MM-dd").format(this.bornDate);
  }

  public Instant getJoinDate() {
    return this.joinDate;
  }

  public String getJoinDateStr() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("UTC"))
        .format(this.joinDate);
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public void setBornDate(Date bornDate) {
    this.bornDate = bornDate;
  }

  public void setJoinDate(Instant joinDate) {
    this.joinDate = joinDate;
  }
}
