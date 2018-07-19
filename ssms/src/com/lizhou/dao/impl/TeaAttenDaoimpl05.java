package com.lizhou.dao.impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.lizhou.bean.Teacher;
import com.lizhou.dao.inter.TeaAttenDaoInter05;
import com.lizhou.tools.MysqlTool;



public class TeaAttenDaoimpl05 extends BaseDaoImpl implements TeaAttenDaoInter05{

	@Override
	public List<Object> queryAll() {
		String sql ="select * from teacher";
		return this.getList(Teacher.class, sql);
	}

	@Override
	public List<Object> queryAll(int page, int rows) {
		List<Object> list =this.queryAll();
		if(list.size()<page*rows)
			return list.subList((page-1)*rows, list.size());
		else
			return list.subList((page-1)*rows, page*rows);
	}
	
	public String getBlob(String number,String realPath){
		   Connection con = null;
	       PreparedStatement ps = null;
	       ResultSet rs = null;
	       String path=null;
	       try {
	             con = MysqlTool.getConnection();
	     		 String sql="SELECT number,photo FROM teacher  WHERE number=?";	 
	     		 ps = con.prepareStatement(sql);
	     		 ps.setString(1, number);
	     		 rs = ps.executeQuery();
	             while(rs.next()){
	                    String studentNumber =String.valueOf(number);
	                    Blob picture = rs.getBlob(2);//得到Blob对象
	                    //开始读入文件
	                    InputStream in = picture.getBinaryStream();
	                    path = realPath+"\\photo\\"+studentNumber+".jpeg";
	                    System.out.println(path);
						OutputStream out = new FileOutputStream(path);
	                    byte[] buffer = new byte[1024];
	                    int len = 0;
	                    while((len = in.read(buffer)) != -1){
	                           out.write(buffer, 0, len);
	                    }
	                   
	             }
	       } catch (Exception e) {
	    	   e.printStackTrace();
	    	   
	       }
	       return path;
//	    	   finally{	    	   
//	    	   MysqlTool.close(con);
//	    	   MysqlTool.close(ps);
//	    	   MysqlTool.close(rs);
//	       }

	}


}
