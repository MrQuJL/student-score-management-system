package com.lizhou.dao.inter;

import java.util.List;
import java.util.Map;

import com.lizhou.bean.*;

public interface EscoreDaoInter02 extends BaseDaoInter{
	public List<Score02> queryAll(String id,String clazzid);
	public List<Map<String, Object>> getScoreList(Exam exam);
}
