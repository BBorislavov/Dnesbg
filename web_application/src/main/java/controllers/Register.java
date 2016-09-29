package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.ConnectionException;
import exceptions.UserException;
import model.User;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("jsp/invalidRegister.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if ((username == null) && (password == null) && (email == null)) {
			response.sendRedirect("./Register");
		} else {
			try {
				DnesbgDAO.UserDAO.registerUser(new User(username, password, email));
			} catch (UserException e) {
				String massage = e.getMessage().toString();
				request.setAttribute("massage", massage);
				request.getRequestDispatcher("jsp/invalidRegister.jsp").forward(request, response);
				return;
			} catch (ConnectionException c) {
				System.out.println(c.getMessage());
				return;
			}
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("username", username);
			httpSession.setMaxInactiveInterval(15 * 60);
			response.sendRedirect("./SuccessRegistration");
		}
	}

}
