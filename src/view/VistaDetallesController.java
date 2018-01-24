package view;

import controller.Prueba;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class VistaDetallesController {
    @FXML
    private ImageView fotoProducto;
    @FXML
    private Label nombre;
    @FXML
    private Label precio;
    @FXML
    private Label stock;
    @FXML
    private Label fechaAlta;
    @FXML
    private Label fechaModificacion;
    @FXML
    private Label descripcion;
    @FXML
    private ImageView codigoBarrasImg;
    @FXML
    private Label codigoBarras;
    @FXML
    private Button botonBorrar;
    
    private Stage escenarioDetalles;
    private Prueba prueba;
    
    public VistaDetallesController(){
    }
    
    public void setEscenarioDetalles(Stage escenarioDetalles) {
        this.escenarioDetalles = escenarioDetalles;
    }
}
