/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Capture;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import javafx.scene.image.Image;
import saglohptlc.SagloHPTLC;
import saglohptlc.DatabaseModel;

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
    DatabaseModel model=new DatabaseModel();
    DatabaseModel logentry = new DatabaseModel();
    @FXML
    ImageView img;
    @FXML
    Button Settings;
    @FXML
    AnchorPane imagecont;
    CvCapture cp;
    public void Browse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
             
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
              
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                       
            try {
                
                bufferedimage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedimage, null);
                img.setImage(image);
                img.fitWidthProperty().bind(imagecont.widthProperty()); 
                //pane.setCenter(img);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
    public void startCamera(ActionEvent event){
         thread=new Thread()
    {
        @Override
        public void run() 
        {             
            cp=opencv_highgui.cvCreateCameraCapture(0);
            grab=opencv_highgui.cvQueryFrame(cp);
            frame=new CanvasFrame("Webcam");
            frame.setBounds(300, 150, 500, 500);
            //frame.setDefaultCloseOperation(CanvasFrame.EXIT_ON_CLOSE); 
            while(frame.isVisible() && (grab=opencv_highgui.cvQueryFrame(cp))!=null){
                frame.showImage(grab);    
            }
        }
    };
    thread.start();

    }
    public void Save(ActionEvent event)
    {
        model.storeImage(bufferedimage);
        logentry.LogEntry("Stored Image");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Saved Successfully");
        alert.showAndWait();
    }
    public void onQualitative (ActionEvent event) {
        SagloHPTLC.quant_qual_flag=1;
        myController.setScreen(SagloHPTLC.QualitativeScene);
        logentry.LogEntry("Opened Qualitative window");
        
    }
    
    public void onQuantitative (ActionEvent event) {
         SagloHPTLC.quant_qual_flag=2;
        logentry.LogEntry("Opened Quantitative window");
        myController.setScreen(SagloHPTLC.QuantitativeScene);
    }
    
    public void aboutUs (ActionEvent event) {
        logentry.LogEntry("Opened AboutUs window");
        myController.setScreen(SagloHPTLC.AboutScene);
    }
    
    public void onReports (ActionEvent event) {
        logentry.LogEntry("Opened Reports window");
        myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    
    public void logOut (ActionEvent event) {
        logentry.LogEntry("Logged Out");
        SagloHPTLC.session_id=0;
        SagloHPTLC.image_id=0;
        myController.setScreen(SagloHPTLC.Main);
    }
    
    public void onSettings (ActionEvent event) {
       
        if(SagloHPTLC.flag==0)
            myController.setScreen(SagloHPTLC.SettingsScene);
        else
        {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("This Option can be accessed by Admin only");
        alert.showAndWait();
        }
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
        logentry.LogEntry("Captured an Image");
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
    myController=screenPage;
    }

    
}
