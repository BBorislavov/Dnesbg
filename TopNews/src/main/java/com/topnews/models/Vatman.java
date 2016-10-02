package com.topnews.models;

public class Vatman {
	private String name;
	private int broiCigarki;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Vatman [name=" + name + ", broiCigarki=" + broiCigarki + "]";
	}
	public Vatman(String name, int broiCigarki) {
		super();
		this.name = name;
		this.broiCigarki = broiCigarki;
	}
	public Vatman() {
	}
	public int getBroiCigarki() {
		return broiCigarki;
	}
	public void setBroiCigarki(int broiCigarki) {
		this.broiCigarki = broiCigarki;
	}
	
	
}
