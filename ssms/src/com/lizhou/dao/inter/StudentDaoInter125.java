package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Page;
import com.lizhou.bean.Student125;

/**
 * 操作学生的数据层接口
 * @author bojiangzhou
 *
 */
public interface StudentDaoInter125 extends BaseDaoInter {
	
	/**
	 * 获取学生信息，这里需要将学生的班级，年级等信息封装进去
	 * @param sql 要执行的sql语句
	 * @param param 参数
	 * @return
	 */
	public List<Student125> getStudentList(String sql, List<Object> param);
	/**
	 * 从数据库获取学生photo并保存到项目路径下的photo文件夹
	 * @param student
	 * @param realPath
	 */
	public void getBlob(Student125 student,String realPath);
	/**
	 * 获取学生通讯录信息
	 * @param sql
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Object> queryStudentByGradeAndClazz(Student125 student);
	
	/**
	 * 根据学号查询学生信息
	 * @param number
	 * @return
	 */
	public Object getStudentByNumber(String number);
	
	/**
	 * 获取学生列表信息,分页查询
	 */
	public List<Object> queryStudentList(int page,int rows);
	
	/**
	 * 获取学生列表长度，给分页查询构成map使用
	 * @return
	 */
	public int getQueryAllLength();
	
	/**
	 * 添加学生信息
	 * @param student
	 * @return
	 */
	public void insertStudentList(Student125 student);
	
	/**
	 * 查询年级
	 * @return
	 */
	public List<Object> queryGrade();
	
	/**
	 * 查询指定年级下的班级
	 * @param gradeId
	 * @return
	 */
	public List<Object> queryClassInGrade(int gradeId);
	
	/**
	 * 查询指定年级下的学生
	 * @param gradeId
	 * @return
	 */
	public List<Object> queryStudentListByGradeName(String gradeName,int page,int rows);
	//获取原始数据长度
	public int queryStudentListByGradeNameLentgth(String gradeName); 
	
	/**
	 * 根据班级下拉框进行分页查询
	 * @param gradeName
	 * @param className
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Object> queryStudentByGradeNameAndClazzName(String gradeName,String className,int page,int rows);
	//依附上面方法，获取未分页前长度
	public int queryStudentByGradeNameAndClazzNameLength(String gradeName,String className);
	
	/**
	 * 修改学生信息
	 * @param student
	 */
	public void updateStudent(Student125 student);
	/**
	 * 学生列表修改界面根据班级年级名称获取相应id
	 * @param clazzName
	 * @param gradeName
	 * @return
	 */
	public Object getStudentClazzIdAndGradeId(String clazzName,String gradeName);
	
	/**
	 * 根据id删除学生信息，三个表全部删除
	 * @param idArray
	 */
	public void deleteStudentById(int id);
	public void deleteEscoreStudentByStudentId(int studentId);
	public void deleteUserStudentByStudentNumber(String studentNumber);
}
