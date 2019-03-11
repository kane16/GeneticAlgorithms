package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChromosomeGenerationService {

    @Autowired
    ParametersService parametersService;

    @Autowired
    OptimizationService optimizationService;

    public List<Individual> generateRandomIndividuals(int coordsSize, int numberOfIndividuals){

        List<Individual> individuals = new ArrayList<>();

        for(int i=0 ; i<numberOfIndividuals ; i++){
            Individual individual = new Individual();
            individual.setIndex(i+1);
            individual.setChromosome(getRandomChromosome(coordsSize));
            individual.setFitness(optimizationService
                    .getFitnessValue(parametersService.getDataInputContainer(),
                            individual.getChromosome()));
            individuals.add(individual);
        }

        return individuals;
    }

    public List<Integer> getRandomChromosome(int coordsSize) {
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
