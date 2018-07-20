package com.lizhou.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.lizhou.bean.*;
import com.lizhou.dao.impl.CourseDaoImpl;
import com.lizhou.dao.impl.EscoreDaoImpl02;
import com.lizhou.dao.inter.CourseDaoInter;
import com.lizhou.dao.inter.EscoreDaoInter02;
import com.lizhou.tools.ExcelTool;

public class ScoreService02 {
	EscoreDaoInter02 dao=new EscoreDaoImpl02();
	CourseDaoInter coudao=new CourseDaoImpl();
	public String queryAll(String id,String clazzid){
		List<Score02> list=dao.queryAll(id,clazzid);
		String scoreJosn=JSONArray.fromObject(list).toString();
		return scoreJosn;
		
	}
	/**
	 * 获取数据栏的列名
	 * @param exam
	 * @return
	 */
	public String columnList(Exam exam) {
		List<Object> list = getColumn(exam);
		
		return JSONArray.fromObject(list).toString();
	}
	
	private List<Object> getColumn(Exam exam){
		List<Object> list = null;
		if(exam.getType() == Exam.EXAM_GRADE_TYPE){ //年级考试
			//获取考试的科目
			list = dao.getList(Course.class, 
					"SELECT c.id id, c.name name FROM course c, grade_course gc WHERE c.id=gc.courseid AND gc.gradeid=?", 
					new Object[]{exam.getGradeid()});
		} else{
			//获取某科
			list =  dao.getList(Course.class, 
					"SELECT * FROM course WHERE id=?", new Object[]{exam.getCourseid()});
			
		}
		return list;
	}
	/**
	 * 导出成绩列表
	 * @param response
	 * @param exam
	 */
	public void exportScore(HttpServletResponse response, Exam exam) {
		//获取需要导出的数据
		List<Map<String, Object>> list = dao.getScoreList(exam);
		//获取考试信息
		Exam em = (Exam) dao.getObject(Exam.class, "SELECT name, time FROM exam WHERE id=?", new Object[]{exam.getId()});
		//设置文件名
		String fileName = em.getName()+".xls";
		//定义输出类型
		response.setContentType("application/msexcel;charset=utf-8");
		//设定输出文件头
		try {
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		//获取导出的课程
		List<Object> courseList =  getColumn(exam);
		
		//表头长度
		int len = 2 + courseList.size();
		if(exam.getType() == Exam.EXAM_GRADE_TYPE){
			len += 1;
		}
		//设置excel的列名
		String[] headers = new String[len];
		headers[0] = "姓名";
		headers[1] = "学号";
		
		int index = 2;
		for(Object obj : courseList){
			Course course = (Course) obj;
			headers[index++] = course.getName();
		}
		
		if(exam.getType() == Exam.EXAM_GRADE_TYPE){
			headers[len-1] = "总分";
		}
		
		ExcelTool et = new ExcelTool();
		//导出
		try {
			et.exportMapExcel(headers, list, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
