package pl.guminski.ga.models.GA.treeModel;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.guminski.ga.models.GA.Individual;

import static pl.guminski.ga.services.geneticAlgorithm.ChromosomeGenerationService.getRandomChromosomeStringRepresentation;

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
