package com.lizhou.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lizhou.bean.Exam;
import com.lizhou.dao.inter.ExamDaoInter39;
import com.lizhou.dto.ExamDTO39;
import com.lizhou.dto.ScoreDTO39;

public class ExamDaoImpl39 extends BaseDaoImpl implements ExamDaoInter39 {

	@Override
	public List<ExamDTO39> getAllExamList() {
		String sql = 
			" SELECT A.id, A.name examName, A.time examTime," +
			" CASE A.type WHEN 1 THEN '年级统考' ELSE '平时考试' END examType," +
			" B.id gradeId, B.name gradeName, C.id clazzId, C.name clazzName," +
			" D.id courseId, D.name courseName, A.remark" +
			" FROM exam A" +
			" INNER JOIN grade B" + 
			"	 ON A.gradeid = B.id" +
			" LEFT OUTER JOIN clazz C" +
			"	 ON A.clazzid = C.id" +
			" LEFT OUTER JOIN course D" +
			"	 ON A.courseid = D.id";
		List<Object> list = this.getList(ExamDTO39.class, sql);
		List<ExamDTO39> result = new ArrayList<ExamDTO39>();
		for (Object obj : list) {
			result.add((ExamDTO39) obj);
		}
		return result;
	}

	@Override
	public void addExam(Exam exam) {
		String sql = "INSERT INTO exam(name, time, remark, type," +
			"gradeid, clazzid, courseid)" +
			"VALUES(?, ?, ?, ?, ?, ?, ?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(exam.getTime());
		Object[] param = {exam.getName(), time, exam.getRemark(),
			exam.getType(), exam.getGradeid(), exam.getClazzid(), exam.getCourseid()};
		this.insert(sql, param);
	}

	@Override
	public List<ScoreDTO39> getScoreList(Integer examId, Integer clazzId, Integer courseId) {
		String sql = 
			" SELECT A.id id, A.examid examId, A.clazzid clazzId, A.courseid courseId," +
			" A.studentid stuId, B.number number, B.name stuName, A.score score " +
			" FROM escore A, student B " +
			" WHERE A.studentid = B.id " +
			" AND A.examid = ? " +
			" AND A.clazzid = ? " +
			" AND A.courseid = ? ";
		Object[] param = {examId, clazzId, courseId};
		List<Object> list = this.getList(ScoreDTO39.class, sql, param);
		List<ScoreDTO39> result = new ArrayList<ScoreDTO39>();
		for (Object obj : list) {
			result.add((ScoreDTO39) obj);
		}
		return result;
	}

	@Override
	public void updateScore(Integer id, Integer score) {
		String sql = "UPDATE escore SET score = ? WHERE id = ?";
		Object[] param = {score, id};
		this.insert(sql, param);
	}

}
