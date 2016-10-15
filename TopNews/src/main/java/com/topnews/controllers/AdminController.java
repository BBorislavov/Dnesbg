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
import com.topnews.exceptions.UserException;
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
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					model.addAttribute("user", user);
					List<String> categories = CategoryDAO.showAllCategories();
					model.addAttribute("categories", categories);
					int unreaded = EmailDAO.numberOfUnreaded();
					int readed = EmailDAO.numberOfReaded();
					model.addAttribute("unreaded", unreaded);
					model.addAttribute("readed", readed);
					return "admin_panel";
				}
				httpSession.setAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			httpSession.setAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (Exception e) {
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		}
	}

	@RequestMapping(value = "/AddUserRights", method = RequestMethod.POST)
	public String addRights(@ModelAttribute("category") Category category, @ModelAttribute("user") User user,
			Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User currentlyLoggedUser = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(currentlyLoggedUser)) {
					if (UserDAO.isUsernameExisting(user)) {
						AdminDAO.giveRights(user.getUsername());
						List<String> categories = CategoryDAO.showAllCategories();
						httpSession.setAttribute("categories", categories);
						httpSession.setAttribute("message", "SuccesAddRights");
						httpSession.setAttribute("username", user.getUsername());
						httpSession.removeAttribute("error");
						return "addUserRights";
					}
					httpSession.setAttribute("error", "unexistingUser");
					httpSession.removeAttribute("username");
					httpSession.removeAttribute("message");
					return "addUserRights";
				}
				httpSession.setAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		}
	}

	@RequestMapping(value = "/AddUserRights", method = RequestMethod.GET)
	public String userToAddRights(Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					model.addAttribute(new User());
					List<String> categories = CategoryDAO.showAllCategories();
					model.addAttribute("categories", categories);
					httpSession.removeAttribute("username");
					httpSession.removeAttribute("error");
					return "addUserRights";
				} else {
					httpSession.setAttribute("message", "notFoundPage");
					return "forward:/Error";
				}
			}
			httpSession.setAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "notFoundPage");
			return "forward:/Error";
		}

	}

	@RequestMapping(value = "/RemoveUserRights", method = RequestMethod.POST)
	public String removeRights(@ModelAttribute("category") Category category, @ModelAttribute("user") User user,
			Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User currentlyLoggedUser = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(currentlyLoggedUser)) {
					if (UserDAO.isUsernameExisting(user)) {
						if (!currentlyLoggedUser.getUsername().equals(user.getUsername())) {
							AdminDAO.removeRights(user.getUsername());
							List<String> categories = CategoryDAO.showAllCategories();
							model.addAttribute("categories", categories);
							httpSession.setAttribute("username", user.getUsername());
							httpSession.setAttribute("message", "successRemoveRights");
							httpSession.removeAttribute("error");
							return "removeUserRights";
						} else {
							httpSession.setAttribute("error", "selfRemoveRights");
							httpSession.removeAttribute("message");
							httpSession.removeAttribute("username");
							return "removeUserRights";
						}
					} else {
						httpSession.setAttribute("error", "unexistingUser");
						httpSession.removeAttribute("message");
						httpSession.removeAttribute("username");
						return "removeUserRights";
					}
				}
				model.addAttribute("message", "serverMaintenance");
				return "redirect:/Error";
			}
			httpSession.setAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		}
	}

	@RequestMapping(value = "/RemoveUserRights", method = RequestMethod.GET)
	public String userToRemoveRights(Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				model.addAttribute(new User());
				User currentlyLoggedUser = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(currentlyLoggedUser)) {
					List<String> categories = CategoryDAO.showAllCategories();
					model.addAttribute("categories", categories);
					httpSession.removeAttribute("username");
					httpSession.removeAttribute("error");
					httpSession.removeAttribute("message");
					return "removeUserRights";
				}
				httpSession.setAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			httpSession.setAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (ConnectionException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		}
	}

	@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
	public String UserToDelete(@ModelAttribute("category") Category category, @ModelAttribute("user") User user,
			Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User currentlyLoggedUser = (User) httpSession.getAttribute("user");
				if (!currentlyLoggedUser.getUsername().equals(user.getUsername())) {
					if (UserDAO.isAdmin(currentlyLoggedUser)) {
						if (UserDAO.isUsernameExisting(user)) {
							AdminDAO.deleteUser(user.getUsername());
							List<String> categories = CategoryDAO.showAllCategories();

							model.addAttribute("categories", categories);
							model.addAttribute("message", "successDelete");
							model.addAttribute("username", user.getUsername());
							return "deleteUser";
						}
						model.addAttribute("error", "deleteUnexistingUser");
						return "deleteUser";
					} else {
						httpSession.setAttribute("message", "notFoundPage");
						return "redirect:/Error";
					}
				}
				model.addAttribute("error", "cantDeleteYourself");
				return "deleteUser";
			} else {
				httpSession.setAttribute("message", "notLogged");
				return "forward:/Login";
			}
		} catch (UserException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		}
	}

	@RequestMapping(value = "/DeleteUser", method = RequestMethod.GET)
	public String deleteUser(Model model, HttpSession httpSession) {
		try {
			if (httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					model.addAttribute(new User());
					List<String> categories = CategoryDAO.showAllCategories();
					model.addAttribute("categories", categories);
					httpSession.removeAttribute("username");
					httpSession.removeAttribute("error");
					httpSession.removeAttribute("message");
					return "deleteUser";
				} else {
					httpSession.setAttribute("message", "notFoundPage");
					return "forward:/Error";
				}

			}
			httpSession.setAttribute("message", "notFoundPage");
			return "forward:/Error";

		} catch (ConnectionException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "forward:/Login";
		}

	}
}
