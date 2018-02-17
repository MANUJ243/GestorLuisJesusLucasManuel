package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
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
import view.VistaPrincipalController;
import view.VistaProductoController;

public class GestorProductos extends Application {

    private Stage escenarioPrincipal;
    private BorderPane layoutPrincipal;
    private AnchorPane vistaProducto;
    private AnchorPane editarProducto;
    private BorderPane detallesProducto;
    private VistaProductoController vistaProductoController;
    private VistaDetallesController vistaDetallesController;
    private ObservableList datosProducto = FXCollections.observableArrayList();

    public GestorProductos() {
        //datosProducto.add(new Producto("Pera","descripcion", "10.00", "1", "src/img/pera3.jpg"));
        //datosProducto.add(new Producto("Hamburguesa","descripcion", "8.00", "3","src/img/camburguer.png"));
        //datosProducto.add(new Producto("Coca-cola","descripcion", "8.00", "3", "src/img/coca_cola.jpg"));
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
        URL location = GestorProductos.class.getResource("../view/VistaPrincipal.fxml");
        loader.setLocation(location);
        try {
            layoutPrincipal = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GestorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        VistaPrincipalController vistaPrincipalController = loader.getController();
        vistaPrincipalController.setEscenarioMenuBar(escenarioPrincipal);
        vistaPrincipalController.setPrueba(this);
        Scene escena = new Scene(layoutPrincipal);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.setMaximized(true);
        escenarioPrincipal.show();
         //Intento cargar el último archivo abierto
        File archivo = getRutaArchivoProducto();
        if (archivo != null){
            cargaProductos(archivo);
        }
    }

    private void initLayoutProducto() {
        FXMLLoader loader = new FXMLLoader();
        URL location = GestorProductos.class.getResource("../view/VistaProducto.fxml");
        loader.setLocation(location);
        try {
            vistaProducto = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GestorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        layoutPrincipal.setCenter(vistaProducto);
        vistaProductoController = loader.getController();
        vistaProductoController.setPrueba(this);
    }

    public boolean muestraEditaProducto(Producto producto) {
        FXMLLoader loader = new FXMLLoader();
        URL location = GestorProductos.class.getResource("../view/EditarProducto.fxml");
        loader.setLocation(location);
        try {
            editarProducto = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GestorProductos.class.getName()).log(Level.SEVERE, null, ex);
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
        URL location = GestorProductos.class.getResource("../view/vistaDetalles.fxml");
        loader.setLocation(location);
        try {
            detallesProducto = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GestorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage escenarioDetalles = new Stage();
        escenarioDetalles.setTitle("Detalles producto");
        escenarioDetalles.initModality(Modality.WINDOW_MODAL);
        escenarioDetalles.initOwner(escenarioPrincipal);
        Scene escena = new Scene(detallesProducto);
        escenarioDetalles.setScene(escena);
        vistaDetallesController = loader.getController();
        vistaDetallesController.setEscenarioDetalles(escenarioDetalles);
        vistaDetallesController.setProducto(producto);
        vistaDetallesController.setPrueba(this);
        vistaDetallesController.setTableView(vistaProductoController.getTable());
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

            setRutaArchivoProducto(archivo);                                  //Guardo la ruta del archivo al registro de preferenciass
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
            m.marshal(empaquetador, System.out);

            //Guardo la ruta delk archivo en el registro
            setRutaArchivoProducto(archivo);
        } catch (Exception e) { // catches ANY exception
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se puede guardar en el archivo " + archivo.getPath());
            alerta.setContentText(e.toString());
            alerta.showAndWait();
        }
    }

    //Obtengo la ruta del archivo de la preferencias de usuario en Java
    public File getRutaArchivoProducto() {

        Preferences prefs = Preferences.userNodeForPackage(GestorProductos.class);
        String rutaArchivo = prefs.get("rutaArchivo", null);
        System.out.println(rutaArchivo);
        if (rutaArchivo != null) {
            return new File(rutaArchivo);
        } else {
            return null;
        }
    }

    //Guardo la ruta del archivo en las preferencias de usuario en Java
    public void setRutaArchivoProducto(File archivo){

        Preferences prefs = Preferences.userNodeForPackage(GestorProductos.class);
        if (archivo != null){
            //Añado la ruta a las preferencias
            prefs.put("rutaArchivo", archivo.getPath());
            //Actualizo el título del escenario a partir del archivo
            escenarioPrincipal.setTitle("GestorProductos - "+archivo.getName());
        }
        else{
            //Elimino la ruta de las preferencias
            prefs.remove("rutaArchivo");
            //Actualizo el título del escenario quitando el nombre del archivo
            escenarioPrincipal.setTitle("GestorProductos");
        }

    }
}
