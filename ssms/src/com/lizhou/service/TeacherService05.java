package com.lizhou.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lizhou.bean.Teacher;
import com.lizhou.dao.impl.TeacherDaoImpl05;
import com.lizhou.dao.inter.TeacherDaoInter05;
import com.lizhou.tools.MysqlTool;




public class TeacherService05 {
	TeacherDaoInter05 dao=new TeacherDaoImpl05();
	public String queryAll(){
		List<Teacher> list=dao.queryAll();
		String teacherJosn=JSONArray.fromObject(list).toString();
		return teacherJosn;
		
	}
	public String queryAll(int page,int rows){
		List<Teacher> list=dao.queryAll(page,rows);
		Map<String,Object> map=new HashMap<String ,Object>();
		map.put("total", dao.queryAll().size());
		map.put("rows", list);
		String teacherJosn=JSONObject.fromObject(map).toString();
		return teacherJosn;
		
	}
	
	/***
	 * 添加
	 */
	public void addTeacher(String number,String name,String sex,String phone,String qq){
		dao.insert("insert into teacher(number,name,sex,phone,qq) values(?,?,?,?,?)", new Object[]{number,name,sex,phone,qq});
	} 
	/***
	 * 删除
	 */
	public void deleteTeacher(String teacherid) throws Exception {
		//获取连接
		Connection conn = MysqlTool.getConnection();
		try {
			//开启事务
			MysqlTool.startTransaction();
	
			//删除课程
			dao.deleteTransaction(conn, "DELETE FROM teacher WHERE id = ?", new Object[]{teacherid});
			
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
	/***
	 *修改
	 * @return
	 */
	public void editTeacherInfo(int id, String number, String name,String sex,
			String phone, String qq) {
		//修改数据库
				dao.update("UPDATE teacher SET number = ?,name=?,sex=?,phone=?,qq=? where id=?", new Object[]{number,name,sex,phone,qq,id});
				//重新加载数据
				//获取系统初始化对象
				Teacher teacher = (Teacher) dao.getObject(Teacher.class, "SELECT * FROM system", null);
		    	return;
		
	}
	
}
