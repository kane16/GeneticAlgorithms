package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankSelectionService extends GeneticAlgorithmService{

    @Autowired
    ParametersService parametersService;

    public List<Individual> makePreparationForSelectionWithRank(List<Individual> population){
        double fitnessSum = population.stream().mapToDouble(Individual::getFitness).sum();
        population.forEach(individual ->
                individual.setFitnessNormalized(getNormalizedFitness(individual, fitnessSum)));
        population = population.stream().sorted(Comparator.comparing(Individual::getFitnessNormalized))
                .collect(Collectors.toList());
        int sum = 0;
        for(int i=0; i<population.size();i++){
            sum += i;
        }
        double partialSum =0;
        for(int i=0; i<population.size();i++){
            partialSum += i*1.0;
            population.get(i).setRank(partialSum/sum);
        }
        return population;
    }

    public List<Individual> prepareSelection(List<Individual> initialPopulation){
        return makePreparationForSelectionWithRank(initialPopulation);
    }

    public List<List<Integer>> makeSelection(List<Individual> preparedPopulation){
        double rouletteRandom = Math.random();
        List<List<Integer>> chromosomes = new ArrayList<>();
        chromosomes.add(preparedPopulation.stream()
                .filter(individual -> individual.getRank() >= rouletteRandom)
                .findFirst().map(Individual::getChromosome).orElse(null));
        chromosomes.add(preparedPopulation.stream()
                .filter(individual -> individual.getRank() >= rouletteRandom)
                .findFirst().map(Individual::getChromosome).orElse(null));
        return chromosomes;
    }

    public double getNormalizedFitness(Individual individual, double fitnessSum){
        return Math.exp(50*individual.getFitness()/Math.abs(fitnessSum));
    }

}
