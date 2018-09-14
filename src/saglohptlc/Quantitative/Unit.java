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
public class Unit {
    String caption;
    double x1,y1,x2,y2;
    float concentration,intensity;
    public Unit(String caption,double x1,double y1,double x2,double y2,float concentration)
    {
        this.caption=caption;
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.concentration=concentration;
    }
}
