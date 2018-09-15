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

import java.awt.image.BufferedImage;
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
               int count=0;
               String caption=a.get(0).caption;
                for(int i=1;i<a.size();i++)
                {
             
                  ArrayList rfvalue=rf.get(count);
                  for(int j=0;j<rfvalue.size();j++)
                  {
                      //Point is i++;
                      //Rf value is rfvalue.get(j);
                        String sql="insert into Qualitative(Image_ID,Caption,Point_No,RFValue) values(?,?,?,?);";
                        PreparedStatement pstmt=conn.prepareStatement(sql);
                        model.add(new ModelRF(String.valueOf(SagloHPTLC.image_id),a.get(i).caption,String.valueOf(i),String.valueOf((double)rfvalue.get(j))));
                        pstmt.setInt(1, SagloHPTLC.image_id);
                        pstmt.setString(2,a.get(i).caption);
                        if(i+1<a.size())
                            pstmt.setInt(3, i++);
                        else 
                            break;
                        pstmt.setDouble(4,(double)rfvalue.get(j));
                        pstmt.execute();
                  }
                  count++;
                }
                              
            }
            
          
    }catch(Exception e)
    {
        System.out.println(e);
    }
        return model;
    }
 
    public static ArrayList<ModelRF> getTable()
    {
        if(!SagloHPTLC.rf1){
        ArrayList<ModelRF> m1=new ArrayList<ModelRF>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               Statement stmt=conn.createStatement();
               String sql="Select * from Qualitative;";
               ResultSet rs=stmt.executeQuery(sql);
               while(rs.next())
               {
                   m1.add(new ModelRF(String.valueOf(rs.getInt("Image_ID")),rs.getString("Caption"),String.valueOf(rs.getInt("Point_No")),String.valueOf(rs.getFloat("RFValue"))));
               }
                              
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

}
