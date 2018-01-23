package model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "productos")
public class Empaquetador {
    private List productos;
    
    @XmlElement(name = "producto")
    public List getProductos(){
        return productos;
    }
    
    public void setProductos(List productos){
        this.productos = productos;
    }
}
