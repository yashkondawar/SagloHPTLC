/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Login;

/**
 *
 * @author Dell
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class LoginModel {
    
    public boolean checkLogin(String Username,String Password){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                return false;
            }
            Statement stmt=conn.createStatement();
           ResultSet rs=stmt.executeQuery("select password from login where username='"+Username+"';");
           if(rs.next()){
            if(Password.equals(rs.getString("password"))){
                return true;
            }
            else 
                return false;
            }
           else{
               return false;
           }
         
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
        
    }
}
