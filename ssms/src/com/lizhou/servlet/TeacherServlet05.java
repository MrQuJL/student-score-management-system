package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lizhou.service.CourseService;
import com.lizhou.service.TeacherService05;
import com.lizhou.tools.StringTool;

public class TeacherServlet05 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TeacherServlet05() {
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

		request.setCharacterEncoding("utf-8");
		String uri=request.getRequestURI().toString();
		String opt = uri.split("/")[ uri.split("/").length-1 ];
		if("TeacherListView".equals(opt)){
			request.getRequestDispatcher("/WEB-INF/view/other/teacherList05.jsp").forward(request, response);
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
		TeacherService05 service=new TeacherService05();
		String uri=request.getRequestURI().toString();
		String opt = uri.split("/")[ uri.split("/").length-1 ];
		
		if("TeacherListView".equals(opt)){
			request.getRequestDispatcher("/WEB-INF/view/other/teacherList05.jsp").forward(request, response);
		}
		else if("queryAll".equals(opt)){		
			int page=Integer.parseInt(request.getParameter("page"));
			int rows=Integer.parseInt(request.getParameter("rows"));
			String jsonString = service.queryAll(page, rows);		
			response.setCharacterEncoding("response");;
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out =  response.getWriter();
			
			out.print(jsonString);
			
			out.flush();
			out.close();
		}else if("addTeacher".equals(opt)){
			
			String number=request.getParameter("number");
			String name = request.getParameter("name");
			String sex=request.getParameter("sex");
			String phone=request.getParameter("phone");
			String qq=request.getParameter("qq");
			service.addTeacher(number,name,sex,phone,qq);
			response.getWriter().write("success");
		}else if("deleteTeahcer".equals(opt)){
			String teacherid=(String)request.getParameter("teacherid");
	
			
			try {
				service.deleteTeacher(teacherid);
				response.getWriter().write("success");
			} catch (Exception e) {
				response.getWriter().write("fail");
				e.printStackTrace();
			}
		}else if("editTeacher".equals(opt)){
			//request.setCharacterEncoding("text/html;charset=utf-8");
			response.getWriter().write("success");
			int id=Integer.parseInt(request.getParameter("id"));
			String number1=request.getParameter("number");
			String name1 = request.getParameter("name");
			String sex = request.getParameter("sex");
			

			String number = StringTool.messyCode(number1);
			String name = StringTool.messyCode(name1);
			
			
			String phone=request.getParameter("phone");
			String qq=request.getParameter("qq");
			service.editTeacherInfo(id,number,name,sex,phone,qq);

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
