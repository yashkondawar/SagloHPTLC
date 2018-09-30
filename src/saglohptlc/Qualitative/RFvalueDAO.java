
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Qualitative;

/**
 *
 * @author Soha
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import saglohptlc.SagloHPTLC;

/**
 *
 * @author Soha
 */
public class RFvalueDAO {
    public static ArrayList<ModelRF> model=new ArrayList<ModelRF>();

    public static ArrayList<ModelRF> storeRF(ArrayList<Point>a, ArrayList<ArrayList<Double>> rf){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               int count=0,count2=0;
               String caption=a.get(0).caption;
                for(int i=1;i<a.size();i++)
                {
                  if(count!=rf.size())
                  {
                  count2=0;
                  ArrayList rfvalue=rf.get(count);
                  for(int j=0;j<rfvalue.size();j++)
                  {
                      //Point is i++;
                      //Rf value is rfvalue.get(j);
                        String sql="insert into Qualitative(Image_ID,Caption,Point_No,RFValue) values(?,?,?,?)";
                        PreparedStatement pstmt=conn.prepareStatement(sql);
                         count2++;
                        model.add(new ModelRF(String.valueOf(SagloHPTLC.image_id),a.get(i).caption,String.valueOf(count2),String.valueOf((double)rfvalue.get(j))));
                        pstmt.setInt(1, SagloHPTLC.image_id);
                        pstmt.setString(2,a.get(i).caption);
                       /* if(i+1<a.size())
                            pstmt.setInt(3, i++);
                        else 
                            break;*/
                       pstmt.setInt(3,count2);
                       System.out.println(count2);
                        i++;
                        pstmt.setDouble(4,(double)rfvalue.get(j));
                        pstmt.execute();
                  }
                  i++;
                  count++;
                }
                }           
            }
            conn.close();
          
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return model;
    }
 
    public static ArrayList<ModelRF> getTable()
    {
        if(!SagloHPTLC.rf1){
        ArrayList<ModelRF> m1=new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               
               String sql="Select * from Qualitative;";
                Statement stmt=conn.createStatement();
               ResultSet rs=stmt.executeQuery(sql);
               while(rs.next())
               {
                   m1.add(new ModelRF(String.valueOf(rs.getInt("Image_ID")),rs.getString("Caption"),String.valueOf(rs.getInt("Point_No")),String.valueOf(rs.getFloat("RFValue"))));
               }
                              conn.close();
          
            }
            
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return m1;
        }
        else
            return model;
    }
    public static ArrayList<ModelRF> getRecent()
    {
             ArrayList<ModelRF> model1=new ArrayList<ModelRF>();
             System.out.println("EE");
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
               System.out.println("Image id is"+image_id);
               String sql2="select * from Qualitative where Image_ID=?;";
               PreparedStatement pstmt1=conn.prepareStatement(sql2);
               pstmt1.setInt(1,image_id);
               ResultSet rs1=pstmt1.executeQuery();
               while(rs1.next())
               {
                   model1.add(new ModelRF(String.valueOf(rs1.getInt("Image_ID")),rs1.getString("Caption"),String.valueOf(rs1.getInt("Point_No")),String.valueOf(rs1.getFloat("RFValue"))));
               }
                              conn.close();
                   System.out.println("Hey"+model1.get(0).caption);
            }
            
    }catch(Exception e)
    {
        System.out.println(e);
    }
             return model1;
    }
}
