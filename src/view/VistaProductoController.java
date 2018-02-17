package view;

import controller.GestorProductos;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Producto;

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
    private Button botonDetalles;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonNuevo;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonAyuda;

    private GestorProductos prueba;

    private Desktop desktop = Desktop.getDesktop();

    private File file = new File("src/file/ayuda.docx");

    public VistaProductoController() {
    }

    @FXML
    private void initialize() {
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
    }

    public void setPrueba(GestorProductos prueba) {
        this.prueba = prueba;
        tablaProducto.setItems(prueba.getDatosProducto());
    }
    
    public TableView getTable(){
        return this.tablaProducto;
    }

    //Aquí podemos sacar otro tipo de alerta para hacerlo más original y no poner el que puso Jairo en la lista de personas
    @FXML
    private void borrarProducto() {
        int indiceSeleccionado = tablaProducto.getSelectionModel().getSelectedIndex();
        if (indiceSeleccionado >= 0) {
            tablaProducto.getItems().remove(indiceSeleccionado);
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText("Producto no seleccionado");
            alerta.setContentText("Por favor,seleccione un producto dela tabla");
            alerta.showAndWait();
        }
    }

    @FXML
    private void detallesProducto() {
        Producto seleccionado = (Producto) tablaProducto.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            prueba.muestraVistaDetalles(seleccionado);
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Alerta");
            alerta.setHeaderText("Producto no seleccionado!");
            alerta.setContentText("Por favor,seleccione un producto");
            alerta.showAndWait();
        }
    }

    @FXML
    public void crearProducto() {
        Producto temporal = new Producto();
        boolean pulsadoGuardar = prueba.muestraEditaProducto(temporal);
        if (pulsadoGuardar) {
            prueba.getDatosProducto().add(temporal);
            System.out.println("nuevo producto -> " + temporal.getNombre());
            System.out.println("nuevo producto -> " + temporal.getPathImagen());
        }
    }

    @FXML
    public void editarProducto() {

        Producto seleccionado = (Producto) tablaProducto.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            boolean pulsadoGuardar = prueba.muestraEditaProducto(seleccionado);
            if (pulsadoGuardar) {
                tablaProducto.setItems(prueba.getDatosProducto());
                tablaProducto.refresh();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Alerta");
            alerta.setHeaderText("Producto no seleccionado!");
            alerta.setContentText("Por favor,seleccione un producto");
            alerta.showAndWait();
        }
    }

    @FXML
    public void ayuda() {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println("error abriendo archivo");
        }
    }

}
