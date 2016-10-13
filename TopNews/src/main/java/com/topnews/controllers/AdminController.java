package com.topnews.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.Category;
import com.topnews.models.User;
import com.topnews.modelsDAO.AdminDAO;
import com.topnews.modelsDAO.CategoryDAO;
import com.topnews.modelsDAO.EmailDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class AdminController {

	@RequestMapping(value = "/AdminPanel", method = RequestMethod.GET)
	public String showNewsInCategory(Model model, HttpSession httpSession, HttpServletRequest req) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				model.addAttribute("user", httpSession.getAttribute("user"));
				List<String> categories = CategoryDAO.showAllCategories();
				model.addAttribute("categories", categories);
				int unreaded = EmailDAO.numberOfUnreaded();
				int readed = EmailDAO.numberOfReaded();
				model.addAttribute("unreaded", unreaded);
				model.addAttribute("readed", readed);
				return "admin_panel";
			} else {
				return "index";
			}

		} catch (Exception e) {
			return "forward:/Login";
		}
	}

	@RequestMapping(value = "/AddUserRights", method = RequestMethod.POST)
	public String addRights(@ModelAttribute("category") Category category, @ModelAttribute("user") User user,
			Model model, HttpSession httpSession) {
		try {
			User currentlyLoggedUser = (User) httpSession.getAttribute("user");
			if (UserDAO.isAdmin(currentlyLoggedUser)) {
				AdminDAO.giveRights(user.getUsername());
				List<String> categories = CategoryDAO.showAllCategories();
				model.addAttribute("categories", categories);
				model.addAttribute("message", "Successfully added rights to " + user.getUsername());
				return "addUserRights";
			} else {
				model.addAttribute("notAdmin", "You are not admin!");
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pages/404.html";
		}
	}

	@RequestMapping(value = "/AddUserRights", method = RequestMethod.GET)
	public String userToAddRights(Model model) {
		model.addAttribute(new User());
		try {
			List<String> categories = CategoryDAO.showAllCategories();
			model.addAttribute("categories", categories);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		}
		return "addUserRights";
	}

	@RequestMapping(value = "/RemoveUserRights", method = RequestMethod.POST)
	public String removeRights(@ModelAttribute("category") Category category, @ModelAttribute("user") User user,
			Model model, HttpSession httpSession) {
		try {
			User currentlyLoggedUser = (User) httpSession.getAttribute("user");
			if (UserDAO.isAdmin(currentlyLoggedUser)) {
				AdminDAO.removeRights(user.getUsername());
				List<String> categories = CategoryDAO.showAllCategories();
				model.addAttribute("categories", categories);
				model.addAttribute("message", "Successfully removed rights of " + user.getUsername());
				return "removeUserRights";
			} else {
				model.addAttribute("notAdmin", "You are not admin!");
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pages/404.html";
		}
	}

	@RequestMapping(value = "/RemoveUserRights", method = RequestMethod.GET)
	public String userToRemoveRights(Model model) {
		model.addAttribute(new User());
		try {
			List<String> categories = CategoryDAO.showAllCategories();
			model.addAttribute("categories", categories);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		}
		return "removeUserRights";
	}

	@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
	public String UserToDelete(@ModelAttribute("category") Category category, @ModelAttribute("user") User user,
			Model model, HttpSession httpSession) {
		try {
			User currentlyLoggedUser = (User) httpSession.getAttribute("user");
			if (!currentlyLoggedUser.getUsername().equals(user.getUsername())) {
				if (UserDAO.isAdmin(currentlyLoggedUser)) {
					AdminDAO.deleteUser(user.getUsername());
					List<String> categories = CategoryDAO.showAllCategories();
					model.addAttribute("categories", categories);
					model.addAttribute("message", "Successfully deleted " + user.getUsername());
					return "deleteUser";
				} else {
					model.addAttribute("notAdmin", "You are not admin!");
					return "index";
				}
			}
			model.addAttribute("error", "You cannot delete yourself");
			return "deleteUser";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pages/404.html";
		}
	}

	@RequestMapping(value = "/DeleteUser", method = RequestMethod.GET)
	public String deleteUser(Model model) {
		model.addAttribute(new User());
		try {
			List<String> categories = CategoryDAO.showAllCategories();
			model.addAttribute("categories", categories);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		}
		return "deleteUser";
	}
}
