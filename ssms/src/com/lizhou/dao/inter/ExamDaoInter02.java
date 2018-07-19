package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Exam;

public interface ExamDaoInter02 extends BaseDaoInter{
	public List<Exam> getExamList(String gradeid, String clazzid);
	public List<Exam> queryAll(String gradeid,String clazzid,int page,int rows);

}
