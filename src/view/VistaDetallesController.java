package view;

import controller.GestorProductos;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Producto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
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
    private TextArea descripcion;
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
    private static final String SIN_MODIFICACIONES = "Sin modificaciones";

    String rutaJar = GestorProductos.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    File fichero = new File(rutaJar);
    String nombreFinal = rutaJar.replace(fichero.getName(), "");
    private BarCodeGenerator bar;

    public VistaDetallesController() {
    }

    public void setEscenarioDetalles(Stage escenarioDetalles) {
        this.escenarioDetalles = escenarioDetalles;
    }

    public void setProducto(Producto producto) {
        this.product = producto;
        fotoProducto.setImage(new Image("file:" + nombreFinal + producto.getPathImagen()));
        id.setText("ID : " + producto.getId() + "");
        nombre.setText("Nombre : " + producto.getNombre());
        precio.setText("Precio : " + producto.getPrecio() + "");
        stock.setText("Stock : " + producto.getStock() + "");
        fechaAlta.setText("Fecha de alta : " + producto.getFechaAlta());
        if (producto.getFechaModificacion() == null) {
            fechaModificacion.setText("Fecha de modificación : " + SIN_MODIFICACIONES);
        } else {
            fechaModificacion.setText("Fecha de modificación : " + producto.getFechaModificacion());
        }

        descripcion.setEditable(false);
        descripcion.setText(producto.getDescripcion());
        bar = new BarCodeGenerator(producto.getId() + "");
        bar.anadirAImageView(codigoBarrasImg);
    }

    public void setPrueba(GestorProductos prueba) {
        this.prueba = prueba;
    }

    public void setTableView(TableView tabla) {
        this.tabla = tabla;
    }

    @FXML
    public void editar() {
        boolean pulsadoGuardar = prueba.muestraEditaProducto(product);
        if (pulsadoGuardar) {
            this.setProducto(product);
            tabla.setItems(prueba.getDatosProducto());
            tabla.refresh();
        }
    }

    public void crearPDF() throws IOException {
        //Creo un nuevo documento, una página y la añado
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        documento.addPage(pagina);
        documento.getPage(0);
        //Inicio un nuevo stream de contenido
        PDPageContentStream contentStream = new PDPageContentStream(documento, pagina);

        //Establezco la posición Y de la primera líena y el tipo de fuente
        int linea = 700;
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, linea);
        contentStream.showText(product.getId() + " ");
        contentStream.showText(product.getNombre() + " ");
        contentStream.showText(product.getDescripcion() + " ");
        contentStream.showText(product.getFechaAlta()+ " ");
        contentStream.showText(product.getFechaModificacion()+ " ");
        contentStream.showText(product.getStock()+ " ");
        contentStream.drawImage(JPEGFactory.createFromImage(documento, bar.getBuffered()), 0, 0, 100, 100);
        
        contentStream.endText();
        //Cambio de línea
        linea -= 25;

        //Cierro el content stream
        contentStream.close();
        //INicio el file chooser
        FileChooser fileChooser = new FileChooser();

        //Filtro para la extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        //Muestro el diálogo de guardar
        File archivo = fileChooser.showSaveDialog(escenarioDetalles);
        if (archivo != null) {

            //Me aseguro de que tiene la extensión correcta y si no la cambio
            String extension = "";
            if (!archivo.getPath().endsWith(extension)) {
                extension = ".pdf";
            }
            //Escribo en el archivo y lo cierro
            archivo = new File(archivo.getPath() + extension);
            documento.save(archivo);
            documento.close();

        }
        
        //Abro el archivo en el visor de PDF del sistema
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(archivo);
            } catch (IOException ex) {
            }
        }
    }
    
    @FXML
    private void pdf() throws IOException {
      crearPDF();
    }
}
