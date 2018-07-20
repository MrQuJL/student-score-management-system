package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Teacher;

public interface TeacherDaoInter05 extends BaseDaoInter {
	public List<Teacher> queryAll();
	public List<Teacher> queryAll(int page, int rows);


}
