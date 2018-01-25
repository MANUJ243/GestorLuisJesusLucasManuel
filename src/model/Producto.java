package model;

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

public class Producto {

    private static int id = 1000;
    private final IntegerProperty idFinal;
    private final StringProperty nombre;
    private final StringProperty descripcion;
    private final DoubleProperty precio;
    private final IntegerProperty stock;
    private final ObjectProperty fechaAlta;
    private final ObjectProperty fechaModificacion;
    private String pathImagen;
    private final Button imagen;

    public Producto() {

        this(null,0.0, 0, null);
    }

    public Producto(String nombre, Double precio, Integer stock, String rutaImg) {
        id++;
        idFinal = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty("Descripcion por defecto");
        this.precio = new SimpleDoubleProperty(precio);
        this.stock = new SimpleIntegerProperty(stock);
        this.fechaAlta = new SimpleObjectProperty(LocalDate.now());
        this.fechaModificacion = new SimpleObjectProperty(null);
        this.pathImagen = rutaImg;
        this.imagen = new Button();
        this.imagen.setPrefSize(80, 80);
        this.imagen.setStyle("-fx-pref-height: 80px; -fx-pref-width: 80px; -fx-background-image: url('file:" + pathImagen + "'); -fx-background-size: 80px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");
        this.imagen.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imagen.setStyle("-fx-pref-height: 150px; -fx-pref-width: 150px; -fx-background-image: url('file:" + pathImagen + "'); -fx-background-size: 150px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");
            }            
        });
        this.imagen.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imagen.setStyle("-fx-pref-height: 80px; -fx-pref-width: 80px; -fx-background-image: url('file:" + pathImagen + "'); -fx-background-size: 80px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");
            }            
        });
    }
    
    public int getId() {
        return idFinal.get();
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

    public double getPrecio() {
        return precio.get();
    }

    public int getStock() {
        return stock.get();
    }

    public LocalDate getFechaAlta() {
        return (LocalDate) fechaAlta.get();
    }
    public void setFechaModificacion(LocalDate fechaModificacion){
        this.fechaModificacion.set(fechaModificacion);
    }

    public LocalDate getFechaModificacion() {
        return (LocalDate) fechaModificacion.get();
    }

    public Button getImagen() {
        return imagen;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String path) {
        this.pathImagen = path;
        this.imagen.setStyle("-fx-background-image: url('file:" + pathImagen + "'); -fx-background-size: 80px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");

    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public void setPrecio(Double precio) {
        this.precio.set(precio);
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

}
