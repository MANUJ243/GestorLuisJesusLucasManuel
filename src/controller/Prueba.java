package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Empaquetador;
import model.Producto;
import view.EditarProductoController;
import view.VistaDetallesController;
import view.VistaProductoController;

public class Prueba extends Application {

    private Stage escenarioPrincipal;
    private BorderPane layoutPrincipal;
    private AnchorPane vistaProducto;
    private AnchorPane editarProducto;
    private BorderPane detallesProducto;
    private ObservableList datosProducto = FXCollections.observableArrayList();

    public Prueba() {
        datosProducto.add(new Producto("Pera", 10.00, 1, "src/img/pera3.jpg"));
        datosProducto.add(new Producto("Hamburguesa", 8.00, 3, "src/img/camburguer.png"));
        datosProducto.add(new Producto("Coca-cola", 8.00, 3, "src/img/coca_cola.jpg"));
    }

    public ObservableList getDatosProducto() {
   
        return datosProducto;
    }

    @Override
    public void start(Stage primaryStage) {
        this.escenarioPrincipal = primaryStage;
        initVistaPrincipal();
        initLayoutProducto();
    }

    private void initVistaPrincipal() {
        FXMLLoader loader = new FXMLLoader();
        URL location = Prueba.class.getResource("../view/VistaPrincipal.fxml");
        loader.setLocation(location);
        try {
            layoutPrincipal = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene escena = new Scene(layoutPrincipal);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    private void initLayoutProducto() {
        FXMLLoader loader = new FXMLLoader();
        URL location = Prueba.class.getResource("../view/VistaProducto.fxml");
        loader.setLocation(location);
        try {
            vistaProducto = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        layoutPrincipal.setCenter(vistaProducto);
        VistaProductoController vistaProductoController = loader.getController();
        vistaProductoController.setPrueba(this);
    }

    public boolean muestraEditaProducto(Producto producto) {

        FXMLLoader loader = new FXMLLoader();
        URL location = Prueba.class.getResource("../view/EditarProducto.fxml");
        loader.setLocation(location);
        try {
            editarProducto = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        Stage escenarioEdicion = new Stage();
        escenarioEdicion.setTitle("Editar producto");
        escenarioEdicion.initModality(Modality.WINDOW_MODAL);
        escenarioEdicion.initOwner(escenarioPrincipal);
        Scene escena = new Scene(editarProducto);
        escenarioEdicion.setScene(escena);
        EditarProductoController controller = loader.getController();
        controller.setEscenarioEdicion(escenarioEdicion);
        controller.setProducto(producto);
        escenarioEdicion.showAndWait();
        return controller.pulsadoGuardar();
    }
    
    public void muestraVistaDetalles(Producto producto) {

        FXMLLoader loader = new FXMLLoader();
        URL location = Prueba.class.getResource("../view/vistaDetalles.fxml");
        loader.setLocation(location);
        try {
            detallesProducto = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage escenarioDetalles = new Stage();
        escenarioDetalles.setTitle("Detalles producto");
        escenarioDetalles.initModality(Modality.WINDOW_MODAL);
        escenarioDetalles.initOwner(escenarioPrincipal);
        Scene escena = new Scene(detallesProducto);
        escenarioDetalles.setScene(escena);
        VistaDetallesController controller = loader.getController();
        controller.setEscenarioDetalles(escenarioDetalles);
        controller.setProducto(producto);
        escenarioDetalles.showAndWait();
    }

    public Stage getPrimaryStage() {
        return escenarioPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void cargaProductos(File archivo) {
        try {
            //Contexto
            JAXBContext context = JAXBContext.newInstance(Empaquetador.class);
            Unmarshaller um = context.createUnmarshaller();

            //Leo XML del archivo y hago unmarshall
            Empaquetador empaquetador = (Empaquetador) um.unmarshal(archivo);

            //Borro los anteriores
            datosProducto.clear();
            datosProducto.addAll(empaquetador.getProductos());

            //setRutaArchivoPersonas(archivo);                                  //Guardo la ruta del archivo al registro de preferencias
        } catch (Exception e) {
            //Muestro alerta
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pueden cargar datos de la ruta " + archivo.getPath());
            alerta.setContentText(e.toString());
            alerta.showAndWait();
        }
    }

    public void guardaProductos(File archivo) {

        try {
            //Contexto
            JAXBContext context = JAXBContext.newInstance(Empaquetador.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Empaqueto los datos de las personas
            Empaquetador empaquetador = new Empaquetador();
            empaquetador.setProductos(datosProducto);

            //Marshall y guardo XML a archivo
            m.marshal(empaquetador, archivo);

            //Guardo la ruta delk archivo en el registro
            //setRutaArchivoPersonas(archivo);
        } catch (Exception e) { // catches ANY exception
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se puede guardar en el archivo " + archivo.getPath());
            alerta.setContentText(e.toString());
            alerta.showAndWait();
        }
    }
}
