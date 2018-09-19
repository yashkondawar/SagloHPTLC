/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import saglohptlc.Quantitative.ModelQuant;
import saglohptlc.Quantitative.Unit;
import saglohptlc.SagloHPTLC;

/**
 *
 * @author Soha
 */
public class SettingsModel {
     public static boolean addOrg(String Name,String Username,String Password){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="select * from Organisation where Name=? or Username=?;";
               PreparedStatement pstmt=conn.prepareStatement(sql);
               pstmt.setString(1,Name);
               pstmt.setString(2,Username);
               ResultSet rs=pstmt.executeQuery();
               if(rs.next())
                   return false;
               else{
               String sql1="insert into Organisation(Name,Username,Password) values(?,?,?,?)";
               PreparedStatement pstmt1=conn.prepareStatement(sql1);
               pstmt1.setString(1,Name);
               pstmt1.setString(2,Username);
               pstmt1.setString(3,Password);
               ResultSet rs1=pstmt1.executeQuery();
               
                }
               conn.close();
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return true;
    }
    public static boolean addUser(String Username,String Password,String Org,String org_id){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="select * from User where username=?;";
               PreparedStatement pstmt=conn.prepareStatement(sql);
               pstmt.setString(1,Username);
               ResultSet rs=pstmt.executeQuery();
               if(rs.next())
                   return false;
               else{
               String sql1="insert into User(username,password,Name,Roll_No) values(?,?,?,?)";
               PreparedStatement pstmt1=conn.prepareStatement(sql1);
               pstmt1.setString(1,Username);
               pstmt1.setString(2,Password);
               pstmt1.setString(3,Org);
               pstmt1.setString(4,org_id);

                   ResultSet rs1=pstmt1.executeQuery();
               
                }
               conn.close();
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return true;
    }
   
}
