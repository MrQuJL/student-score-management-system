package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.lizhou.bean.Exam;
import com.lizhou.service.ScoreService02;

public class ScoreServlet02 extends HttpServlet {
	ScoreService02 service=new ScoreService02();

	/**
	 * Constructor of the object.
	 */
	public ScoreServlet02() {
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

		String uri=request.getRequestURI().toString();
		String opt = uri.split("/")[ uri.split("/").length-1 ];
		
		if("EscoreListView".equals(opt)){
			request.getRequestDispatcher("/WEB-INF/view/other/scoreList02.jsp").forward(request, response);
		}else if("export".equals(opt)){
			exportScore(request,response);
		}
	}
	private void exportScore(HttpServletRequest request, HttpServletResponse response) {
		//遍历参数
		Enumeration<String> pNames = request.getParameterNames();
		Exam exam = new Exam();
		while(pNames.hasMoreElements()){
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			try {
				BeanUtils.setProperty(exam, pName, value);
			} catch (IllegalAccessException  e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		service.exportScore(response, exam);
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

		String uri=request.getRequestURI().toString();
		String opt = uri.split("/")[ uri.split("/").length-1 ];
		
		if("EscoreListView".equals(opt)){
			request.getRequestDispatcher("/WEB-INF/view/other/scoreList02.jsp").forward(request, response);
		}else if("query".equals(opt)){
			String clazzid=(String)request.getParameter("clazzid");
			String id=(String)request.getParameter("examid");
			String jsonString = service.queryAll(id, clazzid);	
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out =  response.getWriter();
			out.print(jsonString);
			
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
