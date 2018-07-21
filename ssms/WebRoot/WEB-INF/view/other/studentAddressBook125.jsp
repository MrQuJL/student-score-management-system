<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String imgPath=request.getSession().getServletContext().getRealPath("")+"/photo/";
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("text/html;charset=UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>学生通讯录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	$(function(){
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'学生列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"StudentServlet/queryAll",
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	        rownumbers: true,//行号 
	        sortName: 'id',
	        sortOrder: 'asc', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'number',title:'学号',width:100},
 		       	{field:'name',title:'姓名',width:100},
 		      	{field:'sex',title:'性别',width:100},
 		      	{field:'phone',title:'电话',width:100},
 		      	{field:'qq',title:'QQ',width:100},
 		      	{field:'clazzName',title:'班级',width:100},
 		        {field:'gradeName',title:'年级',width:100},
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	  	//设置工具类按钮
	    $("#lookOver").click(function(){
	    	var selectRow=$("#dataList").datagrid("getSelected");
	    	if(selectRow==null){
	    		$.messager.alert("消息提醒", "请选择要查看的数据!", "warning");
	    	}else{
	    		$("#studentInfo").dialog("open");
	    	}
	    });
	  	//设置学生信息窗口
	    $("#studentInfo").dialog({
	    	title: "学生信息",
	    	width: 600,
	    	height: 500,
	    	iconCls: "icon-play",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	onOpen:function(){
	    		var selectRow=$("#dataList").datagrid("getSelected");
	    		var studentNumber=selectRow.number;
				var studentName=selectRow.name;
				var studentSex=selectRow.sex;
				var studentPhone=selectRow.phone;
				var studentQQ=selectRow.qq;
				var studentGrade=selectRow.gradeName;
				var studentClass=selectRow.clazzName;
				$("#studentNumber").textbox('setValue',studentNumber);
				$("#studentNumber").textbox('readonly',true);
				$("#studentName").textbox('setValue',studentName);
				$("#studentName").textbox('readonly',true);
				$("#studentSex").combobox('setValue',studentSex);
				$("#studentSex").combobox('readonly',true);
				$("#studentTele").textbox('setValue',studentPhone);
				$("#studentTele").textbox('readonly',true);
				$("#studentQQ").textbox('setValue',studentQQ);
				$("#studentQQ").textbox('readonly',true);
				$("#studentGrade").textbox('setValue',studentGrade);
				$("#studentGrade").textbox('readonly',true);
				$("#studentClass").textbox('setValue',studentClass);
				$("#studentClass").textbox('readonly',true);
				
 				//var studentImg='/studentImg/' + studentNumber+ '.jpeg';
 				
 				$("img").attr({src: "/ssms/photo/"+studentNumber+".jpeg"});
	    	}
	    });
	  
	  
	});
	//初始化结束
</script>
  </head>
  
  <body>
    <!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	</table> 
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="lookOver" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查看</a></div>		
	</div>
	
	<!-- 学生信息窗口 -->
	<div id="studentInfo" name="studentInfo" style="padding: 10px">  
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>学号:</td>
	    			<td><input id="studentNumber" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="studentNumber" /></td>
 	    			<td rowspan="7" valign="top"><img alt="暂无图片" src="" id="studentImag" name="studentImag" style="width: 200px; height: 200px;"></td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="studentName" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="studentName" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><input id="studentSex" class="easyui-combobox" name="studentSex"  style="width: 200px; height: 30px;"  /></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="studentTele" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="studentTele" /></td>
	    		</tr>
	    		<tr>
	    			<td>QQ:</td>
	    			<td><input id="studentQQ" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="studentQQ" /></td>
	    		</tr>
	    		<tr>
	    			<td>年级:</td>
	    			<td><input id="studentGrade" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="studentGrade" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td><input id="studentClass" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="studentClass" /></td>
	    		</tr>
	    	</table>
	</div>
  </body>
</html>
