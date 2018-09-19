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
   String image_id,caption,intensity,concentration;

    public ModelQuant(String image_id, String caption, String intensity, String concentration) {
        this.image_id = image_id;
        this.caption = caption;
        this.intensity = intensity;
        this.concentration = concentration;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    
    
    
}
