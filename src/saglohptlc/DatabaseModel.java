/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Soha
 */
public class DatabaseModel {
    public void storeImage(BufferedImage buf){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                ImageIO.write(buf, "jpg", baos );
                
                byte[] imageInByte = baos.toByteArray();
                String sql="insert into Images(User_ID,image) values(?,?);";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                pstmt.setInt(1, SagloHPTLC.session_id);
                pstmt.setBytes(2,imageInByte);
                pstmt.execute();
                baos.close();
                
            }
            conn.close();
          
    }catch(Exception e)
    {
        System.out.println(e);
    }
}
 
public BufferedImage retriveImage (){
    BufferedImage image = null;
     try {
         Class.forName("org.sqlite.JDBC");
         Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else{
                    String sql="Select * from Images where User_ID=?;";
                    PreparedStatement stmt=conn.prepareStatement(sql);
                    stmt.setInt(1, SagloHPTLC.session_id);
                    ResultSet rs=stmt.executeQuery();
                    while(rs.next())
                    {
                        //System.out.println(rs.getInt("ID")+rs.getBytes("image"));
                        InputStream in = new ByteArrayInputStream(rs.getBytes("image"));
                        image = ImageIO.read(in);
                        SagloHPTLC.image_id=rs.getInt("Image_ID");
                    }
            }
            conn.close();
     } catch (Exception ex) {
         System.out.println(ex);
     }
     System.out.println("Retrieving image");
    return image;
} 
    
    public void LogEntry(String activity){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
                java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
                String s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
                String sql="insert into Logs(User_ID,Activity,Time) values(?,?,?);";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                pstmt.setInt(1, SagloHPTLC.session_id);
                pstmt.setString(2,activity);
                pstmt.setString(3, s);
                pstmt.execute();
                conn.close();
            }
          
    }catch(Exception e)
    {
        System.out.println(e);
    }
}
    
}
