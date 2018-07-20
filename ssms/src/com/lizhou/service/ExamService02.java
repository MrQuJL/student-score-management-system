package com.lizhou.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;



import net.sf.json.JSONObject;

import com.lizhou.bean.Exam;
import com.lizhou.dao.impl.ExamDaoImpl02;
import com.lizhou.dao.inter.ExamDaoInter02;
import com.lizhou.tools.MysqlTool;

public class ExamService02 {
	ExamDaoInter02 dao=new ExamDaoImpl02();
	/***
	 * 查询
	 * @param gradeid
	 * @param clazzid
	 * @return
	 */
	public String queryAll(String gradeid,String clazzid){
		List<Exam> list=dao.getExamList(gradeid, clazzid);
		String examJosn=JSONArray.fromObject(list).toString();
		return examJosn;
		
	}
	/***
	 * 分页查询
	 * @param gradeid
	 * @param clazzid
	 * @param page
	 * @param rows
	 * @return
	 */
	public String queryAll(String gradeid,String clazzid,int page,int rows){
		List<Exam> list=dao.queryAll(gradeid, clazzid, page, rows);
		Map<String,Object> map=new HashMap<String ,Object>();
		map.put("total", this.queryAll(gradeid, clazzid));
		map.put("rows", list);
		String courseJosn=JSONObject.fromObject(map).toString();
		return courseJosn;
		
	}
	/***
	 * 添加
	 */
	public void addExam(Exam e){
		
		if(e.getType()==1){
			dao.insert("insert into exam(name,time,type,gradeid,remark) values(?,?,?,?,?)", 
					new Object[]{e.getName(),e.getTime(),e.getType(),e.getGradeid(),e.getRemark()});
	
		}else{
			dao.insert("insert into exam(name,time,type,gradeid,clazzid,courseid,remark) values(?,?,?,?,?,?,?)", 
					new Object[]{e.getName(),e.getTime(),e.getType(),e.getGradeid(),e.getClazzid(),e.getCourseid(),e.getRemark()});
		}
	}
	/***
	 * 删除
	 */
	public void deleteExam(String id) throws Exception {
		//获取连接
		Connection conn = MysqlTool.getConnection();
		try {
			//开启事务
			MysqlTool.startTransaction();
			
			dao.deleteTransaction(conn, "DELETE FROM exam WHERE id=?", new Object[]{id});
			//删除课程成绩
			//dao.deleteTransaction(conn, "DELETE FROM grade WHERE grade.id=eaxm.gradeid and exam.id in (?)", new Object[]{courseid});
			
			//删除课程
			//dao.deleteTransaction(conn, "DELETE FROM course WHERE id in (?)", new Object[]{courseid});
			
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
