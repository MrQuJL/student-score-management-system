<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>课程列表</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/css/demo.css">
	<script type="text/javascript" src="<%=path%>/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path%>/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	var loadCourse = function() {
		$('#dataList').datagrid({
	        title:'课程列表', 
	        iconCls:'icon-more', //图标 
	        border: true, 
	        collapsible: false, //是否可折叠的 
	        fit: true, //自动大小 
	        method: "post",
	        url:"CourseServlet/courseList?rows=5&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: false,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName: 'id',
	        sortOrder: 'DESC',
	        remoteSort: false,
	        columns: [[
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},
 		        {field:'name',title:'课程名称',width:200},
	 		]],
	        toolbar: "#toolbar"
	    });
		var p = $('#dataList').datagrid('getPager');
	    //设置分页控件
	    $(p).pagination({ 
	        pageSize: 5,//每页显示的记录条数，默认为10 
	        pageList: [5,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    });
	    $(".pagination").css({"margin-top":"-26px"});
	};
	$(function() {	
		//datagrid初始化 
	    loadCourse();
	    var p = $('#dataList').datagrid('getPager');
	    //设置分页控件
	    $(p).pagination({ 
	        pageSize: 5,//每页显示的记录条数，默认为10 
	        pageList: [5,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    });
	    $(".pagination").css({"margin-top":"-26px"});
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var courseId = selectRow.id;
            	$.messager.confirm("消息提醒", "将删除与班级相关的所有数据(包括学生)，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "CourseServlet/DeleteCourse",
							data: {courseId: courseId},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									// $("#dataList").datagrid("reload");
									loadCourse();
									//设置分页控件
								    $(p).pagination({ 
								        pageSize: 5,//每页显示的记录条数，默认为10 
								        pageList: [5,20,30,50,100],//可以设置每页记录条数的列表 
								        beforePageText: '第',//页数文本框前显示的汉字 
								        afterPageText: '页    共 {pages} 页',
								        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
								    });
								    $(".pagination").css({"margin-top":"-26px"});
								} else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	  	
	  	//设置添加学生窗口
	    $("#addDialog").dialog({
	    	title: "添加课程",
	    	width: 500,
	    	height: 400,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						}

						//var gradeid = $("#add_gradeList").combobox("getValue");

						$.ajax({
							type: "post",
							url: "CourseServlet/AddCourse",
							data: $("#addForm").serialize(),
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","添加成功!","info");
									//关闭窗口
									$("#addDialog").dialog("close");
									//清空原表格数据
									$("#add_name").textbox('setValue', "");
									// 添加成功后重新加载课程
						  			loadCourse();
								} else{
									$.messager.alert("消息提醒","添加失败!","warning");
									return;
								}
							}
						});
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#add_name").textbox('setValue', "");
						//重新加载年级
						$("#add_gradeList").combobox("reload");
					}
				},
			]
	    });
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0">
	</table> 
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;">
			<a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
		</div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;">
			<a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>
		</div>
		<div style="float: left;margin: 0 0 0 -3px;" class="datagrid-btn-separator"></div>
		<div style="float:left;margin:3px 2px 0 4px;">课程：<input id="gradeList" class="easyui-textbox" name="grade" /></div>
		<div style="float: left;">
			<a id="query" class="easyui-linkbutton">查询</a>
		</div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px">
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>课程名称:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
<script type="text/javascript">
	//模糊查询
	$("#query").click(function(){
		var courseName = $(".validatebox-text").val();
		$('#dataList').datagrid({
	        title:'课程列表',
	        iconCls:'icon-more', // 图标 
	        border: true,
	        collapsible: false,// 是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"CourseServlet/courseList?page=1&rows=5&courseName=" + courseName + "&t="+new Date().getTime(),
	        idField:'id',
	        singleSelect: false,//是否单选
	        pagination: true,//分页控件
	        rownumbers: true,//行号
	        sortName: 'id',
	        sortOrder: 'DESC',
	        remoteSort: false,
	        columns: [[
				{field:'chk',checkbox: true,width:50},
			        {field:'id',title:'ID',width:50, sortable: true},
			        {field:'name',title:'课程名称',width:200},
	 		]],
	        toolbar: "#toolbar"
	    });
		var p = $('#dataList').datagrid('getPager');
	    //设置分页控件 
	    $(p).pagination({
	        pageSize: 5,//每页显示的记录条数，默认为10 
	        pageList: [5,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	        onSelectPage: function (pageNumber, pageSize) {
	            PageDataGridView(pageNumber, pageSize);//重新加载
	        }
	    });
	    $(".pagination").css({"margin-top":"-26px"});
	});

	var PageDataGridView = function(pi,ps) {
		$('#dataList').datagrid("reload","CourseServlet/courseList?page="+pi+"&rows="+ps+"&t="+new Date().getTime());
	}
</script>
</body>
</html>