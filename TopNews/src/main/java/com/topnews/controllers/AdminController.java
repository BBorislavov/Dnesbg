package com.topnews.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Category;
import com.topnews.models.User;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class AdminController {

	@RequestMapping(value = "/AdminPanel", method = RequestMethod.GET)
	public String showNewsInCategory(Model model, HttpSession httpSession, HttpServletRequest req) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				model.addAttribute("name", req.getAttribute("Category"));
				return "admin_panel";
			} else {
				return "redirect:404.html";
			}
		} catch (ConnectionException e) {
		} catch (UserException e) {
			e.printStackTrace();
		}
		return "redirect:404.html";
	}
	
	@RequestMapping(value = "/AdminPanel/Category/{name}", method = RequestMethod.GET)
	public String showNewsInCategory(Model model, HttpSession httpSession) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				
				model.addAttribute(new Category());
				return "ShowCategory";
			} else {
				return "redirect:404.html";
			}
		} catch (ConnectionException e) {
		} catch (UserException e) {
			e.printStackTrace();
		}
		return "redirect:404.html";
	}

}
