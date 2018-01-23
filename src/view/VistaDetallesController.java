package view;

import controller.Prueba;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
    
    private Prueba prueba;
    
    public VistaDetallesController(){
    }
    
    @FXML
    private void initialize() {
    }
}
