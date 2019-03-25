package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;

import java.util.*;

@Service
public class RouletteSelectionService extends GeneticAlgorithmService{

    @Autowired
    ParametersService parametersService;

    @Autowired
    OptimizationService optimizationService;

    public List<Individual> setRouletteMap(List<Individual> orderedPopulation){
        final double sum = orderedPopulation.stream().mapToDouble(Individual::getFitness).sum();
        orderedPopulation.forEach(individual ->
                individual.setFitnessNormalized(getNormalizedFitness(individual, sum)));
        return orderedPopulation;
    }

    public double getNormalizedFitness(Individual individual, double fitnessSum){
        return Math.exp(50*individual.getFitness()/Math.abs(fitnessSum));
    }

    public List<Individual> prepareSelection(List<Individual> population){
        return setRouletteMap(population);
    }

    public List<List<Integer>> makeSelection(List<Individual> individuals){
        double normalizedSum = 0;
        for(Individual individual: individuals){
            normalizedSum += individual.getFitnessNormalized();
            individual.setRouletteSum(normalizedSum);
        }
        double rouletteRandom = Math.random()*normalizedSum;
        List<List<Integer>> chromosomes = new ArrayList<>();
        chromosomes.add(individuals.stream()
                .filter(individual -> individual.getRouletteSum() >= rouletteRandom)
                .findFirst().orElse(new Individual()).getChromosome());
        chromosomes.add(individuals.stream()
                .filter(individual -> individual.getRouletteSum() >= rouletteRandom)
                .findFirst().orElse(new Individual()).getChromosome());
        return chromosomes;
    }

}
