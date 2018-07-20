package com.lizhou.service;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;

import com.lizhou.bean.Student;
import com.lizhou.dao.impl.StudentDaoImpl;
import com.lizhou.dao.inter.StudentDaoInter;

public class StudentService {

	StudentDaoInter dao=new StudentDaoImpl();
	public int getStudentId(String account)
	{
		
		List<Object> list=dao.getStudentID(account);
		Student[] studentArr=list.toArray(new Student[0]);
		int sutdentId=-1;
		for(Student student:studentArr){
			 sutdentId=student.getId();
		}
		if(sutdentId>0)
		{
			return sutdentId;
		}
		return -1;
		
	}
	
	public List<Object> getStudent(int id)
	{
        List<Object> list = dao.getStudent(id);
		
		
		
		return list;
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
