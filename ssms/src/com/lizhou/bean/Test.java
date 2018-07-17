package com.lizhou.bean;

public class Test {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Test(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}
}
