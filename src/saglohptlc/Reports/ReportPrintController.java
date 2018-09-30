/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Reports;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
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
    Reports_formController report=new Reports_formController();

   
    @FXML
    Label orgname,username,datetime,product,method,eqno,test,instno,batchno,analysis,plmat,solvent,devmode,asign,rsign,arno;
    
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
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        String s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
        datetime.setText(s);
        arno.setText(report.arno);
        product.setText(report.product);
        solvent.setText(report.solvent);
        test.setText(report.test);
        eqno.setText(report.equipno);
        instno.setText(report.instruno);
        devmode.setText(report.devmode);
        plmat.setText(report.platemat);
        batchno.setText(report.batchno);
        analysis.setText(report.analysis);
        method.setText(report.method);
        System.out.println("print controller "+method);
        asign.setGraphic(new ImageView(report.analysedimage));
        rsign.setGraphic(new ImageView(report.reviewedimage));
        BufferedImage buf=model.retriveImage();
     if(buf==null)
     {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("No previous images");
     }else{
    Image  mainImage = SwingFXUtils.toFXImage(buf, null);
    img.setImage(mainImage);
     }
      try {
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:yash.db");
            if(conn==null)
            {
                System.out.println("Connection not reached");
            }
            else
            {
               String sql="select Org_Name,username FROM  User where ID=?;";
               PreparedStatement pstmt=conn.prepareStatement(sql);
               pstmt.setInt(1, SagloHPTLC.session_id);
               ResultSet rs=pstmt.executeQuery();
               while(rs.next())
               {
                   orgname.setText(rs.getString("Org_Name"));
                   username.setText(rs.getString("username"));
               }
            }
            conn.close();
          
    }catch(Exception e)
    {
        System.out.println(e);
    }
     

        if(SagloHPTLC.quant_qual_flag==1)
        {
            ReportsTable.getItems().clear();
            ReportsTable.getItems().addAll(getQualData());
            
        }
        else if(SagloHPTLC.quant_qual_flag==2)
        {
            ReportsTable.getItems().clear();
            ReportsTable.getItems().addAll(getQuantData());
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

               sno.setCellValueFactory(new PropertyValueFactory<>("id"));
               caption.setCellValueFactory(new PropertyValueFactory<>("caption"));
               pointno.setCellValueFactory(new PropertyValueFactory<>("pointno"));
               result.setCellValueFactory(new PropertyValueFactory<>("result"));

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
         //System.out.println(points.get(0).getImage_id());
        // System.out.println(points.isEmpty());
          return points;
     }
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}