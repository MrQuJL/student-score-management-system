package com.lizhou.service;

import java.util.List;

import net.sf.json.*;

import com.lizhou.bean.Test;
import com.lizhou.dao.impl.TestDaoImpl;
import com.lizhou.dao.inter.TestDaoInter;

public class TestService {

	TestDaoInter dao = new TestDaoImpl();
	
	public String queryAll()
	{
		List<Test> list = dao.queryAll();
		
		String jsonString = JSONArray.fromObject(list).toString();
		
		return jsonString;
	}
	
}
