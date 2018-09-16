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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class QualitativeFXMLController implements Initializable,ControlledScreen{
    ScreensController myController;
    opencv_core.IplImage grab=null;
    BufferedImage bufferedimage=null;
    CanvasFrame frame=null;
    Thread thread=null;
    DatabaseModel model=new DatabaseModel();
    DatabaseModel logentry=new DatabaseModel();
    //Addition of Points and Caption
    public TextField text;
    public TableView<ModelRF> table;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
    } 
    private void clearSelection(Group group) {
        //deletes everything except for base container layer
        isAreaSelected = false;
        group.getChildren().remove(1,group.getChildren().size());
    }
    public void onRevert(ActionEvent event)
    {
        ArrayList<Point> a=ResizableRectangle.getArray_of_Points();
        a.remove(a.size()-1);
        ResizableRectangle.setArray_of_Points(a);
    }
    public void onDisplay(ActionEvent event)
    {
     ArrayList<Point> a=ResizableRectangle.getArray_of_Points();
     for(int i=0;i<a.size();i++)
     {
         System.out.println(a.get(i).caption+" "+a.get(i).x+" "+a.get(i).y);
     }
    }
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
    }
    @Override
    public void setScreenParent(ScreensController screenPage) {
            myController=screenPage;
    }
    public void onCrop(ActionEvent event)
    {
        areaSelection.selectArea(selectionGroup);
    }
    public void onCalculateRFValue(ActionEvent event)
    {
      ArrayList <Point>a=ResizableRectangle.getArray_of_Points();
      ArrayList<ArrayList<Double>> rfvalues=new ArrayList<ArrayList<Double>>();
      boolean flag=false;
      String caption=null;
      caption=a.get(0).caption;
      double first=a.get(0).y;
      double nextfirst=first;
      int count=0,firstpos=0;
      System.out.println("Points");
      for(int j=0;j<a.size();j++)
      {
          System.out.println(a.get(j).y);
      }
      for(int i=0;i<a.size();i++)
      {                                                             
      if(!caption.equals(a.get(i).caption) || i==a.size()-1)
      {
          flag=true;
          caption=a.get(i).caption;
          nextfirst=a.get(i).y;
          firstpos=i-count+1;
          System.out.println("Flag true"+firstpos);

      }
      count++;
      if(flag){
           ArrayList rf=new ArrayList();
          double denominator=0;
          if(i==a.size()-1){
                   denominator=a.get(i).y-first;
                    for(int j=firstpos;j<=i;j++)
                    {
                         rf.add((a.get(j).y-first)/(denominator));
                         System.out.println("Rf"+(a.get(j).y-first)/(denominator));
                    }
          }
          else
          {  denominator=a.get(i-1).y-first;
     
      for(int j=firstpos;j<i;j++)
      {
          rf.add((a.get(j).y-first)/(denominator));
          System.out.println("Rf"+(a.get(j).y-first)/(denominator));
      }}
      rfvalues.add(rf);
      first=nextfirst;
      flag=false;
      count=0;
      }
     }
      //Display
      for(ArrayList a2:rfvalues)
      {
          for(int i=0;i<a2.size();i++)
          {
              System.out.print(a2.get(i)+"\t");
          }
          System.out.println();
      }
      //Store in db
    ArrayList<ModelRF>rf=RFvalueDAO.storeRF(a,rfvalues);
    /*ObservableList<ModelRF> rfpoints=FXCollections.observableArrayList();
    for(int i=0;i<rf.size();i++)
    {
        rfpoints.add(rf.get(i));
    }*/
    /* try{
       FXMLLoader fxmlLoader = new FXMLLoader();
fxmlLoader.setLocation(getClass().getResource("DisplayFXML.fxml"));
AnchorPane frame = fxmlLoader.load();
DisplayFXMLController c = (DisplayFXMLController) fxmlLoader.getController();
    c.txt=text;
    text.setText("Heyyaaa");
       }
       catch(Exception e){
           e.printStackTrace();
       }*/
      // table.setItems(rfpoints);
         // text.setText("Heyyaaa");

      SagloHPTLC.rf1=true;
      myController.setScreen(SagloHPTLC.QualitativeDisplayScene);

    }
    public void retrieveImage1(ActionEvent event)
    {
     BufferedImage buf=model.retriveImage();
     if(buf==null)
     {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("No previous images");
     }else{
     mainImage = SwingFXUtils.toFXImage(buf, null);
     img.setImage(mainImage);
     selectionGroup.getChildren().add(img);
     stackpane.getChildren().add(selectionGroup);
    }
    }
    public void onClear(ActionEvent event)
    {
        ArrayList<Point> a=ResizableRectangle.getArray_of_Points();
        a.clear();
        ResizableRectangle.setArray_of_Points(a);
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
