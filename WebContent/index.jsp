<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="public/javascripts/layui/css/layui.css">
  <link rel="stylesheet" type="text/css" href="public/stylesheets/index.css">
  <title>首页</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">图书查询系统</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-right">
      <!-- <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="头像.jpg" class="layui-nav-img">
                         请求集合
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">修改密码</a></dd>
        </dl>
      </li> -->
      <div id="right-top">	      
      </div>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <!-- <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="Mindex.jsp" id="Mindex" target="iframe">后台首页</a>
        </li> -->
        <li class="layui-nav-item">
          <a href="queryBook.jsp" id="queryBook" target="iframe">查询图书</a>
          <!-- <dl class="layui-nav-child">
            <dd><a href="javascript:;">列表一</a></dd>
            <dd><a href="javascript:;">列表二</a></dd>
            <dd><a href="">超链接</a></dd>
          </dl> -->
        </li>
        <li class="layui-nav-item">
          <a href="javascript:" id="manageBook" target="iframe">管理图书</a>
        </li>
      </ul>
    </div>
  </div>
  <!-- 内容主体区域 -->
  <div class="layui-body layui-form">
  	<div class="layui-tab-item layui-show" style="margin-left:10px;">		 
  		<iframe src="queryBook.jsp" class="iframe" name="iframe"></iframe>   
    </div>
  </div>
  
  <div class="layui-footer footer">
    <!-- 底部固定区域 -->
    copyright@请求集合
  </div>
</div>
<script src="public/javascripts/layui/layui.js"></script>
<script>
layui.use(['element','jquery'],function(){
	var elenemt = layui.element;
    var $ = layui.jquery;

  <% 
  //获取后台登录时设置的session
  String sess = (String)session.getAttribute("login");
  %>
  console.log(<%=sess%>);
  var session1 = <%=sess%>;
  console.log(session1);
  if(session1 == true){
  	console.log("666");
  	$("#right-top").html("<li class='layui-nav-item'><a href='' id='logout'>退出</a></li>");
  	$("#logout").click(function(){
  		//点击退出时销毁session
  		<%session.invalidate();%>
  	})
  }else{
    $("#right-top").html("<li class='layui-nav-item'><a href='login.jsp'>登录</a></li><li class='layui-nav-item'><a href='register.jsp'>注册</a></li>"); 
  }
  //设置左边菜单栏管理图书的状态
  $("#manageBook").click(function(){
	  console.log("8888");
	  if(session1 != true){
		  alert("您还未登录，请先登录！");
		  window.location.href = "/bookstore/login.jsp"
	  }else{
		  $("#manageBook").attr('href','manageBook.jsp');
	  } 
  })  
});
</script>
</body>
</html>