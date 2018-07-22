<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<base href="<%=basePath%>"> 
	<meta charset="UTF-8">
	<title>班级列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'考试列表', 
	        iconCls:'icon-more',//图标 
	        //pagination:true,
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"<%=path%>/ExamServlet02/queryAll",
	        idField:'id', 
	        singleSelect: true,//是否单选 
	       
	        rownumbers: true,//行号 
	        sortName: 'id',
	        sortOrder: 'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'name',title:'考试名称',width:100},
 		        {field:'etime',title:'考试时间',width:150},
 		        {field:'type',title:'考试类型',width:100,
 		        formatter: function(value,row,index){
 						if (row.type==1){
 							return "年级统考";
 						} else {
 							return "平常考试";
 						}
 					}	
 				},
 		        {field:'grade',title:'考试年级',width:100, 
 		        	formatter: function(value,row,index){
 						if (row.grade){
 							return row.grade.name;
 						} else {
 							return value;
 						}
 					}	
 		        },
 		        {field:'clazz',title:'考试班级',width:100,
 		        	formatter: function(value,row,index){
 						if (row.clazz){
 							return row.clazz.name;
 						} else {
 							return value;
 						}
 					}	
 		        },
 		       {field:'course',title:'考试科目',width:100,
 		       		formatter: function(value,row,index){
 						if (row.course){
 							return row.course.name;
 						} else {
 							return value;
 						}
 					}
 				},
 		       {field:'remark',title:'备注',width:200}
 		        
	 		]], 
	        toolbar:"#toolbar"
	    }); 
       //设置分页控件 
	   /*  var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    });  */
	    //设置工具类按钮
	   
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var id = selectRow.id;
            	$.messager.confirm("消息提醒", "将删除与班级相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							data: {id: id},
							url: "ExamServlet02/delete",
							
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
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
	     //设置工具类按钮
	    $("#count").click(function(){
	    	
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行查询!", "warning");
            } else{
            	$("#scorequery").dialog("open");
            	var gradeid = selectRow.gradeid;
            	var id=selectRow.id;
            	$("#query_clazzList").combobox({
			  		width: "150",
			  		height: "25",
			  		valueField: "id",
			  		textField: "name",
			  		multiple: false, //可多选
			  		editable: false, //不可编辑
			  		method: "post",
			  		url: "ClazzServlet?method=Clazzlist&gradeid="+gradeid,
			  		onSelect: function(query){
	  			
						var url='ScoreServlet02/query?clazzid='+query.id;
						$("#scoreList").datagrid('reload',url);
						
			  		}
				});
            	 $("#scoreList").datagrid({ 
			        border: true, 
			        collapsible: false,//是否可折叠的 
			        fit: true,//自动大小 
			        method: "post",
			        url:"ScoreServlet02/query?examid="+id,
			        idField:'number', 
			        singleSelect: true,//是否单选   
			        rownumbers: true,//行号 
			        sortName: 'number',
			        sortOrder: 'asc', 
			        remoteSort: false,
			        columns: [[  
		 		        {field:'number',title:'学号',width:100, sortable: true},    
		 		        {field:'name',title:'姓名',width:100},
		 		        {field:'chineseScore',title:'语文',width:50},
		 		        {field:'mathScore',title:'数学',width:50},
		 		        {field:'englishScore',title:'英语',width:50},
		 		        {field:'physicsScore',title:'物理',width:50},
		 		        {field:'chemistryScore',title:'化学',width:50},
		 		        {field:'totalScore',title:'总分',width:100},
		 		       
		 		     ]],
		 		     
		   	 	}); 
		   	 	
	    
           }
	    });	
	    
	    //设置成绩统计窗口
	    $("#scorequery").dialog({
	    	title: "成绩统计",
	    	width:600,
	    	height:500,
	    	resizable:true,
	    	iconCls: "icon-chart_bar",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	onClose: function(){
   	        	$("#query_clazzList").combobox("clear");
   	        }
	    	
	  	
		});
		 
	    
	  	//年级下拉框
	  	$("#gradeList").combobox({
	  		width: "150",
	  		height: "25",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		url: "GradeServlet?method=GradeList&t="+new Date().getTime(),
	  		onSelect: function(grade){
	  			
				var url='ExamServlet02/queryAll?grade='+grade.id;
				$("#dataList").datagrid('reload',url);
				var url1='ClazzServlet?method=clazzlist&gradeid='+grade.id;
				$("#clazzList").combobox('reload',url1);
				
	  		}
	    
	  	
	  	});
	  	$("#clazzList").combobox({
	  		width: "150",
	  		height: "25",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		
	  		onSelect: function(clazz){
	  			
				var url='ExamServlet02/queryAll?clazzid='+clazz.id;
				$("#dataList").datagrid('reload',url);
				
				
	  		}
	    
	  	
	  	});
	  	//导出
	  	$("#export").click(function(){
	  		var exam = $("#dataList").datagrid("getSelected");
	  		var clazzid = exam.clazzid;
	    	if(exam.type == 1){
	    		clazzid = $("#query_clazzList").combobox("getValue");
	    	}
        	//var data = {id: exam.id, gradeid: exam.gradeid, clazzid:clazzid,courseid:exam.courseid, type: exam.type};
	    	
        	var url = "ScoreServlet02/export?id="+exam.id+"&gradeid="+exam.gradeid+"&clazzid="+clazzid+"&courseid="+exam.courseid+"&type="+exam.type;
	    	
	  		window.open(url, "_blank");
	  	});
         $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    	$("#add_courseList").combobox({disabled: false});
			$("#add_clazzList").combobox({disabled: false});
	    });
	  	//添加年级下拉框
	  	$("#add_gradeList").combobox({
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		url: "GradeServlet?method=GradeList&t="+new Date().getTime(),
	  		onLoadSuccess: function(){
		  		//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		},
	  		onSelect: function(grade){
	  			
				var url1='ClazzServlet?method=clazzlist&gradeid='+grade.id;
				$("#add_clazzList").combobox('reload',url1);
				
	  		}
	  		
	  	});
	  	//添加班级下拉框
	  	$("#add_clazzList").combobox({
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post"
	  		
	  		
	  		
	  	});
	  	//添加科目下拉框
	  	$("#add_courseList").combobox({
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		url: "CourseServlet/query"
	  		
	  		
	  	});
	  	//添加考试类型下拉框
	  	$("#add_type").combobox({
	  		valueField: "id",
	  		textField: "text",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		data: [{
				id: '1',
				text: '年级统考',
			},{
				id: '2',
				text: '平时考试',
			}],
	  		/*onLoadSuccess: function(){
		  		//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
			
	  		},*/
	  		onSelect: function(type){
	  			
				if(type.id==1){
					$("#add_courseList").combobox({disabled: true});
					$("#add_clazzList").combobox({disabled: true});
				}else{
					$("#add_courseList").combobox({disabled: false});
					$("#add_clazzList").combobox({disabled: false});
				}
				
	  		}
	  		
	  		
	  	});
	  	//设置添加学生窗口
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
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							
							var gradeid = $("#add_gradeList").combobox("getValue");
							var clazzid=$("#add_clazzList").combobox("getValue");
						
							$.ajax({
								type: "post",
								url: "ExamServlet02/addExam",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										
										//重新刷新页面数据
							  			$('#gradeList').combobox("setValue", gradeid);
							  			$('#clazzList').combobox("setValue", clazzid);
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","添加失败!","warning");
										return;
									}
								}
							});
						}
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
	<table id="dataList" cellspacing="0" cellpadding="0" style="padding-bottom:200px;background:#eee;" > 
	</table> 
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="count" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-chart_bar',plain:true">成绩统计</a></div>
		<div style="float: left;margin: 3 10px 0 10px">年级：<input id="gradeList" class="easyui-combobox" name="grade" /></div>
		<div style="float: left;margin: 3 10px 0 10px">班级：<input id="clazzList" class="easyui-combobox" name="clazz" data-options="valueFiled:'id',textFiled:'name'"/></div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" id="addtable">
	    		<tr>
	    			<td>考试名称:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name"  validType="repeat_exam['#add_gradeList']" data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>考试时间:</td>
	    			<td><input id="time" type="text" class="easyui-datebox" required="required" name="time"></input></td>
	    		</tr>
	    		<tr>
	    			<td>考试类型:</td>
	    			<td><select id="add_type" style="width: 200px; height: 30px;" name="type" ></select></td>
	    		</tr>
	    		<tr>
	    			<td>年级:</td>
	    			<td><select id="add_gradeList" style="width: 200px; height: 30px;" name="gradeid" ></select></td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td><select id="add_clazzList" style="width: 200px; height: 30px;" name="clazzid" ></select></td>
	    		</tr>
	    		<tr>
	    			<td>考试科目:</td>
	    			<td><select id="add_courseList" style="width: 200px; height: 30px;" name="courseid" ></select></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="remark  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<!--  成绩统计 -->
	<div id="scorequery" cellspacing="0" cellpadding="0">
		 
		<div id="tool">
			<div style="float: left;"><a id="export" href="javascript:;" class="easyui-linkbutton"   data-options="iconCls:'icon-redo',plain:true" >导出</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
			<div style="float: left;margin: 3 10px 0 10px">班级：<input id="query_clazzList" class="easyui-combobox" name="clazz" data-options="valueFiled:'id',textFiled:'name'"/></div>
		</div>
		<table id="scoreList" cellspacing="0" cellpadding="0"> 
		
		</table>
	</div>
</body>
</html>