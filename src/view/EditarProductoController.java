package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Producto;

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

    public void setEscenarioEdicion(Stage escenarioEdicion) {
        this.escenarioEdicion = escenarioEdicion;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        idTextField.setText(String.valueOf(producto.getId()));
        nombreTextField.setText(producto.getNombre());
        precioTextField.setText(String.valueOf(producto.getPrecio()));
        stockTextField.setText(String.valueOf(producto.getStock()));
        String img = producto.getPathImagen();
        imagen.setStyle("-fx-background-image: url('file:" + img + "'); -fx-background-size: 150px; -fx-background-repeat: no-repeat; -fx-background-position: 50%; -fx-background-color:#F9F9F9;");

    }

    public boolean pulsadoGuardar() {
        return pulsadoGuardar;
    }

    @FXML
    public void guardar() {
        if (!datosValidos()) {
            producto.setNombre(nombreTextField.getText());
            producto.setPrecio(Double.parseDouble(precioTextField.getText()));
            producto.setStock(Integer.parseInt(stockTextField.getText()));
            producto.setPathImagen("src/img/pera_amarilla.jpg");
            pulsadoGuardar=true;
            escenarioEdicion.close();
        }

    }

    @FXML
    public void cancelar() {
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
        if (error) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText("Datos no validos!");
            alerta.setContentText("Por favor,corrija los errores");
            alerta.showAndWait();
        }
        return error;
    }

}
