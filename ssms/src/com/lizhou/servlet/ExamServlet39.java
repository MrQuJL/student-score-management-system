package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lizhou.bean.Exam;
import com.lizhou.service.ExamService39;
import com.lizhou.tools.StringTool;

/**
 * 用于成绩登记的servlet
 * @author 曲健磊
 */
public class ExamServlet39 extends HttpServlet {

	private static final long serialVersionUID = 6619015915617678824L;

	ExamService39 service = new ExamService39();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String opt = uri.split("/")[uri.split("/").length - 1];
		if ("toExamListView".equals(opt)) { // 跳转页面的请求
			request.getRequestDispatcher("/WEB-INF/view/teacher/scoreRegister.jsp").forward(request, response);
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String opt = uri.split("/")[uri.split("/").length - 1];
		if ("ExamList".equals(opt)) { // 查询考试列表
			String examListStr = service.getAllExamList();
			PrintWriter out =response.getWriter();
			out.print(examListStr);
			out.flush();
			out.close();
		} else if ("AddExam".equals(opt)) {
			addExam(request, response);
		} else if ("ScoreList".equals(opt)) { // 查询学生成绩列表
			getScoreList(request, response);
			
		} else if ("updateScore".equals(opt)) { // 更新学生的成绩
			updateScore(request, response);
		}
	}

	/**
	 * 更新学生成绩
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void updateScore(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer id = Integer.valueOf(request.getParameter("id"));
		Integer score = 0;
		try {
			score = Integer.valueOf(request.getParameter("score"));
		} catch (NumberFormatException e) { // 成绩格式输入有误
//			System.out.println("成绩格式输入有误");
			return;
		}
		// System.out.println("id:" + id + "-------" + "score:" + score);
		service.updateScore(id, score);
	}

	/**
	 * 查询学生成绩列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getScoreList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String examIdStr = request.getParameter("examId");
		String clazzIdStr = request.getParameter("clazzId");
		String courseIdStr = request.getParameter("courseId");
		
		Integer examId = Integer.valueOf(examIdStr);
		Integer clazzId = Integer.valueOf(clazzIdStr);
		Integer courseId = Integer.valueOf(courseIdStr);
		
		String result = service.getScoreList(examId, clazzId, courseId);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * 添加考试信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ParseException
	 */
	private void addExam(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String clazzStr = request.getParameter("clazz");
		String courseStr = request.getParameter("course");
		
		String name = request.getParameter("name");
		String time = request.getParameter("time");
		Integer type;
		try {
			type = Integer.valueOf(request.getParameter("type"));
		} catch (Exception e) { // 考试类型格式输入有误
			PrintWriter out = response.getWriter();
			out.print("考试类型格式输入有误!");
			return;
		}
		Integer grade;
		try {
			grade = Integer.valueOf(request.getParameter("grade"));
		} catch (Exception e) { // 年级格式输入有误
			PrintWriter out = response.getWriter();
			out.print("年级格式输入有误!");
			return;
		}
		Integer clazzid = 0;
		if (!StringTool.isEmpty(clazzStr)) { // 非空判断
			try {
				clazzid = Integer.valueOf(clazzStr);
			} catch(Exception e) { // 班级格式输入有误
				PrintWriter out = response.getWriter();
				out.print("班级格式输入有误！");
				return;
			}
		}
		Integer courseid = 0;
		if (!StringTool.isEmpty(courseStr)) { // 非空不能为空
			try {
				courseid = Integer.valueOf(courseStr); // 课程格式输入有误
			} catch (Exception e) {
				PrintWriter out = response.getWriter();
				out.print("课程格式输入有误！");
				return;
			}
		}
		String remark = request.getParameter("remark");
		
		Exam exam = new Exam();
		exam.setName(name);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			exam.setTime(sdf.parse(time));
		} catch (Exception e) {
			// 日期格式输入错误
			PrintWriter out = response.getWriter();
			out.print("日期格式输入错误!");
			return;
		}
		exam.setType(type);
		exam.setGradeid(grade);
		exam.setClazzid(clazzid);
		exam.setCourseid(courseid);
		exam.setRemark(remark);
		try {
			service.addExam(exam);
		} catch (Exception e) {
			// 日期格式输入错误
			PrintWriter out = response.getWriter();
			out.print("日期格式输入错误!");
			return;
		}
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
	}

}
