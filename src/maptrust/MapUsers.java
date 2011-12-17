/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maptrust;

import com.mysql.jdbc.Statement;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import maptrust.Database;
import maptrust.SystemUtils.StatusFlag;

/**
 *
 * @author lee
 */
public class MapUsers {
    String username;
    String password;
    Statement statement = null;
    ResultSet rs;
    Database db;
    /**
     * @param args the command line arguments
     */
    
    public MapUsers(){
        db = new Database();
        rs = null;
        String sql = "Select * from db_user;";
        try {
            statement = null;
            statement = (Statement) Database.conn.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MapUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   public MapUsers(String username, String password){
       this.username = username;
       this.password = password;
   }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
    
     public boolean addUser(String username, String password){
        Database db = new Database();
        rs = null;
        System.out.println(username);
        String sqlStatement = "INSERT INTO d_user (user_id, user_name, user_password) VALUES (null, '"+username+"', password('"+password+"'))";
//        sqlStatement = db.sqlFilter(sqlStatement);
            sqlStatement=SystemUtils.sqlCommandFilter(sqlStatement); //remove illegal sql characters if any
		db.ExecuteCommand(sqlStatement);
        try {
            rs=statement.executeQuery(sqlStatement);
        } catch (SQLException ex) {
            Logger.getLogger(MapUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
                rs = db.ExecuteCommand(sqlStatement);
		db.closeConnection();
		
		
		if(Database.getStatusFlag()==StatusFlag.SUCCESS)
//                    System.out.println("True");
                     return true;
		else
                        return false;
//			System.out.println("False");
     }
     
     
    public static void main(String[] args) {
        // TODO code application logic here
        boolean flag;
        MapUsers map = new MapUsers();
        flag = map.addUser("Ben", "private");
        
    }
}
