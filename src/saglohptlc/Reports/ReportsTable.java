/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Reports;

/**
 *
 * @author Dell
 */
public class ReportsTable {
    String sno;
    String caption,pointno,result;

    public ReportsTable(String sno, String caption, String pointno, String result) {
        this.sno = sno;
        this.caption = caption;
        this.pointno = pointno;
        this.result = result;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPointno() {
        return pointno;
    }

    public void setPointno(String pointno) {
        this.pointno = pointno;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    }
