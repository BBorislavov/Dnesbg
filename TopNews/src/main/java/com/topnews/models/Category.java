package com.topnews.models;

import java.util.concurrent.atomic.AtomicInteger;

import com.topnews.exceptions.CategoryException;
import com.topnews.exceptions.NameException;

public class Category {

	private String name;
	private AtomicInteger subcategoryId = new AtomicInteger(0);
	private AtomicInteger categoryId = new AtomicInteger(0);

	public Category(String name, int subcategoryId, int categoryId) throws NameException, CategoryException {
		setName(name);
		setSubcategoryId(subcategoryId);
		setCategoryId(categoryId);
	}

	public Category() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws NameException {
		if ((name != null) && (!name.trim().isEmpty())) {
			this.name = name;
		} else {
			throw new NameException("Incorrect name of category");
		}
	}

	public int getSubcategoryId() {
		return subcategoryId.get();
	}

	public void setSubcategoryId(int subcategoryId) throws CategoryException {
		if (subcategoryId > 0) {
			this.subcategoryId.getAndSet(subcategoryId);
		} else {
			throw new CategoryException("Invalid subcategory");
		}
	}

	public int getCategoryId() {
		return categoryId.get();
	}

	public void setCategoryId(int categoryId) throws CategoryException {
		if (categoryId > 0) {
			this.categoryId.getAndSet(categoryId);
		} else {
			throw new CategoryException("Invalid category");
		}
	}
}
