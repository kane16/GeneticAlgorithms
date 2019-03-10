package pl.guminski.ga.services;

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
import pl.guminski.ga.models.treeModel.TreeChromosome;


@Service
public class PopulateTableService {

    @Autowired
    SimulationService simulationService;

    public void populateTable(JFXTreeTableView treeTable, boolean isSimulationTable){
        JFXTreeTableColumn<TreeChromosome, String> chromosomeNumber = getChromosomeNumber();
        JFXTreeTableColumn<TreeChromosome, String> fitnessValue = getChromosomeFitness();
        JFXTreeTableColumn<TreeChromosome, String> generationValue = getChromosomeGeneration();
        JFXTreeTableColumn<TreeChromosome, String> chromosomeValue = getChromosomeColumn();

        ObservableList<TreeChromosome> chromosomes = FXCollections.observableArrayList();
        this.simulationService.getPopulation()
                .forEach(individual -> chromosomes.add(new TreeChromosome((long) individual.getIndex(), individual)));


        final TreeItem<TreeChromosome> root =
                new RecursiveTreeItem<TreeChromosome>(chromosomes, RecursiveTreeObject::getChildren);

        treeTable.getColumns().clear();
        treeTable.setRoot(root);
        if(isSimulationTable){
            treeTable.getColumns().setAll(chromosomeNumber, fitnessValue, generationValue, chromosomeValue);
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
                return param.getValue().getValue().fitness;
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
