<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="public/javascripts/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="public/stylesheets/index.css">
<title>Insert title here</title>
</head>
<body>
   <div class="demoTable" style="margin-top:10px;">
         搜索图书：
      <input type="radio" name="query" value="title" title="书名" checked id="queryTitle">书名
      <input type="radio" name="query" value="type" title="类型" id="queryType">类型
	  <div class="layui-inline">
	    <input name="id" class="layui-input" id="data" type="text">
	  </div>
	  <button class="layui-btn" data-type="reload" id="query">搜索</button>
	  <button class="layui-btn" id="refresh">刷新</button>
   </div>

  <table id="manager" lay-filter="manager"></table>
  <script src="public/javascripts/layui/layui.js"></script>
  <!-- 右边操作 -->
  <script type="text/html" id="bar">
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
  </script>
  <script>
    layui.use(['table','jquery','layer'], function(){
      var table = layui.table;
      var $ = layui.jquery;
      var layer = layui.layer;
      //第一个实例
      var table1 = table.render({
        elem: '#manager',
        width:1150,
        height: 490,
        url: '${pageContext.request.contextPath}/book?method=queryAllBook' ,//数据接口
        page: true ,//开启分页
        cols: [[ //表头
          {type:'checkbox',fixed:'left',align:'center'},
          {field:'',title:'序号',type:'numbers',width:80},
          {field: 'title', title: '书名', width:110},
          {field: 'type', title: '类型', width:130} ,
          {field: 'price', title: '价格', width: 180,align:'center'},
          {field: 'right', title: '操作',align:'center', width: 150, toolbar:'#bar'}
        ]]
      });
      //监听行工具栏事件
      table.on('tool(manager)',function(obj){
        let data = obj.data;//获取当前行所有数据
        let id = data.id;//获取选中行的id
        let picture = data.picture;
        let photoSrc = "http://localhost:8080/bookstore"+picture;
        let layEvent = obj.event;//获取点击的是哪个操作按钮
        let tr = obj.tr;//获取当前行tr的DOM对象
        if(layEvent === 'detail'){
        	console.log(photoSrc);
            var index1 = layer.open({
                type:0,
                title:'查看详情',
                content:'<div style="width:200px;height:200px;margin:0 auto;">\
                          <img src="" id="img" style="width:100%;height:100%;">\
                        </div>'
              });
            $("#img").attr('src',photoSrc); 
            
        }
      });     
      //点击搜索按钮
      $("#query").click(function(){
    	  let data1 = $("#data").val();
    	  let msg1 = $('input:radio[name="query"]:checked').val();
    	  console.log(msg1);
    	  console.log(data1);
    	  $.ajax({
            type:'POST',
            url:'${pageContext.request.contextPath}/book?method=queryBook',
            data:{'msg':msg1,"data":data1},
            success:function(data){
              console.log(data.msg);
              if(data.msg === "success"){
            	 console.log("66888");
            	 console.log(data);
            	 table1.reload({
            		 elem:"#manager",
            		 url:"${pageContext.request.contextPath}/book?method=queryBook",
          			 where:{
          				 'msg':msg1,
          				 'data':data1
          			 }
            	 });     
              }else{
            	layer.confirm('没数据，请重新输入查询！',function(index){
                  layer.close(index);//关闭对话框
                })
              }
            }
          })
      })
      //点击刷新按钮的操作
      $("#refresh").click(function(){
    	  table1.reload({
    		  url:"${pageContext.request.contextPath}/book?method=queryAllBook"
    	  });//重载表格 
      })
    })
  </script>
</body>
</html>