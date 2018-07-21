package com.lizhou.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lizhou.bean.Student125;
import com.lizhou.bean.User;
import com.lizhou.service.StudentService125;
/**
 * 学生通讯表servlet
 * @author BiGSon
 */
public class StudentServlet125 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public StudentServlet125() {
		
	}
	
	public void destroy() {
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentService125 service=new StudentService125();
		PrintWriter out=response.getWriter();
		String uri=request.getRequestURL().toString();
		String option=uri.split("/")[uri.split("/").length-1];
		if( "StudentListView".equalsIgnoreCase(option))
		{
			request.getRequestDispatcher("/WEB-INF/view/other/studentAddressBook125.jsp").forward(request, response);
		}else if("StudentList".equalsIgnoreCase(option)){
			request.getRequestDispatcher("/WEB-INF/view/other/studentList125.jsp").forward(request, response);
		}else if("queryAll".equalsIgnoreCase(option)){
			//班级通讯录
			
			//这里需要修改成当前登录学生的年级和班级！！！！！！！！！！
			User user=(User)request.getSession().getAttribute("user");
			Student125 student=service.getStudentByNumber(user.getAccount());
			String result=service.queryStudentByGradeAndClazz(student);
			String realPath=request.getSession().getServletContext().getRealPath("");
			service.getBlob(student, realPath);
			out.print(result);
			out.flush();
			out.close();	
			
		}else if("queryStudentList".equalsIgnoreCase(option)){
			//学生列表分页
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			
			String result=service.queryStudentList(page,rows);
			out.print(result);
			out.flush();
			out.close();
		}else if(option.contains("addStudentList")){
			//查询所有学号
			String[] studentNumberArr=service.getNumber();
			//获得添加信息
			String number=request.getParameter("studentNumber");
			//判断学号是否已经存在
			for(String num:studentNumberArr){
				if(num.equals(number)){
					out.print("fail,学号重复");
					return;
				}
			}
			String name=request.getParameter("studentName");
			String sex=request.getParameter("add_studentSex");
			
			String phone=request.getParameter("studentPhone");
			String qq=request.getParameter("studentQQ");
			int gradeid=Integer.parseInt(request.getParameter("add_studentGrade"));
			int clazzid=Integer.parseInt(request.getParameter("add_studentClass"));
			
			Student125 student=new Student125();
			student.setNumber(number);
			student.setName(name);
			student.setSex(sex);
			student.setPhone(phone);
			student.setQq(qq);
			student.setGradeid(gradeid);
			student.setClazzid(clazzid);
			
			try{
				service.insertStudentList(student);
				out.write("success");
			}catch(Exception e){
				out.write("fail,未知错误");
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
		}else if("queryStudentGrade".equalsIgnoreCase(option)){
			//二级联动
			String result=service.queryGrade();
			out.print(result);
			out.flush();
			out.close();
		}else if(option.contains("queryStudentClassByGradeId")){
			//二级联动
			try{
				int gradeId=Integer.parseInt(request.getParameter("gradeId"));
			}catch(Exception e){
				return;
			}
			int gradeId=Integer.parseInt(request.getParameter("gradeId"));
			String result=service.queryClassByGradeId(gradeId);
			out.print(result);
			out.flush();
			out.close();
		}else if(option.contains("queryStudentListByGrade")){
			//年级下拉框选择年级，表格数据变化
			String gradeName=request.getParameter("gradeName");
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			
			String result=service.queryStudentListByGradeName(gradeName,page,rows);
			out.print(result);
			out.flush();
			out.close();
		}else if(option.contains("queryStudentListByClassCombobox")){
			//班级下拉框选择班级，表格数据变化
			String gradeName=request.getParameter("gradeName");
			String className=request.getParameter("clazzName");
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			String result=service.queryStudentByGradeNameAndClazzName(gradeName, className, page, rows);
			out.print(result);
			out.flush();
			out.close();
		}else if("editStudentList".equalsIgnoreCase(option)){
			//获得修改界面信息
			int id=Integer.parseInt(request.getParameter("edit_studentId"));
			String number=request.getParameter("edit_studentNumber");
			String name=request.getParameter("edit_studentName");
			String sex=request.getParameter("edit_studentSex");
			String phone=request.getParameter("edit_studentPhone");
			String qq=request.getParameter("edit_studentQQ");
			
			int clazzId=Integer.parseInt(request.getParameter("edit_studentClass"));
			int gradeId=Integer.parseInt(request.getParameter("edit_studentGrade"));
			
			Student125 student=new Student125();
			student.setId(id);
			student.setNumber(number);
			student.setName(name);
			student.setSex(sex);
			student.setPhone(phone);
			student.setQq(qq);
			student.setClazzid(clazzId);
			student.setGradeid(gradeId);
			
			try{
				service.updateStudent(student);
				out.write("success");
			}catch(Exception e){
				out.write("fail,未知错误");
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
		}else if("deleteStudentList".equalsIgnoreCase(option)){
			//批量删除学生
			//id数组
			String[] idArray=request.getParameterValues("idArray[]");
			//学号数组
			String[] numberArray=request.getParameterValues("numberArray[]");
			//id数组
			int[] idArr=new int[idArray.length];
			//字符串数组转成int数组
			for(int i=0;i<idArray.length;i++){
				idArr[i]=Integer.parseInt(idArray[i]);
			}
			
			if(service.deleteStudent(idArr, numberArray)==true){
				out.print("success");
			}else{
				out.print("fail");
			}
			out.flush();
			out.close();
		}
	}
	
	public void init() throws ServletException {
		
	}

}
