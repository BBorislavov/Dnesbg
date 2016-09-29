package UnitTests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import DnesbgDAO.AdminDAO;
import DnesbgDAO.UserDAO;
import exceptions.CommentException;
import exceptions.ConnectionException;
import exceptions.NewsException;
import exceptions.UserException;
import model.News;
import model.User;

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
//		AdminDAO.addSubcategory("Гребане", "Спорт");
//	}
	
//	@Test
//	public void deleteSubcategory() throws ConnectionException, UserException{
//		AdminDAO.removeSubcategory("бадмингтон");
//	}
	
	@Test
	public void addNews() throws ConnectionException, UserException, NewsException{
		AdminDAO.addNews(new News("ЦСКА вече носят оранжеви екипи", "В ЦСКА нямат пари за червени екипи", null), "Футбол");
	}
	
	
	
}
