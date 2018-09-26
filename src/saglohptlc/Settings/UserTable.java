/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Settings;

/**
 *
 * @author Soha
 */
public class UserTable {
    String Name,Username,Password,Org_Name,Rollno;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getOrg_Name() {
        return Org_Name;
    }

    public void setOrg_Name(String Org_Name) {
        this.Org_Name = Org_Name;
    }

    public String getRollno() {
        return Rollno;
    }

    public void setRollno(String Rollno) {
        this.Rollno = Rollno;
    }

    public UserTable(String Name, String Username, String Password, String Org_Name, String Rollno) {
        this.Name = Name;
        this.Username = Username;
        this.Password = Password;
        this.Org_Name = Org_Name;
        this.Rollno = Rollno;
    }
}
