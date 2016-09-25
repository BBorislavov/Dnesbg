package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DnesbgDAO.UserDAO;
import exceptions.ConnectionException;
import exceptions.UserException;
import model.User;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/jsp/registration.html").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if ((username == null) && (password == null) && (email == null)) {
			response.sendRedirect("./registration");
		} else {
			try {
				DnesbgDAO.UserDAO.registerUser(new User(email, username, password));
			} catch (UserException e) {
				System.out.println(e.getMessage());
				return;
			} catch (ConnectionException c) {
				System.out.println(c.getMessage());
				return;
			}
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("username", username);
			httpSession.setMaxInactiveInterval(15 * 60);
			response.sendRedirect("./successreg");
		}
	}

}
