/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Quantitative;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
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
import saglohptlc.Qualitative.ModelRF;
import saglohptlc.Qualitative.RFvalueDAO;
import saglohptlc.Quantitative.ResizableRectangle;
/**
 * FXML Controller class
 *
 * @author Soha
 */
public class DisplayFXMLQController implements Initializable,ControlledScreen{
    ScreensController myController;
    opencv_core.IplImage grab=null;
    BufferedImage bufferedimage=null;
    CanvasFrame frame=null;
    Thread thread=null;
    DatabaseModel model=new DatabaseModel();
    //Addition of Points and Caption
    
    @FXML
    TableView<ModelQuant> table;
    @FXML
    private TableColumn<ModelQuant,String> image_id;
    @FXML
    private TableColumn<ModelQuant,String> caption;
    @FXML
    private TableColumn<ModelQuant,String> intensity;
    @FXML
    private TableColumn<ModelQuant,String> concentration;
    @FXML
    AnchorPane imagecont;
    @FXML
    StackPane stackpane;
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   image_id.setCellValueFactory(new PropertyValueFactory<>("image_id"));
   caption.setCellValueFactory(new PropertyValueFactory<>("caption"));
   intensity.setCellValueFactory(new PropertyValueFactory<>("intensity"));
   concentration.setCellValueFactory(new PropertyValueFactory<>("concentration"));

   System.out.println("Data"+getData().isEmpty());
   /*if(!getData().isEmpty())
            table.getItems().addAll(getData());*/
    } 
    public ObservableList<ModelQuant>getData()
     {
         ArrayList<ModelQuant> a;
         if(!QuantitativeModel.getTable().isEmpty()){
         a= QuantitativeModel.getTable();
         }
         else
         {
         a=new ArrayList();    
         }
         ObservableList<ModelQuant> points=FXCollections.observableArrayList();
          points.addAll(a);
         //System.out.println(points.get(0).getCaption());
         System.out.println(points.isEmpty());
          return points;
     }
    public void onLoadImage(ActionEvent event) {
        myController.setScreen(SagloHPTLC.CaptureScene);
    }
    
    public void onQualitative (ActionEvent event) {
        myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    public void onAboutUs (ActionEvent event) {
        myController.setScreen(SagloHPTLC.AboutScene);
    }    
    public void onReports (ActionEvent event) {
        myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    public void onLogOut (ActionEvent event) {
        
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
    @Override
    public void setScreenParent(ScreensController screenPage) {
            myController=screenPage;

    }
   
    public void retrieveImage1(ActionEvent event)
    {
        table.getItems().addAll(getData());
    }
}