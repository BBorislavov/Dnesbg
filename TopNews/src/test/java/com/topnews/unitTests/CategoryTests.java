package com.topnews.unitTests;

import org.junit.Test;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.modelsDAO.CategoryDAO;

public class CategoryTests {

	@Test
	public void addSubcategory() throws ConnectionException, UserException{
		CategoryDAO.addCategory("parent category", "category");
	}
	
	@Test
	public void deleteSubcategory() throws ConnectionException, UserException{
		CategoryDAO.deleteCategory("test");
	}

}
