package view;

import controller.Prueba;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Empaquetador;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class VistaPrincipalController {

    @FXML
    public MenuItem stats;
    @FXML
    private MenuItem itemAbrir;
    @FXML
    private MenuItem itemGuardar;
    @FXML
    private MenuItem itemGuardarComo;
    @FXML
    private MenuItem itemSalir;
    @FXML
    private MenuItem itemManual;
    @FXML
    private MenuItem itemAbout;


    private AnchorPane estadisticas;

    private ObservableList datosProducto = FXCollections.observableArrayList();

    private Stage escenarioMenuBar;
    private Prueba prueba;

    private Desktop desktop = Desktop.getDesktop();

    private File file = new File("src/file/ayuda.docx");

    public VistaPrincipalController() {
        cargaProductos(getRutaArchivoProducto());
    }

    public void setEscenarioMenuBar(Stage escenarioMenuBar) {
        this.escenarioMenuBar = escenarioMenuBar;
    }


    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }

    @FXML
    public void abrir() {
        FileChooser fileChooser = new FileChooser();

        //Filtro para la extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Muestro el diálogo de guardar
        File archivo = fileChooser.showOpenDialog(prueba.getPrimaryStage());

        if (archivo != null) {
            prueba.cargaProductos(archivo);
        }
    }

    @FXML
    private void guardar() {
        File archivo = prueba.getRutaArchivoProducto();
        if (archivo != null) {
            prueba.guardaProductos(archivo);
        } else {
            guardarComo();
        }
    }

    @FXML
    private void guardarComo() {

        FileChooser fileChooser = new FileChooser();

        //Filtro para la extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Muestro el diálogo de guardar
        File archivo = fileChooser.showSaveDialog(prueba.getPrimaryStage());

        if (archivo != null) {
            //Me aseguro de que tiene la extensión correctaAbrir
            if (!archivo.getPath().endsWith(".xml")) {
                archivo = new File(archivo.getPath() + ".xml");
            }
            prueba.guardaProductos(archivo);
        }
    }

    @FXML
    public void salir() {
        System.exit(0);
    }

    @FXML
    public void ayuda() {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println("error abriendo archivo");
        }
    }

    @FXML
    public void stats() {
        //Cargo la vista estadísticas
        FXMLLoader loader = new FXMLLoader();
        URL location = Prueba.class.getResource("../view/VistaEstadisticas.fxml");
        loader.setLocation(location);
        try {
            estadisticas = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Inicializo un nuevo escenario y asigno el principal
        Stage escenarioEstadisticas = new Stage();
        escenarioEstadisticas.setTitle("Estadisticas");
        escenarioEstadisticas.initOwner(escenarioMenuBar);

        //Cargo la escena que contiene ese layout de estadisticas
        Scene escena = new Scene(estadisticas);
        escenarioEstadisticas.setScene(escena);

        //Asigno el controlador
        VistaEstadisticasController controller = loader.getController();
        controller.setEscenarioEstadisticas(escenarioEstadisticas);
        controller.setProductos(datosProducto);

        //Muestro las estadisticas
        escenarioEstadisticas.setMaximized(true);
        escenarioEstadisticas.show();
    }

    @FXML
    private void about() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("About");
        alerta.setHeaderText("Developed by:");
        alerta.setContentText("correojesusmc@gmail.com\nmanuj243@gmail.com\nlucas1sanz96@gmail.com\nluismunozcastro1@gmail.com");
        alerta.showAndWait();

    }

    //Obtengo la ruta del archivo de la preferencias de usuario en Java
    public File getRutaArchivoProducto() {

        Preferences prefs = Preferences.userNodeForPackage(Prueba.class);
        String rutaArchivo = prefs.get("rutaArchivo", null);
        System.out.println(rutaArchivo);
        if (rutaArchivo != null) {
            return new File(rutaArchivo);
        } else {
            return null;
        }
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
            datosProducto.addAll(empaquetador.getProductos());                            //Guardo la ruta del archivo al registro de preferenciass
        } catch (Exception e) {
            //Muestro alerta
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pueden cargar datos de la ruta " + archivo.getPath());
            alerta.setContentText(e.toString());
            alerta.showAndWait();
        }
    }
}
