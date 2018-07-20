package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lizhou.bean.Exam;
import com.lizhou.service.ExamService02;

public class ExamServlet02 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExamServlet02() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取请求的方法
		String uri=request.getRequestURI().toString();
		String opt = uri.split("/")[ uri.split("/").length-1 ];
		if("ExamListView".equals(opt)){
			request.getRequestDispatcher("/WEB-INF/view/other/ExamList02.jsp").forward(request, response);
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		ExamService02 service =new ExamService02();
		String uri=request.getRequestURI().toString();
		String opt = uri.split("/")[ uri.split("/").length-1 ];
		
		if("ExamListView".equals(opt)){
			request.getRequestDispatcher("/WEB-INF/view/other/ExamList02.jsp").forward(request, response);
		}
		else if("queryAll".equals(opt)){
			//int page=Integer.parseInt(request.getParameter("page"));
			//int rows=Integer.parseInt(request.getParameter("rows"));
			String gradeid=request.getParameter("grade");
			String clazzid=request.getParameter("clazzid");
			
			String jsonString = service.queryAll(gradeid, clazzid);	
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out =  response.getWriter();
			
			out.print(jsonString);
			
			out.flush();
			out.close();
		}else if("addExam".equals(opt)){
			Exam e=new Exam();
			String name = request.getParameter("name");
			SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
			String time=f.format(new Date());
			int type=Integer.parseInt(request.getParameter("type"));
			int gradeid=Integer.parseInt(request.getParameter("gradeid"));
			if(type==1){
				
				String remark=request.getParameter("remark");
				e.setName(name);
				e.setEtime(time);
				e.setType(type);
				e.setGradeid(gradeid);
				e.setRemark(remark);
				service.addExam(e);
			}else{
				int clazzid=Integer.parseInt(request.getParameter("clazzid"));
				int courseid=Integer.parseInt(request.getParameter("courseid"));
				String remark=request.getParameter("remark");
				e.setName(name);
				e.setEtime(time);
				e.setType(type);
				e.setGradeid(gradeid);
				e.setClazzid(clazzid);
				e.setCourseid(courseid);
				e.setRemark(remark);
				service.addExam(e);
			}
			response.getWriter().write("success");
		}else if("delete".equals(opt)){
			String id=request.getParameter("id");
			
			
			try {
				service.deleteExam(id);
				response.getWriter().write("success");
			} catch (Exception e) {
				response.getWriter().write("fail");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
