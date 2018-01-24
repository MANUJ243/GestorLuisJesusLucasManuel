package view;

import controller.Prueba;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
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

    private Prueba prueba;

    private Desktop desktop = Desktop.getDesktop();
    //Luis:He creado un paquete file en el proyecto para que el word o pdf se aloje en el proyecto
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
        botonDetalles.setStyle("-fx-background-image: url('file:src/img_buttons/button_detalles.png');");
        botonEditar.setStyle("-fx-background-image: url('file:src/img_buttons/button_editar.png');");
        botonNuevo.setStyle("-fx-background-image: url('file:src/img_buttons/button_nuevo.png');");
        botonBorrar.setStyle("-fx-background-image: url('file:src/img_buttons/button_borrar.png');");
        botonAyuda.setStyle("-fx-background-image: url('file:src/img_buttons/button_ayuda.png');");

        /* //Borro los detalles de la persona
        mostrarDetallesPersona(null);
        
        //Escucho cambios en la selección de la tabla y muestro los detalles en caso de cambio
        tablaPersonas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> mostrarDetallesPersona((Persona) newValue));*/
    }

    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
        tablaProducto.setItems(prueba.getDatosProducto());
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

    //Luis: He cambiado el método ayuda y lo he puesto para que enlace directamente con SceneBuilder porque
    //el anterior cuando le dabas el 1º click a ayuda no abria el archivo , asi si lo abre , no tengo ni idea porque lo hacía
    @FXML
    public void ayuda() {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println("error abriendo archivo");
        }
    }
    /*public void ayuda() {
        this.botonAyuda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("ayuda");
                try {
                    desktop.open(file);
                } catch (IOException ex) {
                    System.out.println("error abriendo archivo");
                }
            }
        });
    }*/

}
