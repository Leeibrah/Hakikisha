/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maptrust;

/**
 *
 * @author lee
 */
//import com.sun.java.swing.SwingUtilities;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.StringTokenizer;
import maptrust.SystemUtils.StatusFlag;


public class Database {

	public static Connection conn=null;
        private static ResultSet rs = null;
        private Statement statement = null;
	private static String host="localhost:3306";
	private static String dbName="map_kibera";
	private static String mysqlUserName="root";
	private static String mysqlPassword="sqlyella";
        private static StatusFlag flag = StatusFlag.RESET;

	
	public static Connection getConnection(){
	;	if(conn==null)
			conn=createConnection();
		return conn;
	}
	
	public static Connection createConnection(){		
		String url="jdbc:mysql://" + Database.host + "/" + Database.dbName;		
		try{
			Class.forName("com.mysql.jdbc.Driver");						
			conn=DriverManager.getConnection(url, mysqlUserName, mysqlPassword);

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return conn;
	}
	
	public static void initialiseDatabase(String host,String dbName,String username,String password){
		Database.host=host;
		Database.dbName=dbName;
		Database.mysqlUserName=username;
		Database.mysqlPassword=password;
                
	}
	
	public static String sqlFilter(String sqlCommand){
		return sqlCommand;
	}
	

        public  ResultSet ExecuteCommand(String sqlCommand){
		
		try 
		{
			Database.flag=StatusFlag.RESET;			
			sqlCommand=sqlCommand.trim();
			//evaluate the type of command being parsed 
			String commandType=new StringTokenizer(sqlCommand).nextToken().toUpperCase();
//			
//			Class.forName("com.mysql.jdbc.Driver");
//			String str="jdbc:mysql://localhost:3306/map_kibera";
//			conn=DriverManager.getConnection(str,"root","sqlyella");
			statement=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			//determine if the sequel command is an update command that alters data or a one that gets/reads data
			if(commandType.equals("INSERT") || commandType.equals("UPDATE") || commandType.equals("DELETE"))
			{
				//The sequel command is an update statement,i.e,makes changes to the database
				statement.executeUpdate(sqlCommand);
				flag=StatusFlag.SUCCESS; //success
			}
			else if(commandType.equals("SELECT"))
			{
				rs=statement.executeQuery(sqlCommand);
				flag=StatusFlag.SUCCESS; //success
				return rs;
			}
			else{
				System.err.println("Allowed sql commands are INSERT,DELETE,UPDATE and SELECT only");
				flag=StatusFlag.FAILED; //failure
				throw new Exception("Invalid sql command");
			}
			return rs;
		} 
		catch (Exception ex) {
			flag=StatusFlag.FAILED;
//			WriteToLog(ex);
		}
		return rs;	
		
	}
        
        public static StatusFlag getStatusFlag(){
		if(flag==StatusFlag.FAILED)
		{
			flag=StatusFlag.RESET;
			return StatusFlag.FAILED;
		}
		else if(flag==StatusFlag.SUCCESS)
		{
			flag=StatusFlag.RESET;//reset
			return StatusFlag.SUCCESS;
		}
		else			
			return flag=StatusFlag.RESET;
	}
        public void closeConnection(){
            if(rs != null){
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            }
        }
}
