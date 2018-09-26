package saglohptlc.Settings;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import saglohptlc.ControlledScreen;
import saglohptlc.DatabaseModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    Label userid;
    @FXML
    Button resetmorg,modify_org;
    @FXML
    TextField m_name_org,m_username_org,m_password_org,m_pass2_org;
    @FXML
    TextField nameof_org,username_org,password_org,pass2_org;
    @FXML
    TextField nameof_user,username_user,password_user,pass2_user,org_name,org_id;
    private boolean tabSelection=false;
    ScreensController myController;
    DatabaseModel logentry;
    
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
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
     }
   @FXML
   void viewData(Event e) {
    if (checkTab()) {
        System.out.println("View Tab is Now Selected");
    }
   }
   @FXML
   void addData(Event e) {
    if (checkTab()) {
        System.out.println("Add Tab is Now Selected");
    }
   }
   @FXML
   void modifyData(Event e) {
    if (checkTab()) {
        System.out.println("Modify Tab is Now Selected");
    }
   }
    private boolean checkTab() {
    tabSelection = !tabSelection;
    return tabSelection;

}
    public void onReset1(ActionEvent event)
    {
        nameof_org.setText("");
        username_org.setText("");
        password_org.setText("");
        pass2_org.setText("");
    }
    public void onReset2(ActionEvent event)
    {
        nameof_user.setText("");
        username_user.setText("");
        password_user.setText("");
        pass2_user.setText("");
        org_name.setText("");
        org_id.setText("");
    }
     public void onResetM(ActionEvent event)
    {
        m_name_org.setText("");
        m_username_org.setText("");
        m_password_org.setText("");
        m_pass2_org.setText("");
      
    }
     public void onModifyORG(ActionEvent event)
     {
         if(m_pass2_org.getText().equals(m_password_org.getText()))
          if(!SettingsModel.modifyORG(m_name_org.getText(),m_username_org.getText(),m_password_org.getText()))
          {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Username or Name already exists");
            alert.showAndWait();   
          }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Passwords don't match!");
            alert.showAndWait();   
        }
     }
     public void onAdd(ActionEvent event)
    {
        if(pass2_org.getText().equals(password_org.getText()))
          if(!SettingsModel.addOrg(nameof_org.getText(),username_org.getText(),password_org.getText()))
          {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Username or Name already exists");
            alert.showAndWait();   
          }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Passwords don't match!");
            alert.showAndWait();   
        }
    }    

   
    @FXML
    TableView<Organisation> org;
    @FXML
    TableColumn<Organisation,String> orgname;
    @FXML
    TableColumn<Organisation,String> orgusername;
    @FXML
    TableColumn<Organisation,String> orgpass;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orgname.setCellValueFactory(new PropertyValueFactory<>("orgname"));
        orgusername.setCellValueFactory(new PropertyValueFactory<>("orgusername"));
        orgpass.setCellValueFactory(new PropertyValueFactory<>("orgpassword"));
    } 
    
     public void onAddUser(ActionEvent event)
     {
//         if(pass2_user.getText().equals(password_user.getText()))
          if(!SettingsModel.addUser(username_user.getText(),password_user.getText(),org_name.getText(),org_id.getText()))
          {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Username or Name already exists");
            alert.showAndWait();   
          }
          else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Passwords don't match!");
            alert.showAndWait();   
        }
     }
     

}
