package view;

import controller.GestorProductos;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Producto;
import util.BarCodeGenerator;
import util.UtilidadDeFechas;

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
    private Text descripcion;
    @FXML
    private ImageView codigoBarrasImg;
    @FXML
    private Label codigoBarras;
    @FXML
    private Button editar;
    
    private Stage escenarioDetalles;
    
    private GestorProductos prueba;
    Producto product;
    private TableView tabla;
    private static final String SIN_MODIFICACIONES="Sin modificaciones";
    
    String rutaJar = GestorProductos.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File fichero = new File(rutaJar);        
        String nombreFinal = rutaJar.replace(fichero.getName(),"");
    
    
    public VistaDetallesController(){
    }
    
    public void setEscenarioDetalles(Stage escenarioDetalles) {
        this.escenarioDetalles = escenarioDetalles;
    }
    
    public void setProducto(Producto producto) {
        this.product = producto;
        fotoProducto.setImage(new Image("file:"+nombreFinal+producto.getPathImagen()));
        id.setText("ID : " + producto.getId()+"");
        nombre.setText("Nombre : " + producto.getNombre());
        precio.setText("Precio : " + producto.getPrecio()+"");
        stock.setText("Stock : " + producto.getStock()+"");
        fechaAlta.setText("Fecha de alta : " + producto.getFechaAlta());
        if(producto.getFechaModificacion()==null){
            fechaModificacion.setText("Fecha de modificación : " + SIN_MODIFICACIONES);
        }else{
            fechaModificacion.setText("Fecha de modificación : " + producto.getFechaModificacion());
        }
        
        
        descripcion.setText(producto.getDescripcion());
        BarCodeGenerator bar = new BarCodeGenerator(producto.getId()+"");
        bar.anadirAImageView(codigoBarrasImg);
    }
    
    public void setPrueba(GestorProductos prueba){
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
