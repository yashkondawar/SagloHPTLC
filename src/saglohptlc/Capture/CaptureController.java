/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Capture;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import saglohptlc.ControlledScreen;
import saglohptlc.ScreensController;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
//import com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import javafx.scene.image.Image;
import saglohptlc.SagloHPTLC;
/**
 * FXML Controller class
 *
 * @author Dell
 */
public class CaptureController implements Initializable,ControlledScreen{

    ScreensController myController;
    opencv_core.IplImage grab=null;
    BufferedImage bufferedimage=null;
    CanvasFrame frame=null;
    Thread thread=null;

    @FXML
    ImageView img;
    
    @FXML
    AnchorPane imagecont;
    public void Browse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
             
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
              
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                       
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                img.setImage(image);
                img.fitWidthProperty().bind(imagecont.widthProperty()); 
                //pane.setCenter(img);
            } catch (IOException ex) {
                Logger.getLogger(CaptureController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public void startCamera(ActionEvent event){
         thread=new Thread()
    {
        @Override
        public void run() 
        {             
            CvCapture cp=opencv_highgui.cvCreateCameraCapture(0);
          

            grab=opencv_highgui.cvQueryFrame(cp);
            frame=new CanvasFrame("Webcam");
            //frame.setDefaultCloseOperation(CanvasFrame.EXIT_ON_CLOSE); 
            while(frame.isVisible() && (grab=opencv_highgui.cvQueryFrame(cp))!=null){
                frame.showImage(grab);    
            }
        }
    };
    thread.start();

    }
    
    public void onQualitative (ActionEvent event) {
        myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    
    public void onQuantitative (ActionEvent event) {
        myController.setScreen(SagloHPTLC.QuantitativeScene);
    }
    
    public void aboutUs (ActionEvent event) {
        myController.setScreen(SagloHPTLC.AboutScene);
    }
    
    public void onReports (ActionEvent event) {
        myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    
    public void logOut (ActionEvent event) {
        
    }
    
    public void onSettings (ActionEvent event) {
        
    }
    
    public static BufferedImage IplImageToBufferedImage(IplImage src) {
    OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
    Java2DFrameConverter paintConverter = new Java2DFrameConverter();
    Frame frame = grabberConverter.convert(src);
    return paintConverter.getBufferedImage(frame,1);
} 
    public void Capture(ActionEvent event){
        bufferedimage = grab.getBufferedImage();
        Image image = SwingFXUtils.toFXImage(bufferedimage, null);
        img.setImage(image);
        frame.dispose();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
    myController=screenPage;
    }

    
}
