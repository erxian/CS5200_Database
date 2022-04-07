package musicraze.servlet;

import musicraze.dal.*;
import musicraze.model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {

  private UsersDao usersDao;

  @Override
  public void init() throws ServletException {
    this.usersDao = UsersDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    Users user = (Users) (req.getSession().getAttribute("user"));
    if (user == null) {
      res.sendRedirect("UserLogin");
      return;
    }
    req.getRequestDispatcher("/EditProfile.jsp").forward(req, res);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    Users user = (Users) (req.getSession().getAttribute("user"));
    if (user == null) {
      res.sendRedirect("UserLogin");
      return;
    }
    Map<String, String> alerts = new HashMap<>();
    try {
      String firstName = this.validateName(req.getParameter("firstName"));
      String lastName = this.validateName(req.getParameter("lastName"));
      String email = this.validateEmail(req.getParameter("email"));
      String avatar = this.validateAvatar(req.getParameter("avatar"));
      String bio = this.trimString(req.getParameter("bio"));
      Date bornDate = this.validateBornDate(req.getParameter("bornDate"));
      if (firstName == null) {
        alerts.put("firstName", "First name must contain only letters or spaces.");
      }
      if (lastName == null) {
        alerts.put("lastName", "Last name must contain only letters or spaces.");
      }
      if (email == null) {
        alerts.put("email", "Email must be formatted correctly.");
      }
      if (avatar == null) {
        alerts.put("avatar", "Avatar URL must be valid.");
      }
      if (bio.isEmpty()) {
        alerts.put("bio", "Bio cannot be empty.");
      }
      if (bornDate == null) {
        alerts.put("bornDate",
            "Born date must be in format: yyyy-MM-dd. You must be 18+ to use MusiCraze.");
      }
      if (alerts.size() != 0) {
        throw new IllegalArgumentException();
      }
      if (!user.getFirstName().equals(firstName)) {
        user = this.usersDao.updateFirstName(user, firstName);
      }
      if (!user.getLastName().equals(lastName)) {
        user = this.usersDao.updateLastName(user, lastName);
      }
      if (!user.getEmail().equals(email)) {
        user = this.usersDao.updateEmail(user, email);
      }
      if (!user.getAvatar().equals(avatar)) {
        user = this.usersDao.updateAvatar(user, avatar);
      }
      if (!user.getBio().equals(bio)) {
        user = this.usersDao.updateBio(user, bio);
      }
      if (!user.getBornDate().equals(bornDate)) {
        user = this.usersDao.updateBornDate(user, bornDate);
      }
      req.setAttribute("user", user);
      req.getSession().setAttribute("user", user);
      res.sendRedirect("UserProfile");
    } catch (IllegalArgumentException e) {
      req.setAttribute("alerts", alerts);
      req.getRequestDispatcher("/EditProfile.jsp").forward(req, res);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
  }

  private String trimString(String str) {
    return str == null ? "" : str.trim();
  }

  private String validateName(String name) {
    String trimed = this.trimString(name);
    if (trimed.matches("^[ A-Za-z]+$")) {
      return trimed;
    }
    return null;
  }

  private String validateEmail(String email) {
    String trimed = this.trimString(email);
    if (trimed.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      return trimed;
    }
    return null;
  }

  private String validateAvatar(String avatar) {
    String trimed = this.trimString(avatar);
    try {
      if (ImageIO.read(new URL(trimed)) != null) {
        return trimed;
      }
    } catch (Exception e) {
    }
    return null;
  }

  private Date validateBornDate(String bornDate) {
    String trimed = this.trimString(bornDate);
    try {
      if (Period.between(LocalDate.parse(trimed), LocalDate.now()).getYears() >= 18) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(trimed);
      }
    } catch (Exception e) {
    }
    return null;
  }
}
