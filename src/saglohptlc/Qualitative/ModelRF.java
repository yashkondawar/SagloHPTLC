package saglohptlc.Qualitative;
import javafx.beans.property.*;

/*public class ModelRF{
    private final SimpleStringProperty id;
    private final SimpleStringProperty caption;
    private final SimpleStringProperty point_no;
    private final SimpleStringProperty rfvalue;

    /**
     * Default constructor.
     */
  /*  public ModelRF()
    {
        this(" "," "," "," ");
    }
   
    /**
     * Constructor with some initial data.
     * 
     * @param id
     * @param caption
     * @param point_no
     * @param rfvalue
     */
    /*public ModelRF(String id,String caption, String point_no,String rfvalue) {
        this.id = new SimpleStringProperty(id);
        this.caption = new SimpleStringProperty(caption);
        this.point_no = new SimpleStringProperty(point_no);
        this.rfvalue = new SimpleStringProperty(rfvalue);
    }

    public String getId()
    {
        return id.get();
    }
    public void setId(String id)
    {
        this.id.set(id);
    }
    public SimpleStringProperty getIdProperty() {
        return id;
    }

    public String getCaption()
    {
        return caption.get();
    }
     public void setCaption(String caption)
    {
        this.caption.set(caption);
    }
    public StringProperty getCaptionProperty() {
        return caption;
    }
    
    public String getPoint_no()
    {
        return point_no.get();
    }
     public void setPoint_no(String point_no)
    {
        this.point_no.set(point_no);
    }
    public SimpleStringProperty getPoint_noProperty() {
        return point_no;
    }

    public String getRfvalue()
    {
        return rfvalue.get();
    }
     public void setRfvalue(String rfvalue)
    {
        this.rfvalue.set(rfvalue);
    }
    public SimpleStringProperty getRfvalueProperty() {
        return rfvalue;
    }
    
    
    
}*/
public class ModelRF{
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

    public ModelRF(String id, String caption, String pointno, String result) {
        this.id = id;
        this.caption = caption;
        this.pointno = pointno;
        this.result = result;
    }

  
}