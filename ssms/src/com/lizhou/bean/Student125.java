package com.lizhou.bean;

import java.util.List;

/**
 * 学生类
 * @author bojiangzhou
 *
 */
public class Student125 {
	
	private int id; //ID
	
	public String getClazzName() {
		return clazzName;
	}



	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}



	public String getGradeName() {
		return gradeName;
	}



	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}



	private String number; //学号
	
	private String name; //姓名
	
	private String sex; //性别
	
	private String phone; //电话
	
	private String qq; //QQ
	
	private Clazz clazz; //班级
	
	private int clazzid; //班级ID
	
	private String clazzName;//班级名称
	
	private Grade grade; //年级
	
	private int gradeid; //年级ID
	
	private String gradeName;//年级名称
	
	
	public Student125() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Student125(int id, String number, String name, String sex,
			String phone, String qq, Clazz clazz, int clazzid,
			String clazzName, Grade grade, int gradeid, String gradeName,
			List<EScore> scoreList) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.sex = sex;
		this.phone = phone;
		this.qq = qq;
		this.clazz = clazz;
		this.clazzid = clazzid;
		this.clazzName = clazzName;
		this.grade = grade;
		this.gradeid = gradeid;
		this.gradeName = gradeName;
		this.scoreList = scoreList;
	}



	private List<EScore> scoreList; //成绩集合
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public int getClazzid() {
		return clazzid;
	}

	public void setClazzid(int clazzid) {
		Clazz clazz = new Clazz();
		clazz.setId(clazzid);
		this.clazz = clazz;
		this.clazzid = clazzid;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public int getGradeid() {
		return gradeid;
	}

	public void setGradeid(int gradeid) {
		Grade grade = new Grade();
		grade.setId(gradeid);
		this.grade = grade;
		this.gradeid = gradeid;
	}

}
