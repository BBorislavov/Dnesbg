package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			String username = request.getParameter("Username");
			String password = request.getParameter("Password");
			if (username == null || password == null) {
				throw new ServletException("Ivalid parameters");
			}

			if (DnesbgDAO.UserDAO.isUserExisting(username, password)) {
				HttpSession httpSession = request.getSession(true);
				httpSession.setAttribute("username", username);
				httpSession.setMaxInactiveInterval(15 * 60);
				request.getRequestDispatcher("/WEB-INF/jsp/home.html").forward(request, response);
			} else {
				response.sendRedirect("./login");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to log in");
			response.sendRedirect("./login");
		}
	}

}
