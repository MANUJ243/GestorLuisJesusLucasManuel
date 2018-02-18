package model;

import controller.GestorProductos;
import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javax.swing.Icon;
import javax.xml.bind.annotation.XmlElement;
import util.UtilidadDeFechas;

public class Producto {

    private static int id = 1000;
    private final StringProperty idFinal;
    private final StringProperty nombre;
    private final StringProperty descripcion;
    private final StringProperty precio;
    private final StringProperty stock;
    private final StringProperty fechaAlta;
    private final StringProperty fechaModificacion;
    private String pathImagen;
    private final Button imagen;
    
    
    String rutaJar = GestorProductos.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File fichero = new File(rutaJar);        
        String nombreFinal = rutaJar.replace(fichero.getName(),"");

    public Producto() {

        this(null,null,null, null, null);
    }

    public Producto(String nombre, String descripcion, String precio, String stock, String pathImagen) {
        this.aumentarID();
        idFinal = new SimpleStringProperty(String.valueOf(id));
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleStringProperty(precio);
        this.stock = new SimpleStringProperty(stock);
        //this.fechaAlta = new SimpleObjectProperty(LocalDate.now());
        this.fechaAlta = new SimpleStringProperty(UtilidadDeFechas.formato(LocalDate.now()));
        this.fechaModificacion = new SimpleStringProperty(null);
        this.pathImagen = pathImagen;
        this.imagen = new Button();
        this.imagen.setPrefSize(80, 80);
        propiedadesBotones();        
    }
    
    public String getId() {
        return idFinal.get();
    }
    @XmlElement
    public void setId(String id) {
        this.idFinal.set(id);
    }

    public boolean isNullProduct() {
        if (this.nombre == null) {
            return true;
        }
        return false;
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public String getPrecio() {
        return precio.get();
    }

    public String getStock() {
        return stock.get();
    }

    public String getFechaAlta() {
        return fechaAlta.get();
    }
    @XmlElement
    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta.set(fechaAlta);
    }
    @XmlElement
    public void setFechaModificacion(String fechaModificacion){
        this.fechaModificacion.set(fechaModificacion);
    }

    public String getFechaModificacion() {
        return fechaModificacion.get();
    }

    public Button getImagen() {
        return imagen;
    }

    public String getPathImagen() {
        return pathImagen;
    }
    @XmlElement
    public void setPathImagen(String path) {
        this.pathImagen = path;
        
        this.imagen.setStyle("-fx-background-image: url('file:" + nombreFinal+pathImagen + "'); -fx-background-size: 80px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");

    }
    @XmlElement
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    @XmlElement
    public void setPrecio(String precio) {
        this.precio.set(String.valueOf(precio));
    }
    @XmlElement
    public void setStock(String stock) {
        this.stock.set(stock);
    }
    @XmlElement
    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    private void propiedadesBotones() {
        this.imagen.setStyle("-fx-pref-height: 80px; -fx-pref-width: 80px; -fx-background-image: url('file:" + nombreFinal+pathImagen + "'); -fx-background-size: 80px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");
        this.imagen.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imagen.setStyle("-fx-pref-height: 150px; -fx-pref-width: 150px; -fx-background-image: url('file:" + nombreFinal+pathImagen + "'); -fx-background-size: 150px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");
            }            
        });
        this.imagen.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imagen.setStyle("-fx-pref-height: 80px; -fx-pref-width: 80px; -fx-background-image: url('file:" + nombreFinal+pathImagen + "'); -fx-background-size: 80px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");
            }            
        });
    }

    public void aumentarID() {
        id++;
    }

    public void restarID() {
        id--;
    }

}
