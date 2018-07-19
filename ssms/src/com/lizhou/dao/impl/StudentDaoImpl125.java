package com.lizhou.dao.impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Student;
import com.lizhou.bean.Student125;
import com.lizhou.dao.inter.StudentDaoInter125;
import com.lizhou.tools.MysqlTool;

public class StudentDaoImpl125 extends BaseDaoImpl implements StudentDaoInter125 {
	/**
	 * 查询所有同班同年级学生
	 */
	@Override
	public List<Object> queryStudentByGradeAndClazz(Student125 student) {
		String sql ="SELECT * FROM (SELECT student.id,student.number,student.`name`,student.sex,student.phone,student.qq,clazz.`name` as clazzName, grade.`name` gradeName FROM student LEFT OUTER JOIN grade ON student.gradeid=grade.id LEFT OUTER JOIN clazz ON student.clazzid=clazz.id) as studentInfo WHERE clazzName=? AND gradeName=? ORDER BY id;";
		Object[] params = { student.getClazzName(), student.getGradeName() };
		return this.getList(Student125.class, sql, params);
	}
	
	/**
	 * 得到同班同年级学生照片,并按照学号命名
	 */
	public void getBlob(Student125 student,String realPath){
		   Connection con = null;
	       PreparedStatement ps = null;
	       ResultSet rs = null;
	       try {
	             con = MysqlTool.getConnection();
	     		 String sql="SELECT * FROM (SELECT student.id,student.number,student.photo,clazz.`name` as clazzName,grade.`name` as gradeName FROM student  LEFT OUTER JOIN grade ON student.gradeid=grade.id  LEFT OUTER JOIN clazz ON student.clazzid=clazz.id) as studentInfo  WHERE clazzName=? AND gradeName=? order by id";	      
	     		 ps = con.prepareStatement(sql);
	     		 ps.setString(1, student.getClazzName());
	     		 ps.setString(2, student.getGradeName());
	             rs = ps.executeQuery();
	             while(rs.next()){
	                    String studentNumber = rs.getString(2);
	                    String path="";
	                    Blob picture = rs.getBlob(3);//得到Blob对象
	                    //开始读入文件
	                    if(picture!=null){
		                    InputStream in = picture.getBinaryStream();
		                    path= realPath+"photo\\"+studentNumber+".jpeg";
		                    OutputStream out = new FileOutputStream(path);
		                    byte[] buffer = new byte[1024];
		                    int len = 0;
		                    while((len = in.read(buffer)) != -1){
		                           out.write(buffer, 0, len);
		                    }
	                    }
	             }
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }finally{	    
	    	   MysqlTool.close(con);
	    	   MysqlTool.close(ps);
	    	   MysqlTool.close(rs);
	       }

	}
	
	@Override
	public List<Student125> getStudentList(String sql, List<Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 分页查询
	 * 查询学生列表信息
	 */
	@Override
	public List<Object> queryStudentList(int page,int rows) {
		String sql="SELECT student.id,student.number,student.`name`,student.sex,student.phone,student.qq,clazz.`name` as clazzName,grade.`name` as gradeName,student.gradeid,student.clazzid FROM student  LEFT OUTER JOIN grade ON student.gradeid=grade.id   LEFT OUTER JOIN clazz ON student.clazzid=clazz.id order by id";
		List<Object> list=this.getList(Student125.class, sql);
		if (page * rows < list.size()) {
			return list.subList((page - 1) * rows, page * rows);
		}else{
			return list.subList((page - 1) * rows, list.size());
		}
	}
	/**
	 * 查询全部学生，返回集合长度，给分页用
	 * @return
	 */
	@Override
	public int getQueryAllLength(){
		String sql="SELECT student.id,student.number,student.`name`,student.sex,student.phone,student.qq,clazz.`name` as clazzName,grade.`name` as gradeName FROM student  LEFT OUTER JOIN grade ON student.gradeid=grade.id   LEFT OUTER JOIN clazz ON student.clazzid=clazz.id";
		List<Object> list=this.getList(Student125.class, sql);
		return list.size();
	}
	
	/**
	 * 添加学生信息
	 * 先插入年级，获取主键
	 * 再插入班级信息，获取主键
	 * 最后插入学生信息
	 */
	@Override
	public void insertStudentList(Student125 student) {
		String sql="INSERT INTO student(number,name,sex,phone,qq,clazzid,gradeid) values(?,?,?,?,?,?,?)";
		Object[] param={student.getNumber(),student.getName(),student.getSex(),student.getPhone(),student.getQq(),student.getClazzid(),student.getGradeid()};
		this.insert(sql, param);
	}
	
	/**
	 * 查询年级
	 */
	@Override
	public List<Object> queryGrade() {
		String sql="select * from grade";
		return this.getList(Grade.class, sql);
	}
	
	/**
	 * 查询指定年级下的班级
	 */
	@Override
	public List<Object> queryClassInGrade(int gradeId) {
		String sql="select * from clazz where gradeid=?";
		Object[] param={gradeId};
		return this.getList(Clazz.class, sql, param);
	}
	
	/**
	 * 查询指定年级下的学生信息
	 */
	@Override
	public List<Object> queryStudentListByGradeName(String gradeName,int page,int rows) {
		String sql="SELECT * FROM (SELECT student.id,student.number,student.`name`,student.sex,student.phone,student.qq,clazz.`name` as clazzName,grade.`name` as gradeName,student.gradeid,student.clazzid FROM student  LEFT OUTER JOIN grade ON student.gradeid=grade.id LEFT OUTER JOIN clazz ON student.clazzid=clazz.id) studenTable WHERE gradeName=? order by id";
		Object[] param={gradeName};
		List<Object> list=this.getList(Student125.class, sql, param);
		if (page * rows < list.size()) {
			return list.subList((page - 1) * rows, page * rows);
		}else{
			return list.subList((page - 1) * rows, list.size());
		}
	}
	
	@Override
	public int queryStudentListByGradeNameLentgth(String gradeName) {
		// 获得原始数据长度
		String sql="SELECT * FROM (SELECT student.id,student.number,student.`name`,student.sex,student.phone,student.qq,clazz.`name` as clazzName,grade.`name` as gradeName FROM student  LEFT OUTER JOIN grade ON student.gradeid=grade.id LEFT OUTER JOIN clazz ON student.clazzid=clazz.id) studenTable WHERE gradeName=?";
		Object[] param={gradeName};
		List<Object> list=this.getList(Student125.class, sql, param);
		return list.size();
	}
	/**
	 * 初始界面二级联动查询学生信息
	 */
	@Override
	public List<Object> queryStudentByGradeNameAndClazzName(String gradeName,
			String className, int page, int rows) {
		String sql ="SELECT * FROM (SELECT student.id,student.number,student.`name`,student.sex,student.phone,student.qq,clazz.`name` as clazzName, grade.`name` gradeName,student.gradeid,student.clazzid FROM student LEFT OUTER JOIN grade ON student.gradeid=grade.id LEFT OUTER JOIN clazz ON student.clazzid=clazz.id) as studentInfo WHERE clazzName=? AND gradeName=? ORDER BY id";
		Object[] params = { className,gradeName};
		List<Object> list=this.getList(Student125.class, sql, params);
		if (page * rows < list.size()) {
			return list.subList((page - 1) * rows, page * rows);
		}else{
			return list.subList((page - 1) * rows, list.size());
		}
	}

	@Override
	public int queryStudentByGradeNameAndClazzNameLength(String gradeName,
			String className) {
		String sql ="SELECT * FROM (SELECT student.id,student.number,student.`name`,student.sex,student.phone,student.qq,clazz.`name` as clazzName, grade.`name` gradeName FROM student LEFT OUTER JOIN grade ON student.gradeid=grade.id LEFT OUTER JOIN clazz ON student.clazzid=clazz.id) as studentInfo WHERE clazzName=? AND gradeName=? ";
		Object[] params = { className,gradeName};
		List<Object> list=this.getList(Student125.class, sql, params);
		return list.size();
	}
	
	/**
	 * 修改学生信息
	 */
	@Override
	public void updateStudent(Student125 student) {
		String sql="UPDATE student SET number=?,name=?,sex=?,phone=?,qq=?,clazzid=?,gradeid=? WHERE id=?";
		Object[] param={student.getNumber(),student.getName(),student.getSex(),student.getPhone(),student.getQq(),student.getClazzid(),student.getGradeid(),student.getId()};
		this.update(sql, param);
	}

	/**
	 * 学生列表修改界面根据班级年级名称获取相应id
	 * @param clazzName
	 * @param gradeName
	 * @return
	 */
	@Override
	public Object getStudentClazzIdAndGradeId(String clazzName, String gradeName) {
		String sql="SELECT clazzid,gradeid FROM( SELECT clazz.id AS clazzid,clazz.gradeid,clazz.`name` AS clazzName,grade.`name` as gradeName  FROM clazz LEFT OUTER JOIN grade ON clazz.gradeid=grade.id ) AS gradeAndClazzInfo  WHERE clazzName=? AND gradeName=?";
		Object[] param={clazzName,gradeName};
		return this.getObject(Student125.class, sql, param);
	}

	/**
	 * 删除学生信息，三表关联
	 */
	@Override
	public void deleteStudentById(int id) {
		String sql="DELETE FROM student WHERE id=?";
		Object[] param={id};
		this.delete(sql, param);
	}

	@Override
	public void deleteEscoreStudentByStudentId(int studentId) {
		String sql="DELETE FROM escore WHERE studentid=?";
		Object[] param={studentId};
		this.delete(sql, param);
	}

	@Override
	public void deleteUserStudentByStudentNumber(String studentNumber) {
		String sql="DELETE FROM `user` WHERE account=?";
		Object[] param={studentNumber};
		this.delete(sql, param);
	}
	
	/**
	 * 根据学号查询学生信息
	 */
	@Override
	public Object getStudentByNumber(String number) {
		String sql="SELECT * FROM(SELECT student.*,grade.`name` as gradeName,clazz.`name` as clazzName FROM student  LEFT OUTER JOIN grade ON student.gradeid=grade.id LEFT OUTER JOIN clazz ON student.clazzid=clazz.id) as studentInfo WHERE number=?";
		Object[] param={number};
		return this.getObject(Student125.class, sql, param);
	}
	
	

}
