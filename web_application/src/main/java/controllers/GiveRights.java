package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ConnectionException;
import exceptions.UserException;

@WebServlet("/successfullRights")
public class GiveRights extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");

		if (username == null) {
			response.sendRedirect("./NotExist");
		} else {
			try {
				DnesbgDAO.AdminDAO.giveRights("username");
			} catch (ConnectionException e) {
				System.out.println(e.getMessage());
				return;
			} catch (UserException e) {
				System.out.println(e.getMessage());
				return;
			}
			request.getRequestDispatcher("/WEB-INF/jsp/successfullyGiveRights.jsp").forward(request, response);
		}
	}
}
