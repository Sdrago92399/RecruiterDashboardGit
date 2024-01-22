package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Signup extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Jdbc mysql = new Jdbc();
	Methods Do = new Methods();
	
	PrintWriter out = null;
	RequestDispatcher rd = null;
	
	String user;
	String comp;
	String pass;
	String email;
	String phone;
	String otp;
	String userid;
	
	boolean tableExists;
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		mysql.jdbcInit();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("uncool");
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub  
		try {
			
			resp.setContentType("text/html");
			out = resp.getWriter();
			
			user = req.getParameter("user");
			comp = req.getParameter("comp");
			email = req.getParameter("mail");
			phone = req.getParameter("phone");
			pass = Do.encrypt(req.getParameter("pass"));
			
			otp = Do.generateOtp(mysql);
			userid = Do.generateUserId(mysql);
			
			runDB(user,email,phone,mysql);
			
			if(!mysql.duplicateEntry(phone,email)) {
				
				req=Do.setAttribute(email, comp, user, pass, phone, userid, null, req);
				Do.sendMail("otp",otp,email,req,resp,"emailOTP.jsp","signup.jsp",mysql);
				
			} else if(mysql.duplicateEntry(phone,email)) {
				System.out.println("Username or phone-number already registered");
				Do.showAlert(req,resp,"signup.jsp","duplicate",mysql);
			}
			
			//destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("an error occured");
			mysql.log( e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
			Do.showAlert(req,resp,"signup.jsp","error",mysql);
		}
	}


	private void runDB(String user, String email, String phone, Jdbc mysql) throws SQLException, ServletException {
		// TODO Auto-generated method stub
		tableExists = mysql.tableExists();
		
		if (tableExists) {
			System.out.println("db already exists");
		}
		else if(!tableExists) {
			System.out.println("db doesn't exists");
			mysql.createTable();
		}
		
		if(!mysql.duplicateEntry(phone,email)) {
			mysql.logOtp(userid,otp);
		}
	}
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		mysql.jdbcDestroy();
	}
	
	
}
