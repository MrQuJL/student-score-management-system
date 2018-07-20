package com.lizhou.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lizhou.bean.Student;
import com.lizhou.bean.Student125;
import com.lizhou.dao.impl.StudentDaoImpl125;
import com.lizhou.dao.inter.StudentDaoInter125;

public class StudentService125 {
	StudentDaoInter125 studentDao = new StudentDaoImpl125();

	/**
	 * 根据相同班级和年级的学生
	 * 
	 * @param student
	 * @return
	 */
	public String queryStudentByGradeAndClazz(Student125 student) {
		List<Object> list = studentDao.queryStudentByGradeAndClazz(student);
		String jsonArray = JSONArray.fromObject(list).toString();
		return jsonArray;
	}

	/**
	 * 获取学生照片并保存在本地文件
	 * 
	 * @param student
	 * @param realPath
	 * @return
	 */
	public void getBlob(Student125 student, String realPath) {

		studentDao.getBlob(student, realPath);

	}

	/**
	 * 查询学生列表信息，即所有学生,分页查询
	 * 
	 * @return
	 */
	public String queryStudentList(int page, int rows) {
		List<Object> list = studentDao.queryStudentList(page, rows);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", studentDao.getQueryAllLength());
		map.put("rows", list);

		String jsonArray = JSONObject.fromObject(map).toString();
		return jsonArray;
	}
	
	
	public Student125 getStudentByNumber(String number){
		Student125 stu=(Student125)studentDao.getStudentByNumber(number);
		return stu;
	}
	
	/**
	 * 插入学生
	 * 
	 * @param student
	 */
	public void insertStudentList(Student125 student) {
		studentDao.insertStudentList(student);
	}

	/**
	 * 查询年级
	 * 
	 * @return
	 */
	public String queryGrade() {
		List<Object> list = studentDao.queryGrade();
		String jsonArray = JSONArray.fromObject(list).toString();
		return jsonArray;
	}

	/**
	 * 查询指定年级下的班级
	 */
	public String queryClassByGradeId(int gradeId) {
		List<Object> list = studentDao.queryClassInGrade(gradeId);
		String jsonArray = JSONArray.fromObject(list).toString();
		return jsonArray;
	}

	/**
	 * 查询所有学号
	 * 
	 * @return
	 */
	public String[] getNumber() {
		List<Object> list = studentDao.getList(Student125.class, "select * from student");
		Student125[] studentArr = list.toArray(new Student125[0]);
		String[] studentNumberArr = new String[studentArr.length];
		for (int i = 0; i < studentArr.length; i++) {
			studentNumberArr[i] = studentArr[i].getNumber();
		}
		return studentNumberArr;
	}

	/**
	 * 根据年级下拉框查询并分页
	 * 
	 * @param gradeName
	 * @param page
	 * @param rows
	 * @return
	 */
	public String queryStudentListByGradeName(String gradeName, int page, int rows) {
		int length = studentDao.queryStudentListByGradeNameLentgth(gradeName);
		/*
		 * if(page*rows>length){ page=1;这样改的话前台page和后台page两个值不一致，比如第三页显示第一页的学生 }
		 */
		List<Object> list = studentDao.queryStudentListByGradeName(gradeName, page, rows);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("total", length);
		map.put("rows", list);

		String jsonArray = JSONObject.fromObject(map).toString();
		return jsonArray;
	}

	/**
	 * 根据班级下拉框查询并分页
	 * 
	 * @param gradeName
	 * @param className
	 * @param page
	 * @param rows
	 * @return
	 */
	public String queryStudentByGradeNameAndClazzName(String gradeName, String className, int page, int rows) {
		List<Object> list = studentDao.queryStudentByGradeNameAndClazzName(gradeName, className, page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", studentDao.queryStudentByGradeNameAndClazzNameLength(gradeName, className));
		map.put("rows", list);

		String jsonArray = JSONObject.fromObject(map).toString();
		return jsonArray;
	}

	/**
	 * 修改学生信息
	 * 
	 * @param student
	 */
	public void updateStudent(Student125 student) {
		studentDao.updateStudent(student);
	}

	/**
	 * 学生列表修改界面根据班级年级名称获取相应id
	 * 
	 * @param clazzName
	 * @param gradeName
	 * @return
	 */
	public Student125 getStudentClazzIdAndGradeId(String clazzName, String gradeName) {
		Student125 stu = (Student125) studentDao.getStudentClazzIdAndGradeId(clazzName, gradeName);
		return stu;
	}

	/**
	 * 从escore，student，user三表中删除学生信息
	 * 
	 * @return
	 */
	public boolean deleteStudent(int[] idArr, String[] numberArr) {
		boolean flag = true;
		try {
			for (int i = 0; i < idArr.length; i++) {
				studentDao.deleteEscoreStudentByStudentId(idArr[i]);
				studentDao.deleteStudentById(idArr[i]);
				studentDao.deleteUserStudentByStudentNumber(numberArr[i]);
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
