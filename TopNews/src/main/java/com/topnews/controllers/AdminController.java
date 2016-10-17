package com.topnews.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.dataLoad.DataLoader;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Category;
import com.topnews.models.User;
import com.topnews.modelsDAO.AdminDAO;
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
					DataLoader.LoadSiteData(httpSession, model);
					return "admin_panel";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
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
						DataLoader.LoadSiteData(httpSession, model);
						model.addAttribute("message", "SuccesAddRights");
						model.addAttribute("nameOfUser", user.getUsername());
						return "addUserRights";
					}
					model.addAttribute("error", "unexistingUser");
					return "addUserRights";
				}
				model.addAttribute("message", "notFoundPage");
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
					DataLoader.LoadSiteData(httpSession, model);
					return "addUserRights";
				} else {
					model.addAttribute("message", "notFoundPage");
					return "forward:/Error";
				}
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "notFoundPage");
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
							DataLoader.LoadSiteData(httpSession, model);
							model.addAttribute("nameOfUser", user.getUsername());
							model.addAttribute("message", "successRemoveRights");
							return "removeUserRights";
						} else {
							model.addAttribute("error", "selfRemoveRights");
							return "removeUserRights";
						}
					} else {
						model.addAttribute("error", "unexistingUser");
						return "removeUserRights";
					}
				}
				model.addAttribute("message", "serverMaintenance");
				return "redirect:/Error";
			}
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (ConnectionException e) {
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (Exception e) {
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
					DataLoader.LoadSiteData(httpSession, model);
					return "removeUserRights";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
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
							DataLoader.LoadSiteData(httpSession, model);
							model.addAttribute("message", "successDelete");
							model.addAttribute("nameOfUser", user.getUsername());
							return "deleteUser";
						}
						model.addAttribute("error", "deleteUnexistingUser");
						return "deleteUser";
					} else {
						model.addAttribute("message", "notFoundPage");
						return "redirect:/Error";
					}
				}
				model.addAttribute("error", "cantDeleteYourself");
				return "deleteUser";
			} else {
				model.addAttribute("message", "notLogged");
				return "forward:/Login";
			}
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
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
					DataLoader.LoadSiteData(httpSession, model);
					return "deleteUser";
				} else {
					model.addAttribute("message", "notFoundPage");
					return "forward:/Error";
				}

			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";

		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Login";
		}

	}
}
