package com.lizhou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.lizhou.bean.Clazz;
import com.lizhou.bean.Grade;
import com.lizhou.bean.Page;
import com.lizhou.bean.Student;
import com.lizhou.dao.inter.BaseDaoInter;
import com.lizhou.dao.inter.ClazzDaoInter;
import com.lizhou.tools.MysqlTool;
import com.lizhou.tools.StringTool;

public class ClazzDaoImpl extends BaseDaoImpl implements ClazzDaoInter {

	public List<Clazz> getClazzDetailList(String gradeid, Page page) {
		//閺佺増宓侀梿鍡楁値
		List<Clazz> list = new LinkedList<Clazz>();
		try {
			StringBuffer sb = new StringBuffer("SELECT c.id cid, c.name cname, g.id gid, g.name gname FROM clazz c, grade g WHERE c.gradeid = g.id");
			List<Object> param = new LinkedList<Object>();
			if(!StringTool.isEmpty(gradeid)){
				sb.append(" AND c.gradeid=? ");
				param.add(Integer.parseInt(gradeid));
			}
			sb.append(" LIMIT ?,?");
			param.add(page.getStart());
			param.add(page.getSize());
			
			String sql = sb.toString();
			//閼惧嘲褰囬弫鐗堝祦鎼存捁绻涢幒锟�			
			Connection conn = MysqlTool.getConnection();
			//妫板嫮绱拠锟�			
			PreparedStatement ps = conn.prepareStatement(sql);
			//鐠佸墽鐤嗛崣鍌涙殶
			if(param != null && param.size() > 0){
				for(int i = 0;i < param.size();i++){
					ps.setObject(i+1, param.get(i));
				}
			}
			//閹笛嗩攽sql鐠囶厼褰�
			ResultSet rs = ps.executeQuery();
			//闁秴宸荤紒鎾寸亯闂嗭拷
			while(rs.next()){
				//閸掓稑缂撶�纭呰杽
				Clazz clazz = new Clazz();
				Grade grade = new Grade();
				//鐏忎浇顥栭崣鍌涙殶
				grade.setId(rs.getInt("gid"));
				grade.setName(rs.getString("gname"));
				clazz.setId(rs.getInt("cid"));
				clazz.setName(rs.getString("cname"));
				clazz.setGradeid(rs.getInt("gid"));
				//濞ｈ濮�
				clazz.setGrade(grade);
				//濞ｈ濮為崚浼存肠閸氾拷
				list.add(clazz);
			}
			//閸忔娊妫存潻鐐村复
			MysqlTool.closeConnection();
			MysqlTool.close(ps);
			MysqlTool.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

}
