
package view;

import controller.Prueba;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class VistaProductoController {
    
    @FXML
    private TableView tablaProducto;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn imagenColumn;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn precioColumn;
    @FXML
    private TableColumn stockColumn;
    @FXML
    private ComboBox comboColumn;
    @FXML
    private Button botonAyuda;
    
    private Prueba prueba;
    
    private Desktop desktop = Desktop.getDesktop();
    private File file = new File("C:\\Users\\Jesus\\Desktop\\DAM2\\antonio\\Introduccion_JSP.pptx");
    
    public VistaProductoController(){
    }
    
    @FXML
    private void initialize(){
        //Inicializo la tabla con las dos primera columnas
        String id = "id";
        String imagen = "imagen";
        String nombre = "nombre";
        String precio = "precio";
        String stock = "stock";
        idColumn.setCellValueFactory(new PropertyValueFactory<>(id));
        imagenColumn.setSortable(false);
        imagenColumn.setCellValueFactory(new PropertyValueFactory<>(imagen));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>(nombre));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>(precio));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>(stock));
        
       /* //Borro los detalles de la persona
        mostrarDetallesPersona(null);
        
        //Escucho cambios en la selección de la tabla y muestro los detalles en caso de cambio
        tablaPersonas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> mostrarDetallesPersona((Persona) newValue));*/
    }
    
    public void setPrueba(Prueba prueba){
        this.prueba=prueba;
        tablaProducto.setItems(prueba.getDatosProducto());
    }
    
    public void ayuda(){
        this.botonAyuda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                try {
                    desktop.open(file);
                } catch (IOException ex) {
                    System.out.println("error abriendo archivo");
                }
            }
        });
    }
    
}
