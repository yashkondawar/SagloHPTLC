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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
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
import saglohptlc.Quantitative.ResizableRectangle;
/**
 * FXML Controller class
 *
 * @author Soha
 */
public class QuantitativeFXMLController implements Initializable,ControlledScreen{
    ScreensController myController;
    opencv_core.IplImage grab=null;
    BufferedImage bufferedimage=null;
    CanvasFrame frame=null;
    Thread thread=null;
    DatabaseModel model=new DatabaseModel();
    DatabaseModel logentry = new DatabaseModel();
    //Addition of Points and Caption
    
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
     public void onAnalyse(ActionEvent event) {
       ArrayList<Unit> res=ResizableRectangle.getArray_of_Unit();
       bufferedimage=SwingFXUtils.fromFXImage(mainImage,null);
       int pixel,red,green,blue;
       for(int i=0;i<res.size();i++)
       {
           int j=0,k=0;
           float sum=0;
           System.out.println("**********Band no*********************"+i);
           for(j=(int)res.get(i).x1;j<(int)res.get(i).x2;j++)
           {
                for(k=(int)res.get(i).y1;k<(int)res.get(i).y2;k++)
                {
                    pixel = bufferedimage.getRGB(k, j);
                    //alpha = (pixel >> 24) & 0xff;
                    red = (pixel >> 16) & 0xff;
                    green = (pixel >> 8) & 0xff;
                    blue = (pixel) & 0xff;
                   // avg=avg+alpha;
                   sum=sum+red+green+blue;
                   System.out.println(red+" "+green+" "+blue);
                }
           }
           System.out.println(" ");
           sum=sum/(j*k);
           res.get(i).intensity=1/sum;
       }
       for(int i=0;i<res.size();i++)
       {
           System.out.println(res.get(i).caption +"\t"+res.get(i).x1+" "+res.get(i).x2+" "+res.get(i).y1+" "+res.get(i).y2+" "+res.get(i).concentration+" "+res.get(i).intensity);
       }
       LinearRegression(res);
     }
      public void LinearRegression(ArrayList<Unit> units) {
        double intercept, slope;
        double r2;
        
        int n = units.size();

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < n-1; i++) {
            sumx  += units.get(i).intensity;
            sumx2 += units.get(i).intensity*units.get(i).intensity;
            sumy  += units.get(i).concentration;
        }
        double xbar = sumx / n-1;
        double ybar = sumy / n-1;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n-1; i++) {
            xxbar += (units.get(i).intensity - xbar) * (units.get(i).intensity- xbar);
            yybar += (units.get(i).concentration - ybar) * (units.get(i).concentration - ybar);
            xybar += (units.get(i).intensity - xbar) * (units.get(i).concentration - ybar);
        }
        slope  = xybar / xxbar;
        
        intercept = ybar - slope * xbar;
        System.out.println(units.get(n-1).intensity);
        double prediction=slope*units.get(n-1).intensity+intercept;
        System.out.println(slope);
        System.out.println(intercept);
        System.out.println(prediction);
        
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
         /* 
      for(int i=0;i<a.size()-2;i++)
      {
          System.out.println(rfvalues[i]);
      }*/
    
    public void retrieveImage1(ActionEvent event)
    {
     BufferedImage buf=model.retriveImage();
     mainImage = SwingFXUtils.toFXImage(buf, null);
     img.setImage(mainImage);
     selectionGroup.getChildren().add(img);
     stackpane.getChildren().add(selectionGroup);
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
