package JavaBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
public class SqlBean {
	Connection conn=null;
	public SqlBean(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useSSL=false","root","root");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//获取Book所有数据
	public List<BookBean> queryAllBook() throws SQLException{
		List<BookBean> list=new ArrayList<BookBean>();
		String sql="select * from book";
		try {
			Statement stmt=conn.createStatement();//statement执行语句和获取查询结果
			ResultSet rs=stmt.executeQuery(sql);//执行查询
			while(rs.next()){
				BookBean book=new BookBean();//实例化book对象
				book.setRecID(rs.getInt("recID"));//对recID赋值
				book.setTitle(rs.getString("title"));//对title赋值
				book.setType(rs.getString("type"));//对type赋值
				book.setPrice(rs.getFloat("price"));//对价格赋值
				book.setPicture(rs.getString("picture"));//对图片url赋值
				list.add(book);//将图书对象添加到集合中
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}
	//book删除一条数据
	public boolean isDeleteOneBook(int id)throws SQLException{
		PreparedStatement pre = null;
		String sql = "delete from book where recID=?";
		boolean isDelete = false;
		try {
			pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setInt(1, id);
			int row = pre.executeUpdate();
			if(row > 0) {
				isDelete = true;
			}
			pre.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pre.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return isDelete;
	}
	//book根据书名或类型查找图书
	public List<BookBean> queryBook(String sql) throws SQLException{
		PreparedStatement pre = null;
		List<BookBean> list=new ArrayList<BookBean>();
		try {
			Statement stmt=conn.createStatement();//statement执行语句和获取查询结果
			ResultSet rs=stmt.executeQuery(sql);//执行查询
			while(rs.next()){
				BookBean book=new BookBean();//实例化book对象
				book.setRecID(rs.getInt("recID"));//对recID赋值
				book.setTitle(rs.getString("title"));//对title赋值
				book.setType(rs.getString("type"));//对type赋值
				book.setPrice(rs.getFloat("price"));//对价格赋值
				book.setPicture(rs.getString("picture"));//对图片url赋值
				list.add(book);//将图书对象添加到集合中
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}
	
	//book增加一本书
	public boolean isAddBook(String title,String type,Float price,String picture)throws SQLException{
		PreparedStatement pre = null;
		String sql = "insert into book(title,type,price,picture) values(?,?,?,?)";
		boolean isAddBook = false;
		try {
			pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setString(1, title);
			pre.setString(2, type);
			pre.setFloat(3, price);
			pre.setString(4, picture);
			int row = pre.executeUpdate();
			if(row > 0) {
				isAddBook = true;
			}
			pre.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pre.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return isAddBook;
	}
	
	//book编辑一本书（修改了图片）
	public boolean isEditBook(String title,String type,Float price,String picture,int recID)throws SQLException{
		PreparedStatement pre = null;
		String sql = "update book set title=?,type=?,price=?,picture=? where recID=?"; 
		boolean isEditBook = false;
		try {
			pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setString(1, title);
			pre.setString(2, type);
			pre.setFloat(3, price);
			pre.setString(4, picture);
			pre.setInt(5, recID);
			int row = pre.executeUpdate();
			if(row > 0) {
				isEditBook = true;
			}
			pre.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pre.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return isEditBook;
	}
	//book编辑一本书（没有修改图片）
	public boolean isEditBook1(String title,String type,Float price,int recID)throws SQLException{
		PreparedStatement pre = null;
		String sql = "update book set title=?,type=?,price=? where recID=?"; 
		boolean isEditBook = false;
		try {
			pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setString(1, title);
			pre.setString(2, type);
			pre.setFloat(3, price);
			pre.setInt(4, recID);
			int row = pre.executeUpdate();
			if(row > 0) {
				isEditBook = true;
			}
			pre.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pre.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return isEditBook;
	}
	//book根据查询出一条数据
	public List<BookBean> queryOneBook(int recID) throws SQLException{
		//PreparedStatement pre = null;
		List<BookBean> list=new ArrayList<BookBean>();
		String sql = "select * from book where recID=?";
		try {
			PreparedStatement  pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setInt(1, recID);
			//Statement stmt=conn.createStatement();//statement执行语句和获取查询结果
			ResultSet rs=pre.executeQuery();//执行查询
			while(rs.next()){
				BookBean book=new BookBean();//实例化book对象
				book.setRecID(rs.getInt("recID"));//对recID赋值
				book.setTitle(rs.getString("title"));//对title赋值
				book.setType(rs.getString("type"));//对type赋值
				book.setPrice(rs.getFloat("price"));//对价格赋值
				book.setPicture(rs.getString("picture"));//对图片url赋值
				list.add(book);//将图书对象添加到集合中
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//user用户是否存在（根据用户名和密码查询）
	public boolean isExist(String username, String password)throws SQLException{
		PreparedStatement pre = null;
		ResultSet rs;
		String sql = "select * from user where username=? and password =?";
		boolean isExist = false;
		try {
			pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setString(1, username);
			pre.setString(2, password);
			rs = pre.executeQuery();
			if(rs.next()) {
				isExist = true;
			}
			rs.close();
			pre.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pre.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return isExist;
	}
	
	//user用户名是否存在（根据用户名查询）
	public boolean isExistUserName(String username)throws SQLException{
		PreparedStatement pre = null;
		ResultSet rs;
		String sql = "select * from user where username=?";
		boolean isExistUserName = false;
		try {
			pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setString(1, username);
			rs = pre.executeQuery();
			if(rs.next()) {
				isExistUserName = true;
			}
			rs.close();
			pre.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pre.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return isExistUserName;
	}
	
	//user增加一个用户
	public boolean isAddUser(String username,String password)throws SQLException{
		PreparedStatement pre = null;
		String sql = "insert into user(username,password) values(?,?)";
		boolean isAddUser = false;
		try {
			pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setString(1, username);
			pre.setString(2, password);
			int row = pre.executeUpdate();
			if(row > 0) {
				isAddUser = true;
			}
			pre.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pre.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return isAddUser;
	}
}
