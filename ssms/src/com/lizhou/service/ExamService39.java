package com.lizhou.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lizhou.bean.Exam;
import com.lizhou.dao.impl.ExamDaoImpl39;
import com.lizhou.dao.inter.ExamDaoInter39;
import com.lizhou.dto.ExamDTO39;
import com.lizhou.dto.ScoreDTO39;
import com.lizhou.dto.ScoreRangeDTO39;

import net.sf.json.JSONArray;

/**
 * 考试服务层
 * @author 曲健磊
 */
public class ExamService39 {
	
	ExamDaoInter39 dao = new ExamDaoImpl39();
	
	/**
	 * 获取所有的考试列表的json格式的字符串
	 * @return
	 */
	public String getAllExamList(){
		//获取数据
		List<ExamDTO39> list = dao.getAllExamList();
		//json化
        String result = JSONArray.fromObject(list).toString();
        return result;
	}
	
	/**
	 * 添加考试信息
	 * @param exam
	 */
	public void addExam(Exam exam) {
		dao.addExam(exam);
	}
	
	/**
	 * 获取某场考试的学生成绩
	 * @param examId
	 * @param clazzId
	 * @param courseId
	 * @return
	 */
	public String getScoreList(Integer examId, Integer clazzId, Integer courseId) {
		List<ScoreDTO39> list = dao.getScoreList(examId, clazzId, courseId);
		// json化
        String result = JSONArray.fromObject(list).toString();
        return result;
	}
	
	/**
	 * 更新学生成绩
	 * @param id
	 * @param score
	 */
	public void updateScore(Integer id, Integer score) {
		this.dao.updateScore(id, score);
	}
	
	/**
	 * 统计某次考试某班某门课程的学生成绩范围
	 * @param examId 考试id
	 * @param clazzId 班级id
	 * @param courseId 课程id
	 * @return json格式的成绩范围字符串
	 */
	public String countScore(Integer examId, Integer clazzId, Integer courseId) {
		List<ScoreRangeDTO39> list = dao.countScore(examId, clazzId, courseId);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (ScoreRangeDTO39 temp : list) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("value", temp.getA()+"");
			map1.put("name", "0~90");
			result.add(map1);
			
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("value", temp.getB()+"");
			map2.put("name", "90~100");
			result.add(map2);
			
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("value", temp.getC()+"");
			map3.put("name", "100~120");
			result.add(map3);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("value", temp.getD()+"");
			map4.put("name", "120~140");
			result.add(map4);
			
			Map<String, String> map5 = new HashMap<String, String>();
			map5.put("value", temp.getE()+"");
			map5.put("name", "140~150");
			result.add(map5);
		}
		String ret = JSONArray.fromObject(result).toString();
		return ret;
	}
	
}
