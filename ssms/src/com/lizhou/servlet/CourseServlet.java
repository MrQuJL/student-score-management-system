package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lizhou.bean.Course;
import com.lizhou.service.CourseService;

/**
 * @author 赵学成
 */
public class CourseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
      
	private CourseService service = new CourseService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI().toString();
		String[] uriArray = uri.split("/");
		// 获取uri的最后一部分
		String opt = uriArray[uriArray.length - 1];
		if("toCourseListView".equalsIgnoreCase(opt)){ //转发到课程列表页
			request.getRequestDispatcher("/WEB-INF/view/other/courseList.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI().toString();
		String[] uriArray = uri.split("/");
		// 获取uri的最后一部分
		String opt = uriArray[uriArray.length - 1];
		
		if("courseList".equals(opt)){ // 查询所有的课程列表(分页查询)
			getCourseList(request, response);
		} else if("AddCourse".equals(opt)){ //添加班级
			addCourse(request, response);
		} else if("DeleteCourse".equals(opt)){ //删除班级
			deleteCourse(request, response);
		} else if ("getCourseListByClazzId".equals(opt)) {
			getCourseListByClazzId(request, response);
		} else if ("CourseList".equals(opt)) { // 进入年级管理页面时查询的所有课程信息(不分页)
			getCourseListForGrade(request, response);
		}
		
	}
	
	/**
	 * 查询出所有的课程信息,只有id和name
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getCourseListForGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String courses = service.getAllCourseListForGrade();
		PrintWriter out = response.getWriter();
		out.print(courses);
		out.flush();
		out.close();
	}

	/**
	 * 查询出指定班级下的所有课程
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getCourseListByClazzId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer clazzid = Integer.valueOf(request.getParameter("clazzid"));
		String result = service.getCListByClazzid(clazzid);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * 删除课程
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		try {
			service.delCourse(courseId);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	/**
	 * 添加课程
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		Course course = new Course();
		course.setName(name);
		service.addCourse(course);
		response.getWriter().write("success");
	}

	/**
	 * 查询出所有的课程列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getCourseList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String courseName = request.getParameter("courseName");
		courseName = courseName == null ? "" : courseName;
		
		String courseString = service.getCourseList(page, rows, courseName);
		
		PrintWriter out = response.getWriter();
		out.print(courseString);
		out.flush();
		out.close();
	}

}
