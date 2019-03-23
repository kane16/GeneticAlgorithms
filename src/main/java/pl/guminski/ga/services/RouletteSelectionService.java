package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.exceptions.NoItemFoundInCity;
import pl.guminski.ga.models.Individual;
import pl.guminski.ga.models.dataInput.Item;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouletteSelectionService extends GeneticAlgorithmService{

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
        List<Individual> individuals = new ArrayList<>();
        List<Individual> tourPopulation = new ArrayList<>();
        Random random = new Random();
        int selectionSize = parametersService.getTour();
        while(tourPopulation.size() < selectionSize){
            int rouletteRandom = random.nextInt(population.size());
            if(!tourPopulation.contains(population.get(rouletteRandom)))
                tourPopulation.add(population.get(rouletteRandom));
        }
        setRouletteMap(tourPopulation);
        while(individuals.size() < selectionSize/2){
            double rouletteRandom = Math.random()*rouletteSum;
            tourPopulation.stream()
                    .filter(individual -> !individuals.contains(individual) && individual.getRouletteSum() >= rouletteRandom)
                    .findFirst().ifPresent(individuals::add);
        }
        return individuals;
    }

}
