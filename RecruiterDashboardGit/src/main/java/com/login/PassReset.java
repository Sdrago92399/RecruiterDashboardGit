package com.login;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PassReset extends HttpServlet {

	private static final long serialVersionUID = 5L;
	Jdbc mysql = new Jdbc();
	Methods Do = new Methods();
	Mail sendMail = new Mail();
	
	String alert;
	String email;
	String newpass;
	String chkuserid;
	String chkotp;
	String otp;
	String userid;
	
	boolean tableExists;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    
    @Override
	public void init() {
		// TODO Auto-generated method stub
		mysql.jdbcInit();
	}

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        
    	try {
    		chkuserid = request.getParameter("userid");
    		newpass = Do.encrypt(request.getParameter("pass"));
    		email = request.getParameter("email");
    		
    		alert = runDBget(chkuserid,newpass,email);
    		Do.showAlert(request, response, "login.jsp", alert,mysql);
    		
        } catch (Exception e) {
        	e.printStackTrace();
			System.out.println("an error occured");
			mysql.log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
			Do.showAlert(request,response,"login.jsp","error",mysql);
        }
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
    	try {
    		email = request.getParameter("email");
    		userid=Do.generateUserId(mysql);
    		otp="000000";
    		
    		runDBpost(userid,otp);
    		
    		Do.sendMail("pass",userid,email,request,response,"login.jsp","passReset.jsp",mysql);
    		
        } catch (Exception e) {
        	e.printStackTrace();
			System.out.println("an error occured");
			mysql.log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
			Do.showAlert(request,response,"login.jsp","error",mysql);
        }
        
    }
    
    
	private void runDBpost(String userid, String otp) throws SQLException {
		// TODO Auto-generated method stub
		
		if (mysql.tableExists()) {
			System.out.println("db already exists");
		}
		else {
			System.out.println("db doesn't exists");
			mysql.createTable();
		}
		mysql.logOtp(userid,otp);
	}
	
	
	private String runDBget(String chkuserid, String newpass, String email) throws SQLException {
		// TODO Auto-generated method stub
		
		String returnThis = null;
		tableExists = mysql.tableExists();
		
		if (tableExists) {
			System.out.println("db already exists");
		}
		else if (!tableExists) {
			System.out.println("db doesn't exists");
			mysql.createTable();
		}
		
		chkotp = mysql.fetchOtp(chkuserid);
		
		if (chkotp.equals("0")) {
			mysql.changePassword(email, newpass);
			returnThis = "changed";
		}
		else if (chkotp.equals("expired")) {
			returnThis = "expiredLink";
		}
		else if (!chkotp.equals("expired") & !chkotp.equals("0")) {
			returnThis = "invalid";
		}
		return returnThis;
		
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		mysql.jdbcDestroy();
	}
	
	
}
