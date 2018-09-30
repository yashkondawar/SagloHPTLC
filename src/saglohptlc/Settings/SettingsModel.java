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
import saglohptlc.Qualitative.ModelRF;
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
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="select * from Organisation where Username=?;";
               PreparedStatement pstmt=conn.prepareStatement(sql);
               pstmt.setString(1,Username);
               ResultSet rs=pstmt.executeQuery();
               if(rs.next())
                   return false;
               else{
               String sql1="insert into Organisation(Name,Username,Password) values(?,?,?);";
               PreparedStatement pstmt1=conn.prepareStatement(sql1);
               pstmt1.setString(1,Name);
               pstmt1.setString(2,Username);
               pstmt1.setString(3,Password);
              pstmt1.executeUpdate();
                }
               conn.close();
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return true;
    }
    public static boolean addUser(String Name,String Username,String Password,String Org,String org_id){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
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
               String sql1="insert into User(username,password,Name,Org_Name,Roll_No) values(?,?,?,?,?);";
               PreparedStatement pstmt1=conn.prepareStatement(sql1);
               pstmt1.setString(1,Username);
               pstmt1.setString(2,Password);
               pstmt1.setString(3,Name);
               pstmt1.setString(4,Org);
               pstmt1.setString(5,org_id);
               pstmt1.executeUpdate();
                }
               conn.close();
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return true;
    }
    public static boolean delUser(String Name,String Username)
    {
         try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
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
               {
               String sql1="delete from User where username=?;";
               PreparedStatement pstmt1=conn.prepareStatement(sql1);
               pstmt1.setString(1,Username);
               pstmt1.executeUpdate();
               conn.close();
               return true;
               }
               else{
                   conn.close();
                   return false;
                }
               
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return true;
    }
    public static boolean modifyOrg(String orgname,String Username,String password)
    {
      try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="select * from Organisation where Name=?;";
               PreparedStatement pstmt=conn.prepareStatement(sql);
               pstmt.setString(1,orgname);
               ResultSet rs=pstmt.executeQuery();
               if(rs.next())
               {
               String sql1="update Organisation set Username=?,Password=? where Name=? ;";
               PreparedStatement pstmt1=conn.prepareStatement(sql1);
               pstmt1.setString(1,Username);
               pstmt1.setString(2, password);
               pstmt1.setString(3,orgname);
               pstmt1.executeUpdate();
               conn.close();
               return true;
               }
               else{
                   conn.close();
                   return false;
                }
               
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }   
        return true;
    }
    public static boolean modifyUser(String Name,String Username,String Password,String Orgname,String Orgid)
    {
         try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="select * from User where Name=?;";
               PreparedStatement pstmt=conn.prepareStatement(sql);
               pstmt.setString(1,Name);
               ResultSet rs=pstmt.executeQuery();
               if(rs.next())
               {
               String sql1="update User set username=?,password=?,Org_Name=?,Roll_No=? where Name=? ;";
               PreparedStatement pstmt1=conn.prepareStatement(sql1);
               pstmt1.setString(1,Username);
               pstmt1.setString(2, Password);
               pstmt1.setString(3,Orgname);
               pstmt1.setString(4, Orgid);
               pstmt1.setString(5,Name);
               pstmt1.executeUpdate();
               conn.close();
               return true;
               }
               else{
                   conn.close();
                   return false;
                }
               
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }   
        return true;
    }
    public static ArrayList<Organisation> getOrgTable()
    {
        ArrayList<Organisation> orgtable=new ArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               
               String sql="Select * from Organisation;";
               Statement stmt=conn.createStatement();
               ResultSet rs=stmt.executeQuery(sql);
               while(rs.next())
               {
                  orgtable.add(new Organisation(rs.getString("Name"),rs.getString("Username"),rs.getString("Password")));
               }
               System.out.println("Org size is"+orgtable.size());
               conn.close();
          
            }
            
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return orgtable;
    }
    
    public static ArrayList<UserTable> getUserTable()
    {
        ArrayList<UserTable> utable=new ArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               
               String sql="Select * from User;";
               Statement stmt=conn.createStatement();
               ResultSet rs=stmt.executeQuery(sql);
               while(rs.next())
               {
                 utable.add(new UserTable(rs.getString("Name"),rs.getString("username"),rs.getString("password"),rs.getString("Org_Name"),rs.getString("Roll_No")));
               }
               conn.close();
          
            }
            
    }catch(Exception e)
    {
        System.out.println("Exception"+e);
    }
        return utable;
    }
    
    public static ArrayList<LogTable> getLogTable()
    {
        ArrayList<LogTable> ltable=new ArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:tlc.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               
               String sql="Select * from Logs;";
                Statement stmt=conn.createStatement();
               ResultSet rs=stmt.executeQuery(sql);
               while(rs.next())
               {
                 ltable.add(new LogTable(String.valueOf(rs.getInt("User_Id")),rs.getString("Activity"),rs.getString("Time")));
               }
               conn.close();
          
            }
            
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return ltable;
    }
}
