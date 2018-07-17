package com.lizhou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lizhou.dao.inter.BaseDaoInter;
import com.lizhou.tools.MysqlTool;

/**
 * 基础数据访问层
 * @author 曲健磊
 *
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl implements BaseDaoInter{
	
	public List<Object> getList(Class type, String sql) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		List<Object> list = new LinkedList<Object>();
		try {
			list = qr.query(sql, new BeanListHandler(type));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object> getList(Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		List<Object> list = new LinkedList<Object>();
		try {
			list = qr.query(sql, new BeanListHandler(type), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object> getList(Class type, String sql, List<Object> param) {
		Object[] params = new Object[param.size()];
		for(int i = 0;i < param.size();i++){
			params[i] = param.get(i);
		}
		return getList(type, sql, params);
	}
	
	public List<Object> getList(Connection conn, Class type, String sql) {
		QueryRunner qr = new QueryRunner();
		List<Object> list = new LinkedList<Object>();
		try {
			list = qr.query(conn, sql, new BeanListHandler(type));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object> getList(Connection conn, Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner();
		List<Object> list = new LinkedList<Object>();
		try {
			list = qr.query(conn, sql, new BeanListHandler(type), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object> getList(Connection conn, Class type, String sql, List<Object> param) {
		Object[] params = new Object[param.size()];
		for(int i = 0;i < param.size();i++){
			params[i] = param.get(i);
		}
		return getList(conn, type, sql, params);
	}
	
	public Object getObject(Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		Object obj = new LinkedList<Object>();
		try {
			obj = qr.query(sql, new BeanHandler(type), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public Object getObject(Connection conn, Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner();
		Object obj = new LinkedList<Object>();
		try {
			obj = qr.query(conn, sql, new BeanHandler(type), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public Long count(String sql) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		Long count = 0L;
		try {
			count = qr.query(sql, new ScalarHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Long count(String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		Long count = 0L;
		try {
			count = qr.query(sql, new ScalarHandler(), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Long count(String sql, List<Object> param) {
		//灏嗛泦鍚堜腑鐨勫弬鏁板皝瑁呭埌鏁扮粍瀵硅薄涓�
		Object[] params = new Object[param.size()];
		for(int i = 0;i < param.size();i++){
			params[i] = param.get(i);
		}
		return count(sql, params);
	}
	
	public void update(String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		try {
			qr.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String sql, List<Object> param) {
		Object[] params = new Object[param.size()];
		for(int i = 0;i < param.size();i++){
			params[i] = param.get(i);
		}
		update(sql, params);
	}
	
	public void updateTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.update(conn, sql, param);
	}
	
	public void updateBatch(String sql, Object[][] param) {
		insertBatch(sql, param);
	}
	
	public void insert(String sql, Object[] param) {
		update(sql, param);
	}
	
	public void insertTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		updateTransaction(conn, sql, param);
	}
	
	public int insertReturnKeys(String sql, Object[] param) {
		int key = 0;
		//鑾峰彇鏁版嵁搴撹繛鎺�
		Connection conn = MysqlTool.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			//璁剧疆鍊�
			if(param != null && param.length > 0){
				for(int i = 0;i < param.length;i++){
					ps.setObject(i+1, param[i]);
				}
			}
			//鎵цsql璇彞
			ps.execute();
			//鑾峰彇鎻掑叆鏁版嵁鐨刬d鍊�
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt(1);
			};
			//杩欓噷涓嶈兘鍏抽棴杩炴帴锛氫竴鑸幏鍙栦富閿悗锛岃繕浼氬仛涓嬩竴姝ユ搷浣�
//			MysqlTool.closeConnection();
//			MysqlTool.close(rs);
//			MysqlTool.close(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public int insertReturnKeysTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		int key = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			//璁剧疆鍊�
			if(param != null && param.length > 0){
				for(int i = 0;i < param.length;i++){
					ps.setObject(i+1, param[i]);
				}
			}
			//鎵цsql璇彞
			ps.execute();
			//鑾峰彇鎻掑叆鏁版嵁鐨刬d鍊�
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt(1);
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public void insertBatch(String sql, Object[][] param) {
		QueryRunner runner = new QueryRunner(MysqlTool.getDataSource());
		try {
			runner.batch(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertBatchTransaction(Connection conn, String sql, Object[][] param) throws SQLException {
		QueryRunner runner = new QueryRunner();
		try {
			runner.batch(conn, sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String sql, Object[] param) {
		update(sql, param);
	}
	
	public void deleteTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		updateTransaction(conn, sql, param);
	}
	
	public void deleteTransaction(Connection conn, String sql, List<Object> param) throws SQLException {
		Object[] params = new Object[param.size()];
		for(int i = 0;i < param.size();i++){
			params[i] = param.get(i);
		}
		updateTransaction(conn, sql, params);
	}
	
	public void deleteBatchTransaction(Connection conn, String sql, Object[][] param) {
		QueryRunner runner = new QueryRunner();
		try {
			runner.batch(conn, sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getColumn(String sql, Object[] param) {
		//鏁版嵁闆嗗悎
		List<String> list = new LinkedList<String>();
		try {
			//鑾峰彇鏁版嵁搴撹繛鎺�
			Connection conn = MysqlTool.getConnection();
			//棰勭紪璇�
			PreparedStatement ps = conn.prepareStatement(sql);
			//璁剧疆鍊�
			if(param != null && param.length > 0){
				for(int i = 0;i < param.length;i++){
					ps.setObject(i+1, param[i]);
				}
			}
			//鎵цsql璇彞
			ResultSet rs = ps.executeQuery();
			//閬嶅巻缁撴灉闆�
			while(rs.next()){
				String account = rs.getString(1);
				//娣诲姞鍒伴泦鍚�
				list.add(account);
			}
			//鍏抽棴杩炴帴
			MysqlTool.closeConnection();
			MysqlTool.close(ps);
			MysqlTool.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
