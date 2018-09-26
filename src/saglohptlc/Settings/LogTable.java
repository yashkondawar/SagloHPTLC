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
public class LogTable {
    String Uid,Activity,Time;

    public LogTable(String Uid, String Activity, String Time) {
        this.Uid = Uid;
        this.Activity = Activity;
        this.Time = Time;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String Activity) {
        this.Activity = Activity;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

}
