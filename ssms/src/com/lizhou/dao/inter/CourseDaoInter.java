package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Course;

/**
 * @author 赵学成
 */
public interface CourseDaoInter extends BaseDaoInter {

	/**
	 * 获取所有的班级列表
	 * @param courseName 课程名称
	 * @return
	 */
	public List<Course> getAllCourseList(String courseName);
	
	/**
	 * 获取指定班级下的所有课程
	 * @param clazzid
	 * @return
	 */
	public List<Course> getCourseListByClazzId(Integer clazzid);
	
	/**
	 * 添加课程
	 * @param course
	 * @return
	 */
	public void addCourse(Course course);
	
	/**
	 * 删除课程
	 * @param id
	 */
	public void delCourse(Integer id);
	
}
