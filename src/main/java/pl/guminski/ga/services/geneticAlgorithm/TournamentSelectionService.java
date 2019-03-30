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

    @Autowired
    RouletteSelectionService rouletteSelectionService;

    @Override
    public List<Individual> prepareSelection(List<Individual> population){
        return population.stream().sorted(Comparator.comparing(Individual::getFitness).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<List<Integer>> makeSelection(List<Individual> preparedPopulation) {
        List<Individual> chosenPopulation;
        chosenPopulation = rouletteSelectionService.makeSelectionForTour(preparedPopulation, parametersService.getTour());
        chosenPopulation = chosenPopulation.stream().sorted(Comparator.comparing(Individual::getFitness).reversed())
                .collect(Collectors.toList());
        List<List<Integer>> chromosomes = new ArrayList<>();
        chromosomes.add(chosenPopulation.get(0).getChromosome());
        chromosomes.add(chosenPopulation.get(1).getChromosome());
        return chromosomes;
    }

    public double getNormalizedFitness(Individual individual, double fitnessSum){
        return Math.exp(50*individual.getFitness()/Math.abs(fitnessSum));
    }

}
