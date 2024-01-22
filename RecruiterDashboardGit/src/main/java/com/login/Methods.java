package com.login;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

public class Methods {
	
	Mail sendMail = new Mail();
	
	RequestDispatcher rd = null;
	Random generator = new Random();
	
	String ivRaw = "FFFFFFFFFFFFFFFF", returnThis="";
	IvParameterSpec iv = new IvParameterSpec(ivRaw.getBytes());
	String keyBytes = "3da770b31f80efade1d4dbeefcb2d155";
	boolean sentSuccess = false;
	
    
	String encrypt(String message) 
      throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(message.getBytes());
        return Base64.encodeBase64String(encrypted);
    }
    
    
    String decrypt(String encryptedMessage) 
    		  throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, 
	    BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    SecretKeySpec secretKey = new SecretKeySpec(keyBytes.getBytes(), "AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
	    byte[] decrypted = cipher.doFinal(Base64.decodeBase64(encryptedMessage));
	    return new String(decrypted);
    }
    
    
	HttpServletRequest setAttribute(String email, String comp, String user, String pass, String phone, String userid, String others, HttpServletRequest req) {
		// TODO Auto-generated method stub
		req.setAttribute("comp", comp);
		req.setAttribute("email", email);
		req.setAttribute("user", user);
		req.setAttribute("pass", pass);
		req.setAttribute("phone", phone);
		req.setAttribute("userid", userid);
		req.setAttribute("others", others);
		return req;
	}
	
	
	String getMethodName(StackTraceElement[] stackTrace) {
		// TODO Auto-generated method stub
		
		String returnThis = null;
		
		for (StackTraceElement element : stackTrace) {
	        String className = element.getClassName();

	        // Check if the class name starts with your package name
	        if (className.startsWith("com.login")) {
	            String methodName = element.getMethodName();
	            String fileName = element.getFileName();
	            int lineNumber = element.getLineNumber();

	            // Print or use this information as needed
	            returnThis = " in " + methodName + " at line " + lineNumber + " in file " + fileName;

	            // You can break the loop here if you only want the first occurrence
	            break;
	        }
	    }
		return returnThis;
	}
    
	
	void showAlert(HttpServletRequest req, HttpServletResponse resp, String page, String alert, Jdbc mysql) {
		// TODO Auto-generated method stub
		try {
			rd = req.getRequestDispatcher(page);
			req.setAttribute("alert", alert);
			rd.forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysql.log(e.getClass().getName(), e.getMessage()+getMethodName(e.getStackTrace()));
		}
	}


	void sendMail(String mailType, String valueToSend, String sendTo, HttpServletRequest req, HttpServletResponse resp, String onSuccess, String onFailure, Jdbc mysql) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		switch(mailType) {
			case "otp":
				sentSuccess = sendMail.otp(valueToSend, sendTo);
				break;
			case "pass":
				sentSuccess = sendMail.changePassword(valueToSend, sendTo);
				break;
		}
		if(sentSuccess) {
			showAlert(req,resp,onSuccess,"mail",mysql);
			System.out.println("Mail sent");
			
		} else if(!sentSuccess) {
			showAlert(req,resp,onFailure,"notMail",mysql);
			System.out.println("Coudn't send mail");
		}
		
	}
	
	
	String generateOtp(Jdbc mysql) throws SQLException {
		// TODO Auto-generated method stub
		returnThis = Integer.toString(generator.nextInt((999999 - 100000) + 1) + 100000);
		boolean isDuplicateId = mysql.isDuplicateId(returnThis,"otp_table","otp");
		
		if(isDuplicateId) {
			System.out.println("Generatng new one");
			return generateOtp(mysql);
		} else if(!isDuplicateId) {
			return returnThis;
		} else {
			return null;
		}
	}


	String generateUserId(Jdbc mysql) throws SQLException {
		// TODO Auto-generated method stub
		returnThis = Integer.toString(generator.nextInt((999999 - 100000) + 1) + 100000);
		boolean isDuplicateId = mysql.isDuplicateId(returnThis,"otp_table","user_id");
		
		if(isDuplicateId) {
			System.out.println("Generatng new one");
			return generateUserId(mysql);
		} else if(!isDuplicateId) {
			return returnThis;
		} else {
			return null;
		}
	}


	public String generateReqId(Jdbc mysql) throws SQLException {
		// TODO Auto-generated method stub
		returnThis = "ED"+Integer.toString(generator.nextInt((99999999 - 10000000) + 1) + 10000000);
		
		boolean isDuplicateId = mysql.isDuplicateId(returnThis,"db","reqId");
		
		if(isDuplicateId) {
			System.out.println("Generatng new one");
			return generateReqId(mysql);
		} else if(!isDuplicateId) {
			return returnThis;
		} else {
			return null;
		}
	}
	
	
	public String generateChatId(Jdbc mysql) throws SQLException {
		// TODO Auto-generated method stub
		int noOfChars = 5;
		for (int i = 0; i < noOfChars; i++) {
			returnThis += (char) (generator.nextInt(26) + 65);
		}
		
		returnThis+=Integer.toString(generator.nextInt(10 * (int) Math.pow(10,(10 - noOfChars - 1))) + (int) Math.pow(10,(10 - noOfChars - 1)));
		
		boolean isDuplicateId = mysql.isDuplicateId(returnThis,"master_chats","chatId");
		
		if(isDuplicateId) {
			System.out.println("Generatng new one");
			return generateChatId(mysql);
		} else if(!isDuplicateId) {
			return returnThis;
		} else {
			return null;
		}
	}


	public void showDahboardPage(HttpServletRequest req, HttpServletResponse resp, String page) throws ServletException, IOException {
		// TODO Auto-generated method stub
		rd = req.getRequestDispatcher("dashboard.jsp");
		req.setAttribute("alert", page);
		rd.forward(req, resp);
	}
	
	
}