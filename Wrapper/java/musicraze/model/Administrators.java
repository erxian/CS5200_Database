package musicraze.model;

public class Administrators extends Persons {

  public Administrators(String userName) {
    super(userName);
  }

  public Administrators(String userName, String password, String firstName, String lastName,
      String email) {
    super(userName, password, firstName, lastName, email);
  }

  public Administrators(Persons person) {
    super(person.getUserName(), person.getPassword(), person.getFirstName(), person.getLastName(),
        person.getEmail());
  }
}
