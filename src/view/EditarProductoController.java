package view;

import controller.GestorProductos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Producto;
import util.UtilidadDeFechas;

public class EditarProductoController {

    @FXML
    private TextField idTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField precioTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private TextArea descripcionTextArea;
    @FXML
    private Button imagen;

    private Stage escenarioEdicion;
    private Producto producto;
    private boolean pulsadoGuardar = false;
    private File file;
    private String img;
    
    
    String rutaJar = GestorProductos.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File fichero = new File(rutaJar);        
        String nombreFinal = rutaJar.replace(fichero.getName(),"");
        String rutaImg = nombreFinal+"img/";
        String rutaFormateo = rutaImg.replace("/", "\\");
        String rutaFinal = rutaFormateo.replace("\\C", "C");
    

    public void setEscenarioEdicion(Stage escenarioEdicion) {
        this.escenarioEdicion = escenarioEdicion;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        idTextField.setText(String.valueOf(producto.getId()));        
        
        if (producto.getNombre() != null) {        
        nombreTextField.setText(producto.getNombre());
        precioTextField.setText(String.valueOf(producto.getPrecio()));
        stockTextField.setText(String.valueOf(producto.getStock()));
        descripcionTextArea.setText(String.valueOf(producto.getDescripcion()));
        img = producto.getPathImagen();
        
        imagen.setStyle("-fx-background-image: url('file:" + nombreFinal+img + "'); -fx-background-size: 150px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");

        }
        nombreTextField.requestFocus();
    }

    public boolean pulsadoGuardar() {
        return pulsadoGuardar;
    }

    @FXML
    public void guardar() {
        if (datosValidos()) {
            
            producto.setNombre(nombreTextField.getText());
            //producto.setPrecio(Double.parseDouble(precioTextField.getText()));
            producto.setPrecio(precioTextField.getText());
            //producto.setStock(Integer.parseInt(stockTextField.getText()));
            producto.setStock(stockTextField.getText());
            producto.setPathImagen(img);
            producto.setDescripcion(descripcionTextArea.getText());
            producto.setFechaModificacion(UtilidadDeFechas.formato(LocalDate.now()));   
            pulsadoGuardar = true;
            escenarioEdicion.close();
        }

    }

    @FXML
    public boolean seleccionaImagen() {        
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Open Resource File");
        file = fileChooser.showOpenDialog(escenarioEdicion);
        if (file != null) {
            copyImageToFilePackage(file);
            img = "img/" + file.getName();
            imagen.setStyle("-fx-background-image: url('file:" + nombreFinal+img + "'); -fx-background-size: 150px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");

        }
        return false;
    }

    private void copyImageToFilePackage(File file) {
        Path entrada = Paths.get(file.getAbsolutePath());
        System.out.println("ruta final: " + rutaFinal +file.getName());
        Path salida = Paths.get(rutaFinal +file.getName());
        try {
            InputStream input = Files.newInputStream(entrada);
            OutputStream output = Files.newOutputStream(salida);
            int c;
            while ((c = input.read()) != -1) {
                output.write(c);
            }
            input.close();
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void cancelar() {
        producto.restarID();
        escenarioEdicion.close();
    }

    private boolean datosValidos() {
        boolean error = false;
        if (nombreTextField.getText() == null || nombreTextField.getText().length() == 0) {
            error = true;
        }
        if (precioTextField.getText() == null || precioTextField.getText().length() == 0) {
            error = true;
        } else {
            try {
                Double.parseDouble(precioTextField.getText());
            } catch (Exception e) {
                error = true;
            }

        }
        if (stockTextField.getText() == null || stockTextField.getText().length() == 0) {
            error = true;
        } else {
            try {
                Integer.parseInt(stockTextField.getText());
            } catch (Exception e) {
                error = true;
            }
        }
        if(img==null){
            error=true;
        }
        if (error) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText("Datos no validos!");
            alerta.setContentText("Por favor,corrija los errores");
            alerta.showAndWait();
        }
        return !error;
    }

}
