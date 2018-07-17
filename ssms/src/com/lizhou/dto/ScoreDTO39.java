package com.lizhou.dto;

/**
 * 类名称：登记学生成绩时所使用的数据传输对象
 * 全限定性类名: com.lizhou.dto.ScoreDTO
 * @author 曲健磊
 * @date 2018年7月15日下午9:39:22
 * @version V1.0
 */
public class ScoreDTO39 {
	private Integer id; // id唯一
	private Integer examId; // 哪场考试
	private Integer clazzId; // 哪个班
	private Integer courseId; // 哪科
	private Integer stuId; // 学生id
	private String number; // 谁
	private String stuName; // 叫啥名
	private Integer score = 0; // 考多少分
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public Integer getClazzId() {
		return clazzId;
	}
	public void setClazzId(Integer clazzId) {
		this.clazzId = clazzId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
