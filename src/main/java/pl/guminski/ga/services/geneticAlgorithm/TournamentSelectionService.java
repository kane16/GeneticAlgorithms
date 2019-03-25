package pl.guminski.ga.services.geneticAlgorithm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentSelectionService extends GeneticAlgorithmService{

    @Autowired
    ParametersService parametersService;

    @Override
    public List<Individual> prepareSelection(List<Individual> population){
        return population.stream().sorted(Comparator.comparing(Individual::getFitness).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<List<Integer>> makeSelection(List<Individual> preparedPopulation) {
        List<Individual> chosenPopulation = new ArrayList<>();
        int chosenIndividual = (int) (Math.random() * preparedPopulation.size());
        while(preparedPopulation.size() < parametersService.getTour() &&
                !chosenPopulation.contains(preparedPopulation.get(chosenIndividual))){
            chosenPopulation.add(preparedPopulation.get(chosenIndividual));
        }
        preparedPopulation = preparedPopulation.stream().sorted(Comparator.comparing(Individual::getFitness).reversed())
                .collect(Collectors.toList());
        List<List<Integer>> chromosomes = new ArrayList<>();
        chromosomes.add(preparedPopulation.get(0).getChromosome());
        chromosomes.add(preparedPopulation.get(1).getChromosome());
        return chromosomes;
    }

    public double getNormalizedFitness(Individual individual, double fitnessSum){
        return Math.exp(50*individual.getFitness()/Math.abs(fitnessSum));
    }

}
