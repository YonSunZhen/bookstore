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
	//��ȡBook��������
	public List<BookBean> queryAllBook() throws SQLException{
		List<BookBean> list=new ArrayList<BookBean>();
		String sql="select * from book";
		try {
			Statement stmt=conn.createStatement();//statementִ�����ͻ�ȡ��ѯ���
			ResultSet rs=stmt.executeQuery(sql);//ִ�в�ѯ
			while(rs.next()){
				BookBean book=new BookBean();//ʵ����book����
				book.setRecID(rs.getInt("recID"));//��recID��ֵ
				book.setTitle(rs.getString("title"));//��title��ֵ
				book.setType(rs.getString("type"));//��type��ֵ
				book.setPrice(rs.getFloat("price"));//�Լ۸�ֵ
				book.setPicture(rs.getString("picture"));//��ͼƬurl��ֵ
				list.add(book);//��ͼ�������ӵ�������
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}
	//bookɾ��һ������
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
	//book�������������Ͳ���ͼ��
	public List<BookBean> queryBook(String sql) throws SQLException{
		PreparedStatement pre = null;
		List<BookBean> list=new ArrayList<BookBean>();
		try {
			Statement stmt=conn.createStatement();//statementִ�����ͻ�ȡ��ѯ���
			ResultSet rs=stmt.executeQuery(sql);//ִ�в�ѯ
			while(rs.next()){
				BookBean book=new BookBean();//ʵ����book����
				book.setRecID(rs.getInt("recID"));//��recID��ֵ
				book.setTitle(rs.getString("title"));//��title��ֵ
				book.setType(rs.getString("type"));//��type��ֵ
				book.setPrice(rs.getFloat("price"));//�Լ۸�ֵ
				book.setPicture(rs.getString("picture"));//��ͼƬurl��ֵ
				list.add(book);//��ͼ�������ӵ�������
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}
	
	//book����һ����
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
	
	//book�༭һ���飨�޸���ͼƬ��
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
	//book�༭һ���飨û���޸�ͼƬ��
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
	//book���ݲ�ѯ��һ������
	public List<BookBean> queryOneBook(int recID) throws SQLException{
		//PreparedStatement pre = null;
		List<BookBean> list=new ArrayList<BookBean>();
		String sql = "select * from book where recID=?";
		try {
			PreparedStatement  pre = (PreparedStatement) conn.prepareStatement(sql);
			pre.setInt(1, recID);
			//Statement stmt=conn.createStatement();//statementִ�����ͻ�ȡ��ѯ���
			ResultSet rs=pre.executeQuery();//ִ�в�ѯ
			while(rs.next()){
				BookBean book=new BookBean();//ʵ����book����
				book.setRecID(rs.getInt("recID"));//��recID��ֵ
				book.setTitle(rs.getString("title"));//��title��ֵ
				book.setType(rs.getString("type"));//��type��ֵ
				book.setPrice(rs.getFloat("price"));//�Լ۸�ֵ
				book.setPicture(rs.getString("picture"));//��ͼƬurl��ֵ
				list.add(book);//��ͼ�������ӵ�������
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//user�û��Ƿ���ڣ������û����������ѯ��
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
	
	//user�û����Ƿ���ڣ������û�����ѯ��
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
	
	//user����һ���û�
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
