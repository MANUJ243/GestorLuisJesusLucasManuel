package view;

import controller.GestorProductos;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Producto;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;

public class VistaEstadisticasController{
    @FXML
    private AnchorPane root = new AnchorPane();

    @FXML
    private PieChart grafico = new PieChart();

    private ObservableList datosProducto = FXCollections.observableArrayList();

    private WritableImage img;

    private Stage escenarioEstadisticas;
    
    String rutaJar = GestorProductos.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File fichero = new File(rutaJar);        
        String nombreFinal = rutaJar.replace(fichero.getName(),"");

    public void setProductos(ObservableList productos) {
        
            //Obtengo los productos
            this.datosProducto = productos;
            
            //PieChart Data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            
            //Meto los nombres y stock en sus respectivas listas observables
            for (Object p:datosProducto) {
                pieChartData.add(new PieChart.Data(((Producto) p).getNombre(), Double.valueOf(((Producto) p).getStock())));
            }
            
            //Meto los datos en el grafo y le quito los labels y la leyenda
            this.grafico.setData(pieChartData);
            this.grafico.setTitle("Stock");
            this.grafico.setLegendVisible(false);
            this.grafico.setAnimated(false);
            this.grafico.setVisible(true);
            
            //EventHandler para cada trozo de tarta
            for (final PieChart.Data dt:grafico.getData()) {
                dt.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> grafico.setTitle("Producto: " + dt.getName() + "; Stock: " + dt.getPieValue()));
            }
            
            //Intento de captura
    }

    public void setEscenarioEstadisticas(Stage escenarioEstadisticas) {
        this.escenarioEstadisticas = escenarioEstadisticas;
    }
    
    @FXML
    public void crearPDF() throws IOException {
        WritableImage image = grafico.snapshot(null, null);
        
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        documento.addPage(pagina);
        documento.getPage(0);

        PDPageContentStream contentStream = new PDPageContentStream(documento, pagina);

        int linea = 700;
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);

        contentStream.drawImage(JPEGFactory.createFromImage(documento, SwingFXUtils.fromFXImage(image, null)), 0, linea, 100, 100);

        contentStream.close();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        File archivo = fileChooser.showSaveDialog(escenarioEstadisticas);
        if (archivo != null) {

            String extension = "";
            if (!archivo.getPath().endsWith(extension)) {
                extension = ".pdf";
            }

            archivo = new File(archivo.getPath() + extension);
            documento.save(archivo);
            documento.close();

        }

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(archivo);
            } catch (IOException ex) {
            }
        }
    }
}
