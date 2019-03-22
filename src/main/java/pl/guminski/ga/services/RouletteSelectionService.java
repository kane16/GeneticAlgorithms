package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.exceptions.NoItemFoundInCity;
import pl.guminski.ga.models.Individual;
import pl.guminski.ga.models.dataInput.Item;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouletteSelectionService {

    private double rouletteSum;

    @Autowired
    ParametersService parametersService;

    @Autowired
    OptimizationService optimizationService;

    public void setRouletteMap(List<Individual> orderedPopulation){
        final double sum = orderedPopulation.stream().mapToDouble(Individual::getFitness).sum();
        orderedPopulation.forEach(individual ->
                individual.setFitnessNormalized(getNormalizedFitness(individual, sum)));
        double normalizedSum = 0;
        for(Individual individual: orderedPopulation){
            normalizedSum += individual.getFitnessNormalized();
            individual.setRouletteSum(normalizedSum);
        }
        this.rouletteSum = normalizedSum;
    }

    public double getNormalizedFitness(Individual individual, double fitnessSum){
        return Math.exp(50*individual.getFitness()/Math.abs(fitnessSum));
    }

    public List<Individual> makeSelection(List<Individual> population){
        setRouletteMap(population);
        List<Individual> individuals = new ArrayList<>();
        int selectionSize = parametersService.getPop_size()/parametersService.getTour();
        while(individuals.size() < selectionSize){
            double rouletteRandom = Math.random()*rouletteSum;
            population.stream()
                    .filter(individual -> !individuals.contains(individual) && individual.getRouletteSum() >= rouletteRandom)
                    .findFirst().ifPresent(individuals::add);
        }
        return individuals;
    }

}
