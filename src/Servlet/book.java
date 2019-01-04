package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import JavaBean.BookBean;
import JavaBean.SqlBean;


/**
 * Servlet implementation class book
 */
@WebServlet("/book")
public class book extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public book() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method=request.getParameter("method");//得到传入的值下面根据传入的值执行不同的方法！！
        System.out.println("method"+method);
        if(method.equals("queryAllBook")){
        	queryAllBook(request, response);//执行login代码
        }
        else if(method.equals("addOneBook")){
        	addOneBook(request, response);//执行注册代码
        }else if(method.equals("delOneBook")) {
        	delOneBook(request,response);//执行删除代码
        }else if(method.equals("queryBook")) {
        	queryBook(request,response);//执行查询代码
        }else if(method.equals("editOneBook")) {
        	editOneBook(request,response);//执行更新查询代码
        }else if(method.equals("queryOneBook")) {
        	queryOneBook(request,response);//执行查询一条数据代码
        }

	}
	//查询所有图书数据
	private void queryAllBook(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		SqlBean sqlBean=new SqlBean();
		List<BookBean> list;
		try {
			list = sqlBean.queryAllBook();
			String[] array = new String[list.size()];
			for(int i = 0; i < list.size();i++){
		        array[i] = JSON.toJSONString(list.get(i));
		    }
			String jsonStr ="{\"code\":0,\"msg\":\"\",\"count\":" + array.length + ",\"data\":" + Arrays.toString(array) + "}";
			PrintWriter out =null ;
			out =response.getWriter();
			out.write(jsonStr);
			out.close();
			System.out.println(jsonStr);
			System.out.println(Arrays.toString(array));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//添加一本书
	private void addOneBook(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		SmartUpload smartUpload = new SmartUpload();
		ServletConfig config = this.getServletConfig();
		smartUpload.initialize(config,request,response);
		try{
			//System.out.println("7777");
			smartUpload.upload();
			String title = request.getParameter("title");
			String type = request.getParameter("type");
			Float price = Float.parseFloat(request.getParameter("price"));
			//获取值在upload后面执行并对传输值（中文）进行转码,这样对个别中文也会出现bug
			//String title=new String(smartUpload.getRequest().getParameter("title").getBytes("iso-8859-1"),"utf-8");
			//String type=new String(smartUpload.getRequest().getParameter("type").getBytes("GBK"),"utf-8");
			//String price=new String(smartUpload.getRequest().getParameter("price").getBytes("GBK"),"utf-8");
			//对传输值（中文）进行转码
			//title = new String(title.getBytes("GBK"),"utf-8");
			System.out.println(title);
			System.out.println(type);
			System.out.println(price);
			File smartFile = smartUpload.getFiles().getFile(0);
			//存储图片并且对图片重命名
			smartFile.saveAs("/upload/"+type+"-"+title+"."+smartFile.getFileExt(),smartUpload.SAVE_VIRTUAL);
			String picture = "/upload/"+type+"-"+title+"."+smartFile.getFileExt();
			int fileSize = smartFile.getSize();
			//System.out.println("/upload/"+smartFile.getFileName());
			boolean isAddBook = false;
			SqlBean sqlBean = new SqlBean();
			try {
				isAddBook = sqlBean.isAddBook(title, type, price, picture);
			}catch(SQLException e){
				e.printStackTrace();
			}
			if(isAddBook) {
				PrintWriter out =null ;
				out =response.getWriter();
				out.write("{\"msg\":\"success\"}");
				out.close();
			}else {
				PrintWriter out =null ;
				out =response.getWriter();
				out.write("{\"msg\":\"fail\"}");
				out.close();
			}
		}catch(SmartUploadException e){
			e.printStackTrace();
		}
		//System.out.println("666");
	}
	//删除一本书
	private void delOneBook(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		//response.setContentType("application/json;charset=utf-8");
		String id = request.getParameter("recID");
		System.out.println(id);
		response.setCharacterEncoding("UTF-8");
		SqlBean sqlBean=new SqlBean();
		boolean isDel = false;
		try {
			isDel = sqlBean.isDeleteOneBook(Integer.parseInt(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(isDel) {
			PrintWriter out =null ;
			out =response.getWriter();
			out.write("success");
			out.close();
		}else {
			PrintWriter out =null ;
			out =response.getWriter();
			out.write("fail");
			out.close();
		}
	}
	
	//按类型或书名查询图书
	private void queryBook(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String str = request.getParameter("msg");
		String data = request.getParameter("data");
		System.out.println(str);
		System.out.println(data);
		String sql;
		String jsonStr;
		if(str.equals("type")) {
			sql = "select * from book where type like" + "\"%" + data + "%\"";
		}else {
			sql = "select * from book where title like" + "\"%" + data + "%\"";
		}
		System.out.println(sql);
		SqlBean sqlBean=new SqlBean();
		List<BookBean> list;
		try {
			list = sqlBean.queryBook(sql);
			String[] array = new String[list.size()];
			for(int i = 0; i < list.size();i++){
		        array[i] = JSON.toJSONString(list.get(i));
		    }
			if(array.length > 0) {
				jsonStr ="{\"code\":0,\"msg\":\"success\",\"count\":" + array.length + ",\"data\":" + Arrays.toString(array) + "}";
			}else {
				jsonStr ="{\"code\":0,\"msg\":\"fail\",\"count\":" + array.length + ",\"data\":" + Arrays.toString(array) + "}";
			}
			PrintWriter out =null ;
			out =response.getWriter();
			out.write(jsonStr);
			out.close();
			System.out.println(jsonStr);
			//System.out.println(Arrays.toString(array));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//编辑一本书
	private void editOneBook(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		SmartUpload smartUpload = new SmartUpload();
		ServletConfig config = this.getServletConfig();
		smartUpload.initialize(config,request,response);
		try{
			smartUpload.upload();
			String title = request.getParameter("title");
			String type = request.getParameter("type");
			Float price = Float.parseFloat(request.getParameter("price"));
			int id =  Integer.parseInt(request.getParameter("recID"));
			boolean isEditBook = false;
			SqlBean sqlBean = new SqlBean();
			//如果图片不更改呢
			System.out.println("测试成功3");
			//没有修改图片
			if(smartUpload.getFiles().getSize() < 1) {
				System.out.println("测试成功4");
				try {
					isEditBook = sqlBean.isEditBook1(title, type, price, id);
				}catch(SQLException e){
					e.printStackTrace();
				}			
			}else {
				//修改了图片
				System.out.println("测试成功5");
				File smartFile = smartUpload.getFiles().getFile(0);
				smartFile.saveAs("/upload/"+type+"-"+title+"."+smartFile.getFileExt(),smartUpload.SAVE_VIRTUAL);
				String picture = "/upload/"+type+"-"+title+"."+smartFile.getFileExt();
				try {
					isEditBook = sqlBean.isEditBook(title, type, price, picture, id);
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			
			System.out.println("测试成功6");					
			if(isEditBook) {
				PrintWriter out =null ;
				out =response.getWriter();
				out.write("{\"msg\":\"success\"}");
				out.close();
			}else {
				PrintWriter out =null ;
				out =response.getWriter();
				out.write("{\"msg\":\"fail\"}");
				out.close();
			}
		}catch(SmartUploadException e){
			e.printStackTrace();
		}
		//System.out.println("666");
	}
	
	//根据图书id查询出一条数据
	private void queryOneBook(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		SqlBean sqlBean=new SqlBean();
		List<BookBean> list;
		int id =  Integer.parseInt(request.getParameter("recID"));
		System.out.println(id);
		try {
			list = sqlBean.queryOneBook(id);
			String[] array = new String[list.size()];
			for(int i = 0; i < list.size();i++){
		        array[i] = JSON.toJSONString(list.get(i));
		    }
			String jsonStr ="{\"code\":0,\"msg\":\"\",\"count\":" + array.length + ",\"data\":" + Arrays.toString(array) + "}";
			PrintWriter out =null ;
			out =response.getWriter();
			out.write(jsonStr);
			out.close();
			System.out.println(jsonStr);
			System.out.println(Arrays.toString(array));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
