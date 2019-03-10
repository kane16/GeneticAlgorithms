package pl.guminski.ga.dataProcessing;

import pl.guminski.ga.models.Individual;

import java.util.*;
import java.util.stream.Collectors;

public class OptimizationModel {

    public Integer generationNumber;

    public static List<Individual> generateRandomIndividuals(int coordsSize, int numberOfIndividuals){

        List<Individual> individuals = new ArrayList<>();

        for(int i=0 ; i<numberOfIndividuals ; i++){
            Individual individual = new Individual();
            individual.setChromosome(getRandomChromosome(coordsSize));
            individuals.add(individual);
        }

        return individuals;
    }

    public static List<Integer> getRandomChromosome(int coordsSize) {
        ArrayList<Integer> chromosome = new ArrayList<>();
        int i=0;
        while(i < coordsSize){
            i++;
            chromosome.add(i);
        }
        Collections.shuffle(chromosome);
        return chromosome;
    }

    public static String getRandomChromosomeStringRepresentation(List<Integer> chromosome){
        StringBuilder gens = new StringBuilder();
        for(int i=0 ; i<chromosome.size() ; i++){
            gens.append(chromosome.get(i)).append(" ");
        }
        return gens.toString();
    }

}
