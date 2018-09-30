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
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
            if(conn==null)
            {
                return false;
            }
           PreparedStatement pstmt=conn.prepareStatement("select ID,Password from Admin where Username=?;");
           pstmt.setString(1, Username);
           ResultSet rs0=pstmt.executeQuery();
           PreparedStatement pstmt1=conn.prepareStatement("select ID,Password from Organisation where Username=?;");
           pstmt1.setString(1, Username);
           ResultSet rs1=pstmt1.executeQuery();
           PreparedStatement pstmt2=conn.prepareStatement("select ID,Password from User where username=?;");
           pstmt2.setString(1, Username);
           ResultSet rs2=pstmt2.executeQuery();
           if(rs0.next()){
             System.out.println("True");
            if(Password.equals(rs0.getString("Password"))){
                SagloHPTLC.flag=0;
                SagloHPTLC.session_id=rs0.getInt("ID");
                conn.close();
                return true;
            }           
           }
           else if(rs1.next())
           {
              if(Password.equals(rs1.getString("Password"))){
                SagloHPTLC.flag=1;
                SagloHPTLC.session_id=rs1.getInt("ID");
                conn.close();
                return true;
            }  
           }
           else if(rs2.next())
           {
                if(Password.equals(rs2.getString("password"))){
                SagloHPTLC.flag=2;
                System.out.println("here");
                SagloHPTLC.session_id=rs2.getInt("ID");
                conn.close();
                return true;
            }

           }
           else{
               conn.close();
               return false;
           }
        }
     
        catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}