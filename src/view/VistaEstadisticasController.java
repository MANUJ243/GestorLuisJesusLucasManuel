package view;

import com.sun.scenario.effect.impl.prism.PrDrawable;
import controller.Prueba;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import model.Empaquetador;
import model.Producto;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VistaEstadisticasController {
    @FXML
    private BarChart<String, Integer> graficoBarras;

    @FXML
    private CategoryAxis ejeX;

    @FXML
    private NumberAxis ejeY;

    private ObservableList datosProducto = FXCollections.observableArrayList();

    private ObservableList<String> listaNombres = FXCollections.observableArrayList();

    private ObservableList<String> listaStock = FXCollections.observableArrayList();

    private WritableImage img;

    private Stage escenarioEstadisticas;

    public void setProductos(ObservableList productos) {
        //Obtengo los productos
        this.datosProducto = productos;

        //Meto los nombres y stock en sus respectivas listas observables
        for (Object p:datosProducto
             ) {
            listaNombres.add(((Producto) p).getNombre());
            listaStock.add(((Producto) p).getStock());
        }

        //Meto en el ejeX que es el de categorias la lista con los nombres de los productos
        ejeX.setCategories(listaNombres);

        //Separacion entre categorias
        graficoBarras.setCategoryGap(20);

        //Labels de los ejes
        ejeX.setLabel("Producto");
        ejeY.setLabel("Stock");

        //Nueva serie "Stock"
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Stock");

        //Recorro la listaStock y creo los datos de la grafica
        int i = 0;
        for (String s: listaStock
                ) {
            series.getData().add(new XYChart.Data<>(listaNombres.get(i), Integer.parseInt(listaStock.get(i))));
            i++;
        }

        //Añado la serie al gráfico
        graficoBarras.getData().add(series);

        graficoBarras.autosize();
        graficoBarras.setTitle("Stock disponible");

        WritableImage image = graficoBarras.snapshot(new SnapshotParameters(), null);

        File file = new File("src/snaps/chart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }catch (IOException ignored) {
        }
    }

    public void setEscenarioEstadisticas(Stage escenarioEstadisticas) {
        this.escenarioEstadisticas = escenarioEstadisticas;
    }
}
