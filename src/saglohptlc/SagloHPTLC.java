/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc;

import java.awt.image.BufferedImage;
import saglohptlc.Login.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 *
 * @author Dell
 */
public class SagloHPTLC extends Application {
    
    public static String Main = "main";
    public static String MainFile = "/saglohptlc/Login/Login.fxml";
    public static String CaptureScene = "Capture";
    public static String CaptureFile = "/saglohptlc/Capture/Capture.fxml";
    public static String QualitativeScene = "QualitativeAnalysis";
    public static String QualitativeFile = "/saglohptlc/Qualitative/QualitativeFXML.fxml";
    public static String QuantitativeScene = "QuantitativeAnalysis";
    public static String QuantitativeFile = "/saglohptlc/Quantitative/QuantitativeFXML.fxml";
    public static String ReportScene = "Reports";
    public static String ReportFile = "/saglohptlc/Reports/Reports.fxml";
    public static String AboutScene = "AboutUs";
    public static String AboutFile = "/saglohptlc/Aboutus/About.fxml";
    public BufferedImage bufferedimage=null;
    public Image image=null;
   public static int session_id,flag,image_id;
    @Override
    public void start(Stage primaryStage) {
        try{
            /*Parent root = FXMLLoader.load(getClass().getResource("/saglohptlc/Login/Login.fxml"));
            Scene scene1 = new Scene(root, 514, 314);
            
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            //primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene1);
            primaryStage.show();*/
             ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(SagloHPTLC.Main, SagloHPTLC.MainFile);
        mainContainer.loadScreen(SagloHPTLC.CaptureScene, SagloHPTLC.CaptureFile);
        mainContainer.loadScreen(SagloHPTLC.QualitativeScene, SagloHPTLC.QualitativeFile);
       mainContainer.loadScreen(SagloHPTLC.QuantitativeScene, SagloHPTLC.QuantitativeFile);
      /*  mainContainer.loadScreen(SagloHPTLC.ReportScene, SagloHPTLC.ReportFile);
        mainContainer.loadScreen(SagloHPTLC.AboutScene, SagloHPTLC.AboutFile);*/
        
        mainContainer.setScreen(SagloHPTLC.Main);
        
        Pane root = new Pane();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        letterbox(scene, root);

        //primaryStage.setFullScreen(true);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void letterbox(final Scene scene, final Pane contentPane) {
    final double initWidth  = scene.getWidth();
    final double initHeight = scene.getHeight();
    final double ratio      = initWidth / initHeight;

    SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
    scene.widthProperty().addListener(sizeListener);
    scene.heightProperty().addListener(sizeListener);
  }

        
        
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
