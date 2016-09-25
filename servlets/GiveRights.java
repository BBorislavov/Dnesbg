//package servlets;
//
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import exceptions.UserException;
//import model.User;
//
//@WebServlet("/successfullRights")
//public class GiveRights extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//  
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String username = request.getParameter("username");
//
//		if (username == null) {
//			response.sendRedirect("./NotExist");
//		} else {
//			try {
//				DnesbgDAO.UserDAO.registerUser(new User(email, username, password));
//			} catch (UserException e) {
//				System.out.println(e.getMessage());
//				return;
//			}
//			HttpSession httpSession = request.getSession(true);
//			httpSession.setAttribute("username", username);
//			httpSession.setMaxInactiveInterval(15 * 60);
//			response.sendRedirect("./successreg");
//		}
//	}
//
//}
