package com.lizhou.dao.inter;

import java.util.List;

import com.lizhou.bean.Exam;
import com.lizhou.dto.ExamDTO39;
import com.lizhou.dto.ScoreDTO39;

/**
 * 考试数据层接口
 * @author 曲健磊
 */
public interface ExamDaoInter39 extends BaseDaoInter {

	/**
	 * 获取所有的考试列表
	 * @return
	 */
	public List<ExamDTO39> getAllExamList();
	
	/**
	 * 添加考试
	 * @param exam
	 * @return
	 */
	public void addExam(Exam exam);
	
	/**
	 * 获取成绩列表
	 * @param examId
	 * @param clazzId
	 * @param courseId
	 * @return
	 */
	public List<ScoreDTO39> getScoreList(Integer examId, Integer clazzId, Integer courseId);
	
	/**
	 * 更新学生成绩
	 * @param id
	 * @param score
	 */
	public void updateScore(Integer id, Integer score);
	
}
