package com.lizhou.dao.impl;

import java.util.*;

import com.lizhou.bean.Test;
import com.lizhou.dao.inter.TestDaoInter;

public class TestDaoImpl implements TestDaoInter {

	@Override
	public List<Test> queryAll() {
		
		List<Test> list = new ArrayList<Test>();
		list.add( new Test(1, "—Ó”Òª∑") );
		
		return list;
	}

}
