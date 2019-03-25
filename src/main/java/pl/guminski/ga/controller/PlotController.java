package pl.guminski.ga.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.models.GA.StatisticsIndividual;
import pl.guminski.ga.services.geneticAlgorithm.SimulationService;

import java.util.List;

@Controller
public class PlotController {


    @FXML
    JFXComboBox tableNameChoice;

    @FXML
    AnchorPane plotPane;

    ObservableList<String> tableNames = FXCollections.observableArrayList();

    @Autowired
    SimulationService simulationService;


    public void initialize(){
        tableNames.addAll("All algorithms", "Rank", "Roulette", "Random", "Greedy");
        tableNameChoice.setValue("All algorithms");
        tableNameChoice.setItems(tableNames);
    }

    public void onChosenTable(){
        if (tableNameChoice.getValue().equals("All algorithms")) {
            setChart("All algorithms");
        }
    }

    public BarChart getChart(String table) {
        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();
        if(table.equals("All algorithms")){
            XYChart.Series<String, Double> rankSeries = new XYChart.Series<>();
            XYChart.Series<String, Double> rouletteSeries = new XYChart.Series<>();
            rankSeries.setName("Rank");
            rouletteSeries.setName("Roulette");

            List<StatisticsIndividual> rankIndividuals = simulationService.rankAlgorithmBestIndividuals;
            List<StatisticsIndividual> rouletteIndividuals = simulationService.rouletteAlgorithmBestIndividuals;


            for(StatisticsIndividual individual: rankIndividuals){
                rankSeries.getData().add(new XYChart.Data<>(Integer.toString(individual.getGeneration()), individual.getFitness()));
            }

            for(StatisticsIndividual individual: rouletteIndividuals){
                rouletteSeries.getData().add(new XYChart.Data<>(Integer.toString(individual.getGeneration()), individual.getFitness()));
            }

            data.addAll(rankSeries, rouletteSeries);
        }

        if(data.size() > 0){
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            return new BarChart(xAxis, yAxis, data);
        }else return null;
    }

    public void setChart(String tableName){
        BarChart barChart = getChart(tableName);
        if(barChart != null){
            barChart.setMinWidth(800);
            barChart.setMinHeight(600);
            plotPane.getChildren().clear();
            plotPane.getChildren().add(barChart);
        }
    }



}
