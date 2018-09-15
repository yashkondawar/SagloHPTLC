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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private Image mainImage;
    private boolean isAreaSelected = false;
    private AreaSelection areaSelection = new AreaSelection();
    private Group selectionGroup = new Group();
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
  
    DisplayFXMLController mainApp;
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
  
   ID.setCellValueFactory(new PropertyValueFactory<>("id"));
   Caption.setCellValueFactory(new PropertyValueFactory<>("caption"));
   Point_No.setCellValueFactory(new PropertyValueFactory<>("point_no"));
   RFValue.setCellValueFactory(new PropertyValueFactory<>("rfvalue"));
  
    table.getItems().addAll(getData());
   
    }
    
     public ObservableList<ModelRF>getData()
     {
           ArrayList<ModelRF>rf=RFvalueDAO.getTable();
          ObservableList<ModelRF>rfpoints=FXCollections.observableArrayList();
          rfpoints.addAll(rf);
         System.out.println(rfpoints.get(0).getCaption());
          return rfpoints;
     }
    private void clearSelection(Group group) {
        //deletes everything except for base container layer
        isAreaSelected = false;
        group.getChildren().remove(1,group.getChildren().size());
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
        myController.setScreen(SagloHPTLC.CaptureScene);
    }    
    @FXML
    public void onQuantitative (ActionEvent event) {
        myController.setScreen(SagloHPTLC.QuantitativeScene);
    }
    @FXML
    public void onAboutUs (ActionEvent event) {
        myController.setScreen(SagloHPTLC.AboutScene);
    }    
    @FXML
    public void onReports (ActionEvent event) {
    //    myController.setScreen(SagloHPTLC.QualitativeScene);
    }
    @FXML
    public void onLogOut (ActionEvent event) {
        
    }
    public void onSettings (ActionEvent event) {
        
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
            System.out.println("dw");
            
    }
   
  
    private class AreaSelection {

        private Group group;
        private ResizableRectangle selectionRectangle = null;
        private double rectangleStartX;
        private double rectangleStartY;
        private Paint darkAreaColor = Color.color(0,0,0,0.5);

        private ResizableRectangle selectArea(Group group) {
            this.group = group;

            // group.getChildren().get(0) == mainImageView. We assume image view as base container layer
            if (img != null && mainImage != null) {
                this.group.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
                this.group.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
                this.group.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
            }

            return selectionRectangle;
        }

        EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
            if (event.isSecondaryButtonDown())
                return;

            rectangleStartX = event.getX();
            rectangleStartY = event.getY();

            clearSelection(group);
            selectionRectangle = new ResizableRectangle(rectangleStartX, rectangleStartY, 0, 0, group);
            darkenOutsideRectangle(selectionRectangle);
        };
        EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
            if (event.isSecondaryButtonDown())
                return;
            double offsetX = event.getX() - rectangleStartX;
            double offsetY = event.getY() - rectangleStartY;
            if (offsetX > 0) {
                if (event.getX() > mainImage.getWidth())
                    selectionRectangle.setWidth(mainImage.getWidth() - rectangleStartX);
                else
                    selectionRectangle.setWidth(offsetX);
            } else {
                if (event.getX() < 0)
                    selectionRectangle.setX(0);
                else
                    selectionRectangle.setX(event.getX());
                selectionRectangle.setWidth(rectangleStartX - selectionRectangle.getX());
            }

            if (offsetY > 0) {
                if (event.getY() > mainImage.getHeight())
                    selectionRectangle.setHeight(mainImage.getHeight() - rectangleStartY);
                else
                    selectionRectangle.setHeight(offsetY);
            } else {
                if (event.getY() < 0)
                    selectionRectangle.setY(0);
                else
                    selectionRectangle.setY(event.getY());
                selectionRectangle.setHeight(rectangleStartY - selectionRectangle.getY());
            }
        };
        EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
            if (selectionRectangle != null)
                isAreaSelected = true;
        };
        private void darkenOutsideRectangle(Rectangle rectangle) {
            Rectangle darkAreaTop = new Rectangle(0,0,darkAreaColor);
            Rectangle darkAreaLeft = new Rectangle(0,0,darkAreaColor);
            Rectangle darkAreaRight = new Rectangle(0,0,darkAreaColor);
            Rectangle darkAreaBottom = new Rectangle(0,0,darkAreaColor);

            darkAreaTop.widthProperty().bind(mainImage.widthProperty());
            darkAreaTop.heightProperty().bind(rectangle.yProperty());

            darkAreaLeft.yProperty().bind(rectangle.yProperty());
            darkAreaLeft.widthProperty().bind(rectangle.xProperty());
            darkAreaLeft.heightProperty().bind(rectangle.heightProperty());

            darkAreaRight.xProperty().bind(rectangle.xProperty().add(rectangle.widthProperty()));
            darkAreaRight.yProperty().bind(rectangle.yProperty());
            darkAreaRight.widthProperty().bind(mainImage.widthProperty().subtract(
                    rectangle.xProperty().add(rectangle.widthProperty())));
            darkAreaRight.heightProperty().bind(rectangle.heightProperty());

            darkAreaBottom.yProperty().bind(rectangle.yProperty().add(rectangle.heightProperty()));
            darkAreaBottom.widthProperty().bind(mainImage.widthProperty());
            darkAreaBottom.heightProperty().bind(mainImage.heightProperty().subtract(
                    rectangle.yProperty().add(rectangle.heightProperty())));

            // adding dark area rectangles before the selectionRectangle. So it can't overlap rectangle
            group.getChildren().add(1,darkAreaTop);
            group.getChildren().add(1,darkAreaLeft);
            group.getChildren().add(1,darkAreaBottom);
            group.getChildren().add(1,darkAreaRight);

            // make dark area container layer as well
            darkAreaTop.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaTop.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaTop.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

            darkAreaLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaLeft.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaLeft.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

            darkAreaRight.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaRight.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaRight.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

            darkAreaBottom.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaBottom.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaBottom.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        }
    }

}   
