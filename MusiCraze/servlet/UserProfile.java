package musicraze.servlet;

import musicraze.model.*;
import java.io.IOException;
import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserProfile")
public class UserProfile extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    Users user = (Users) (req.getSession().getAttribute("user"));
    if (user == null) {
      res.sendRedirect("UserLogin");
      return;
    }
    req.getRequestDispatcher("/UserProfile.jsp").forward(req, res);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    doGet(req, res);
  }
}
