<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>班级列表</title>
<link rel="stylesheet" type="text/css"
	href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
<script type="text/javascript">
	$(function() {
		//datagrid初始化 
		$('#dataList').datagrid(
		{
			title : '教师列表',
			iconCls : 'icon-more',//图标 
			border : true,
			collapsible : false,//是否可折叠的 
			pagination : true,//分页控件 
			//fit: true,//自动大小
			singleSelect : true,//是否单选 
			pagination : true,//分页控件 
			rownumbers : true,//行号  
			method : "post",
			url : "TeacherAddressServlet05?method=TeaATTENList&t="+ new Date().getTime(),
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			remoteSort : false,
			columns : [[  {
				field : 'chk',
				checkbox : true,
				width : 50
			}, {
				field : 'id',
				title : 'ID',
				width : 50,
				sortable : true
			}, {
				field : 'number',
				title : '工号',
				width : 200
			}, {
				field : 'name',
				title : '姓名',
				width : 200
			}, {
				field : 'sex',
				title : '性别',
				width : 200
			}, {
				field : 'phone',
				title : '电话',
				width : 200
			}, {
				field : 'qq',
				title : 'QQ',
				width : 200
			},{
				field : 'photo',
				title : '照片',
				width : 200
			},  ]],
			toolbar : "#toolbar"
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
		//分页栏上浮
		/*$(".pagination").css({
			"margin-top" : "-26px"
		});*/
		//设置工具类按钮
		$("#query").click(function(){
			var selectRow = $("#dataList").datagrid("getSelected");
			var id=null;
			var number = null;
			var name = null;
			var sex = null;
			var phone = null;
			var qq = null;
			var photo = null;
			if (selectRow == null) {
				$.messager.alert("消息提醒", "请选择数据进行修改!", "warning");
			} else {
				$("#queryDialog").dialog("open");
				
				id=selectRow.id;
				number = selectRow.number;
				name = selectRow.name;
				sex = selectRow.sex;
				phone = selectRow.phone;
				qq = selectRow.qq;
				photo=selectRow.photo;
				$("#headphoto").attr("src","photo/"+number+".jpeg");
				$("#query_number").textbox('setValue', number);
				$("#query_name").textbox('setValue', name);
				$("#query_sex").textbox('setValue', sex);
				$("#query_phone").textbox('setValue', phone);
				$("#query_qq").textbox('setValue', qq);
				
				//$("#headphoto").attr("src","TeaATTENServlet?method=photo&number="+number);
				var url;
				$.ajax({
					type: "post",
					url: "TeacherAddressServlet05?method=photo&number="+number,
					//data: {courseid: courseid},
					success:function(data){
						//alert("success:" + data);
						url = data;
						//alert("url:" + url);
						$("#headphoto").attr("src",url);
					}
					
				});
				//alert("data:" + url);
				
			 	//alert("number" + number);
			 	 
			 }
		});
		
		//设置查看信息窗口
		$("#queryDialog").dialog({
			title : "查看信息",
			width : 500,
			height : 400,
			iconCls : "icon-edit",
			modal : true,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			draggable : true,
			closed : true,
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
			<a id="query" href="javascript:;" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true">查看</a>
		</div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
	</div>

	<!-- 查询窗口 -->
	<div id="queryDialog" style="padding: 10px">
		 
		<form id="addForm" method="post">
			<table cellpadding="8">
				<tr>
					<td>工号:</td>
					<td><input id="query_number" style="width: 200px; height: 30px;"
						readonly="true" class="easyui-textbox" type="text" name="number" /></td>
						<td colspan="5" > <img id="headphoto" style="height: 74px; width: 112px;" src=""/ ></td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td><input id="query_name" style="width: 200px; height: 30px;"
						readonly="true" class="easyui-textbox" type="text" name="name"/></td>
				</tr>
				<tr>
					<td>性别:</td>
					<td><input id="query_sex" style="width: 200px; height: 30px;"
						readonly="true" class="easyui-textbox" type="text" name="sex"/></td>
				</tr>
				<tr>
					<td>电话:</td>
					<td><input id="query_phone" style="width: 200px; height: 30px;"
						readonly="true" class="easyui-textbox" type="text" name="phone"/></td>
				</tr>
				<tr>
					<td>QQ:</td>
					<td><input id="query_qq" style="width: 200px; height: 30px;"
						readonly="true" class="easyui-textbox" type="text" name="qq"/></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>