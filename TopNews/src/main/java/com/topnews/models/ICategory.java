package com.topnews.models;

import com.topnews.exceptions.CategoryException;

public interface ICategory {

	String getSubcategory();

	void setSubcategory(String subcategory) throws CategoryException;

	String getName();

	void setName(String name);

}