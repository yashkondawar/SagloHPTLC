/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Reports;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import saglohptlc.ControlledScreen;
import saglohptlc.DatabaseModel;
import saglohptlc.Qualitative.ModelRF;
import saglohptlc.Qualitative.RFvalueDAO;
import saglohptlc.Quantitative.ModelQuant;
import saglohptlc.Quantitative.QuantitativeModel;
import saglohptlc.SagloHPTLC;
import saglohptlc.ScreensController;


public class ReportPrintController implements Initializable,ControlledScreen {

    @FXML
    AnchorPane printthis;
    @FXML
    TableView<ReportsTable> ReportsTable;
    @FXML
    TableColumn<ReportsTable,String> sno;
    @FXML
    TableColumn<ReportsTable,String> caption;
    @FXML
    TableColumn<ReportsTable,String> pointno;
    @FXML
    TableColumn<ReportsTable,String> result;
    ScreensController myController;
    DatabaseModel logentry=new DatabaseModel();
    DatabaseModel model = new DatabaseModel();
    Reports_formController rep = new Reports_formController();
   
    @FXML
    Label orgname,dptname,username,date,product,method,eqno,test,instno,batchno,analysis,plmat,solvent,devmode,asign,rsign;
    
    @FXML
    ImageView img;
    
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
    public void loadTable(ActionEvent event){
        if(SagloHPTLC.quant_qual_flag==1)
        {
            ReportsTable.getItems().clear();
            ReportsTable.getItems().addAll(getQuantData());
            
        }
        else if(SagloHPTLC.quant_qual_flag==2)
        {
            ReportsTable.getItems().clear();
            ReportsTable.getItems().addAll(getQualData());
        }
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
    
    public void PrintAction(ActionEvent event) {  
        print(printthis);
    }

    private void print(Node node){
     /*   PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null)
        {
            // Print the node
           boolean printed = job.printPage(node);
            if (printed)// End the printer job
               job.endJob();
            else// Write Error Message
                System.out.println("Printing failed.");
        }
        else// Write Error Message
            System.out.println("Could not create a printer job.");*/
     
     
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));
 
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //orgname.setText(model.orgret());
        //dptname.setText(rep.deptname);
        //username,date,product,method,eqno,test,instno,batchno,analysis,plmat,solvent,devmode,asign,rsign;
      /* sno.setCellValueFactory(new PropertyValueFactory<>("sno"));
       caption.setCellValueFactory(new PropertyValueFactory<>("caption"));
       pointno.setCellValueFactory(new PropertyValueFactory<>("pointno"));
       result.setCellValueFactory(new PropertyValueFactory<>("result"));*/
    
    }    
    public ObservableList getQualData()
     {
           ArrayList rf=RFvalueDAO.getTable();
          ObservableList rfpoints=FXCollections.observableArrayList();
          rfpoints.addAll(rf);
          return rfpoints;
     }
     public ObservableList getQuantData()
     {
         ArrayList a;
         if(!QuantitativeModel.getTable().isEmpty()){
         a= QuantitativeModel.getTable();
         }
         else
         {
         a=new ArrayList();    
         }
         ObservableList points=FXCollections.observableArrayList();
          points.addAll(a);
         //System.out.println(points.get(0).getCaption());
         System.out.println(points.isEmpty());
          return points;
     }
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}