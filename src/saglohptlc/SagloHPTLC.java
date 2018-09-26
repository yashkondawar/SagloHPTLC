/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc;

import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
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
    public static String QualitativeDisplayScene = "QualitativeDAnalysis";
    public static String QualitativeDisplayFile = "/saglohptlc/Qualitative/DisplayFXML.fxml";
    public static String QuantitativeScene = "QuantitativeAnalysis";
    public static String QuantitativeFile = "/saglohptlc/Quantitative/QuantitativeFXML.fxml";
    public static String DisplayFXMLQFile="/saglohptlc/Quantitative/DisplayFXMLQ.fxml";
    public static String DisplayQScene="DisplayQuant";
    public static String SettingsScene = "Settings";
    public static String SettingsFile = "/saglohptlc/Settings/Settings.fxml";
    public static String ReportScene = "Reports";
    public static String ReportFile = "/saglohptlc/Reports/Reports_form.fxml";
    public static String AboutScene = "AboutUs";
    public static String AboutFile = "/saglohptlc/Aboutus/AboutUs.fxml";
    public BufferedImage bufferedimage=null;
    public Image image=null;
    public static boolean rf1=false;
    public static int session_id,flag,image_id;
    private Pane contentPane;
    
    @Override
    public void start(Stage primaryStage) {
        try{
            ScreensController mainContainer = new ScreensController();
            mainContainer.loadScreen(SagloHPTLC.Main, SagloHPTLC.MainFile);
            mainContainer.loadScreen(SagloHPTLC.CaptureScene, SagloHPTLC.CaptureFile);
            mainContainer.loadScreen(SagloHPTLC.QualitativeScene, SagloHPTLC.QualitativeFile);
            mainContainer.loadScreen(SagloHPTLC.QualitativeDisplayScene, SagloHPTLC.QualitativeDisplayFile);
            mainContainer.loadScreen(SagloHPTLC.QuantitativeScene, SagloHPTLC.QuantitativeFile);
            mainContainer.loadScreen(SagloHPTLC.ReportScene, SagloHPTLC.ReportFile);
            mainContainer.loadScreen(SagloHPTLC.SettingsScene, SagloHPTLC.SettingsFile);
            mainContainer.loadScreen(SagloHPTLC.DisplayQScene, SagloHPTLC.DisplayFXMLQFile);
            mainContainer.loadScreen(SagloHPTLC.QuantitativeScene, SagloHPTLC.QuantitativeFile);
            mainContainer.loadScreen(SagloHPTLC.SettingsScene, SagloHPTLC.SettingsFile);
            mainContainer.loadScreen(SagloHPTLC.AboutScene, SagloHPTLC.AboutFile);
            
            mainContainer.setScreen(SagloHPTLC.Main);
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX(primaryScreenBounds.getMinX());
            primaryStage.setY(primaryScreenBounds.getMinY());
            primaryStage.setWidth(primaryScreenBounds.getWidth());
            primaryStage.setHeight(primaryScreenBounds.getHeight());
            
            Pane root = new Pane();
            root.getChildren().addAll(mainContainer);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            double newWidth  = scene.getWidth();
            double newHeight = scene.getHeight();

            double scaleFactor = 1.75;
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);

            contentPane.setPrefWidth (newWidth  / scaleFactor);
            contentPane.setPrefHeight(newHeight / scaleFactor);  
        }catch (Exception e){
            e.printStackTrace();
        }
    }       
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
