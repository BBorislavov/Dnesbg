package com.topnews.controllers;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.topnews.dataLoad.DataLoader;
import com.topnews.exceptions.CommentException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Email;
import com.topnews.models.IEmail;
import com.topnews.models.User;
import com.topnews.modelsDAO.EmailDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class EmailController {

	public static final String LOCATION = "D:\\emails_images\\";
	public static final String SERVER_IMAGES_PATH = "./emails_images/";
	public static final String SERVER_DEFAULT_IMAGE = "./emails_images/alert-001.jpg";

	@RequestMapping(value = "/Alert", method = RequestMethod.POST)
	public String addEmail(@ModelAttribute Email email, @RequestParam("photo") MultipartFile multipartFile, Model model,
			HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				int userId = UserDAO.getUserIdFromDB(user.getUsername());
				String photoUrl = SERVER_DEFAULT_IMAGE;
				if (!multipartFile.isEmpty()) {
					String[] path = multipartFile.getOriginalFilename().split(":\\\\");
					String fileName = path[path.length - 1];
					String username = null;
					if ((String) httpSession.getAttribute("username") != null) {
						username = (String) httpSession.getAttribute("username");
					} else {
						username = "default";
					}
					String location = LOCATION + username + "\\";
					new File(location).mkdir();
					FileCopyUtils.copy(multipartFile.getBytes(), new File(location + fileName));
					photoUrl = SERVER_IMAGES_PATH + username + "/" + fileName;
				}
				if (email.getSubject().equals("Invalid subject") || email.getText().equals("Invalid text")) {
					model.addAttribute("error", "incorrectAlert");
					DataLoader.LoadSiteData(httpSession, model);
					return "email";
				}
				EmailDAO.sendEmail(email, userId, photoUrl);
				model.addAttribute("message", "successAlert");
				DataLoader.LoadSiteData(httpSession, model);
				return "email";

			}
			return "redirect:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/Alert", method = RequestMethod.GET)
	public String showLoggedToSendAlert(Model model, HttpSession httpSession) {
		try {
			if (httpSession.getAttribute("user") != null) {
				model.addAttribute(new Email());
				DataLoader.LoadSiteData(httpSession, model);
				return "email";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}

	}

	@RequestMapping(value = "/UnreadedAlerts", method = RequestMethod.GET)
	public String showUnreadedAlerts(Model model, HttpSession httpSession, HttpServletRequest req) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				boolean isAdmin = UserDAO.isAdmin(user);
				if (isAdmin) {
					List<IEmail> alerts = EmailDAO.showUnreaded();
					model.addAttribute("alerts", alerts);
					DataLoader.LoadSiteData(httpSession, model);
					return "showUnreadedAlerts";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/ReadedAlerts", method = RequestMethod.GET)
	public String showReadedAlerts(Model model, HttpSession httpSession, HttpServletRequest req) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				boolean isAdmin = UserDAO.isAdmin(user);
				if (isAdmin) {
					List<IEmail> alerts = EmailDAO.showReaded();
					model.addAttribute("alerts", alerts);
					DataLoader.LoadSiteData(httpSession, model);
					return "showReadedAlerts";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			httpSession.setAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/Alerts", method = RequestMethod.GET)
	public String showCurrentNews(Model model, HttpSession httpSession, HttpServletRequest request) {
		try {
			if (request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				httpSession.setAttribute("id", id);
				if ((User) httpSession.getAttribute("user") != null) {
					User user = (User) httpSession.getAttribute("user");
					boolean isAdmin = UserDAO.isAdmin(user);
					if (isAdmin) {
						IEmail alert = EmailDAO.showCurrentAlert(id);
						EmailDAO.setReaded(id);
						model.addAttribute("alert", alert);
						DataLoader.LoadSiteData(httpSession, model);
						return "showCurrentAlert";
					}
					model.addAttribute("message", "notFoundPage");
					return "forward:/Error";
				}
				model.addAttribute("message", "notLogged");
				return "forward:/Login";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
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
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/DeleteAlert", method = RequestMethod.GET)
	public String deleteNews(Model model, HttpSession httpSession, HttpServletRequest request) {
		try {
			if (request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				httpSession.setAttribute("id", id);
				if ((User) httpSession.getAttribute("user") != null) {
					User user = (User) httpSession.getAttribute("user");
					if (id != 0) {
						if (UserDAO.isAdmin(user)) {
							EmailDAO.deleteAlert(id);
							String referer = request.getHeader("Referer");
							return "redirect:" + referer;
						}
						model.addAttribute("message", "notFoundPage");
						return "forward:/Error";
					}
					model.addAttribute("message", "notFoundPage");
					return "forward:/Error";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (CommentException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}

}
