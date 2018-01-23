package controller;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Producto;
import view.VistaProductoController;

public class Prueba extends Application {

    private Stage escenarioPrincipal;
    private BorderPane layoutPrincipal;
    private AnchorPane vistaProducto;
    private ObservableList datosProducto = FXCollections.observableArrayList();
    
    public Prueba(){
        datosProducto.add(new Producto("Pera",10.00,1, "C:/Users/Jesus/Desktop/prJairo/JavaFXApplication1/src/img/camburguer.png"));
        datosProducto.add(new Producto("Zanahoria",8.00,3, "C:/Users/Jesus/Desktop/prJairo/JavaFXApplication1/src/img/camburguer.png"));
        
    }
    
    public ObservableList getDatosProducto(){
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
        Scene escena=new Scene(layoutPrincipal);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    private void initLayoutProducto() {
        FXMLLoader loader = new FXMLLoader();
        URL location = Prueba.class.getResource("../view/VistaProducto.fxml");
        loader.setLocation(location);
        try {
            vistaProducto=loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        layoutPrincipal.setCenter(vistaProducto);
        VistaProductoController vistaProductoController = loader.getController();
        vistaProductoController.setPrueba(this);
    }
    
      public Stage getPrimaryStage() {
        return escenarioPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
