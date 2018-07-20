package com.lizhou.dao.inter;

import java.io.IOException;
import java.util.List;

public interface TeacherDaoInter {

	public List<Object> getTeacher(int id);
	public List<Object> getTeacherID(String account);
	public void getBlob(int id,String realPath);
	public void update(String sex,String phone,String qq,String number);
	public boolean insertphoto(int id,String path) throws IOException ;
	public void updatePsd(String number,String psd);
	public String getPsd(String number);
}
