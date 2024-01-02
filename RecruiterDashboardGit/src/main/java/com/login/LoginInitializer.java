package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInitializer extends HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	
	Jdbc mysql = new Jdbc();
	Methods Do = new Methods();
	Mail sendMail = new Mail();
	
	PrintWriter out = null;
	HashMap<String,ResultSet> dataBank = new HashMap<String,ResultSet>();
	ResultSet userResultset = null;
	ResultSet walletResultset = null;

	String user;
	String mail;
	String phone;
	String comp;
	String reqId;
	
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
			
			dataBank = runDB(req.getParameter("user"));
			
			userResultset=dataBank.get("user");
			while(userResultset.next()) {
				user = userResultset.getString("user");
				mail = userResultset.getString("mail");
				comp = userResultset.getString("comp");
				phone = userResultset.getString("phone");
				reqId = userResultset.getString("reqId");
			}
			
			String table="";
			walletResultset=dataBank.get("wallet");
			while(walletResultset.next()) {
				int sNo=walletResultset.getInt("sNo");
				Timestamp date=walletResultset.getTimestamp("time");
				float trnsc=walletResultset.getFloat("trnsc");
				String reqId=walletResultset.getString("reqId");
				
				String credit="<tr class=\"addition\">\r\n"
						+ "	<td>"+sNo+"</td>\r\n"
						+ "	<td>"+date+"</td>\r\n"
						+ "	<td>Your wallet has been recharged with Rs. XXXXX amount.</td>\r\n"
						+ "</tr>\r\n";
				String debit="<tr class=\"deduction\">\r\n"
						+ "	<td>"+sNo+"</td>\r\n"
						+ "	<td>"+date+"</td>\r\n"
						+ "	<td>Your wallet had been deducted for Rs. XXXXX to see the contact details of a candidate with Eduinq ID "+reqId+"</td>\r\n"
						+ "</tr>\r\n";
				
				table+=(trnsc>0?credit.replace("XXXXX", Float.toString(trnsc)):debit.replace("XXXXX", Float.toString(-trnsc)));
			}
			
			req=Do.setAttribute(mail, comp, user, null, phone, reqId, table, req);
			Do.showAlert(req, resp, "dashboard.jsp", "profile");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("an error occured");
			mysql.log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
			Do.showAlert(req,resp,"login.jsp","error");
		}
	}



	private HashMap<String,ResultSet> runDB(String loginInput) throws SQLException {
		// TODO Auto-generated method stub
		tableExists = mysql.tableExists();
		
		if (tableExists) {
			System.out.println("db already exists");
			
			userResultset = mysql.fetchData(loginInput);
			walletResultset = mysql.fetchTransaction(mysql.getReqId(loginInput));
			dataBank.put("user", userResultset);
			dataBank.put("wallet", walletResultset);
			
		}
		else if(!tableExists) {
			System.out.println("db doesn't exists");
			mysql.createTable();
		}
		return dataBank;
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		mysql.jdbcDestroy();
	}
	
	
}
