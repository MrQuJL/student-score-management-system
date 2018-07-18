<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
				$('#dataList').datagrid({ 
					title:'年级列表',   
				    url:'/ssms/TestServlet/QueryAll',    
				    columns:[[    
				        {field:'id',title:'ID',width:100},    
				        {field:'name',title:'Name',width:200}   
				    ]] ,toolbar:"#toolbar"   
				}); 
				
				//设置工具类按钮
			    $("#add").click(function(){
			    	$("#addDialog").dialog("open");
			    }); 
			    
			    $("#addDialog").dialog({
			    	title: "添加年级",
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
										"TestServlet/Add",
										$("#addForm").serialize(),
										function(data)
										{
											alert(data);
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
								$("#add_name").textbox('setValue', "");
								$("#add_courseList").combobox("clear");
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
			<div><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		</div>  
		
		<!-- 添加数据窗口 -->
		<div id="addDialog" style="padding: 10px;">  
	    	<form id="addForm" method="post">
		    	<table cellpadding="8" >
		    		<tr>
		    			<td>ID:</td>
		    			<td><input id="add_id" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    		<tr>
		    			<td>Name:</td>
		    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true,  missingMessage:'不能为空'" /></td>
		    		</tr>
		    	</table>
		    </form>
		</div>				 
  </body>
</html>




