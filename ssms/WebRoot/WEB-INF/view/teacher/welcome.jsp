<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
	<p style="font-size: 50px; line-height: 60px; height: 60px;">${systemInfo.schoolName}</p>
	<p style="font-size: 25px; line-height: 30px; height: 30px;">欢迎使用学生成绩管理系统</p>
  <hr />
  	<h2>最新通知：</h2>
    <br/>
  	<p><h3>${systemInfo.noticeTeacher}</h3></p>
  	<!-- <p><h3>请各科任教教师尽快登记此次期末考试成绩！！！</h3></p> -->
	
</div>
</body>
</html>