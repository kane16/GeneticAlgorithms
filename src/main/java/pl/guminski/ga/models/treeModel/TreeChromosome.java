package pl.guminski.ga.models.treeModel;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import pl.guminski.ga.models.Individual;

import static pl.guminski.ga.services.ChromosomeGenerationService.getRandomChromosomeStringRepresentation;

public class TreeChromosome extends RecursiveTreeObject<TreeChromosome> {

    public Long id;

    public StringProperty chromosome;

    public StringProperty generation;

    public double fitness;

    public TreeChromosome(Long id, Individual individual){
        this.id = id;
        chromosome = new SimpleStringProperty(getRandomChromosomeStringRepresentation(individual.getChromosome()));
        fitness = individual.getFitness();
        generation = new SimpleStringProperty(String.valueOf(individual.getGeneration()));
    }

}
