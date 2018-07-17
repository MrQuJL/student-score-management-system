package com.lizhou.service;

import java.util.List;

import com.lizhou.bean.Exam;
import com.lizhou.dao.impl.ExamDaoImpl39;
import com.lizhou.dao.inter.ExamDaoInter39;
import com.lizhou.dto.ExamDTO39;
import com.lizhou.dto.ScoreDTO39;

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
	
}
