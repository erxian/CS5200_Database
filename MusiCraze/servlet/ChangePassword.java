package musicraze.servlet;

import musicraze.dal.*;
import musicraze.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {

  protected UsersDao usersDao;

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
    req.getRequestDispatcher("/ChangePassword.jsp").forward(req, res);
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
      String oldPassword = this.trimString(req.getParameter("oldPassword"));
      String newPassword = this.trimString(req.getParameter("newPassword"));
      String confirmPassword = this.trimString(req.getParameter("confirmPassword"));
      if (!user.getPassword().equals(oldPassword)) {
        alerts.put("oldPassword", "Old password does not match.");
      }
      if (newPassword.isEmpty()) {
        alerts.put("newPassword", "New password can not be empty.");
      }
      if (!newPassword.equals(confirmPassword)) {
        alerts.put("confirmPassword", "Confirm password does not match.");
      }
      if (alerts.size() != 0) {
        throw new IllegalArgumentException();
      }
      user = this.usersDao.updatePassword(user, newPassword);
      req.setAttribute("user", user);
      req.getSession().setAttribute("user", user);
      res.sendRedirect("UserProfile");
    } catch (IllegalArgumentException e) {
      req.setAttribute("alerts", alerts);
      req.getRequestDispatcher("/ChangePassword.jsp").forward(req, res);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
  }

  private String trimString(String str) {
    return str == null ? "" : str.trim();
  }
}
