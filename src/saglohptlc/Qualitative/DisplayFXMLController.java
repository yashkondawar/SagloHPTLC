/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Qualitative;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import saglohptlc.DatabaseModel;
import saglohptlc.SagloHPTLC;
import saglohptlc.ScreensController;
import saglohptlc.*;
import saglohptlc.Qualitative.ResizableRectangle;
/**
 * FXML Controller class
 *
 * @author Soha
 */
public class DisplayFXMLController implements Initializable,ControlledScreen{
    ScreensController myController;
    opencv_core.IplImage grab=null;
    BufferedImage bufferedimage=null;
    CanvasFrame frame=null;
    Thread thread=null;
    DatabaseModel model=new DatabaseModel();
    DatabaseModel logentry=new DatabaseModel();
    //Addition of Points and Caption
    @FXML
    TableView<ModelRF> table;
    @FXML
    private TableColumn<ModelRF, String> ID;
    @FXML
    private TableColumn<ModelRF,String> Caption;
    @FXML
    private TableColumn<ModelRF,String> Point_No;
    @FXML
    private TableColumn<ModelRF,String> RFValue;
    @FXML
    ImageView img;
    @FXML
    AnchorPane imagecont;
    @FXML
    StackPane stackpane;
    @FXML
    private Button loadimage;
    @FXML
    private Button qualitative;
    @FXML
    private Button quantitative;
    @FXML
    private Button repo;
    @FXML
    private Button logout;
    @FXML
    private Button about;
    /**
     * Initializes the controller class.
     */
  
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
  
   ID.setCellValueFactory(new PropertyValueFactory<>("id"));
   Caption.setCellValueFactory(new PropertyValueFactory<>("caption"));
   Point_No.setCellValueFactory(new PropertyValueFactory<>("point_no"));
   RFValue.setCellValueFactory(new PropertyValueFactory<>("rfvalue"));
  
    //table.getItems().addAll(getData());
   
    }
    
     public ObservableList<ModelRF>getData()
     {
           ArrayList<ModelRF>rf=RFvalueDAO.getTable();
          ObservableList<ModelRF>rfpoints=FXCollections.observableArrayList();
          rfpoints.addAll(rf);
          System.out.println(rfpoints.get(0).getCaption());
          return rfpoints;
     }
    
  
    @FXML
    public void onDisplay(ActionEvent event)
    {
     ArrayList<Point> a=ResizableRectangle.getArray_of_Points();
     for(int i=0;i<a.size();i++)
     {
         System.out.println(a.get(i).caption+" "+a.get(i).x+" "+a.get(i).y);
     }
    }
    @FXML
    public void onLoadImage(ActionEvent event) {
        logentry.LogEntry("Opened Load Image window");
        myController.setScreen(SagloHPTLC.CaptureScene);
    }  
    @FXML
    public void onQualitative (ActionEvent event) {
        SagloHPTLC.quant_qual_flag=1;
        logentry.LogEntry("Opened Qualitative window");
        myController.setScreen(SagloHPTLC.QualitativeScene);
        
    }
    @FXML
    public void onQuantitative (ActionEvent event) {
        SagloHPTLC.quant_qual_flag=2;
        logentry.LogEntry("Opened Quantitative window");
        myController.setScreen(SagloHPTLC.QuantitativeScene);
    }
    @FXML
    public void aboutUs (ActionEvent event) {
        logentry.LogEntry("Opened AboutUs window");
        myController.setScreen(SagloHPTLC.AboutScene);
    }
    @FXML
    public void onReports (ActionEvent event) {
        logentry.LogEntry("Opened Reports window");
        myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    @FXML
    public void logOut (ActionEvent event) {
        logentry.LogEntry("Logged Out");
        SagloHPTLC.session_id=0;
        SagloHPTLC.image_id=0;
        myController.setScreen(SagloHPTLC.Main);
    }
    @FXML
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
    @Override
    public void setScreenParent(ScreensController screenPage) {
            myController=screenPage;
    }
   
    
    @FXML
    public void retrieveTable(ActionEvent event)
    {
     
            table.getItems().clear();
            table.getItems().addAll(getData());
            //System.out.println("dw");
            
    }
   
  
    
}   
