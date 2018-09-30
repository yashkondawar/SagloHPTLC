/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saglohptlc.Quantitative;


/**
 *
 * @author Soha
 */
public class ModelQuant {
   String id,caption,pointno,result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ModelQuant(String id, String caption, String pointno, String result) {
        this.id = id;
        this.caption = caption;
        this.pointno = pointno;
        this.result = result;
    }
   

       
    
}
