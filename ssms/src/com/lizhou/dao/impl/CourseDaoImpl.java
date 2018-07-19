package com.lizhou.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.lizhou.bean.Course;
import com.lizhou.dao.inter.CourseDaoInter;

/**
 * @author 赵学成
 */
public class CourseDaoImpl extends BaseDaoImpl implements CourseDaoInter {

	/**
	 * 查询所有的课程列表(模糊查询)
	 */
	@Override
	public List<Course> getAllCourseList(String courseName) {
		String sql = "SELECT id, name FROM course WHERE name LIKE ?";
		courseName = courseName == null ? "":courseName;
		Object[] param = {"%" + courseName + "%"};
		List<Object> list = this.getList(Course.class, sql, param);
		List<Course> cList = new ArrayList<Course>();
		for (Object obj : list) {
			cList.add((Course) obj);
		}
		return cList;
	}

	@Override
	public void addCourse(Course course) {
		String sql = "INSERT INTO course(name) VALUES(?)";
		Object[] param = {course.getName()};
		this.insert(sql, param);
	}

	@Override
	public void delCourse(Integer id) {
		String sql = "DELETE FROM course WHERE id = ?";
		Object[] param = {id};
		this.delete(sql, param);
	}

	@Override
	public List<Course> getCourseListByClazzId(Integer clazzid) {
		String sql = " SELECT A.id, A.name " +
			" FROM course A, clazz_course_teacher B " +
			" WHERE A.id = B.courseid " +
			" AND B.clazzid = ? ";
		Object[] param = {clazzid};
		List<Object> list = this.getList(Course.class, sql, param);
		List<Course> result = new ArrayList<Course>();
		for (Object obj : list) {
			result.add((Course) obj);
		}
		return result;
	}

}
