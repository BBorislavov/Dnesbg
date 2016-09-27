package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./Home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (username == null || password == null) {
				throw new ServletException("Ivalid parameters");
			}

			if (DnesbgDAO.UserDAO.isUserExisting(username, password)) {
				if (DnesbgDAO.UserDAO.isAdmin(username)) {
					HttpSession httpSession = request.getSession(false);
					httpSession.setMaxInactiveInterval(5);
					httpSession.setAttribute("username", username);
					httpSession.setMaxInactiveInterval(15 * 60);
					request.getRequestDispatcher("jsp/admin.jsp").forward(request, response);
				} else {
					HttpSession httpSession = request.getSession(false);
					httpSession.setAttribute("username", username);
					httpSession.setMaxInactiveInterval(15 * 60);
					request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
				}
			} else {
				response.sendRedirect("./Login");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to log in");
			response.sendRedirect("./Login");
		}
	}

}
