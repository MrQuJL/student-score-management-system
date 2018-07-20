package com.lizhou.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lizhou.bean.Teacher;
import com.lizhou.bean.Teacher24;
import com.lizhou.dao.impl.TeacherDaoImpl;
import com.lizhou.dao.inter.TeacherDaoInter;


public class TeacherService {
	TeacherDaoInter dao=new TeacherDaoImpl();
	public int getTeacherId(String account)
	{
		
		List<Object> list=dao.getTeacherID(account);
//		Teacher[] teacherArr=list.toArray(new Teacher[0]);
		int teacherId=-1;
		  List<Teacher24> result = new ArrayList<Teacher24>();
	        for (Object obj : list) {
	        	result.add((Teacher24)obj);
	        }
	        teacherId=result.get(0).getId();
		if(teacherId>0)
		{
			return teacherId;
		}
		return -1;
		
	}
	
	public List<Teacher24> getTeacher(int id)
	{
        List<Object> list = dao.getTeacher(id);
        List<Teacher24> result = new ArrayList<Teacher24>();
        for (Object obj : list) {
        	result.add((Teacher24)obj);
        }
		return result;
	}
	public void getBlob(int id,String realPath){
		
		dao.getBlob(id, realPath);
		
		
	}
	public void update(String sex,String phone,String qq,String number)
	{
		
		dao.update(sex, phone, qq, number);
		
	}
	public void updatePsd(String number,String psd){
		
		dao.updatePsd(number, psd);
	}
	public String getPsd(String number){
		return dao.getPsd(number);
		
	}	
	public boolean insertphoto(int id, String path)
	{
		try {
			return dao.insertphoto(id, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
