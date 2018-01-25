package view;

import controller.Prueba;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class VistaPrincipalController {

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

    private Stage escenarioMenuBar;
    private Prueba prueba;

    private Desktop desktop = Desktop.getDesktop();

    private File file = new File("src/file/ayuda.docx");

    public VistaPrincipalController() {

    }

    public void setEscenarioMenuBar(Stage escenarioMenuBar) {
        this.escenarioMenuBar = escenarioMenuBar;
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
    private void about() {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("About");
            alerta.setHeaderText("Developed by:");
            alerta.setContentText("correojesusmc@gmail.com\nmanuj243@gmail.com\nlucas1sanz96@gmail.com\nluismunozcastro1@gmail.com");
            alerta.showAndWait();
        
    }
}
