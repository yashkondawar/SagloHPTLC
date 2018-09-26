/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Reports;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    AnchorPane printthis;
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
        myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    
    public void logOut (ActionEvent event) {
        logentry.LogEntry("Logged Out");
        SagloHPTLC.session_id=0;
        SagloHPTLC.image_id=0;
        myController.setScreen(SagloHPTLC.Main);
    }
    
    public void onSettings (ActionEvent event) {
        myController.setScreen(SagloHPTLC.SettingsScene);

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
    
    private void PrintAction(ActionEvent event) {
        print(printthis);
    }

    private void print(Node node)

    {

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null)
        {
            // Print the node
           boolean printed = job.printPage(node);
            if (printed)
            {               // End the printer job

               job.endJob();
            }
            else
            {
                // Write Error Message
                System.out.println("Printing failed.");

            }
        }
        else
        {
            // Write Error Message
            System.out.println("Could not create a printer job.");
        }
    }  

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
