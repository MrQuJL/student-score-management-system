package com.lizhou.dao.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;

import com.lizhou.bean.Student;
import com.lizhou.dao.inter.StudentDaoInter;
import com.lizhou.tools.MysqlTool;



public class StudentDaoImpl extends BaseDaoImpl implements StudentDaoInter{


	@Override
	public List<Object> getStudentID(String account) {
		String sql="select id from student where number=?";
		Object[] param={account};
		List<Object> obj=this.getList(Student.class, sql,param);
		return obj;
	}
	@Override
	public List<Object> getStudent(int id) {
		String sql="select number,student.`name`,sex,phone,qq,clazz.`name` clazzname,grade.`name` gradename from student LEFT OUTER JOIN clazz on student.clazzid=clazz.id LEFT OUTER JOIN grade on student.gradeid=grade.id where student.id=?";
		Object[] param={id};
		List<Object> obj=this.getList(Student.class, sql,param);
		return obj;
	}/*
	**
	 * �õ�ѧ����Ƭ,����id����
	 */
	@Override
	public void getBlob(int id,String realPath){
		   Connection con = null;
	       PreparedStatement ps = null;
	       ResultSet rs = null;
	       try {
	             con = MysqlTool.getConnection();
	     		 String sql="SELECT id,photo FROM student  WHERE id=?";	 
	     		 ps = con.prepareStatement(sql);
	     		 ps.setInt(1, id);
	     		 rs = ps.executeQuery();
	             while(rs.next()){
	                    String studentNumber ="student"+String.valueOf(id);
	                    Blob picture = rs.getBlob(2);//�õ�Blob����
	                    //��ʼ�����ļ�
	                    InputStream in = picture.getBinaryStream();
	                    String path = realPath+"photo\\"+studentNumber+".jpeg";
	                    OutputStream out = new FileOutputStream(path);
	                    byte[] buffer = new byte[1024];
	                    int len = 0;
	                    while((len = in.read(buffer)) != -1){
	                           out.write(buffer, 0, len);
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
	public void update(String sex,String phone,String qq,String number)
	{
		String sqlstring="update student set sex=?,phone=?,qq=? where number=?";
		Object[] param={sex,phone,qq,number };
		this.update(sqlstring, param);
		
	}
	@Override
public boolean insertphoto(int id, String path) throws IOException {
		
		String sql="update student set photo=? where id=?";

		//Connection conn = MysqlTool.getConnection();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ssms", "root", "123");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		byte[] data=null;
		try {
			InputStream in=new FileInputStream(path);
			data=new byte[in.available()];
			in.read(data);
			//in.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBytes(1, data);
			ps.setInt(2, id);
			int rs=ps.executeUpdate();
			 
			while(rs>0){
				
              return true;
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
		
	}
	@Override
	public void updatePsd(String number, String psd) {
		String sqlstring="update user set password=? where account=?";
		Object[] param={psd,number};
		this.update(sqlstring, param);
		
	}
	@Override
	public String getPsd(String number) {
		String psd="";
		String sql="select password from user where account=?";

		Connection conn = MysqlTool.getConnection();
	    
		ResultSet rs=null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, number);
			rs=ps.executeQuery();
			 
			while(rs.next()){
				psd=rs.getString("password");
              return psd;
			}
			
			MysqlTool.closeConnection();
			MysqlTool.close(ps);
			MysqlTool.close(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "����";
	}
		
	
//	 public static void main(String args[]) throws IOException
//	   {
//		   StudentDaoImpl t=new StudentDaoImpl();
//		  boolean i= t.insertphoto(56, "C:\\Tomcat 8.0\\webapps\\ssms\\photo\\111.jpeg");
//	       System.out.println(i);
//	   }

}

