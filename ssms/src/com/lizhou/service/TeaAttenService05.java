package com.lizhou.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



import com.lizhou.dao.impl.TeaAttenDaoimpl05;
import com.lizhou.dao.inter.TeaAttenDaoInter05;


public class TeaAttenService05 {
	TeaAttenDaoInter05 dao = new TeaAttenDaoimpl05();
	public String queryAll()
	{
		List<Object> list = dao.queryAll();
		String jsonString = JSONArray.fromObject(list).toString();
		return jsonString;
	}
	
	public String queryAll( int page , int rows)
	{
		List<Object> list = dao.queryAll(page , rows);
		
		
		Map< String, Object > map = new HashMap<String, Object>();
		map.put("total", dao.queryAll().size());
		map.put("rows", list);
		
		String jsonString = JSONObject.fromObject(map).toString();
		return jsonString;
	}


	public String getBlob(String number,String realPath){
		
		return dao.getBlob(number, realPath);
	}
}