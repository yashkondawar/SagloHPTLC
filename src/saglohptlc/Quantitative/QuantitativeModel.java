/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Quantitative;

/**
 *
 * @author Soha
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import saglohptlc.SagloHPTLC;

/**
 *
 * @author Soha
 */
public class QuantitativeModel{
  static  ArrayList<ModelQuant>model=new ArrayList<ModelQuant>();
    public static void storeUnit(ArrayList<Unit>a){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="insert into Quantitative(Image_ID,Caption,Intensity,Concentration) values(?,?,?,?)";
               for(int i=0;i<a.size();i++)
               {
                 int j1=SagloHPTLC.image_id;
                 model.add(new ModelQuant(String.valueOf(j1),a.get(i).caption,String.valueOf(a.get(i).intensity),String.valueOf(a.get(i).concentration)));
                 PreparedStatement pstmt=conn.prepareStatement(sql);
                 pstmt.setInt(1, SagloHPTLC.image_id);
                 pstmt.setString(2,a.get(i).caption);
                 pstmt.setDouble(3, a.get(i).intensity);
                 pstmt.setDouble(4, a.get(i).concentration);
                 pstmt.execute();
               }
               conn.close();
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
    }
    public static ArrayList<ModelQuant> getTable()
    {
        ArrayList<ModelQuant> quant=new ArrayList<ModelQuant>();
         try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="select * from Quantitative;";
               Statement stmt=conn.createStatement();
               ResultSet rs=stmt.executeQuery(sql);
               while(rs.next())
               {
                   quant.add(new ModelQuant(String.valueOf(rs.getInt("Image_ID")),rs.getString("Caption"),String.valueOf(rs.getFloat("Intensity")),String.valueOf(rs.getFloat("Concentration"))));
               }
               conn.close();
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
         if(model.size()==0)
         return quant;
         else
             return model;
    }
    
     public static ArrayList<ModelQuant> getRecent()
    {
        ArrayList<ModelQuant> model1=new ArrayList<ModelQuant>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="Select * from Images where User_ID=?;";
               PreparedStatement pstmt=conn.prepareStatement(sql);
               pstmt.setInt(1, SagloHPTLC.session_id);
               ResultSet rs=pstmt.executeQuery();
               int image_id=0;
               while(rs.next())
               {
                   image_id=rs.getInt("Image_ID");
               }
               String sql2="select * from Quantitative where Image_ID=?;";
               PreparedStatement pstmt1=conn.prepareStatement(sql2);
               pstmt1.setInt(1,image_id);
               ResultSet rs1=pstmt1.executeQuery();
               while(rs1.next())
               {
                 model1.add(new ModelQuant(String.valueOf(rs1.getInt("Image_ID")),rs1.getString("Caption"),String.valueOf(rs1.getFloat("Intensity")),String.valueOf(rs1.getFloat("Concentration"))));

               }
               conn.close();
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return model1;
    }
}


