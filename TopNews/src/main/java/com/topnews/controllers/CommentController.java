package com.topnews.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.topnews.exceptions.CommentException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.Comment;
import com.topnews.modelsDAO.CommentDAO;

@RestController
public class CommentController {

	@RequestMapping(value = "/ShowComments", method = RequestMethod.GET)
	public List<Comment> showCurrentNews(Model model, HttpSession httpSession) {
		try {
			int id = (int) httpSession.getAttribute("id");
			List<Comment> comments = CommentDAO.showCommentsUnderNews(id);
			model.addAttribute("comments", comments);
			return comments;
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/AddComment", method = RequestMethod.POST)
	public List<Comment> AddComment(@ModelAttribute("text") String text, Model model, HttpSession httpSession) {
		try {
			int userId = (int) httpSession.getAttribute("userId");
			int newsId = (int) httpSession.getAttribute("id");
			boolean isSucces = CommentDAO.addComment(newsId, userId, text);
			if (isSucces) {
				List<Comment> comments = CommentDAO.showCommentsUnderNews(newsId);
				return comments;
			} else {
				List<Comment> blank = new ArrayList<Comment>();
				return blank;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Comment> blank = new ArrayList<Comment>();
		return blank;

	}

	@RequestMapping(value = "/DeleteComment", method = RequestMethod.DELETE)
	public List<Comment> DeleteComment(@ModelAttribute("id") int id, Model model, HttpSession httpSession) {
		try {
			int newsId = (int) httpSession.getAttribute("id");
			boolean isSucces = CommentDAO.removeComment(id);
			if (isSucces) {
				List<Comment> comments = CommentDAO.showCommentsUnderNews(newsId);
				return comments;
			} else {
				List<Comment> blank = new ArrayList<Comment>();
				return blank;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		} catch (CommentException e) {
			e.printStackTrace();
		}
		List<Comment> blank = new ArrayList<Comment>();
		return blank;

	}
}
