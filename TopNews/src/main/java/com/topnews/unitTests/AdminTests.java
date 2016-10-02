package com.topnews.unitTests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import com.topnews.modelsDAO.AdminDAO;
import com.topnews.modelsDAO.UserDAO;
import com.topnews.exceptions.CommentException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.News;
import com.topnews.models.User;

public class AdminTests {

//	@Test
//	public void giveRights() throws ConnectionException{
//		AdminDAO.giveRights("mesibg2");
//	}
	
	
//	@Test
//	public void removeRights() throws ConnectionException{
//		AdminDAO.removeRights("mesibg2");
//	}

//	@Test
//	public void deleteUser() throws ConnectionException{
//		AdminDAO.deleteUser("admin");
//	}
	
//	@Test
//	public void addSubcategory() throws ConnectionException, UserException{
//		AdminDAO.addSubcategory("�������", "�����");
//	}
	
//	@Test
//	public void deleteSubcategory() throws ConnectionException, UserException{
//		AdminDAO.removeSubcategory("����������");
//	}
	
	@Test
	public void addNews() throws ConnectionException, UserException, NewsException{
		AdminDAO.addNews(new News("��������", "����� �����", "http://novavarna.net/wp-content/uploads/2015/03/11004572_10153723979534546_6475081640795663185_o-Small.jpg"), "������");
	}
	
	
	
}
