package com.topnews.models;

import com.topnews.exceptions.CategoryException;
import com.topnews.validators.AbstractValidator;

public class Category implements ICategory {

	private String subcategory;
	private String name;

	public Category(String name, String subcategory) throws CategoryException {
		setSubcategory(subcategory);
		setName(name);
	}

	public Category() {
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) throws CategoryException {
		if (subcategory != null) {
			this.subcategory = subcategory;
		} else {
			this.subcategory = AbstractValidator.INVALID_SUBCATEGORY;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	



}
