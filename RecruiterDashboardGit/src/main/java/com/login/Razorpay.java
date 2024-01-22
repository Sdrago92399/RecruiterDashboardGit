package com.login;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.razorpay.*;

/**
 * Servlet implementation class OrderCreationDemo
 */
public class Razorpay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	Jdbc mysql = new Jdbc();
	Methods Do = new Methods();

	String reqId;
	String rayId=Globals.Rayzorpay_Key_ID;
	String raySecret=Globals.Rayzorpay_Client_Secret;

	ResultSet resultset;

	float amt;
	
	
    public Razorpay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RazorpayClient client=null;
		String orderId=null;
		int amt=Integer.parseInt(request.getParameter("amount"));
		try {
			  client=new RazorpayClient(rayId,raySecret);
			  JSONObject options = new JSONObject();
			  options.put("amount", amt); // amount in the smallest currency unit
			  options.put("currency", "INR");
			  //options.put("receipt", "zxr456");
			  options.put("payment_capture", true);
			 
			Order order = client.orders.create(options);
            orderId=order.get("id");
            System.out.println(orderId);
            request.setAttribute("orderId", orderId);
            request.setAttribute("amt", amt);
            Do.showAlert(request, response, "payment.jsp", null,mysql);
            
			} catch (Exception e) {
			  // Handle Exception
				 e.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			  JSONObject options = new JSONObject();
			  options.put( "razorpay_payment_id",request.getParameter("razorpay_payment_id")); // amount in the smallest currency unit
			  options.put("razorpay_order_id",request.getParameter("razorpay_order_id"));
			  options.put("razorpay_signature",request.getParameter("razorpay_signature"));
			  boolean paymentIsVerified=Utils.verifyPaymentSignature(options,raySecret);
			  if(paymentIsVerified) {
				  System.out.println("Payment success");
				  runDB(amt,reqId);
			  } else if(!paymentIsVerified) {
				  System.out.println("Payment faied");
			  }
		    } catch (Exception e) {
			  // Handle Exception
			 e.printStackTrace();
			}
		 
	}

	private void runDB(float amt, String reqId) throws SQLException {
		// TODO Auto-generated method stub
		boolean tableExists = mysql.tableExists();
		
		if (tableExists & mysql.walletIsVerified(reqId)) {
			System.out.println("db already exists");
			
			resultset=mysql.getLastTransaction(reqId);
			int sNo=resultset.getInt("sNo")+1;
			float bal=resultset.getInt("bal")+amt;
			
			mysql.addTransact(null, sNo, amt, bal, reqId);
		}
		else if(!tableExists) {
			System.out.println("db doesn't exists");
			mysql.createTable();
		}
	}

}