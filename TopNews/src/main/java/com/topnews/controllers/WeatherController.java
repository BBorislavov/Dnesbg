package com.topnews.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.dataLoad.DataLoader;

@Controller
public class WeatherController {

	@RequestMapping(value = "/Weather", method = RequestMethod.GET)
	public String homePage(HttpSession httpSession, Model model) {
		try {
			DataLoader.LoadSiteData(httpSession, model);
			return "weather";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}
}
