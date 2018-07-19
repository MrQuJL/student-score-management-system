package com.lizhou.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JsonConfig;

import com.lizhou.bean.EScore;
import com.lizhou.bean.Exam;
import com.lizhou.bean.Score02;
import com.lizhou.bean.Student;
import com.lizhou.dao.inter.EscoreDaoInter02;
import com.lizhou.tools.MysqlTool;
import com.lizhou.tools.StringTool;

public class EscoreDaoImpl02 extends BaseDaoImpl implements EscoreDaoInter02{

	@Override
	public List<Score02> queryAll(String id,String clazzid) {
		List<Score02> scorelist=new ArrayList<Score02>();
		Connection con=MysqlTool.getConnection();
		StringBuffer sb = new StringBuffer("select B.number number,B.name name,sum(case when courseid=1 then score else 0 end) as chineseScore,"
				+"sum(case when courseid=2 then score else 0 end) as mathScore,"
				+"sum(case when courseid=3 then score else 0 end) as englishScore,"
				+"sum(case when courseid=4 then score else 0 end) as physicsScore,"
				+"sum(case when courseid=5 then score else 0 end) as chemistryScore,"
				+"sum(score) as totalScore from escore A LEFT OUTER JOIN student B on A.studentid=B.id ");
		if(!StringTool.isEmpty(clazzid)&&!StringTool.isEmpty(id)){
			sb.append(" where A.clazzid =? and A.examid=? group by A.studentid;");
		}else if(!StringTool.isEmpty(clazzid)&&StringTool.isEmpty(id)){
			sb.append(" where A.clazzid=? group by A.studentid;");
		}else if(StringTool.isEmpty(clazzid)&&!StringTool.isEmpty(id)){
			sb.append(" where A.examid=? group by A.studentid;");
		}
		String sql=sb.toString();
		try {
			PreparedStatement stmt=con.prepareStatement(sql);
			if(!StringTool.isEmpty(clazzid)&&!StringTool.isEmpty(id)){
				stmt.setInt(1,Integer.parseInt(clazzid));
				stmt.setInt(2,Integer.parseInt(id));
			}else if(!StringTool.isEmpty(clazzid)&&StringTool.isEmpty(id)){
				stmt.setInt(1,Integer.parseInt(clazzid));
			}else if(StringTool.isEmpty(clazzid)&&!StringTool.isEmpty(id)){
				stmt.setInt(1,Integer.parseInt(id));
			}
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Score02 score=new Score02();
				score.setNumber(rs.getString("number"));
				score.setName(rs.getString("name"));
				score.setChineseScore(rs.getInt("chineseScore"));
				score.setMathScore(rs.getInt("mathScore"));
				score.setEnglishScore(rs.getInt("englishScore"));
				score.setChemistryScore(rs.getInt("chemistryScore"));
				score.setPhysicsScore(rs.getInt("physicsScore"));
				score.setTotalScore(rs.getInt("totalScore"));
				scorelist.add(score);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scorelist;
	}

	@Override
	public List<Map<String, Object>> getScoreList(Exam exam) {
		//sql语句
				List<Object> stuParam = new LinkedList<Object>();
				StringBuffer stuSb = new StringBuffer("SELECT id, name, number FROM student WHERE gradeid=? ");
				stuParam.add(exam.getGradeid());
				if(exam.getClazzid() != 0){
					stuSb.append(" AND clazzid=?");
					stuParam.add(exam.getClazzid());
				}
				String stuSql = stuSb.toString();
				
				//获取数据库连接
				Connection conn = MysqlTool.getConnection();
				
				//获取该年级下的所有学生
				List<Object> stuList = getList(Student.class, stuSql, stuParam);
				
				//数据集合
				List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
				
				//sql语句
				String sql = "SELECT e.id,e.courseid,e.score FROM student s, escore e "
						+ "WHERE s.id=e.studentid AND e.examid=? AND e.studentid=?";
				
				for(int i = 0;i < stuList.size();i++){
					Map<String, Object> map = new LinkedHashMap<String, Object>();
					
					Student student = (Student) stuList.get(i);
					
					//map.put("name", student.getName());
					//map.put("number", student.getNumber());
					
					List<Object> scoreList = getList(conn, EScore.class, sql, new Object[]{exam.getId(), student.getId()});
					int total = 0;
					for(Object obj : scoreList){
						EScore score = (EScore) obj;
						total += score.getScore();
						map.put("name", student.getName());
						map.put("number", student.getNumber());
						//将成绩表id放入:便于获取单科成绩用于登记
						map.put("course"+score.getCourseid(), score.getScore());
						map.put("escoreid"+score.getCourseid(), score.getId());
					}
					if(exam.getType() == 1){
						map.put("total", total);
					}
					if(scoreList.size()>0)
						list.add(map);
				}
				return list;
	}

}
