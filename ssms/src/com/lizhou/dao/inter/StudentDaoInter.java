package com.lizhou.dao.inter;

import java.io.IOException;
import java.util.List;

import com.lizhou.bean.Page;
import com.lizhou.bean.Student;

/**
 * 操作学生的数据层接口
 * @author bojiangzhou
 *
 */
public interface StudentDaoInter extends BaseDaoInter {
	
	public List<Object> getStudent(int id);
	public List<Object> getStudentID(String account);
	public void getBlob(int id,String realPath);
	public void update(String sex,String phone,String qq,String number);
	public boolean insertphoto(int id,String path) throws IOException;
	public void updatePsd(String number,String psd);
	public String getPsd(String number);
}
