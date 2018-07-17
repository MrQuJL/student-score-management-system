package com.lizhou.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * 骞寸骇绫�
 * @author bojiangzhou
 *
 */
public class Grade {
	
	private int id; //ID
	
	private String name; //鍚嶇О
	
	private List<Clazz> clazzList = new LinkedList<Clazz>(); //骞寸骇涓嬬殑鐝骇鍒楄〃
	
	private List<Course> courseList = new LinkedList<Course>(); //鏈勾绾х殑璇剧▼
	
	private List<Student> studentList = new LinkedList<Student>(); //骞寸骇涓嬬殑瀛�

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

	public List<Clazz> getClazzList() {
		return clazzList;
	}

	public void setClazzList(List<Clazz> clazzList) {
		this.clazzList = clazzList;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}
	
}
