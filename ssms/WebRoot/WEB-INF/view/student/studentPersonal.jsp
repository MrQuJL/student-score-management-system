<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>个人信息</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	
	<style type="text/css">
		.table th{font-weight:bold}
		.table th,.table td{padding:8px;line-height:20px}
		.table td{text-align:left}
		.table-border{border-top:1px solid #ddd}
		.table-border th,.table-border td{border-bottom:1px solid #ddd}
		.table-bordered{border:1px solid #ddd;border-collapse:separate;*border-collapse:collapse;border-left:0}
		.table-bordered th,.table-bordered td{border-left:1px solid #ddd}
		.table-border.table-bordered{border-bottom:0}
		.table-striped tbody > tr:nth-child(odd) > td,.table-striped tbody > tr:nth-child(odd) > th{background-color:#f9f9f9}
	</style>
	
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {
	     
		 if (${photoname eq '1'}) { // 最开始
              $("#headphoto").attr("src", "photo/student${stu.id}.jpeg");//设置头像src
	      } else { // 点击之后
	    	  $("#headphoto").attr("src", "photo/${photoname}");//设置头像src
	      }
			
		//修改密码窗口
	    $("#passwordDialog").dialog({
	    	title: "修改密码",
	    	width: 500,
	    	height: 300,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	  	    		{
	  					text:'提交',
	  					iconCls:'icon-user_add',
	  					handler:function(){
	  						var validate = $("#editPassword").form("validate");
	  						if(!validate){
	  							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
	  							return;
	  						} else{
	  							$.ajax({
	  								type: "post",
	  								url: "StudentServlet?method=EditPasswod",
	  								data: $(
																	"#editPassword")
																	.serialize(),
	  								success: function(msg){
	  								
		if (msg == "success") {
																		$.messager
																				.alert(
																						"消息提醒",
																						"修改成功，将重新登录",
																						"info");
																		setTimeout(
																				function() {
																					top.location.href = "SystemServlet?method=LoginOut";
																				},
																				1000);
																	} else if (msg == "oldPwderror") {
																		$.messager
																				.alert(
																						"消息提醒",
																						"原密码错误!",
																						"info");

																	} else if (msg == "newPwderror") {
																		$.messager
																				.alert(
																						"消息提醒",
																						"新输入的两次密码不一致！",
																						"info");

																	} else
																		(msg == "false");
																	{
																		
																	}

																}
															});
												}
											}
										},
										{
											text : '重置',
											iconCls : 'icon-reload',
											handler : function() {
												//清空表单
												$("#old_password").textbox(
														'setValue', "");
												$("#new_password").textbox(
														'setValue', "");
												$("#re_password").textbox(
														'setValue', "");
											}
										} ],
							});
			
			//设置主窗口
			$("#editDialog")
					.dialog(
							{
								title : "修改密码",
								width : 500,
								height : 400,
								fit : true,
								modal : false,
								noheader : true,
								collapsible : false,
								minimizable : false,
								maximizable : false,
								draggable : true,
								closed : false,
								toolbar : [
										{
											text : '提交',
											iconCls : 'icon-user_add',
											handler : function() {
												var validate = $("#updateStudent").form("validate");
						  						if(!validate){
						  							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
						  							return;
						  						}
												$
														.ajax({
															type : "post",
															url : "StudentServlet?method=update",
															data : $(
																	"#updateStudent")
																	.serialize(),
															success : function(
																	msg) {
																if (msg == "success") {
																	$.messager
																			.alert(
																					"消息提醒",
																					"修改成功",
																					"info");
																	$(
																			"#updateStudent")
																			.datagrid(
																					"reload",
																					"StudentServlet?method=toStudentPersonalView");
																} else if (msg == "false") {
																	$.messager
																			.alert(
																					"消息提醒",
																					"修改失败",
																					"info");
																}
															}
														});
											}
										},
										{
											text : '重置',
											plain : true,
											iconCls : 'icon-reload',
											handler : function() {
												//清空表单
												$("#qq")
														.textbox('setValue', "");
												$("#phone").textbox('setValue',
														"");

											}
										},
										'-',
										{
											text : '修改密码',
											plain : true,
											iconCls : 'icon-password',
											handler : function() {
												$("#passwordDialog").dialog(
														"open");
											}
										} ],

							});

		});
	</script>
</head>
<body>
	
	<div id="editDialog" style="padding: 20px;">
		<div style="width: 400px;float:left;" >
		<form id="updateStudent"  method="post">
    	<table id="studentTable" class="table table-border table-bordered table-striped" style="width: 567px" cellpadding="8" >
    		<tr>
    			<td width="200">学号:</td>
    			<td width="350">
    				<input id="id" readonly="true" value="${stu.number}" class="easyui-textbox" style="width: 300px; height: 30px;" type="text" name="id" />
    			</td>
    			
    		</tr>
    		<tr>
    			<td>姓名:</td>
    			<td><input id="name" readonly="true" value="${stu.name }" class="easyui-textbox" style="width: 300px; height: 30px;" type="text" name="name" /></td>
    			
    		</tr>
    		<tr>
    			<td>性别:</td>
    			<td>
	    			<select id="sex" style="width: 200px; height: 30px;" name="sex"  class="easyui-combobox" >
		    			<c:choose>
		    				<c:when test="${stu.sex eq '男' }">
		    					<option value="1" selected>男</option>
		                 		<option value="2">女</option>  
		    				</c:when>
		    				<c:otherwise>
		    					<option value="1">男</option>
		                 		<option value="2" selected>女</option> 
		    				</c:otherwise>
		    			</c:choose>
	    			</select>
    			</td>
    			
    		</tr>
    		<tr>
    			<td>电话:</td>
    			<td><input id="phone" value="${stu.phone}" class="easyui-textbox" data-options="required:true, validType:'phone' ,missingMessage:'请输入电话'" style="width: 300px;  height: 30px;" type="text" name="phone" /></td>
    			
    		</tr>
    		<tr>
    			<td>QQ:</td>
    			<td><input id="qq" value="${stu.qq}" class="easyui-textbox" style="width: 300px; height: 30px;" data-options="required:true, validType:'qq' ,missingMessage:'请输入QQ'" type="text" name="qq" /></td>
    			
    		</tr>
    		<tr>
    			<td>班级:</td>
    			<td><input id="clazz" readonly="true" value="${stu.clazzname}" class="easyui-textbox" style="width: 300px; height: 30px;" type="text" name="clazz" /></td>
    			
    		</tr>
    		<tr>
    			<td>年级:</td>
    			<td><input id="grade" readonly="true" value="${stu.gradename}" class="easyui-textbox" style="width: 300px; height: 30px;" type="text" name="grade" /></td>
    			
    		</tr>
    	</table>
    	</form>
    	</div>
    	<div style="width: 450px;float:right;" >
    	
    			<img id="headphoto" width="100px" height="100px" style="height: 200px; width: 198px; ">
    			
    			<form id="form1" action="StudentServlet?method=uploadphoto" enctype="multipart/form-data" method="post">
						<input class="easyui-filebox" name="file1" id="file1" data-options="buttonText:'选择文件'" style="width:300px"> 
						<input type="button" id="btnOK" name="btnOK" value="更换头像">
				</form>
						
    	</div>
    	
    	
	</div>
	
	<!-- 修改密码窗口 -->
	<div id="passwordDialog" style="padding: 20px">
    	<form id="editPassword">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>原密码:</td>
	    			<td>
	    				<input id="old_password" name="old_password" style ="width: 200px; height: 30px;" class="easyui-textbox" type="password" validType="oldPassword[${user.password}]"  data-options="required:true, missingMessage:'请输入原密码'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>新密码:</td>
	    			<td>
	    				<input  type="hidden" name="account" value="${user.account }" />
	    				<input id="new_password" name="new_password" style="width: 200px; height: 30px;" class="easyui-textbox" type="password" validType="password" name="password" data-options="required:true, missingMessage:'请输入新密码'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>新密码:</td>
	    			<td><input id="re_password" name="re_password" style="width: 200px; height: 30px;" class="easyui-textbox" type="password" validType="equals['#new_password']"  data-options="required:true, missingMessage:'再次输入密码'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<script type="text/javascript">
	$("#btnOK").click(function(){
		var fileVal = $("input[style='margin-left: 0px; margin-right: 57px; padding-top: 3px; padding-bottom: 3px; width: 233px;']").val();
		
		if (fileVal == "") {
			$.messager
			.alert(
					"消息提醒",
					"请选择图片！",
					"info");
			return;
		} else {
			$("#form1").submit();
		}
	});
	 $.extend($.fn.validatebox.defaults.rules, {
	       phone : {// 验证电话
	           validator : function(value) {
	               return /^1[0-9]{10}$/gi.test(value);
	           },
	           message : '电话格式不正确!'
	       },
	       qq : {// 验证qq
	           validator : function(value) {
	               return /^[1-9][0-9]{5,10}$/gi.test(value);
	           },
	           message : 'qq格式不正确!'
	       }
	   });
</script>
</body>
</html>