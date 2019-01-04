<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="public/javascripts/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" href="public/javascripts/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="public/stylesheets/index.css">
<link rel='stylesheet' href='public/stylesheets/public.css' />
<link rel='stylesheet' href='public/stylesheets/admin.css' />
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
    <a class="layui-btn layui-btn-xs layui-btn-primary " lay-event="detail">查看</a>
	<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
	<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</a>
  </script>
  <!-- 头部操作 -->
  <script type="text/html" id="toolbar">
    <div class="layui-btn-container">
      <button class="layui-btn layui-btn-sm" lay-event="addBook">添加图书</button>
    </div>
  </script>
  <script>
  //价格输入框验证数字
  function verifyNumber1(){
	  let price = $("#price1").val();
	  console.log(price);
	  //数字开头，
	  let reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/g;
	  if(reg.test(price)){
		  //alert("正确")
	  }else{
		  alert("请输入正确的数字格式！");
		  $("#price1").val("");
	  }	  
  }
  function verifyNumber2(){
	  let price = $("#price").val();
	  console.log(price);
	  //数字开头，
	  let reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/g;
	  if(reg.test(price)){
		  //alert("正确")
	  }else{
		  alert("请输入正确的数字格式！");
		  $("#price").val("");
	  }	  
  }
  </script>
  <script>
    layui.use(['table','jquery','layer','upload'], function(){
      var table = layui.table;
      var $ = layui.jquery;
      var layer = layui.layer;
      var upload = layui.upload;
      //第一个实例
      var table1 = table.render({
        elem: '#manager',
        width:1150,
        height: 490,
        url: '${pageContext.request.contextPath}/book?method=queryAllBook' ,//数据接口
        page: true ,//开启分页
        toolbar:'#toolbar',//开启头部工具栏
        cols: [[ //表头
          {type:'checkbox',fixed:'left',align:'center'},
          {field:'',title:'序号',type:'numbers',width:80},
          {field: 'title', title: '书名', width:110},
          {field: 'type', title: '类型', width:130} ,
          {field: 'price', title: '价格', width: 180,align:'center'},
          {field: 'right', title: '操作',align:'center', width: 200, toolbar:'#bar'}
        ]]
      });
      //监听行工具栏事件
      table.on('tool(manager)',function(obj){
        let data = obj.data;//获取当前行所有数据
        let id = data.recID;//获取选中行的id
        let picture = data.picture;
        let photoSrc = "http://localhost:8080/bookstore"+picture;
        let layEvent = obj.event;//获取点击的是哪个操作按钮
        let tr = obj.tr;//获取当前行tr的DOM对象
        if(layEvent === 'delete'){
          console.log(obj);
          $.ajax({
            type:'POST',
            url:'${pageContext.request.contextPath}/book?method=delOneBook',
            data:{'recID':id},
            success:function(data){
              if(data === "success"){
                layer.confirm('已成功删除！',function(index){
                  obj.del();//删除对应行（tr）的DOM结构，并更新缓存
                  layer.close(index);//关闭对话框
                })
              }else{
                alert("删除失败");
              }
            }
          })
        }else if(layEvent === 'edit'){
            var index1 = layer.open({
                type:0,
                title:'编辑图书',
                btn:['提交','取消'],
                //按钮的回调操作
                btn1:function(index, layero){
              		let photo = $("#photo").get(0).files[0];
         			let title = $("#title").val();
         			let type = $("#type").val();
         			let price = $("#price").val();
	              	let data = new FormData();//将表单的数据序列化
	              	//data.append("title",title);
	              	data.append("photo",photo);
	              	//console.log(photo);
	                  //console.log(data);
	                  $.ajax({
	                    type:"POST",
	                    //如果请求数据有文字必须通过这种方式传输数据
	                    url:"${pageContext.request.contextPath}/book?method=editOneBook&title="+title+"&type="+type+"&price="+price+"&recID="+id,
	                    data: data,
	                    enctype: 'multipart/form-data',
	                    dataType:'JSON',
	                    cache:false,
	                    contentType: false,//如果加了这个后台获取不到text的值  不要去处理发送的数据
	                    processData:false,//这个同上 不要去设置Content-Type请求头
	                    success:function(result){
	                  	console.log(result);
	                  	if(result.msg === "success"){
	                        layer.confirm('更新成功！',function(index){
	                          layer.close(index);//关闭对话框
	                        })
	                        table1.reload();//重载表格
	                  	}
	                    },
	                    error:function(){
	                  	console.log("更新失败！");
	                    }
	                  })
	                  // console.log(state);
	                },
	                btn2:function(index, layero){
	                  // console.log("999");
	                },
	                content:'<div class="login-item">\
	                          <span class="user-icon"></span>\
	                          <input class="form-text" type="text" placeholder="请输入书名"  id="title" name="title">\
	                        </div>\
	                        <div class="login-item">\
	                          <span class="user-icon"></span>\
	                          <input class="form-text" type="text" placeholder="请输入类型"  id="type" name="type">\
	                        </div>\
	                        <div class="login-item">\
	                          <span class="user-icon"></span>\
	                          <input class="form-text" type="text" placeholder="请输入价格"  id="price" name="price" onfocusout="verifyNumber2()">\
	                        </div>\
	                        <div class="login-item">\
	                          <input class="form-text" type="file" id="photo" name="photo">\
	                          <img src="" style="width:275px;height:140px;">\
	                        </div>',
	                success:function(layero, index){
	                    let data = {
	                    	"recID":id
	                    }
	                    console.log(data);
                       	//将原数据展示在输入框里面
			        	$.ajax({
			        		type:"POST",
			        		url:"${pageContext.request.contextPath}/book?method=queryOneBook",
			        		data: data,
			        		success:function(result){
			        			console.log(result.data);
			        			let msg = result.data[0];
			        			console.log(msg);
			        			$("#title").val(msg.title);
			        			$("#type").val(msg.type);
			        			$("#price").val(msg.price);
			        			console.log($("#photo").get(0).files[0]);
			        			//$("#photo").get(0).files[0] = "http://localhost:8080/bookstore"+msg.picture;
			        			//$("#picture").val("http://localhost:8080/bookstore"+msg.picture);
			        			$("img").attr("src","http://localhost:8080/bookstore"+msg.picture);
			        		}
			        	})
	                }
              });
        }else if(layEvent === 'detail'){
            var index3 = layer.open({
                type:0,
                title:'查看详情',
                content:'<div style="width:200px;height:200px;margin:0 auto;">\
                          <img src="" id="img" style="width:100%;height:100%;">\
                        </div>'
              });
            $("#img").attr('src',photoSrc);
        }
      });
      //监听头部工具栏事件
      table.on('toolbar(manager)',function(obj){
        switch(obj.event){
          case 'addBook':
            // console.log("4444");
            var index2 = layer.open({
              type:0,
              title:'添加图书',
              btn:['提交','取消'],
              //按钮的回调操作
              btn1:function(index, layero){
            	let photo = $("#photo1").get(0).files[0];
       			let title = $("#title1").val();
       			let type = $("#type1").val();
       			let price = $("#price1").val();
            	let data = new FormData();//将表单的数据序列化
            	//data.append("title",title);
            	data.append("photo",photo);
            	//console.log(photo);
                //console.log(data);
                $.ajax({
                  type:"POST",
                  //如果请求数据有文字必须通过这种方式传输数据
                  url:"${pageContext.request.contextPath}/book?method=addOneBook&title="+title+"&type="+type+"&price="+price,
                  data: data,
                  enctype: 'multipart/form-data',
                  dataType:'JSON',
                  cache:false,
                  contentType: false,//如果加了这个后台获取不到text的值  不要去处理发送的数据
                  processData:false,//这个同上 不要去设置Content-Type请求头
                  success:function(result){
                	console.log(result);
                	if(result.msg === "success"){
                      layer.confirm('已成功上传！',function(index){
                        layer.close(index);//关闭对话框
                      })
                      table1.reload();//重载表格
                	}
                  },
                  error:function(){
                	console.log("上传失败！");
                  }
                })
                // console.log(state);
              },
              btn2:function(index, layero){
                // console.log("999");
              },
              content:'<div class="login-item">\
                        <span class="user-icon"></span>\
                        <input class="form-text" type="text" placeholder="请输入书名"  id="title1" name="title1">\
                      </div>\
                      <div class="login-item">\
                        <span class="user-icon"></span>\
                        <input class="form-text" type="text" placeholder="请输入类型"  id="type1" name="type1">\
                      </div>\
                      <div class="login-item">\
                        <span class="user-icon"></span>\
                        <input class="form-text" type="text" placeholder="请输入价格"  id="price1" name="price1" onfocusout="verifyNumber1()">\
                      </div>\
                      <div class="login-item">\
                        <input class="form-text" type="file" id="photo1" id="photo1" name="photo1">\
                      </div>',
              success:function(layero, index){
                 //渲染添加图书上传图片按钮  
              }
            });
          break;
        }
      })
      
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