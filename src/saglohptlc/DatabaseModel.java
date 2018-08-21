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
                String sql="insert into Images values(1,?);";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                pstmt.setBytes(1,imageInByte);
                pstmt.execute();
                baos.close();
                System.out.println("heyyyy");
            }
          
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
    String sql="Select * from Images;";
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(sql);
    
    while(rs.next())
    {
        //System.out.println(rs.getInt("ID")+rs.getBytes("image"));
        InputStream in = new ByteArrayInputStream(rs.getBytes("image"));
        image = ImageIO.read(in);
    }
     } catch (Exception ex) {
         System.out.println(ex);
     }
     System.out.println("Retrieving image");
    return image;
} 

}
