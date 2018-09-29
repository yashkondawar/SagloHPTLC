/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Reports;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
    
    @FXML
    TextField method1,product1,equip,batch,ar,instru,test1,analysis1,plate,dev,solv;

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
    
    public void generatereport(ActionEvent event)
    {
        method = method1.getText();
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
