package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyOTP extends HttpServlet {

	private static final long serialVersionUID = 3L;
	
	Jdbc mysql = new Jdbc();
	Methods Do = new Methods();
	Mail sendMail = new Mail();
	
	PrintWriter out = null;
	
	private int otp;
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		mysql.jdbcInit();
	}
	
	//Send OTP to email using get
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			resp.setContentType("text/html");
			out = resp.getWriter();
			
			String email = req.getParameter("email");
			String user = req.getParameter("user");
			String pass = req.getParameter("pass");
			String phone = req.getParameter("phone");
			String userid = req.getParameter("userid");
			String comp = req.getParameter("user");

			otp=Do.generateOtp();
			mysql.setOtp(Integer.parseInt(userid),otp);
			sendMail.otp(Integer.toString(otp),email);
			
			req=Do.setAttribute(email,comp,user,pass,phone,userid,null,req);
			
			RequestDispatcher rd = req.getRequestDispatcher("emailOTP.jsp");
			rd.forward(req, resp);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("an error occured");
			mysql.log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
			Do.showAlert(req,resp,"emailOTP.jsp","error");
		}
	}

	//Verify OTP using post
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		try{
			resp.setContentType("text/html");
			out = resp.getWriter();
			
			String email = req.getParameter("email");
			String reqId = Do.generateReqId();
			String user = req.getParameter("user");
			String comp = req.getParameter("comp");
			String pass = req.getParameter("pass");
			String phone = req.getParameter("phone");
			String userid = req.getParameter("userid");
			String serverotp = mysql.getOtp(Integer.parseInt(userid));
			String otpInput = req.getParameter("first") 
					+ req.getParameter("second")
					+ req.getParameter("third")
					+ req.getParameter("fourth")
					+ req.getParameter("fifth")
					+ req.getParameter("sixth");
			
			if(otpInput.equals(serverotp)) {
				runDB(reqId,user,comp,pass,email,phone,mysql);
				System.out.println("Registration success");
				Do.showAlert(req,resp,"login.jsp","registered");
				
			} else if(otpInput.equals("expired")) {
				
				System.out.println("Otp expired");
				
				req=Do.setAttribute(email,comp,user,pass,phone,userid,null,req);
				Do.showAlert(req,resp,"emailOTP.jsp","expired");
				
			} else if(!(otpInput.equals(serverotp))) {
				
				System.out.println("Wrong Otp. Try again");
				
				req=Do.setAttribute(email,comp,user,pass,phone,userid,null,req);
				Do.showAlert(req,resp,"emailOTP.jsp","notOtp");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("an error occured");
			
			mysql.log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
			Do.showAlert(req,resp,"emailOTP.jsp","error");
		
		}
	}


	private void runDB(String reqId, String user, String comp, String pass, String mail, String phone, Jdbc mysql) throws ServletException, SQLException {
		// TODO Auto-generated method stub
		
		try {
			if (mysql.tableExists()) {
				System.out.println("db already exists");
			}
			else if(!mysql.tableExists()) {
				System.out.println("db doesn't exists");
				mysql.createTable();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysql.log(Integer.toString(e.getErrorCode()),e.getMessage());
//			mysql.backup(Integer.toString(e.getErrorCode()));
//			mysql.delTable();
//			mysql.createTable();
		} finally {
			mysql.addData(reqId,user,comp,pass,mail,phone);
		}
	}
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		mysql.jdbcDestroy();
	}
	
}
