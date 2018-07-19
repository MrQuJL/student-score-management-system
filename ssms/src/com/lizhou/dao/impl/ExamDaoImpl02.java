package com.lizhou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JsonConfig;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Course;
import com.lizhou.bean.Exam;
import com.lizhou.bean.Grade;
import com.lizhou.dao.inter.ExamDaoInter02;
import com.lizhou.tools.MysqlTool;
import com.lizhou.tools.StringTool;

public class ExamDaoImpl02 extends BaseDaoImpl implements ExamDaoInter02 {

	@Override
	public List<Exam> getExamList(String gradeid, String clazzid) {
		List<Exam> list=new ArrayList<Exam>();
		Connection con=MysqlTool.getConnection();
		StringBuffer sb = new StringBuffer("SELECT A.id id, A.name name, A.time etime,A.type type,B.id gradeid, B.name grade, C.id clazzid, C.name clazz,D.id courseid, D.name course, A.remark" +
				" FROM exam A INNER JOIN grade B ON A.gradeid = B.id LEFT OUTER JOIN clazz C ON A.clazzid = C.id LEFT OUTER JOIN course D ON A.courseid = D.id");
		if(!StringTool.isEmpty(gradeid)&&!StringTool.isEmpty(clazzid)){
			sb.append(" where A.gradeid=? and A.clazzid=?");
		}else if(!StringTool.isEmpty(gradeid)&&StringTool.isEmpty(clazzid)){
			sb.append(" where A.gradeid=? ");
		}else if(StringTool.isEmpty(gradeid)&&!StringTool.isEmpty(clazzid)){
			sb.append(" where A.clazzid=? ");
		}
		String sql=sb.toString();
		try {
			PreparedStatement stmt=con.prepareStatement(sql);
			if(!StringTool.isEmpty(gradeid)&&!StringTool.isEmpty(clazzid)){
				stmt.setInt(1, Integer.parseInt(gradeid));
				stmt.setInt(2, Integer.parseInt(clazzid));
			}else if(!StringTool.isEmpty(gradeid)&&StringTool.isEmpty(clazzid)){
				stmt.setInt(1, Integer.parseInt(gradeid));
			}
			else if(StringTool.isEmpty(gradeid)&&!StringTool.isEmpty(clazzid)){
				stmt.setInt(1, Integer.parseInt(clazzid));
			}
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Exam exam=new Exam();
				Grade grade=new Grade();
				Course course=new Course();
				Clazz clazz=new Clazz();
				exam.setId(rs.getInt("id"));
				exam.setName(rs.getString("name"));
				exam.setEtime(rs.getString("etime"));
				exam.setType(rs.getInt("type"));
				exam.setGradeid(rs.getInt("gradeid"));
				grade.setName(rs.getString("grade"));
				exam.setGrade(grade);
				exam.setClazzid(rs.getInt("clazzid"));
				clazz.setName(rs.getString("clazz"));
				exam.setClazz(clazz);
				exam.setCourseid(rs.getInt("courseid"));
				course.setName(rs.getString("course"));
				exam.setCourse(course);
				exam.setRemark(rs.getString("remark"));
				list.add(exam);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Exam> queryAll(String gradeid,String clazzid,int page, int rows) {
		List<Exam> list=this.getExamList(gradeid, clazzid);
		int count =page*rows;
		if((page*rows)>list.size())
		{
			count=list.size();
		}
		return list.subList((page-1)*rows, count);
	}

	

}
