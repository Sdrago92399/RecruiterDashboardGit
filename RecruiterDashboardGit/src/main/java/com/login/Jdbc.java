package com.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc {
	
	Methods Do = new Methods();
	
	private ResultSet resultset = null;
	private int tableResponse, walletResponse, otpResponse, backupResponse, logResponse;
	private PreparedStatement insertStatement = null, selectStatement = null, deleteStatement = null, updateStatement = null;
	
	Connection connection = null;
	Statement statement = null;
	
	final String dbname = Globals.MySQL_Database_Name;
	final String dburl = Globals.MySQL_Address;
	final String dbuser = Globals.MySQL_User;
	final String dbpass = Globals.MySQL_Pass;
	
	private String table, wallet, otp, backup, log, insertQuery, selectQuery, deleteQuery, updateQuery, error = "";
	private float trnsc=0, bal=0, tempBal;
	private Boolean returnThis=true;
	
	
	void jdbcInit() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(dburl,dbuser,dbpass);
			statement=connection.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
		}
	}
	
	
	boolean tableExists() throws SQLException {
		// TODO Auto-generated method stub
		
		table = "SELECT COUNT(*) AS table_count \r\n"
				+ "FROM information_schema.tables \r\n"
				+ "WHERE \r\n"
				+ "	table_schema = '" + dbname +  "' \r\n"
				+ "	AND table_name = 'db'";
		wallet = "SELECT COUNT(*) AS table_count \r\n"
				+ "FROM information_schema.tables \r\n"
				+ "WHERE \r\n"
				+ "	table_schema = '" + dbname +  "' \r\n"
				+ "	AND table_name = 'master_wallet'";
		otp = "SELECT COUNT(*) AS table_count \r\n"
				+ "FROM information_schema.tables \r\n"
				+ "WHERE \r\n"
				+ "	table_schema = '" + dbname +  "' \r\n"
				+ "	AND table_name = 'otp_table'";
		backup = "SELECT COUNT(*) AS table_count \r\n"
				+ "FROM information_schema.tables \r\n"
				+ "WHERE \r\n"
				+ "	table_schema = '" + dbname +  "' \r\n"
				+ "	AND table_name = 'db_backup'";
		log = "SELECT COUNT(*) AS table_count \r\n"
				+ "FROM information_schema.tables \r\n"
				+ "WHERE \r\n"
				+ "	table_schema = '" + dbname +  "' \r\n"
				+ "	AND table_name = 'log'\r\n";

	    resultset = statement.executeQuery(table);
	    resultset.next();
	    tableResponse=resultset.getInt(1);
	    
	    resultset = statement.executeQuery(wallet);
	    resultset.next();
	    walletResponse=resultset.getInt(1);
	    
	    resultset = statement.executeQuery(otp);
	    resultset.next();
	    otpResponse=resultset.getInt(1);
	    
	    resultset = statement.executeQuery(backup);
	    resultset.next();
	    backupResponse=resultset.getInt(1);
	    
	    resultset = statement.executeQuery(log);
	    resultset.next();
	    logResponse=resultset.getInt(1);
	    
	    return (tableResponse != 0 & walletResponse != 0 & otpResponse != 0 & backupResponse != 0 & logResponse != 0);
	}


	void delTable() throws SQLException {
		// TODO Auto-generated method stub
		//create a query
		table="mysqldump " + dbname +  " db > db.sql";
		wallet="mysqldump " + dbname +  " master_wallet > master_wallet.sql";
		otp="drop table otp_table";
		backup="drop table db_backup";
		log="drop table log";
		
		//create a statement
		statement.addBatch(table);
		statement.addBatch(wallet);
		statement.addBatch(otp);
//		statement.addBatch(backup);
//		statement.addBatch(log);
		
		statement.executeBatch();
		
		System.out.println("table deleted in database...");
		
		statement.clearBatch();
	}


	void addData(String reqId, String user, String comp, String pass, String mail, String phone) throws SQLException {
		// TODO Auto-generated method stub
		insertQuery ="insert into db(reqId,user,comp,pass,mail,phone) values (?,?,?,?,?,?)";
		insertStatement=connection.prepareStatement(insertQuery);
		insertStatement.setString(1, reqId);
		insertStatement.setString(2, user);
		insertStatement.setString(3, comp);
		insertStatement.setString(4, pass);
		insertStatement.setString(5, mail);
		insertStatement.setString(6, phone);
		
		try {
			insertStatement.executeUpdate();
			System.out.println("added succesfully");
		} catch (SQLException e) {
			error = e.getMessage();
			System.out.println(error); 
			if(error.contains("Duplicate entry '"+reqId+"' for key 'PRIMARY'")) {
				System.out.println("Duplicate reqId, generating new one...");
				addData(Do.generateReqId(),user,comp,pass,mail,phone);
			} else {
				System.out.println("Couldn't add data");
				e.printStackTrace();
				log(Integer.toString(e.getErrorCode()),e.getMessage());
//				mysql.backup(Integer.toString(e.getErrorCode()));
//				mysql.delTable();
//				mysql.createTable();
			}
		}
	}
	
	
	
	void createTable() throws SQLException {
		// TODO Auto-generated method stub
		//create a query
		table="CREATE TABLE db (\r\n"
				+ "	reqId char(10) NOT NULL PRIMARY KEY,\r\n"
				+ "	user varchar(200) NOT NULL, \r\n"
				+ "	comp varchar(200) NOT NULL, \r\n"
				+ "	pass varchar(200) NOT NULL, \r\n"
				+ "	mail varchar(200) NOT NULL UNIQUE, \r\n"
				+ "	phone char(10) NOT NULL UNIQUE\r\n"
				+ ")";
		
		wallet="CREATE TABLE master_wallet (\r\n"
				+ "	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n"
				+ "	trnscId varchar(10),\r\n"
				+ "	sNo INT NOT NULL,\r\n"
				+ "	time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, \r\n"
				+ "	trnsc FLOAT NOT NULL, \r\n"
				+ "	bal FLOAT NOT NULL,\r\n"
				+ "	reqId char(10) NOT NULL\r\n"
				+ ")";
		
		otp="CREATE TABLE otp_table (\r\n"
				+ "    user_id INT,\r\n"
				+ "    otp INT(6) NOT NULL,\r\n"
				+ "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\r\n"
				+ "    PRIMARY KEY (user_id, otp)\r\n"
				+ ")";
		backup="CREATE TABLE db_backup (\r\n"
				+ "	time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\r\n"
				+ "	log varchar(200),\r\n"
				+ "	reqId char(10),\r\n"
				+ "	user varchar(200), \r\n"
				+ "	comp varchar(200) NOT NULL, \r\n"
				+ "	pass varchar(200) NOT NULL, \r\n"
				+ "	mail varchar(200), \r\n"
				+ "	phone char(10),\r\n"
				+ "	PRIMARY KEY (time, log, reqId)\r\n"
				+ ")";
		log="CREATE TABLE log (\r\n"
				+ "    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\r\n"
				+ "    logid varchar(200) NOT NULL,\r\n"
				+ "    err varchar(1000) NOT NULL,\r\n"
				+ "    PRIMARY KEY (created_at, logid)\r\n"
				+ ")";
		
		try {
			tableResponse = statement.executeUpdate(table);
			walletResponse = statement.executeUpdate(wallet);
			otpResponse = statement.executeUpdate(otp);
			backupResponse = statement.executeUpdate(backup);
			logResponse = statement.executeUpdate(log);
		} catch(SQLSyntaxErrorException e) {
			error = e.getMessage();
			System.out.println(error); 
			if(error.equals("Table 'db' already exists")) {
				try {
					walletResponse = statement.executeUpdate(wallet);
					otpResponse = statement.executeUpdate(otp);
					backupResponse = statement.executeUpdate(backup);
					logResponse = statement.executeUpdate(log);
				} catch(SQLSyntaxErrorException e1) {
					error = e1.getMessage();
					System.out.println(error); 
					if(error.equals("Table 'master_wallet' already exists")) {
						try {
							otpResponse = statement.executeUpdate(otp);
							backupResponse = statement.executeUpdate(backup);
							logResponse = statement.executeUpdate(log);
						} catch(SQLSyntaxErrorException e2) {
							error = e2.getMessage();
							System.out.println(error);
							if(error.equals("Table 'otp_table' already exists")) {
								try {
									backupResponse = statement.executeUpdate(backup);
									logResponse = statement.executeUpdate(log);
								} catch(SQLSyntaxErrorException e3) {
									error = e3.getMessage();
									System.out.println(error);
									if(error.equals("Table 'db_backup' already exists")) {
										logResponse = statement.executeUpdate(log);
									}
								}
							}
						}
					}
				}
			}
		}
		if(tableResponse==0 & walletResponse==0 & otpResponse==0 & backupResponse==0 & logResponse==0) {
			System.out.println("database initialized successfully...");
		} else if(tableResponse==0 | walletResponse==0 | otpResponse==0 | backupResponse==0 | logResponse==0) {
			System.out.println("partially initialized...");
		} else {
			System.out.println("couldn't initialize...");
		}
	}


	ResultSet fetchData(String loginInput) throws SQLException {
		// TODO Auto-generated method stub
		selectQuery="select * from db where reqId = ? or mail = ?";
		selectStatement=connection.prepareStatement(selectQuery);
		selectStatement.setString(1, loginInput);
		selectStatement.setString(2, loginInput);
		return selectStatement.executeQuery();
	}
	
	
	String getReqId(String loginInput) throws SQLException {
		// TODO Auto-generated method stub
		selectQuery="select reqId,mail from db where mail = ? or reqId=?";
		selectStatement=connection.prepareStatement(selectQuery);
		selectStatement.setString(1, loginInput);
		selectStatement.setString(2, loginInput);
		resultset=selectStatement.executeQuery();
		
		if(resultset.next()) {
			System.out.println("retreived reqId");
			return resultset.getString("reqId");
		} else {
			System.out.println("couldn't get reqId");
			return null;
		}
	}


	void setOtp(int userid, int otp) throws SQLException {
		// TODO Auto-generated method stub
		insertQuery ="insert into otp_table(user_id,otp) values (?,?)";
		insertStatement=connection.prepareStatement(insertQuery);
		insertStatement.setInt(1, userid);
		insertStatement.setInt(2, otp);
		insertStatement.executeUpdate();
		System.out.println("otp generated succesfully");
	}


	String getOtp(int userid) {
		// TODO Auto-generated method stub
		selectQuery = "SELECT otp, created_at FROM otp_table WHERE user_id = ?";
        deleteQuery = "DELETE FROM otp_table WHERE user_id = ?";
        String otp = null;
        
    	try {
			selectStatement = connection.prepareStatement(selectQuery);
			deleteStatement = connection.prepareStatement(deleteQuery);
	        selectStatement.setInt(1, userid);

	        resultset = selectStatement.executeQuery();
            if (resultset.next()) {
                otp = Integer.toString(resultset.getInt("otp"));
                Timestamp createdAt = resultset.getTimestamp("created_at");

                // Calculate the difference between the current time and the OTP creation time
                long currentTimeMillis = System.currentTimeMillis();
                long otpTimeMillis = createdAt.getTime();
                long timeDifferenceMinutes = (currentTimeMillis - otpTimeMillis) / (1000 * 60);

                // Check if the OTP is valid (created within the last 5 minutes)
                if (timeDifferenceMinutes <= 5) {
                    System.out.println("OTP for user_id " + userid + ": " + otp);
                    // Continue with further processing using the OTP as needed
                } else if (!(timeDifferenceMinutes <= 5)) {
                    // If OTP is not valid, delete the entry from the table
                    deleteStatement.setInt(1, userid);
                    deleteStatement.executeUpdate();
                    System.out.println("OTP for user_id " + userid + " has expired and has been deleted.");
                    otp="expired";
                }
            } else if (!resultset.next()) {
                System.out.println("No OTP found for user_id " + userid);
            }
            return otp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
        
    }


	void backup(String log) throws SQLException {
		// TODO Auto-generated method stub
		insertQuery = "INSERT INTO db_backup (log, reqId, user, comp, pass, mail, phone) " +
		                	 "SELECT ?, reqId, user, comp, pass, mail, phone FROM db";
		insertStatement = connection.prepareStatement(insertQuery);
		insertStatement.setString(1, log);
		
		insertStatement.executeUpdate();
		System.out.println("Backup created");
	}


	void log(String log, String err) {
		// TODO Auto-generated method stub
		insertQuery = "INSERT INTO log (logid, err) values (?,?)";
				try {
					insertStatement = connection.prepareStatement(insertQuery);

					insertStatement.setString(1, log);
					insertStatement.setString(2, err);
					
					insertStatement.executeUpdate();
					System.out.println("log generated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Coudn't log");
				}
	}


	boolean duplicateEntry(String phone, String email) throws SQLException {
		// TODO Auto-generated method stub
		selectQuery = "SELECT mail, phone FROM db WHERE mail = ? OR phone = ?";
		selectStatement = connection.prepareStatement(selectQuery);

        selectStatement.setString(1, email);
        selectStatement.setString(2, phone);
        
        resultset = selectStatement.executeQuery();
        
        boolean hasNext = resultset.next();
        if(hasNext) {
        	System.out.println("Duplicate entry warning");
        }
		return hasNext;
	}


	void jdbcDestroy() {
		// TODO Auto-generated method stub
		try {
            statement.close();
            insertStatement.close();
            updateStatement.close();
            selectStatement.close();
            deleteStatement.close();
			resultset.close();
			connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log(e.getClass().getName(), e.getMessage()+Do.getMethodName(e.getStackTrace()));
        }
	}


	void changePassword(String email, String newpass) throws SQLException {
		// TODO Auto-generated method stub
		updateQuery = "UPDATE db\r\n"
				+ "SET pass = ?\r\n"
				+ "WHERE\r\n"
				+ "	mail = ?";
		
		updateStatement = connection.prepareStatement(updateQuery);

		updateStatement.setString(1, newpass);
		updateStatement.setString(2, email);
		
		updateStatement.executeUpdate();
		System.out.println("password updated to : " + newpass);
	}
	
	
	ResultSet fetchTransaction(String reqId) throws SQLException {
		// TODO Auto-generated method stub
		selectQuery="select trnscId,sNo,time,trnsc,bal,reqId FROM master_wallet\r\n"
				+ "	WHERE reqId = ?\r\n"
				+ "	ORDER BY time DESC";
		selectStatement=connection.prepareStatement(selectQuery);
		selectStatement.setString(1, reqId);
		return selectStatement.executeQuery();
	}
	
	
	boolean walletIsVerified(String reqId) throws SQLException {
		selectQuery="select trnsc,bal FROM master_wallet\r\n"
				+ "	WHERE reqId = ?\r\n"
				+ "	ORDER BY time";
		selectStatement=connection.prepareStatement(selectQuery);
		selectStatement.setString(1, reqId);
		resultset=selectStatement.executeQuery();
		
		while(resultset.next()) {
			trnsc=resultset.getFloat("trnsc");
			bal=resultset.getFloat("bal");
			
			tempBal+=trnsc;
			if(tempBal!=bal) {
				returnThis=false;
			}
		}
		return returnThis;
	}
	
	
	ResultSet getLastTransaction(String reqId) throws SQLException {
		// TODO Auto-generated method stub
		selectQuery="SELECT * FROM master_wallet\r\n"
				+ "	WHERE reqiId = ?\r\n"
				+ "	ORDER BY time DESC\r\n"
				+ "	LIMIT 1";
		selectStatement.setString(1, reqId);
		return selectStatement.executeQuery();
	}
	
	
	void addTransact(String trnscId, int sNo, Float amt, Float bal, String reqId) throws SQLException {
		// TODO Auto-generated method stub
		insertQuery="insert into master_wallet(trnscId,sNo,trnsc,bal,reqId) values (?,?,?,?,?)";
		insertStatement=connection.prepareStatement(insertQuery);
		insertStatement.setString(1, trnscId);
		insertStatement.setInt(2, sNo);
		insertStatement.setFloat(3, amt);
		insertStatement.setFloat(4, bal);
		insertStatement.setString(5, reqId);
		insertStatement.executeUpdate();
		System.out.println("added succesfully");
	}


}
