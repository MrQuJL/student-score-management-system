package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lizhou.service.TeaAttenService05;

public class TeacherAddressServlet05 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TeacherAddressServlet05() {
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

		TeaAttenService05 service = new TeaAttenService05();
		String method = request.getParameter("method");
		if("toTeaATTENListView".equalsIgnoreCase(method)){ //转发到教师列表页
			request.getRequestDispatcher("/WEB-INF/view/other/teaATTENList05.jsp").forward(request, response);
		}else if("photo".equalsIgnoreCase(method)){
			String number=request.getParameter("number");
		    System.out.print(number);
			String realPath=request.getSession().getServletContext().getRealPath("");
			System.out.print(realPath);
		    String msg=service.getBlob(number, realPath);
		    response.getWriter().write(msg);
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

		TeaAttenService05 service = new TeaAttenService05();
		String method = request.getParameter("method");
		if("toTeaATTENListView".equalsIgnoreCase(method)){ //转发到教师列表页
			request.getRequestDispatcher("/WEB-INF/view/other/teaATTENList05.jsp").forward(request, response);
		}else if("TeaATTENList".equalsIgnoreCase(method)){
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			String jsonString = service.queryAll(page,rows);
			response.setCharacterEncoding("response");
			response.setContentType("test/html;charset=utf-8");
			PrintWriter out =  response.getWriter();
			
			out.print(jsonString);
			
			out.flush();
			out.close();
			
		}else if("photo".equalsIgnoreCase(method)){
			String number=request.getParameter("number");
		    System.out.print(number);
			String realPath=request.getSession().getServletContext().getRealPath("");
			System.out.print(realPath);
		    String msg=service.getBlob(number, realPath);
		   /* msg = 
		    E:\实训\7.9\.metadata\.me_tcat7\webapps\ssms\photo\2008.jpeg*/
		    msg = "photo/" + number + ".jpeg";
		    
		    PrintWriter out = response.getWriter();
		    out.print(msg);
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
