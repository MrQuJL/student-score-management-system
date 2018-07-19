package com.lizhou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JsonConfig;

import com.lizhou.bean.Teacher;
import com.lizhou.dao.inter.TeacherDaoInter05;
import com.lizhou.tools.MysqlTool;



public class TeacherDaoImpl05 extends BaseDaoImpl implements TeacherDaoInter05{

	@Override
	public List<Teacher> queryAll() {
		List<Teacher> teacherlist=new ArrayList<Teacher>();
		Connection con=MysqlTool.getConnection();
		String sql="select * from teacher";
		JsonConfig config = new JsonConfig();
		try {
			PreparedStatement stmt=con.prepareStatement(sql);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Teacher teacher=new Teacher();
				teacher.setId(rs.getInt("id"));
				teacher.setNumber(rs.getString("number"));
				teacher.setName(rs.getString("name"));
				teacher.setSex(rs.getString("sex"));
				teacher.setPhone(rs.getString("phone"));
				teacher.setQq(rs.getString("qq"));
				teacherlist.add(teacher);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teacherlist;
	}

	@Override
	public List<Teacher> queryAll(int page, int rows) {
		List<Teacher> list=this.queryAll();
		int count =page*rows;
		if((page*rows)>list.size())
		{
			count=list.size();
		}
		return list.subList((page-1)*rows, count);
	}


}
