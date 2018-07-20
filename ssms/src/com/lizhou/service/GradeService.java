package com.lizhou.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Course;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Student;
import com.lizhou.dao.impl.BaseDaoImpl;
import com.lizhou.dao.inter.BaseDaoInter;
import com.lizhou.tools.MysqlTool;
import com.lizhou.tools.StringTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 骞寸骇鏈嶅姟灞�
 * @author bojiangzhou
 *
 */
public class GradeService {
	
	BaseDaoInter dao = new BaseDaoImpl();
	
	/**
	 * 鑾峰彇鎵�湁骞寸骇
	 * @param clazz 鏄惁鑾峰彇骞寸骇涓嬭绋�
	 * @return JSON鏍煎紡鐨勫勾绾�
	 */
	@Test
	public String getGradeList(String course){
		//鑾峰彇鏁版嵁
		List<Object> list = dao.getList(Grade.class, "SELECT * FROM grade");
		JsonConfig config = new JsonConfig();
		if(StringTool.isEmpty(course)){ //濡傛灉娌℃湁浼犺繘course鍙傛暟锛屽垯杩斿洖骞寸骇鐨刬d鍜屽悕绉板嵆鍙�
			config.setExcludes(new String[]{"clazzList", "clazzList", "studentList"});
		} else{ //涓嶄负绌洪渶瑕佸啀灏嗗勾绾т笅鐨勭彮绾ц幏鍙栧嚭鏉�
			//鑾峰彇璇剧▼
			for(Object obj : list){
				Grade grade = (Grade) obj;
				List<Object> gradeCourse = dao.getList(Course.class, 
						"SELECT c.* FROM grade_course gc, course c WHERE gc.gradeid=? AND gc.courseid=c.id", 
						new Object[]{grade.getId()});
				
				List<Course> courseList = new LinkedList<Course>();
				for(Object gc : gradeCourse){
					Course c = (Course) gc;
					courseList.add(c);
				}
				grade.setCourseList(courseList);
			}
			config.setExcludes(new String[]{"clazzList", "studentList"});
		}
		
		//json鍖�
        String result = JSONArray.fromObject(list, config).toString();
        
        return result;
	}

	/**
	 * 添加年级
	 * @param name 年级的名称
	 * @param clazzids 课程id列表
	 */
	public void addGrade(String name, String[] clazzids) {
		// 先向年级表中添加一条记录，并返回添加的主键
		int key = dao.insertReturnKeys("INSERT INTO grade(name) value(?)", new Object[]{name});
		
		String sql = "INSERT INTO grade_course(gradeid, courseid) value(?, ?)";
		// 
		Object[][] params = new Object[clazzids.length][2];
		for(int i = 0;i < clazzids.length;i++){
			params[i][0] = key;
			params[i][1] = Integer.parseInt(clazzids[i]);
		}
		dao.insertBatch(sql, params);
	}

	/**
	 * 删除年级
	 * @param gradeid 年级id
	 * @throws Exception 
	 */
	public void deleteGrade(int gradeid) throws Exception {
		// 建立数据库连接
		Connection conn = MysqlTool.getConnection();
		try {
			// 开启事务
			MysqlTool.startTransaction();
			// 删除考试成绩表中与该年级有关的考试成绩
			dao.deleteTransaction(conn, "DELETE FROM escore WHERE gradeid=?", new Object[]{gradeid});
			// 删除该年级的有关考试
			dao.deleteTransaction(conn, "DELETE FROM exam WHERE gradeid=?", new Object[]{gradeid});
			// 删除与该年级的课程有关的教师关联信息
			dao.deleteTransaction(conn, "DELETE FROM clazz_course_teacher WHERE gradeid=?", new Object[]{gradeid});
			// 删除班级课程的有关信息
			dao.deleteTransaction(conn, "DELETE FROM grade_course WHERE gradeid=?", new Object[]{gradeid});
			// 查询出该年级的学生的学号
			List<Object> list = dao.getList(Student.class, "SELECT number FROM student WHERE gradeid=?",  new Object[]{gradeid});
			if(list.size() > 0){
				Object[] param = new Object[list.size()];
				for(int i = 0;i < list.size();i++){
					Student stu = (Student) list.get(i);
					param[i] = stu.getNumber();
				}
				// 删除该年级的用户
				String sql = "DELETE FROM user WHERE account IN ("+StringTool.getMark(list.size())+")";
				dao.deleteTransaction(conn, sql, param);
				// 删除该年级的
				dao.deleteTransaction(conn, "DELETE FROM student WHERE gradeid=?", new Object[]{gradeid});
			}
			// 删除该年级下的相关班级
			dao.deleteTransaction(conn, "DELETE FROM clazz WHERE gradeid=?",  new Object[]{gradeid});
			// 上面的与年级相关的表都删除了之后，最后删除年级表中的相关信息
			dao.deleteTransaction(conn, "DELETE FROM grade WHERE id=?",  new Object[]{gradeid});
			// 提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			// 发生异常就回滚
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}
	
}
