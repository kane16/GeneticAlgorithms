package pl.guminski.ga.services.geneticAlgorithm;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;
import pl.guminski.ga.models.GA.treeModel.TreeChromosome;
import pl.guminski.ga.models.GA.treeModel.TreeOutputTable;

import java.util.List;


@Service
public class PopulateTableService {

    @Autowired
    SimulationService simulationService;

    @Autowired
    FormatService formatService;

    @Autowired
    ParametersService parametersService;

    public void populateAllAlgorithmsTable(JFXTreeTableView treeTable, String table){
        formatService.setDecimalFormat();
        formatService.setIntegerFormat();
        JFXTreeTableColumn<TreeOutputTable, String> generationValue = getGenerationValue();
        JFXTreeTableColumn<TreeOutputTable, String> tourValue = getTourFitnessValue();
        JFXTreeTableColumn<TreeOutputTable, String> tourStdDev = getTourStdDevValue();
        JFXTreeTableColumn<TreeOutputTable, String> fitnessRankValue = getRankFitnessValue();
        JFXTreeTableColumn<TreeOutputTable, String> rankStdDeviation = getRankStdDevValue();
        JFXTreeTableColumn<TreeOutputTable, String> fitnessRouletteValue = getRouletteFitnessValue();
        JFXTreeTableColumn<TreeOutputTable, String> rouletteStdDeviation = getRouletteRankStdDevValue();
        JFXTreeTableColumn<TreeOutputTable, String> fitnessGreedyValue = getFitnessGreedyValue();
        JFXTreeTableColumn<TreeOutputTable, String> greedyStdDevValue = getGreedyStdDevValue();
        JFXTreeTableColumn<TreeOutputTable, String> randomFitnessValue = getRandomFitnessValue();
        JFXTreeTableColumn<TreeOutputTable, String> randomStdDeviationValue = getRandomStdDeviationValue();

        ObservableList<TreeOutputTable> chromosomes = FXCollections.observableArrayList();
        for(int i=0; i<parametersService.getGenerationNumber()+1;i++){
            TreeOutputTable treeOutputTable = new TreeOutputTable();
            treeOutputTable.generation = simulationService.tournamentAlgorithmBestIndividuals.get(i).getGeneration();
            treeOutputTable.tourFitness = simulationService.tournamentAlgorithmBestIndividuals.get(i).getFitness();
            treeOutputTable.tourStdDeviation = simulationService.tournamentAlgorithmBestIndividuals.get(i).getStdDeviation();
            treeOutputTable.rankFitness = simulationService.rankAlgorithmBestIndividuals.get(i).getFitness();
            treeOutputTable.rankStdDeviation = simulationService.rankAlgorithmBestIndividuals.get(i).getStdDeviation();
            treeOutputTable.rouletteFitness = simulationService.rouletteAlgorithmBestIndividuals.get(i).getFitness();
            treeOutputTable.rouletteStdDeviation = simulationService.rouletteAlgorithmBestIndividuals.get(i).getStdDeviation();
            treeOutputTable.greedyFitness = simulationService.greedyBestIndividual.get(i).getFitness();
            treeOutputTable.greedyStdDeviation = simulationService.greedyBestIndividual.get(i).getStdDeviation();
            treeOutputTable.randomFitness = simulationService.randomBestIndividual.get(i).getFitness();
            treeOutputTable.randomStdDeviation = simulationService.randomBestIndividual.get(i).getStdDeviation();
            chromosomes.add(treeOutputTable);
        }


        final TreeItem<TreeOutputTable> root =
                new RecursiveTreeItem<>(chromosomes, RecursiveTreeObject::getChildren);

        treeTable.getColumns().clear();
        treeTable.setRoot(root);
        if(table.equals("All algorithms")){
            treeTable.getColumns().setAll(generationValue, getTourFitnessValue(), getTourStdDevValue(),
                    fitnessRankValue, rankStdDeviation, fitnessRouletteValue,
                    rouletteStdDeviation, fitnessGreedyValue, greedyStdDevValue, randomFitnessValue, randomStdDeviationValue);
        }else if(table.equals("Rank")){
            treeTable.getColumns().setAll(generationValue, fitnessRankValue, rankStdDeviation);
        }else if(table.equals("Roulette")){
            treeTable.getColumns().setAll(generationValue, fitnessRouletteValue, rouletteStdDeviation);
        }else if(table.equals("Random")){
            treeTable.getColumns().setAll(generationValue, randomFitnessValue, randomStdDeviationValue);
        }else if(table.equals("Greedy")){
            treeTable.getColumns().setAll(generationValue, fitnessGreedyValue, greedyStdDevValue);
        }
        treeTable.setShowRoot(false);
        treeTable.setEditable(false);

    }

    private JFXTreeTableColumn<TreeOutputTable, String> getTourStdDevValue() {
        JFXTreeTableColumn<TreeOutputTable, String> tourStdDevValue = new JFXTreeTableColumn<>("Tour std. dev.");
        tourStdDevValue.setPrefWidth(100);
        tourStdDevValue.setStyle("-fx-alignment: center;");
        tourStdDevValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(tourStdDevValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().tourStdDeviation));
            } else {
                return tourStdDevValue.getComputedValue(param);
            }
        });
        return tourStdDevValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getTourFitnessValue() {
        JFXTreeTableColumn<TreeOutputTable, String> tourFitnessValue = new JFXTreeTableColumn<>("Tour fitness");
        tourFitnessValue.setPrefWidth(100);
        tourFitnessValue.setStyle("-fx-alignment: center;");
        tourFitnessValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(tourFitnessValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().tourFitness));
            } else {
                return tourFitnessValue.getComputedValue(param);
            }
        });
        return tourFitnessValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getRandomStdDeviationValue() {
        JFXTreeTableColumn<TreeOutputTable, String> randomStdDevValue = new JFXTreeTableColumn<>("Random std. dev.");
        randomStdDevValue.setPrefWidth(100);
        randomStdDevValue.setStyle("-fx-alignment: center;");
        randomStdDevValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(randomStdDevValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().randomStdDeviation));
            } else {
                return randomStdDevValue.getComputedValue(param);
            }
        });
        return randomStdDevValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getRandomFitnessValue() {
        JFXTreeTableColumn<TreeOutputTable, String> randomFitnessValue = new JFXTreeTableColumn<>("Random fitness");
        randomFitnessValue.setPrefWidth(100);
        randomFitnessValue.setStyle("-fx-alignment: center;");
        randomFitnessValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(randomFitnessValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().randomFitness));
            } else {
                return randomFitnessValue.getComputedValue(param);
            }
        });
        return randomFitnessValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getGreedyStdDevValue() {
        JFXTreeTableColumn<TreeOutputTable, String> greedyStdDevValue = new JFXTreeTableColumn<>("Greedy std. dev.");
        greedyStdDevValue.setPrefWidth(100);
        greedyStdDevValue.setStyle("-fx-alignment: center;");
        greedyStdDevValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(greedyStdDevValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().greedyStdDeviation));
            } else {
                return greedyStdDevValue.getComputedValue(param);
            }
        });
        return greedyStdDevValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getFitnessGreedyValue() {
        JFXTreeTableColumn<TreeOutputTable, String> greedyFitnessValue = new JFXTreeTableColumn<>("Greedy fitness");
        greedyFitnessValue.setPrefWidth(100);
        greedyFitnessValue.setStyle("-fx-alignment: center;");
        greedyFitnessValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(greedyFitnessValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().greedyFitness));
            } else {
                return greedyFitnessValue.getComputedValue(param);
            }
        });
        return greedyFitnessValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getRouletteFitnessValue() {
        JFXTreeTableColumn<TreeOutputTable, String> rouletteFitnessValue = new JFXTreeTableColumn<>("Roulette fitness");
        rouletteFitnessValue.setPrefWidth(100);
        rouletteFitnessValue.setStyle("-fx-alignment: center;");
        rouletteFitnessValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(rouletteFitnessValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().rouletteFitness));
            } else {
                return rouletteFitnessValue.getComputedValue(param);
            }
        });
        return rouletteFitnessValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getRouletteRankStdDevValue() {
        JFXTreeTableColumn<TreeOutputTable, String> rankValue = new JFXTreeTableColumn<>("Roulette std. dev.");
        rankValue.setPrefWidth(100);
        rankValue.setStyle("-fx-alignment: center;");
        rankValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(rankValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().rouletteStdDeviation));
            } else {
                return rankValue.getComputedValue(param);
            }
        });
        return rankValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getRankStdDevValue() {
        JFXTreeTableColumn<TreeOutputTable, String> rankValue = new JFXTreeTableColumn<>("Rank std. dev.");
        rankValue.setPrefWidth(100);
        rankValue.setStyle("-fx-alignment: center;");
        rankValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(rankValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().rankStdDeviation));
            } else {
                return rankValue.getComputedValue(param);
            }
        });
        return rankValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getRankFitnessValue() {
        JFXTreeTableColumn<TreeOutputTable, String> rankValue = new JFXTreeTableColumn<>("Rank fitness");
        rankValue.setPrefWidth(100);
        rankValue.setStyle("-fx-alignment: center;");
        rankValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(rankValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().rankFitness));
            } else {
                return rankValue.getComputedValue(param);
            }
        });
        return rankValue;
    }

    private JFXTreeTableColumn<TreeOutputTable, String> getGenerationValue() {
        JFXTreeTableColumn<TreeOutputTable, String> generationValue = new JFXTreeTableColumn<>("Generation");
        generationValue.setPrefWidth(100);
        generationValue.setStyle("-fx-alignment: center;");
        generationValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeOutputTable, String> param) -> {
            if(generationValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.integerFormat
                        .format(param.getValue().getValue().generation));
            } else {
                return generationValue.getComputedValue(param);
            }
        });
        return generationValue;
    }

    public void populateTable(JFXTreeTableView treeTable, boolean isSimulationTable, List<Individual> individuals){
        formatService.setDecimalFormat();
        formatService.setIntegerFormat();
        JFXTreeTableColumn<TreeChromosome, String> chromosomeNumber = getChromosomeNumber();
        JFXTreeTableColumn<TreeChromosome, String> fitnessValue = getChromosomeFitness();
        JFXTreeTableColumn<TreeChromosome, String> generationValue = getChromosomeGeneration();
        JFXTreeTableColumn<TreeChromosome, String> chromosomeValue = getChromosomeColumn();

        ObservableList<TreeChromosome> chromosomes = FXCollections.observableArrayList();
        individuals
                .forEach(individual -> chromosomes.add(new TreeChromosome((long) individual.getIndex(), individual)));


        final TreeItem<TreeChromosome> root =
                new RecursiveTreeItem<TreeChromosome>(chromosomes, RecursiveTreeObject::getChildren);

        treeTable.getColumns().clear();
        treeTable.setRoot(root);
        if(isSimulationTable){
            treeTable.getColumns().setAll(generationValue, fitnessValue);
        }else treeTable.getColumns().setAll(chromosomeNumber, fitnessValue, chromosomeValue);
        treeTable.setShowRoot(false);
        treeTable.setEditable(false);
    }

    private JFXTreeTableColumn<TreeChromosome, String> getChromosomeFitness() {
        JFXTreeTableColumn<TreeChromosome, String> generationValue = new JFXTreeTableColumn<>("Fitness");
        generationValue.setPrefWidth(100);
        generationValue.setStyle("-fx-alignment: center;");
        generationValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeChromosome, String> param) -> {
            if(generationValue.validateValue(param)) {
                return new SimpleStringProperty(formatService.decimalFormat
                        .format(param.getValue().getValue().fitness));
            } else {
                return generationValue.getComputedValue(param);
            }
        });
        return generationValue;
    }

    private JFXTreeTableColumn<TreeChromosome, String> getChromosomeGeneration() {
        JFXTreeTableColumn<TreeChromosome, String> generationValue = new JFXTreeTableColumn<>("Generation");
        generationValue.setPrefWidth(100);
        generationValue.setStyle("-fx-alignment: center;");
        generationValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeChromosome, String> param) -> {
            if(generationValue.validateValue(param)) {
                return param.getValue().getValue().generation;
            } else {
                return generationValue.getComputedValue(param);
            }
        });
        return generationValue;
    }

    private JFXTreeTableColumn<TreeChromosome, String> getChromosomeNumber() {
        JFXTreeTableColumn<TreeChromosome, String> chromosomeValue = new JFXTreeTableColumn<>("No.");
        chromosomeValue.setPrefWidth(100);
        chromosomeValue.setStyle("-fx-alignment: center;");
        chromosomeValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeChromosome, String> param) -> {
            if(chromosomeValue.validateValue(param)) {
                return new SimpleStringProperty(param.getValue().getValue().id.toString());
            } else {
                return chromosomeValue.getComputedValue(param);
            }
        });
        return chromosomeValue;
    }

    private JFXTreeTableColumn<TreeChromosome, String> getChromosomeColumn() {
        JFXTreeTableColumn<TreeChromosome, String> chromosomeValue = new JFXTreeTableColumn<>("Chromosome value");
        chromosomeValue.setPrefWidth(1000);
        chromosomeValue.setStyle("-fx-alignment: center;");
        chromosomeValue.setCellValueFactory((TreeTableColumn.CellDataFeatures<TreeChromosome, String> param) -> {
            if(chromosomeValue.validateValue(param)) {
                return param.getValue().getValue().chromosome;
            } else {
                return chromosomeValue.getComputedValue(param);
            }
        });
        return chromosomeValue;
    }

}
