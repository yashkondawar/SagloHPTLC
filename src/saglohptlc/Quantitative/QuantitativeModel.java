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
    public static void storeRF(ArrayList<Unit>a){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="insert into Quantitative(Image_ID,Caption,Intensity) values(?,?,?);";
               for(int i=0;i<a.size();i++)
               {
                 PreparedStatement pstmt=conn.prepareStatement(sql);
                 pstmt.setInt(1, SagloHPTLC.image_id);
                 pstmt.setString(2,a.get(i).caption);
                 pstmt.setDouble(3, a.get(i).intensity);
                 pstmt.execute();
               }
            }
    }catch(Exception e)
    {
        System.out.println(e);
    }
}
}
