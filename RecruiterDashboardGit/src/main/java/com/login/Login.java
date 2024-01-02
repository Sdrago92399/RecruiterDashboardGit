package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	Jdbc mysql = new Jdbc();
	Methods Do = new Methods();
	Mail sendMail = new Mail();
	
	PrintWriter out = null;
	ResultSet rst = null;

	String reqIddb;
	String passdb;
	String maildb;
	String input;
	String pass;
	String mail;
	String phone;
	
	boolean tableExists;
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		mysql.jdbcInit();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		// TODO Auto-generated method stub
		try {
			
			resp.setContentType("text/html");
			out = resp.getWriter();
			input = req.getParameter("user");
			pass = req.getParameter("pass");
			
			runDB(input,mysql);
			
			if(input.equals(reqIddb)|input.equals(maildb)&pass.equals(passdb)) {
				
//				Cookie c = new Cookie("user", user);
//	            resp.addCookie(c);
	            
	            Do.showAlert(req,resp,"loginInit",null);
				
			} else if(reqIddb == null) {
				System.out.println("Username doesnt exist");
				Do.showAlert(req,resp,"login.jsp","notExist");
			} else if((input.equals(reqIddb)|input.equals(maildb))&!(pass.equals(passdb))) {
				System.out.println("Login Denied");
				Do.showAlert(req,resp,"login.jsp","notLogin");
			}
			
			//destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("an error occured");
			mysql.log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
			Do.showAlert(req,resp,"login.jsp","error");
		}
	}



	private void runDB(String user, Jdbc mysql) throws SQLException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
		// TODO Auto-generated method stub
		tableExists = mysql.tableExists();
		
		if (tableExists) {
			System.out.println("db already exists");
			rst = mysql.fetchData(user);
			while(rst.next()) {
				reqIddb = rst.getString("reqId");
				passdb = Do.decrypt(rst.getString("pass"));
				maildb = rst.getString("mail");
			}
		}
		else if(!tableExists) {
			System.out.println("db doesn't exists");
			mysql.createTable();
		}
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		mysql.jdbcDestroy();
	}
	
	
}
