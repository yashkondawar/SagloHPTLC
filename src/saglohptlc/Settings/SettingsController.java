/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Settings;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import saglohptlc.ControlledScreen;
import saglohptlc.SagloHPTLC;
import saglohptlc.ScreensController;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class SettingsController implements Initializable,ControlledScreen {

    /**
     * Initializes the controller class.
     */
    ScreensController myController;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
    public void abc(){
        System.out.println("Heyyyy");
    }
    
}
