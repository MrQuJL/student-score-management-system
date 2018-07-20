package com.lizhou.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.lizhou.bean.Course;
import com.lizhou.bean.Student;
import com.lizhou.dao.impl.CourseDaoImpl;
import com.lizhou.dao.inter.CourseDaoInter;
import com.lizhou.tools.MysqlTool;
import com.lizhou.tools.StringTool;

/**
 * @author 赵学成
 */
public class CourseService {
	
	CourseDaoInter dao = new CourseDaoImpl();
	
	/**
	 * 获取所有的课程信息(json格式字符串)
	 * @param page 第几页
	 * @param rows 每页多少条
	 * @param courseName 课程名称
	 * @return
	 */
	public String getCourseList(int page, int rows, String courseName){
		//获取数据
		int total = 0;
		List<Course> list = dao.getAllCourseList(courseName);
		total = list.size();
		//json化
//		JsonConfig config = new JsonConfig();
//		config.setExcludes(new String[]{"grade", "studentList"});
		int fromIndex = (page - 1) * rows;
		int toIndex = page * rows;
		if (toIndex > list.size()) {
			toIndex = list.size();
		}
		
		list = list.subList(fromIndex, toIndex);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", total);
		jsonMap.put("rows", list);
		
        String result = JSONObject.fromObject(jsonMap).toString();
        return result;
	}
	
	/**
	 * 查询出所有的课程列表(json字符串只有id和name)
	 * @return
	 */
	public String getAllCourseListForGrade(){
		//获取数据
		List<Course> list = dao.getAllCourseList(null);
		
		String result = JSONArray.toJSONString(list);// fromObject(list).toString();
		return result;
	}
	
	/**
	 * 根据班级id获取课程信息
	 * @param clazzid
	 * @return
	 */
	public String getCListByClazzid(int clazzid) {
		List<Course> list = dao.getCourseListByClazzId(clazzid);
		String result = JSONArray.toJSONString(list);
        return result;
	}
	
	/**
	 * 添加课程
	 * @param name
	 */
	public void addCourse(Course course) {
		dao.addCourse(course);
	}
	
	/**
	 * 删除课程
	 * @param id
	 */
	public void delCourse(Integer id) {
		dao.delCourse(id);;
	}
	
	/**
	 * 
	 * @param clazzid
	 * @throws Exception 
	 */
	@Deprecated
	public void deleteClazz(int clazzid) throws Exception {
		//获取连接
		Connection conn = MysqlTool.getConnection();
		try {
			//开启事务
			MysqlTool.startTransaction();
			//删除成绩表
			dao.deleteTransaction(conn, "DELETE FROM escore WHERE clazzid=?", new Object[]{clazzid});
			//删除考试记录
			dao.deleteTransaction(conn, "DELETE FROM exam WHERE clazzid=?", new Object[]{clazzid});
			//删除用户
			List<Object> list = dao.getList(Student.class, "SELECT number FROM student WHERE clazzid=?",  new Object[]{clazzid});
			if(list.size() > 0){
				Object[] param = new Object[list.size()];
				for(int i = 0;i < list.size();i++){
					Student stu = (Student) list.get(i);
					param[i] = stu.getNumber();
				}
				String sql = "DELETE FROM user WHERE account IN ("+StringTool.getMark(list.size())+")";
				dao.deleteTransaction(conn, sql, param);
				//删除学生
				dao.deleteTransaction(conn, "DELETE FROM student WHERE clazzid=?", new Object[]{clazzid});
			}
			//删除班级的课程和老师的关联
			dao.deleteTransaction(conn, "DELETE FROM clazz_course_teacher WHERE clazzid=?", new Object[]{clazzid});
			//最后删除班级
			dao.deleteTransaction(conn, "DELETE FROM clazz WHERE id=?",  new Object[]{clazzid});
			
			//提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			//回滚事务
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}
	
}
