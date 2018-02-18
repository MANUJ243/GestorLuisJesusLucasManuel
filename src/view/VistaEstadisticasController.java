package view;

import controller.GestorProductos;
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
        WritableImage image = root.snapshot(null, null);
        File file = new File(nombreFinal+"snaps/chart.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }catch (IOException ignored) {}
    }

    public void setEscenarioEstadisticas(Stage escenarioEstadisticas) {
        this.escenarioEstadisticas = escenarioEstadisticas;
    }
}
