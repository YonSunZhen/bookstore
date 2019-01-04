<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel='stylesheet' href='public/stylesheets/public.css' />
<link rel='stylesheet' href='public/stylesheets/admin.css' />
<title>登录</title>
</head>
<body>
  <div id="body">
    <div class="container">
      <header class="header">
        <div class="logo"></div>
      </header>
      <div class="nav">
        <div class="l-line"></div>
        <div class="r-line"></div>
        <div class="tab-navs">
          <div style="margin:0 auto; width:180px;">
            <a href="#" id="btn-login">登录</a>
            <a href="#" id="btn-register">注册</a>
          </div>
        </div>
      </div>
      <div class="clear"></div>
      <div class="main" id="main-login">
        <div class="content">
          <div class="login-item">
            <span class="user-icon"></span>
            <input class="form-text" type="text" placeholder="请输入用户名"  id="username1">
          </div>
          <div class="login-item">
            <input class="form-text" type="password" placeholder="密码"  id="password1">
          </div>
          <div class="login-item">
            <input class="submit-btn "type="submit" value="登录" id="login">
          </div>
        </div>
      </div>

      <div class="main hide" id="main-register">
        <div class="content">
          <div class="login-item">
            <span class="user-icon"></span>
            <input class="form-text" type="text" placeholder="请输入用户名"  id="username">
          </div>
          <div class="login-item">
            <input class="form-text" type="password" placeholder="请输入密码"  id="password">
          </div>
          <div class="login-item">
            <input class="form-text" type="password" placeholder="请再次输入密码"  id="passwordAgain">
          </div>
          <div class="login-item">
            <input class="submit-btn "type="submit" value="立即注册" id="register">
          </div>
        </div>
      </div>
      <footer class="footer">
        <p>2015112026-孙永镇,2015112028-郑立达</p>
      </footer>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
  <script>
    var login = $("#btn-login");
    var register = $("#btn-register");
    login.click(function(){
      $("#main-register").css('display','none');
      $("#main-login").css('display','block');
    })
    register.click(function(){
      $("#main-login").css('display','none');
      $("#main-register").css('display','block');
    })

    //注册
    $("#register").click(function(){
      var username = $('#username').val();
      var password = $('#password').val();
      var passwordAgain = $("#passwordAgain").val();
      if(password === passwordAgain){
        $.ajax({
          type:'POST',
          url:'${pageContext.request.contextPath}/user',
          async:true,
          data:{'method':'register','username':username,'password':password},
          success:function(result){
            if(result === "success"){
            	alert("注册成功！");
            	window.location.href = '/bookstore/login.jsp';
            }else if(result === "isExist"){
            	alert("该用户名已存在，请重新输入！");
            }else{
            	alert("注册失败！");
            }         
          }
        })
      }else{
        alert("两次密码输入不一致，请重新输入!");
      }
    })
    //登录
    $("#login").click(function(){
      var username1 = $('#username1').val();
      var password1 = $('#password1').val();
      console.log(username1);
      $.ajax({
        type:'POST',
        url:'${pageContext.request.contextPath}/user',
        async:true,
        data:{'method':'login','username':username1,'password':password1},
        success:function(result){
          if(result === "success"){
        	  console.log(result);
              window.location.href = '/bookstore/index.jsp';
          }else{
            // console.log(result);
            alert("账户不存在或密码错误!");
          }
        }
      })
    })
    // console.log(username1);    
  </script>
</body>
</html>