package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import model.Producto;

public class VistaEstadisticasController {
    @FXML
    private PieChart grafico = new PieChart();

    private ObservableList datosProducto = FXCollections.observableArrayList();

    private WritableImage img;

    private Stage escenarioEstadisticas;

    public void setProductos(ObservableList productos) {
        //Obtengo los productos
        this.datosProducto = productos;

        //PieChart Data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        //Meto los nombres y stock en sus respectivas listas observables
        for (Object p:datosProducto
             ) {
            pieChartData.add(new PieChart.Data(((Producto) p).getNombre(), Double.valueOf(((Producto) p).getStock())));
        }

        this.grafico.setData(pieChartData);
        this.grafico.setTitle("Stock");
        grafico.setVisible(true);


    }

    public void setEscenarioEstadisticas(Stage escenarioEstadisticas) {
        this.escenarioEstadisticas = escenarioEstadisticas;
    }
}
