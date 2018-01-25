package view;

import controller.Prueba;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Producto;
import util.BarCodeGenerator;

public class VistaDetallesController {
    @FXML
    private ImageView fotoProducto;
    @FXML
    private Label id;
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
    private Button editar;
    
    private Stage escenarioDetalles;
    
    private Prueba prueba;
    Producto product;
    private TableView tabla;
    
    public VistaDetallesController(){
    }
    
    public void setEscenarioDetalles(Stage escenarioDetalles) {
        this.escenarioDetalles = escenarioDetalles;
    }
    
    public void setProducto(Producto producto) {
        this.product = producto;
        fotoProducto.setImage(new Image("file:"+producto.getPathImagen()));
        id.setText("ID : " + producto.getId()+"");
        nombre.setText("Nombre : " + producto.getNombre());
        precio.setText("Precio : " + producto.getPrecio()+"");
        stock.setText("Stock : " + producto.getStock()+"");
        descripcion.setText(producto.getDescripcion());
        BarCodeGenerator bar = new BarCodeGenerator(producto.getId()+"");
        bar.anadirAImageView(codigoBarrasImg);
    }
    
    public void setPrueba(Prueba prueba){
        this.prueba=prueba;
    }
    
    public void setTableView(TableView tabla){
        this.tabla = tabla;
    }
    
    @FXML
    public void editar(){
        boolean pulsadoGuardar = prueba.muestraEditaProducto(product);
        if (pulsadoGuardar) {
                this.setProducto(product);
                tabla.setItems(prueba.getDatosProducto());
                tabla.refresh();
            }
        
    }
}
