package com.lizhou.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lizhou.bean.Student;
import com.lizhou.bean.User;
import com.lizhou.service.StudentService;

public class StudentServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public StudentServlet() {
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

		doPost(request,response);
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

      String method = request.getParameter("method");
      StudentService stuService=new StudentService();
      User user= (User)request.getSession().getAttribute("user");
	    String account =user.getAccount();
      
		
		if("LoginOut".equals(method)){ //�˳�ϵͳ
			loginOut(request, response);
		} else if("toStudentPersonalView".equals(method)){ //��ѧ�������Ϣ����
			    boolean flag=false;
			    request.setAttribute("flag", flag);
			    request.setAttribute("photoname", "1");
			    int id= stuService.getStudentId(account);//��ȡѧ��id
			    List<Object> list=stuService.getStudent(id);//��ȡobj����
			    Student[] stuarr=list.toArray(new Student[0]);
			    Student stu=new Student();
			    for(Student student:stuarr)
			    { 
			    	stu.setId(id);
			    	stu.setNumber(student.getNumber());
			    	stu.setName(student.getName());
			    	stu.setSex(student.getSex());
			    	stu.setPhone(student.getPhone());
			    	stu.setQq(student.getQq());
			    	stu.setClazzname(student.getClazzname());
			    	stu.setGradename(student.getGradename());
			    }
     		   
			    String realPath=request.getSession().getServletContext().getRealPath("");
			    stuService.getBlob(id, realPath);
			    
			    request.setAttribute("stu", stu);//����ݵ�ǰ̨
			    request.getRequestDispatcher("/WEB-INF/view/student/studentPersonal.jsp").forward(request, response);
		      
		} else if("update".equals(method))
		{ 
			   String sex=request.getParameter("sex");
			   if("1".equals(sex))
			   {
				   sex="男";
			   }else{
				   sex="女";
			   }
			   String phone=request.getParameter("phone");
			   String qq=request.getParameter("qq");
			   String number=request.getParameter("id");
			   PrintWriter pw=response.getWriter();
			    String msg=null;
			   try {
				   stuService.update(sex, phone, qq, number);
				   msg="success";
				} catch (Exception e) {
					 msg="false";
					e.printStackTrace();
				}
			  pw.write(msg);
			  pw.flush();
			  pw.close();
			 
		}else if("EditPasswod".equals(method))
		{
			   String psd=stuService.getPsd(account);//ԭʼ����
			   System.out.println(account+"-------------"+psd+"----------");
			   String oldPsd=request.getParameter("old_password");//ǰ̨�����ľ�����
			   System.out.println(oldPsd+"++++");
			   String newPsd=request.getParameter("new_password");//������
			   String rePsd=request.getParameter("re_password");
			   System.out.println(newPsd+"++++"+"------"+rePsd);
			   PrintWriter pw=response.getWriter();
			   String msg=null;
			   if(psd.equals(oldPsd))
			   {
				   if(newPsd.equals(rePsd)){
				   try {
					   stuService.updatePsd(account,newPsd);
					   msg="success";
					} catch (Exception e) {
						 msg="false";
						e.printStackTrace();
					}}else{
						msg="newPwderror";
						
					}
			   }else{
				   msg="oldPwderror";
				   
			   }
			  
			  pw.write(msg);
			  pw.flush();
			  pw.close();
			
			
		}else if("uploadphoto".equals(method))
		{

			String realPath = request.getServletContext().getRealPath("/photo");
			String photoname=null;
			String path=null;
			File file = new File(realPath);
			if( file.exists() == false )
			{
				file.mkdir();//����Ŀ¼
			}
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8"); 
			
			try {
				List<FileItem> list = upload.parseRequest(request);
				
				System.out.println("�ļ�" + list.size());
				
				for( FileItem item : list )
				{
					if(item.isFormField()){
						//�������?��ͨ�ı?�ؼ�
						
					}else
					{
						// �õ��ļ�����
						String name = item.getName();
						//String type = item.getContentType()
						String type = name.substring(name.lastIndexOf(".")-1);
						System.out.print(type + "�ļ���׺��");
						
						Random random = new Random();
						int rad = random.nextInt(10000); 
						
						String newName = new Date().getTime() + "_" + rad + "." + type;
						photoname=newName;
						System.out.println(realPath + "\\" +  newName);
						path=realPath + "\\" +  newName;
						FileOutputStream fos = new FileOutputStream( realPath + "/" +  newName);
						InputStream is = item.getInputStream();
						BufferedInputStream bis = new BufferedInputStream(is);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						
						byte[] bytes = new byte[256];
						int len = -1;
						while((len = bis.read(bytes))!=-1)
						{
							baos.write(bytes , 0 , len);
						}
						
						fos.write(baos.toByteArray());
						
						fos.flush();
						fos.close();
						bis.close();
						is.close();
						
						item.delete();

					}
					}
				 int id= stuService.getStudentId(account);//��ȡѧ��id
				    List<Object> list1=stuService.getStudent(id);//��ȡobj����
				    Student[] stuarr=list1.toArray(new Student[0]);
				    Student stu=new Student();
				    for(Student student:stuarr)
				    { 
				    	stu.setId(id);
				    	stu.setNumber(student.getNumber());
				    	stu.setName(student.getName());
				    	stu.setSex(student.getSex());
				    	stu.setPhone(student.getPhone());
				    	stu.setQq(student.getQq());
				    	stu.setClazzname(student.getClazzname());
				    	stu.setGradename(student.getGradename());
				    	}
				boolean flag=true;
				request.setAttribute("flag", flag);
                request.setAttribute("photoname", photoname);
                request.setAttribute("stu", stu);//����ݵ�ǰ̨
                System.out.println(photoname);
                stuService.insertphoto(id, path.replaceAll("\\\\", "/")); 
				request.getRequestDispatcher("/WEB-INF/view/student/studentPersonal.jsp").forward(request, response);
				
				
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
	}
	private void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�˳�ϵͳʱ���ϵͳ��¼���û�
		request.getSession().removeAttribute("user");
		String contextPath = request.getContextPath();
		//ת������¼����
		response.sendRedirect(contextPath+"/index.jsp");
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
