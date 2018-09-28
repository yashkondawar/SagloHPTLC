/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Login;
import java.net.URL;
import saglohptlc.*;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import saglohptlc.*;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class LoginController implements Initializable,ControlledScreen{
    ScreensController myController;
    @FXML
    private TextField username;
    @FXML
    private Button settings;
    @FXML
    private PasswordField password;
    @FXML
    private Label alert;
    @FXML
    private AnchorPane root1;
    public LoginModel model=new LoginModel();
    public DatabaseModel logentry = new DatabaseModel();
    
   
    public void login (ActionEvent event) {
        try{
            System.out.println(username.getText());
            if(username.getText().equals("") || password.getText().equals(""))
            {
                alert.setText("Enter the credentials.");
            }
            else
            {
                
                if(model.checkLogin(username.getText(), password.getText()))
                {
                    
                            myController.setScreen(SagloHPTLC.CaptureScene);
                            username.setText("");
                            password.setText("");
                            logentry.LogEntry("Logged In");

                }
                else
                {
                    alert.setText("Invalid Username or password");
                    username.clear();
                    password.clear();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void close (ActionEvent event) {
        logentry.LogEntry("Closed the System");
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
                myController = screenPage;

    }
    
}
