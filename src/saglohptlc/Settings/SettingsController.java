/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Settings;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    DatabaseModel logentry = new DatabaseModel();
    ScreensController myController;
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
    
    public void onSettings (ActionEvent event) {
        myController.setScreen(SagloHPTLC.SettingsScene);
    }
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
    public void abc(){
        System.out.println("Heyyyy");
    }
    @FXML
    TextField nameof_user_modify,username_user_modify,password_user_modify,pass2_user_modify,orgname_user_modify,orgid_user_modify;
    @FXML
    TextField org_name_modify,username_org_modify,password_org_modify,pass2_org_modify;
    @FXML
    TextField nameof_user_del,username_user_del,password_user_del,pass2_user_del;
    @FXML
    TextField nameof_org,username_org,password_org,pass2_org;
    @FXML
    TextField nameof_user,username_user,password_user,pass2_user,org_name,org_id;
    private boolean tabSelection=false;
    @FXML
    TableView<Organisation> org;
    @FXML
    TableColumn<Organisation,String> orgname;
    @FXML
    TableColumn<Organisation,String> orgusername;
    @FXML
    TableColumn<Organisation,String> orgpass;
    @FXML
    TableView<UserTable> user_table;
    @FXML
    TableColumn<UserTable,String> uname;
    @FXML
    TableColumn<UserTable,String> uname2;
    @FXML
    TableColumn<UserTable,String> upass;
    @FXML
    TableColumn<UserTable,String> u_orgname;
    @FXML
    TableColumn<UserTable,String> u_roll;
    
    @FXML
    TableView<LogTable> log_table;
    @FXML
    TableColumn<LogTable,String> log_id;
    @FXML
    TableColumn<LogTable,String> log_activity;
    @FXML
    TableColumn<LogTable,String> log_time;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orgname.setCellValueFactory(new PropertyValueFactory<>("name"));
        orgusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        orgpass.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        uname.setCellValueFactory(new PropertyValueFactory<>("name"));
        uname2.setCellValueFactory(new PropertyValueFactory<>("username"));
        upass.setCellValueFactory(new PropertyValueFactory<>("password"));
        u_orgname.setCellValueFactory(new PropertyValueFactory<>("org_Name"));
        u_roll.setCellValueFactory(new PropertyValueFactory<>("rollno"));
        
        log_id.setCellValueFactory(new PropertyValueFactory<>("uid"));
        log_activity.setCellValueFactory(new PropertyValueFactory<>("activity"));
        log_time.setCellValueFactory(new PropertyValueFactory<>("time"));
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
    public void refreshOrg(ActionEvent event)
    {
      org.getItems().clear();
      org.getItems().addAll(getOrgData());   
    }
    public void refreshUser(ActionEvent event)
    {
        user_table.getItems().clear();
        user_table.getItems().addAll(getuserData());
    }
    public void refreshLog(ActionEvent event)
    {
        log_table.getItems().clear();
        log_table.getItems().addAll(getlogData());
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
    public void resetOrg(ActionEvent event)
    {
        org_name_modify.setText("");
        username_org_modify.setText("");
        password_org_modify.setText("");
        pass2_org_modify.setText("");
    }
    public void resetUserMod(ActionEvent event)
    {
        nameof_user_modify.setText("");
        username_user_modify.setText("");
        password_user_modify.setText("");
        pass2_user_modify.setText("");
        orgname_user_modify.setText("");
        orgid_user_modify.setText("");
    }
    public void modifyUser(ActionEvent event)
    {
        if( (orgname_user_modify.getText()=="")||(orgid_user_modify.getText()=="")||(nameof_user_modify.getText()=="") || (username_user_modify.getText()=="") || (password_user_modify.getText()=="") || (pass2_user_modify.getText()=="")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter all the fields");
            alert.showAndWait(); 
      }
      else
      {
          if(pass2_user_modify.getText().equals(password_user_modify.getText()))
          {
              if(!SettingsModel.modifyUser(nameof_user_modify.getText(),username_user_modify.getText(),password_user_modify.getText(),orgname_user_modify.getText(),orgid_user_modify.getText()))
              {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Username or Name doesnot exist");
                alert.showAndWait();   
              }
              else
             {
                nameof_user_modify.setText("");
                username_user_modify.setText("");
                password_user_modify.setText("");
                pass2_user_modify.setText("");
                orgname_user_modify.setText("");
                orgid_user_modify.setText("");   
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("User Modified");
                alert.showAndWait();
             } 
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
    public void modifyOrg(ActionEvent event)
    {
      if( (org_name_modify.getText()=="") || (username_org_modify.getText()=="") || (password_org_modify.getText()=="") || (pass2_org_modify.getText()=="")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter all the fields");
            alert.showAndWait(); 
      }
      else
      {
          if(pass2_org_modify.getText().equals(password_org_modify.getText()))
          {
              if(!SettingsModel.modifyOrg(org_name_modify.getText(),username_org_modify.getText(),password_org_modify.getText()))
              {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Username or Name doesnot exist");
                alert.showAndWait();   
              }
              else
             {
                 org_name_modify.setText("");
                username_org_modify.setText("");
                password_org_modify.setText("");
                pass2_org_modify.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Organistaion Modified");
                alert.showAndWait();
             } 
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
    public void delUser(ActionEvent event)
    {
        if( (nameof_user_del.getText()=="") || (username_user_del.getText()=="") || (password_user_del.getText()=="") || (pass2_user_del.getText()=="")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter all the fields");
            alert.showAndWait(); 
        }
        else
        {
          if(pass2_user_del.getText().equals(password_user_del.getText()))
          {
              if(!SettingsModel.delUser(nameof_user_del.getText(),username_user_del.getText()))
              {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Username or Name doesnot exist");
                alert.showAndWait();   
              }
              else
             {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("User deleted");
                alert.showAndWait();
             } 
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
     public void onAdd(ActionEvent event)
    {
        if( (nameof_org.getText()=="") || (username_org.getText()=="") || (password_org.getText()=="") || (pass2_org.getText()=="")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter all the fields");
            alert.showAndWait(); 
        }
        else{
            if(pass2_org.getText().equals(password_org.getText()))
            {
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
                alert.setContentText("Organisation added");
                alert.showAndWait();
             }
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
         public void onAddUser(ActionEvent event)
         {
          if( (username_user.getText()=="")||(nameof_user.getText()=="") || (password_user.getText()=="") || (org_name.getText()=="") ||(org_id.getText()=="")|| (pass2_user.getText()=="")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter all the fields");
            alert.showAndWait(); 
        }
          else{  
             if(pass2_user.getText().equals(password_user.getText()))
             {
              if(!SettingsModel.addUser(nameof_user.getText(),username_user.getText(),password_user.getText(),org_name.getText(),org_id.getText()))
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
                alert.setContentText("User added");
                alert.showAndWait();      
              }
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
     public ObservableList<Organisation>getOrgData()
     {
        ArrayList<Organisation>org2=SettingsModel.getOrgTable();
         ObservableList<Organisation>org1=FXCollections.observableArrayList();
         org1.addAll(org2);
         return org1;
     }
     @FXML
     void org_Table(Event e)
     {
         if(checkTab()){
         org.getItems().clear();
         org.getItems().addAll(getOrgData());
         }
     }
     public ObservableList<UserTable>getuserData()
     {
        ArrayList<UserTable>u2=SettingsModel.getUserTable();
        ObservableList<UserTable>u1=FXCollections.observableArrayList();
        u1.addAll(u2);
        return u1;
     }
     
      @FXML
     void user_Table(Event e)
     {
         user_table.getItems().clear();
         user_table.getItems().addAll(getuserData());   
     }
     public ObservableList<LogTable>getlogData()
     {
         ArrayList<LogTable>l2=SettingsModel.getLogTable();
         ObservableList<LogTable>l1=FXCollections.observableArrayList();
         l1.addAll(l2);
         return l1;
     }
      @FXML
     void log_Table(Event e)
     {
         log_table.getItems().clear();
         log_table.getItems().addAll(getlogData());
         
     }
}
