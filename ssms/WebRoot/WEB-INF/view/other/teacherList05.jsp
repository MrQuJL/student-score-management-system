<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <base href="<%=basePath%>">    
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>

	<script type="text/javascript">
		$(
			function()
			{
				$("#dataList").datagrid({ 
						title:'教师列表',   
						pagination:true,
						singleSelect:true,
						rownumbers: true,//行号 
			    		url:"TeacherServlet05/queryAll?t="+new Date().getTime(),    
			    		columns:[[   
			   				{field:'chk',checkbox: true,width:50}, 
			        		{field:'id',title:'ID',width:100},    
			        		{field:'number',title:'工号',width:200},   
			        		{field:'name',title:'姓名',width:200},   
			        		{field:'sex',title:'性别',width:200},   
			        		{field:'phone',title:'电话',width:200},   
			        		{field:'qq',title:'QQ',width:200}   
			   			 ]] ,toolbar:"#toolbar"   
					}); 
                //设置分页控件 
			    var p = $('#dataList').datagrid('getPager'); 
			    $(p).pagination({ 
			        pageSize: 10,//每页显示的记录条数，默认为10 
			        pageList: [5,10,20,30,50],//可以设置每页记录条数的列表 
			        beforePageText: '第',//页数文本框前显示的汉字 
			        afterPageText: '页    共 {pages} 页', 
			        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
			    });
				
			   
				//删除
			    $("#delete").click(function(){
			    	var selectRow = $("#dataList").datagrid("getSelections");
		        	if(selectRow.lengh == 0){
		            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
		            } else{
		           		var teacherid = "";
		            	for(var i=0;i<selectRow.length;i++){
		            		teacherid+=selectRow[i].id;
		            	}
		            	
		            	$.messager.confirm("消息提醒", "将删除与课程相关的所有数据(包括成绩)，确认继续？", function(r){
		            		if(r){
		            			$.ajax({
									type: "post",
									url: "TeacherServlet05/deleteTeahcer?t="+new Date().getTime(),
									data: {teacherid: teacherid},
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
			     //设置分页控件 
			    var p = $('#dataList').datagrid('getPager'); 
			    $(p).pagination({ 
			        pageSize: 10,//每页显示的记录条数，默认为10 
			        pageList: [5,10,20,30,50],//可以设置每页记录条数的列表 
			        beforePageText: '第',//页数文本框前显示的汉字 
			        afterPageText: '页    共 {pages} 页', 
			        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
			    });
				//设置工具类按钮
			    $("#add").click(function(){
			    	$("#addDialog").dialog("open");
			    }); 
			    //添加考试类型下拉框
			  	$("#add_sex").combobox({
			  		valueField: "id",
			  		textField: "text",
			  		multiple: false, //可多选
			  		editable: false, //不可编辑
			  		method: "post",
			  		data: [{
						id: '男',
						text: '男',
					},{
						id: '女',
						text: '女',
					}],
			  		onLoadSuccess: function(){
				  		//默认选择第一条数据
						var data = $(this).combobox("getData");
						$(this).combobox("setValue", data[0].id);
					
			  		}
			  		
			  		
			  		
			  	});

			    $("#addDialog").dialog({
			    	title: "添加教师",
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
								} else{
									//通过验证，数据提交
									$.post(
										"TeacherServlet05/addTeacher?t="+new Date().getTime(),
										$("#addForm").serialize(),
										function(data)
										{
											if(data=="success"){
												$.messager.alert("消息提醒", "添加成功", "info");
												//关闭窗口
												$("#addDialog").dialog("close");
												//清空原表格数据
												$("#add_number").textbox('setValue', "");
												$("#add_name").textbox('setValue', "");
												$("#add_sex").textbox('setValue', "");
												$("#add_phone").textbox('setValue', "");
												$("#add_qq").textbox('setValue', "");

												//重新刷新页面数据
							  					$('#gradeList').combobox("setValue", gradeid);
							  					$('#dataList').datagrid("reload");
											}else{
												$.messager.alert("消息提醒", "添加失败", "warning");
												return;
											}
										}
									);
								}
							}
						},
						{
							text:'重置',
							plain: true,
							iconCls:'icon-world-reset',
							handler:function(){
							$("#add_number").textbox('setValue', "");
							$("#add_name").textbox('setValue', "");
							$("#add_sex").textbox('setValue', "");
							$("#add_phone").textbox('setValue', "");
							$("#add_qq").textbox('setValue', "");
							}
						},
					]
			    });
			     $("#edit").click(function(){
			    	
			    		var selectRow = $("#dataList").datagrid("getSelected");
			    		var teacherid =null;
			    		var number=null;
			            	var name=null;
			            	var sex=null;
			            	var phone=null;
			            	var qq=null;
			        	if(selectRow == null){
			            	$.messager.alert("消息提醒", "请选择数据进行修改!", "warning");
			            } else{
			            	$("#editDialog").dialog("open");
			            	 teacherid = selectRow.id;
			            	 number=selectRow.number;
			            	 name=selectRow.name;
			            	 sex=selectRow.sex;
			            	 phone=selectRow.phone;
			            	 qq=selectRow.qq;
			            	$("#edit_id").textbox('setValue',teacherid);
			            	$("#edit_number").textbox('setValue', number);
			            	$("#edit_name").textbox('setValue', name);
			            	$("#edit_sex").textbox('setValue', sex);
			            	$("#edit_phone").textbox('setValue', phone);
			            	$("#edit_qq").textbox('setValue', qq);
			            	
			            }
			    }); 
			    
			    $("#editDialog").dialog({

			    	title: "教师修改",
			    	width: 500,
			    	height: 400,
			    	iconCls: "icon-edit",
			    	modal: true,
			    	collapsible: false,
			    	minimizable: false,
			    	maximizable: false,
			    	draggable: true,
			    	closed: true,
			    	buttons: [
			    		{
							text:'修改',
							plain: true,
							iconCls:'icon-world-edit',
							handler:function(){
								var validate = $("#editForm").form("validate");
								if(!validate){
									$.messager.alert("消息提醒","请检查你输入的数据!","warning");
									return;
								} else{
									//通过验证，数据提交
									$.post(
										"TeacherServlet05/editTeacher?t="+new Date().getTime(),
										$("#editForm").serialize(),
										function(data)
										{
											if(data=="success"){
												$.messager.alert("消息提醒", "修改成功", "info");
												$("#dataList").datagrid("reload");
											}else{
												$.messager.alert("消息提醒", "修改失败", "warning");
												return;
											}
										}
									);
								}
							}
						},
						{
							text:'重置',
							plain: true,
							iconCls:'icon-world-reset',
							handler:function(){
							$("#add_number").textbox('setValue', "");
							$("#add_name").textbox('setValue', "");
							$("#add_sex").textbox('setValue', "");
							$("#add_phone").textbox('setValue', "");
							$("#add_qq").textbox('setValue', "");
							}
						},
					]
					
			    });
			    
			}
		);	
	</script>


  </head>
  
  <body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
		<!-- 工具栏 -->
		<div id="toolbar">
			<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
				<div style="float: left;" class="datagrid-btn-separator"></div>
			<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
				<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
				
				
		</div>  
		
		<!-- 添加数据窗口 -->
		<div id="addDialog" style="padding: 10px;">  
	    	<form id="addForm" method="post">
		    	<table cellpadding="8" >
		    		<tr>
		    			<td>工号:</td>
		    			<td><input id="add_number" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="number" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>姓名:</td>
		    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>性别:</td>
		    			<td><input id="add_sex" style="width: 200px; height: 30px;" class="easyui-combobox" name="sex"  /></td>
		    		</tr>
		    		<tr>
		    			<td>电话:</td>
		    			<td><input id="add_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>qq:</td>
		    			<td><input id="add_qq" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="qq" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		
		    	</table>
		    </form>
		</div>	
		<div id="editDialog" style="padding: 10px;">  
	    	<form id="editForm" method="post">
		    	<table cellpadding="8" >
		    		<tr>
		    			<td>序号:</td>
		    			<td><input id="edit_id" style="width: 200px; height: 30px;" readonly="true" class="easyui-textbox" type="text" name="id" /></td>
		    		</tr>
		    		<tr>
		    			<td>工号:</td>
		    			<td><input id="edit_number" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="number" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>姓名:</td>
		    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>性别:</td>
		    			<td><input id="edit_sex" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="sex" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>电话:</td>
		    			<td><input id="edit_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>qq:</td>
		    			<td><input id="edit_qq" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="qq" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    	</table>
		    </form>
		</div>				 
  </body>
</html>
