<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>考试列表</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/easyui/css/demo.css">
	<script type="text/javascript" src="<%=path%>/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path%>/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/easyui/js/validateExtends.js"></script>
	<script type="text/javascript" src="<%=path%>/echarts/echarts.js"></script>
	<script type="text/javascript">
	var scoreMgr = { // 一个全局的对象，用于记录当前登记的成绩的考试id
		examId : 0
	};
	$(function() {
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'考试列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"ExamServlet39/ExamList?t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: false,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},
 		        {field:'examName',title:'考试名称',width:100},
 		        {field:'examTime',title:'考试时间',width:100},
 		        {field:'examType',title:'考试类型',width:100},
 		        {field:'gradeId',title:'考试年级id',width:0,hidden:true},
 		        {field:'gradeName',title:'考试年级',width:100},
 		        {field:'clazzId',title:'考试班级id',width:0,hidden:true},
 		        {field:'clazzName',title:'考试班级',width:100},
 		        {field:'courseId',title:'考试科目id',width:0,hidden:true},
 		        {field:'courseName',title:'考试科目',width:100},
 		        {field:'remark',title:'备注',width:200}
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	   
	    // 打开添加考试对话框
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    // 登记成绩按钮
	    $("#regScore").click(function(){
	    	// 1. 获取当前选中的考试id，考试年级，考试班级，考试课程
	    	var selectedRows = $('#dataList').datagrid("getSelections");	
	    	if (selectedRows == null || selectedRows.length < 1) {
				$.messager.alert("消息提醒","请先选择要登记成绩的考试!","warning");
				return;
	    	}
	    	// 保存取到的值
	    	var examId = "";
	    	var gradeId = ""; // 年级id
	    	var examGrade = "";
	    	var clazzId = ""; // 班级id
	    	var examClazz = "";
	    	var courseId = ""; // 课程id
	    	var examCourse = "";
			for( var i = 0 ; i < selectedRows.length ; i++ )
			{
				var row = selectedRows[i];
				
				examId = row.id == undefined ? "" : row.id;
				scoreMgr.examId = examId; // 设置一个全局的考试id属性
				gradeId = row.gradeId == undefined ? "" : row.gradeId; // 年级id
				examGrade = row.gradeName == undefined ? "" : row.gradeName;
				clazzId = row.clazzId == undefined ? "" : row.clazzId; // 班级id
				examClazz = row.clazzName == undefined ? "" : row.clazzName;
				courseId = row.courseId == undefined ? "" : row.courseId; // 课程id
				examCourse = row.courseName == undefined ? "" : row.courseName;
				
				/* alert("考试id:" + examId
				+ "\r\n考试年级:" + examGrade 
				+ "\r\n年级id:" + gradeId 
				+ "\r\n考试班级:" + examClazz 
				+ "\r\n班级id:" + clazzId 
				+ "\r\n课程:" + examCourse
				+ "\r\n课程id:" + courseId); */
			}
	    	$('#add_course_score').combobox("clear");
			$("#inputScoreDialog").dialog("open");
			
	    	// 2. 加载相应的班级列表
	    	$('#add_clazz_score').combobox({
	    	    valueField:'id',
	    	    textField:'name',
	    	    method: 'post',
	    	    url:'/ssms/ClazzServlet?method=ClazzList&gradeid='+gradeId+'&t='+new Date().getTime(),  
	    	    onLoadSuccess : function() { // 必须使用onloadSunnces做成一个伪同步
	    	    	if (clazzId != "") { // 班级加载完毕之后，班级不为空则选中班级，同时加载该班级下的课程
	    	    		$('#add_clazz_score').combobox('select', clazzId);
	    	    		var curl = '/ssms/CourseServlet/getCourseListByClazzId?clazzid='+clazzId+'&t='+new Date().getTime();
	    	    		$('#add_course_score').combobox({
	    	    			valueField:'id',
	    		    	    textField:'name',
	    		    	    method: 'post',
	    		    	    url: curl,
	    		    	    onLoadSuccess: function(){ // 课程加载完毕后，课程id不为空则选中对应的课程
	    		    	    	if (courseId != "") { 
	    		    	    		$('#add_course_score').combobox('select', courseId);
	    		    	    	}
	    		    	    }
	    	    		});
	    	    	}
	    	    }
	    	});
	    	
	    	// 班级id和课程id均不为空的时候就直接查询对应的学生成绩
	    	// 有一个为空就不查
	    	if (clazzId != "" && courseId != "") {
	    		loadStudentScore(examId, clazzId, courseId);
	    	}
	    });
	    
	  	//设置添加考试窗口
	    $("#addDialog").dialog({
	    	title: "添加考试",
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
					iconCls:'icon-world-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						}
						$.ajax({
							type: "post",
							url: "ExamServlet39/AddExam",
							data: $("#addForm").serialize(),
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","添加成功!","info");
									//关闭窗口
									$("#addDialog").dialog("close");
									//清空原表格数据
									$("#add_name").textbox('setValue', "");
									$("#add_time").textbox('setValue', "");
									$("#add_type").combobox("clear");
									$("#add_grade").combobox("clear");
									$("#add_clazz").combobox("clear");
									$("#add_course").combobox("clear");
						  			$("#add_remark").textbox('setValue', "");
									//重新刷新页面数据
						  			$('#dataList').datagrid("reload");
								} else{
									$.messager.alert("消息提醒", msg ,"warning");
									return;
								}
							}
						});
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-world-reset',
					handler:function(){
						$("#add_name").textbox('setValue', "");
						$("#add_time").textbox('setValue', "");
						$("#add_type").combobox("clear");
						$("#add_grade").combobox("clear");
						$("#add_clazz").combobox("clear");
						$("#add_course").combobox("clear");
			  			$("#add_remark").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  	// 设置成绩登记窗口
	    $("#inputScoreDialog").dialog({
	    	title: "成绩登记",
	    	width: 800,
	    	height: 400,
	    	iconCls: "icon-pencil-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    });
	});
	
	// 封装了加载学生成绩信息
	var loadStudentScore = function(examId, clazzId, courseId){
		// alert(examId);
		// alert(clazzId);
		// alert(courseId);
		$('#scoreList').datagrid({
	        fit: true,//自动大小 
	        method: "post",
	        url:"ExamServlet39/ScoreList?examId="+examId+"&clazzId="+clazzId
	        	+"&courseId="+courseId+"&t="+new Date().getTime(),
	        idField:'id',
	        singleSelect: true,//是否单选 
	        pagination: false,//分页控件 
	        rownumbers: true,//行号 
	        remoteSort: false,
	        columns: [[
 		        {field:'id',title:'唯一id',width:120,hidden:true},
 		        {field:'examId',title:'考试id',width:120,hidden:true},
 		        {field:'clazzId',title:'班级id',width:120,hidden:true},
 		        {field:'courseId',title:'课程id',width:120,hidden:true},
 		        {field:'stuId',title:'学生id',width:120,hidden:true},
 		        {field:'number',title:'学号',width:120},
 		        {field:'stuName',title:'姓名',width:120},
				{
					field:'score', title:$("#add_course_score").combobox("getText"),width:100,align:'center',
                    formatter:function(val,row,index)
					{
						return "<input value='" + row.score + "' id='txtScore" + index + "' style='width:80px' type='text' />";
					}
               	}
	 		]]
	    });
	}
	
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
		<div style="float:left;">
			<a id="regScore" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-pencil-add',plain:true">登记成绩</a>
		</div>
		<div style="float:left;">
			<a id="countScore" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-chart_bar',plain:true">成绩统计</a>
		</div>
	</div>
	
	<!-- 添加数据窗口 -->
	<div id="addDialog" style="padding: 10px;">
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>名称:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'考试名称不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>考试时间:</td>
	    			<td>
	    				<input id="add_time" type="text" name="time" style="width: 200px; height: 30px;" class="easyui-datebox" data-options="required:true, missingMessage:'考试时间不能为空',
	    					" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>考试类型:</td>
	    			<td>
	    				<select id="add_type" class="easyui-combobox" name="type" style="width:200px;height:30px;">   
						    <option value="1">年级统考</option>
						    <option value="2">平时考试</option>
						</select>
	    			</td>
	    		</tr>
	    		<!-- start:年级-班级-课程三级联动 -->
	    		<tr>
	    			<td>年级:</td>
	    			<td>
	    				<input id="add_grade" style="width: 200px; height: 30px;" class="easyui-combobox" name="grade" value="" data-options="required:true, missingMessage:'年级不能为空',
	    					valueField: 'id',
	    					textField: 'name',
	    					method: 'post',
	    					url: '/ssms/GradeServlet?method=GradeList&t='+new Date().getTime(),
	    					onSelect:function(rec){
	    						$('#add_clazz').combobox({
	    							valueField: 'id',
			    					textField: 'name',
			    					method: 'post',
			    					url: '/ssms/ClazzServlet?method=ClazzList&gradeid='+rec.id+'&t='+new Date().getTime(),
			    					onLoadSuccess: function(){
			    						$('#add_clazz').combobox('clear');
			    						$('#add_course').combobox('clear');
			    						$('#add_course').combobox('loadData',{});
			    					}
	    						});
	    					}
	    				" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td>
	    				<input id="add_clazz" style="width: 200px; height: 30px;" class="easyui-combobox" name="clazz" value="" data-options="
	    					valueField: 'id',
	    					textField: 'name',
	    					method: 'post',
	    					onSelect:function(rec){
	    						$('#add_course').combobox({
	    							valueField: 'id',
			    					textField: 'name',
			    					method: 'post',
			    					url: '/ssms/CourseServlet/getCourseListByClazzId?clazzid='+rec.id+'&t='+new Date().getTime(),
			    					onLoadSuccess:function(){
			    						$('#add_course').combobox('setValue','');
			    					}
	    						});
	    					}
	    				" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>课程:</td>
	    			<td>
	    				<input id="add_course" style="width: 200px; height: 30px;" class="easyui-combobox" name="course" value="" data-options="
	    					valueField: 'id',
	    					textField: 'name'
	    				"/>
	    			</td>
	    		</tr>
	    		<!-- end:年级-班级-课程三级联动 -->
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 60px;" class="easyui-textbox" type="text" name="remark" data-options="multiline:true" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<!-- 成绩登记窗口 -->
	<div id="inputScoreDialog" class="easyui-dialog" title="成绩登记" 
		style="left:140px;top:50px;" toolbar="#score-toolbar">
		<!-- 成绩列表 -->
    	<table id="scoreList" cellspacing="0" cellpadding="0">
		</table>
	</div>
	<!-- 成绩登记窗口的toolbar -->
	<div id="score-toolbar">
		<table cellpadding="0" cellspacing="0" >
			<tr>
				<td>
					<a id="saveScore" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true">提交</a>
					<a id="cancelScore" href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">清空</a>
				</td>
    			<td>
    				&nbsp;&nbsp;班级:<input id="add_clazz_score" style="width: 150px; height: 25px;" class="easyui-combobox" name="clazz" value="" data-options="
    					valueField: 'id',
    					textField: 'name',
    					method: 'post',
    					onSelect:function(rec){
    						$('#add_course_score').combobox({
    							valueField: 'id',
		    					textField: 'name',
		    					method: 'post',
		    					url:'/ssms/CourseServlet/getCourseListByClazzId?clazzid='+rec.id+'&t='+new Date().getTime(),
		    					onLoadSuccess:function(){
		    						$('#add_course_score').combobox('setValue','');
		    					}
    						});
    					}
    					"/>
    			</td>
    			<td>
    				&nbsp;&nbsp;课程:<input id="add_course_score" style="width: 150px; height: 25px;" class="easyui-combobox" name="course" value="" data-options="
    					valueField: 'id',
    					textField: 'name',
    					onSelect: function(rec){
    						if ($('#add_clazz_score').combobox('getValue') == '') {
    							$.messager.alert('消息提醒','请先选择要登记成绩的考试!','warning');
    							return;
    						}
    						loadStudentScore(scoreMgr.examId, $('#add_clazz_score').combobox('getValue') , rec.id);
    					}
    				"/>
    			</td>
			</tr>
		</table>
	</div>
	<!-- 成绩统计窗口 -->
	<div id="countScoreDialog" class="easyui-dialog" title="成绩统计" 
		style="left:140px;top:50px;" toolbar="#count-score-toolbar"
		data-options="iconCls:'icon_chart-bar',resizable:true,modal:true,closed:true">
		
		<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    	<div id="mainContainer" style="width: 600px;height:400px;"></div>
    	
	</div>
	<!-- 成绩统计窗口的toolbar -->
	<div id="count-score-toolbar">
		<table cellpadding="0" cellspacing="0">
			<tr>
    			<td>
    				&nbsp;&nbsp;班级:<input id="add_clazz_score_count" style="width: 150px; height: 25px;" class="easyui-combobox" name="clazz" value="" data-options="
    					valueField: 'id',
    					textField: 'name',
    					method: 'post',
    					onSelect:function(rec){
    						$('#add_course_score_count').combobox({
    							valueField: 'id',
		    					textField: 'name',
		    					method: 'post',
		    					url:'/ssms/CourseServlet/getCourseListByClazzId?clazzid='+rec.id+'&t='+new Date().getTime(),
		    					onLoadSuccess:function(){
		    						$('#add_course_score_count').combobox('setValue','');
		    					}
    						});
    					}
    					"/>
    			</td>
    			<td>
    				&nbsp;&nbsp;课程:<input id="add_course_score_count" style="width: 150px; height: 25px;" class="easyui-combobox" name="course" value="" data-options="
    					valueField: 'id',
    					textField: 'name',
    					onSelect: function(rec){
    						if ($('#add_course_score_count').combobox('getValue') == '') {
    							$.messager.alert('消息提醒','请先选择要登记成绩的考试!','warning');
    							return;
    						}
    						loadScoreChart(scoreMgr.examId, $('#add_course_score_count').combobox('getValue'), rec.id);
    					}
    				"/>
    			</td>
			</tr>
		</table>
	</div>
	
<script type="text/javascript">
	// 提交登记的成绩
	$("#saveScore").click(function(){
		var rows = $('#scoreList').datagrid("getRows");
		// alert("长度：" + rows.length);
		// 1. 提交之前校验每个输入框中的成绩格式是否合法
		for(var i = 0 ; i < rows.length ; i++)
		{
			var id = rows[i].id;
			var number = rows[i].number;
			// 注：此处获取到的成绩时编辑之后的成绩
			var score = $("#txtScore"+i).val();// rows[i].score;
			// alert("唯一确定一条记录的id:" + id	+ "score:" + score);
			// 先判断输入的数据是否是数字
			if (!isDigit(score)) {
				$.messager.alert("消息提醒","学号为"+number+"的学生的成绩格式输入有误!","warning");
				return false;
			}
			// 再判断输入数字的范围是否合法
			if (score < 0 || score > 150) {
				$.messager.alert("消息提醒","学号为"+number+"的学生的成绩输入范围非法!","warning");
				return false;
			}
		}
		// 2.全部数据均合法之后再提交更新成绩
		for(var i = 0 ; i < rows.length ; i++)
		{
			var id = rows[i].id;
			var number = rows[i].number;
			var score = $("#txtScore"+i).val();// rows[i].score --> 数据库中的值
			updateScore(id, score);
		}
		// 3.重新加载学生成绩
		loadStudentScore(scoreMgr.examId, $("#add_clazz_score").combobox("getValue"),
			$("#add_course_score").combobox("getValue"));
		// 4.提示更新成功
		$.messager.alert('提示','登记学生成绩成功!','info');
	});
	
	// 取消登记的成绩
	$("#cancelScore").click(function(){
		var rows = $('#scoreList').datagrid("getRows");
		for(var i = 0 ; i < rows.length ; i++)
		{
			$("#txtScore"+i).val(""); // 将输入框中的成绩全部置为空
		}
	});
	
	// 判断字符串是否是数字
	var isDigit = function(str){
		// isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
	    if(str === "" || str ==null || !(/^\d+$/).test(str)){
	        return false;
	    }
	    if(!isNaN(str)){
	        return true;
	    }else{
	        return false;
	    }
	}
	
	// 更新学生的成绩,返回true或者false来表示更新成功或者失败
	var updateScore = function(id, score){
		$.ajax({
			type: "POST",
			url: "/ssms/ExamServlet39/updateScore",
			data: {"id":id,"score":score},
			dataType: "text",
		});
	}
	
	// 成绩统计按钮
	$("#countScore").click(function(){
		// 首先销毁旧图表
		echarts.dispose(document.getElementById('mainContainer'));
		// 1. 获取当前选中的考试id，考试年级，考试班级，考试课程
    	var selectedRows = $('#dataList').datagrid("getSelections");	
    	if (selectedRows == null || selectedRows.length < 1) {
			$.messager.alert("消息提醒","请先选择要统计成绩的考试!","warning");
			return;
    	}
    	
    	// 2.保存取到的值
    	var examId = "";
    	var gradeId = ""; // 年级id
    	var examGrade = "";
    	var clazzId = ""; // 班级id
    	var examClazz = "";
    	var courseId = ""; // 课程id
    	var examCourse = "";
		for( var i = 0 ; i < selectedRows.length ; i++ )
		{
			var row = selectedRows[i];
			
			examId = row.id == undefined ? "" : row.id;
			scoreMgr.examId = examId; // 设置一个全局的考试id属性
			gradeId = row.gradeId == undefined ? "" : row.gradeId; // 年级id
			examGrade = row.gradeName == undefined ? "" : row.gradeName;
			clazzId = row.clazzId == undefined ? "" : row.clazzId; // 班级id
			examClazz = row.clazzName == undefined ? "" : row.clazzName;
			courseId = row.courseId == undefined ? "" : row.courseId; // 课程id
			examCourse = row.courseName == undefined ? "" : row.courseName;
		}
		$('#add_course_score_count').combobox("clear");
		$("#countScoreDialog").dialog("open");
		
    	// 2. 加载相应的班级列表
    	$('#add_clazz_score_count').combobox({
    	    valueField:'id',
    	    textField:'name',
    	    method: 'post',
    	    url:'/ssms/ClazzServlet?method=ClazzList&gradeid='+gradeId+'&t='+new Date().getTime(),  
    	    onLoadSuccess : function() { // 必须使用onloadSunnces做成一个伪同步
    	    	if (clazzId != "") { // 班级加载完毕之后，班级不为空则选中班级，同时加载该班级下的课程
    	    		$('#add_clazz_score_count').combobox('select', clazzId);
    	    		var curl = '/ssms/CourseServlet/getCourseListByClazzId?clazzid='+clazzId+'&t='+new Date().getTime();
    	    		$('#add_course_score_count').combobox({
    	    			valueField:'id',
    		    	    textField:'name',
    		    	    method: 'post',
    		    	    url: curl,
    		    	    onLoadSuccess: function(){ // 课程加载完毕后，课程id不为空则选中对应的课程
    		    	    	if (courseId != "") { 
    		    	    		$('#add_course_score_count').combobox('select', courseId);
    		    	    	}
    		    	    }
    	    		});
    	    	}
    	    }
    	});
    	
    	// 班级id和课程id均不为空的时候就直接查询对应的学生成绩
    	// 有一个为空就不查
    	/* if (clazzId != "" && courseId != "") {
    		loadStudentScore(examId, clazzId, courseId);
    	} */
    	
    	// 加载echarts图表
		// loadScoreChart();
	});
	
	// 加载成绩统计图表
	var loadScoreChart = function(examId, clazzId, courseId) {
		// 统计某次考试，某个班级，某个科目的学生成绩分布情况
		// A: 0~90
		// B: 90~100
		// C: 100~120
		// D: 120~140
		// E: 140~150
		
		// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('mainContainer'));

		// 此处去后台请求数据------------------------------
		// 准备好echarts所需的option对象
        var option = {
       	    backgroundColor: '#2c343c',

       	    title: {
       	        text: '2013级1班语文考试成绩分布图',
       	        left: 'center',
       	        top: 20,
       	        textStyle: {
       	            color: '#ccc'
       	        }
       	    },

       	    tooltip : {
       	        trigger: 'item',
       	        formatter: "{a} <br/>{b} : {c} ({d}%)"
       	    },

       	    visualMap: {
       	        show: false,
       	        min: 80,
       	        max: 600,
       	        inRange: {
       	            colorLightness: [0, 1]
       	        }
       	    },
       	    series : [
       	        {
       	            name:'分值范围',
       	            type:'pie',
       	            radius : '55%',
       	            center: ['50%', '50%'],
       	            data:[
       	                {value:335, name:'0~90'},
       	                {value:310, name:'90~100'},
       	                {value:274, name:'100~120'},
       	                {value:235, name:'120~140'},
       	                {value:400, name:'140~150'}
       	            ].sort(function (a, b) { return a.value - b.value; }),
       	            roseType: 'radius',
       	            label: {
       	                normal: {
       	                    textStyle: {
       	                        color: 'rgba(255, 255, 255, 0.3)'
       	                    }
       	                }
       	            },
       	            labelLine: {
       	                normal: {
       	                    lineStyle: {
       	                        color: 'rgba(255, 255, 255, 0.3)'
       	                    },
       	                    smooth: 0.2,
       	                    length: 10,
       	                    length2: 20
       	                }
       	            },
       	            itemStyle: {
       	                normal: {
       	                    color: '#c23531',
       	                    shadowBlur: 200,
       	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
       	                }
       	            },

       	            animationType: 'scale',
       	            animationEasing: 'elasticOut',
       	            animationDelay: function (idx) {
       	                return Math.random() * 200;
       	            }
       	        }
       	    ]
       	};
		
     	// 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
	}
	
</script>
</body>
</html>