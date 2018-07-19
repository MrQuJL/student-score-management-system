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

import com.lizhou.bean.Student;
import com.lizhou.bean.Teacher24;
import com.lizhou.dao.inter.TeacherDaoInter;
import com.lizhou.tools.MysqlTool;

public class TeacherDaoImpl extends BaseDaoImpl implements TeacherDaoInter {

	@Override
	public List<Object> getTeacherID(String account) {
		String sql="select id from teacher where number=?";
		Object[] param={account};
		List<Object> obj=this.getList(Teacher24.class, sql,param);
		return obj;
	}
	@Override
	public List<Object> getTeacher(int id) {
		String sql="select teacher.id,number,teacher.`name`,sex,phone,qq,grade.`name` gradename,clazz.name classname,course.`name` coursename from teacher LEFT OUTER JOIN clazz_course_teacher a ON teacher.id=a.teacherid LEFT OUTER JOIN grade on grade.id=a.gradeid LEFT OUTER JOIN clazz on clazz.id=a.clazzid LEFT OUTER JOIN course on course.id=a.courseid where teacher.id=?";
		Object[] param={id};
		List<Object> obj=this.getList(Teacher24.class, sql,param);
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
	       String path =null;
	       try {
	             con = MysqlTool.getConnection();
	     		 String sql="SELECT id,photo FROM teacher  WHERE id=?";	 
	     		 ps = con.prepareStatement(sql);
	     		 ps.setInt(1, id);
	     		 rs = ps.executeQuery();
	             while(rs.next()){
	                    String studentNumber ="teacher"+String.valueOf(id);
	                    Blob picture = rs.getBlob(2);//�õ�Blob����
	                    //��ʼ�����ļ�
	                    InputStream in = picture.getBinaryStream();
	                    path = realPath+"photo\\"+studentNumber+".jpeg";
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
		String sqlstring="update teacher set sex=?,phone=?,qq=? where number=?";
		Object[] param={sex,phone,qq,number };
		this.update(sqlstring, param);
		
	}
	@Override
	public boolean insertphoto(int id, String path) throws IOException {
		
		String sql="update teacher set photo=? where id=?";

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
	public String getPath(String path)//�����ַ��滻
	{
		String topath="C:/Tomcat 8.0/webapps/ssms/photo/1531912716580_791.1.jpg";
		
		String[] s=new String[20];
	
		System.out.println(s.toString());
		return topath;
	}
   /*public static void main(String args[]) throws IOException
   {
	   TeacherDaoImpl t=new TeacherDaoImpl();
	   String path="C:/Tomcat 8.0/webapps/ssms/photo/1531912716580_791.1.jpg";
       System.out.println(t.insertphoto(10, path)); 
   }*/
	
}
