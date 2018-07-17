package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lizhou.service.TestService;

public class TestServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TestServlet() {
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

		doPost(request, response);
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

//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
		
		TestService service = new TestService();
		
		String uri = request.getRequestURI().toString(); //查找清单数据
		String opt = uri.split("/")[ uri.split("/").length-1 ];
		if( "TestListView".equals(opt))
		{
			request.getRequestDispatcher("/WEB-INF/view/other/testList.jsp").forward(request, response);
		}else if( "QueryAll".equals(opt) ) 
		{			
			String jsonString = service.queryAll();
			
			response.setCharacterEncoding("response");
			response.setContentType("test/html;charset=utf-8");
			PrintWriter out =  response.getWriter();
			
			out.print(jsonString);
			
			out.flush();
			out.close();
		}else if( "Add".equals(opt) )
		{
			//调用service的新增方法add
			
			String result = "成功";
			
			response.setCharacterEncoding("response");
			response.setContentType("test/html;charset=utf-8");
			PrintWriter out =  response.getWriter();
			
			out.print(result);
			
			out.flush();
			out.close();
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
