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
 							return row.clazz.name;
 						} else {
 							return value;
 						}
 					}
 				},
 		       {field:'remark',title:'备注',width:200}
 		        
	 		]], 
	        toolbar:  
	        [
	        	{
	        		text: '查看成绩',
	        		iconCls: 'icon-search',
	        		handler: function(){
	        			table = $("#scoreList");
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
				  			
									var url='ScoreServlet02/query?clazzid='+query.id+'&gradeid'+gradeid+'&examid'+id;
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
					 		       
					 		     ]]
					   	 	}); 
					   	 	
				    
			           }
	        		}
	        	}          
	        ]
	    }); 
	    //设置分页控件 
	    /*  var p = $('#dataList').datagrid('getPager'); 
   		 $(p).pagination({ 
        	pageSize: 10,//每页显示的记录条数，默认为10 
        	pageList: [5,10,20,30,50],//可以设置每页记录条数的列表 
        	beforePageText: '第',//页数文本框前显示的汉字 
        	afterPageText: '页    共 {pages} 页', 
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
   		 }); */
	  
	    //设置工具类按钮
	   /* $("#query").click(function(){
	    	
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
		 		       
		 		     ]]
		   	 	}); 
		   	 	
	    
           }
	    });	*/
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
	    	closed: true
		});
      
	  
	  
	  	
});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	</table> 
	
	<!--  成绩统计 -->
	<div id="scorequery" cellspacing="0" cellpadding="0">
		 
		
		<table id="scoreList" cellspacing="0" cellpadding="0"> 
		
		</table>
	</div>
	
</body>
</html>