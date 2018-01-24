package model;

import java.io.File;
import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

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

        this(null, 0.0, 0, null);
    }

    /* public Producto() {
        id++;
        this.idFinal = new SimpleIntegerProperty(id);
        this.nombre = null;
        this.descripcion = null;
        this.precio = null;
        this.stock = null;
        this.fechaAlta = null;
        this.fechaModificacion = null;
        this.pathImagen = null;
        this.imagen = null;
    }*/
    public Producto(String nombre, Double precio, Integer stock, String img) {
        id++;
        idFinal = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty("descripcion");
        this.precio = new SimpleDoubleProperty(precio);
        this.stock = new SimpleIntegerProperty(stock);
        this.fechaAlta = new SimpleObjectProperty(LocalDate.of(1974, 6, 15));
        this.fechaModificacion = new SimpleObjectProperty(LocalDate.of(1974, 6, 15));
        this.pathImagen = img;
        this.imagen = new Button();
        //Luis: he cambiado el tamaño del botón de las imagenes para que salgan más productos por fila (incluso los podriamos hacer + pequeños..)
        this.imagen.setPrefSize(125, 110);
        // String path  = new File(img).getAbsolutePath();
        // String javaPath = path.replace("\\", "/");
        this.imagen.setStyle("-fx-background-image: url('file:" + pathImagen + "'); -fx-background-size: 150px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");
        this.imagen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("boton");
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

    public ObjectProperty getFechaAlta() {
        return fechaAlta;
    }

    public ObjectProperty getFechaModificacion() {
        return fechaModificacion;
    }

    public Button getImagen() {
        return imagen;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String path) {
        this.pathImagen = path;
        this.imagen.setStyle("-fx-background-image: url('file:" + pathImagen + "'); -fx-background-size: 150px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");

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
