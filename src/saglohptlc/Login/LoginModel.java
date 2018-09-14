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
import saglohptlc.SagloHPTLC;
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
           ResultSet rs0=stmt.executeQuery("select ID,Password from Admin where Username='"+Username+"';");
           ResultSet rs1=stmt.executeQuery("select ID,Password from Organisation where Username='"+Username+"';");
           ResultSet rs2=stmt.executeQuery("select ID,Password from User where Username='"+Username+"';");

           if(rs0.next()){
            if(Password.equals(rs0.getString("password"))){
                SagloHPTLC.flag=0;
                SagloHPTLC.session_id=rs0.getInt("ID");
                return true;
            }           
           }
           else if(rs1.next())
           {
              if(Password.equals(rs1.getString("password"))){
                SagloHPTLC.flag=1;
                SagloHPTLC.session_id=rs1.getInt("ID");
                return true;
            }  
           }
           else if(rs2.next())
           {
                if(Password.equals(rs2.getString("password"))){
                SagloHPTLC.flag=2;
                SagloHPTLC.session_id=rs2.getInt("ID");
                return true;
            }
           }
           else
               return false;
                
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
        
    }
}
