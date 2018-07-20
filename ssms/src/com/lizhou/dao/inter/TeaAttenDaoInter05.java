package com.lizhou.dao.inter;

import java.util.List;

public interface TeaAttenDaoInter05 extends BaseDaoInter {
	public List<Object> queryAll();
	public List<Object> queryAll(int page, int rows);
	public String getBlob(String number,String realPath);
}
