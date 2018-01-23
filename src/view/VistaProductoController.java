
package view;

import controller.Prueba;
import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
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
    
    private Prueba prueba;
    
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
    
}
