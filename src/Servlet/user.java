package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import JavaBean.SqlBean;

/**
 * Servlet implementation class user
 */
@WebServlet("/user")
public class user extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SqlBean sqlBean = new SqlBean();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public user() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method=request.getParameter("method");//�õ������ֵ������ݴ����ִֵ�в�ͬ�ķ�������
        //System.out.println("method"+method);
        if(method.equals("login"))
        {
        	login(request, response);//ִ��login����
        }
        else if(method.equals("register")){
            register(request, response);//ִ��ע�����
        }
	}
	//��½�ӿ�
	private void login(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
//		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username);
		System.out.println(password);
		boolean isLogin = false;
		try {
			isLogin = sqlBean.isExist(username, password);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isLogin) {
			System.out.println(isLogin);
			//д��session
			HttpSession session = request.getSession();
			session.setAttribute("login", "true");
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
	
	//ע��ӿ�
	private void register(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");	
		//����֤���û����Ƿ��ѱ�ע��
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean isRegister = false;
		try {
			isRegister = sqlBean.isExistUserName(username);
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(isRegister) {
			PrintWriter out =null ;
			out =response.getWriter();
			out.write("isExist");
			out.close();
		}else {
			try {
				isRegister = sqlBean.isAddUser(username, password);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(isRegister) {
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

}
