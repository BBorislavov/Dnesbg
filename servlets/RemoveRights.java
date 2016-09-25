package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ConnectionException;

@WebServlet("/successfullRights")
public class RemoveRights extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");

		if (username == null) {
			response.sendRedirect("./NotExist");
		} else {
			try {
				DnesbgDAO.AdminDAO.removeRights("username");
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
			response.sendRedirect("./successfullyRemoveRights");
		}
	}

}
