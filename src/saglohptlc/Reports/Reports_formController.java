/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Reports;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import saglohptlc.ControlledScreen;
import saglohptlc.DatabaseModel;
import saglohptlc.SagloHPTLC;
import saglohptlc.ScreensController;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class Reports_formController implements Initializable,ControlledScreen {

    /**
     * Initializes the controller class.
     */
    ScreensController myController;
    DatabaseModel logentry=new DatabaseModel();
    public String method,product,equipno,batchno,arno,instruno,test,analysis,platemat,devmode,solvent;
    public Image analysedimage,reviewedimage;
    @FXML
    TextField method1,product1,equip,batch,ar,instru,test1,analysis1,plate,dev,solv;
    
    @FXML
    Label lb1,lb2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    
    public void onLoadImage(ActionEvent event) {
        logentry.LogEntry("Opened Load Image window");
        myController.setScreen(SagloHPTLC.CaptureScene);
    }    
    public void onQualitative (ActionEvent event) {
        
        logentry.LogEntry("Opened Qualitative window");
        myController.setScreen(SagloHPTLC.QualitativeScene);
        
    }
    
    public void onQuantitative (ActionEvent event) {
        logentry.LogEntry("Opened Quantitative window");
        myController.setScreen(SagloHPTLC.QuantitativeScene);
    }
    
    public void aboutUs (ActionEvent event) {
        logentry.LogEntry("Opened AboutUs window");
        myController.setScreen(SagloHPTLC.AboutScene);
    }
    
    public void onReports (ActionEvent event) {
        logentry.LogEntry("Opened Reports window");
        myController.setScreen(SagloHPTLC.ReportScene);
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("This Option can be accessed by Admin only");
        alert.showAndWait();
        }
    }
    
    public void Analysedsign(ActionEvent event) {
        System.out.println("You clicked me!");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            analysedimage = SwingFXUtils.toFXImage(bufferedImage, null);

            lb1.setText("Uploaded");
        } catch (Exception ex) {
            System.out.println("error " + ex);
        }
    }
    
    public void Reviewedsign(ActionEvent event) {
        System.out.println("You clicked me!");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            reviewedimage = SwingFXUtils.toFXImage(bufferedImage, null);

            lb2.setText("Uploaded");
        } catch (Exception ex) {
            System.out.println("error " + ex);
        }
    }

    public void generatereport(ActionEvent event)
    {
        method = method1.getText();
        System.out.println("form controller "+method);
        product = product1.getText();
        equipno = equip.getText();
        batchno = batch.getText();
        arno = ar.getText();
        instruno = instru.getText();
        test = test1.getText();
        analysis = analysis1.getText();
        devmode = dev.getText();
        platemat = plate.getText();
        solvent = solv.getText();
        myController.setScreen(SagloHPTLC.ReportPrintScene);
           }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
