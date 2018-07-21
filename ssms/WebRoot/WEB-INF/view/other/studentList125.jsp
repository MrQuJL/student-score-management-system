<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>班级列表</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/easyui/css/demo.css">
	<script type="text/javascript" src="<%=basePath %>/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/easyui/js/validateExtends.js"></script>
 	<script type="text/javascript">
  		
  		$(function(){
  			//datagrid初始化 
  		    $('#dataList').datagrid({ 
  		        title:'学生列表', 
  		        iconCls:'icon-more',//图标 
  		        border: true, 
  		        collapsible: true,//是否可折叠的 
  		        fit: true,//自动大小 
  		        method: "post",
  		        url:"StudentServlet/queryStudentList",
  		        idField:'id', 
  		        singleSelect: false,//是否单选 
  		        pagination: true,//分页控件 
  		      	pageSize: 10,//每页显示的记录条数，默认为10 
  	        	pageList: [10,20,30],//可以设置每页记录条数的列表 
  		        rownumbers: true,//行号 
  		        sortName: 'id',
  		        sortOrder: 'asc', 
  		        remoteSort: false,
  		        columns: [[  
  					{field:'chk',checkbox: true,width:50},
  	 		        {field:'id',title:'ID',width:50, sortable: true},    
  	 		        {field:'number',title:'学号',width:200},
  	 		        {field:'name',title:'姓名',width:100},
  	 		     	{field:'sex',title:'性别',width:100},
  	 		  		{field:'phone',title:'电话',width:100},
  	 		  		{field:'qq',title:'QQ',width:100},
  	 		 		{field:'clazzName',title:'班级',width:100},
  	 				{field:'gradeName',title:'年级',width:100}
  		 		]], 
  		        toolbar: "#toolbar"
  		    }); 
  			
  			
  			//设置分页控件 
  			var p = $('#dataList').datagrid('getPager');
  			$(p).pagination({
  				pageSize: 10,//每页显示的记录条数，默认为10 
  	        	pageList: [10,20,30],//可以设置每页记录条数的列表 
  				beforePageText : '第',//页数文本框前显示的汉字 
  				afterPageText : '页    共 {pages} 页',
  				displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
  			});
  			
  			
  			
  			//打开修改学生窗体
  		    $("#edit").click(function(){
  		    	var selectRow=$("#dataList").datagrid("getSelections");
  		    	if(selectRow.length==0){
  		    		$.messager.alert("消息提醒", "请选择要修改的学生!", "warning");
  		    	}else if(selectRow.length>1){
  		    		$.messager.alert("消息提醒", "一次只能修改一个学生，请重新选择!", "warning");
  		    	}else{
  		    		$("#editDialog").dialog("open");
  		    	}
  		    });
  		  	//修改学生信息窗体
  		    $("#editDialog").dialog({
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
  		    	buttons: [
  	  		    		{
  	  						text:'修改',
  	  						plain: true,
  	  						iconCls:'icon-edit',
  	  						handler:function(){
  	  							var validate = $("#editForm").form("validate");
  	  							if(!validate){
  	  								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
  	  								return;
  	  							} else{
  	  								$.ajax({
  	  									type: "post",
  	  									url: "StudentServlet/editStudentList",
  	  									data: $("#editForm").serialize(),
  	  									success: function(msg){
  	  										if(msg == "success"){
  	  											$.messager.alert("消息提醒","修改成功!","info");
  	  											//关闭窗口
  	  											$('#dataList').datagrid('clearSelections');
  	  											$("#editDialog").dialog("close");
  	  											//清空原表格数据
  	  											$("#edit_studentId").textbox('setValue',"");
					  	    					$("#edit_studentNumber").textbox('setValue',"");
					  	    					$("#edit_studentName").textbox('setValue',"");
					  	    					//$("#edit_studentSex").combobox('setValue',"男");
					  	    					$("#edit_studentPhone").textbox('setValue',"");
					  	    					$("#edit_studentQQ").textbox('setValue',"");
					  	    					$("#edit_studentGrade").combobox("clear");
					  	    					$("#edit_studentClass").combobox("clear");
  	  											
  	  											//重新刷新页面数据
  	  								  			$('#gradeList').combobox("clear");
  	  								  			$('#clazzList').combobox("clear");
  	  								  			//datagrid初始化 
  	  								  			$('#dataList').datagrid("reload","StudentServlet/queryStudentList");
  	  										} else{
  	  											$.messager.alert("消息提醒",msg,"warning");
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
	  	  						var selectRow=$("#dataList").datagrid("getSelected");
	  	    		    		var studentId=selectRow.id;
	  	    		    		var studentNumber=selectRow.number;
	  	    					var studentName=selectRow.name;
	  	    					var studentSex=selectRow.sex;
	  	    					var studentPhone=selectRow.phone;
	  	    					var studentQQ=selectRow.qq;
	  	    					var studentGrade=selectRow.gradeid;
			  					var studentClass=selectRow.clazzid; 
	  	    					$("#edit_studentId").textbox('setValue',studentId);
	  	    					$("#edit_studentNumber").textbox('setValue',studentNumber);
	  	    					$("#edit_studentName").textbox('setValue',studentName);
	  	    					$("#edit_studentSex").combobox('setValue',studentSex);
	  	    					$("#edit_studentPhone").textbox('setValue',studentPhone);
	  	    					$("#edit_studentQQ").textbox('setValue',studentQQ);
	  	    					$("#edit_studentGrade").combobox('setValue',studentGrade);
			  					$("#edit_studentClass").combobox('setValue',studentClass);
  	  						}
  	  					}],
		  		    	onBeforeOpen:function(){
		  		    		var selectRow=$("#dataList").datagrid("getSelected");
		  		    		var studentId=selectRow.id;
		  		    		var studentNumber=selectRow.number;
		  					var studentName=selectRow.name;
		  					var studentSex=selectRow.sex;
		  					var studentPhone=selectRow.phone;
		  					var studentQQ=selectRow.qq;
		  					
		  					var studentGrade=selectRow.gradeid;
		  					var studentClass=selectRow.clazzid; 
		  					$("#edit_studentId").textbox('setValue',studentId);
		  					$("#edit_studentId").textbox('readonly',true);
		  					$("#edit_studentNumber").textbox('setValue',studentNumber);
		  					$("#edit_studentNumber").textbox('readonly',true);
		  					$("#edit_studentName").textbox('setValue',studentName);
		  					$("#edit_studentSex").combobox('setValue',studentSex);
		  					$("#edit_studentPhone").textbox('setValue',studentPhone);
		  					$("#edit_studentQQ").textbox('setValue',studentQQ);
		  					
		  					$("#edit_studentGrade").combobox('setValue',studentGrade);
		  					$("#edit_studentClass").combobox('setValue',studentClass);
		  					
		  					
		  					
		  					
		  		    	}
  		    });
  			
  			//设置工具类按钮
  		    $("#add").click(function(){
  		    	$("#addDialog").dialog("open");
  		    	//$("#add_studentSex").combobox('sexValue','男');
  		    });
  			//设置添加学生窗口
  		    $("#addDialog").dialog({
  		    	title: "添加学生",
  		    	width: 400,
  		    	height: 450,
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
  							
  							//alert(studentSex);
  							var validate = $("#addForm").form("validate");
  							
  							if(!validate){
  								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
  								return;
  							} else{
  								$.ajax({
  									type: "post",
  									url: "StudentServlet/addStudentList",
  									data: $("#addForm").serialize(),
  									success: function(msg){
  										if(msg == "success"){
  											$.messager.alert("消息提醒","添加成功!","info");
  											//关闭窗口
  											$("#addDialog").dialog("close");
  											//清空原表格数据
  											$("#studentNumber").textbox('setValue', "");
  											$("#studentName").textbox('setValue', "");
  											//$("#add_studentSex").combobox("clear");
  											$("#studentPhone").textbox('setValue', "");
  											$("#studentQQ").textbox('setValue', "");	
  											$("#add_studentClass").combobox("clear");
  											$("#add_studentGrade").combobox("clear");
  											
  											//重新刷新页面数据
  								  			$('#gradeList').combobox("clear");
  								  			$('#clazzList').combobox("clear");
  								  			
  								  			//datagrid初始化 
  								  			$('#dataList').datagrid("reload","StudentServlet/queryStudentList");
  										} else{
  											$.messager.alert("消息提醒",msg,"warning");
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
  							$("#studentNumber").textbox('setValue', "");
  							$("#studentName").textbox('setValue', "");
  							//重新加载性别
  							//$("#add_studentSex").combobox("clear");
  							$("#studentPhone").textbox('setValue', "");
  							$("#studentQQ").textbox('setValue', "");	
  							
  							//重新加载年级和班级
  							$("#add_studentGrade").combobox("clear");
  							$("#add_studentClass").combobox("clear");
  						}
  					}
  				]
  		    });
  			
  			//删除按钮点击事件
  			$("#delete").click(function(){
  				var selectRows=$("#dataList").datagrid("getSelections");
  				if(selectRows.length==0){
  		    		$.messager.alert("消息提醒", "请选择要删除的学生!", "warning");
  				}else{
  					var idArray=new Array(selectRows.length);
  					var numberArray=new Array(selectRows.length);
  					for(var i=0;i<selectRows.length;i++){
  						idArray[i]=selectRows[i].id;
  					}
  					for(var i=0;i<selectRows.length;i++){
  						numberArray[i]=selectRows[i].number;
  					}
  					$.messager.confirm("消息提醒", "将删除与学生相关的所有数据(包括成绩和用户)，确认继续？", function(r){
  	            		if(r){
  	            			$.ajax({
  								type: "post",
  								url: "StudentServlet/deleteStudentList",
  								data: {"idArray[]":idArray,"numberArray[]":numberArray},
  								success: function(msg){
  									if(msg == "success"){
  										$.messager.alert("消息提醒","删除成功!","info");
  										
  							  			//datagrid初始化 
  							  			$('#dataList').datagrid("reload","StudentServlet/queryStudentList");
  									} else{
  										$.messager.alert("消息提醒","删除失败！","warning");
  										return;
  									}
  								}
  							});
  	            		}
  	            	});
  					
  				}
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
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;margin: 0 10px 0 10px">年级：
			<input id="gradeList" class="easyui-combobox"
                                name="gradeList" 
                                data-options="url:'StudentServlet/queryStudentGrade',
					                          method:'post',
					                          valueField:'id',
					                          textField:'name',
					                          panelHeight:'auto',
                                              editable:false,
                                              value:'--请选择年级--'" />
		</div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin: 0 10px 0 10px">班级：
			<input id="clazzList" class="easyui-combobox"
                               name="clazzList"
                               data-options="method:'post',
					                         valueField:'id',
					                         textField:'name',
					                         panelHeight:'auto',
                                             editable:false,
                                             value:'--请选择班级--'" />
		</div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>学号:</td>
	    			<td><input id="studentNumber" style="width: 200px; height: 30px;" class="easyui-textbox"  name="studentNumber"   data-options="required:true, missingMessage:'不能为空',validType:'stuNumber'" /></td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="studentName" style="width: 200px; height: 30px;" class="easyui-textbox"  name="studentName"   data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="add_studentSex" class="easyui-combobox" name="add_studentSex" style="width: 200px; height: 30px;" data-options="editable:false">   
						    <!-- <option>--请选择性别--</option>  -->
						    <option value="男">男</option>   
						    <option value="女">女</option>   
						</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="studentPhone" style="width: 200px; height: 30px;" class="easyui-textbox"  name="studentPhone"   data-options="required:true, missingMessage:'不能为空',validType:'tel'" /></td>
	    		</tr>
	    		<tr>
	    			<td>QQ:</td>
	    			<td><input id="studentQQ" style="width: 200px; height: 30px;" class="easyui-textbox"  name="studentQQ"   data-options="required:true, missingMessage:'不能为空',validType:'QQ'" /></td>
	    		</tr>
	    		<tr>
	    			<td>年级:</td>
	    			<td> <input style="width: 200px; height: 30px;" id="add_studentGrade" class="easyui-combobox"
                                name="add_studentGrade"
                                data-options="url:'StudentServlet/queryStudentGrade',
					                          method:'post',
					                          valueField:'id',
					                          textField:'name',
					                          panelHeight:'auto',
                                              editable:false,
                                              required:true" />
					</td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td><input style="width: 200px; height: 30px;" id="add_studentClass" class="easyui-combobox"
                               name="add_studentClass"
                               data-options="method:'post',
					                         valueField:'id',
					                         textField:'name',
					                         panelHeight:'auto',
                                             editable:false,
                                             required:true" />
					</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>ID:</td>
	    			<td><input id="edit_studentId" style="width: 200px; height: 30px;" class="easyui-textbox"  name="edit_studentId" /></td>
	    		</tr>
	    		<tr>
	    			<td>学号:</td>
	    			<td><input id="edit_studentNumber" style="width: 200px; height: 30px;" class="easyui-textbox"  name="edit_studentNumber" /></td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_studentName" style="width: 200px; height: 30px;" class="easyui-textbox"  name="edit_studentName" data-options="required:true, missingMessage:'不能为空'"/></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="edit_studentSex" class="easyui-combobox" name="edit_studentSex" style="width: 200px; height: 30px;" data-options="editable:false">   
						    <!-- <option>--请选择性别--</option>  -->
						    <option value="男">男</option>   
						    <option value="女">女</option>   
						</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="edit_studentPhone" style="width: 200px; height: 30px;" class="easyui-textbox"  name="edit_studentPhone" data-options="validType:'tel'" /></td>
	    		</tr>
	    		<tr>
	    			<td>QQ:</td>
	    			<td><input id="edit_studentQQ" style="width: 200px; height: 30px;" class="easyui-textbox"  name="edit_studentQQ" data-options="validType:'QQ'"/></td>
	    		</tr>
	    		<tr>
	    			<td>年级:</td>
	    			<td> <input style="width: 200px; height: 30px;" id="edit_studentGrade" class="easyui-combobox"
                                name="edit_studentGrade"
                                data-options="url:'StudentServlet/queryStudentGrade',
					                          method:'post',
					                          valueField:'id',
					                          textField:'name',
					                          panelHeight:'auto',
                                              editable:false,
                                              required:true" />
					</td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td><input style="width: 200px; height: 30px;" id="edit_studentClass" class="easyui-combobox"
                               name="edit_studentClass"
                               data-options="method:'post',
					                         valueField:'id',
					                         textField:'name',
					                         panelHeight:'auto',
                                             editable:false,
                                             required:true" />
					</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
</body>
<script type="text/javascript">  
	//textbox数据验证
	$.extend($.fn.textbox.defaults.rules, {  
	    tel: {  
	        validator: function(value){
	            var partten = /^1[3,5,7,8]\d{9}$/;
	            return partten.test(value);  
	        },  
	        message: '请填写正确的手机号！'  
	    },
	    stuNumber:{
	    	validator: function(value){
	            var partten = /^20[1-9][3-9]\d{5}$/;
	            return partten.test(value);  
	        },  
	        message: '请填写正确的学号！'
	    },
	    QQ: {
	          validator: function (value) {
	              return /^[1-9]\d{4,10}$/.test(value);
	            },
	            message: 'QQ号码不正确'
	          }
    }); 
	  //添加学生窗口---下拉框二级联动
	  $('#add_studentGrade').combobox({
	      onSelect: function (row) {
	          if (row != null) {
	              $('#add_studentClass').combobox({
	                  url: "StudentServlet/queryStudentClassByGradeId?gradeId=" + row.id
	              });
	          }
	      }
	  });
	  
	  //修改学生窗口---下拉框二级联动
	  $('#edit_studentGrade').combobox({
	      onSelect: function (row) {
	          if (row != null) {
	              $('#edit_studentClass').combobox({
	                  url: "StudentServlet/queryStudentClassByGradeId?gradeId=" + row.id
	              });
	          }
	      } ,
	      onChange:function(newValue,oldValue){
	    	  if(newValue!=null){
	    		  $('#edit_studentClass').combobox({
	                  url: "StudentServlet/queryStudentClassByGradeId?gradeId=" + newValue
	              });
	    	  }
	      }
	  });
	  
	  
	 
	  
	  
	  
	  //初始化窗口---年级下拉框选择事件
	  
	  $('#gradeList').combobox({
		  
	      onSelect: function (row) {
	          if (row != null) {
	        	  //班级下拉框联动
	        	  gradeName=row.name;
	              $('#clazzList').combobox({
	                  url: "StudentServlet/queryStudentClassByGradeId?gradeId=" + row.id
	              });
	              
	              //
	              $('#dataList').datagrid({ 
	  		        title:'学生列表', 
	  		        iconCls:'icon-more',//图标 
	  		        border: true, 
	  		        collapsible: true,//是否可折叠的 
	  		        fit: true,//自动大小 
	  		        method: "post",
	  		        url:"StudentServlet/queryStudentListByGrade?page=1&rows=10&gradeName="+gradeName,
	  		        idField:'id', 
	  		        singleSelect: false,//是否单选 
	  		        pagination: true,//分页控件 
	  		      	pageSize: 10,//每页显示的记录条数，默认为10 
	  	        	pageList: [10,20,30],//可以设置每页记录条数的列表 
	  		        rownumbers: true,//行号 
	  		        sortName: 'id',
	  		        sortOrder: 'asc', 
	  		        remoteSort: false,
	  		        columns: [[  
	  					{field:'chk',checkbox: true,width:50},
	  	 		        {field:'id',title:'ID',width:50, sortable: true},    
	  	 		        {field:'number',title:'学号',width:200},
	  	 		        {field:'name',title:'姓名',width:100},
	  	 		     	{field:'sex',title:'性别',width:100},
	  	 		  		{field:'phone',title:'电话',width:100},
	  	 		  		{field:'qq',title:'QQ',width:100},
	  	 		 		{field:'clazzName',title:'班级',width:100},
	  	 				{field:'gradeName',title:'年级',width:100}
	  		 		]], 
	  		        toolbar: "#toolbar",
	  		        onLoadSuccess:function(){
	  		        	$(".pagination-num").val("1");
	  		        	$('#dataList').datagrid('getPager').pagination({
		            	    onSelectPage: function (pageNumber, pageSize) {
		            	        PageDataGridView(pageNumber, pageSize);//重新加载
		            	    }
		                });
		            	
	            	    function PageDataGridView(pi,ps) {
	            		  //alert("第"+pi+"页,每页"+ps+"条!");
	            		  $('#dataList').datagrid('reload',"StudentServlet/queryStudentListByGrade?page="+pi+"&rows="+ps+"&gradeName="+$("#gradeList").combobox("getText"));
	            	    }
	  		        }
	  		      });
	              
	              
	          }
	      }
	  	
	  });
	  
	  var gradeName;//保存年级下拉框名称
	  //初始化窗口---班级下拉框选择事件
	  $('#clazzList').combobox({
	      onSelect: function (row) {
	          /* if (row != null) {
	        		//datagrid初始化 
		  			$('#dataList').datagrid("reload","StudentServlet/queryStudentListByClassCombobox?gradeName="+gradeName+"&clazzName="+row.name);
	          } */
	          if (row != null) {
	        	  
	              
	              //
	              $('#dataList').datagrid({ 
	  		        title:'学生列表', 
	  		        iconCls:'icon-more',//图标 
	  		        border: true, 
	  		        collapsible: true,//是否可折叠的 
	  		        fit: true,//自动大小 
	  		        method: "post",
	  		        url:"StudentServlet/queryStudentListByClassCombobox?page=1&rows=10&gradeName="+gradeName+"&clazzName="+row.name,
	  		        idField:'id', 
	  		        singleSelect: false,//是否单选 
	  		        pagination: true,//分页控件 
	  		      	pageSize: 10,//每页显示的记录条数，默认为10 
	  	        	pageList: [10,20,30],//可以设置每页记录条数的列表 
	  		        rownumbers: true,//行号 
	  		        sortName: 'id',
	  		        sortOrder: 'asc', 
	  		        remoteSort: false,
	  		        columns: [[  
	  					{field:'chk',checkbox: true,width:50},
	  	 		        {field:'id',title:'ID',width:50, sortable: true},    
	  	 		        {field:'number',title:'学号',width:200},
	  	 		        {field:'name',title:'姓名',width:100},
	  	 		     	{field:'sex',title:'性别',width:100},
	  	 		  		{field:'phone',title:'电话',width:100},
	  	 		  		{field:'qq',title:'QQ',width:100},
	  	 		 		{field:'clazzName',title:'班级',width:100},
	  	 				{field:'gradeName',title:'年级',width:100}
	  		 		]], 
	  		        toolbar: "#toolbar",
	  		        onLoadSuccess:function(){
	  		        	//因为每次url请求都带参数，所以需要重写参数请求
	  		        	$(".pagination-num").val("1");
	  		        	$('#dataList').datagrid('getPager').pagination({
		            	    onSelectPage: function (pageNumber, pageSize) {
		            	        PageDataGridView(pageNumber, pageSize);//重新加载
		            	    }
		                });
		            	
	            	    function PageDataGridView(pi,ps) {
	            		  //alert("第"+pi+"页,每页"+ps+"条!");
	            		  $('#dataList').datagrid('reload',"StudentServlet/queryStudentListByClassCombobox?page="+pi+"&rows="+ps+"&gradeName="+$("#gradeList").combobox("getText")+"&clazzName="+$("#clazzList").combobox("getText"));
	            	    }
	  		        }
	  		      }); 
	              
	          }
	      }
	  });
</script>
</html>